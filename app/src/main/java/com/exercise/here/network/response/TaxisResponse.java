package com.exercise.here.network.response;

import com.exercise.here.data.Taxi;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class TaxisResponse {

	@SerializedName("taxis")
	private List<Taxi> taxis;

	public void setTaxis(List<Taxi> taxis){
		this.taxis = taxis;
	}

	public List<Taxi> getTaxis(){
		return taxis;
	}

	@Override
 	public String toString(){
		return 
			"TaxisResponse{" +
			"taxis = '" + taxis + '\'' + 
			"}";
		}
}