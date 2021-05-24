package com.tcs.cma.apis.tools.models;

import java.io.Serializable;
import java.util.List;

/**
 * 
 * @author Manish Srivastava
 * 20 Sep 2019
 */
public abstract class ApiEndPointCommonPolicy implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -2261262593295491669L;
	
	private List<ApiStep> defaultFalutRule;
	
	private List<ApiStep> preFlowRequest;
	
	private List<ApiStep> preFlowResponse;
	
	private List<ApiStep> postFlowRequest;
	
	private List<ApiStep> postFlowResponse;

	/**
	 * @return the defaultFalutRule
	 */
	public List<ApiStep> getDefaultFalutRule() {
		return defaultFalutRule;
	}

	/**
	 * @param defaultFalutRule the defaultFalutRule to set
	 */
	public void setDefaultFalutRule(List<ApiStep> defaultFalutRule) {
		this.defaultFalutRule = defaultFalutRule;
	}

	/**
	 * @return the preFlowRequest
	 */
	public List<ApiStep> getPreFlowRequest() {
		return preFlowRequest;
	}

	/**
	 * @param preFlowRequest the preFlowRequest to set
	 */
	public void setPreFlowRequest(List<ApiStep> preFlowRequest) {
		this.preFlowRequest = preFlowRequest;
	}

	/**
	 * @return the preFlowResponse
	 */
	public List<ApiStep> getPreFlowResponse() {
		return preFlowResponse;
	}

	/**
	 * @param preFlowResponse the preFlowResponse to set
	 */
	public void setPreFlowResponse(List<ApiStep> preFlowResponse) {
		this.preFlowResponse = preFlowResponse;
	}

	/**
	 * @return the postFlowRequest
	 */
	public List<ApiStep> getPostFlowRequest() {
		return postFlowRequest;
	}

	/**
	 * @param postFlowRequest the postFlowRequest to set
	 */
	public void setPostFlowRequest(List<ApiStep> postFlowRequest) {
		this.postFlowRequest = postFlowRequest;
	}

	/**
	 * @return the postFlowResponse
	 */
	public List<ApiStep> getPostFlowResponse() {
		return postFlowResponse;
	}

	/**
	 * @param postFlowResponse the postFlowResponse to set
	 */
	public void setPostFlowResponse(List<ApiStep> postFlowResponse) {
		this.postFlowResponse = postFlowResponse;
	}
	
	
}
