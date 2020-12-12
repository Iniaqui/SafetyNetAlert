package com.safetyNetAlerts.models;

import java.util.ArrayList;

public class MedicalRecord {

	ArrayList<String> medications;
	ArrayList<String> allergies;

	public MedicalRecord() {

	}

	public MedicalRecord(ArrayList<String> medication, ArrayList<String> Allergies) {
		this.allergies = Allergies;
		this.medications = medication;
	}

	public ArrayList<String> getMedications() {
		return medications;
	}

	public void setMedications(ArrayList<String> medications) {
		this.medications = medications;
	}

	public ArrayList<String> getAllergies() {
		return allergies;
	}

	public void setAllergies(ArrayList<String> allergies) {
		this.allergies = allergies;
	}

}
