package com.tcs.cma.apis.tools.models;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * 
 * @author Manish Srivastava
 * 17 Sep 2019
 */
public class ApiResource implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -696750621394326950L;
	
	/**
	 * 
	 */
	private String apiEndPoint;
	
	/**
	 * 
	 */
	private List<ApiMethod> apiMethods;
	
	/**
	 * 
	 */
	private String apiHttpVerb;
	
	/**
	 * 
	 */
	private List<String> apiQueryParameters;
	
	/**
	 * 
	 */
	private String apiFlowName;
	
	/**
	 * 
	 */
	private String apiFlowDesc;
	
	/**
	 * 
	 */
	private List<ApiParameter> apiParameterModelList;

	/**
	 * @return the apiEndPoint
	 */
	public String getApiEndPoint() {
		return apiEndPoint;
	}

	/**
	 * @param apiEndPoint the apiEndPoint to set
	 */
	public void setApiEndPoint(String apiEndPoint) {
		this.apiEndPoint = apiEndPoint;
	}

	/**
	 * @return the apiMethods
	 */
	public List<ApiMethod> getApiMethods() {
		return apiMethods;
	}

	/**
	 * @param apiMethods the apiMethods to set
	 */
	public void setApiMethods(List<ApiMethod> apiMethods) {
		this.apiMethods = apiMethods;
	}

	/**
	 * @return the apiHttpVerb
	 */
	public String getApiHttpVerb() {
		return apiHttpVerb;
	}

	/**
	 * @param apiHttpVerb the apiHttpVerb to set
	 */
	public void setApiHttpVerb(String apiHttpVerb) {
		this.apiHttpVerb = apiHttpVerb;
	}

	/**
	 * @return the apiQueryParameters
	 */
	public List<String> getApiQueryParameters() {
		return apiQueryParameters;
	}

	/**
	 * @param apiQueryParameters the apiQueryParameters to set
	 */
	public void setApiQueryParameters(List<String> apiQueryParameters) {
		this.apiQueryParameters = apiQueryParameters;
	}

	/**
	 * @return the apiFlowName
	 */
	public String getApiFlowName() {
		return apiFlowName;
	}

	/**
	 * @param apiFlowName the apiFlowName to set
	 */
	public void setApiFlowName(String apiFlowName) {
		this.apiFlowName = apiFlowName;
	}

	/**
	 * @return the apiFlowDesc
	 */
	public String getApiFlowDesc() {
		return apiFlowDesc;
	}

	/**
	 * @param apiFlowDesc the apiFlowDesc to set
	 */
	public void setApiFlowDesc(String apiFlowDesc) {
		this.apiFlowDesc = apiFlowDesc;
	}

	/**
	 * @return the apiParameterModelList
	 */
	public List<ApiParameter> getApiParameterModelList() {
		return apiParameterModelList;
	}

	/**
	 * @param apiParameterModelList the apiParameterModelList to set
	 */
	public void setApiParameterModelList(List<ApiParameter> apiParameterModelList) {
		this.apiParameterModelList = apiParameterModelList;
	}
	
	/**
	 * 
	 */
	public ApiResource() {
		this.apiQueryParameters = new ArrayList<String>();
	}
}
