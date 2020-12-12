package com.safetyNetAlerts.SpringBootApplication.services;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.joda.time.DateTime;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.safetyNetAlerts.models.FireStation;
import com.safetyNetAlerts.models.Person;

@Service
public class FireStationsService {
	private static final Logger logger = LogManager.getLogger("FireStationService");
	@Autowired
	private HandlerJsonFile handlerJsonFile;

	public HashMap<String, JSONArray> listeOfPerson(String nbStation) {
		JSONArray listePerson= new JSONArray();
		JSONArray listeOfSorting = new JSONArray();
		HashMap<String,Integer> sort;
		HashMap<String,JSONArray> listeIntoJsonArray = new HashMap<String,JSONArray>();
		// Obtentions de la liste des adress couvertes par une station
		HashMap<String, JSONObject> liste = new HashMap<String, JSONObject>();
		HashMap<String, Person> persons= new HashMap<String, Person>();
		ArrayList<String> listeAdress = getAdress(nbStation);
		// Recherche de person se trouvant à cette adress
		for (int i = 0; i < listeAdress.size(); i++) {
			String adress = listeAdress.get(i);
			for (Map.Entry mapentry : handlerJsonFile.getListePersons().entrySet()) {
				Person p = (Person) mapentry.getValue();
				String adressPerson = p.getLieu().getAdress();
				if (adress.equals(adressPerson)) {
					JSONObject person =new JSONObject();
					person.put("FirstName",p.getFirstName());
					person.put("LastName",p.getLastName());
					person.put("Adress", p.getLieu());
					person.put("Telephone",p.getPhone());
					persons.put(p.getFirstName(), p);
					liste.put(p.getFirstName(),person );
				}
			}
		}
		 sort = this.sortTheListByBirthday(persons);
		 listePerson.add(liste);
		 listeOfSorting.add(sort);
		 listeIntoJsonArray.put("List Of Person", listePerson);
		 listeIntoJsonArray.put("Categorie", listeOfSorting);
		if (liste.isEmpty()) {
			logger.info("Liste nulle");
		}
		return listeIntoJsonArray;

	}

	public ArrayList<String> getAdress(String nbStation) {
		ArrayList<String> listeAdress = new ArrayList<String>();

		if (handlerJsonFile.getListeFireStations().isEmpty()) {
			System.out.println("listFireStation null ");
		}
		// Parcours la liste de FireStation
		for (Map.Entry mapentry : handlerJsonFile.getListeFireStations().entrySet()) {
			// Extrait chaque element de la liste avec la clé adress .Chaque element de la
			// liste est un liste HashSet de Station
			HashSet<FireStation> set = (HashSet<FireStation>) mapentry.getValue();
			System.out.println("Parcours de la liste des station a l'adress  " + (String) mapentry.getKey());
			System.out.println("Recherche station :" + nbStation);
			Iterator it = set.iterator();
			while (it.hasNext()) {
				String stationNumber = (String) ((FireStation) it.next()).getStation();
				System.out.println("Entree dans la boucle de fireStation");
				System.out.println("Station existante : " + stationNumber);
				if (nbStation.equals(stationNumber)) {
					System.out.println("Trouvé ");
					// Enregistrement de l'adress
					listeAdress.add((String) mapentry.getKey());
				}
			}
		}
		if (listeAdress.isEmpty()) {
			System.out.println("Liste Adress null");
		}
		return listeAdress;
	}

	public HashMap<String,Integer> sortTheListByBirthday(HashMap<String, Person> listeOfPerson) {
		int nbEnfants = 0;
		int nbAdultes = 0;
		DateTime tdy = new DateTime();
		HashMap<String,Integer> sort= new HashMap<String,Integer>();

		for (Map.Entry mapentry : listeOfPerson.entrySet()) {
			Person p = (Person) (mapentry.getValue());

			if ((tdy.getYear()) - (p.returnDateIntoDateTime().getYear()) <= 18) {
				nbEnfants++;
			} else {
				nbAdultes++;
			}
		}
		sort.put("NbAdultes", nbAdultes);
		sort.put("NbEnfants", nbEnfants);
		System.out.println("Il y a " + nbEnfants + " enfants et " + nbAdultes + " adultes");
		return sort;
		
	}

}
