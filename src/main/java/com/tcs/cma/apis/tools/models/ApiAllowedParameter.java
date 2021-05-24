package com.tcs.cma.apis.tools.models;

import java.io.Serializable;

/**
 * 
 * @author Manish Srivastava
 * 18 Sep 2019
 */
public class ApiAllowedParameter implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 4835768814549258049L;
	
	/**
	 * 
	 */
	private String apiProxyFlowName;
	
	/**
	 * 
	 */
	private String apiProxyParameterList;

	/**
	 * @return the apiProxyFlowName
	 */
	public String getApiProxyFlowName() {
		return apiProxyFlowName;
	}

	/**
	 * @param apiProxyFlowName the apiProxyFlowName to set
	 */
	public void setApiProxyFlowName(String apiProxyFlowName) {
		this.apiProxyFlowName = apiProxyFlowName;
	}

	/**
	 * @return the apiProxyParameterList
	 */
	public String getApiProxyParameterList() {
		return apiProxyParameterList;
	}

	/**
	 * @param apiProxyParameterList the apiProxyParameterList to set
	 */
	public void setApiProxyParameterList(String apiProxyParameterList) {
		this.apiProxyParameterList = apiProxyParameterList;
	}
	
	
	
}
