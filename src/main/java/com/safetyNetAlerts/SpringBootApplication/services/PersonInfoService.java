package com.safetyNetAlerts.SpringBootApplication.services;

import java.util.ArrayList;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.safetyNetAlerts.models.MedicalRecord;
import com.safetyNetAlerts.models.Person;

@Service
public class PersonInfoService {
@Autowired
HandlerJsonFile handlerJsonFile;

public ArrayList<Person> getListeOfPersonInfo(String firstName, String lastName) {
	// TODO Auto-generated method stub
	
	ArrayList<Person> liste = new ArrayList<Person>();
	if(lastName==null && firstName != null) {
		for(Map.Entry mapentry : handlerJsonFile.getListePersons().entrySet()) {
			Person p = (Person)mapentry.getValue();
			if(p.getFirstName().equals(firstName)) {
				liste.add(p);
			}
		}
	}
	if(firstName == null && lastName !=null) {
		for(Map.Entry mapentry : handlerJsonFile.getListePersons().entrySet()) {
			Person p = (Person)mapentry.getValue();
			if(p.getLastName().equals(lastName)) {
				liste.add(p);
			}
		}
	}
	else {
		for(Map.Entry mapentry : handlerJsonFile.getListePersons().entrySet()) {
			Person p = (Person)mapentry.getValue();
			if(p.getLastName().equals(lastName) && p.getFirstName().equals(firstName)) {
				System.out.println("Liste name  "+lastName +""+firstName);
				liste.add(p);
			}
		}
	}
	
	
	return liste;
}


}
