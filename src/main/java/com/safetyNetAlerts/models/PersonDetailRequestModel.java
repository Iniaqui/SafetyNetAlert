package com.safetyNetAlerts.models;

import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

public class PersonDetailRequestModel {
	String phone;
	String mail;
	Lieu lieu;
	DateTime birthday;
	public PersonDetailRequestModel() {
		
	}
	public PersonDetailRequestModel(String phone,String mail,Lieu l) {
		this.lieu=l;
		this.phone=phone;
		this.mail=mail;
	}
	public String getPhone() {
		return phone;
	}
	public String getMail() {
		return mail;
	}
	public Lieu getLieu() {
		return lieu;
	}
	public DateTime getBirthday() {
		return birthday;
	}
	public void setBirthday(String b) {
		DateTimeFormatter formatter = DateTimeFormat.forPattern("MM/dd/yyyy");
		DateTime dateTime = DateTime.parse(b, formatter);
		this.birthday = dateTime;
		System.out.println("Modification de la date reussi");
	}
}
