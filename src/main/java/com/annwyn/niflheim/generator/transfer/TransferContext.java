package com.annwyn.niflheim.generator.transfer;

import com.annwyn.niflheim.generator.models.TableEntity;
import lombok.Data;

import java.io.Serializable;



@Data
public class TransferContext implements Serializable {

	private TableEntity tableEntity;
	private String basePackageName;

	private String modelClassName;
	private String modelPropertyName;
	private String modelPackage;

	private String mapperClassName;
	private String mapperPropertyName;
	private String mapperJavaPackage;
	private String mapperXmlPackage;

	private String repositoryClassName;
	private String repositoryPropertyName;
	private String repositoryPackage;

	private String serviceClassName;
	private String serviceImplClassName;
	private String servicePropertyName;
	private String servicePackage;
	private String serviceImplPackage;

	private String mapstructClassName;
	private String mapstructPropertyName;
	private String mapstructPackage;

	private String controllerClassName;
	private String controllerPackage;

	private String responsePackage;
	private String requestPackage;

	private String listResponseClassName;
	private String detailResponseClassName;
	private String listRequestClassName;
	private String saveRequestClassName;

	private String queryPackageName;

}
