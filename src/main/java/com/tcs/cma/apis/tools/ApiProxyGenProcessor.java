package com.tcs.cma.apis.tools;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathExpression;
import javax.xml.xpath.XPathFactory;

import org.apache.commons.io.FileUtils;
import org.w3c.dom.Document;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.tcs.cma.apis.tools.constant.ApiConstants;
import com.tcs.cma.apis.tools.models.ApiProxyEndPointCommonPolicy;
import com.tcs.cma.apis.tools.models.ApiProxyEndPointConfiguration;
import com.tcs.cma.apis.tools.models.ApiStep;
import com.tcs.cma.apis.tools.models.ApiTargetEndPointCommonPolicy;
import com.tcs.cma.apis.tools.models.ApiTargetEndPointConfiguration;

/**
 * 
 * @author Manish Srivastava 19 Sep 2019
 */
public class ApiProxyGenProcessor implements ApiConstants {

	/**
	 * 
	 */
	private File rootDirectory;

	/**
	 * 
	 */
	private File[] apiProxyDirectories;

	/**
	 * 
	 */
	private String apiProxyEndConfig;

	/**
	 * 
	 */
	private String apiTargetEndConfig;

	/**
	 * 
	 */
	private String apiCommonPolicy;

	/**
	 * 
	 */
	private Set<String> apiCommonPolicySet = new HashSet<>();

	/**
	 * 
	 * @param rootDirectory
	 * @param apiCommonPolicy
	 * @param apiProxyEndConfig
	 * @param apiTargetEndConfig
	 * @param apiProxyDirectories
	 */
	public ApiProxyGenProcessor(File rootDirectory, String apiCommonPolicy, String apiProxyEndConfig,
			String apiTargetEndConfig, File... apiProxyDirectories) {
		this.rootDirectory = rootDirectory;
		this.apiCommonPolicy = apiCommonPolicy;
		this.apiProxyEndConfig = apiProxyEndConfig;
		this.apiTargetEndConfig = apiTargetEndConfig;
		this.apiProxyDirectories = apiProxyDirectories;
	}

	/**
	 * 
	 * @throws IOException
	 */
	public void applyPolicyDependency() throws IOException {
		List<File> apiXmlFiles = getXmlsFiles();
		processPolicy(apiXmlFiles);
	}

	/**
	 * 
	 * @return
	 */
	private List<File> getXmlsFiles() {
		List<File> apiXmlFiles = new ArrayList<File>();
		for (File apiProxyDirectory : apiProxyDirectories) {
			apiXmlFiles.addAll(FileUtils.listFiles(apiProxyDirectory, new String[] { "xml" }, false));
		}
		return apiXmlFiles;
	}

	/**
	 * 
	 * @param apiXmlFiles
	 * @throws IOException
	 */
	private void processPolicy(List<File> apiXmlFiles) throws IOException {
		for (File xml : apiXmlFiles) {
			if (xml.getPath().endsWith("proxies" + File.separatorChar + "default.xml")) {
				applyProxyEndCommonPolicy(xml);
			} else if (xml.getPath().endsWith("targets" + File.separatorChar + "default.xml")
					|| xml.getPath().endsWith("targets" + File.separatorChar + "sandbox.xml")) {
				applyTargetEndCommonPolicy(xml);
			} else {
				System.out.println("No policy for the processing");
			}
		}
		copy(apiCommonPolicySet);
	}

	/**
	 * 
	 * @param xml
	 */
	private void applyProxyEndCommonPolicy(File xml) throws IOException{
		ApiProxyEndPointCommonPolicy apiProxyEndPointCommonPolicy = getApiProxyEndPointCommonPolicy();
		List<ApiStep> defaultFaultRule = apiProxyEndPointCommonPolicy.getDefaultFalutRule();
		List<ApiStep> postClientFlowResponse = apiProxyEndPointCommonPolicy.getPostClientFlowResponse();
		List<ApiStep> postFlowRequest = apiProxyEndPointCommonPolicy.getPostFlowRequest();
		List<ApiStep> postFlowResponse = apiProxyEndPointCommonPolicy.getPostFlowResponse();
		List<ApiStep> preFlowRequest = apiProxyEndPointCommonPolicy.getPreFlowRequest();
		List<ApiStep> preFlowResponse = apiProxyEndPointCommonPolicy.getPreFlowResponse();
		String[] vHosts = apiProxyEndPointCommonPolicy.getvHosts();
		String unknownResource = apiProxyEndPointCommonPolicy.getUnknownResource();
		String content = FileUtils.readFileToString(xml,StandardCharsets.UTF_8);
		StringBuilder builder = new StringBuilder();
		content = createFileContents(builder, defaultFaultRule, content, API_PROXY_DEFAULT_FAULT_RULE);
		content = createFileContents(builder, postClientFlowResponse, content, API_PROXY_POST_CLIENT_FLOW_RESPONSE);
		content = createFileContents(builder, postFlowRequest, content, API_PROXY_REQUEST_POST_FLOW);
		content = createFileContents(builder, postFlowResponse, content, API_PROXY_RESPONSE_POST_FLOW);
		content = createFileContents(builder, preFlowRequest, content, API_PROXY_REQUEST_PRE_FLOW);
		content = createFileContents(builder, preFlowResponse, content, API_PROXY_RESPONSE_PRE_FLOW);
		builder.setLength(0);
		createApiStep(builder, unknownResource);
		content = content.replaceAll(API_PROXY_UNKNOWN_RESOURCE, builder.toString());
		builder.setLength(0);
		for(String vHost: vHosts) {
			createVhostTag(builder, vHost);
		}
		content = content.replaceAll(API_PROXY_VHOSTS, builder.toString());
		FileUtils.writeStringToFile(xml, content,StandardCharsets.UTF_8);
	}

	/**
	 * 
	 * @return
	 */
	private ApiProxyEndPointCommonPolicy getApiProxyEndPointCommonPolicy() {
		ApiProxyEndPointCommonPolicy apiProxyEndPoint = new ApiProxyEndPointCommonPolicy();
		try {
			byte[] jsonData = Files.readAllBytes(Paths.get(apiProxyEndConfig));
			ObjectMapper objMapper = new ObjectMapper();
			ApiProxyEndPointConfiguration config = objMapper.readValue(jsonData, ApiProxyEndPointConfiguration.class);
			apiProxyEndPoint.setDefaultFalutRule(getSteps(config.getApiDefaultFaultRule()));
			apiProxyEndPoint.setPostClientFlowResponse(getSteps(config.getApiPostFlowResponse()));
			apiProxyEndPoint.setPostFlowRequest(getSteps(config.getApiPostFlowRequest()));
			apiProxyEndPoint.setPostFlowResponse(getSteps(config.getApiPostFlowResponse()));
			apiProxyEndPoint.setPreFlowRequest(getSteps(config.getApiPreFlowRequest()));
			apiProxyEndPoint.setPreFlowResponse(getSteps(config.getApiPreFlowResponse()));
			apiProxyEndPoint.setvHosts(getVHosts(config.getApiProxyVirtualHosts()));
			apiProxyEndPoint.setUnknownResource(config.getApiProxyUnknownResource());
		} catch (Exception e) {
			// TODO: handle exception
		}
		return apiProxyEndPoint;
	}

	/**
	 * 
	 * @return
	 */
	private ApiTargetEndPointCommonPolicy getApiTargetEndPointCommonPolicy() {
		ApiTargetEndPointCommonPolicy apiTargetEndPoint = new ApiTargetEndPointCommonPolicy();
		try {
			byte[] jsonData = Files.readAllBytes(Paths.get(apiTargetEndConfig));
			ObjectMapper objMapper = new ObjectMapper();
			ApiTargetEndPointConfiguration config = objMapper.readValue(jsonData, ApiTargetEndPointConfiguration.class);
			apiTargetEndPoint.setDefaultFalutRule(getSteps(config.getApiDefaultFaultRule()));
			apiTargetEndPoint.setPostFlowRequest(getSteps(config.getApiPostFlowRequest()));
			apiTargetEndPoint.setPostFlowResponse(getSteps(config.getApiPostFlowResponse()));
			apiTargetEndPoint.setPreFlowRequest(getSteps(config.getApiPreFlowRequest()));
			apiTargetEndPoint.setPreFlowResponse(getSteps(config.getApiPreFlowResponse()));
		} catch (Exception e) {
			// TODO: handle exception
		}
		return apiTargetEndPoint;
	}
	
	/**
	 * 
	 * @param xml
	 */
	private void applyTargetEndCommonPolicy(File xml) throws IOException{
		ApiTargetEndPointCommonPolicy apiTargetEndPointCommonPolicy = getApiTargetEndPointCommonPolicy();
		List<ApiStep> defaultFaultRule = apiTargetEndPointCommonPolicy.getDefaultFalutRule();
		List<ApiStep> postFlowRequest = apiTargetEndPointCommonPolicy.getPostFlowRequest();
		List<ApiStep> postFlowResponse = apiTargetEndPointCommonPolicy.getPostFlowResponse();
		List<ApiStep> preFlowRequest = apiTargetEndPointCommonPolicy.getPreFlowRequest();
		List<ApiStep> preFlowResponse = apiTargetEndPointCommonPolicy.getPreFlowResponse();
		String content = FileUtils.readFileToString(xml,StandardCharsets.UTF_8);
		StringBuilder builder = new StringBuilder();
		for(ApiStep step : defaultFaultRule) {
			createApiStep(builder, step.getApiStepName());
		}
		content = content.replaceAll(API_PROXY_DEFAULT_FAULT_RULE, builder.toString());
		
		builder.setLength(0);
		
		for(ApiStep step : postFlowRequest) {
			createApiStep(builder, step.getApiStepName());
		}
		content = content.replaceAll(API_PROXY_REQUEST_POST_FLOW, builder.toString());
		
		builder.setLength(0);
		
		for(ApiStep step : postFlowResponse) {
			createApiStep(builder, step.getApiStepName());
		}
		content = content.replaceAll(API_PROXY_RESPONSE_POST_FLOW, builder.toString());
		
		builder.setLength(0);
		
		for(ApiStep step : preFlowRequest) {
			createApiStep(builder, step.getApiStepName());
		}
		content = content.replaceAll(API_PROXY_REQUEST_PRE_FLOW, builder.toString());
		
		builder.setLength(0);
		
		for(ApiStep step : preFlowResponse) {
			createApiStep(builder, step.getApiStepName());
		}
		content = content.replaceAll(API_PROXY_RESPONSE_PRE_FLOW, builder.toString());
		FileUtils.writeStringToFile(xml, content, StandardCharsets.UTF_8);
		
	}

	/**
	 * 
	 * @param policySet
	 */
	private void copy(Set<String> policySet) throws IOException {
		final File destinationDir = new File(rootDirectory.getPath() + "/apiproxy/policies");
		final File jsDestinationDir = new File(rootDirectory.getPath() + "/apiproxy/resources/jsc");
		String policyName = null;
		File policyFIle = null;
		for (String policy : policySet) {
			policyName = apiCommonPolicy + File.separator + policy + ".xml";
			policyFIle = new File(policyName);
			FileUtils.copyFileToDirectory(policyFIle, destinationDir);
			if (policy.substring(0, 3).equalsIgnoreCase("JAS")) {
				String jsFile = getJavaScriptFileName(policyName);
				if (null != jsFile) {
					FileUtils.copyFileToDirectory(
							new File(apiCommonPolicy + File.separator + "js" + File.separator + jsFile),
							jsDestinationDir);
				}
			}
		}
	}

	/**
	 * 
	 * @param policy
	 * @return
	 */
	private String getJavaScriptFileName(String policy) {
		String name = null;
		try {
			DocumentBuilderFactory docBuilderFactory = DocumentBuilderFactory.newInstance();
			docBuilderFactory.setFeature("http://apache.org/xml/features/disallow-doctype-dec1", true);
			docBuilderFactory.setNamespaceAware(true);
			DocumentBuilder builder = docBuilderFactory.newDocumentBuilder();
			Document document = builder.parse(policy);
			XPathFactory xPathFactory = XPathFactory.newInstance();
			XPath xPath = xPathFactory.newXPath();
			XPathExpression xPathExpr = xPath.compile("//Javascript//ResourceURL");
			String value = xPathExpr.evaluate(document);
			int index = value.lastIndexOf('/');
			name = value.substring(index + 1);
		} catch (Exception e) {
			// TODO: handle exception
		}
		return name;
	}

	/**
	 * 
	 * @param vHostList
	 * @return
	 */
	private String[] getVHosts(String vHostList) {
		if (null != vHostList) {
			return vHostList.split(";");
		} else
			return new String[] {};
	}

	/**
	 * 
	 * @param builder
	 * @param apiStep
	 */
	private void createApiStep(StringBuilder builder, String apiStep) {
		if (!apiStep.isEmpty()) {
			builder.append("\n\t\t<Step>\n\t\t\t<Name>" + apiStep + "</Name>\n\t\t</Step>\n");
			apiCommonPolicySet.add(apiStep);
		}
	}

	/**
	 * 
	 * @param builder
	 * @param vHost
	 */
	private void createVhostTag(StringBuilder builder, String vHost) {
		if (!vHost.isEmpty()) {
			builder.append("<VirtualHost>" + vHost + "</VirtualHost>");
		}
	}

	/**
	 * 
	 * @param stepList
	 * @return
	 */
	private String[] getApiSteps(String stepList) {
		if (null != stepList) {
			return stepList.split(";");
		} else {
			return new String[] {};
		}
	}

	/**
	 * 
	 * @param stepList
	 * @return
	 */
	private List<ApiStep> getSteps(String stepList) {
		List<ApiStep> stepsList = new ArrayList<>();
		String[] apiSteps = getApiSteps(stepList);
		for (String s : apiSteps) {
			ApiStep apiStep = new ApiStep(s);
			stepsList.add(apiStep);
		}
		return stepsList;
	}

	/**
	 * 
	 * @param builder
	 * @param stepsList
	 * @param content
	 * @param replacement
	 * @return
	 */
	private String createFileContents(StringBuilder builder, List<ApiStep> stepsList, String content,
			String replacement) {
		builder.setLength(0);
		for (ApiStep apiStep : stepsList) {
			createApiStep(builder, apiStep.getApiStepName());
		}
		return content.replaceAll(replacement, builder.toString());
	}
}
