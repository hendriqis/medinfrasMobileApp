package com.example.lenovo.medinfras.pojo;

import java.util.List;
import com.google.gson.annotations.SerializedName;

public class PatientItem{

	@SerializedName("birthday")
	private String birthday;

	@SerializedName("image")
	private String image;

	@SerializedName("gender")
	private String gender;

	@SerializedName("name")
	private String name;

	@SerializedName("mrn")
	private String mrn;

	@SerializedName("id")
	private int id;

	@SerializedName("drug")
	private List<DrugItem> drug;

	public void setBirthday(String birthday){
		this.birthday = birthday;
	}

	public String getBirthday(){
		return birthday;
	}

	public void setImage(String image){
		this.image = image;
	}

	public String getImage(){
		return image;
	}

	public void setGender(String gender){
		this.gender = gender;
	}

	public String getGender(){
		return gender;
	}

	public void setName(String name){
		this.name = name;
	}

	public String getName(){
		return name;
	}

	public void setMrn(String mrn){
		this.mrn = mrn;
	}

	public String getMrn(){
		return mrn;
	}

	public void setId(int id){
		this.id = id;
	}

	public int getId(){
		return id;
	}

	public void setDrug(List<DrugItem> drug){
		this.drug = drug;
	}

	public List<DrugItem> getDrug(){
		return drug;
	}

	@Override
 	public String toString(){
		return 
			"PatientItem{" + 
			"birthday = '" + birthday + '\'' + 
			",image = '" + image + '\'' + 
			",gender = '" + gender + '\'' + 
			",name = '" + name + '\'' + 
			",mrn = '" + mrn + '\'' + 
			",id = '" + id + '\'' + 
			",drug = '" + drug + '\'' + 
			"}";
		}
}