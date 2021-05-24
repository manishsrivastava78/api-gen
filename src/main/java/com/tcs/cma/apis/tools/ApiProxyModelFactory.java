package com.tcs.cma.apis.tools;

import java.net.URL;

import com.tcs.cma.apis.tools.models.ApiProxyModel;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.parser.OpenAPIV3Parser;

/**
 * 
 * @author Manish Srivastava
 * 22 Sep 2019
 */
public final class ApiProxyModelFactory {
	/**
	 * 
	 */
	private ApiProxyModelFactory() {
		
	}
	
	/**
	 * 
	 * @param apiSpec
	 * @return
	 */
	public static ApiProxyModel getApiModel(String apiSpec) {
		return parse3xSwagger(apiSpec);
	}
	
	/**
	 * 
	 * @param apiSpec
	 * @return
	 */
	private static ApiProxyModel parse3xSwagger(String apiSpec) {
		OpenAPI openApi = new OpenAPIV3Parser().read(apiSpec);
		ApiProxyModel model = new ApiProxyModel();
		model.setApiDescription(openApi.getInfo().getDescription());
		if(openApi.getServers() !=null) {
			try {
				URL url = new URL(openApi.getServers().get(0).getUrl());
				model.setApiBasePath(url.getPath());
			}catch (Exception e) {
				// TODO: handle exception
			}
		}
		model.setApiPaths(openApi.getPaths());
		model.setComponents(openApi.getComponents());
		return model;
		
	}
}
