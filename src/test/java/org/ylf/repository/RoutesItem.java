package org.ylf.repository;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public class RoutesItem{

	@JsonProperty("duration")
	private int duration;

	@JsonProperty("unavoid_polygons_idx")
	private String unavoidPolygonsIdx;

	@JsonProperty("distance")
	private double distance;

	@JsonProperty("origin")
	private Origin origin;

	@JsonProperty("destination")
	private Destination destination;

	@JsonProperty("toll_distance")
	private double tollDistance;

	@JsonProperty("tag")
	private String tag;

	@JsonProperty("track_idx")
	private int trackIdx;

	@JsonProperty("toll")
	private int toll;

	@JsonProperty("steps")
	private List<StepsItem> steps;

	@JsonProperty("oil_cost")
	private double oilCost;

	public void setDuration(int duration){
		this.duration = duration;
	}

	public int getDuration(){
		return duration;
	}

	public void setUnavoidPolygonsIdx(String unavoidPolygonsIdx){
		this.unavoidPolygonsIdx = unavoidPolygonsIdx;
	}

	public String getUnavoidPolygonsIdx(){
		return unavoidPolygonsIdx;
	}

	public void setDistance(double distance){
		this.distance = distance;
	}

	public double getDistance(){
		return distance;
	}

	public void setOrigin(Origin origin){
		this.origin = origin;
	}

	public Origin getOrigin(){
		return origin;
	}

	public void setDestination(Destination destination){
		this.destination = destination;
	}

	public Destination getDestination(){
		return destination;
	}

	public void setTollDistance(double tollDistance){
		this.tollDistance = tollDistance;
	}

	public double getTollDistance(){
		return tollDistance;
	}

	public void setTag(String tag){
		this.tag = tag;
	}

	public String getTag(){
		return tag;
	}

	public void setTrackIdx(int trackIdx){
		this.trackIdx = trackIdx;
	}

	public int getTrackIdx(){
		return trackIdx;
	}

	public void setToll(int toll){
		this.toll = toll;
	}

	public int getToll(){
		return toll;
	}

	public void setSteps(List<StepsItem> steps){
		this.steps = steps;
	}

	public List<StepsItem> getSteps(){
		return steps;
	}

	public void setOilCost(double oilCost){
		this.oilCost = oilCost;
	}

	public double getOilCost(){
		return oilCost;
	}

	@Override
 	public String toString(){
		return
			"RoutesItem{" +
			"duration = '" + duration + '\'' +
			",unavoid_polygons_idx = '" + unavoidPolygonsIdx + '\'' +
			",distance = '" + distance + '\'' +
			",origin = '" + origin + '\'' +
			",destination = '" + destination + '\'' +
			",toll_distance = '" + tollDistance + '\'' +
			",tag = '" + tag + '\'' +
			",track_idx = '" + trackIdx + '\'' +
			",toll = '" + toll + '\'' +
			",steps = '" + steps + '\'' +
			",oil_cost = '" + oilCost + '\'' +
			"}";
		}
}
