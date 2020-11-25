package org.ylf.repository;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public class Result{

	@JsonProperty("routes")
	private List<RoutesItem> routes;

	@JsonProperty("total")
	private int total;

	@JsonProperty("routesinfo_ext")
	private String routesinfoExt;

	@JsonProperty("restriction")
	private Restriction restriction;

	public void setRoutes(List<RoutesItem> routes){
		this.routes = routes;
	}

	public List<RoutesItem> getRoutes(){
		return routes;
	}

	public void setTotal(int total){
		this.total = total;
	}

	public int getTotal(){
		return total;
	}

	public void setRoutesinfoExt(String routesinfoExt){
		this.routesinfoExt = routesinfoExt;
	}

	public String getRoutesinfoExt(){
		return routesinfoExt;
	}

	public void setRestriction(Restriction restriction){
		this.restriction = restriction;
	}

	public Restriction getRestriction(){
		return restriction;
	}

	@Override
 	public String toString(){
		return
			"Result{" +
			"routes = '" + routes + '\'' +
			",total = '" + total + '\'' +
			",routesinfo_ext = '" + routesinfoExt + '\'' +
			",restriction = '" + restriction + '\'' +
			"}";
		}
}
