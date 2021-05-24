package com.tcs.cma.apis.tools.models;

import java.io.Serializable;
import java.util.List;

/**
 * 
 * @author Manish Srivastava
 * 17 Sep 2019
 */
public class ApiParameter implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 794363067494405586L;
	
	/**
	 * 
	 */
	private String apiParameterName;
	
	/**
	 * 
	 */
	private String apiParameterIn;
	
	/**
	 * 
	 */
	private boolean apiParameterRequired;
	
	/**
	 * 
	 */
	private String apiParameterType;
	
	/**
	 * 
	 */
	private List<String> apiParameterEnum; 
	
	/**
	 * 
	 */
	private String apiParameterNameInCaps;
	
	/**
	 * 
	 */
	private ApiJavaScript apiJsModel;

	/**
	 * @return the apiParameterName
	 */
	public String getApiParameterName() {
		return apiParameterName;
	}

	/**
	 * @param apiParameterName the apiParameterName to set
	 */
	public void setApiParameterName(String apiParameterName) {
		this.apiParameterName = apiParameterName;
	}

	/**
	 * @return the apiParameterIn
	 */
	public String getApiParameterIn() {
		return apiParameterIn;
	}

	/**
	 * @param apiParameterIn the apiParameterIn to set
	 */
	public void setApiParameterIn(String apiParameterIn) {
		this.apiParameterIn = apiParameterIn;
	}

	/**
	 * @return the apiParameterRequired
	 */
	public boolean isApiParameterRequired() {
		return apiParameterRequired;
	}

	/**
	 * @param apiParameterRequired the apiParameterRequired to set
	 */
	public void setApiParameterRequired(boolean apiParameterRequired) {
		this.apiParameterRequired = apiParameterRequired;
	}

	/**
	 * @return the apiParameterType
	 */
	public String getApiParameterType() {
		return apiParameterType;
	}

	/**
	 * @param apiParameterType the apiParameterType to set
	 */
	public void setApiParameterType(String apiParameterType) {
		this.apiParameterType = apiParameterType;
	}

	/**
	 * @return the apiParameterEnum
	 */
	public List<String> getApiParameterEnum() {
		return apiParameterEnum;
	}

	/**
	 * @param apiParameterEnum the apiParameterEnum to set
	 */
	public void setApiParameterEnum(List<String> apiParameterEnum) {
		this.apiParameterEnum = apiParameterEnum;
	}

	/**
	 * @return the apiParameterNameInCaps
	 */
	public String getApiParameterNameInCaps() {
		return apiParameterNameInCaps;
	}

	/**
	 * @param apiParameterNameInCaps the apiParameterNameInCaps to set
	 */
	public void setApiParameterNameInCaps(String apiParameterNameInCaps) {
		this.apiParameterNameInCaps = apiParameterNameInCaps;
	}

	/**
	 * @return the apiJsModel
	 */
	public ApiJavaScript getApiJsModel() {
		return apiJsModel;
	}

	/**
	 * @param apiJsModel the apiJsModel to set
	 */
	public void setApiJsModel(ApiJavaScript apiJsModel) {
		this.apiJsModel = apiJsModel;
	}
	
	
}
