package com.safetyNetAlerts.SpringBootApplication.services;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.safetyNetAlerts.models.FireStation;
import com.safetyNetAlerts.models.Person;
@Service
public class PhoneAlertService {
	@Autowired
	HandlerJsonFile handlerJsonFile;




	public ArrayList<String> listOfNumber(String nbStation) {
		// TODO Auto-generated method stub
		// Obtentions de la liste des adress couvertes par une station
		ArrayList<String> liste = new ArrayList<String>();
		ArrayList<String> listeAdress = getAdress(nbStation);
		// Recherche de person se trouvant à cette adress
		for (int i = 0; i < listeAdress.size(); i++) {
			String adress = listeAdress.get(i);
			for (Map.Entry mapentry : handlerJsonFile.getListePersons().entrySet()) {
				Person p = (Person) mapentry.getValue();
				String adressPerson = p.getLieu().getAdress();
				if (adress.equals(adressPerson)) {
					liste.add( p.getPhone());
				}
			}
		}

		if (liste.isEmpty()) {
			System.out.println("Liste nulle");
		}
		return liste;

	}

	private ArrayList<String> getAdress(String nbStation) {
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

}
