package com.safetyNetAlerts.SpringBootApplication.servicesTest;

import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.HashMap;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import com.safetyNetAlerts.SpringBootApplication.services.CommunityService;
import com.safetyNetAlerts.SpringBootApplication.services.HandlerJsonFile;
import com.safetyNetAlerts.models.Lieu;
import com.safetyNetAlerts.models.Person;
@RunWith(MockitoJUnitRunner.class)
@SpringBootTest
class CommunityServiceTest {
	@Autowired
	CommunityService communityService;
	@MockBean
	HandlerJsonFile handlerJsonFile;

	@Test
	void listeOfEveryBodyMailtest() {
		HashMap<String, Lieu> listeOfLieux;
		HashMap<String, Person> listeOfPersons;
		Lieu lieuTest = new Lieu("24 François", "Culver", "70000");
		Lieu lieuTest2 = new Lieu("20 François", "Culver", "70000");
		listeOfLieux = new HashMap<String, Lieu>();
		listeOfPersons = new HashMap<String, Person>();
		listeOfLieux.put("24 François", lieuTest);
		listeOfLieux.put("20 François", lieuTest2);
		Person person1 = new Person("DUPONT", "Paul", "066-546-258", "pauldupont@gmail.com", lieuTest);
		person1.setBirthday("03/06/1984");
		Person person2 = new Person("DUBOIS", "Laurine", "255-668-547", "laurinedubois@gmail.com", lieuTest);
		person2.setBirthday("06/05/2006");
		Person person3 = new Person("DUCON", "Louis", "865-874-963", "louisducon@gmail.com", lieuTest);
		person3.setBirthday("08/07/2001");
		listeOfPersons.put("DUPONT", person1);
		listeOfPersons.put("DUBOIS", person2);
		listeOfPersons.put("DUCON", person3);
		
		when(handlerJsonFile.getListePersons()).thenReturn(listeOfPersons);
		ArrayList<String>  liste = communityService.listeOfEveryBodyMail("Culver");
		assertTrue(!liste.isEmpty());
	}

}
