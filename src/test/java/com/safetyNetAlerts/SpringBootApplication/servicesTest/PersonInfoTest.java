package com.safetyNetAlerts.SpringBootApplication.servicesTest;

import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import com.safetyNetAlerts.SpringBootApplication.services.HandlerJsonFile;
import com.safetyNetAlerts.SpringBootApplication.services.PersonInfoService;
import com.safetyNetAlerts.models.FireStation;
import com.safetyNetAlerts.models.Lieu;
import com.safetyNetAlerts.models.Person;
@SpringBootTest
@RunWith(SpringRunner.class)
class PersonInfoTest {
	@Autowired
	PersonInfoService personInfoService;
	@MockBean 
	HandlerJsonFile handlerJsonFile;
	static Lieu lieuTest;
	static HashMap<String, HashSet<FireStation>> liste;
	static FireStation fireStationTest;
	static HashMap<String, Lieu> listeOfLieuTest;
	static HashSet<FireStation> listeStationTest;
	static HashMap<String,Person> listeOfPersons;

	
	@BeforeAll
	static void setUpTest() {
		lieuTest = new Lieu("24 François", "Culver", "70000");
		fireStationTest = new FireStation("22", lieuTest);
		Lieu l1 = new Lieu("17 rue Jean", "Nantes", "14000");
		Lieu l2 = new Lieu("26 avenue Claude", "Lionnes", "27000");
		Lieu l3 = new Lieu("6 rue de Bellevues", "Paris", "76000");
		listeOfLieuTest = new HashMap<String, Lieu>();
		listeOfLieuTest.put("17 rue Jean", l1);
		listeOfLieuTest.put("26 avenue Claude", l2);
		listeOfLieuTest.put("6 rue de Bellevues", l3);
		listeOfLieuTest.put("24 François", lieuTest);
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
	void getListeOfPersonInfo() {
		when(handlerJsonFile.getListePersons()).thenReturn(listeOfPersons);
		ArrayList<Person> liste=null;
		liste= personInfoService.getListeOfPersonInfo("DUPONT", "Paul");
		assertTrue(!liste.isEmpty());
	}

}
