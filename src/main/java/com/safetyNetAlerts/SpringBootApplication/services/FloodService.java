package com.safetyNetAlerts.SpringBootApplication.services;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.safetyNetAlerts.models.FireStation;
import com.safetyNetAlerts.models.MedicalRecord;
import com.safetyNetAlerts.models.Person;

@Service
public class FloodService {
	@Autowired 
	HandlerJsonFile handlerJsonFile;

	public HashMap<String,JSONArray> getListeOfPersonCoverByStationAndMedicalRecord(String nbStation) {
		// TODO Auto-generated method stub
		HashMap<String, JSONArray> liste = new HashMap<String, JSONArray>();
		 ArrayList<String> listeOfAdress = listeOfAdressOfFireStation(nbStation);
		if(listeOfAdress.isEmpty()) {
			System.out.println("Liste vide ");
		}
		for(int i =0;i<listeOfAdress.size();i++) {
			String adress = listeOfAdress.get(i);
			JSONArray listeOfHabitants = new JSONArray();
			
			HashSet<MedicalRecord> listeMedicalRecord = new HashSet<MedicalRecord>();
			for(Map.Entry mapentry : handlerJsonFile.getListePersons().entrySet()) {
				Person p = (Person) mapentry.getValue();
				if(p.getLieu().getAdress().equals(adress)) {
					JSONObject person= new JSONObject();
					person.put("FirstName", p.getFirstName());
					person.put("LastName", p.getLastName());
					person.put("Telephone",p.getPhone());
					person.put("MedicalRecord",p.getMedicalRecord());
					listeOfHabitants.add(person);
				}
			}
			liste.put(adress, listeOfHabitants);
		}
		
		return liste;
	}
	
	public ArrayList<String> listeOfAdressOfFireStation(String nbStation){
		ArrayList<String> listeOfAdress = new ArrayList<String>();
		for(Map.Entry mapentry : handlerJsonFile.getListeFireStations().entrySet()) {
			HashSet<FireStation> listeFire = (HashSet<FireStation>) mapentry.getValue();
			Iterator i = listeFire.iterator();
			while(i.hasNext()) {
				FireStation f =(FireStation) i.next();
				if(f.getStation().equals(nbStation)) {
					listeOfAdress.add(f.getLieu().getAdress());
				}
			}
			
			
		}
		return listeOfAdress;
	}
}
