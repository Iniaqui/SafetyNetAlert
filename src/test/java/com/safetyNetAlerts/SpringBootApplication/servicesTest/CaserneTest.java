package com.safetyNetAlerts.SpringBootApplication.servicesTest;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.mockito.Mockito.when;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import com.safetyNetAlerts.SpringBootApplication.services.CaserneService;
import com.safetyNetAlerts.SpringBootApplication.services.HandlerJsonFile;
import com.safetyNetAlerts.models.FireStation;
import com.safetyNetAlerts.models.Lieu;
import com.safetyNetAlerts.models.ModelFireStationUpdate;

//@RunWith(MockitoJUnitRunner.class)
@SpringBootTest
@RunWith(SpringRunner.class)
//@WebMvcTest(ControllerApp.class)
//@MockBeans({@MockBean(HandlerJsonFile.class), @MockBean( CaserneService.class), @MockBean( FireStationsService.class),@MockBean(ChildAlertService.class),@MockBean(PhoneAlertService.class),@MockBean(FireService.class)})
class CaserneTest {
	@Autowired
	CaserneService caserneService;
	@MockBean
	HandlerJsonFile handlerJsonFile;
	
	static FireStation fireStationTest;
	static HashMap<String ,Lieu> listeOfLieuTest;
	static HashSet<FireStation> listeStationTest;
	static Lieu lieuTest;
	static HashMap<String , HashSet<FireStation>> liste;
	
	@BeforeAll
	static void  setUp() {
		
	    lieuTest= new Lieu("24 François","Culver","70000");
		fireStationTest = new FireStation("22",lieuTest);
		Lieu l1 = new Lieu("17 rue Jean","Nantes","14000");
		Lieu l2 = new Lieu("26 avenue Claude","Lionnes","27000");
		Lieu l3 = new Lieu("6 rue de Bellevues","Paris","76000");
		listeOfLieuTest = new HashMap<String , Lieu>();
		listeOfLieuTest.put("17 rue Jean", l1);
		listeOfLieuTest.put("26 avenue Claude", l2);
		listeOfLieuTest.put("6 rue de Bellevues", l3);
		listeOfLieuTest.put("24 François", lieuTest);
		listeStationTest = new HashSet<FireStation>();
		listeStationTest.add(new FireStation("23",lieuTest));
		listeStationTest.add(new FireStation("22",lieuTest));
		liste = new HashMap<String , HashSet<FireStation>>();
		liste.put("24 François", listeStationTest);
		FireStation fToDelete = new FireStation("24",lieuTest);
		
		
	}
	
	@Test
	void ajoutCasernetest() {
	
		
		when(handlerJsonFile.getListeOfLieu()).thenReturn(listeOfLieuTest);
		when(handlerJsonFile.getListeFireStations()).thenReturn(liste);
		assertNotNull(caserneService.ajouterCaserne(fireStationTest));
		
	}
	@Test
	void updateCaserneTest() {
		ModelFireStationUpdate m= new ModelFireStationUpdate(lieuTest,fireStationTest.getStation(),"200");
		when(handlerJsonFile.getListeFireStations()).thenReturn(liste);
		FireStation response = caserneService.modifierNumberStation(m);
		
		assertEquals(response.getStation(),"200");
	}
	@Test
	void deleteStationTest() {
		FireStation response = null;
		//ModelFireStationUpdate m= new ModelFireStationUpdate(lieuTest,fireStationTest.getStation(),"200");
		when(handlerJsonFile.getListeFireStations()).thenReturn(liste);
		//caserneService.modifierNumberStation(m);
		caserneService.deletStation(lieuTest.getAdress(),"200");
		HashSet<FireStation> listeSta = liste.get("24 François");
		if(listeSta!=null) {
			Iterator it = listeSta.iterator();
			while(it.hasNext()) {
				FireStation f = (FireStation) it.next();
				if(f.getStation().equals("200")) {
					response = f;
					
				}
			}
		}
		
		//System.out.println(r);
		assertNull(response);
	}
}








