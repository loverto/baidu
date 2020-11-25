package org.ylf.repository;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Restriction{

	@JsonProperty("type")
	private String type;

	@JsonProperty("info")
	private String info;

	public void setType(String type){
		this.type = type;
	}

	public String getType(){
		return type;
	}

	public void setInfo(String info){
		this.info = info;
	}

	public String getInfo(){
		return info;
	}

	@Override
 	public String toString(){
		return
			"Restriction{" +
			"type = '" + type + '\'' +
			",info = '" + info + '\'' +
			"}";
		}
}
