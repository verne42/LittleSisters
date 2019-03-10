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


import com.verne42.little.sisters.domain.nodes.Association;
import com.verne42.little.sisters.domain.nodes.Legislator;
import org.neo4j.ogm.annotation.*;
import java.util.ArrayList;
import java.util.List;


/**
 * The Associated to relationship.
 */
@RelationshipEntity(type = "ASSOCIATED_TO")
public class Associationship
{
	public Associationship()
	{
	}

	public Associationship(Legislator legislator, Association association)
	{
		this.legislator = legislator;
		this.association = association;
	}


	public void setRoles(	List<String> roles)
	{
		this.roles = roles;
	}

	public void addRole(String role)
	{
		this.roles.add(role);
	}


	public Legislator getLegislator()
	{
		return legislator;
	}


	public void setLegislator(Legislator legislator)
	{
		this.legislator = legislator;
	}


	public Association getAssociation()
	{
		return association;
	}


	public void setAssociation(Association association)
	{
		this.association = association;
	}


	public List<String> getRoles()
	{
		return roles;
	}


	@StartNode
	private Legislator legislator;

	@EndNode
	private Association association;

	@Id
	@GeneratedValue
	private Long id;
	private List<String> roles = new ArrayList<>();
}
