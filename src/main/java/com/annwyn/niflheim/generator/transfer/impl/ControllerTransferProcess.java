package com.annwyn.niflheim.generator.transfer.impl;

import com.annwyn.niflheim.generator.models.parameters.ControllerParameters;
import com.annwyn.niflheim.generator.models.parameters.TransferParameters;
import com.annwyn.niflheim.generator.transfer.TransferContext;
import com.annwyn.niflheim.generator.transfer.TransferProcess;
import com.annwyn.niflheim.utils.ParamUtils;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.nio.file.Path;
import java.util.Arrays;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Component
public class ControllerTransferProcess extends TransferProcess {

	@Override
	public void writeFile(TransferContext transferContext) throws Exception {
		final Path outputPath = this.joinPath(transferContext.getControllerPackage(), transferContext.getControllerClassName() + ".java");
		final TransferParameters transferParameters = this.buildParameters(transferContext);
		this.doWriteFile("templates/controller.java.ftl", outputPath, transferParameters);
	}

	private ControllerParameters buildParameters(TransferContext transferContext) {
		ControllerParameters controllerParameters = new ControllerParameters();
		controllerParameters.setTable(transferContext.getTableEntity());
		controllerParameters.setClassName(transferContext.getControllerClassName());
		controllerParameters.setPackageName(transferContext.getControllerPackage());

		controllerParameters.setServiceClassName(transferContext.getServiceClassName());
		controllerParameters.setServicePropertyName(transferContext.getServicePropertyName());

		controllerParameters.setListResponseClassName(transferContext.getListResponseClassName());
		controllerParameters.setListRequestClassName(transferContext.getListRequestClassName());
		controllerParameters.setDetailResponseClassName(transferContext.getDetailResponseClassName());
		controllerParameters.setSaveRequestClassName(transferContext.getSaveRequestClassName());

		controllerParameters.setRequestUrl( //
			Stream.of("/api", this.niflheimProperties.getModuleName(), transferContext.getModelPropertyName()) //
				.filter(StringUtils::hasText).collect(Collectors.joining("/")) //
		);
		controllerParameters.setImportPackages(Arrays.asList( //
			ParamUtils.joinPackage(transferContext.getQueryPackageName(), "*"), //
			ParamUtils.joinPackage(transferContext.getResponsePackage(), "*"), //
			ParamUtils.joinPackage(transferContext.getRequestPackage(), "*"), //
			ParamUtils.joinPackage(transferContext.getServicePackage(), transferContext.getServiceClassName()) //
		));

		return controllerParameters;
	}
}
