package com.safetyNetAlerts.SpringBootApplication.services;

import java.util.ArrayList;
import java.util.HashMap;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.safetyNetAlerts.models.MedicalRecord;
import com.safetyNetAlerts.models.MedicalRecordModelUpdate;
import com.safetyNetAlerts.models.Person;
@Service
public class MedicalRecordService {
	private static final Logger logger = LogManager.getLogger("MedicalRecordService");
@Autowired 
HandlerJsonFile handlerJsonFile;
	public String deletMedicalRecord(String firstName,String lastName) {
		String r ="Echec de suppression";
		if(handlerJsonFile.getListeMedicalRecords().containsKey(firstName)) {
			handlerJsonFile.getListeMedicalRecords().remove(firstName);
			r="Supression reussi";
		}
		return r;
	}

	public MedicalRecord ajoutMedicalRecord(MedicalRecord medicalRecord,String firstName, String lastName) {
		MedicalRecord m = null;
		// TODO Auto-generated method stub
		//this.ajoutMedicalRecord(medicalRecord.getPerson().getFirstName(), medicalRecord.getPerson().getLastName(), , allergies)
		if(handlerJsonFile.getListePersons().containsKey(firstName) && handlerJsonFile.getListePersons().get(firstName).getLastName().equals(lastName)) {
			
			handlerJsonFile.getListeMedicalRecords().put(firstName, medicalRecord);
			if(handlerJsonFile.getListeMedicalRecords().containsKey(firstName)) {
				logger.info("Enregistrement du dossier medicale reussi ");
				m=handlerJsonFile.getListeMedicalRecords().get(firstName);
			}
			
		}
		else {
			System.out.println("Personne introuvable ");
		}
		return m;
	}

	public MedicalRecord modfierMedicalRecord(String firstName, String lastName, MedicalRecordModelUpdate medicalRecordModelUpdate) {
		// TODO Auto-generated method stub
		MedicalRecord m=null;
		if(handlerJsonFile.getListePersons().containsKey(firstName) && handlerJsonFile.getListePersons().get(firstName).getLastName().equals(lastName)) {
			 m= handlerJsonFile.getListePersons().get(firstName).getMedicalRecord();
			m.setAllergies(medicalRecordModelUpdate.getAllergies());
			m.setMedications(medicalRecordModelUpdate.getMedications());
		}
		else {
			logger.info("Dossier medicale introuvable");
		}
		return m;
	}

	public HashMap<String, MedicalRecord> listeMedicalRecord() {
		// TODO Auto-generated method stub
		return handlerJsonFile.getListeMedicalRecords();
	}
}
