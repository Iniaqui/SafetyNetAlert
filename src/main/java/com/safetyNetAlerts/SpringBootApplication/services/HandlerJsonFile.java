package com.safetyNetAlerts.SpringBootApplication.services;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Properties;

import javax.annotation.PostConstruct;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import com.safetyNetAlerts.models.FireStation;
import com.safetyNetAlerts.models.Lieu;
import com.safetyNetAlerts.models.MedicalRecord;
import com.safetyNetAlerts.models.Person;

@Service
public class HandlerJsonFile {
	private static final Logger logger = LogManager.getLogger("HandlerJsonFile");
	private static final String filePath = "./application.properties";
	private JSONParser parser = new JSONParser();
	private JSONObject object;
	private JSONArray persons;
	private JSONArray fireStations;
	private JSONArray medicalRecords;
	private HashMap<String, Person> listePersons = new HashMap<String, Person>();
	private HashMap<String, HashSet<FireStation>> listFireStations = new HashMap<String, HashSet<FireStation>>();
	private HashMap<String, Lieu> listOfLieu = new HashMap<String, Lieu>();// clé est adress
	private HashMap<String, MedicalRecord> listeMedicalRecords = new HashMap<String, MedicalRecord>();;

	

	public HashMap<String, MedicalRecord> getListeMedicalRecords() {
		return listeMedicalRecords;
	}

	@PostConstruct
	public void initDataHandlerJsonFile() throws IOException {
		logger.info("Start loading of file");
		this.loadOfFile();
		if (object == null) {
			
			logger.info("FAIL OF LOADING");
		} else {
			logger.info("Start of the SHaring Data");
			shareData(this.object);
			logger.info("Creation of the listes");
			initLieu();
			logger.info("Init Of Perons");
			initPerson();
			logger.info("Start of Making FireStation");
			initFireStations();
			logger.info("Medical Record");
			initMedicalRecord();
			logger.info("End of Initialisation");
			verif();
		}
	}

	public void loadOfFile() throws IOException {

		logger.info("Loading of the file  ");
		try {
			
			InputStream inputStream = getClass().getClassLoader().getResourceAsStream("./data.json");
			//this.object = (JSONObject) parser.parse(new FileReader(prop.getProperty("filePath")));
			this.object = (JSONObject) parser.parse(new InputStreamReader(inputStream));
			
			
			
			System.out.println("Extraction success");

		} catch (org.json.simple.parser.ParseException e) {
			logger.error("Error of loading , file corrupted",e);
		}
	}

	public void shareData(JSONObject obj) {
		persons = (JSONArray) obj.get("persons");
		fireStations = (JSONArray) obj.get("firestations");
		medicalRecords = (JSONArray) obj.get("medicalrecords");
	}

	public void initLieu() {
		if (persons != null) {
			logger.info("Making of List Of Location");
			Iterator<JSONObject> iterator = persons.iterator();
			while (iterator.hasNext()) {
				JSONObject person = iterator.next();
				String adress = (String) person.get("address");
				String city = (String) person.get("city");
				String zip = (String) person.get("zip");
				this.listOfLieu.put(adress, new Lieu(adress, city, zip));
			}
			logger.info("Succes of loading HasHMap Location");
		} else {
			logger.error("Fail of Creation Person ; cause : persons is null");
		}
	}

	public void initPerson() {
		if (persons != null) {
			logger.info("Making of List Of Person");
			Iterator<JSONObject> iterator = persons.iterator();
			while (iterator.hasNext()) {
				JSONObject person = iterator.next();
				String firstName = (String) person.get("firstName");
				String lastName = (String) person.get("lastName");
				String phone = (String) person.get("phone");
				String mail = (String) person.get("email");
				String adress = (String) person.get("address");
				Lieu lieu = this.listOfLieu.get(adress);
				listePersons.put(firstName, new Person(firstName, lastName, phone, mail, lieu));
			}
			logger.info("Succes of loading HasHMap Person");
		} else {
			logger.error("Fail of Creation Person ; cause : persons is null");
		}

	}

	public void initFireStations() {
		if (this.fireStations != null) {
			logger.info("Making of List Of FireStations");
			Iterator<JSONObject> iterator = fireStations.iterator();
			while (iterator.hasNext()) {
				JSONObject fireStation = iterator.next();
				String adress = (String) fireStation.get("address");
				String station = (String) fireStation.get("station");
				// Recherche du lieu dans la liste de lieu avec la clé adress
				Lieu lieu = this.listOfLieu.get(adress);
				if (this.listFireStations.containsKey(adress)) {
					// Recuperation de la liste des station a cette adress
					HashSet<FireStation> set = this.listFireStations.get(adress);
					// Mise a jour de la liste de station a cette adress
					set.add(new FireStation(station, lieu));
					this.listFireStations.put(adress, set);
				} else {

					HashSet<FireStation> setFireStation = new HashSet<FireStation>();
					setFireStation.add(new FireStation(station, lieu));
					this.listFireStations.put(adress, setFireStation);
				}
				logger.info("Creation of FireStation " + station + " on the location " + adress);

			}

		} else {
			logger.error("Fail of Creation firestation ; cause : fireStation is null ");
		}
	}

	public void initMedicalRecord() {
		if (this.medicalRecords != null) {
			logger.info("Making of List Of MedicalRecord");
			Iterator<JSONObject> iterator = medicalRecords.iterator();
			while (iterator.hasNext()) {
				ArrayList<String> listeMedications = new ArrayList<String>();
				ArrayList<String> listeAllergies = new ArrayList<String>();
				JSONObject medicalRecord = iterator.next();
				String firstName = (String) medicalRecord.get("firstName");
				String lastName = (String) medicalRecord.get("lastName");
				String date = (String) medicalRecord.get("birthdate");
				JSONArray medication = (JSONArray) medicalRecord.get("medications");
				JSONArray allergies = (JSONArray) medicalRecord.get("allergies");
				// Extraction of data of medication and allergies
				for (int i = 0; i < medication.size(); i++) {
					listeMedications.add((String) medication.get(i));
					System.out.println("medication  : " + (String) medication.get(i));
				}
				for (int i = 0; i < allergies.size(); i++) {
					listeAllergies.add((String) allergies.get(i));
				}
				// Search of persona
				Person p = searchPerson(firstName);
				p.setBirthday(date);
				MedicalRecord m =  new MedicalRecord(listeMedications, listeAllergies);
				p.setMedicalRecord(m);
				this.listeMedicalRecords.put(firstName,p.getMedicalRecord());
				
				System.out.println("Creation dossier medicale de "
						+ p.getFirstName()+ " crée ");
			}
			logger.info("Creation des dossier Medicaux  reussi");

		} else {
			logger.error("Fail of Creation MedicalRecord ; cause : fireStation is null ");
		}
	}

	public Person searchPerson(String firstName) {
		Person p = null;
		System.out.println("Recherche de fistName : " + firstName);

		for (Map.Entry mapentry : this.listePersons.entrySet()) {
			String namePerson = (String) ((Person) mapentry.getValue()).getFirstName();
			if (namePerson.equals(firstName)) {
				p = (Person) mapentry.getValue();
				break;
			}
		}
		return p;
	}

	public HashMap<String, Person> getListePersons() {
		return this.listePersons;
	}
	public boolean ajoutPerson(String firstName,Person p) {
		
		this.listePersons.put(firstName, p);
		if(this.listePersons.containsKey(firstName)) {
			return true;
		}
		else {
			return false;
		}
		
		
	}

	public HashMap<String, HashSet<FireStation>> getListeFireStations() {
		return this.listFireStations;
	}

	public HashMap<String, Lieu> getListeOfLieu() {
		return this.listOfLieu;
	}
	

	public HandlerJsonFile() {
		logger.info("Creation du beans HandlerJsonFile");
	}

	public void verif() {
		if (this.getListeFireStations().isEmpty()) {
			System.out.println("Liste FireStation vide");
		}
		if (this.getListePersons().isEmpty()) {
			System.out.println("Liste Person vide");
		}
	}

	public void supprPerson(String firstName, String lastName) {
		// TODO Auto-generated method stub
		if(this.listePersons.containsKey(firstName) && listePersons.get(firstName).getLastName().equals(lastName)) {
			listePersons.remove(firstName);
			if(this.listeMedicalRecords.containsKey(firstName)) {
				listeMedicalRecords.remove(firstName);
				System.out.println("Supression effective de liste Medicale");
			}
			System.out.println("Supression effective de liste Person");
		}
	}
	public boolean ajoutLieu(Lieu l) {
		// TODO Auto-generated method stub
		if(this.listOfLieu.containsKey(l.getAdress())) {
			return true;
		}
		else {
			return false;
		}
		
	}

	
}
