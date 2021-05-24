package com.tcs.cma.apis.tools.models;

import java.io.Serializable;

/**
 * 
 * @author Manish Srivastava 17 Sep 2019
 */
public class ApiEndPoint implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1982566593327373267L;

	/**
	 * 
	 */
	private String apiProxyEndPoint;

	/**
	 * 
	 */
	private String apiProxyAllowedMethods;

	/**
	 * @return the apiProxyEndPoint
	 */
	public String getApiProxyEndPoint() {
		return apiProxyEndPoint;
	}

	/**
	 * @param apiProxyEndPoint the apiProxyEndPoint to set
	 */
	public void setApiProxyEndPoint(String apiProxyEndPoint) {
		this.apiProxyEndPoint = apiProxyEndPoint;
	}

	/**
	 * @return the apiProxyAllowedMethods
	 */
	public String getApiProxyAllowedMethods() {
		return apiProxyAllowedMethods;
	}

	/**
	 * @param apiProxyAllowedMethods the apiProxyAllowedMethods to set
	 */
	public void setApiProxyAllowedMethods(String apiProxyAllowedMethods) {
		this.apiProxyAllowedMethods = apiProxyAllowedMethods;
	}

	
}
