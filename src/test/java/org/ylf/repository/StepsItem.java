package org.ylf.repository;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public class StepsItem{

	@JsonProperty("road_name")
	private String roadName;

	@JsonProperty("traffic_condition")
	private List<TrafficConditionItem> trafficCondition;

	@JsonProperty("distance")
	private double distance;

	@JsonProperty("toll_gate_location")
	private TollGateLocation tollGateLocation;

	@JsonProperty("restrictions")
	private List<RestrictionsItem> restrictions;

	@JsonProperty("toll_distance")
	private double tollDistance;

	@JsonProperty("adcodes")
	private String adcodes;

	@JsonProperty("leg_index")
	private int legIndex;

	@JsonProperty("duration")
	private int duration;

	@JsonProperty("start_location")
	private StartLocation startLocation;

	@JsonProperty("path")
	private String path;

	@JsonProperty("road_type")
	private int roadType;

	@JsonProperty("end_location")
	private EndLocation endLocation;

	@JsonProperty("direction")
	private int direction;

	@JsonProperty("toll_gate_name")
	private String tollGateName;

	public void setRoadName(String roadName){
		this.roadName = roadName;
	}

	public String getRoadName(){
		return roadName;
	}

	public void setTrafficCondition(List<TrafficConditionItem> trafficCondition){
		this.trafficCondition = trafficCondition;
	}

	public List<TrafficConditionItem> getTrafficCondition(){
		return trafficCondition;
	}

	public void setDistance(double distance){
		this.distance = distance;
	}

	public double getDistance(){
		return distance;
	}

	public void setTollGateLocation(TollGateLocation tollGateLocation){
		this.tollGateLocation = tollGateLocation;
	}

	public TollGateLocation getTollGateLocation(){
		return tollGateLocation;
	}

	public void setRestrictions(List<RestrictionsItem> restrictions){
		this.restrictions = restrictions;
	}

	public List<RestrictionsItem> getRestrictions(){
		return restrictions;
	}

	public void setTollDistance(double tollDistance){
		this.tollDistance = tollDistance;
	}

	public double getTollDistance(){
		return tollDistance;
	}

	public void setAdcodes(String adcodes){
		this.adcodes = adcodes;
	}

	public String getAdcodes(){
		return adcodes;
	}

	public void setLegIndex(int legIndex){
		this.legIndex = legIndex;
	}

	public int getLegIndex(){
		return legIndex;
	}

	public void setDuration(int duration){
		this.duration = duration;
	}

	public int getDuration(){
		return duration;
	}

	public void setStartLocation(StartLocation startLocation){
		this.startLocation = startLocation;
	}

	public StartLocation getStartLocation(){
		return startLocation;
	}

	public void setPath(String path){
		this.path = path;
	}

	public String getPath(){
		return path;
	}

	public void setRoadType(int roadType){
		this.roadType = roadType;
	}

	public int getRoadType(){
		return roadType;
	}

	public void setEndLocation(EndLocation endLocation){
		this.endLocation = endLocation;
	}

	public EndLocation getEndLocation(){
		return endLocation;
	}

	public void setDirection(int direction){
		this.direction = direction;
	}

	public int getDirection(){
		return direction;
	}

	public void setTollGateName(String tollGateName){
		this.tollGateName = tollGateName;
	}

	public String getTollGateName(){
		return tollGateName;
	}

	@Override
 	public String toString(){
		return
			"StepsItem{" +
			"road_name = '" + roadName + '\'' +
			",traffic_condition = '" + trafficCondition + '\'' +
			",distance = '" + distance + '\'' +
			",toll_gate_location = '" + tollGateLocation + '\'' +
			",restrictions = '" + restrictions + '\'' +
			",toll_distance = '" + tollDistance + '\'' +
			",adcodes = '" + adcodes + '\'' +
			",leg_index = '" + legIndex + '\'' +
			",duration = '" + duration + '\'' +
			",start_location = '" + startLocation + '\'' +
			",path = '" + path + '\'' +
			",road_type = '" + roadType + '\'' +
			",end_location = '" + endLocation + '\'' +
			",direction = '" + direction + '\'' +
			",toll_gate_name = '" + tollGateName + '\'' +
			"}";
		}
}
