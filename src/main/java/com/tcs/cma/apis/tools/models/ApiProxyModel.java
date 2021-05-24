package com.tcs.cma.apis.tools.models;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import javax.swing.text.Position.Bias;

import org.apache.commons.lang3.StringUtils;

import com.tcs.cma.apis.tools.constant.ApiConstants;
import com.tcs.cma.apis.tools.util.ApiUtility;

import io.swagger.models.parameters.Parameter;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.Operation;
import io.swagger.v3.oas.models.PathItem;
import io.swagger.v3.oas.models.PathItem.HttpMethod;
import io.swagger.v3.oas.models.Paths;
import io.swagger.v3.oas.models.parameters.RequestBody;

/**
 * 
 * @author Manish Srivastava 18 Sep 2019
 */
public class ApiProxyModel implements ApiConstants {
	/**
	 * 
	 */
	private List<ApiResource> apiResourceList = new ArrayList<ApiResource>();

	private String apiBasePath;

	private String apiDescription;

	private Paths apiPaths;

	private boolean apiRequestBodyPolicy;

	private Components components;

	private boolean requestBodyPolicy;

	/**
	 * @return the requestBodyPolicy
	 */
	public boolean isRequestBodyPolicy() {
		return requestBodyPolicy;
	}

	/**
	 * @param requestBodyPolicy the requestBodyPolicy to set
	 */
	public void setRequestBodyPolicy(boolean requestBodyPolicy) {
		this.requestBodyPolicy = requestBodyPolicy;
	}

	/**
	 * @return the apiResourceList
	 */
	public List<ApiResource> getApiResourceList() {
		return apiResourceList;
	}

	/**
	 * @param apiResourceList the apiResourceList to set
	 */
	public void setApiResourceList(List<ApiResource> apiResourceList) {
		this.apiResourceList = apiResourceList;
	}

	/**
	 * @return the apiBasePath
	 */
	public String getApiBasePath() {
		return Optional.of(apiBasePath).orElse("/");
	}

	/**
	 * @param apiBasePath the apiBasePath to set
	 */
	public void setApiBasePath(String apiBasePath) {
		this.apiBasePath = apiBasePath;
	}

	/**
	 * @return the apiDescription
	 */
	public String getApiDescription() {
		return Optional.of(apiDescription).orElse("");
	}

	/**
	 * @param apiDescription the apiDescription to set
	 */
	public void setApiDescription(String apiDescription) {
		this.apiDescription = apiDescription;
	}

	/**
	 * @return the apiPaths
	 */
	public Paths getApiPaths() {
		return apiPaths;
	}

	/**
	 * @param apiPaths the apiPaths to set
	 */
	public void setApiPaths(Paths apiPaths) {
		this.apiPaths = apiPaths;
	}

	/**
	 * @return the apiRequestBodyPolicy
	 */
	public boolean isApiRequestBodyPolicy() {
		return apiRequestBodyPolicy;
	}

	/**
	 * @param apiRequestBodyPolicy the apiRequestBodyPolicy to set
	 */
	public void setApiRequestBodyPolicy(boolean apiRequestBodyPolicy) {
		this.apiRequestBodyPolicy = apiRequestBodyPolicy;
	}

	/**
	 * @return the components
	 */
	public Components getComponents() {
		return components;
	}

	/**
	 * @param components the components to set
	 */
	public void setComponents(Components components) {
		this.components = components;
	}

	/**
	 * 
	 * @return
	 */
	private String getFlowContent() {
		ApiResource apiEndPointModel = null;
		List<ApiMethod> httpMethods = null;
		ApiMethod apiMethodModel = null;
		String flowDesc = null;
		String methodName = null;
		if(null!= apiPaths) {
			PathItem path = null;
			String pathValue = null;
			List<String> list = new ArrayList<>();
			List<io.swagger.v3.oas.models.parameters.Parameter> paramList = null;
			for(Map.Entry<String, PathItem> entry: apiPaths.entrySet()) {
				httpMethods = new ArrayList<>();
				pathValue = entry.getKey();
				path = entry.getValue();
				Map<HttpMethod, Operation> pathMap = path.readOperationsMap();
				for(Map.Entry<HttpMethod,Operation> entry1 : pathMap.entrySet()) {
					apiEndPointModel = new ApiResource();
					apiEndPointModel.setApiEndPoint(pathValue);
					apiMethodModel = new ApiMethod();
					flowDesc = getFlowDescription(entry1.getValue());
					methodName = entry1.getKey().name();
					apiMethodModel.setApiProxyMethod(methodName);
					apiMethodModel.setApiProxyDesc(flowDesc);
					Operation operation = entry1.getValue();
					httpMethods.add(apiMethodModel);
					paramList = operation.getParameters();
					apiEndPointModel.setApiFlowName(ApiUtility.getApiOperationId(operation.getOperationId(),methodName, pathValue));
					apiEndPointModel.setApiHttpVerb(methodName);
					apiEndPointModel.setApiFlowDesc(flowDesc);
					if(null!=paramList) {
						apiEndPointModel.setApiQueryParameters(getParams(paramList));
						apiEndPointModel.setApiParameterModelList(getParameterModelList(paramList));
					}
					apiResourceList.add(apiEndPointModel);
					list.add(getFlow(pathValue, methodName,flowDesc, operation.getOperationId(), operation.getRequestBody(), apiEndPointModel.getApiParameterModelList()));
				}
			}
			return getFlows(list);
		}
		return "";
	}
	
	
	/**
	 * 
	 * @param path
	 * @param method
	 * @param description
	 * @param operationId
	 * @param requestBody
	 * @param paramModelList
	 * @return
	 */
	private String getFlow(String path,String method,String description, String operationId,RequestBody requestBody,List<ApiParameter> paramModelList) {
		StringBuilder builder = new StringBuilder();
		String flowName = null;
		String asmPolicyPartName = null;
		if(null!=operationId && !operationId.isEmpty()) {
			flowName = operationId;
			asmPolicyPartName = operationId;
		}else {
			flowName = getFlowName(path, method);
			asmPolicyPartName = ApiUtility.getApiOperationId(null, method, path);
		}
		builder.append(TWO_TABS);
		builder.append("<Flow name=\""+flowName+XML_CLOSING_TAG);
		builder.append(THREE_TABS);
		builder.append("<Description>"+description+"</Description>");
		newLineAnd3Indents(builder);
		builder.append(getRequestTag(asmPolicyPartName,requestBody,paramModelList));
		builder.append(NEW_LINE);
		builder.append(API_RESPONSE_TAG);
		builder.append(THREE_TABS);
		builder.append("<Condition>proxy.pathsuffix MatchesPath \"" + path+"\" AND request.verb=\""+method+"\"<Condition>");
		builder.append(NEW_LINE);
		builder.append(API_FLOW_TAG);
		return builder.toString();
	}
	
	/**
	 * 
	 * @param flowName
	 * @param requestBody
	 * @param paramModelList
	 * @return
	 */
	private String getRequestTag(String flowName,RequestBody requestBody,List<ApiParameter> paramModelList) {
		StringBuilder builder = new StringBuilder();
		builder.append(THREE_TABS);
		builder.append("<Request>");
		String rafStepContent = null;
		if(null!=paramModelList) {
			rafStepContent = getRafStepContent(requestBody, paramModelList);
		}
		if(null!= rafStepContent && !rafStepContent.isEmpty()) {
			builder.append(rafStepContent);
		}
		newLineAnd4Indents(builder);
		setCommonRafBuilder(builder);
		builder.append("<Name>ASM-");
		builder.append(StringUtils.capitalize(flowName));
		builder.append("AllowedParams</Name>");
		newLineAnd4Indents(builder);
		builder.append("</Request>");
		return builder.toString();
	}
	/**
	 * 
	 * @param paramList
	 * @return
	 */
	private List<ApiParameter> getParameterModelList(List<io.swagger.v3.oas.models.parameters.Parameter> paramList){
		List<ApiParameter> paramModelList = new ArrayList<>();
		ApiParameter parameterModel = null;
		for(io.swagger.v3.oas.models.parameters.Parameter p: paramList) {
			parameterModel = new ApiParameter();
			parameterModel.setApiParameterName(p.getName());
			parameterModel.setApiParameterIn(p.getIn());
			if(null!=p.getRequired()) {
				parameterModel.setApiParameterRequired(p.getRequired());
			}
			setParameterModelTypeAndEnums(parameterModel,p);
			paramModelList.add(parameterModel);
		}
		return paramModelList;
	}
	
	/**
	 * 
	 * @param paramModel
	 * @param p
	 */
	private void setParameterModelTypeAndEnums(ApiParameter paramModel,io.swagger.v3.oas.models.parameters.Parameter p) {
		List list = null;
		List<String> enums = null;
		String refSchema = p.getSchema().get$ref();
		if(null!=refSchema) {
			io.swagger.v3.oas.models.media.Schema schema = this.getComponents().getSchemas().get(refSchema.substring(refSchema.lastIndexOf('/')+1));
			list = schema.getEnum();
			if(null!=list) {
				enums = new ArrayList<>();
				for(Object o:list) {
					enums.add((String)o);
				}
				paramModel.setApiParameterEnum(enums);
				paramModel.setApiJsModel(getJsValidateModel(paramModel.getApiParameterName(), enums));
			}
			paramModel.setApiParameterType(schema.getType());
		}else {
			list = p.getSchema().getEnum();
			if(null!=list) {
				enums = new ArrayList<>();
				for(Object o : list) {
					enums.add((String)o);
				}
				paramModel.setApiParameterEnum(enums);
			}
			paramModel.setApiParameterType(p.getSchema().getType());
		}
	}
	/**
	 * 
	 * @return
	 */
	private String getApiFlowDescription(Operation apiOperation) {
		if (apiOperation.getSummary() != null) {
			return apiOperation.getSummary();
		} else if (apiOperation.getDescription() != null) {
			return apiOperation.getDescription();
		}
		return API_DEFAULT_FLOW_DESCRIPTION;
	}

	/**
	 * 
	 * @param basePath
	 * @return
	 */
	public String getProxyEndPointContent(String basePath) {
		this.setApiBasePath(basePath);
		return getStartProxyEndPointContent() + getFlowContent() + getPostClientFlowContent() + getEndProxyEndPointContent();
	}
	
	/**
	 * 
	 * @return
	 */
	private String getPostClientFlowContent() {
		StringBuilder builder = new StringBuilder(ONE_TAB);
		builder.append("<PostClientFlow name=\"PostClientFlow\">");
		newLineAnd2Indents(builder);
		builder.append("<Response>"+API_PROXY_POST_CLIENT_FLOW_RESPONSE+"</Response>");
		newLineAnd1Indent(builder);
		builder.append("</PostClientFlow>");
		builder.append(NEW_LINE);
		return builder.toString();
	}
	
	/**
	 * 
	 * @return
	 */
	private String getEndProxyEndPointContent() {
		StringBuilder builder = new StringBuilder(ONE_TAB);
		builder.append("<HTTPProxyConnection>");
		newLineAnd2Indents(builder);
		builder.append("<BasePath>"+getApiBasePath()+"</BasePath>");
		newLineAnd2Indents(builder);
		builder.append("<Properties/>");
		newLineAnd2Indents(builder);
		builder.append(API_PROXY_VHOSTS);
		newLineAnd1Indent(builder);
		builder.append("</HTTPProxyConnection>");
		newLineAnd1Indent(builder);
		builder.append("<"+ROUTE_RULE+" name=\"NoRoute" + XML_CLOSING_TAG);
		builder.append(TWO_TABS);
		builder.append("<Condition>request.verb equals \"OPTIONS\"</Condition>");
		newLineAnd1Indent(builder);
		builder.append("</"+ROUTE_RULE +">");
		newLineAnd1Indent(builder);
		builder.append("<"+ROUTE_RULE+" name=\"sandbox"+XML_CLOSING_TAG);
		builder.append(TWO_TABS);
		builder.append("<!-- Update SandBoxHostValue with actual api sandbox URL -->");
		builder.append("<Condition>request.header.host equals \"SandBoxHostValue\"</Condition>");
		newLineAnd2Indents(builder);
		builder.append("<TargetEndpoint>sandbox</TargetEndpoint>");
		newLineAnd1Indent(builder);
		builder.append("</"+ROUTE_RULE+ ">");
		newLineAnd1Indent(builder);
		builder.append("<"+ROUTE_RULE + " name=\"default\">");
		newLineAnd2Indents(builder);
		builder.append(API_DEFAULT_TARGET_END_POINT);
		newLineAnd1Indent(builder);
		builder.append("</"+ROUTE_RULE+">");
		builder.append(NEW_LINE);
		builder.append("</ProxyEndpoint");
		builder.append(NEW_LINE);
		return builder.toString();
	}
	private String getStartProxyEndPointContent() {
		StringBuilder builder = new StringBuilder(XML_PRE_PROCESSOR);
		builder.append(TOOL_COMMENT);
		builder.append("<ProxyEndpoint name=\"default\">");
		newLineAnd1Indent(builder);
		builder.append("<DefaultFalutRule name=\"PlatformFaults \">\n\t\t<AlwaysEnforce>true</AlwaysEnforce>"+API_PROXY_DEFAULT_FAULT_RULE+"</DefaultFaultRule>");
		newLineAnd1Indent(builder);
		builder.append("<PreFlow name=\"PreFlow\">");
		newLineAnd2Indents(builder);
		builder.append("<Request>"+API_PROXY_REQUEST_PRE_FLOW+"</Request>");
		newLineAnd2Indents(builder);
		builder.append("<Response>"+API_PROXY_RESPONSE_PRE_FLOW+"</Response>");
		newLineAnd1Indent(builder);
		builder.append("</PreFlow>");
		newLineAnd1Indent(builder);
		builder.append("<PostFlow name=\"PostFlow\">");
		newLineAnd2Indents(builder);
		builder.append("<Request>"+API_PROXY_REQUEST_POST_FLOW+"</Request>");
		newLineAnd2Indents(builder);
		builder.append("<Response>"+API_PROXY_RESPONSE_POST_FLOW+"</Response>");
		newLineAnd1Indent(builder);
		builder.append("</PostFlow>");
		builder.append(NEW_LINE);
		return builder.toString();
	}

	/**
	 * 
	 * @param targetName
	 * @param targetServerName
	 * @param backendPath
	 * @return
	 */
	public String getTargetEndPointContent(String targetName, String targetServerName, String backendPath) {
		StringBuilder builder = new StringBuilder(XML_PRE_PROCESSOR);
		builder.append(NEW_LINE);
		builder.append(TOOL_COMMENT);
		builder.append("<TargetEndpoints name=\"" + targetName + XML_CLOSING_TAG);
		builder.append(ONE_TAB);
		builder.append("<DefaultFaultRule name=\"TargetFaults\">"+API_PROXY_DEFAULT_FAULT_RULE+"</DefaultFaultRule>");
		newLineAnd1Indent(builder);
		builder.append("<PreFlow name=\"PreFlow\">");
		newLineAnd2Indents(builder);
		builder.append("<Request>"+API_PROXY_REQUEST_PRE_FLOW+"</Request>");
		newLineAnd2Indents(builder);
		builder.append("<Response>"+API_PROXY_RESPONSE_PRE_FLOW+"</Response>");
		newLineAnd1Indent(builder);
		builder.append("</PreFlow>");
		newLineAnd1Indent(builder);
		builder.append("<Flows/>");
		newLineAnd1Indent(builder);
		builder.append("<PostFlow name=\"PostFlow\">");
		newLineAnd2Indents(builder);
		builder.append("<Request>"+API_PROXY_REQUEST_POST_FLOW+"</Request>");
		newLineAnd2Indents(builder);
		builder.append("<Response>"+API_PROXY_RESPONSE_POST_FLOW+"</Response>");
		newLineAnd1Indent(builder);
		builder.append("</PostFlow>");
		newLineAnd1Indent(builder);
		builder.append("<LoadBalancer>");
		builder.append(NEW_LINE);
		builder.append(TOOL_COMMENT);
		builder.append("\t\t\t<!-- Update name with target server --> \n");
		builder.append(THREE_TABS);
		builder.append("<Server name=\"" +targetServerName + "\"/>");
		newLineAnd2Indents(builder);
		builder.append("</LoadBalancer>");
		builder.append(NEW_LINE);
		builder.append(TOOL_COMMENT);
		builder.append(TWO_TABS);
		builder.append("<Path>/"+backendPath + "</Path>");
		newLineAnd1Indent(builder);
		builder.append("</HTTPTargetConnection>");
		builder.append(NEW_LINE);
		builder.append("</TargerEndpoints>");
		return builder.toString();
	}

	/**
	 * 
	 * @param proxyName
	 * @return
	 */
	public String getProxyManifestContent(String proxyName) {
		StringBuilder builder = new StringBuilder(XML_PRE_PROCESSOR);
		builder.append(NEW_LINE);
		builder.append(TOOL_COMMENT);
		builder.append("<APIProxy revision=\"1\" name=\"" + proxyName+XML_CLOSING_TAG);
		builder.append(ONE_TAB);
		builder.append("<ConfigurationVersion minorVersion=\0\" majorVersion=\"4\"/>");
		newLineAnd1Indent(builder);
		builder.append("<Description>"+getApiDescription()+"</Description>");
		builder.append(ONE_TAB);
		builder.append("<Policies>");
		newLineAnd1Indent(builder);
		builder.append("<ProxyEndpoints>");
		newLineAnd2Indents(builder);
		builder.append(API_DEFAULT_PROXY_END_POINT);
		builder.append(getPartProxyManifestContent());
		builder.append(TWO_TABS);
		builder.append(API_DEFAULT_TARGET_END_POINT);
		newLineAnd1Indent(builder);
		builder.append("</TargetEndpoints>");
		newLineAnd1Indent(builder);
		builder.append("<validate>true</validate>");
		builder.append(NEW_LINE);
		builder.append("</APIProxy>");
		return builder.toString();
	}

	/**
	 * 
	 * @param path
	 * @param verb
	 * @return
	 */
	private String getFlowName(String path, String verb) {
		return verb.toLowerCase() + " " + path;
	}

	/**
	 * 
	 * @param requestTag
	 */
	private void newLineAnd5Indents(StringBuilder requestTag) {
		requestTag.append(NEW_LINE);
		requestTag.append(FIVE_TABS);
	}

	/**
	 * 
	 * @param requestTag
	 */
	private void newLineAnd4Indents(StringBuilder requestTag) {
		requestTag.append(NEW_LINE);
		requestTag.append(FOUR_TABS);
	}

	/**
	 * 
	 * @param requestTag
	 */
	private void newLineAnd3Indents(StringBuilder requestTag) {
		requestTag.append(NEW_LINE);
		requestTag.append(THREE_TABS);
	}

	/**
	 * 
	 * @param requestTag
	 */
	private void newLineAnd2Indents(StringBuilder requestTag) {
		requestTag.append(NEW_LINE);
		requestTag.append(TWO_TABS);
	}

	/**
	 * 
	 * @param requestTag
	 */
	private void newLineAnd1Indent(StringBuilder requestTag) {
		requestTag.append(NEW_LINE);
		requestTag.append(ONE_TAB);
	}

	/**
	 * 
	 * @param operation
	 * @return
	 */
	private String getFlowDescription(Operation operation) {
		String desc = "Please add flow description";
		if (operation.getSummary() != null) {
			desc = operation.getSummary();
		} else if (operation.getDescription() != null) {
			desc = operation.getDescription();
		}
		return desc;
	}

	/**
	 * 
	 * @param requestBody
	 * @param paramModelList
	 * @return
	 */
	private String getRafStepContent(RequestBody requestBody, List<ApiParameter> paramModelList) {
		StringBuilder builder = new StringBuilder();
		for (ApiParameter param : paramModelList) {
			if (param.isApiParameterRequired() && param.getApiParameterIn().equalsIgnoreCase(API_QUERY_PARAMETER)) {
				builder.append(getRafForQueryParam(param));
			} else {
				System.out.println("Missing and invalid param RAF is not required ...");
			}
		}

		if (null != requestBody) {
			setRafMissingRequestBody(builder);
		} else {
			System.out.println("Missing request body RAF is not required ...");
		}

		return builder.toString().trim();
	}

	/**
	 * 
	 * @param paramModel
	 * @return
	 */
	private String getRafForQueryParam(ApiParameter paramModel) {
		if (null != paramModel.getApiParameterEnum() && !paramModel.getApiParameterEnum().isEmpty()) {
			return getRafMissingOrInvalidParamStep(paramModel);
		} else {
			return getRafMissingParamStep(paramModel);
		}
	}

	/**
	 * 
	 * @param paramModel
	 * @return
	 */
	private String getRafMissingOrInvalidParamStep(ApiParameter paramModel) {
		StringBuilder builder = new StringBuilder();
		builder.append(getRafMissingOrInvalidParamStep(paramModel));
		newLineAnd4Indents(builder);
		if (null != paramModel.getApiJsModel()) {
			setCommonRafBuilder(builder);
			builder.append("<Name>JAS-Validate");
			builder.append(StringUtils.capitalize(paramModel.getApiParameterName()));
			builder.append(API_PARAMETER_NAME);
			newLineAnd5Indents(builder);
			newLineAnd4Indents(builder);
			builder.append(API_END_STEP);
		}
		setCommonRafBuilder(builder);
		builder.append("<Name>RAF-Invalid");
		setCommonRafBuilderTwo(builder, paramModel);
		builder.append(
				getRafCondition4InvalidParam(paramModel.getApiParameterName(), paramModel.getApiParameterEnum()));
		builder.append(API_CONDITION_END);
		newLineAnd4Indents(builder);
		builder.append(API_END_STEP);
		return builder.toString();
	}

	/**
	 * 
	 * @param paramName
	 * @param validParamList
	 * @return
	 */
	private String getRafCondition4InvalidParam(String paramName, List<String> validParamList) {
		StringBuilder builder = new StringBuilder();
		for (String s : validParamList) {
			builder.append(API_REQUEST_QUERY_PARAMETER + paramName + " NotEquals \"" + s + "\") AND ");
		}

		String condition = builder.toString();
		int index = condition.lastIndexOf("AND");
		String conditionValue = condition.lastIndexOf("AND") != -1 ? condition.substring(0, index) : condition;
		if (conditionValue.length() > 200) {
			return javaScriptForInvalidParam(paramName);
		} else {
			return conditionValue;
		}
	}

	/**
	 * 
	 * @param param
	 * @return
	 */
	private String javaScriptForInvalidParam(String param) {
		StringBuilder builder = new StringBuilder();
		builder.append("is.");
		builder.append(param);
		builder.append(".in.list=\"no\"");
		return builder.toString();
	}

	/**
	 * 
	 * @param paramModel
	 * @return
	 */
	private String getRafMissingParamStep(ApiParameter paramModel) {
		StringBuilder builder = new StringBuilder();
		newLineAnd4Indents(builder);
		setCommonRafBuilder(builder);
		builder.append(API_REQUEST_QUERY_PARAMETER + paramModel.getApiParameterName()
				+ " equals null OR request.queryparam." + paramModel.getApiParameterName() + " equals \"\"");
		builder.append(API_CONDITION_END);
		newLineAnd4Indents(builder);
		builder.append(API_END_STEP);
		if (paramModel.getApiParameterType().equalsIgnoreCase("boolean")) {
			getMissingConditionForBoolean(builder, paramModel);
		}
		return builder.toString();
	}

	/**
	 * 
	 * @param param
	 * @param enums
	 * @return
	 */
	private ApiJavaScript getJsValidateModel(String param, List<String> enums) {
		ApiJavaScript model = new ApiJavaScript();
		StringBuilder builder = new StringBuilder("[");
		for (String s : enums) {
			builder.append("\"" + s.toUpperCase().trim() + "\",");
		}
		String dataList = builder.toString();
		dataList = dataList.substring(0, dataList.lastIndexOf(',')) + "]";
		model.setApiParameterName(param);
		model.setApiParameterList(dataList);
		return model;
	}

	/**
	 * 
	 * @param paramList
	 * @return
	 */
	private List<String> getParams(List<io.swagger.v3.oas.models.parameters.Parameter> paramList) {
		List<String> params = new ArrayList<>();
		for (io.swagger.v3.oas.models.parameters.Parameter param : paramList) {
			if (param.getIn().equals(API_QUERY_PARAMETER)) {
				params.add(param.getName());
			}
		}
		return params;
	}

	/**
	 * 
	 * @param builder
	 * @param paramModel
	 */
	private void getMissingConditionForBoolean(StringBuilder builder, ApiParameter paramModel) {
		setCommonRafBuilder(builder);
		builder.append("<Name>RAF-Invalid</Name>");
		setCommonRafBuilderTwo(builder, paramModel);
		builder.append(API_REQUEST_QUERY_PARAMETER + paramModel.getApiParameterName()
				+ " NotEquals \"true\" OR request.queryparam." + paramModel.getApiParameterName()
				+ " NotEquals \"false\"");
		builder.append(API_CONDITION_END);
		newLineAnd4Indents(builder);
		builder.append(API_END_STEP);
	}

	/**
	 * 
	 * @param builder
	 * @param paramModel
	 */
	private void setCommonRafBuilderTwo(StringBuilder builder, ApiParameter paramModel) {
		builder.append(StringUtils.capitalize(paramModel.getApiParameterName()));
		builder.append(API_PARAMETER_NAME);
		newLineAnd5Indents(builder);
		builder.append(API_CONDITION_START);
	}

	/**
	 * 
	 * @param builder
	 */
	private void setCommonRafBuilder(StringBuilder builder) {
		builder.append(API_START_STEP);
		newLineAnd5Indents(builder);
	}

	/**
	 * 
	 * @param rafStepBuilder
	 */
	private void setRafMissingRequestBody(StringBuilder rafStepBuilder) {
		this.requestBodyPolicy = true;
		newLineAnd4Indents(rafStepBuilder);
		setCommonRafBuilder(rafStepBuilder);
		rafStepBuilder.append("<Name>RAF-MissingRequestBody</Name>");
		newLineAnd5Indents(rafStepBuilder);
		rafStepBuilder.append(
				"<Condition>request.content equials null OR request.content.equals \"\" OR request.content equals \"{}\"</Condition>");
		newLineAnd4Indents(rafStepBuilder);
		rafStepBuilder.append(API_END_STEP);
	}

	/**
	 * 
	 * @param list
	 * @return
	 */
	private String getFlows(List<String> list) {
		StringBuilder builder = new StringBuilder(ONE_TAB);
		builder.append("<Flows>");
		builder.append(NEW_LINE);
		for (String s : list) {
			builder.append(s);
		}
		builder.append(getOptionsPreFlightFlow());
		builder.append(getUnknownResourceFlow());
		builder.append(ONE_TAB);
		builder.append("</Flows>");
		builder.append(NEW_LINE);
		return builder.toString();
	}

	/**
	 * 
	 * @return
	 */
	private String getUnknownResourceFlow() {
		StringBuilder builder = new StringBuilder("\t\t<Flow name=\"Unknown Resource\">");
		newLineAnd3Indents(builder);
		builder.append("<Description>Unknown Resource</Description>");
		newLineAnd3Indents(builder);
		builder.append("<Request>#common_policy_unknown_resource#\t\t\t</Request>");
		builder.append(NEW_LINE);
		builder.append(API_RESPONSE_TAG);
		builder.append(API_FLOW_TAG);
		return builder.toString();
	}
	
	/**
	 * 
	 * @return
	 */
	private String getPartProxyManifestContent() {
		StringBuilder builder = new StringBuilder();
		newLineAnd1Indent(builder);
		builder.append("</ProxyEndpoints>");
		newLineAnd1Indent(builder);
		builder.append("<Resources/>");
		newLineAnd1Indent(builder);
		builder.append("<TargetServers/>");
		newLineAnd1Indent(builder);
		builder.append("<TargetEndpoints>");
		builder.append(NEW_LINE);
		return builder.toString();
	}
	/**
	 * 
	 * @return
	 */
	private String getOptionsPreFlightFlow() {
		StringBuilder builder = new StringBuilder(TWO_TABS);
		builder.append("<Flow name = \"OptionsPreFlight \">");
		newLineAnd3Indents(builder);
		builder.append("<Description>Options pre flight</Description>");
		newLineAnd3Indents(builder);
		builder.append("<Request/>");
		builder.append(NEW_LINE);
		builder.append(API_RESPONSE_TAG);
		builder.append(THREE_TABS);
		builder.append(
				"<Condition>(request.verb == \"OPTIONS\" AND request.header.origin != null AND request.header.Access-Control-Request-Method != null)</Condition>");
		builder.append(NEW_LINE);
		builder.append(API_FLOW_TAG);
		return builder.toString();
	}
}
