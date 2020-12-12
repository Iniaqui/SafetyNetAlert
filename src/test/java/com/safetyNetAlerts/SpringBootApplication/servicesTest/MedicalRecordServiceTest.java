package com.safetyNetAlerts.SpringBootApplication.servicesTest;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
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
import com.safetyNetAlerts.SpringBootApplication.services.MedicalRecordService;
import com.safetyNetAlerts.models.FireStation;
import com.safetyNetAlerts.models.Lieu;
import com.safetyNetAlerts.models.MedicalRecord;
import com.safetyNetAlerts.models.MedicalRecordModelUpdate;
import com.safetyNetAlerts.models.Person;
@SpringBootTest
@RunWith(MockitoJUnitRunner.class)
class MedicalRecordServiceTest {
	@Autowired
	MedicalRecordService medicalRecordService;
	@MockBean
	HandlerJsonFile handlerJsonFile;

	static HashMap<String, HashSet<FireStation>> liste;
	static HashMap<String, Person> listeOfPersons;
	static  HashMap<String ,Lieu> listeOfLieux ;
	static HashSet<FireStation> listeStationTest;
	static HashMap<String, Lieu> listeOfLieuTest;
	static HashMap<String , MedicalRecord> listeMedicalRecord;
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
		
		listeMedicalRecord = new HashMap<String,MedicalRecord>();
		listeOfPersons= new HashMap<String, Person>();
		Person person1 = new Person("DUPONT", "Paul", "066-546-258", "pauldupont@gmail.com", lieuTest);
		person1.setBirthday("03/06/1984");
		ArrayList<String> medications1= new ArrayList<String>();
		medications1.add("aznol:350mg");
		medications1.add("hydrapermazol:100mg");
		ArrayList<String> allergies1= new ArrayList<String>();
		allergies1.add("nillacilan");
		MedicalRecord m1= new MedicalRecord(medications1,allergies1);
		person1.setMedicalRecord(m1);
		
		Person person2 = new Person("DUBOIS", "Laurine", "255-668-547", "laurinedubois@gmail.com", lieuTest);
		person2.setBirthday("06/05/2006");
		ArrayList<String> medications2= new ArrayList<String>();
		medications2.add("aznol:350mg");
		medications2.add("hydrapermazol:100mg");
		ArrayList<String> allergies2= new ArrayList<String>();
		MedicalRecord m2= new MedicalRecord(medications2,allergies2);
		person2.setMedicalRecord(m2);
		Person person3 = new Person("DUCON", "Louis", "865-874-963", "louisducon@gmail.com", lieuTest);
		person3.setBirthday("08/07/2001");
		
		listeOfPersons.put("DUPONT", person1);
		listeOfPersons.put("DUBOIS", person2);
		listeOfPersons.put("DUCON", person3);
		
		listeMedicalRecord.put("DUPONT", m1);
		listeMedicalRecord.put("DUBOIS", m2);
		
	}
	@Test
	void ajoutMedicaltest() {
		ArrayList<String> medications3= new ArrayList<String>();
		medications3.add("aznol:350mg");
		medications3.add("hydrapermazol:100mg");
		ArrayList<String> allergies3= new ArrayList<String>();
		allergies3.add("nillacilan");
		MedicalRecord mtoadd = new MedicalRecord(medications3,allergies3);
		MedicalRecord m3 = null;
		when(handlerJsonFile.getListeMedicalRecords()).thenReturn(listeMedicalRecord);
		when(handlerJsonFile.getListePersons()).thenReturn(listeOfPersons);
		m3 = medicalRecordService.ajoutMedicalRecord(mtoadd, "DUCON", "Louis");
		assertNotNull(m3);
	}
	@Test
	void updateTest() {
		MedicalRecord m3 = null;
		ArrayList<String> medications2= new ArrayList<String>();
		medications2.add("aznol:350mg");
		medications2.add("hydrapermazol:100mg");
		ArrayList<String> allergies= new ArrayList<String>();
		 MedicalRecordModelUpdate medicalRecordModelUpdate = new  MedicalRecordModelUpdate(medications2,allergies);
		when(handlerJsonFile.getListeMedicalRecords()).thenReturn(listeMedicalRecord);
		when(handlerJsonFile.getListePersons()).thenReturn(listeOfPersons);
		m3=medicalRecordService.modfierMedicalRecord("DUPONT", "Paul", medicalRecordModelUpdate);
		 assertNotNull(m3);
	}
	@Test
	void deleteTest() {
		
		when(handlerJsonFile.getListeMedicalRecords()).thenReturn(listeMedicalRecord);
		when(handlerJsonFile.getListePersons()).thenReturn(listeOfPersons);
		String r = medicalRecordService.deletMedicalRecord("DUBOIS", "Laurine");
		assertEquals("Supression reussi",r);
	}
}
