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


import com.verne42.little.sisters.domain.nodes.Legislator;
import com.verne42.little.sisters.domain.nodes.Legislature;
import org.neo4j.ogm.annotation.*;
import java.util.ArrayList;
import java.util.List;


/**
 * The Member of relationship.
 */
@RelationshipEntity(type = "MEMBER_OF")
public class Membership
{
	public Membership()
	{
	}

	public Membership(Legislator legislator, Legislature legislature)
	{
		this.legislator = legislator;
		this.legislature = legislature;
	}


	public Legislator getLegislator()
	{
		return legislator;
	}


	public void setLegislator(Legislator legislator)
	{
		this.legislator = legislator;
	}


	public Legislature getLegislature()
	{
		return legislature;
	}


	public void setLegislature(Legislature legislature)
	{
		this.legislature = legislature;
	}


	public Long getId()
	{
		return id;
	}


	public void setId(Long id)
	{
		this.id = id;
	}


	public List<String> getCommissions()
	{
		return commissions;
	}


	public void addCommission(String commission)
	{
		this.commissions.add(commission);
	}


	public void setCommissions(List<String> commissions)
	{
		this.commissions = commissions;
	}


	public String getElectedBy()
	{
		return electedBy;
	}


	public void setElectedBy(String electedBy)
	{
		this.electedBy = electedBy;
	}


	@StartNode
	private Legislator legislator;

	@EndNode
	private Legislature legislature;

	@Id
	@GeneratedValue
	private Long id;
	private List<String> commissions = new ArrayList<>();
	private String electedBy;
}
