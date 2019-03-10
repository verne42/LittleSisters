//						Verne42
//                       _oo0oo_
//                      o8888888o
//                      88" . "88
//                      (| -_- |)
//                      0\  =  /0
//                    ___/`---'\___
//                  .' \\|     |// '.
//                 / \\|||  :  |||// \
//                / _||||| -:- |||||- \
//               |   | \\\  -  /// |   |
//               | \_|  ''\---/''  |_/ |
//               \  .-\__  '-'  ___/-. /
//             ___'. .'  /--.--\  `. .'___
//          ."" '<  `.___\_<|>_/___.' >' "".
//         | | :  `- \`.;`\ _ /`;.`/ - ` : | |
//         \  \ `_.   \_ __\ /__ _/   .-` /  /
//     =====`-.____`.___ \_____/___.-`___.-'=====
//                       `=---='
// We code with compassion and love.


package com.verne42.little.sisters.controller;


import com.verne42.little.sisters.domain.nodes.Association;
import com.verne42.little.sisters.domain.nodes.Legislature;
import com.verne42.little.sisters.domain.relationships.Vote;
import com.verne42.little.sisters.services.nodes.AssociationService;
import com.verne42.little.sisters.services.nodes.BillService;
import com.verne42.little.sisters.services.nodes.LegislatorService;
import com.verne42.little.sisters.services.nodes.LegislatureService;
import com.verne42.little.sisters.utils.tables.VoteTable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import java.util.*;


/**
 * The main controller
 */
@Controller
public class V42LittleSistersController
{
	public V42LittleSistersController(AssociationService associationService,
		LegislatorService legislatorService,
		BillService billService,
		LegislatureService legislatureService1)
	{
		this.associationService = associationService;
		this.legislatorService = legislatorService;
		this.billService = billService;
		this.legislatureService = legislatureService1;
	}


	@GetMapping("/")
	public String home()
	{
		return "index";
	}



	@GetMapping("/legislators")
	public String legislators(
		@RequestParam(value="country") String country,
		Model model)
	{
		model.addAttribute("legislators", this.legislatorService.findByCountry(country));
		return "legislators";
	}


	@GetMapping("/dashboard")
	public String dashboard(
		@RequestParam(value="country", required=false) String country,
		@RequestParam(value="billId", required=false) Long billId,
		Model model)
	{
		model.addAttribute("legislatures", this.legislatureService.findAll());
		model.addAttribute("bills", this.billService.findByCountry(country));

		if (billId != null)
		{
			this.addModelBillObjects(model, billId);
			this.addModelAssociationObjects(model, billId);
		}

		return "dashboard";
	}


	/**
	 * This method adds all the objects for the billId in the model.
	 *
	 * @param model The model to set the objects for the billId.
	 * @param billId The billId to get the objects.
	 */
	public void addModelBillObjects(Model model, Long billId)
	{
		model.addAttribute("bill", this.billService.findByBillId(billId));
		model.addAttribute("legislators", this.legislatorService.findByVotedInBillId(billId));
	}


	/**
	 * This method adds all objects from the association related to the billId.
	 * @param model The model to add the associations objects for the billID.
	 * @param billId The billId to get the objects.
	 */
	public void addModelAssociationObjects(Model model, Long billId)
	{
		Legislature chamber1;
		Legislature chamber2 = null;
		Collection<Legislature> legislatures;
		Iterator<Legislature> iterChambers;
		Collection<Association> associationsChamber1;
		Collection<Association> associationsChamber2 = null;

		// Get legislatures where the billID was legislated.
		legislatures = this.legislatureService.findByLegislatedIn(billId);
		iterChambers = legislatures.iterator();
		chamber1 = iterChambers.next();

		// Get all associations who associates voted in bill ID and are in chamber1.
		associationsChamber1 = this.associationService.findByVotedInBillId(
			billId,
			chamber1.getChamber());

		// Model is bicameral, each bill can be legislated in at most two chambers.
		if (iterChambers.hasNext())
		{
			chamber2 = iterChambers.next();
			// Get all associations who associates voted in bill ID and are in chamber2.
			associationsChamber2 = this.associationService.findByVotedInBillId(
				billId,
				chamber2.getChamber());
		}

		// Add chambers
		model.addAttribute("chamber1", chamber1);
		model.addAttribute("chamber2", chamber2);

		// Add associations in chambers
		model.addAttribute("associationsChamber1", associationsChamber1);
		model.addAttribute("associationsChamber2", associationsChamber2);

		// Add associations votes tables.
		model.addAttribute(
			"associationsVotesTableChamber1",
			this.getAssociationsVotesTable(associationsChamber1, chamber1));

		model.addAttribute(
			"associationsVotesTableChamber2",
			this.getAssociationsVotesTable(associationsChamber2, chamber2));
	}


	/**
	 * This method gets the associations vote table.
	 *
	 * @param associations The associations to create the table.
	 * @return A map object representing the associations vote table.
	 */
	public Map<String, VoteTable> getAssociationsVotesTable(
		Collection<Association> associations,
		Legislature chamber)
	{
		Map<String, VoteTable> associationsCounts = new TreeMap<>();

		if (associations != null)
		{
			associations.forEach(association ->
			{
				long infavor =
					association.getAssociationships().stream().filter(associationship ->
						Vote.TypeOfVote.IN_FAVOR.equals(
							associationship.getLegislator().getVotes().iterator().next()
								.getTypeOfVote()) &&
						associationship.getLegislator().getMemberships().iterator().next().
							getLegislature().getChamber().equals(chamber.getChamber())
					).count();

				long against =
					association.getAssociationships().stream().filter(associationship ->
						Vote.TypeOfVote.AGAINST.equals(
							associationship.getLegislator().getVotes().iterator().next()
								.getTypeOfVote()) &&
							associationship.getLegislator().getMemberships().iterator().next().
								getLegislature().getChamber().equals(chamber.getChamber())
					).count();

				long abstention =
					association.getAssociationships().stream().filter(associationship ->
						Vote.TypeOfVote.ABSTENTION.equals(
							associationship.getLegislator().getVotes().iterator().next()
								.getTypeOfVote()) &&
							associationship.getLegislator().getMemberships().iterator().next().
								getLegislature().getChamber().equals(chamber.getChamber())
					).count();

				associationsCounts.put(association.getName(), new VoteTable(infavor, against, abstention));
			});

			Long totalInFavor = associationsCounts.values().stream().mapToLong(
				voteTable -> voteTable.getInFavor().longValue()).sum();

			Long totalAgainst = associationsCounts.values().stream().mapToLong(
				voteTable -> voteTable.getAgainst().longValue()).sum();

			Long totalAbstention = associationsCounts.values().stream().mapToLong(
				voteTable -> voteTable.getAbstention().longValue()).sum();

			// TODO: message.properties
			associationsCounts.put(
				"Totales",
				new VoteTable(totalInFavor, totalAgainst, totalAbstention));
		}

		return associationsCounts;
	}


	//================================================================================
	// Services
	//================================================================================

	private final AssociationService associationService;
	private final LegislatorService legislatorService;
	private final BillService billService;
	private final LegislatureService legislatureService;
}
