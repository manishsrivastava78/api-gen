package com.tcs.cma.apis.tools;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.filefilter.AbstractFileFilter;
import org.apache.commons.io.filefilter.IOFileFilter;
import org.apache.commons.io.filefilter.NotFileFilter;
import org.apache.commons.io.filefilter.SuffixFileFilter;
import org.apache.commons.lang3.StringUtils;
import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugins.annotations.Mojo;
import org.apache.maven.plugins.annotations.Parameter;
import org.apache.maven.project.MavenProject;

import com.tcs.cma.apis.tools.constant.ApiConstants;
import com.tcs.cma.apis.tools.models.ApiAllowedParameter;
import com.tcs.cma.apis.tools.models.ApiParameter;
import com.tcs.cma.apis.tools.models.ApiProxyModel;
import com.tcs.cma.apis.tools.models.ApiResource;
import com.tcs.cma.apis.tools.util.ApiUtility;

@Mojo(name = "hello")
public class ApiProxyGenResolver extends AbstractMojo implements ApiConstants {

	@Parameter(required = false, defaultValue = ".")
	private String apiProxySourceDirectory;

	@Parameter(required = false, defaultValue = "./target")
	private String apiProxyDestinationDirectory;

	@Parameter(required = false)
	private String apiDefinition;

	@Parameter(required = false)
	private String apiProxyEndConfig;

	@Parameter(required = false)
	private String apiTargetEndConfig;

	@Parameter(required = false)
	private String commonPolicyDirectory;

	@Parameter(required = false)
	private String apiProxyName;

	@Parameter(required = false)
	private String apiProxyEnvDir;

	@Parameter(required = false)
	private String apiProxyTestDir;

	@Parameter(required = false)
	private String apiProxyManifest;

	@Parameter(required = false)
	private String apiProxyGitIgnore;

	@Parameter(required = false)
	private String apiProxyPackageJson;

	@Parameter(required = false)
	private String apiProxyZipXml;

	@Parameter(required = false)
	private boolean skip;

	@Parameter(required = false)
	private MavenProject mavenProject;

	@Parameter(required = false)
	private String sampleJs;

	@Parameter(required = false)
	private boolean generateProxyConfig;

	@Parameter(required = false)
	private boolean generateProxyPreDeploymentConfig = true;

	@Parameter(required = false)
	private boolean generateProxyPostDeploymentConfig = true;

	private File proxyDir;
	private File targetDir;
	private ApiProxyModel apiModel;

	/**
	 * @return the apiProxySourceDirectory
	 */
	public String getApiProxySourceDirectory() {
		return apiProxySourceDirectory;
	}

	/**
	 * @param apiProxySourceDirectory the apiProxySourceDirectory to set
	 */
	public void setApiProxySourceDirectory(String apiProxySourceDirectory) {
		this.apiProxySourceDirectory = apiProxySourceDirectory;
	}

	/**
	 * @return the apiProxyDestinationDirectory
	 */
	public String getApiProxyDestinationDirectory() {
		return apiProxyDestinationDirectory;
	}

	/**
	 * @param apiProxyDestinationDirectory the apiProxyDestinationDirectory to set
	 */
	public void setApiProxyDestinationDirectory(String apiProxyDestinationDirectory) {
		this.apiProxyDestinationDirectory = apiProxyDestinationDirectory;
	}

	/**
	 * @return the apiDefinition
	 */
	public String getApiDefinition() {
		return apiDefinition;
	}

	/**
	 * @param apiDefinition the apiDefinition to set
	 */
	public void setApiDefinition(String apiDefinition) {
		this.apiDefinition = apiDefinition;
	}

	/**
	 * @return the apiProxyEndConfig
	 */
	public String getApiProxyEndConfig() {
		return apiProxyEndConfig;
	}

	/**
	 * @param apiProxyEndConfig the apiProxyEndConfig to set
	 */
	public void setApiProxyEndConfig(String apiProxyEndConfig) {
		this.apiProxyEndConfig = apiProxyEndConfig;
	}

	/**
	 * @return the apiTargetEndConfig
	 */
	public String getApiTargetEndConfig() {
		return apiTargetEndConfig;
	}

	/**
	 * @param apiTargetEndConfig the apiTargetEndConfig to set
	 */
	public void setApiTargetEndConfig(String apiTargetEndConfig) {
		this.apiTargetEndConfig = apiTargetEndConfig;
	}

	/**
	 * @return the commonPolicyDirectory
	 */
	public String getCommonPolicyDirectory() {
		return commonPolicyDirectory;
	}

	/**
	 * @param commonPolicyDirectory the commonPolicyDirectory to set
	 */
	public void setCommonPolicyDirectory(String commonPolicyDirectory) {
		this.commonPolicyDirectory = commonPolicyDirectory;
	}

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
	 * @return the apiProxyEnvDir
	 */
	public String getApiProxyEnvDir() {
		return apiProxyEnvDir;
	}

	/**
	 * @param apiProxyEnvDir the apiProxyEnvDir to set
	 */
	public void setApiProxyEnvDir(String apiProxyEnvDir) {
		this.apiProxyEnvDir = apiProxyEnvDir;
	}

	/**
	 * @return the apiProxyTestDir
	 */
	public String getApiProxyTestDir() {
		return apiProxyTestDir;
	}

	/**
	 * @param apiProxyTestDir the apiProxyTestDir to set
	 */
	public void setApiProxyTestDir(String apiProxyTestDir) {
		this.apiProxyTestDir = apiProxyTestDir;
	}

	/**
	 * @return the apiProxyManifest
	 */
	public String getApiProxyManifest() {
		return apiProxyManifest;
	}

	/**
	 * @param apiProxyManifest the apiProxyManifest to set
	 */
	public void setApiProxyManifest(String apiProxyManifest) {
		this.apiProxyManifest = apiProxyManifest;
	}

	/**
	 * @return the apiProxyGitIgnore
	 */
	public String getApiProxyGitIgnore() {
		return apiProxyGitIgnore;
	}

	/**
	 * @param apiProxyGitIgnore the apiProxyGitIgnore to set
	 */
	public void setApiProxyGitIgnore(String apiProxyGitIgnore) {
		this.apiProxyGitIgnore = apiProxyGitIgnore;
	}

	/**
	 * @return the apiProxyPackageJson
	 */
	public String getApiProxyPackageJson() {
		return apiProxyPackageJson;
	}

	/**
	 * @param apiProxyPackageJson the apiProxyPackageJson to set
	 */
	public void setApiProxyPackageJson(String apiProxyPackageJson) {
		this.apiProxyPackageJson = apiProxyPackageJson;
	}

	/**
	 * @return the apiProxyZipXml
	 */
	public String getApiProxyZipXml() {
		return apiProxyZipXml;
	}

	/**
	 * @param apiProxyZipXml the apiProxyZipXml to set
	 */
	public void setApiProxyZipXml(String apiProxyZipXml) {
		this.apiProxyZipXml = apiProxyZipXml;
	}

	/**
	 * @return the skip
	 */
	public boolean isSkip() {
		return skip;
	}

	/**
	 * @param skip the skip to set
	 */
	public void setSkip(boolean skip) {
		this.skip = skip;
	}

	/**
	 * @return the mavenProject
	 */
	public MavenProject getMavenProject() {
		return mavenProject;
	}

	/**
	 * @param mavenProject the mavenProject to set
	 */
	public void setMavenProject(MavenProject mavenProject) {
		this.mavenProject = mavenProject;
	}

	/**
	 * @return the sampleJs
	 */
	public String getSampleJs() {
		return sampleJs;
	}

	/**
	 * @param sampleJs the sampleJs to set
	 */
	public void setSampleJs(String sampleJs) {
		this.sampleJs = sampleJs;
	}

	/**
	 * @return the generateProxyConfig
	 */
	public boolean isGenerateProxyConfig() {
		return generateProxyConfig;
	}

	/**
	 * @param generateProxyConfig the generateProxyConfig to set
	 */
	public void setGenerateProxyConfig(boolean generateProxyConfig) {
		this.generateProxyConfig = generateProxyConfig;
	}

	/**
	 * @return the generateProxyPreDeploymentConfig
	 */
	public boolean isGenerateProxyPreDeploymentConfig() {
		return generateProxyPreDeploymentConfig;
	}

	/**
	 * @param generateProxyPreDeploymentConfig the generateProxyPreDeploymentConfig
	 *                                         to set
	 */
	public void setGenerateProxyPreDeploymentConfig(boolean generateProxyPreDeploymentConfig) {
		this.generateProxyPreDeploymentConfig = generateProxyPreDeploymentConfig;
	}

	/**
	 * @return the generateProxyPostDeploymentConfig
	 */
	public boolean isGenerateProxyPostDeploymentConfig() {
		return generateProxyPostDeploymentConfig;
	}

	/**
	 * @param generateProxyPostDeploymentConfig the
	 *                                          generateProxyPostDeploymentConfig to
	 *                                          set
	 */
	public void setGenerateProxyPostDeploymentConfig(boolean generateProxyPostDeploymentConfig) {
		this.generateProxyPostDeploymentConfig = generateProxyPostDeploymentConfig;
	}

	/**
	 * @return the proxyDir
	 */
	public File getProxyDir() {
		return proxyDir;
	}

	/**
	 * @param proxyDir the proxyDir to set
	 */
	public void setProxyDir(File proxyDir) {
		this.proxyDir = proxyDir;
	}

	/**
	 * @return the targetDir
	 */
	public File getTargetDir() {
		return targetDir;
	}

	/**
	 * @param targetDir the targetDir to set
	 */
	public void setTargetDir(File targetDir) {
		this.targetDir = targetDir;
	}

	/**
	 * @return the apiModel
	 */
	public ApiProxyModel getApiModel() {
		return apiModel;
	}

	/**
	 * @param apiModel the apiModel to set
	 */
	public void setApiModel(ApiProxyModel apiModel) {
		this.apiModel = apiModel;
	}

	@Override
	public void execute() throws MojoExecutionException {
		System.out.println("test mojo");
		if (!skip) {
			createProxyDirectoryStructure();
			initTargetsDir();
			List<String> refs = new ArrayList<String>();
			refs.add(apiProxySourceDirectory);
			processPolicyDependencies();
			try {
				generateAsmAllowedParamsFiles();
				generateRafFiles();
				deleteDeploymentConfig(new File(apiProxyDestinationDirectory + File.separator + "env"));
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} else {
			System.out.println("Skipping api proxy gen plugin...");
		}
	}

	/**
	 * 
	 */
	private void createProxyDirectoryStructure() {
		apiModel = ApiProxyModelFactory.getApiModel(apiDefinition);
		String policyDirectory = API_PROXY + File.separator + API_POLICIES;
		String proxiesDirectory = API_PROXY + File.separator + API_PROXIES;
		String resourceDirectory = API_PROXY + File.separator + API_RESOURCES + File.separator + "jsc";
		String targetDirectory = API_PROXY + File.separator + API_TARGETS;
		String proxyFileName = API_PROXY + File.separator + apiProxyName + ".xml";
		File file = null;
		try {
			file = new File(API_PROXY);
			file.mkdir();
			file = new File(policyDirectory);
			file.mkdir();
			file = new File(proxiesDirectory);
			file.mkdir();
			file = new File(proxiesDirectory + File.separator + "default.xml");
			generateDefaultXml(file, apiModel.getProxyEndPointContent(apiModel.getApiBasePath()));
			file = new File(resourceDirectory);
			file.mkdir();
			file = new File(targetDirectory);
			file.mkdir();
			file = new File(targetDirectory + File.separator + "default.xml");
			generateDefaultXml(file, apiModel.getTargetEndPointContent("default", "mocker", "backendPath"));
			file = new File(targetDirectory + File.separator + "sandbox.xml");
			generateDefaultXml(file, apiModel.getTargetEndPointContent("sandbox", "mocker", "sandboxbackendPath"));
			file = new File(proxyFileName);
			FileUtils.writeStringToFile(file, apiModel.getProxyManifestContent(proxyFileName), StandardCharsets.UTF_8);

		} catch (Exception e) {
			// TODO: handle exception
		}
	}

	/**
	 * 
	 * @param file
	 * @param content
	 */
	private void generateDefaultXml(File file, String content) throws IOException {
		FileUtils.writeStringToFile(file, content, StandardCharsets.UTF_8);
	}

	/**
	 * 
	 */
	private void initTargetsDir() {
		try {
			copyProxySrcToTarget();
		} catch (Exception e) {
			// TODO: handle exception
		}
		File policyDir = new File(
				apiProxyDestinationDirectory + File.separator + API_PROXIES + File.separator + API_POLICIES);
		policyDir.mkdirs();
		this.proxyDir = new File(
				apiProxyDestinationDirectory + File.separator + API_PROXIES + File.separator + API_PROXIES);
		this.proxyDir.mkdirs();
		this.targetDir = new File(
				apiProxyDestinationDirectory + File.separator + API_PROXIES + File.separator + API_TARGETS);
		this.targetDir.mkdirs();
		File jsResDir = new File(apiProxyDestinationDirectory + File.separator + API_PROXIES + File.separator
				+ API_RESOURCES + File.separator + "jsc");
		jsResDir.mkdirs();
		new File(apiProxyDestinationDirectory + File.separator + "api_spec").mkdirs();
		try {
			ApiUtility.copyFile(apiProxyManifest, apiProxyDestinationDirectory + File.separator +"./manifest.json");
			ApiUtility.copyFile(apiProxyGitIgnore, apiProxyDestinationDirectory + File.separator +".gitignore");
			ApiUtility.copyDirectory(apiProxyEnvDir, apiProxyDestinationDirectory + File.separator +"env");
			ApiUtility.copyDirectory(apiProxyTestDir, apiProxyDestinationDirectory + File.separator +"test");
			ApiUtility.copyFile(apiProxyPackageJson, apiProxyDestinationDirectory + File.separator +"package.json");
			ApiUtility.copyFile(apiProxyZipXml, apiProxyDestinationDirectory + File.separator +"zip.zml");
			ApiUtility.copyFile(sampleJs, jsResDir.getPath() + File.separator + "sample.js");
			generateReadMe();
			/*
			 * if(generateProxyConfig) { generateApiConfig(); }else {
			 * System.out.println("Skipping to generate API config file..."); }
			 */
		} catch (Exception e) {
			// TODO: handle exception
		}

		String currentDirectory = Paths.get(".").toAbsolutePath().normalize().toString();
		File file = new File(currentDirectory);
		String[] fileList = file.list((dir, name) -> name.toLowerCase().endsWith(".yaml"));
		for (String s : fileList) {
			try {
				FileUtils.copyFile(new File(currentDirectory + File.separator + s),
						new File(apiProxyDestinationDirectory + File.separator + "api_spec" + File.separator + s));
			} catch (Exception e) {
				// TODO: handle exception
			}
		}

	}

	/**
	 * 
	 */
	private void copyProxySrcToTarget() throws IOException {
		final File destDir = new File(apiProxyDestinationDirectory);
		FileUtils.copyDirectory(new File(apiProxySourceDirectory), destDir);
		cleanupDestDir(destDir);
	}

	/**
	 * 
	 * @param file
	 */
	private void cleanupDestDir(File file) {
		final Collection<File> fileToBeDeleted = FileUtils.listFiles(file,
				new NotFileFilter(new SuffixFileFilter(".xml")), getNonResourcesDirFilter());
		for (File f : fileToBeDeleted) {
			FileUtils.deleteQuietly(f);
		}

		File apiProxy = new File(API_PROXY);
		if (apiProxy.exists()) {
			FileUtils.deleteQuietly(new File(API_PROXY));
		} else {
			System.out.println("File does not exist...");
		}
		FileUtils.deleteQuietly(new File(file.getAbsolutePath() + File.separator + "target"));
	}

	/**
	 * 
	 * @return
	 */
	private IOFileFilter getNonResourcesDirFilter() {
		return new AbstractFileFilter() {
			@Override
			public boolean accept(File file) {
				return !file.getPath().contains(File.separator + API_PROXY + File.separator + API_RESOURCES);
			}
		};
	}

	/**
	 * 
	 */
	private void processPolicyDependencies() throws MojoExecutionException {
		try {
			new ApiProxyGenProcessor(proxyDir, commonPolicyDirectory, apiProxyEndConfig, apiTargetEndConfig, targetDir)
					.applyPolicyDependency();
		} catch (Exception e) {
			// TODO: handle exception
		}
	}

	/**
	 * 
	 */
	private void generateAsmAllowedParamsFiles() throws IOException {
		List<ApiResource> apiResourceList = apiModel.getApiResourceList();
		for (ApiResource api : apiResourceList) {
			generateAsmAllowedParamPolicy(api.getApiFlowName(), api.getApiQueryParameters());
		}
	}

	/**
	 * 
	 * @param flowName
	 * @param paramList
	 * @throws IOException
	 */
	private void generateAsmAllowedParamPolicy(String flowName, List<String> paramList) throws IOException {
		File file = new File(apiProxyDestinationDirectory + File.separator + API_PROXY + File.separator + API_POLICIES
				+ File.separator + ApiUtility.getAsmAllowedParamsPolicy(flowName));
		writeFile(file, ApiUtility.generateFileContents(getAsmAllowedParamModel(flowName, paramList),
				"templates/asm-allowed-params.mustache"));
	}

	/**
	 * 
	 * @param flowName
	 * @param paramList
	 * @return
	 */
	private ApiAllowedParameter getAsmAllowedParamModel(String flowName, List<String> paramList) {
		ApiAllowedParameter model = new ApiAllowedParameter();
		model.setApiProxyFlowName(StringUtils.capitalize(flowName));
		model.setApiProxyParameterList(getAllowedParamList(paramList));
		return model;
	}

	/**
	 * 
	 * @param paramList
	 * @return
	 */
	private String getAllowedParamList(List<String> paramList) {
		StringBuilder builder = new StringBuilder();
		int counter = 1;
		int paramListLen = paramList.size();
		for (String s : paramList) {
			if (counter == paramListLen) {
				builder.append(s.trim());
			} else {
				builder.append(s.trim() + ",");
				counter++;
			}
		}
		return builder.toString();
	}

	/**
	 * 
	 */
	private void generateRafFiles() throws IOException {
		List<ApiResource> apiResourceList = apiModel.getApiResourceList();
		for (ApiResource api : apiResourceList) {
			generateRafPolicy(api.getApiParameterModelList());
		}
		if (apiModel.isApiRequestBodyPolicy()) {
			File rafPolicy = new File(apiProxyDestinationDirectory + File.separator + API_PROXY + File.separator
					+ API_POLICIES + File.separator + ApiUtility.getMissingBodyRafFile());
			writeFile(rafPolicy, ApiUtility.generateFileContents("", "templates/raf-missing-request.mustache"));
		} else {
			System.out.println("Skipping to create RAF policy...");
		}
	}

	/**
	 * 
	 * @param apiParameters
	 */
	private void generateRafPolicy(List<ApiParameter> apiParameters) throws IOException {
		if (null == apiParameters)
			return;
		File rafPolicy = null;
		for (ApiParameter apiParam : apiParameters) {
			if (apiParam.isApiParameterRequired()) {
				rafPolicy = new File(
						apiProxyDestinationDirectory + File.separator + API_PROXY + File.separator + API_POLICIES
								+ File.separator + ApiUtility.getMissingParamsRafFile(apiParam.getApiParameterName()));
				writeFile(rafPolicy, ApiUtility.generateFileContents(apiParam, "templates/raf-missing-param.mustache"));
				if (null != apiParam.getApiParameterEnum() && !apiParam.getApiParameterEnum().isEmpty()) {
					rafPolicy = new File(apiProxyDestinationDirectory + File.separator + API_PROXY + File.separator
							+ API_POLICIES + File.separator
							+ ApiUtility.getInvalidParamsRafFile(apiParam.getApiParameterName()));
					writeFile(rafPolicy,
							ApiUtility.generateFileContents(apiParam, "templates/raf-invalid-param.mustache"));
				}
			}
		}
	}

	/**
	 * 
	 */
	private void deleteDeploymentConfig(File file) throws IOException {
		if (!isGenerateProxyPreDeploymentConfig() && !isGenerateProxyPostDeploymentConfig()) {
			deletePreDeploymentConfig(file);
			deletePostDeploymentConfig(file);
		} else if (!isGenerateProxyPreDeploymentConfig()) {
			deletePreDeploymentConfig(file);
		} else if (!isGenerateProxyPostDeploymentConfig()) {
			deletePostDeploymentConfig(file);
		} else {
			System.out.println("Generate pre/post deployment config...");
		}
	}

	/**
	 * 
	 * @param file
	 * @throws IOException
	 */
	private void deletePreDeploymentConfig(File file) throws IOException {
		for (File f : file.listFiles()) {
			if (f.getName().equals("default")) {
				System.out.println("No operation on defualt config...");
			} else {
				for (File f1 : f.listFiles()) {
					if (f1.getName().equals("org-config") || f1.getName().equals("config.properties")) {
						System.out.println("Keep org-config....");
					} else {
						FileUtils.forceDelete(f1);
					}
				}
			}
		}
	}

	/**
	 * 
	 * @param file
	 * @throws IOException
	 */
	private void deletePostDeploymentConfig(File file) throws IOException {
		File env = null;
		for (File f : file.listFiles()) {
			if (f.getName().equals("default")) {
				System.out.println("No operation on defualt config...");
			} else {
				env = new File(f.getAbsolutePath() + File.separator + "org-config");
				FileUtils.deleteDirectory(env);
			}
		}
	}

	/**
	 * 
	 * @param source
	 * @param destination
	 * @throws IOException
	 */
	/*private void copyFile(String source, String destination) throws IOException {
		FileUtils.copyFile(new File(source), new File(apiProxyDestinationDirectory + File.separator + destination));
	}*/

	/**
	 * 
	 * @param source
	 * @param destination
	 * @throws IOException
	 */
	/*private void copyDirectory(String source, String destination) throws IOException {
		FileUtils.copyDirectory(new File(source),
				new File(apiProxyDestinationDirectory + File.separator + destination));
	}*/

	/**
	 * 
	 * @param file
	 * @param content
	 * @throws IOException
	 */
	private void writeFile(File file, String content) throws IOException {
		try (FileWriter fileWriter = new FileWriter(file)) {
			fileWriter.write(content);
		}
	}

	/**
	 * 
	 * @param source
	 * @param destination
	 * @throws IOException
	 */
	/*private void copyJsFile(String source, String destination) throws IOException {
		FileUtils.copyFile(new File(source), new File(destination));
	}*/

	/**
	 * 
	 * @throws IOException
	 */
	private void generateReadMe() throws IOException {
		List<ApiResource> apiResourceList = apiModel.getApiResourceList();
		File readMeFile = new File(apiProxyDestinationDirectory + File.separator + "README.md");
		StringBuilder builder = new StringBuilder();
		builder.append("# Introduction\n");
		builder.append(apiModel.getApiDescription() + "\n");
		builder.append("\n## API Documentation\n");
		builder.append("\n#### Basepath:" + apiModel.getApiBasePath() + "\n");
		builder.append("| Path | HTTP Verb | Decription|\n| ----- |----- |----- |\n");
		for (ApiResource resource : apiResourceList) {
			builder.append(getTable(resource));
		}
		writeFile(readMeFile, builder.toString());
	}
	
	/**
	 * 
	 * @param apiResource
	 * @return
	 */
	private String getTable(ApiResource apiResource) {
		StringBuilder builder = new StringBuilder();
		builder.append("|" + apiResource.getApiEndPoint() + "|" + apiResource.getApiHttpVerb() + "|"
				+ apiResource.getApiFlowDesc() + "|\n");
		return builder.toString();
	}

}
