package com.safetyNetAlerts.SpringBootApplication.services;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.safetyNetAlerts.models.Person;
@Service
public class Recherche {
	
	public Person searchPerson(HashMap<String, Person> listePersons,String firstName) {
		Person p=null;
		System.out.println("Recherche de fistName : "+firstName);
		 
		for(Map.Entry mapentry : listePersons.entrySet()) {
			System.out.println("On retire chaque person \n Person  : "+((Person) mapentry.getValue()).getFirstName()+"\n");
			String namePerson = (String)((Person) mapentry.getValue()).getFirstName();
			System.out.println(namePerson +" et "+firstName);
			if(namePerson.equals(firstName) ) {
				p=(Person) mapentry.getValue();
				break;
				
			}
		}
		
		return p;
	}
	
	 public void affiche(HashMap<String, Person> listePersons) {
		 System.out.println("Affichage de touet la liste --------------------------------");
		
		 for(Map.Entry entry : listePersons.entrySet()) {
				System.out.println("\n"+entry.getKey()+": "+ entry.getValue());
			}
	 }
}
