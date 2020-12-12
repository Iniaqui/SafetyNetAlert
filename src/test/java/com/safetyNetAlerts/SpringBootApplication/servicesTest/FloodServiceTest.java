package com.safetyNetAlerts.SpringBootApplication.servicesTest;

import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.when;

import java.util.HashMap;
import java.util.HashSet;

import org.json.simple.JSONArray;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import com.safetyNetAlerts.SpringBootApplication.services.FloodService;
import com.safetyNetAlerts.SpringBootApplication.services.HandlerJsonFile;
import com.safetyNetAlerts.models.FireStation;
import com.safetyNetAlerts.models.Lieu;
import com.safetyNetAlerts.models.Person;
@RunWith(MockitoJUnitRunner.class)
@SpringBootTest
class FloodServiceTest {
	@Autowired
	FloodService floodService;
	@MockBean
	HandlerJsonFile handlerJsonFile;
	
	static HashMap<String, HashSet<FireStation>> liste;
	static HashMap<String, Person> listeOfPersons;
	static  HashMap<String ,Lieu> listeOfLieux ;
	static HashSet<FireStation> listeStationTest;
	static HashMap<String, Lieu> listeOfLieuTest;
	@BeforeAll
	static void  setUpTest() {
		Lieu lieuTest = new Lieu("24 François", "Culver", "70000");
		Lieu lieuTest2 = new Lieu("20 François", "Culver", "70000");
		listeOfLieux = new HashMap<String, Lieu>();
		listeOfLieux.put("24 François", lieuTest);
		listeOfLieux.put("20 François", lieuTest2);
		
		listeStationTest = new HashSet<FireStation>();
		listeStationTest.add(new FireStation("23", lieuTest));
		listeStationTest.add(new FireStation("22", lieuTest));
		
		liste = new HashMap<String, HashSet<FireStation>>();
		liste.put("24 François", listeStationTest);
		
		listeOfPersons= new HashMap<String, Person>();
		Person person1 = new Person("DUPONT", "Paul", "066-546-258", "pauldupont@gmail.com", lieuTest);
		person1.setBirthday("03/06/1984");
		Person person2 = new Person("DUBOIS", "Laurine", "255-668-547", "laurinedubois@gmail.com", lieuTest);
		person2.setBirthday("06/05/2006");
		Person person3 = new Person("DUCON", "Louis", "865-874-963", "louisducon@gmail.com", lieuTest);
		person3.setBirthday("08/07/2001");
		listeOfPersons.put("DUPONT", person1);
		listeOfPersons.put("DUBOIS", person2);
		listeOfPersons.put("DUCON", person3);
	}
	@Test
	void test() {
		HashMap<String,JSONArray> value = null;
	when(handlerJsonFile.getListeFireStations()).thenReturn(liste);
	when(handlerJsonFile.getListePersons()).thenReturn(listeOfPersons);
	value = floodService.getListeOfPersonCoverByStationAndMedicalRecord("22");
	assertNotNull(value);
	
	}

}
