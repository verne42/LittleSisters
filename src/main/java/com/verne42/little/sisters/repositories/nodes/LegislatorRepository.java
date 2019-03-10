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

package com.verne42.little.sisters.repositories.nodes;


import com.verne42.little.sisters.domain.nodes.Legislator;
import com.verne42.little.sisters.domain.relationships.Vote;
import org.springframework.data.neo4j.annotation.Query;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import java.util.Collection;
import java.util.List;


@RepositoryRestResource(collectionResourceRel = "legislator", path = "legislator")
public interface LegislatorRepository extends Neo4jRepository<Legislator, Long>
{
	//================================================================================
	// Find by property methods
	//================================================================================

	Collection<Legislator> findAll();
	Legislator findByLegislatorId(@Param("legislatorId") Long legislatorId);
	Legislator findByName(@Param("name") String name);
	Collection<Legislator> findByNameLike(@Param("name") String name);

	//================================================================================
	// Query Methods
	// NOTE: The query method returns the nodes and relationships you specify.
	// i.e. If you want the associateships or memberships you need to return the
	// 	relationships and nodes.
	//================================================================================

	@Query("MATCH (b:Bill)-[v:VOTED_IN]-(l:Legislator)-[m:MEMBER_OF]-(le:Legislature) " +
		"WHERE le.chamber = {legislature} " +
		"RETURN b,v,l")
	Collection<Legislator> findByLegislature(@Param("legislature") String legislature);


	@Query("MATCH (l:Legislator) " +
		"WHERE l.legislatorId IN {legislatorIdList} " +
		"RETURN l")
	Collection<Legislator> findByLegislatorIdList(@Param("legislatorIdList") List<Long> list);

	@Query("MATCH (l:Legislator) " +
		"WHERE l.name IN {legislatorNameList} " +
		"RETURN l")
	Collection<Legislator> findByLegislatorNameList(@Param("legislatorNameList") List<String> list);

	@Query("MATCH (l:Legislator)-[:ASSOCIATED_TO]-(a:Association) " +
			"WHERE a.name = {associationName} " +
			"RETURN l")
	Collection<Legislator> findByAssociationName(@Param("associationName") String affiliationName);


	@Query("MATCH (a:Association)-[at:ASSOCIATED_TO]-(l:Legislator)-[m:MEMBER_OF]-(le:Legislature) " +
		"WHERE le.country = {country} " +
		"RETURN a,at,l,m,le")
	Collection<Legislator> findByCountry(@Param("country") String affiliationName);

	/**
	 * Get the legislators and the votes for that billId.
	 *
	 * @param billId The bill id to get the legislators and their votes.
	 * @return The legislators with their votes in that bill.
	 */
	@Query("MATCH (l:Legislator)-[v:VOTED_IN]-(b:Bill) " +
		"MATCH (a:Association)-[at:ASSOCIATED_TO]-(l)-[m:MEMBER_OF]-(le:Legislature) " +
		"WHERE b.billId = {billId} AND le.chamber = {legislature} AND v.typeOfVote = {typeOfVote} " +
		"RETURN l,v,b,a,at,m,le")
	Collection<Legislator> findByVotedInBillId(
		@Param("billId") Long billId,
		@Param("legislature") String legislature,
		@Param("typeOfVote") Vote.TypeOfVote typeOfVote);

	/**
	 * Get the legislators and the votes for that billId.
	 *
	 * @param billId The bill id to get the legislators and their votes.
	 * @return The legislators with their votes in that bill.
	 */
	@Query("MATCH (l:Legislator)-[v:VOTED_IN]-(b:Bill) " +
		"MATCH (a:Association)-[at:ASSOCIATED_TO]-(l)-[m:MEMBER_OF]-(le:Legislature) " +
		"WHERE b.billId = {billId} AND le.chamber= {legislature}" +
		"RETURN l,v,b,a,at,m,le")
	Collection<Legislator> findByVotedInBillId(
		@Param("billId") Long billId,
		@Param("legislature") String legislature);

	/**
	 * Get the legislators and the votes for that billId.
	 *
	 * @param billId The bill id to get the legislators and their votes.
	 * @return The legislators with their votes in that bill.
	 */
	@Query("MATCH (l:Legislator)-[v:VOTED_IN]-(b:Bill) " +
		"MATCH (a:Association)-[at:ASSOCIATED_TO]-(l)-[m:MEMBER_OF]-(le:Legislature) " +
		"WHERE b.billId = {billId} " +
		"RETURN l,v,b,a,at,m,le")
	Collection<Legislator> findByVotedInBillId(@Param("billId") Long billId);
}
