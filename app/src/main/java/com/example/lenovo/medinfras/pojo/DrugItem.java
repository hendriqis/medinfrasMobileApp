package com.example.lenovo.medinfras.pojo;

import com.google.gson.annotations.SerializedName;

public class DrugItem{

	@SerializedName("signa")
	private String signa;

	@SerializedName("route")
	private String route;

	@SerializedName("genericName")
	private String genericName;

	@SerializedName("drugIndex")
	private int drugIndex;

	@SerializedName("physician")
	private String physician;

	@SerializedName("startDate")
	private String startDate;

	public void setSigna(String signa){
		this.signa = signa;
	}

	public String getSigna(){
		return signa;
	}

	public void setRoute(String route){
		this.route = route;
	}

	public String getRoute(){
		return route;
	}

	public void setGenericName(String genericName){
		this.genericName = genericName;
	}

	public String getGenericName(){
		return genericName;
	}

	public void setDrugIndex(int drugIndex){
		this.drugIndex = drugIndex;
	}

	public int getDrugIndex(){
		return drugIndex;
	}

	public void setPhysician(String physician){
		this.physician = physician;
	}

	public String getPhysician(){
		return physician;
	}

	public void setStartDate(String startDate){
		this.startDate = startDate;
	}

	public String getStartDate(){
		return startDate;
	}

	@Override
 	public String toString(){
		return 
			"DrugItem{" + 
			"signa = '" + signa + '\'' + 
			",route = '" + route + '\'' + 
			",genericName = '" + genericName + '\'' + 
			",drugIndex = '" + drugIndex + '\'' + 
			",physician = '" + physician + '\'' + 
			",startDate = '" + startDate + '\'' + 
			"}";
		}
}