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


import com.verne42.little.sisters.domain.nodes.Legislature;
import org.springframework.data.neo4j.annotation.Query;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import java.util.Collection;
import java.util.List;


@RepositoryRestResource(collectionResourceRel = "legislature", path = "legislature")
public interface LegislatureRepository extends Neo4jRepository<Legislature, Long>
{
	//================================================================================
	// Find by property methods
	//================================================================================

	Collection<Legislature> findAll();
	Legislature findByChamber(@Param("chamber") String chamber);

	//================================================================================
	// Query Methods
	//================================================================================

	@Query("MATCH (l:Legislature)-[:LEGISLATED_IN]-(b:Bill) " +
		"WHERE b.billId = {billId} " +
		"RETURN l")
	Collection<Legislature> findByLegislatedIn(@Param("billId") Long billId);
}
