package com.myProject.rest.webservices.exception;

import java.util.Date;
//Custom Exception class
public class ExceptionResponse {
	
	private Date timeStamp;
	private String customMessage;
	private String details;
	public ExceptionResponse(Date time, String message, String details) {
		super();
		this.timeStamp = time;
		this.customMessage = message;
		this.details = details;
	}
	public Date getTime() {
		return timeStamp;
	}

	public String getMessage() {
		return customMessage;
	}

	public String getDetails() {
		return details;
	}


}
