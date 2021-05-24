package com.tcs.cma.apis.tools.models;

import java.io.Serializable;

/**
 * 
 * @author Manish Srivastava
 * 17 Sep 2019
 */
public class ApiMethod implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 7988864344805490442L;
	
	/**
	 * 
	 */
	private String apiProxyMethod;
	
	/**
	 * 
	 */
	private String apiProxyDesc;

	/**
	 * @return the apiProxyMethod
	 */
	public String getApiProxyMethod() {
		return apiProxyMethod;
	}

	/**
	 * @param apiProxyMethod the apiProxyMethod to set
	 */
	public void setApiProxyMethod(String apiProxyMethod) {
		this.apiProxyMethod = apiProxyMethod;
	}

	/**
	 * @return the apiProxyDesc
	 */
	public String getApiProxyDesc() {
		return apiProxyDesc;
	}

	/**
	 * @param apiProxyDesc the apiProxyDesc to set
	 */
	public void setApiProxyDesc(String apiProxyDesc) {
		this.apiProxyDesc = apiProxyDesc;
	}
	
	
}
