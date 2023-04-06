package com.annwyn.niflheim.generator.models.parameters;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

@Data
@EqualsAndHashCode(callSuper = true)
public class MapperParameters extends TransferParameters implements Serializable {

	private String modelClassName;

	private String responsePackage;

	private String listResponseClassName;
	private String listRequestClassName;
	private String detailResponseClassName;

}
