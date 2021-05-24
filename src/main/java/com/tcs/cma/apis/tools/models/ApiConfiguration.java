package com.tcs.cma.apis.tools.models;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * 
 * @author Manish Srivastava
 * 17 Sep 2019
 */
public class ApiConfiguration implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -7831040662950826234L;
	
	/**
	 * 
	 */
	private String apiProxyName;
	
	/**
	 * 
	 */
	private String apiProxyDesc;
	
	/**
	 * 
	 */
	private String apiProxyBackendUrl;
	
	/**
	 * 
	 */
	private List<ApiEndPoint> apiProxyEndPoints = new ArrayList<>();

	/**
	 * @return the apiProxyName
	 */
	public String getApiProxyName() {
		return apiProxyName;
	}

	/**
	 * @param apiProxyName the apiProxyName to set
	 */
	public void setApiProxyName(String apiProxyName) {
		this.apiProxyName = apiProxyName;
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

	/**
	 * @return the apiProxyBackendUrl
	 */
	public String getApiProxyBackendUrl() {
		return apiProxyBackendUrl;
	}

	/**
	 * @param apiProxyBackendUrl the apiProxyBackendUrl to set
	 */
	public void setApiProxyBackendUrl(String apiProxyBackendUrl) {
		this.apiProxyBackendUrl = apiProxyBackendUrl;
	}

	/**
	 * @return the apiProxyEndPoints
	 */
	public List<ApiEndPoint> getApiProxyEndPoints() {
		return apiProxyEndPoints;
	}

	/**
	 * @param apiProxyEndPoints the apiProxyEndPoints to set
	 */
	public void setApiProxyEndPoints(List<ApiEndPoint> apiProxyEndPoints) {
		this.apiProxyEndPoints = apiProxyEndPoints;
	}
	
	
	
	
}
