package com.safetyNetAlerts.SpringBootApplication.services;

import java.util.HashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.safetyNetAlerts.models.Lieu;
import com.safetyNetAlerts.models.Person;
import com.safetyNetAlerts.models.PersonDetailRequestModel;
@Service
public class PersonService {
@Autowired
HandlerJsonFile handlerJsonFile;

	public HashMap<String,Person> searchPerson(String firstName) {
		HashMap<String,Person> liste = new HashMap<String,Person>();
		Person p = handlerJsonFile.getListePersons().get(firstName);
		liste.put(firstName, p);
		return liste;
	}
	public HashMap<String,Person> listePerson(){
		return handlerJsonFile.getListePersons();
	}
	public Person modifierPerson(String firstName, String lastName,PersonDetailRequestModel p) {
		// TODO Auto-generated method stub
		String response;
		if(searchPerson(firstName,lastName)) {
			if(p.getMail() !=null) {
				handlerJsonFile.getListePersons().get(firstName).setMail(p.getMail());
			}
			if(p.getPhone() != null) {
				handlerJsonFile.getListePersons().get(firstName).setPhone(p.getPhone());
			}
			if(p.getLieu().getAdress() != null) {
				handlerJsonFile.getListePersons().get(firstName).getLieu().setAdress(p.getLieu().getAdress());
			}
			if(p.getLieu().getCity() != null) {
				handlerJsonFile.getListePersons().get(firstName).getLieu().setCity(p.getLieu().getCity());
			}
			if(p.getLieu().getZip() != null) {
				handlerJsonFile.getListePersons().get(firstName).getLieu().setZip(p.getLieu().getZip());
			}
			if(p.getBirthday() !=  null) {
				handlerJsonFile.getListePersons().get(firstName).setBirthday(p.getBirthday());
			}
			System.out.println("Profile modifié");
		}
		else {
			System.out.println("Profile introuvable");
		}
		return handlerJsonFile.getListePersons().get(firstName);
	}
	public String deletePerson(String firstName, String lastName) {
		// TODO Auto-generated method stub
		String r="Echec supression";
		if(searchPerson(firstName,lastName)) {
			handlerJsonFile.supprPerson( firstName,lastName);
			r="Supression effective";
			
		}
		return r;
	}
	public boolean searchPerson(String firstName, String lastName) {
		if(handlerJsonFile.getListePersons().containsKey(firstName) && handlerJsonFile.getListePersons().get(firstName).getLastName().equals(lastName)) {
			return true;
		}
		else {
			return false;
		}
		
	}
	public void addLieu() {
		
	}
	public Person ajoutPersonne(Person p) {
		System.out.println(p.getLieu().getAdress());
		handlerJsonFile.getListeOfLieu().put(p.getLieu().getAdress(), p.getLieu());
		handlerJsonFile.ajoutPerson(p.getFirstName(), p);
		
		return p;
		
	}
}
