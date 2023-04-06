package com.annwyn.niflheim.generator.models.parameters;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

@Data
@EqualsAndHashCode(callSuper = true)
public class ServiceParameters extends TransferParameters implements Serializable {

	private String basePackageName;

	private String serviceClassName;
	private String modelClassName;

	private String repositoryClassName;
	private String repositoryPropertyName;

	private String mapstructClassName;
	private String mapstructPropertyName;

	private String listResponseClassName;
	private String listRequestClassName;
	private String detailResponseClassName;
	private String saveRequestClassName;

}
