package com.verne42.little.sisters.repositories;

import com.verne42.little.sisters.domain.nodes.Association;
import com.verne42.little.sisters.domain.nodes.Bill;
import com.verne42.little.sisters.domain.nodes.Legislator;
import com.verne42.little.sisters.domain.nodes.Legislature;
import com.verne42.little.sisters.domain.relationships.Associationship;
import com.verne42.little.sisters.domain.relationships.Legislated;
import com.verne42.little.sisters.domain.relationships.Membership;
import com.verne42.little.sisters.domain.relationships.Vote;
import com.verne42.little.sisters.repositories.nodes.AssociationRepository;
import com.verne42.little.sisters.repositories.nodes.BillRepository;
import com.verne42.little.sisters.repositories.nodes.LegislatorRepository;
import com.verne42.little.sisters.repositories.nodes.LegislatureRepository;

import static org.junit.Assert.*;

import java.util.Arrays;
import java.util.Collection;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;


@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Transactional
public class LegislatureRepositoryTest
{
	/*
		*************  Test Methods **********************
	 */

	/**
	 * Test of findByName method, of class LegislatorRepository.
	 */
	@Test
	public void testFindByName()
	{
		String name = "Ifigenia Martínez Hernández";
		Legislator result = legislatorRepository.findByName(name);

		assertNotNull(result);
		assertEquals("ifigenia.martinez@senado.gob.mx", result.getEmail());
	}

	/**
	 * Test of findByNameContaining method, of class LegislatorRepository.
	 */
	@Test
	public void testFindByNameContaining()
	{
		String name = "*Ifigenia*";
		Collection<Legislator> result = legislatorRepository.findByNameLike(name);
		assertNotNull(result);
		assertEquals(1, result.size());
	}


	/**
	 * Test AFFILIATE_TO relationship.
	 */
	@Test
	public void testAffiliatship()
	{
		String affiliationName = "Movimiento Regeneración Nacional";

		Collection<Legislator> result = legislatorRepository.findByAssociationName(affiliationName);

		assertEquals("Ifigenia Martínez Hernández", result.iterator().next().getName());
	}


	/*
		*************  Setup Methods **********************
	 */

	@Before
	public void setUp()
	{
		Legislator legislator = this.addLegislator();
		Association association = this.addAffiliation();
		Bill bill = this.addBill();
		Legislature legislature = this.addLegislature();

		// Add legislator relationships.

		// Affiliationship
		Associationship associationship = new Associationship(legislator, association);
		associationship.addRole("AFFILIATE");// FOUNDER?
		legislator.addAssociationship(associationship);

		// Add Vote
		Vote vote = new Vote(bill, legislator);
		vote.setTypeOfVote(Vote.TypeOfVote.IN_FAVOR);
		legislator.addVote(vote);

		// Add Membership
		Membership membership = new Membership(legislator, legislature);
		membership.setElectedBy("Senadora Electa por Representación  Proporcional Basado en el Capítulo II; Sección I Artículo 56");
		membership.addCommission("Secretaria De La Comisión de Reglamentos y Prácticas Parlamentarias");
		membership.addCommission("Integrante De La Comisión de Economía");
		membership.addCommission("Integrante De La Comisión de Federalismo y Desarrollo Municipal");
		membership.addCommission("Integrante De La Comisión de Hacienda y Crédito Público");
		membership.addCommission("Integrante De La Comisión de Relaciones Exteriores");
		legislator.addMembership(membership);

		legislatorRepository.save(legislator);

		// Add legislated in
		Legislated legislated = new Legislated(bill, legislature);
		legislated.addStatus("Votado en el Senado");
		legislated.addVoted("Jueves 15 de noviembre de 2018");
		bill.addLegislated(legislated);
		this.billRepository.save(bill);
	}


	public Legislator addLegislator()
	{
		Legislator legislator;

		// Legislator
		legislator = new Legislator();
		legislator.setLegislatorId(Long.valueOf(1279));
		legislator.setName("Ifigenia Martínez Hernández");
		legislator.setRepresentedEntity("Lista Nacional");
		legislator.setSubstitute("Bertha Elena Lujan Uranga");
		legislator.setEmail("ifigenia.martinez@senado.gob.mx");
		legislator.setPhone("01 (55) 5345 3000  Ext. 3196; 3914; 5560");
		legislator.setPhoto("1279-martinez_y_hernandez_ifigenia_martha-20180825-112056.jpg");

		this.legislatorRepository.save(legislator);
		return legislator;
	}


	public Association addAffiliation()
	{
		Association association;

		// Association
		association = new Association();
		association.setName("Movimiento Regeneración Nacional");
		association.setPresident("Yeidckol Polevnsky");
		association.setSecretary("Yeidckol Polevnsky");
		association.setTotalMembers("319449");
		association.setFoundationDate("2 de octubre de 2011");
		association.setIdeologies(Arrays.asList("socialdemocracia","reformismo y nacionalismo de izquierda"));
		association.setSocialNetworks(Arrays.asList("https://morena.si"));
		association.setPoliticalPostures(Arrays.asList("Izquierda"));
		association.setAddress("Santa Anita #50; Col. Viaducto Piedad; Delegación Iztacalco; C.P. 08200; Ciudad de México");

		this.associationRepository.save(association);
		return association;
	}


	public Legislature addLegislature()
	{
		// Add legislature
		Legislature legislature = new Legislature();
		legislature.setChamber("Senado");
		legislature.setCountry("Mexico");
		//legislature.setNumberOfSeats(128);
		this.legislatureRepository.save(legislature);

		return legislature;
	}

	public Bill addBill()
	{
		Bill bill;

		// Iniciativa
		bill = new Bill();
		bill.setBillId(Long.valueOf(3217));
		bill.addAgenda("Votado en el Senado");
		bill.setText("Dictámenes de la Comisión de Marina:\n" +
			"1. El que contiene puntos de acuerdo por los que se ratifican 7 grados de Almirantes.\n" +
			"2. El que contiene puntos de acuerdo por los que se ratifican 23 grados de Vicealmirantes.\n" +
			"3. El que contiene puntos de acuerdo por los que se ratifican 66 grados de Contralmirantes.\n" +
			"4. El que contiene puntos de acuerdo por los que se ratifican 187 grados de Capitán de Navío.");


		this.billRepository.save(bill);
		return bill;
	}

	/*
		Repositories.
	 */
	@Autowired
	private LegislatorRepository legislatorRepository;

	@Autowired
	private AssociationRepository associationRepository;

	@Autowired
	private BillRepository billRepository;

	@Autowired
	private LegislatureRepository legislatureRepository;
}