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


import com.verne42.little.sisters.domain.relationships.Legislated;
import org.neo4j.ogm.annotation.GeneratedValue;
import org.neo4j.ogm.annotation.Id;
import org.neo4j.ogm.annotation.NodeEntity;
import org.neo4j.ogm.annotation.Relationship;
import java.util.ArrayList;
import java.util.List;


@NodeEntity
public class Bill
{

	public Long getId()
	{
		return id;
	}


	public String getText()
	{
		return text;
	}


	public Long getBillId()
	{
		return billId;
	}


	public void setBillId(Long billId)
	{
		this.billId = billId;
	}


	public void setText(String text)
	{
		this.text = text;
	}


	public void setId(Long id)
	{
		this.id = id;
	}


	public List<Legislated> getLegislatiedIn()
	{
		return legislatiedIn;
	}


	public void setLegislatiedIn(List<Legislated> legislatiedIn)
	{
		this.legislatiedIn = legislatiedIn;
	}


	public void addLegislated(Legislated legislated)
	{
		this.legislatiedIn.add(legislated);
	}


	public List<String> getAgenda()
	{
		return agenda;
	}


	public void setAgenda(List<String> agenda)
	{
		this.agenda = agenda;
	}


	public void addAgenda(String agenda)
	{
		this.agenda.add(agenda);
	}


	public String getPdfURL()
	{
		return pdfURL;
	}


	public void setPdfURL(String pdfURL)
	{
		this.pdfURL = pdfURL;
	}


	public List<String> getTopics()
	{
		return topics;
	}


	public void setTopics(List<String> topics)
	{
		this.topics = topics;
	}

	public void addTopic(String topic)
	{
		this.topics.add(topic);
	}

	/**
	 * Couldn't resist to have a "killBill" method.
	 * @param bill
	 */
	private void killBill(Bill bill)
	{
		// kiddo Do Five Point Palm Exploding Heart Technique (bill)
	}


	@Relationship(type = "LEGISLATED_IN")
	private List<Legislated> legislatiedIn = new ArrayList<>();


	@Id
	@GeneratedValue
	private Long id;
	private Long billId;
	private String text;
	private String pdfURL;
	private List<String> topics = new ArrayList<>();
	private List<String> agenda = new ArrayList<>();
}
