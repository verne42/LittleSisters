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


import com.verne42.little.sisters.domain.relationships.Membership;
import com.verne42.little.sisters.domain.relationships.Vote;
import com.verne42.little.sisters.domain.relationships.Associationship;
import org.neo4j.ogm.annotation.GeneratedValue;
import org.neo4j.ogm.annotation.Id;
import org.neo4j.ogm.annotation.NodeEntity;
import org.neo4j.ogm.annotation.Relationship;
import java.util.ArrayList;
import java.util.List;

/**
 * The Legislator node.
 */
@NodeEntity
public class Legislator
{
	public Long getId()
	{
		return id;
	}


	public String getName()
	{
		return name;
	}


	public String getRepresentedEntity()
	{
		return representedEntity;
	}


	public String getSubstitute()
	{
		return substitute;
	}


	public String getBorn()
	{
		return born;
	}


	public String getEmail()
	{
		return email;
	}


	public String getPhone()
	{
		return phone;
	}


	public String getPhoto()
	{
		return photo;
	}


	public String getCongressAbsences()
	{
		return congressAbsences;
	}


	public void setName(String name)
	{
		this.name = name;
	}


	public void setRepresentedEntity(String representedEntity)
	{
		this.representedEntity = representedEntity;
	}


	public void setSubstitute(String substitute)
	{
		this.substitute = substitute;
	}


	public void setBorn(String born)
	{
		this.born = born;
	}


	public void setEmail(String email)
	{
		this.email = email;
	}


	public void setPhone(String phone)
	{
		this.phone = phone;
	}


	public void setPhoto(String photo)
	{
		this.photo = photo;
	}


	public void setCongressAbsences(String congressAbsences)
	{
		this.congressAbsences = congressAbsences;
	}


	public Long getLegislatorId()
	{
		return legislatorId;
	}


	public void setLegislatorId(Long legislatorId)
	{
		this.legislatorId = legislatorId;
	}


	public void setId(Long id)
	{
		this.id = id;
	}



	public List<Vote> getVotes()
	{
		return votes;
	}


	public void setVotes(List<Vote> votes)
	{
		this.votes = votes;
	}

	public void addVote(Vote vote)
	{
		this.votes.add(vote);
	}


	public List<Membership> getMemberships()
	{
		return memberships;
	}


	public void setMemberships(List<Membership> memberships)
	{
		this.memberships = memberships;
	}

	public void addMembership(Membership membership)
	{
		this.memberships.add(membership);
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


	public String getFacebook()
	{
		return facebook;
	}


	public void setFacebook(String facebook)
	{
		this.facebook = facebook;
	}


	public String getTwitter()
	{
		return twitter;
	}


	public void setTwitter(String twitter)
	{
		this.twitter = twitter;
	}


	public String getInstagram()
	{
		return instagram;
	}


	public void setInstagram(String instagram)
	{
		this.instagram = instagram;
	}


	public String getLinkedin()
	{
		return linkedin;
	}


	public void setLinkedin(String linkedin)
	{
		this.linkedin = linkedin;
	}


	public List<String> getIdeologies()
	{
		return ideologies;
	}

	public void setIdeologies(List<String> ideologies)
	{
		this.ideologies = ideologies;
	}

	public void addIdeology(String ideology)
	{
		this.ideologies.add(ideology);
	}

	@Relationship(type = "VOTED_IN")
	private List<Vote> votes = new ArrayList<>();

	@Relationship(type = "MEMBER_OF")
	private List<Membership> memberships = new ArrayList<>();

	@Relationship(type = "ASSOCIATED_TO")
	private List<Associationship> associationships = new ArrayList<>();

	@Id
	@GeneratedValue
	private Long id;
	private Long legislatorId;
	private String name;
	private String representedEntity;
	private String substitute;
	private String born;
	private String email;
	private String phone;
	private String photo;
	private String congressAbsences;
	private List<String> ideologies = new ArrayList<>();;
	private String facebook;
	private String twitter;
	private String instagram;
	private String linkedin;
}
