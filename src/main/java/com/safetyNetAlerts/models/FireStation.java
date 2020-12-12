package com.safetyNetAlerts.models;

public class FireStation {
	Lieu lieu;
	String station;
	
	public FireStation(String station,Lieu lieu) {
		this.station=station;
		this.lieu=lieu;
	}
	public FireStation() {
		
	}
	public Lieu getLieu() {
		return lieu;
	}
	public void setLieu(Lieu lieu) {
		this.lieu = lieu;
	}
	public String getStation() {
		return station;
	}
	public void setStation(String station) {
		this.station = station;
	}
}
