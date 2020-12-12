package com.safetyNetAlerts.models;

public class ModelFireStationUpdate {
	Lieu lieu;
	String station;
	String newNumber;
	public String getNewNumber() {
		return newNumber;
	}
	public ModelFireStationUpdate() {
		
	}
	public ModelFireStationUpdate(Lieu l,String s,String n) {
		this.lieu=l;
		this.newNumber=n;
		this.station=s;
	}
	public Lieu getLieu() {
		return lieu;
	}
	public String getStation() {
		return station;
	}
}
