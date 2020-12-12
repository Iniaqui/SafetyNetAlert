package com.safetyNetAlerts.SpringBootApplication.services;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.safetyNetAlerts.models.FireStation;
import com.safetyNetAlerts.models.ModelFireStationUpdate;

@Service
public class CaserneService {
	private static final Logger logger = LogManager.getLogger("CaserneService");
@Autowired
HandlerJsonFile handlerJsonFile;

	public String modifierNumberStation(String adress, String nbStation,String newNumber) {
		if(handlerJsonFile.getListeFireStations().containsKey(adress)) {
			HashSet<FireStation> set = handlerJsonFile.getListeFireStations().get(adress);
			Iterator it = set.iterator();
			while(it.hasNext()) {
				FireStation f = (FireStation) it.next();
				if(f.getStation().equals(nbStation)) {
					f.setStation(newNumber);
				}
			}
		}
		return "Modification du numéro de Caserne reussi";
	}
	public String deletStation(String adress,String nbStation) {
		String r ="Supression echoué";
		if(handlerJsonFile.getListeFireStations().containsKey(adress)) {
			HashSet<FireStation> set = handlerJsonFile.getListeFireStations().get(adress);
			if(set==null) {
				r="Element introuvable ";
			}
			else {
				Iterator it = set.iterator();
				while(it.hasNext()) {
					FireStation f=(FireStation) it.next();
					if(f!=null) {
						if(f.getStation().equals(nbStation)) {
							FireStation fs=f;
							it.remove();
							r="Supression reussi";
						}
					}
					
				}
			}
		}
		
		return r;
	}
	public HashMap<String, HashSet<FireStation>> listeCaserne() {
		// TODO Auto-generated method stub
		return handlerJsonFile.getListeFireStations();
	}
	public FireStation ajouterCaserne(FireStation fireStation) {
		// TODO Auto-generated method stub
		if(handlerJsonFile.getListeOfLieu().containsKey(fireStation.getLieu().getAdress()) && handlerJsonFile.getListeOfLieu().get(fireStation.getLieu().getAdress()).getCity().equals(fireStation.getLieu().getCity()) && handlerJsonFile.getListeOfLieu().get(fireStation.getLieu().getAdress()).getZip().equals(fireStation.getLieu().getZip())) {
			
			HashSet<FireStation> set = handlerJsonFile.getListeFireStations().get(fireStation.getLieu().getAdress());
			// Mise a jour de la liste de station a cette adress
			if(set==null) {
				set = new HashSet<FireStation>();
			}
			set.add(fireStation);
		handlerJsonFile.getListeFireStations().put(fireStation.getLieu().getAdress(), set);
		
			
		}
		
		return fireStation;
	}
	public FireStation modifierNumberStation(ModelFireStationUpdate m) {
		// TODO Auto-generated method stub
		FireStation response = null;
		if(m!=null) {
			this.modifierNumberStation(m.getLieu().getAdress(), m.getStation(), m.getNewNumber());
			HashSet<FireStation> listeFire=handlerJsonFile.getListeFireStations().get(m.getLieu().getAdress());
			if(listeFire!=null) {
				Iterator it = listeFire.iterator();
				while(it.hasNext()) {
					FireStation f=(FireStation) it.next();
					if(f!=null) {
						if(f.getStation().equals(m.getNewNumber())) {
							response =f;
						}
					}
					
				}
			}
			
		}
		
		return response;
	}
	
}
