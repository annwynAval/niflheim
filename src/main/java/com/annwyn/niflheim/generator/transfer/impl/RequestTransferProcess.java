package com.annwyn.niflheim.generator.transfer.impl;

import com.annwyn.niflheim.generator.models.parameters.RequestParameters;
import com.annwyn.niflheim.generator.models.parameters.TransferParameters;
import com.annwyn.niflheim.generator.transfer.TransferContext;
import com.annwyn.niflheim.generator.transfer.TransferProcess;
import com.annwyn.niflheim.utils.ParamUtils;
import lombok.Data;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.nio.file.Path;
import java.util.Arrays;
import java.util.stream.Collectors;


@Component
public class RequestTransferProcess extends TransferProcess {


	@Override
	public void writeFile(TransferContext transferContext) throws Exception {
		final RequestParameters transferParameters = this.buildParameters(transferContext);

		transferParameters.setClassName(transferContext.getListRequestClassName());
		transferParameters.setPaginationRequest(true);
		transferParameters.setSaveRequest(false);
		final Path listPath = this.joinPath(transferContext.getRequestPackage(), transferContext.getListRequestClassName() + ".java");
		this.doWriteFile("templates/request.java.ftl", listPath, transferParameters);

		transferParameters.setClassName(transferContext.getSaveRequestClassName());
		transferParameters.setPaginationRequest(false);
		transferParameters.setSaveRequest(true);
		final Path detailPath = this.joinPath(transferContext.getRequestPackage(), transferContext.getSaveRequestClassName() + ".java");
		this.doWriteFile("templates/request.java.ftl", detailPath, transferParameters);
	}

	private RequestParameters buildParameters(TransferContext transferContext) {
		RequestParameters requestParameters = new RequestParameters();
		requestParameters.setTable(transferContext.getTableEntity());
		requestParameters.setPackageName(transferContext.getRequestPackage());
		requestParameters.setQueryPackageName(transferContext.getQueryPackageName());

		requestParameters.setImportPackages(transferContext.getTableEntity().getFields().stream() //
			.map(item -> item.getPropertyType().getPackageName()) //
			.filter(StringUtils::hasText) //
			.distinct().collect(Collectors.toList()));
		return requestParameters;
	}
}
