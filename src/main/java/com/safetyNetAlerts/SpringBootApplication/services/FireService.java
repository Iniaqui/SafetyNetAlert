package com.safetyNetAlerts.SpringBootApplication.services;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.safetyNetAlerts.models.FireStation;
import com.safetyNetAlerts.models.MedicalRecord;
import com.safetyNetAlerts.models.Person;

@Service
public class FireService {
	private static final Logger logger = LogManager.getLogger("FireService");
	@Autowired
	HandlerJsonFile handlerJsonFile;
	
	public JSONObject listeOfPersonCoverBy(String adress){
		JSONArray listeOfStation = new JSONArray();
		JSONArray listOfHabitant = new JSONArray();
		JSONObject value = new JSONObject();;
		HashSet<FireStation> listeStations =handlerJsonFile.getListeFireStations().get(adress);
		
		Iterator i = listeStations.iterator();
		while(i.hasNext()) {
			FireStation f =(FireStation)i.next();
			listeOfStation.add(f.getStation());
		}
		if(!listeStations.isEmpty()) {
			logger.info("Liste non vide ");
		}
		for(Map.Entry mapentry : handlerJsonFile.getListePersons().entrySet()) {
			Person p = (Person)mapentry.getValue();
			if(p.getLieu().getAdress().equals(adress)) {
				JSONObject detailsOfperson = new JSONObject();
				detailsOfperson.put("FirstName", p.getFirstName());
				detailsOfperson.put("Telephone", p.getPhone());
				detailsOfperson.put("Age", p.calculateAge());
				detailsOfperson.put("Medical Record", p.getMedicalRecord());
				listOfHabitant.add(detailsOfperson);
			}
			
		}
		value.put("List Of Habitant", listOfHabitant);
		value.put("List Of Station", listeOfStation);
		
		
		return value ;
	}

}
