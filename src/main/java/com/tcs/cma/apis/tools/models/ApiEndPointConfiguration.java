package com.tcs.cma.apis.tools.models;

import java.io.Serializable;

/**
 * 
 * @author Manish Srivastava
 * 17 Sep 2019
 */
public abstract class ApiEndPointConfiguration implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -322005202140735839L;

	/**
	 * 
	 */
	private String apiDefaultFaultRule;
	
	/**
	 * 
	 */
	private String apiPreFlowRequest;
	
	/**
	 * 
	 */
	private String apiPreFlowResponse;
	
	/**
	 * 
	 */
	private String apiPostFlowRequest;
	
	/**
	 * 
	 */
	private String apiPostFlowResponse;

	/**
	 * @return the apiDefaultFaultRule
	 */
	public String getApiDefaultFaultRule() {
		return apiDefaultFaultRule;
	}

	/**
	 * @param apiDefaultFaultRule the apiDefaultFaultRule to set
	 */
	public void setApiDefaultFaultRule(String apiDefaultFaultRule) {
		this.apiDefaultFaultRule = apiDefaultFaultRule;
	}

	/**
	 * @return the apiPreFlowRequest
	 */
	public String getApiPreFlowRequest() {
		return apiPreFlowRequest;
	}

	/**
	 * @param apiPreFlowRequest the apiPreFlowRequest to set
	 */
	public void setApiPreFlowRequest(String apiPreFlowRequest) {
		this.apiPreFlowRequest = apiPreFlowRequest;
	}

	/**
	 * @return the apiPreFlowResponse
	 */
	public String getApiPreFlowResponse() {
		return apiPreFlowResponse;
	}

	/**
	 * @param apiPreFlowResponse the apiPreFlowResponse to set
	 */
	public void setApiPreFlowResponse(String apiPreFlowResponse) {
		this.apiPreFlowResponse = apiPreFlowResponse;
	}

	/**
	 * @return the apiPostFlowRequest
	 */
	public String getApiPostFlowRequest() {
		return apiPostFlowRequest;
	}

	/**
	 * @param apiPostFlowRequest the apiPostFlowRequest to set
	 */
	public void setApiPostFlowRequest(String apiPostFlowRequest) {
		this.apiPostFlowRequest = apiPostFlowRequest;
	}

	/**
	 * @return the apiPostFlowResponse
	 */
	public String getApiPostFlowResponse() {
		return apiPostFlowResponse;
	}

	/**
	 * @param apiPostFlowResponse the apiPostFlowResponse to set
	 */
	public void setApiPostFlowResponse(String apiPostFlowResponse) {
		this.apiPostFlowResponse = apiPostFlowResponse;
	}
	
	
}
