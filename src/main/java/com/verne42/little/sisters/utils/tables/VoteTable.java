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

package com.verne42.little.sisters.utils.tables;


/**
 * This class wraps the votes as an object that we can use to group votes by.
 * e.g Association-VoteTable
 */
public class VoteTable
{
	public VoteTable(Long inFavor, Long against, Long abstention)
	{
		this.inFavor = inFavor;
		this.against = against;
		this.abstention = abstention;
	}


	public Long getInFavor()
	{
		return inFavor;
	}


	public void setInFavor(Long inFavor)
	{
		this.inFavor = inFavor;
	}


	public Long getAgainst()
	{
		return against;
	}


	public void setAgainst(Long against)
	{
		this.against = against;
	}


	public Long getAbstention()
	{
		return abstention;
	}


	public void setAbstention(Long abstention)
	{
		this.abstention = abstention;
	}


	protected Long inFavor;
	protected Long against;
	protected Long abstention;
}
