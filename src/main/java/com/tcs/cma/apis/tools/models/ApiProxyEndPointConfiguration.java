package com.tcs.cma.apis.tools.models;

/**
 * 
 * @author Manish Srivastava
 * 17 Sep 2019
 */
public class ApiProxyEndPointConfiguration extends ApiEndPointConfiguration{

	/**
	 * 
	 */
	private static final long serialVersionUID = 4781439174478680178L;
	
	/**
	 * 	
	 */
	private String apiProxyPostClientFlowResponse;
	
	/**
	 * 
	 */
	private String apiProxyVirtualHosts;
	
	/**
	 * 
	 */
	private String apiProxyUnknownResource;

	/**
	 * @return the apiProxyPostClientFlowResponse
	 */
	public String getApiProxyPostClientFlowResponse() {
		return apiProxyPostClientFlowResponse;
	}

	/**
	 * @param apiProxyPostClientFlowResponse the apiProxyPostClientFlowResponse to set
	 */
	public void setApiProxyPostClientFlowResponse(String apiProxyPostClientFlowResponse) {
		this.apiProxyPostClientFlowResponse = apiProxyPostClientFlowResponse;
	}

	/**
	 * @return the apiProxyVirtualHosts
	 */
	public String getApiProxyVirtualHosts() {
		return apiProxyVirtualHosts;
	}

	/**
	 * @param apiProxyVirtualHosts the apiProxyVirtualHosts to set
	 */
	public void setApiProxyVirtualHosts(String apiProxyVirtualHosts) {
		this.apiProxyVirtualHosts = apiProxyVirtualHosts;
	}

	/**
	 * @return the apiProxyUnknownResource
	 */
	public String getApiProxyUnknownResource() {
		return apiProxyUnknownResource;
	}

	/**
	 * @param apiProxyUnknownResource the apiProxyUnknownResource to set
	 */
	public void setApiProxyUnknownResource(String apiProxyUnknownResource) {
		this.apiProxyUnknownResource = apiProxyUnknownResource;
	}
	
	
}
