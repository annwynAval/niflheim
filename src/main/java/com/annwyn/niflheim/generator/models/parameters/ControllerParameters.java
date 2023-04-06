package com.annwyn.niflheim.generator.models.parameters;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

@Data
@EqualsAndHashCode(callSuper = true)
public class ControllerParameters extends TransferParameters implements Serializable {

	private String requestUrl;

	private String serviceClassName;
	private String servicePropertyName;

	private String listResponseClassName;
	private String listRequestClassName;
	private String detailResponseClassName;
	private String saveRequestClassName;

}
