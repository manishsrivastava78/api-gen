package com.tcs.cma.apis.tools.models;

import java.util.List;

/**
 * 
 * @author Manish Srivastava
 * 20 Sep 2019
 */
public class ApiProxyEndPointCommonPolicy extends ApiEndPointCommonPolicy{

	/**
	 * 
	 */
	private static final long serialVersionUID = 6609403225460015563L;
	
	private List<ApiStep> postClientFlowResponse;
	
	private String[] vHosts;
	
	private String unknownResource;

	/**
	 * @return the postClientFlowResponse
	 */
	public List<ApiStep> getPostClientFlowResponse() {
		return postClientFlowResponse;
	}

	/**
	 * @param postClientFlowResponse the postClientFlowResponse to set
	 */
	public void setPostClientFlowResponse(List<ApiStep> postClientFlowResponse) {
		this.postClientFlowResponse = postClientFlowResponse;
	}

	/**
	 * @return the vHosts
	 */
	public String[] getvHosts() {
		return vHosts;
	}

	/**
	 * @param vHosts the vHosts to set
	 */
	public void setvHosts(String[] vHosts) {
		this.vHosts = vHosts;
	}

	/**
	 * @return the unknownResource
	 */
	public String getUnknownResource() {
		return unknownResource;
	}

	/**
	 * @param unknownResource the unknownResource to set
	 */
	public void setUnknownResource(String unknownResource) {
		this.unknownResource = unknownResource;
	}
	
	
}
