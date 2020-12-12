package com.safetyNetAlerts.models;

public class Lieu {
	String adress;
	String city;
	String zip;
	
	public Lieu(String adress,String city, String zip) {
		this.adress=adress;
		this.city=city;
		this.zip=zip;
	}
	public Lieu() {
		
	}
	public String getAdress() {
		return adress;
	}
	public void setAdress(String adress) {
		this.adress = adress;
	}
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	public String getZip() {
		return zip;
	}
	public void setZip(String zip) {
		this.zip = zip;
	}
	public void changeLieu(Lieu lieu) {
		this.setAdress(lieu.adress);
		this.setCity(lieu.city);
		this.setZip(lieu.zip);
		
		
	}
}
