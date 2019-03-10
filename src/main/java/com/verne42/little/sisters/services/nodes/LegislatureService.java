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


import com.verne42.little.sisters.domain.nodes.Legislature;
import com.verne42.little.sisters.repositories.nodes.LegislatureRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.Collection;


@Service
public class LegislatureService
{
	public LegislatureService(LegislatureRepository legislatureRepository)
	{
		this.legislatureRepository = legislatureRepository;
	}

	//================================================================================
	// Transactional Methods
	//================================================================================

	@Transactional(readOnly = true)
	public Collection<Legislature> findAll()
	{
		Collection<Legislature> result = this.legislatureRepository.findAll();

		return result;
	}

	@Transactional(readOnly = true)
	public Collection<Legislature> findByLegislatedIn(Long billId)
	{
		Collection<Legislature> result = this.legislatureRepository.findByLegislatedIn(billId);

		return result;
	}

	@Transactional(readOnly = true)
	public Collection<Legislature> findByLegislatedIn(Long billId, String legislature)
	{
		Collection<Legislature> result = this.legislatureRepository.findByLegislatedIn(billId);

		return result;
	}

	//================================================================================
	// Repositories
	//================================================================================

	private final LegislatureRepository legislatureRepository;
}
