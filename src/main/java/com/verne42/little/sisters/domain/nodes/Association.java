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


package com.verne42.little.sisters.domain.nodes;


import com.verne42.little.sisters.domain.relationships.Associationship;
import org.neo4j.ogm.annotation.GeneratedValue;
import org.neo4j.ogm.annotation.Id;
import org.neo4j.ogm.annotation.NodeEntity;
import org.neo4j.ogm.annotation.Relationship;
import java.util.ArrayList;
import java.util.List;


/**
 * The Association node.
 */
@NodeEntity
public class Association
{
	public String getName()
	{
		return name;
	}


	public String getPresident()
	{
		return president;
	}


	public String getSecretary()
	{
		return secretary;
	}


	public String getTotalMembers()
	{
		return totalMembers;
	}


	public String getFoundationDate()
	{
		return foundationDate;
	}


	public List<String> getIdeologies()
	{
		return ideologies;
	}


	public List<String> getSocialNetworks()
	{
		return socialNetworks;
	}


	public List<String> getPoliticalPostures()
	{
		return politicalPostures;
	}


	public String getAddress()
	{
		return address;
	}


	public String getImage()
	{
		return image;
	}

	public Long getId()
	{
		return id;
	}


	public void setId(Long id)
	{
		this.id = id;
	}


	public void setName(String name)
	{
		this.name = name;
	}


	public void setPresident(String president)
	{
		this.president = president;
	}


	public void setSecretary(String secretary)
	{
		this.secretary = secretary;
	}


	public void setTotalMembers(String totalMembers)
	{
		this.totalMembers = totalMembers;
	}


	public void setFoundationDate(String foundationDate)
	{
		this.foundationDate = foundationDate;
	}


	public void setIdeologies(List<String> ideologies)
	{
		this.ideologies = ideologies;
	}


	public void setSocialNetworks(List<String> socialNetworks)
	{
		this.socialNetworks = socialNetworks;
	}


	public void setPoliticalPostures(List<String> politicalPostures)
	{
		this.politicalPostures = politicalPostures;
	}


	public void addPoliticalPosture(String politicalPosture)
	{
		this.politicalPostures.add(politicalPosture);
	}

	public void setAddress(String address)
	{
		this.address = address;
	}


	public void setImage(String image)
	{
		this.image = image;
	}


	public List<Associationship> getAssociationships()
	{
		return associationships;
	}


	public void setAssociationships(List<Associationship> associationships)
	{
		this.associationships = associationships;
	}

	public void addAssociationship(Associationship associationship)
	{
		this.associationships.add(associationship);
	}


	@Relationship(type = "ASSOCIATED_TO", direction = Relationship.INCOMING)
	private List<Associationship> associationships = new ArrayList<>();

	@Id
	@GeneratedValue
	private Long id;
	private String name;
	private String president;
	private String secretary;
	private String totalMembers;
	private String foundationDate;
	private List<String> ideologies = new ArrayList<>();
	private List<String> socialNetworks = new ArrayList<>();
	private List<String> politicalPostures = new ArrayList<>();
	private String address;
	private String image;
}
