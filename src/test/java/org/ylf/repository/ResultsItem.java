package org.ylf.repository;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class ResultsItem{

	@JsonProperty("area")
	private String area;

	@JsonProperty("uid")
	private String uid;

	@JsonProperty("address")
	private String address;

	@JsonProperty("province")
	private String province;

	@JsonProperty("city")
	private String city;

	@JsonProperty("name")
	private String name;

	@JsonProperty("location")
	private Location location;

	@JsonProperty("telephone")
	private String telephone;

	@JsonProperty("detail")
	private int detail;

	public void setArea(String area){
		this.area = area;
	}

	public String getArea(){
		return area;
	}

	public void setUid(String uid){
		this.uid = uid;
	}

	public String getUid(){
		return uid;
	}

	public void setAddress(String address){
		this.address = address;
	}

	public String getAddress(){
		return address;
	}

	public void setProvince(String province){
		this.province = province;
	}

	public String getProvince(){
		return province;
	}

	public void setCity(String city){
		this.city = city;
	}

	public String getCity(){
		return city;
	}

	public void setName(String name){
		this.name = name;
	}

	public String getName(){
		return name;
	}

	public void setLocation(Location location){
		this.location = location;
	}

	public Location getLocation(){
		return location;
	}

	public void setTelephone(String telephone){
		this.telephone = telephone;
	}

	public String getTelephone(){
		return telephone;
	}

	public void setDetail(int detail){
		this.detail = detail;
	}

	public int getDetail(){
		return detail;
	}

	@Override
 	public String toString(){
		return
			"ResultsItem{" +
			"area = '" + area + '\'' +
			",uid = '" + uid + '\'' +
			",address = '" + address + '\'' +
			",province = '" + province + '\'' +
			",city = '" + city + '\'' +
			",name = '" + name + '\'' +
			",location = '" + location + '\'' +
			",telephone = '" + telephone + '\'' +
			",detail = '" + detail + '\'' +
			"}";
		}
}
