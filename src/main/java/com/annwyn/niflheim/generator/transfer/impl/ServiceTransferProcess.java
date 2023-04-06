package com.annwyn.niflheim.generator.transfer.impl;

import com.annwyn.niflheim.generator.models.parameters.ServiceParameters;
import com.annwyn.niflheim.generator.models.parameters.TransferParameters;
import com.annwyn.niflheim.generator.transfer.TransferContext;
import com.annwyn.niflheim.generator.transfer.TransferProcess;
import com.annwyn.niflheim.utils.ParamUtils;
import org.springframework.stereotype.Component;

import java.nio.file.Path;
import java.util.Arrays;
import java.util.List;

@Component
public class ServiceTransferProcess extends TransferProcess {

	@Override
	public void writeFile(TransferContext transferContext) throws Exception {
		final TransferParameters transferParameters = this.buildParameters(transferContext);

		transferParameters.setClassName(transferContext.getServiceClassName());
		transferParameters.setImportPackages(this.getServicePackages(transferContext));
		final Path interfacePath = this.joinPath(transferContext.getServicePackage(), transferContext.getServiceClassName() + ".java");
		this.doWriteFile("templates/service.java.ftl", interfacePath, transferParameters);

		transferParameters.setClassName(transferContext.getServiceImplClassName());
		transferParameters.setImportPackages(this.getImplPackages(transferContext));
		final Path implementPath = this.joinPath(transferContext.getServiceImplPackage(), transferContext.getServiceImplClassName() + ".java");
		this.doWriteFile("templates/serviceImpl.java.ftl", implementPath, transferParameters);
	}

	private ServiceParameters buildParameters(TransferContext transferContext) {
		ServiceParameters serviceParameters = new ServiceParameters();
		serviceParameters.setTable(transferContext.getTableEntity());
		serviceParameters.setClassName(transferContext.getServiceClassName());
		serviceParameters.setPackageName(transferContext.getServicePackage());

		serviceParameters.setBasePackageName(transferContext.getBasePackageName());
		serviceParameters.setServiceClassName(transferContext.getServiceClassName());
		serviceParameters.setMapstructClassName(transferContext.getMapstructClassName());
		serviceParameters.setMapstructPropertyName(transferContext.getMapstructPropertyName());
		serviceParameters.setRepositoryClassName(transferContext.getRepositoryClassName());
		serviceParameters.setRepositoryPropertyName(transferContext.getRepositoryPropertyName());
		serviceParameters.setModelClassName(transferContext.getModelClassName());

		serviceParameters.setListResponseClassName(transferContext.getListResponseClassName());
		serviceParameters.setListRequestClassName(transferContext.getListRequestClassName());
		serviceParameters.setDetailResponseClassName(transferContext.getDetailResponseClassName());
		serviceParameters.setSaveRequestClassName(transferContext.getSaveRequestClassName());
		return serviceParameters;
	}

	private List<String> getServicePackages(TransferContext transferContext) {
		return Arrays.asList( //
			ParamUtils.joinPackage(transferContext.getQueryPackageName(), "*"), //
			ParamUtils.joinPackage(transferContext.getResponsePackage(), "*"), //
			ParamUtils.joinPackage(transferContext.getRequestPackage(), "*") //
		);
	}

	private List<String> getImplPackages(TransferContext transferContext) {
		return Arrays.asList(
			ParamUtils.joinPackage(transferContext.getQueryPackageName(), "*"), //
			ParamUtils.joinPackage(transferContext.getResponsePackage(), "*"), //
			ParamUtils.joinPackage(transferContext.getRequestPackage(), "*"), //
			ParamUtils.joinPackage(transferContext.getModelPackage(), transferContext.getModelClassName()), //
			ParamUtils.joinPackage(transferContext.getServicePackage(), transferContext.getServiceClassName()), //
			ParamUtils.joinPackage(transferContext.getMapstructPackage(), transferContext.getMapstructClassName()), //
			ParamUtils.joinPackage(transferContext.getRepositoryPackage(), transferContext.getRepositoryClassName()) //
		);
	}



}
