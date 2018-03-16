package com.example.lenovo.medinfras.pojo;

import java.util.List;
import com.google.gson.annotations.SerializedName;

public class ResponsePatientList{

	@SerializedName("patient")
	private List<PatientItem> patient;

	public void setPatient(List<PatientItem> patient){
		this.patient = patient;
	}

	public List<PatientItem> getPatient(){
		return patient;
	}

	@Override
 	public String toString(){
		return 
			"ResponsePatientList{" + 
			"patient = '" + patient + '\'' + 
			"}";
		}
}