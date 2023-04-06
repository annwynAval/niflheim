package com.annwyn.niflheim.generator.transfer;

import com.annwyn.niflheim.configuration.properties.NiflheimProperties;
import com.annwyn.niflheim.generator.configure.keyword.KeywordHandle;
import com.annwyn.niflheim.generator.configure.naming.NamingStrategy;
import com.annwyn.niflheim.generator.configure.registry.ColumnRegistry;
import com.annwyn.niflheim.generator.models.TableEntity;
import com.annwyn.niflheim.generator.models.TableFieldEntity;
import com.annwyn.niflheim.utils.ParamUtils;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

@Component
public class TransferContextFactory {

	@Resource
	private NiflheimProperties niflheimProperties;

	@Resource
	private NamingStrategy namingStrategy;

	@Resource
	private ColumnRegistry columnRegistry;

	@Resource
	private KeywordHandle keywordHandle;

	public TransferContext buildTransferContext(TableEntity tableEntity) {
		// 先处理字段属性信息
		for (TableFieldEntity tableFieldEntity : tableEntity.getFields()) {
			tableFieldEntity.setPropertyType(this.columnRegistry.getColumnType(tableFieldEntity.getJdbcType()));
			tableFieldEntity.setPropertyName(this.namingStrategy.switchFieldName(tableFieldEntity.getFieldName()));
			if (this.keywordHandle.isKeyWords(tableFieldEntity.getFieldName())) {
				tableFieldEntity.setKeyWord(true); // 如果是关键字的话, 需要改变下
				tableFieldEntity.setFieldName(this.keywordHandle.formatColumn(tableFieldEntity.getFieldName()));
			}
			if(tableFieldEntity.isPrimaryKey()) { // 获取主键信息
				tableEntity.setPrimaryKey(tableFieldEntity);
			}
		}

		final String moduleClassName = this.namingStrategy.switchClassName(this.niflheimProperties.getModuleName());
		final TransferContext transferContext = new TransferContext();
		transferContext.setTableEntity(tableEntity);
		transferContext.setBasePackageName(this.niflheimProperties.getPackageName());

		transferContext.setModelClassName(this.namingStrategy.switchClassName(tableEntity.getTableName()));
		transferContext.setModelPropertyName(this.namingStrategy.switchFieldName(transferContext.getModelClassName()));
		transferContext.setModelPackage(this.joinMybatisPackage("models"));

		transferContext.setMapperClassName(String.format("%sMapper", transferContext.getModelClassName()));
		transferContext.setMapperPropertyName(this.namingStrategy.switchFieldName(transferContext.getMapperClassName()));
		transferContext.setMapperJavaPackage(this.joinMybatisPackage("mapper"));
		transferContext.setMapperXmlPackage("mappers");

		transferContext.setRepositoryClassName(String.format("%sRepository", transferContext.getModelClassName()));
		transferContext.setRepositoryPropertyName(this.namingStrategy.switchFieldName(transferContext.getRepositoryClassName()));
		transferContext.setRepositoryPackage(this.joinMybatisPackage("repository"));

		transferContext.setServiceClassName(String.format("%s%sService", moduleClassName, transferContext.getModelClassName()));
		transferContext.setServicePropertyName(this.namingStrategy.switchFieldName(transferContext.getServiceClassName()));
		transferContext.setServicePackage(this.joinCorePackage("service"));
		transferContext.setServiceImplClassName(String.format("%s%sServiceImpl", moduleClassName, transferContext.getModelClassName()));
		transferContext.setServiceImplPackage(this.joinCorePackage("service.impl"));

		transferContext.setMapstructClassName(String.format("%s%sMapstruct", moduleClassName, transferContext.getModelClassName()));
		transferContext.setMapstructPropertyName(this.namingStrategy.switchFieldName(transferContext.getMapstructClassName()));
		transferContext.setMapstructPackage(this.joinCorePackage("mapstruct"));

		transferContext.setControllerClassName(String.format("%s%sController", moduleClassName, transferContext.getModelClassName()));
		transferContext.setControllerPackage(this.joinCorePackage("controller"));

		transferContext.setRequestPackage(this.joinCorePackage("models.request"));
		transferContext.setResponsePackage(this.joinCorePackage("models.response"));

		transferContext.setListResponseClassName(String.format("%sList%sResponse", moduleClassName, transferContext.getModelClassName()));
		transferContext.setDetailResponseClassName(String.format("%s%sDetailResponse", moduleClassName, transferContext.getModelClassName()));
		transferContext.setListRequestClassName(String.format("%sList%sRequest", moduleClassName, transferContext.getModelClassName()));
		transferContext.setSaveRequestClassName(String.format("%sSave%sRequest", moduleClassName, transferContext.getModelClassName()));

		transferContext.setQueryPackageName(ParamUtils.joinPackage(this.niflheimProperties.getPackageName(), "models"));
		return transferContext;
	}

	private String joinMybatisPackage(String packageName) {
		return ParamUtils.joinPackage(this.niflheimProperties.getPackageName(), "mybatis", packageName);
	}

	private String joinCorePackage(String packageName) {
		return ParamUtils.joinPackage(this.niflheimProperties.getPackageName(), "core", //
			this.niflheimProperties.getModuleName(), packageName);
	}


}

