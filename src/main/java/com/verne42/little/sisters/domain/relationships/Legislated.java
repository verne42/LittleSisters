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


import com.verne42.little.sisters.domain.nodes.Legislature;
import com.verne42.little.sisters.domain.nodes.Bill;
import org.neo4j.ogm.annotation.*;
import java.util.ArrayList;
import java.util.List;


@RelationshipEntity(type = "LEGISLATED_IN")
public class Legislated
{
	public Legislated()
	{

	}


	public Legislated(Bill bill, Legislature legislature)
	{
		this.bill = bill;
		this.legislature = legislature;
	}


	public Bill getBill()
	{
		return bill;
	}


	public void setBill(Bill bill)
	{
		this.bill = bill;
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


	public List<String> getStatuses()
	{
		return statuses;
	}


	public void setStatuses(List<String> statuses)
	{
		this.statuses = statuses;
	}

	public void addStatus(String status)
	{
		this.statuses.add(status);
	}


	public List<String> getVoted()
	{
		return voted;
	}


	public void setVoted(List<String> voted)
	{
		this.voted = voted;
	}

	public void addVoted(String voted)
	{
		this.voted.add(voted);
	}

	@StartNode
	private Bill bill;

	@EndNode
	private Legislature legislature;

	@Id
	@GeneratedValue
	private Long id;
	private List<String> statuses = new ArrayList<>();
	private List<String> voted = new ArrayList<>();
}
