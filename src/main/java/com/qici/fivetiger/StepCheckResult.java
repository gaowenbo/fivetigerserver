package com.qici.fivetiger;

public class StepCheckResult {
	public Boolean isLegal;
	public String message;
	
	public StepCheckResult(Boolean isLegal, String message) {
		this.isLegal = isLegal;
		this.message = message;
	}
	public Boolean getIsLegal() {
		return isLegal;
	}
	public void setIsLegal(Boolean isLegal) {
		this.isLegal = isLegal;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
}
