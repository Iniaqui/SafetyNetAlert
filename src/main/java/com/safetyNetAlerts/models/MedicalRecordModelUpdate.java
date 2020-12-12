package com.safetyNetAlerts.models;

import java.util.ArrayList;

public class MedicalRecordModelUpdate {
	
	ArrayList<String> medications;
	ArrayList<String> allergies;
	public MedicalRecordModelUpdate() {
		
	}
	public MedicalRecordModelUpdate(ArrayList<String> medications, ArrayList<String> allergies) {
		this.allergies=allergies;
		this.medications = medications;
	}
	public ArrayList<String> getMedications() {
		return medications;
	}
	public ArrayList<String> getAllergies() {
		return allergies;
	}
}
