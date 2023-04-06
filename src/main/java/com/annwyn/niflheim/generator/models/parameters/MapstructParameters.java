package com.annwyn.niflheim.generator.models.parameters;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

@Data
@EqualsAndHashCode(callSuper = true)
public class MapstructParameters extends TransferParameters implements Serializable {

	private String modelClassName;

	private String saveRequestClassName;

}
