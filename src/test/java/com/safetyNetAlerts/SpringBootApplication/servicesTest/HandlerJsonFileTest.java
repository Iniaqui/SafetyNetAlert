package com.safetyNetAlerts.SpringBootApplication.servicesTest;

import static org.junit.jupiter.api.Assertions.*;

import java.util.HashMap;
import java.util.HashSet;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import com.safetyNetAlerts.SpringBootApplication.services.HandlerJsonFile;
import com.safetyNetAlerts.models.FireStation;
import com.safetyNetAlerts.models.Lieu;
import com.safetyNetAlerts.models.MedicalRecord;
import com.safetyNetAlerts.models.Person;

class HandlerJsonFileTest {

	static HandlerJsonFile handlerJsonFileTest;
	static Person personTest;
	static Lieu l;
	@BeforeAll
	public static void  setUp() {
		 l = new Lieu("24 François","Culver","70000");
		 handlerJsonFileTest = new HandlerJsonFile();
		personTest= new Person("DUPONT","Paul","888-667-555","24 François",l);
		 
	}
	@Test
	void getListeFireStationtest() {
		HashMap<String,HashSet<FireStation>> listeFireStation = handlerJsonFileTest.getListeFireStations();
		assertNotNull(listeFireStation);
	}
	@Test
	void getListePerson() {
		HashMap<String,Person> listePerson = handlerJsonFileTest.getListePersons();
		assertNotNull(listePerson);
	}
	@Test
	void getListelieuTest() {
		HashMap<String,Lieu> listeLieu = handlerJsonFileTest.getListeOfLieu();
		assertNotNull(listeLieu);
	}
	@Test
	void getListeMedicaleTest() {
		HashMap<String,MedicalRecord> liste = handlerJsonFileTest.getListeMedicalRecords();
		assertNotNull(liste);
	}
	@Test
	void ajoutPersonTest() {
		boolean response = handlerJsonFileTest.ajoutPerson("DUPONT", personTest);
		assertTrue(response);
	}
	@Test
	void supprPersonTest() {
		boolean response = handlerJsonFileTest.ajoutPerson("DUPONT", personTest);
		handlerJsonFileTest.supprPerson("DUPONT", "Paul");
		assertNull(handlerJsonFileTest.searchPerson("DUPONT"));
	}
	@Test
	void initDataTest() {
		
	}

}
