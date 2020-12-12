package com.safetyNetAlerts.SpringBootApplication.controllers;

import java.net.URI;
import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
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

import com.safetyNetAlerts.SpringBootApplication.services.PersonService;
import com.safetyNetAlerts.models.Person;
import com.safetyNetAlerts.models.PersonDetailRequestModel;
@RestController
@RequestMapping("/person")
public class ControllerPerson {
	
	@Autowired
	PersonService personService;
	
	@PostMapping(value="/add")
	public ResponseEntity<Void> ajoutPerson(@RequestBody Person person) {
		
		Person p =this.personService.ajoutPersonne(person);
		if(p==null) {
			return ResponseEntity.noContent().build();
		}
		URI location =ServletUriComponentsBuilder.fromCurrentRequest().path("/{firstName}").buildAndExpand(person.getFirstName()).toUri();
		return ResponseEntity.created(location).build();
	}
	@PutMapping(value="/update")
	public ResponseEntity<Void> modfierPerson(HttpServletRequest request,@RequestBody PersonDetailRequestModel personDetailRequestModel) {
		String firstName = request.getParameter("firstName") != null
				&& !request.getParameter("firstName").isEmpty() ? request.getParameter("firstName")
						: null;
		String lastName = request.getParameter("lastName") != null
				&& !request.getParameter("lastName").isEmpty() ? request.getParameter("lastName")
						: null;
		Person p =this.personService.modifierPerson(firstName, lastName, personDetailRequestModel);
		if(p==null) {
			return ResponseEntity.noContent().build();
		}
		URI location =ServletUriComponentsBuilder.fromCurrentRequest().path("/{firstName}").buildAndExpand(p.getFirstName()).toUri();
		return ResponseEntity.created(location).build();
	}
	@DeleteMapping(value="/delete")
	public ResponseEntity<Void> supprPerson(HttpServletRequest request) {
		String firstName = request.getParameter("firstName") != null
				&& !request.getParameter("firstName").isEmpty() ? request.getParameter("firstName")
						: null;
		String lastName = request.getParameter("lastName") != null
				&& !request.getParameter("lastName").isEmpty() ? request.getParameter("lastName")
						: null;
		this.personService.deletePerson(firstName,lastName);
		return ResponseEntity.accepted().build();
	}
	@GetMapping
	@ResponseBody
	public HashMap<String, Person> listeOfPerson(HttpServletRequest request,ModelMap modelMap) {
		return  this.personService.listePerson();
	}
}
