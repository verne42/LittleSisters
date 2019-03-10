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

package com.verne42.little.sisters.services.nodes;


import com.verne42.little.sisters.domain.nodes.Association;
import com.verne42.little.sisters.domain.relationships.Vote;
import com.verne42.little.sisters.repositories.nodes.AssociationRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;


@Service
public class AssociationService
{
	public AssociationService(AssociationRepository associationRepository)
	{
		this.associationRepository = associationRepository;
	}

	//================================================================================
	// Transactional Methods
	//================================================================================

	@Transactional
	public Collection<Association> findByVotedInBillId(Long billId)
	{
		return this.associationRepository.findByVotedInBillId(billId);
	}

	@Transactional
	public Collection<Association> findByVotedInBillId(Long billId, String chamber)
	{
		return this.associationRepository.findByVotedInBillId(billId, chamber);
	}


	protected final AssociationRepository associationRepository;
}
