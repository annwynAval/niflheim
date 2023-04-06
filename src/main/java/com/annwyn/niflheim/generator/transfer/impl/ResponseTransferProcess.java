package com.annwyn.niflheim.generator.transfer.impl;

import com.annwyn.niflheim.generator.models.parameters.ResponseParameters;
import com.annwyn.niflheim.generator.models.parameters.TransferParameters;
import com.annwyn.niflheim.generator.transfer.TransferContext;
import com.annwyn.niflheim.generator.transfer.TransferProcess;
import com.annwyn.niflheim.utils.ParamUtils;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.nio.file.Path;
import java.util.Arrays;
import java.util.stream.Collectors;


@Component
public class ResponseTransferProcess extends TransferProcess {

	@Override
	public void writeFile(TransferContext transferContext) throws Exception {
		final TransferParameters transferParameters = this.buildParameters(transferContext);

		transferParameters.setClassName(transferContext.getListResponseClassName());
		final Path listPath = this.joinPath(transferContext.getResponsePackage(), transferContext.getListResponseClassName() + ".java");
		this.doWriteFile("templates/response.java.ftl", listPath, transferParameters);

		transferParameters.setClassName(transferContext.getDetailResponseClassName());
		final Path detailPath = this.joinPath(transferContext.getResponsePackage(), transferContext.getDetailResponseClassName() + ".java");
		this.doWriteFile("templates/response.java.ftl", detailPath, transferParameters);
	}

	private TransferParameters buildParameters(TransferContext transferContext) {
		ResponseParameters responseParameters = new ResponseParameters();
		responseParameters.setTable(transferContext.getTableEntity());
		responseParameters.setPackageName(transferContext.getServicePackage());

		responseParameters.setImportPackages(transferContext.getTableEntity().getFields().stream() //
			.map(item -> item.getPropertyType().getPackageName()) //
			.filter(StringUtils::hasText) //
			.distinct().collect(Collectors.toList()));
		return responseParameters;
	}

}
