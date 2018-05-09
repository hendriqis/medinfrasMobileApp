package com.example.lenovo.medinfras.pojo;

import com.google.gson.annotations.SerializedName;

public class DrugItem{

	@SerializedName("route")
	private String route;

	@SerializedName("name")
	private String name;

	@SerializedName("physician")
	private String physician;

	@SerializedName("id")
	private int id;

	@SerializedName("generic")
	private String generic;

	public void setRoute(String route){
		this.route = route;
	}

	public String getRoute(){
		return route;
	}

	public void setName(String name){
		this.name = name;
	}

	public String getName(){
		return name;
	}

	public void setPhysician(String physician){
		this.physician = physician;
	}

	public String getPhysician(){
		return physician;
	}

	public void setId(int id){
		this.id = id;
	}

	public int getId(){
		return id;
	}

	public void setGeneric(String generic){
		this.generic = generic;
	}

	public String getGeneric(){
		return generic;
	}

	@Override
 	public String toString(){
		return 
			"DrugItem{" + 
			"route = '" + route + '\'' + 
			",name = '" + name + '\'' + 
			",physician = '" + physician + '\'' + 
			",id = '" + id + '\'' + 
			",generic = '" + generic + '\'' + 
			"}";
		}
}