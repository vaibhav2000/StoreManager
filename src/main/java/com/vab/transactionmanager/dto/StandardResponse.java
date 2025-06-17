package com.vab.transactionmanager.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

public class StandardResponse {
    private String message;

	public StandardResponse(String message) {
		super();
		this.message = message;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}
    
}
