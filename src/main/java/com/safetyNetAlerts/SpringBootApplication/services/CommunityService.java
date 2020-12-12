package com.safetyNetAlerts.SpringBootApplication.services;

import java.util.ArrayList;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.safetyNetAlerts.models.Person;

@Service
public class CommunityService {
@Autowired
HandlerJsonFile handlerJsonFile;
	public ArrayList<String> listeOfEveryBodyMail(String city) {
		ArrayList<String> listeMail= new ArrayList<String>();
		// TODO Auto-generated method stub
		for(Map.Entry mapentry : handlerJsonFile.getListePersons().entrySet()) {
			Person p=(Person)mapentry.getValue();
			if(p.getLieu().getCity().equals(city)) {
				listeMail.add(p.getMail());
			}
		}
		return listeMail;
	}

}
