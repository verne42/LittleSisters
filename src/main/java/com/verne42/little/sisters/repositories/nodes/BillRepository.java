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


import com.verne42.little.sisters.domain.nodes.Bill;
import org.springframework.data.neo4j.annotation.Query;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import java.util.Collection;


@RepositoryRestResource(collectionResourceRel = "bill", path = "bill")
public interface BillRepository extends Neo4jRepository<Bill, Long>
{

	//================================================================================
	// Find by property methods
	//================================================================================

	Bill findByBillId(@Param("billId") Long billId);

	//================================================================================
	// Query Methods
	//================================================================================

	@Query("MATCH (b:Bill) " +
		"WHERE b.text CONTAINS {billText} " +
		"RETURN b")
	Collection<Bill> findByTextLike(@Param("billText") String billText);

	@Query("MATCH (b:Bill)-[:LEGISLATED_IN]->(bl:Legislature) " +
		"WHERE bl.country = {country} " +
		"RETURN b")
	Collection<Bill> findByCountry(@Param("country") String country);

	@Query("MATCH (l:Legislator)-[v:VOTED_IN]-(b:Bill)-[li:LEGISLATED_IN]->(le:Legislature) " +
		"WHERE le.chamber = {legislature} " +
		"RETURN l,v,b")
	Collection<Bill> findByLegislature(@Param("legislature") String legislature);
}
