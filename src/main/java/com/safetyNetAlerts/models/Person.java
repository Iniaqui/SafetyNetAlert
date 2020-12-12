package com.safetyNetAlerts.models;

import java.util.ArrayList;  

import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.json.simple.JSONObject;

import com.fasterxml.jackson.annotation.JsonIgnore;

public class Person {
	String firstName;
	String lastName;
	String phone;
	
	String mail;

	Lieu lieu;
	
	DateTime birthday;
	int age=10 ;
	MedicalRecord medicalRecord;
	
	public MedicalRecord getMedicalRecord() {
		return medicalRecord;
	}

	public void setMedicalRecord(MedicalRecord medicalRecord) {
		this.medicalRecord = medicalRecord;
	}

	public void setAge(int age) {
		this.age = age;
		
	}
	
	public Person(String firstName,String lastName,String phone,String mail) {
		this.firstName=firstName;
		this.lastName=lastName;
		this.mail=mail;
		this.phone=phone;
		System.out.println("Création de person dont le mail est "+mail);
	}
	public Person(String firstName,String lastName,String phone,String mail,Lieu lieu) {
		this.firstName=firstName;
		this.lastName=lastName;
		this.mail=mail;
		this.phone=phone;
		this.lieu=lieu;
		System.out.println("Création de person dont le mail est "+mail);
	}
	public Person() {
		
	}
	public DateTime returnBirthday() {
		return this.birthday;
	}
	public String getBirthday() {
		return birthday.toString();
	}
	public Lieu getLieu() {
		return lieu;
	}
	
	public void changeLieu(Lieu lieu) {
		this.lieu.changeLieu(lieu);
	}
	public void setBirthday(String b) {
		DateTimeFormatter formatter = DateTimeFormat.forPattern("MM/dd/yyyy");
		DateTime dateTime = DateTime.parse(b, formatter);
		this.birthday = dateTime;
		System.out.println("Modification de la date reussi");
	}
	public void setBirthday(DateTime d) {
		this.birthday=d;
	}
	public String getMail() {
		return mail;
	}
	public void setMail(String mail) {
		this.mail = mail;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public String getFirstName() {
		return firstName;
	}
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	public String getLastName() {
		return lastName;
	}
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	public DateTime returnDateIntoDateTime() {
		
		return this.birthday;
	}
	public int calculateAge() {
		DateTime tdy = new DateTime();
		return (tdy.getYear()) - (this.returnDateIntoDateTime().getYear());
		
	}
}
