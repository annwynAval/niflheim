package com.annwyn.niflheim.generator.configure;


import com.annwyn.niflheim.generator.configure.TableScanFilter;
import com.annwyn.niflheim.generator.configure.keyword.KeywordHandle;
import com.annwyn.niflheim.generator.configure.naming.NamingStrategy;
import com.annwyn.niflheim.generator.configure.registry.ColumnRegistry;
import com.annwyn.niflheim.generator.models.TableEntity;
import com.annwyn.niflheim.generator.models.TableFieldEntity;
import com.annwyn.niflheim.utils.Conditions;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.JDBCType;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

@Component
public class DatabaseQueryExecute {

	@Resource
	private DataSource dataSource;

	@Resource
	private TableScanFilter tableScanFilter;

	/**
	 * 查询所有表信息
	 *
	 * @return .
	 */
	public List<TableEntity> queryTables() throws SQLException {
		final List<TableEntity> tableEntities = new ArrayList<>();
		try (Connection connection = this.dataSource.getConnection()) {
			final String schema = connection.getSchema(), catalog = connection.getCatalog();
			try (ResultSet resultSet = connection.getMetaData().getTables(catalog, schema, null, null)) {
				while (resultSet.next()) {
					final String tableName = resultSet.getString("TABLE_NAME");
					if(!this.tableScanFilter.include(tableName)) {
						continue;
					}

					TableEntity tableEntity = new TableEntity();
					tableEntity.setSchemaName(schema);
					tableEntity.setTableName(tableName);
					tableEntity.setComment(resultSet.getString("REMARKS"));
					tableEntity.setFields(this.queryFields(connection, catalog, schema, tableName));
					tableEntities.add(tableEntity);
				}
			}
		}
		return tableEntities;
	}

	public List<TableFieldEntity> queryFields(Connection connection, String catalog, String schema, String tableName) throws SQLException {
		final String primaryKeyName; // 主键信息, 只取一个, 如果不存在, 直接报错
		try (ResultSet resultSet = connection.getMetaData().getPrimaryKeys(catalog, schema, tableName)) {
			primaryKeyName = resultSet.next() ? resultSet.getString("COLUMN_NAME") : null;
			Conditions.checkExpression(!StringUtils.hasText(primaryKeyName), String.format("表: %s 主键信息未找到, 请先设置一个主键再执行", tableName));
		}

		List<TableFieldEntity> tableFieldEntities = new ArrayList<>();
		try (ResultSet resultSet = connection.getMetaData().getColumns(catalog, schema, tableName, null)) {
			while (resultSet.next()) {
				final TableFieldEntity tableFieldEntity = new TableFieldEntity();
				tableFieldEntity.setFieldName(resultSet.getString("COLUMN_NAME"));
				tableFieldEntity.setJdbcType(JDBCType.valueOf(resultSet.getInt("DATA_TYPE")));
				tableFieldEntity.setComment(resultSet.getString("REMARKS"));
				tableFieldEntity.setPrimaryKey(Objects.equals(primaryKeyName, tableFieldEntity.getFieldName()));
				tableFieldEntity.setAutoincrement(resultSet.getBoolean("IS_AUTOINCREMENT"));

				tableFieldEntities.add(tableFieldEntity);
			}
		}
		return tableFieldEntities;
	}

}
