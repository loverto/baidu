package org.ylf.repository;

import com.fasterxml.jackson.annotation.JsonProperty;

public class TrafficConditionItem{

	@JsonProperty("distance")
	private double distance;

	@JsonProperty("status")
	private int status;

	@JsonProperty("geo_cnt")
	private int geoCnt;

	public void setDistance(double distance){
		this.distance = distance;
	}

	public double getDistance(){
		return distance;
	}

	public void setStatus(int status){
		this.status = status;
	}

	public int getStatus(){
		return status;
	}

	public void setGeoCnt(int geoCnt){
		this.geoCnt = geoCnt;
	}

	public int getGeoCnt(){
		return geoCnt;
	}

	@Override
 	public String toString(){
		return
			"TrafficConditionItem{" +
			"distance = '" + distance + '\'' +
			",status = '" + status + '\'' +
			",geo_cnt = '" + geoCnt + '\'' +
			"}";
		}
}
