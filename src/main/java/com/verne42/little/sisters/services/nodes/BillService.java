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


import com.verne42.little.sisters.domain.nodes.Bill;
import com.verne42.little.sisters.repositories.nodes.BillRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.Collection;


@Service
public class BillService
{
	public BillService(BillRepository billRepository)
	{
		this.billRepository = billRepository;
	}

	//================================================================================
	// Transactional Methods
	//================================================================================

	@Transactional(readOnly = true)
	public Bill findByBillId(Long billId)
	{
		Bill result = this.billRepository.findByBillId(billId);

		return result;
	}

	@Transactional(readOnly = true)
	public Collection<Bill> findByTextLike(String billText)
	{
		Collection<Bill> result = this.billRepository.findByTextLike(billText);

		return result;
	}


	@Transactional(readOnly = true)
	public Collection<Bill> findByCountry(String country)
	{
		Collection<Bill> result = this.billRepository.findByCountry(country);

		return result;
	}


	@Transactional(readOnly = true)
	public Collection<Bill> findByLegislature(String legislature)
	{
		Collection<Bill> result = this.billRepository.findByLegislature(legislature);

		return result;
	}


	private final BillRepository billRepository;
}
