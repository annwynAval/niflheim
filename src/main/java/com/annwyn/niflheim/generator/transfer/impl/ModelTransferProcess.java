package com.annwyn.niflheim.generator.transfer.impl;

import com.annwyn.niflheim.generator.models.parameters.ModelParameters;
import com.annwyn.niflheim.generator.models.parameters.TransferParameters;
import com.annwyn.niflheim.generator.transfer.TransferContext;
import com.annwyn.niflheim.generator.transfer.TransferProcess;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.nio.file.Path;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class ModelTransferProcess extends TransferProcess {

	@Override
	public void writeFile(TransferContext transferContext) throws Exception {
		final Path outputPath = this.joinPath(transferContext.getModelPackage(), transferContext.getModelClassName() + ".java");
		final TransferParameters transferParameters = this.buildParameters(transferContext);
		this.doWriteFile("templates/model.java.ftl", outputPath, transferParameters);
	}

	private TransferParameters buildParameters(TransferContext transferContext) {
		ModelParameters modelParameters = new ModelParameters();
		modelParameters.setTable(transferContext.getTableEntity());
		modelParameters.setClassName(transferContext.getModelClassName());
		modelParameters.setPackageName(transferContext.getModelPackage());

		modelParameters.setImportPackages(transferContext.getTableEntity().getFields().stream() //
			.map(item -> item.getPropertyType().getPackageName()) //
			.filter(StringUtils::hasText) //
			.distinct().collect(Collectors.toList()));
		return modelParameters;
	}
}

