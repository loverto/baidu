package org.ylf.repository;

import com.fasterxml.jackson.annotation.JsonProperty;

public class BaiduTrunkRouterResponse {

	@JsonProperty("result")
	private Result result;

	@JsonProperty("message")
	private String message;

	@JsonProperty("status")
	private int status;

	public void setResult(Result result){
		this.result = result;
	}

	public Result getResult(){
		return result;
	}

	public void setMessage(String message){
		this.message = message;
	}

	public String getMessage(){
		return message;
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
			"BaiduTrunkRouterResponse{" +
			"result = '" + result + '\'' +
			",message = '" + message + '\'' +
			",status = '" + status + '\'' +
			"}";
		}
}
