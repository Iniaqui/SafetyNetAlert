package com.safetyNetAlerts.SpringBootApplication.controllers;

import java.net.URI;
import java.util.HashMap;

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
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.safetyNetAlerts.SpringBootApplication.services.MedicalRecordService;
import com.safetyNetAlerts.models.MedicalRecord;
import com.safetyNetAlerts.models.MedicalRecordModelUpdate;
@RestController
@RequestMapping(value="/medicalRecord")
public class ControllerMedicalRecord {
	@Autowired
	MedicalRecordService medicalRecordService;
	@PostMapping(value="/add")
	public ResponseEntity<Void> ajoutMedicalRecord(@RequestBody MedicalRecord medicalRecord,HttpServletRequest request){
		String firstName = request.getParameter("firstName") != null
				&& !request.getParameter("firstName").isEmpty() ? request.getParameter("firstName")
						: null;
		String lastName = request.getParameter("lastName") != null
				&& !request.getParameter("lastName").isEmpty() ? request.getParameter("lastName")
						: null;
		MedicalRecord m = this.medicalRecordService.ajoutMedicalRecord(medicalRecord,firstName,lastName);
		if(m==null) {
			return ResponseEntity.noContent().build();
		}
		URI location =ServletUriComponentsBuilder.fromCurrentRequest().path("/{firstName}").buildAndExpand(firstName).toUri();
		return ResponseEntity.created(location).build();
		
	}
	@PutMapping(value="/update")
	public ResponseEntity<Void> updateMedicalRecord(@RequestBody MedicalRecordModelUpdate  medicalRecordModelUpdate,HttpServletRequest request){
		String firstName = request.getParameter("firstName") != null
				&& !request.getParameter("firstName").isEmpty() ? request.getParameter("firstName")
						: null;
		String lastName = request.getParameter("lastName") != null
				&& !request.getParameter("lastName").isEmpty() ? request.getParameter("lastName")
						: null;
		MedicalRecord m= this.medicalRecordService.modfierMedicalRecord(firstName,lastName,medicalRecordModelUpdate);
		if(m==null) {
			return ResponseEntity.noContent().build();
		}
		return ResponseEntity.accepted().build();
	}
	@DeleteMapping(value="/delete")
	public ResponseEntity<Void> supprMedicalRecord(HttpServletRequest request){
		String firstName = request.getParameter("firstName") != null
				&& !request.getParameter("firstName").isEmpty() ? request.getParameter("firstName")
						: null;
		String lastName = request.getParameter("lastName") != null
				&& !request.getParameter("lastName").isEmpty() ? request.getParameter("lastName")
						: null;
		this.medicalRecordService.deletMedicalRecord(firstName, lastName);
		return ResponseEntity.accepted().build();
	}
	@GetMapping
	@ResponseBody
	public HashMap<String,MedicalRecord> listeMedicalRecord(){
		return this.medicalRecordService.listeMedicalRecord();
	}
}
