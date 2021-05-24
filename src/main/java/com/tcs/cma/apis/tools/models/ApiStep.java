package com.tcs.cma.apis.tools.models;

import java.io.Serializable;

/**
 * 
 * @author Manish Srivastava
 * 17 Sep 2019
 */
public class ApiStep implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -9187869487470695831L;

	/**
	 * 
	 */
	private String apiStepName;
	
	/**
	 * 	
	 * @param apiStepName
	 */
	public ApiStep(String apiStepName) {
		this.apiStepName = apiStepName;
	}

	/**
	 * @return the apiStepName
	 */
	public String getApiStepName() {
		return apiStepName;
	}

	/**
	 * @param apiStepName the apiStepName to set
	 */
	public void setApiStepName(String apiStepName) {
		this.apiStepName = apiStepName;
	}
	
	
	
}
