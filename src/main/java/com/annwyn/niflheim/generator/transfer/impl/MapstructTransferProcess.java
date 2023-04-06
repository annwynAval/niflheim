package com.annwyn.niflheim.generator.transfer.impl;

import com.annwyn.niflheim.generator.models.parameters.MapstructParameters;
import com.annwyn.niflheim.generator.models.parameters.TransferParameters;
import com.annwyn.niflheim.generator.transfer.TransferContext;
import com.annwyn.niflheim.generator.transfer.TransferProcess;
import com.annwyn.niflheim.utils.ParamUtils;
import org.springframework.stereotype.Component;

import java.nio.file.Path;
import java.util.Arrays;

@Component
public class MapstructTransferProcess extends TransferProcess {

	@Override
	public void writeFile(TransferContext transferContext) throws Exception {
		final Path outputPath = this.joinPath(transferContext.getMapstructPackage(), transferContext.getMapstructClassName() + ".java");
		final TransferParameters transferParameters = this.buildParameters(transferContext);
		this.doWriteFile("templates/mapstruct.java.ftl", outputPath, transferParameters);
	}

	private TransferParameters buildParameters(TransferContext transferContext) {
		MapstructParameters mapstructParameters = new MapstructParameters();
		mapstructParameters.setTable(transferContext.getTableEntity());
		mapstructParameters.setClassName(transferContext.getMapstructClassName());
		mapstructParameters.setPackageName(transferContext.getMapstructPackage());

		mapstructParameters.setModelClassName(transferContext.getModelClassName());
		mapstructParameters.setSaveRequestClassName(transferContext.getSaveRequestClassName());

		mapstructParameters.setImportPackages(Arrays.asList( //
			ParamUtils.joinPackage(transferContext.getModelPackage(), transferContext.getModelClassName()), //
			ParamUtils.joinPackage(transferContext.getRequestPackage(), transferContext.getSaveRequestClassName()) //
		));

		return mapstructParameters;
	}

}
