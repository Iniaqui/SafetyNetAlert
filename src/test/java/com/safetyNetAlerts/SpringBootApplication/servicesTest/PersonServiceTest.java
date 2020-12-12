package com.safetyNetAlerts.SpringBootApplication.servicesTest;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.when;

import java.util.HashMap;
import java.util.HashSet;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import com.safetyNetAlerts.SpringBootApplication.services.HandlerJsonFile;
import com.safetyNetAlerts.SpringBootApplication.services.PersonService;
import com.safetyNetAlerts.models.FireStation;
import com.safetyNetAlerts.models.Lieu;
import com.safetyNetAlerts.models.Person;
import com.safetyNetAlerts.models.PersonDetailRequestModel;
@RunWith(MockitoJUnitRunner.class)
@SpringBootTest
class PersonServiceTest {
	@Autowired
	PersonService personService;
	@MockBean
	HandlerJsonFile handlerJsonFile;
	static Lieu lieuTest;
	static HashMap<String, HashSet<FireStation>> liste;
	static FireStation fireStationTest;
	static HashMap<String, Lieu> listeOfLieuTest;
	static HashSet<FireStation> listeStationTest;
	static HashMap<String,Person> listeOfPersons;
	static Lieu l1;
	@BeforeAll
	static void setUpTest() {

		lieuTest = new Lieu("24 François", "Culver", "70000");
		fireStationTest = new FireStation("22", lieuTest);
		 l1 = new Lieu("17 rue Jean", "Nantes", "14000");
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
	void ajoutPersonnetest() {
		Person p = new Person("NAME", "FirstName", "066-546-258", "namefirstname@gmail.com", lieuTest);
		when(handlerJsonFile.getListePersons()).thenReturn(listeOfPersons);
		when(handlerJsonFile.getListeOfLieu()).thenReturn(listeOfLieuTest);
		when(handlerJsonFile.ajoutPerson(p.getFirstName(), p)).thenReturn(true);
		Person p1 = personService.ajoutPersonne(p);
		assertNotNull(p1);
	}
	@Test
	void deletePersonTest() {
		when(handlerJsonFile.getListePersons()).thenReturn(listeOfPersons);
		String r = personService.deletePerson("DUPONT", "Paul");
		assertEquals("Supression effective",r);
	}
	@Test
	void modifierPersonTest() {
		PersonDetailRequestModel p= new PersonDetailRequestModel("066-546-258","pauldupont@gmail.com",l1);
		when(handlerJsonFile.getListePersons()).thenReturn(listeOfPersons);
		Person p1 = personService.modifierPerson("DUBOIS", "Laurine", p);
		assertNotNull(p1);
	}
}
