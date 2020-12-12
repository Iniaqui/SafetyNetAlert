package com.safetyNetAlerts.SpringBootApplication;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.Before;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultHandler;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.safetyNetAlerts.SpringBootApplication.services.FireStationsService;
//@WebAppConfiguration
@SpringBootTest
//@ContextConfiguration(classes = {HandlerJsonFile.class})
@RunWith(SpringRunner.class)
@AutoConfigureMockMvc 
class SafetyNetAlertsApplicationTests {
	@Autowired
	private MockMvc mockMvc;
	@Before
	public void setUp() {
		
		//final Authentication authentication =  new TestingAuthenticationToken("celine.gilet", "netapsys");
		//this.mockMvc = MockMvcBuilders.webAppContextSetup(this.wac).build();
		 
	}
	@Test
	void testFireStationService() throws Exception {
		MockHttpServletRequestBuilder req =get("/firestation?stationNumber=3").contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON_UTF8);
		this.mockMvc.perform(req).andExpect(status().isOk());
	}
	@Test
	void testlisteOfChildInThisAdress() throws Exception {
		MockHttpServletRequestBuilder req =get("/childAlert?address=1509 Culver St").contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON_UTF8);
		this.mockMvc.perform(req).andExpect(status().isOk());
	}
	@Test
	void testlistOfTelephoneNumber() throws Exception {
		MockHttpServletRequestBuilder req =get("/phoneAlert?fireStation=3").contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON_UTF8);
		this.mockMvc.perform(req).andExpect(status().isOk());
	}
	@Test
	void testlisteOfFire() throws Exception {
		MockHttpServletRequestBuilder req =get("/fire?address=1509 Culver St").contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON_UTF8);
		this.mockMvc.perform(req).andExpect(status().isOk());
	}
	@Test
	void testlisteOfPersonCoverByStationAndMedicalRecord() throws Exception {
		MockHttpServletRequestBuilder req =get("/flood/stations?stations=3").contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON_UTF8);
		this.mockMvc.perform(req).andExpect(status().isOk());
	}
	@Test
	void testlistePersonInfo() throws Exception {
		MockHttpServletRequestBuilder req =get("/personInfo?firstName=John&&lastName=Boyd").contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON_UTF8);
		this.mockMvc.perform(req).andExpect(status().isOk());
	}
	@Test
	void testlisteOfEveryBodyMail() throws Exception {
		MockHttpServletRequestBuilder req =get("/communityEmail?city=Culver").contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON_UTF8);
		this.mockMvc.perform(req).andExpect(status().isOk());
	}
	@Test
	void testajoutPerson() throws Exception {
		String person = "{ \"lastName\": \"Paul\",\n"
				+ "     \"firstName\": \"DUPONT\",\n"
				+ "    \"phone\": \"888-666-448-669\",\n"
				+ "    \"mail\": \"pauldupont@gmail.com\",\n"
				+ "    \"lieu\": {\n"
				+ "        \"adress\": \"1509 François St\",\n"
				+ "        \"city\": \"Culver\",\n"
				+ "        \"zip\": \"96000\"\n"
				+ "    },\n"
				+ "    \"birthday\": \"10/07/2002\"\n"
				+ "}";
		MockHttpServletRequestBuilder req =post("/person").contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON_UTF8).content(person);
		this.mockMvc.perform(req).andExpect(status().isCreated());
	}
	@Test
	void testajoutCasern() throws Exception {
		String lieu ="{\n"
				+ "            \"lieu\": {\n"
				+ "                \"adress\": \"748 Townings Dr\",\n"
				+ "                \"city\": \"Culver\",\n"
				+ "                \"zip\": \"97451\"\n"
				+ "            },\n"
				+ "            \"station\": \"22\"\n"
				+ "        }";
		MockHttpServletRequestBuilder req =post("/fireStation").contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON_UTF8).content(lieu);
		this.mockMvc.perform(req).andExpect(status().isCreated());
	}
	@Test
	void testajoutMedicalRecord() throws Exception {
		String medicalRecord ="{\n"
				+ "        \"person\":{\n"
				+ "        \"firstName\": \"DUPONT\",\n"
				+ "        \"lastName\": \"Paul\",\n"
				+ "        \"phone\": \"841-841-874\",\n"
				+ "        \"mail\": \"dupontpaul@email.com\",\n"
				+ "        \"lieu\": {\n"
				+ "            \"adress\": \"1500 Culver St\",\n"
				+ "            \"city\": \"Culver\",\n"
				+ "            \"zip\": \"97451\"\n"
				+ "        },\n"
				+ "        \"birthday\": \"10/07/2000\"\n"
				+ "    },\n"
				+ "        \"medications\": [\n"
				+ "            \"hydrapermazol:300mg\",\n"
				+ "            \"dodoxadin:30mg\"\n"
				+ "        ],\n"
				+ "        \"allergies\": [\n"
				+ "            \"shellfish\"\n"
				+ "        ]\n"
				+ "    }";
		MockHttpServletRequestBuilder req =post("/medicalRecord?firstName=John&&lastName=Boyd").contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON_UTF8).content(medicalRecord);
		this.mockMvc.perform(req).andExpect(status().isCreated());
	}
	@Test
	void testmodfierPerson() throws Exception {
		String personDetailRequestModel ="{\n"
				+ "    \"phone\": \"0618461306\",\n"
				+ "    \"mail\": \"paul.dupont.22@gmail.fr\",\n"
				+ "    \"lieu\": {\n"
				+ "        \"adress\": \"1509 Culver St\",\n"
				+ "        \"city\": \"Culver\",\n"
				+ "        \"zip\": \"97451\"\n"
				+ "    },\n"
				+ "    \"birthday\": \"10/07/2002\"\n"
				+ "}";
		MockHttpServletRequestBuilder req =put("/person?firstName=John&&lastName=Boyd").contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON_UTF8).content(personDetailRequestModel);
		this.mockMvc.perform(req).andExpect(status().isCreated());
	}

	@Test
	void testupdateMedicalRecord() throws Exception {
		String MedicalRecordModelUpdate =" {\n"
				+ "        \n"
				+ "        \"medications\": [],\n"
				+ "        \"allergies\": []\n"
				+ "    }";
		MockHttpServletRequestBuilder req =put("/medicalRecord?firstName=John&&lastName=Boyd").contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON_UTF8).content(MedicalRecordModelUpdate);
		this.mockMvc.perform(req).andExpect(status().isAccepted());
	}
	@Test
	void testsupprPerson() throws Exception {
		
		MockHttpServletRequestBuilder req =delete("/person?firstName=John&&lastName=Boyd").contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON_UTF8);
		this.mockMvc.perform(req).andExpect(status().isAccepted());
	}
	@Test
	void testsupprCaserne() throws Exception {
		MockHttpServletRequestBuilder req =delete("/fireStation?adress=1509 Culver St&&nbStation=3").contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON_UTF8);
		this.mockMvc.perform(req).andExpect(status().isAccepted());
	}
	@Test
	void testupdateCaserne() throws Exception {
		String ModelFireStationUpdate ="{\n"
				+ "            \"lieu\": {\n"
				+ "                \"adress\": \"748 Townings Dr\",\n"
				+ "                \"city\": \"Culver\",\n"
				+ "                \"zip\": \"97451\"\n"
				+ "            },\n"
				+ "            \"station\": \"3\",\n"
				+ "            \"newNumber\" : \"34\"\n"
				+ "        }";
		MockHttpServletRequestBuilder req =put("/fireStation").contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON_UTF8).content(ModelFireStationUpdate);
		this.mockMvc.perform(req).andExpect(status().isAccepted());
	}
}
