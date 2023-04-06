package com.annwyn.niflheim.generator.models.parameters;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

@Data
@EqualsAndHashCode(callSuper = true)
public class RequestParameters extends TransferParameters implements Serializable {

	private boolean paginationRequest;

	private boolean saveRequest;

	private String queryPackageName;
}
