package com.safetyNetAlerts.SpringBootApplication.servicesTest;

import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.when;

import java.io.IOException;
import java.util.HashMap;

import org.json.simple.JSONObject;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import com.safetyNetAlerts.SpringBootApplication.services.ChildAlertService;
import com.safetyNetAlerts.SpringBootApplication.services.HandlerJsonFile;
import com.safetyNetAlerts.models.Lieu;
import com.safetyNetAlerts.models.Person;
@RunWith(MockitoJUnitRunner.class)
@SpringBootTest
class ChildAlertServiceTest {
	@MockBean
	HandlerJsonFile handlerJsonFile;
	@Autowired
	ChildAlertService childAlertService;
	static  HashMap<String ,Lieu> listeOfLieux ;
	static  HashMap<String,Person>listeOfPersons;
	@BeforeAll
	static void setUp() throws IOException {
		/*myService = new HandlerJsonFile();
		myService.initDataHandlerJsonFile();*/
		 Lieu lieuTest = new Lieu("24 François","Culver","70000");
		 Lieu lieuTest2 = new Lieu("20 François","Culver","70000");
		 listeOfLieux = new HashMap<String, Lieu>();
		listeOfPersons= new HashMap<String, Person>();
		 listeOfLieux.put("24 François", lieuTest);
		 listeOfLieux.put("20 François", lieuTest2);
		 Person person1 = new Person("DUPONT","Paul","066-546-258","pauldupont@gmail.com",lieuTest);
		 person1.setBirthday("03/06/1984");
		 Person person2 = new Person("DUBOIS","Laurine","255-668-547","laurinedubois@gmail.com",lieuTest);
		 person2.setBirthday("06/05/2006");
		 Person person3 = new Person("DUCON","Louis","865-874-963","louisducon@gmail.com",lieuTest);
		 person3.setBirthday("08/07/2001");
		 listeOfPersons.put("DUPONT",person1);
		 listeOfPersons.put("DUBOIS", person2);
		 listeOfPersons.put("DUCON", person3);
	}
	@Test
	void listeOfChildTest() {
		when(handlerJsonFile.getListePersons()).thenReturn(listeOfPersons);
		when(handlerJsonFile.getListeOfLieu()).thenReturn(listeOfLieux);
		JSONObject j=childAlertService.listOfChild("24 François");
		assertTrue(!j.isEmpty());
	}
	@Test
	void getLieuTest() {
		when(handlerJsonFile.getListeOfLieu()).thenReturn(listeOfLieux);
		assertNotNull(childAlertService.getLieu("24 François"));
	}

}
