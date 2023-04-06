package com.annwyn.niflheim.generator.transfer.impl;

import com.annwyn.niflheim.generator.models.parameters.RepositoryParameters;
import com.annwyn.niflheim.generator.models.parameters.TransferParameters;
import com.annwyn.niflheim.generator.transfer.TransferContext;
import com.annwyn.niflheim.generator.transfer.TransferProcess;
import com.annwyn.niflheim.utils.ParamUtils;
import org.springframework.stereotype.Component;

import java.nio.file.Path;
import java.util.Arrays;


@Component
public class RepositoryTransferProcess extends TransferProcess {

	@Override
	public void writeFile(TransferContext transferContext) throws Exception {
		final Path outputPath = this.joinPath(transferContext.getRepositoryPackage(), transferContext.getRepositoryClassName() + ".java");
		final TransferParameters transferParameters = this.buildParameters(transferContext);
		this.doWriteFile("templates/repository.java.ftl", outputPath, transferParameters);
	}

	private RepositoryParameters buildParameters(TransferContext transferContext) {
		RepositoryParameters repositoryParameters = new RepositoryParameters();
		repositoryParameters.setTable(transferContext.getTableEntity());
		repositoryParameters.setClassName(transferContext.getRepositoryClassName());
		repositoryParameters.setPackageName(transferContext.getRepositoryPackage());

		repositoryParameters.setModelClassName(transferContext.getModelClassName());
		repositoryParameters.setMapperClassName(transferContext.getMapperClassName());

		repositoryParameters.setListResponseClassName(transferContext.getListResponseClassName());
		repositoryParameters.setListRequestClassName(transferContext.getListRequestClassName());
		repositoryParameters.setDetailResponseClassName(transferContext.getDetailResponseClassName());

		repositoryParameters.setImportPackages(Arrays.asList( //
			ParamUtils.joinPackage(transferContext.getResponsePackage(), "*"), //
			ParamUtils.joinPackage(transferContext.getRequestPackage(), "*"), //
			ParamUtils.joinPackage(transferContext.getModelPackage(), transferContext.getModelClassName()), //
			ParamUtils.joinPackage(transferContext.getMapperJavaPackage(), transferContext.getMapperClassName()) //
		));

		return repositoryParameters;
	}
}
