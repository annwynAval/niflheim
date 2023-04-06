package com.annwyn.niflheim.generator.models.parameters;

import com.annwyn.niflheim.generator.models.TableEntity;
import com.annwyn.niflheim.generator.models.TableFieldEntity;
import lombok.Data;

import java.io.Serializable;
import java.util.List;


@Data
public class TransferParameters implements Serializable {

	private TableEntity table;

	private String className;

	private String packageName;

	private List<String> importPackages;

}
