package com.annwyn.niflheim.generator.transfer.impl;

import com.annwyn.niflheim.generator.models.parameters.MapperParameters;
import com.annwyn.niflheim.generator.models.parameters.TransferParameters;
import com.annwyn.niflheim.generator.transfer.TransferContext;
import com.annwyn.niflheim.generator.transfer.TransferProcess;
import com.annwyn.niflheim.utils.ParamUtils;
import org.springframework.stereotype.Component;

import java.nio.file.Path;
import java.util.Arrays;


@Component
public class MapperTransferProcess extends TransferProcess {

	@Override
	public void writeFile(TransferContext transferContext) throws Exception {
		TransferParameters transferParameters = this.buildParameters(transferContext);

		final Path javaPath = this.joinPath(transferContext.getMapperJavaPackage(), transferContext.getMapperClassName() + ".java");
		this.doWriteFile("templates/mapper.java.ftl", javaPath, transferParameters);

		final Path xmlPath = this.joinPath(transferContext.getMapperXmlPackage(), transferContext.getMapperClassName() + ".xml");
		this.doWriteFile("templates/mapper.xml.ftl", xmlPath, transferParameters);
	}

	private TransferParameters buildParameters(TransferContext transferContext) {
		MapperParameters mapperParameters = new MapperParameters();
		mapperParameters.setTable(transferContext.getTableEntity());
		mapperParameters.setClassName(transferContext.getMapperClassName());
		mapperParameters.setPackageName(transferContext.getMapperJavaPackage());

		mapperParameters.setModelClassName(transferContext.getModelClassName());
		mapperParameters.setResponsePackage(transferContext.getResponsePackage());
		mapperParameters.setListResponseClassName(transferContext.getListResponseClassName());
		mapperParameters.setListRequestClassName(transferContext.getListRequestClassName());
		mapperParameters.setDetailResponseClassName(transferContext.getDetailResponseClassName());

		mapperParameters.setImportPackages(Arrays.asList( //
			ParamUtils.joinPackage(transferContext.getModelPackage(), transferContext.getModelClassName()), //
			ParamUtils.joinPackage(transferContext.getResponsePackage(), "*"), //
			ParamUtils.joinPackage(transferContext.getRequestPackage(), "*") //
		));
		return mapperParameters;
	}

}
