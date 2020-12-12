package com.safetyNetAlerts.SpringBootApplication.controllers;

import java.net.URI;
import java.util.HashMap;
import java.util.HashSet;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
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
import com.safetyNetAlerts.models.FireStation;
import com.safetyNetAlerts.models.ModelFireStationUpdate;
@RestController
@RequestMapping(value="/fireStation")
public class ControllerFireStation {
	@Autowired
	CaserneService casernService;
	@PostMapping(value="/add")
	public ResponseEntity<Void> ajoutCasern(@RequestBody FireStation fireStation){
		FireStation f=this.casernService.ajouterCaserne(fireStation);
		if(f==null) {
			return ResponseEntity.noContent().build();
		}
		URI location =ServletUriComponentsBuilder.fromCurrentRequest().path("/{numberStation}").buildAndExpand(f.getStation()).toUri();
		return ResponseEntity.created(location).build();
	}
	@PutMapping(value="/update")
	public ResponseEntity<Void> updateCaserne(@RequestBody ModelFireStationUpdate m){
		FireStation f = this.casernService.modifierNumberStation(m);
		if(f==null) {
			return ResponseEntity.noContent().build();
		}
		//URI location =ServletUriComponentsBuilder.fromCurrentRequest().path("/{stationNumber}").buildAndExpand(f.getStation()).toUri();
		return ResponseEntity.accepted().build();
	}
	@DeleteMapping(value="/delete")
	public ResponseEntity<Void> supprCaserne(HttpServletRequest request){
		String adress = request.getParameter("adress") != null
				&& !request.getParameter("adress").isEmpty() ? request.getParameter("adress")
						: null;
				String nbStation = request.getParameter("nbStation") != null
						&& !request.getParameter("nbStation").isEmpty() ? request.getParameter("nbStation")
								: null;
		this.casernService.deletStation(adress, nbStation);
		return ResponseEntity.accepted().build();
	}
	@GetMapping
	@ResponseBody
	public HashMap<String,HashSet<FireStation>> listeOfCaserne(){
		return this.casernService.listeCaserne();
	}
}
