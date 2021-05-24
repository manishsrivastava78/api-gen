package com.tcs.cma.apis.tools.models;

import java.io.Serializable;

/**
 * 
 * @author Manish Srivastava
 * 17 Sep 2019
 */
public class ApiJavaScript implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 3389714541665486767L;
	
	/**
	 * 
	 */
	private String apiParameterNameInCaps;
	
	/**
	 * 
	 */
	private String apiParameterName;
	
	/**
	 * 
	 * 
	 */
	private String apiParameterList;

	
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
	 * @return the apiParameterList
	 */
	public String getApiParameterList() {
		return apiParameterList;
	}

	/**
	 * @param apiParameterList the apiParameterList to set
	 */
	public void setApiParameterList(String apiParameterList) {
		this.apiParameterList = apiParameterList;
	}
	
	
}
