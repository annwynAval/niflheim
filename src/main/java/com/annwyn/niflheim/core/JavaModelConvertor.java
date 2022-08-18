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

    /**
     * 解析数据库表结构, 生成需要的数据库实体类配置信息
     * @param tableModels .
     * @return .
     */
    public List<JavaModel> convertJavaModels(List<TableModel> tableModels) {
        Map<String, NiflheimProperties.OverrideTable> overrideTableCaches = this.buildOverrideTableCaches();

        final List<JavaModel> javaModels = new ArrayList<>(tableModels.size());
        for (TableModel tableModel : tableModels) {
            final JavaModel javaModel = new JavaModel();
            javaModel.setTableName(tableModel.getTableName());
            javaModel.setRemark(tableModel.getRemark());
            javaModel.setJavaName(this.getConvertModelName(overrideTableCaches, tableModel));
            javaModel.setJavaColumnModels(this.convertJavaColumnModels(tableModel.getTableColumnModels()));
            javaModels.add(javaModel);
        }
        return javaModels;
    }

    /**
     * 如果有配置includeEntities, 直接获取配置里面的实体名称
     * 如果没有配置includeEntities, 将表名的前缀去除然后进行大小写转换
     * @param convertModelEntityCaches .
     * @param tableModel .
     * @return .
     */
    private String getConvertModelName(Map<String, NiflheimProperties.OverrideTable> convertModelEntityCaches, TableModel tableModel) {
        if(convertModelEntityCaches.containsKey(tableModel.getTableName())) {
            // 如果在overrideTable中存在, 使用配置里面的实体名称
            return convertModelEntityCaches.get(tableModel.getTableName()).getModelName();
        }

        // 如果配置中有配置java-name-suffixes, 就需要将前缀去掉, 然后再进行大小写转换.
        String tableName = tableModel.getTableName();
        if(!CollectionUtils.isEmpty(this.niflheimProperties.getRemoveSuffixes())) {
            tableName = this.niflheimProperties.getRemoveSuffixes().stream()
                    .reduce(tableModel.getTableName(), (result, element) -> result.replaceAll(element, ""));
        }
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

    private Map<String, NiflheimProperties.OverrideTable> buildOverrideTableCaches() {
        if(CollectionUtils.isEmpty(this.niflheimProperties.getOverrideTables())) {
            return Collections.emptyMap();
        }

        return this.niflheimProperties.getOverrideTables().stream()
                .collect(Collectors.toMap(NiflheimProperties.OverrideTable::getTableName, value -> value));
    }

}
