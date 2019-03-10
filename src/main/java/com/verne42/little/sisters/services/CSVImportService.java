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

package com.verne42.little.sisters.services;


import com.verne42.little.sisters.domain.nodes.Association;
import com.verne42.little.sisters.domain.nodes.Bill;
import com.verne42.little.sisters.domain.nodes.Legislator;
import com.verne42.little.sisters.domain.nodes.Legislature;
import com.verne42.little.sisters.domain.relationships.Associationship;
import com.verne42.little.sisters.domain.relationships.Legislated;
import com.verne42.little.sisters.domain.relationships.Membership;
import com.verne42.little.sisters.domain.relationships.Vote;
import com.verne42.little.sisters.repositories.nodes.AssociationRepository;
import com.verne42.little.sisters.repositories.nodes.BillRepository;
import com.verne42.little.sisters.repositories.nodes.LegislatorRepository;
import com.verne42.little.sisters.repositories.nodes.LegislatureRepository;
import com.verne42.little.sisters.utils.CSVImportHelper;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ResourceLoaderAware;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;


/**
 * This services handles the csv imports.
 * TODO: improve performance by use of save all.
 */
@Service
public class CSVImportService implements ResourceLoaderAware
{
	/**
	 * Spring Constructor.
	 *
	 * @param legislatorRepository
	 * @param associationRepository
	 * @param billRepository
	 * @param legislatureRepository
	 */
	public CSVImportService(LegislatorRepository legislatorRepository,
		AssociationRepository associationRepository,
		BillRepository billRepository,
		LegislatureRepository legislatureRepository)
	{
		this.legislatorRepository = legislatorRepository;
		this.associationRepository = associationRepository;
		this.billRepository = billRepository;
		this.legislatureRepository = legislatureRepository;
	}

	//================================================================================
	// Class Methods
	//================================================================================

	/**
	 * This methods imports all the csv files from {@CSVImportService#csvRootDir}
	 *
	 * @throws IOException If it fails to read the files, or the files are not found.
	 */
	public void importFromCSV() throws IOException
	{
		try (Stream<Path> paths = Files.walk(Paths.get(
			this.resourceLoader.getResource(this.csvRootDir).getFile().getAbsolutePath())))
		{
			// Add all csv files.
			List<CSVImportHelper> lstOfCSVImportHelper = new ArrayList<>();
			// Go through each country directory.
			paths.filter(Files::isRegularFile).
				forEach(path ->
				{
					if (path.getFileName().toString().equals(this.affiliationsFileName))
					{
						// get associations import helper
						lstOfCSVImportHelper.add(this.getAssociationsCSVImportHelper(path));
					}
					else if (path.getFileName().toString().equals(this.legislatorsFileName))
					{
						// Add legislature
						this.addLegislature(path);
						// Get legislator import helper.
						lstOfCSVImportHelper.add(this.getLegislatorsCSVImportHelper(path));
					}
					else if (path.getFileName().toString().equals(this.billsFileName))
					{
						// Get bills import helper.
						lstOfCSVImportHelper.add(this.getBillsCSVImportHelper(path));
					}
				});

			this.importFromCSVList(lstOfCSVImportHelper);
		}
	}


	/**
	 * This method sorts the list of {@link CSVImportHelper} with the right import order
	 * 	and then it loops through the list importing the files.
	 *
	 * @param lstOfCSVImportHelper The list of {@link CSVImportHelper}
	 * @throws IOException If it fails to read the files, or the files are not found.
	 */
	public void importFromCSVList(List<CSVImportHelper> lstOfCSVImportHelper)
		throws IOException
	{
		// Sort by import order: affiliations, legislators, bills
		lstOfCSVImportHelper.sort((CSVImportHelper o1, CSVImportHelper o2) ->
		{
			int order = 3;
			if (o1.getFile().getName().equals(this.affiliationsFileName))
			{
				order = -1;
			}
			else if (o1.getFile().getName().equals(this.billsFileName))
			{
				order = 0;
			}
			else if (o1.getFile().getName().equals(this.legislatorsFileName))
			{
				order = 1;
			}

			return order;
		});


		// Loop through the import helpers and import the files.
		Iterator<CSVImportHelper> iterCSVs = lstOfCSVImportHelper.iterator();
		while (iterCSVs.hasNext())
		{
			iterCSVs.next().importCSV();
		}
	}

	/**
	 * This method adds a legislature from the path of the legislators file.
	 * Convention: {country}/{chamber}.
	 *
	 * @param legislaturePath The legislature path.
	 */
	@Transactional
	public void addLegislature(Path legislaturePath)
	{
		Legislature legislature = new Legislature();
		// get chamber and country from the convention path {country}/{chamber}
		legislature.setChamber(legislaturePath.getParent().getFileName().toString());
		legislature.setCountry(legislaturePath.getParent().getParent().getFileName().toString());
		legislatureRepository.save(legislature);
	}


	//================================================================================
	// Factory Methods
	//================================================================================


	/**
	 * This factory method gets the CSV reader and import helper for
	 * the Associations objects.
	 *
	 * @param file The file name for the affiliations.csv.
	 * @return {@link CSVImportHelper} Implementation for {@link Association}
	 */
	public CSVImportHelper getAssociationsCSVImportHelper(Path file)
	{
		return new CSVImportHelper(file.toFile())
		{
			/**
			 * This method add all the properties for an Association record.
			 *
			 * @param properties Record properties.
			 */
			@Transactional
			@Override
			public void addRecord(String[] properties)
			{
				Association association = new Association();

				association.setName(properties[0]);
				association.setPresident(
					properties[1].equals(this.getNullString()) ? "" : properties[1]);
				association.setSecretary(
					properties[2].equals(this.getNullString()) ? "" : properties[2]);
				association.setTotalMembers(
					properties[3].equals(this.getNullString()) ? "" : properties[3]);
				association.setFoundationDate(
					properties[4].equals(this.getNullString()) ? "" : properties[4]);

				// Separate array properties
				if (!properties[5].equals(this.getNullString()))
					association.setIdeologies(
						Arrays.asList(properties[5].split(this.getListSplitBy())));

				if (!properties[6].equals(this.getNullString()))
					association.setSocialNetworks(
						Arrays.asList(properties[6].split(this.getListSplitBy())));

				if (!properties[7].equals(this.getNullString()))
					association.setPoliticalPostures(
						Arrays.asList(properties[7].split(this.getListSplitBy())));

				association.setAddress(
					properties[8].equals(this.getNullString()) ? "" : properties[9]);
				association.setImage(
					properties[9].equals(this.getNullString()) ? "" : properties[9]);

				associationRepository.save(association);
			}
		};
	}


	/**
	 * This factory method gets the CSV reader and import helper for
	 * the Legislatures objects.
	 *
	 * @param file The file name for the legislatures.csv.
	 * @return {@link CSVImportHelper} Implementation for {@link Legislature}
	 */
	public CSVImportHelper getLegislatorsCSVImportHelper(Path file)
	{
		return new CSVImportHelper(file.toFile())
		{
			/**
			 * This method add all the properties for an affiliation record.
			 *
			 * @param properties Record properties.
			 */
			@Transactional
			@Override
			public void addRecord(String[] properties)
			{
				// Relationship properties.
				String associationName;
				String electedBy;
				List<String> commissions;

				// Create legislator and set the properties.
				Legislator legislator = new Legislator();
				legislator.setLegislatorId(Long.valueOf(properties[0]));
				legislator.setName(properties[1]);
				electedBy = properties[2];
				commissions = Arrays.asList(properties[3].split(this.getListSplitBy()));
				legislator.setRepresentedEntity(properties[4]);
				associationName = properties[5];
				legislator.setSubstitute(properties[6]);
				legislator.setBorn(properties[7]);
				legislator.setEmail(properties[8]);
				legislator.setPhone(properties[9]);
				legislator.setPhoto(properties[10]);
				legislator.setCongressAbsences(properties[11]);
				legislator.setIdeologies(
					Arrays.asList(properties[12].split(this.getListSplitBy())));
				legislator.setFacebook(properties[13]);
				legislator.setTwitter(properties[14]);
				legislator.setInstagram(properties[15]);
				legislator.setLinkedin(properties[16]);

				// Add relationships
				this.addAssociation(legislator, associationName);
				this.addMembership(legislator, electedBy, commissions);
				legislatorRepository.save(legislator);
			}


			/**
			 * This method gets the affiliation from the affiliationName and sets the
			 * 	legislator relationship with the affiliation.
			 *
			 * @param legislator The legislator to affiliate.
			 * @param associationName The association name.
			 */
			public void addAssociation(Legislator legislator, String associationName)
			{
				//System.out.println(associationName);
				// Get association
				Association association =
					associationRepository.findByName(associationName);

				// Create associationship
				this.addAssociationship(legislator, association);
			}


			/**
			 * This method adds the affiliatship.
			 *
			 * @param legislator The legislator.
			 * @param association The association.
			 */
			@Transactional
			public void addAssociationship(Legislator legislator, Association association)
			{
				Associationship
					associationship = new Associationship(legislator, association);
				associationship.addRole("AFFILIATE");// TODO: other roles? President, secretery.
				legislator.addAssociationship(associationship);
				association.addAssociationship(associationship);
				associationRepository.save(association);
			}


			/**
			 * This method add the the legislator as a member of the legislature.
			 *
			 * @param legislator The member to add.
			 * @param electedBy How was he elected. Vote/assign/?
			 * @param commissions The commissions that he belong.
			 */
			public void addMembership(
				Legislator legislator,
				String electedBy,
				List<String> commissions)
			{
				// Get legislature by the chamber name in the file path.
				Legislature legislature = legislatureRepository.
					findByChamber(file.getParent().getFileName().toString());

				// Create membership of legislature.
				Membership membership = new Membership(legislator, legislature);
				membership.setElectedBy(electedBy);
				membership.setCommissions(commissions);

				legislator.addMembership(membership);
			}
		};
	}


	/**
	 * This factory method gets the CSV reader and import helper for
	 * the Bill objects.
	 *
	 * @param file The file name for the bills.csv.
	 * @return {@link CSVImportHelper} Implementation for {@link Bill}
	 */
	public CSVImportHelper getBillsCSVImportHelper(Path file)
	{
		return new CSVImportHelper(file.toFile())
		{
			/**
			 * This method add all the properties for an bill record.
			 *
			 * @param properties Record properties.
			 */
			@Transactional
			@Override
			public void addRecord(String[] properties)
			{
				String status;
				Bill bill;
				Long billID;
				String pdfURL = properties[1];
				String topics = properties[2];
				String voted = properties[3];

				// Check if billId is a valid Id, if not copy the node Id as billId.
				if (properties[0].isEmpty() || properties[0].equals(this.getNullString()))
				{
					bill = new Bill();
					// If there is no billId between chambers we generate one.
					bill.setBillId(getNextID());
					bill.setText(
						this.getChamber() + ":" + System.lineSeparator() + properties[4]);
				}
				else
				{
					billID = Long.valueOf(properties[0]);
					// we search if the bill already exist, has been legislated in other chamber
					bill = billRepository.findByBillId(billID);

					if (bill == null)
					{
						bill = new Bill();
						bill.setText(System.lineSeparator() + this.getChamber() + ":" +
							System.lineSeparator() + properties[4]);
					}
					else
					{
						// merged Text from previous chambers.
						bill.setText(bill.getText() + System.lineSeparator() +
							this.getChamber() + ":" + System.lineSeparator() +
							properties[4]);
					}

					bill.setBillId(billID);
				}

				bill.setPdfURL(pdfURL);
				bill.setTopics(Arrays.asList(topics.split(this.getListSplitBy())));
				status = properties[5];
				bill.addAgenda(status);

				// Add legislated in
				Legislature legislature = legislatureRepository.findByChamber(this.getChamber());
				Legislated legislated = new Legislated(bill, legislature);
				legislated.addStatus(status);
				legislated.addVoted(voted);
				bill.addLegislated(legislated);

				// Save bill
				billRepository.save(bill);

				// Votes
				this.addVotes(bill, properties[6], Vote.TypeOfVote.IN_FAVOR);
				this.addVotes(bill, properties[7], Vote.TypeOfVote.AGAINST);
				this.addVotes(bill, properties[8], Vote.TypeOfVote.ABSTENTION);
				// TODO: add absences. type of vote abcesnec, increase absence counter
				//this.addVotes(bill, properties[7], Vote.TypeOfVote.ABSENCE);
			}


			/**
			 * This method add the list of votes of typeOfVote to the bill.
			 *
			 * @param bill The bill to add the votes.
			 * @param votesString The votes in string format separated by {@CSVImportHelper#listSplitBy}
			 * @param typeOfVote The type of vote in the list of votes.
			 */
			@Transactional
			public void addVotes(Bill bill, String votesString, Vote.TypeOfVote typeOfVote)
			{
				// Check if the votesString is not null or list of only null.
				if (votesString != null &&
					!votesString.replaceAll(this.getListSplitBy(), "").
						equals(this.getNullString()))
				{
					Collection<Legislator> legislators;
					// Split votes string into list of legislatorIds.
					String[] votesLst = votesString.split(this.getListSplitBy());

					try
					{
						// Try to get by legislatorId first
						List<Long> votes = Arrays.asList(votesLst).
							stream().map(voteString ->
							Long.parseLong(voteString.trim())).collect(Collectors.toList());

						// Get the legislators by Id.
						legislators = legislatorRepository.findByLegislatorIdList(votes);
					}
					catch (Exception e)
					{
						// Get the legislators by name then.
						LoggerFactory.getLogger(this.getClass()).warn(
							"Importing legislators vote by name will take exponentially more than by ID." +
						System.lineSeparator() + "Consider importing only once or changing to ID" +
						System.lineSeparator() + "Recommended use only if there is no way to get votes by ID");
						legislators = this.getLegislatorsVotedByName(votesLst);
					}

					// Create vote and add it to the legislator.
					legislators.forEach(legislator ->
					{
						Vote vote = new Vote(bill, legislator);
						vote.setTypeOfVote(typeOfVote);
						legislator.addVote(vote);
					});

					legislatorRepository.saveAll(legislators);
				}

			}


			/**
			 * This method gets the list of legislators names that voted.
			 *
			 * @param votesLst List of legislators names.
			 * @return List of legislators.
			 */
			public Collection<Legislator> getLegislatorsVotedByName(String[] votesLst)
			{
				Collection<Legislator> legislatorsFilter = new ArrayList<>();
				Collection<Legislator> allLegislators = legislatorRepository.findAll();

				// For each name we filter all the legislators to match the legislator
				// who's name split by spaces is equal.
				Arrays.asList(votesLst).forEach(name ->
				{
					List<String> nameLst = Arrays.asList(name.toUpperCase().split(" "));
					// Two names are equals if the names split by space are equals.
					legislatorsFilter.addAll(allLegislators.stream().filter(legislator ->
					{
						List<String> legislatorNameLst = Arrays.asList(
							legislator.getName().toUpperCase().split(" "));

						// Check also if the substitute voted for him.
						List<String> substituteNameLst = Arrays.asList(
							legislator.getSubstitute().toUpperCase().split(" "));

						// Double contention in different list with names.
						return ((legislatorNameLst.containsAll(nameLst) &&
							nameLst.containsAll(legislatorNameLst)) ||
							(substituteNameLst.containsAll(nameLst) &&
								nameLst.containsAll(substituteNameLst)));
					}).collect(Collectors.toList()));

					if (legislatorsFilter.isEmpty())
						System.out.println(name);

				});

				return legislatorsFilter;
			}
		};
	}

	//================================================================================
	// Extended Methods
	//================================================================================


	/**
	 * Set the resource loader.
	 *
	 * @param resourceLoader
	 */
	@Override
	public void setResourceLoader(ResourceLoader resourceLoader)
	{
		this.resourceLoader = resourceLoader;
	}

	//================================================================================
	// Methods
	//================================================================================


	/**
	 * This method generates a new id value.
	 *
	 * @return The ID value.
	 */
	public Long getNextID()
	{
		this.idGenerator += 1;

		return this.idGenerator;
	}


	public String getCsvRootDir()
	{
		return csvRootDir;
	}


	public void setCsvRootDir(String csvRootDir)
	{
		this.csvRootDir = csvRootDir;
	}

	//================================================================================
	// Repositories
	//================================================================================

	private final LegislatorRepository legislatorRepository;
	private final AssociationRepository associationRepository;
	private final BillRepository billRepository;
	private final LegislatureRepository legislatureRepository;



	/**
	 * The resource loader.
	 */
	private ResourceLoader resourceLoader;

	protected Long idGenerator = Long.valueOf(70000000);

	@Value("${oley.csv.root.dir:file:#{systemProperties['user.dir']}/data/}")
	protected String csvRootDir;

	@Value("${oley.affiliations.file.name:associations.csv}")
	protected String affiliationsFileName;

	@Value("${oley.legislators.file.name:legislators.csv}")
	protected String legislatorsFileName;

	@Value("${oley.bills.file.name:bills.csv}")
	protected String billsFileName;
}
