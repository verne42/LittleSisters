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


import com.verne42.little.sisters.domain.nodes.Association;
import org.springframework.data.neo4j.annotation.Query;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import java.util.Collection;


@RepositoryRestResource(collectionResourceRel = "association", path = "association")
public interface AssociationRepository extends Neo4jRepository<Association, Long>
{
	//================================================================================
	// Find by property methods
	//================================================================================

	Association findByName(@Param("name") String name);

	Collection<Association> findByNameLike(@Param("name") String name);

	//================================================================================
	// Query Methods
	//================================================================================


	/**
	 * This method gets all the associations who associates voted in billId.
	 *
	 * @param billId The bill ID.
	 * @return The associations, it legislator associates and the votes.
	 */
	@Query("MATCH (a:Association)-[aa:ASSOCIATED_TO]-(l:Legislator)-[v:VOTED_IN]-(b:Bill) " +
		"WHERE b.billId = {billId} " +
		"RETURN a,aa,l,v")
	Collection<Association> findByVotedInBillId(@Param("billId") Long billId);


	/**
	 * This method gets all the associations and its relationships who assocaiates voted
	 * 	in billId and belong to chamber.
	 *
	 * @param billId The billId
	 * @param chamber The legislature chamber.
	 * @return The associations, it legislator associates and the votes.
	 */
	@Query("MATCH (a:Association)-[aa:ASSOCIATED_TO]-(l:Legislator)-[v:VOTED_IN]-(b:Bill)-[li:LEGISLATED_IN]-(le:Legislature) " +
		"MATCH (l)-[m:MEMBER_OF]-(le) " +
		"WHERE b.billId = {billId} AND le.chamber = {chamber} " +
		"RETURN a,aa,l,v,b,li,le,m")
	Collection<Association> findByVotedInBillId(
		@Param("billId") Long billId,
		@Param("chamber") String chamber);
}
