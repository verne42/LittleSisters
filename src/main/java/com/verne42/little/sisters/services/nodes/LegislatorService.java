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


import com.verne42.little.sisters.domain.nodes.Legislator;
import com.verne42.little.sisters.domain.relationships.Vote;
import com.verne42.little.sisters.repositories.nodes.LegislatorRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.Collection;


@Service
public class LegislatorService
{
	public LegislatorService(LegislatorRepository legislatorRepository)
	{
		this.legislatorRepository = legislatorRepository;
	}

	//================================================================================
	// Transactional Methods
	//================================================================================

	@Transactional(readOnly = true)
	public Legislator findByLegislatorId(Long legislatorId)
	{
		Legislator result =
			this.legislatorRepository.findByLegislatorId(legislatorId);
		return result;
	}

	@Transactional(readOnly = true)
	public Legislator findByName(String name)
	{
		Legislator result =
			this.legislatorRepository.findByName(name);
		return result;
	}

	@Transactional(readOnly = true)
	public Collection<Legislator> findByNameLike(String name)
	{
		Collection<Legislator> result =
			this.legislatorRepository.findByNameLike("*".concat(name.concat("*")));
		return result;
	}

	@Transactional(readOnly = true)
	public Collection<Legislator> findByAssociationName(String affiliationName)
	{
		Collection<Legislator> result =
			this.legislatorRepository.findByAssociationName(affiliationName);
		return result;
	}

	@Transactional(readOnly = true)
	public Collection<Legislator> findByVotedInBillId(Long billId)
	{
		Collection<Legislator> result =
			this.legislatorRepository.findByVotedInBillId(billId);
		return result;
	}

	@Transactional(readOnly = true)
	public Collection<Legislator> findByVotedInBillId(Long billId, String legislature)
	{
		Collection<Legislator> result =
			this.legislatorRepository.findByVotedInBillId(billId, legislature);
		return result;
	}

	@Transactional(readOnly = true)
	public Collection<Legislator> findByVotedInBillId(Long billId, String legislature, Vote.TypeOfVote typeOfVote)
	{
		Collection<Legislator> result =
			this.legislatorRepository.findByVotedInBillId(billId, legislature, typeOfVote);
		return result;
	}

	@Transactional(readOnly = true)
	public Collection<Legislator> findByCountry(String country)
	{
		Collection<Legislator> result =
			this.legislatorRepository.findByCountry(country);
		return result;
	}

	@Transactional(readOnly = true)
	public Collection<Legislator> findByLegislature(String legislature)
	{
		Collection<Legislator> result =
			this.legislatorRepository.findByLegislature(legislature);
		return result;
	}

	private final LegislatorRepository legislatorRepository;
}
