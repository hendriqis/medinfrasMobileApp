package com.example.lenovo.medinfras.pojo;

import java.util.List;
import com.google.gson.annotations.SerializedName;

public class DrugListResponse{

	@SerializedName("drug")
	private List<DrugItem> drug;

	public void setDrug(List<DrugItem> drug){
		this.drug = drug;
	}

	public List<DrugItem> getDrug(){
		return drug;
	}

	@Override
 	public String toString(){
		return 
			"DrugListResponse{" + 
			"drug = '" + drug + '\'' + 
			"}";
		}
}