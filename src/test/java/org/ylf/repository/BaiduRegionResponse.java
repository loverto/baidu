package org.ylf.repository;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
@JsonIgnoreProperties(ignoreUnknown = true)
public class BaiduRegionResponse{

	@JsonProperty("result_type")
	private String resultType;

	@JsonProperty("message")
	private String message;

	@JsonProperty("results")
	private List<ResultsItem> results;

	@JsonProperty("status")
	private int status;

	public void setResultType(String resultType){
		this.resultType = resultType;
	}

	public String getResultType(){
		return resultType;
	}

	public void setMessage(String message){
		this.message = message;
	}

	public String getMessage(){
		return message;
	}

	public void setResults(List<ResultsItem> results){
		this.results = results;
	}

	public List<ResultsItem> getResults(){
		return results;
	}

	public void setStatus(int status){
		this.status = status;
	}

	public int getStatus(){
		return status;
	}

	@Override
 	public String toString(){
		return
			"BaiduRegionResponse{" +
			"result_type = '" + resultType + '\'' +
			",message = '" + message + '\'' +
			",results = '" + results + '\'' +
			",status = '" + status + '\'' +
			"}";
		}
}
