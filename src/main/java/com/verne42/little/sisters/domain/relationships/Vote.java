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

package com.verne42.little.sisters.domain.relationships;

import com.verne42.little.sisters.domain.nodes.Bill;
import com.verne42.little.sisters.domain.nodes.Legislator;
import java.util.ArrayList;
import java.util.List;

import org.neo4j.ogm.annotation.*;

/**
 * The Voted relationship.
 */
@RelationshipEntity(type = "VOTED_IN")
public class Vote
{
	public Vote()
	{
	}

	public Vote(Bill bill, Legislator legislator)
	{
		this.bill = bill;
		this.legislator = legislator;
	}


	public TypeOfVote getTypeOfVote()
	{
		return typeOfVote;
	}


	public void setTypeOfVote(TypeOfVote typeOfVote)
	{
		this.typeOfVote = typeOfVote;
	}


	public Long getId()
	{
		return id;
	}


	public List<String> getNotes()
	{
		return notes;
	}


	public Legislator getLegislator()
	{
		return legislator;
	}


	public Bill getBill()
	{
		return bill;
	}


	public void setLegislator(Legislator legislator)
	{
		this.legislator = legislator;
	}


	public void setBill(Bill bill)
	{
		this.bill = bill;
	}


	public void setId(Long id)
	{
		this.id = id;
	}


	public void setNotes(List<String> notes)
	{
		this.notes = notes;
	}

	public void addNote(String note)
	{
		this.notes.add(note);
	}


	public enum TypeOfVote
	{
		IN_FAVOR,
		AGAINST,
		ABSTENTION,
		ABSENCE
	}

	@StartNode
	private Legislator legislator;

	@EndNode
	private Bill bill;

	@Id
	@GeneratedValue
	private Long id;
	private List<String> notes = new ArrayList<>();
	private TypeOfVote typeOfVote;
}