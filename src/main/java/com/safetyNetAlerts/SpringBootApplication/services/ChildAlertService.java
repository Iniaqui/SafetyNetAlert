package com.safetyNetAlerts.SpringBootApplication.services;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.joda.time.DateTime;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.safetyNetAlerts.models.Lieu;
import com.safetyNetAlerts.models.Person;
@Service
public class ChildAlertService {
	private static final Logger logger = LogManager.getLogger("ChildAlertService");
	@Autowired
	private HandlerJsonFile handlerJsonFile;
	public JSONObject listOfChild(String adress) {
		JSONArray listeOfChild = new JSONArray();
		JSONObject value = new JSONObject();
		ArrayList<String> listeMember=new ArrayList<String>();
		
		Lieu lieu = getLieu(adress);
		DateTime tdy = new DateTime();
		for (Map.Entry mapentry : handlerJsonFile.getListePersons().entrySet()) {
			Person p = (Person) mapentry.getValue();
			// Verification du lieu
			if (p.getLieu().equals(lieu)) {
				// Verification  pde l'age
				if ((tdy.getYear() - (p.returnDateIntoDateTime().getYear()) <= 18)) {
					JSONObject child = new JSONObject();
					child.put("FirstName",p.getFirstName());
					child.put("LastName", p.getLastName());
					child.put("Age",p.calculateAge());
					listeOfChild.add(child);
				}
				else {
					System.out.println(p.getFirstName());
					listeMember.add(p.getFirstName());
				}

			}
		}
		value.put("Liste Of Child",listeOfChild);
		value.put("List of Member", listeMember);

		return value;
	}
	/*public int calculateAge(Person p) {
		
	}*/
	public Lieu getLieu(String adress) {
		// TODO Auto-generated method stub
		Lieu lieu = (Lieu) handlerJsonFile.getListeOfLieu().get(adress);
		if (lieu == null) {
			logger.error("Adress incoorect ou introuvable ");
		}

		return lieu;
	}
}
