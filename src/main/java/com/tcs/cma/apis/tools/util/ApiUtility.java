package com.tcs.cma.apis.tools.util;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;

import com.samskivert.mustache.Mustache;

/**
 * 
 * @author Manish Srivastava
 * 19 Sep 2019
 */
public class ApiUtility {
	
	/**
	 * 
	 */
	private ApiUtility() {
		
	}
	
	/**
	 * 
	 * @param data
	 * @param template
	 * @return
	 */
	public static final String generateFileContents(Object data,String templateFile) {
		String template = getTemplate(templateFile);
		if(null!=template) {
			return Mustache.compiler().escapeHTML(false).defaultValue("").compile(template).execute(data);
		}else {
			return null;
		}
		
	}
	
	/**
	 * 
	 * @param file
	 * @return
	 */
	private static String getTemplate(String file) {
		String results = null;
		ClassLoader clazzLoader = ApiUtility.class.getClassLoader();
		try {
			results = IOUtils.toString(clazzLoader.getResourceAsStream(file),StandardCharsets.UTF_8);
		}catch (Exception e) {
			// TODO: handle exception
		}
		return results;
	}
	
	/**
	 * 
	 * @param apiFlowName
	 * @return
	 */
	public static final String getAsmAllowedParamsPolicy(String apiFlowName) {
		StringBuilder fileName = new StringBuilder();
		fileName.append("ASM-");
		fileName.append(StringUtils.capitalize(apiFlowName));
		fileName.append("AllowedParameters.xml");
		return fileName.toString();
	}

	/**
	 * 
	 * @param content
	 * @param charSet
	 * @return
	 */
	public static final String removeCharSetAndCapsString(String content,String charSet) {
		return StringUtils.capitalize(content.replaceAll(charSet, "")).trim();
	}
	
	/**
	 * 
	 * @param apiOperationId
	 * @param httpVerb
	 * @param apiPath
	 * @return
	 */
	public static final String getApiOperationId(String apiOperationId,String httpVerb,String apiPath) {
		if(null!=apiOperationId && !apiOperationId.isEmpty()) {
			return apiOperationId;
		}else {
			httpVerb = httpVerb.toLowerCase();
			StringBuilder builder = new StringBuilder();
			String[] apiPaths = apiPath.split("/");
			for(String s: apiPaths) {
				builder.append(removeCharSetAndCapsString(s, "[{}]"));
			}
			return builder.toString();
		}
	}
	
	/**
	 * 
	 * @param parameterName
	 * @return
	 */
	public static final String getMissingParamsRafFile(String parameterName) {
		StringBuilder builder  = new StringBuilder();
		builder.append("RAF-Missing");
		builder.append(StringUtils.capitalize(parameterName));
		builder.append("Parameter.xml");
		return builder.toString();
	}
	
	/**
	 * 
	 * @param parameterName
	 * @return
	 */
	public static final String getInvalidParamsRafFile(String parameterName) {
		StringBuilder builder  = new StringBuilder();
		builder.append("RAF-Invalid");
		builder.append(StringUtils.capitalize(parameterName));
		builder.append("Parameter.xml");
		return builder.toString();
	}
	
	/**
	 * 
	 * @return
	 */
	public static final String getMissingBodyRafFile() {
		return "RAF-MissingRequestBody.xml";
	}
	
	/**
	 * 
	 * @param parameterName
	 * @return
	 */
	public static final String getInvalidJsRafFile(String parameterName) {
		StringBuilder builder = new StringBuilder();
		builder.append("Validate");
		builder.append(StringUtils.capitalize(parameterName));
		builder.append("Parameter.js");
		return builder.toString();
	}
	
	/**
	 * 
	 * @param parameterName
	 * @return
	 */
	public static final String getJsValidateParamPolicyFile(String parameterName) {
		StringBuilder builder = new StringBuilder();
		builder.append("JAS-VAlidate");
		builder.append(StringUtils.capitalize(parameterName));
		builder.append("Parameter.xml");
		return builder.toString();
	}
	
	/**
	 * 
	 * @param source
	 * @param destination
	 * @throws IOException
	 */
	public static void copyFile(String source, String destination) throws IOException {
		FileUtils.copyFile(new File(source), new File(destination));
	}

	/**
	 * 
	 * @param source
	 * @param destination
	 * @throws IOException
	 */
	public static void copyDirectory(String source, String destination) throws IOException {
		FileUtils.copyDirectory(new File(source),new File(destination));
	}

}
