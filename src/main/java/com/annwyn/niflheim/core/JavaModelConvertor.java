package com.annwyn.niflheim.core;


import com.annwyn.niflheim.configuration.properties.NiflheimProperties;
import com.annwyn.niflheim.core.models.JavaColumnModel;
import com.annwyn.niflheim.core.models.JavaModel;
import com.annwyn.niflheim.core.models.TableColumnModel;
import com.annwyn.niflheim.core.models.TableModel;
import com.annwyn.niflheim.core.registry.AbstractTypeRegistry;
import com.google.common.base.CaseFormat;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Component
public class JavaModelConvertor {

    @Resource
    private AbstractTypeRegistry abstractTypeRegistry;

    @Resource
    private NiflheimProperties niflheimProperties;

    public List<JavaModel> convertJavaModels(List<TableModel> tableModels) {
        Map<String, NiflheimProperties.ConvertModelEntity> convertModelEntityCaches = this.buildConvertModelCaches();

        final List<JavaModel> javaModels = new ArrayList<>(tableModels.size());
        for (TableModel tableModel : tableModels) {
            if(!this.isTableNeedConvert(convertModelEntityCaches, tableModel)) {
                continue; // 判断是否需要转换
            }

            final JavaModel javaModel = new JavaModel();
            javaModel.setTableName(tableModel.getTableName());
            javaModel.setRemark(tableModel.getRemark());
            javaModel.setJavaName(this.getConvertModelName(convertModelEntityCaches, tableModel));
            javaModel.setJavaColumnModels(this.convertJavaColumnModels(tableModel.getTableColumnModels()));
            javaModels.add(javaModel);
        }
        return javaModels;
    }

    /**
     * 如果没有配置includeEntities, 那就需要全部转换, 否则只需要转换部分就可以了.
     * @param convertModelEntityCaches .
     * @param tableModel .
     * @return .
     */
    private boolean isTableNeedConvert(Map<String, NiflheimProperties.ConvertModelEntity> convertModelEntityCaches, TableModel tableModel) {
        if(CollectionUtils.isEmpty(convertModelEntityCaches)) {
            return true;
        }
        return convertModelEntityCaches.containsKey(tableModel.getTableName());
    }

    /**
     * 如果有配置includeEntities, 直接获取配置里面的实体名称
     * 如果没有配置includeEntities, 将表名的前缀去除然后进行大小写转换
     * @param convertModelEntityCaches .
     * @param tableModel .
     * @return .
     */
    private String getConvertModelName(Map<String, NiflheimProperties.ConvertModelEntity> convertModelEntityCaches, TableModel tableModel) {
        if(!CollectionUtils.isEmpty(convertModelEntityCaches)) {
            NiflheimProperties.ConvertModelEntity convertModelEntity = convertModelEntityCaches.get(tableModel.getTableName());
            return convertModelEntity.getModelName();
        }

        if(CollectionUtils.isEmpty(this.niflheimProperties.getJavaNameSuffixes())) {
            return tableModel.getTableName();
        }

        String tableName = this.niflheimProperties.getJavaNameSuffixes().stream()
                .reduce(tableModel.getTableName(), (result, element) -> result.replaceAll(element, ""));

        return CaseFormat.UPPER_UNDERSCORE.to(CaseFormat.UPPER_CAMEL, tableName);
    }

    private List<JavaColumnModel> convertJavaColumnModels(List<TableColumnModel> tableColumnModels) {
        final List<JavaColumnModel> javaColumnModels = new ArrayList<>(tableColumnModels.size());
        for (TableColumnModel tableColumnModel : tableColumnModels) {
            final JavaColumnModel javaColumnModel = new JavaColumnModel();
            javaColumnModel.setColumnName(CaseFormat.UPPER_UNDERSCORE.to(CaseFormat.LOWER_CAMEL, tableColumnModel.getColumnName()));
            javaColumnModel.setColumnClass(this.abstractTypeRegistry.getJavaType(tableColumnModel.getColumnType()));
            javaColumnModel.setColumnClassName(javaColumnModel.getColumnClass().getSimpleName());
            javaColumnModel.setRemark(tableColumnModel.getRemark());
            javaColumnModel.setPrimaryKey(tableColumnModel.isPrimaryKey());
            javaColumnModel.setAutoIncrement(tableColumnModel.isAutoIncrement());
            javaColumnModels.add(javaColumnModel);
        }
        return javaColumnModels;
    }

    private Map<String, NiflheimProperties.ConvertModelEntity> buildConvertModelCaches() {
        if(CollectionUtils.isEmpty(this.niflheimProperties.getIncludeEntities())) {
            return Collections.emptyMap();
        }
        return this.niflheimProperties.getIncludeEntities().stream()
                .collect(Collectors.toMap(NiflheimProperties.ConvertModelEntity::getTableName, value -> value));
    }

}
