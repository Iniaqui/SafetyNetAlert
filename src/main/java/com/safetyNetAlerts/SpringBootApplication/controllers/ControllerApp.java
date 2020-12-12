package com.safetyNetAlerts.SpringBootApplication.controllers;

import java.net.URI;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.safetyNetAlerts.SpringBootApplication.services.CaserneService;
import com.safetyNetAlerts.SpringBootApplication.services.ChildAlertService;
import com.safetyNetAlerts.SpringBootApplication.services.CommunityService;
import com.safetyNetAlerts.SpringBootApplication.services.FireService;
import com.safetyNetAlerts.SpringBootApplication.services.FireStationsService;
import com.safetyNetAlerts.SpringBootApplication.services.FloodService;
import com.safetyNetAlerts.SpringBootApplication.services.HandlerJsonFile;
import com.safetyNetAlerts.SpringBootApplication.services.MedicalRecordService;
import com.safetyNetAlerts.SpringBootApplication.services.PersonInfoService;
import com.safetyNetAlerts.SpringBootApplication.services.PersonService;
import com.safetyNetAlerts.SpringBootApplication.services.PhoneAlertService;
import com.safetyNetAlerts.models.FireStation;
import com.safetyNetAlerts.models.Lieu;
import com.safetyNetAlerts.models.MedicalRecord;
import com.safetyNetAlerts.models.MedicalRecordModelUpdate;
import com.safetyNetAlerts.models.ModelFireStationUpdate;
import com.safetyNetAlerts.models.Person;
import com.safetyNetAlerts.models.PersonDetailRequestModel;

@RestController
public class ControllerApp {
	private static final Logger logger = LogManager.getLogger("ControllerApp");
	// Instanciation des classe primaires
	@Autowired
	HandlerJsonFile handlerJsonFile;
	@Autowired
	private FireStationsService fireStationService;
	@Autowired
	private ChildAlertService childAlertService;
	@Autowired 
	PhoneAlertService phoneAlertService;
	@Autowired
	FireService fireService;
	@Autowired
	FloodService floodService;
	@Autowired
	PersonInfoService personInfoService;
	@Autowired
	CommunityService communityService;
	
	@GetMapping("/firestation")
	@ResponseBody
	public HashMap<String, JSONArray> listeOfPersonCoverByStation(HttpServletRequest request) {
		logger.info("Liste Of The Person Cover By Station request");
		String nbStation = request.getParameter("stationNumber") != null
				&& !request.getParameter("stationNumber").isEmpty() ? request.getParameter("stationNumber") : "null";
		if (nbStation == "null") {
			System.out.println("Erreur ");
			return null;
		} 
		return this.fireStationService.listeOfPerson(nbStation);

	}

	@GetMapping("/childAlert")
	@ResponseBody
	public JSONObject listeOfChildInThisAdress(HttpServletRequest request) {
		logger.info("List of child in this adress request ");
		String adress = request.getParameter("address") != null && !request.getParameter("address").isEmpty()
				? request.getParameter("address")
				: null;
		if (adress == "null") {
			System.out.println("Adress get invalide ");
			return null;
		} 
		return this.childAlertService.listOfChild(adress);
	}

	@GetMapping("/phoneAlert")
	//@ResponseBody
	public List<String> listOfTelephoneNumber(HttpServletRequest request) {
		logger.info("Liste Of telephone number ");
		String nbStation = request.getParameter("firestation") != null
				&& !request.getParameter("firestation").isEmpty() ? request.getParameter("firestation")
						: "null";
		if (nbStation == "null") {
			System.out.println("Erreur ");
			return null;
		} 
		return this.phoneAlertService.listOfNumber(nbStation);

	}
	
	@GetMapping("/fire")
	@ResponseBody
	public JSONObject listeOfFire(HttpServletRequest request){
		logger.info("List of the station in this adress request");
		String adress = request.getParameter("address") != null && !request.getParameter("address").isEmpty()
				? request.getParameter("address")
				: null;
		if (adress == "null") {
			System.out.println("Adress get invalide ");
			return null;
		} 
		else {
			System.out.println(adress);
		}
		return this.fireService.listeOfPersonCoverBy(adress);
	}
	
	@GetMapping("/flood/stations")
	@ResponseBody
	public HashMap<String,JSONArray> listeOfPersonCoverByStationAndMedicalRecord(HttpServletRequest request){
		logger.info("listeOfPersonCoverByStationAndMedicalRecord");
		String nbStation = request.getParameter("stations") != null
				&& !request.getParameter("stations").isEmpty() ? request.getParameter("stations")
						: "null";
		if (nbStation == "null") {
			System.out.println("Erreur ");
			return null;
		} 
		return this.floodService.getListeOfPersonCoverByStationAndMedicalRecord(nbStation);
	}
	
	@GetMapping("/personInfo")
	@ResponseBody
	public ArrayList<Person> listePersonInfo(HttpServletRequest request){
		String firstName = request.getParameter("firstName") != null
				&& !request.getParameter("firstName").isEmpty() ? request.getParameter("firstName")
						: null;
		String lastName = request.getParameter("lastName") != null
				&& !request.getParameter("lastName").isEmpty() ? request.getParameter("lastName")
						: null;
		return this.personInfoService.getListeOfPersonInfo(firstName,lastName);
	}
	@GetMapping("/communityEmail")
	@ResponseBody
	public ArrayList<String> listeOfEveryBodyMail(HttpServletRequest request){
		String city = request.getParameter("city") != null
				&& !request.getParameter("city").isEmpty() ? request.getParameter("city")
						: null;
		return this.communityService.listeOfEveryBodyMail(city);
	}
	@PostMapping(value = "/lieu")
	public void ajoutLieu(@RequestBody Lieu l) {
		System.out.println("Invoke");
		this.handlerJsonFile.getListeOfLieu().put(l.getAdress(), l);
	}
	@GetMapping(value = "/listelieu")
	public HashMap<String ,Lieu> ajoutLieu() {
		
		return this.handlerJsonFile.getListeOfLieu();
	}
}
