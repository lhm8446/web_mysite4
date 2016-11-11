package com.bit2016.dto;

@SuppressWarnings("unused")
public class JSONResult {

	private String result;	// "success" or "fail"
	private String message; // result가 "fail" 일 때 원인 
	private Object data;	// result가 "success" 일 때 전달해야 할 데이터 객체  
	
	public JSONResult(String message){
		result = "fail";
		this.message = message;
	}
	
	public JSONResult(Object data){
		result = "success";
		this.data = data;
	}
}
