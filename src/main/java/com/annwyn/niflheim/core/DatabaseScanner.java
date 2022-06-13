package com.annwyn.niflheim.core;

import com.annwyn.niflheim.core.models.TableColumnModel;
import com.annwyn.niflheim.core.models.TableModel;
import com.annwyn.niflheim.core.registry.AbstractTypeRegistry;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;


@Component
public class DatabaseScanner {

    @Resource
    private DataSource dataSource;

    @Resource
    private AbstractTypeRegistry abstractTypeRegistry;

    public List<TableModel> scanTables() throws SQLException {
        try (Connection connection = this.dataSource.getConnection()) {
            return this.scanTables(connection, connection.getCatalog());
        }
    }

    private List<TableModel> scanTables(Connection connection, String catalog) throws SQLException {
        final List<TableModel> tableModels = new ArrayList<>();
        ResultSet resultSet = connection.getMetaData().getTables(catalog, null, null, null);
        while (resultSet.next()) {
            final String tableName = resultSet.getString("TABLE_NAME");
            final List<TableColumnModel> tableColumnModels = this.scanTableColumns(connection, catalog, tableName);

            final TableModel tableModel = new TableModel();
            tableModel.setTableName(resultSet.getString("TABLE_NAME"));
            tableModel.setRemark(resultSet.getString("REMARKS"));
            tableModel.setTableColumnModels(tableColumnModels);
            tableModels.add(tableModel);

        }
        return tableModels;
    }

    private List<TableColumnModel> scanTableColumns(Connection connection, String catalog, String tableName) throws SQLException {
        final List<TableColumnModel> tableColumnModels = new ArrayList<>();
        final Set<String> primaryKeys = this.scanTablePrimaryKey(connection, catalog, tableName);

        ResultSet resultSet = connection.getMetaData().getColumns(catalog, null, tableName, null);
        while (resultSet.next()) {
            final TableColumnModel tableColumnModel = new TableColumnModel();
            tableColumnModel.setColumnName(resultSet.getString("COLUMN_NAME"));
            tableColumnModel.setColumnType(this.abstractTypeRegistry.getJdbcType(resultSet.getString("TYPE_NAME")));
            tableColumnModel.setRemark(resultSet.getString("REMARKS"));
            tableColumnModel.setPrimaryKey(primaryKeys.contains(tableColumnModel.getColumnName()));
            tableColumnModel.setAutoIncrement(resultSet.getBoolean("IS_AUTOINCREMENT"));
            tableColumnModels.add(tableColumnModel);
        }
        return tableColumnModels;
    }

    private Set<String> scanTablePrimaryKey(Connection connection, String catalog, String tableName) throws SQLException {
        final Set<String> primaryKeys = new HashSet<>();
        ResultSet resultSet = connection.getMetaData().getPrimaryKeys(catalog, null, tableName);
        while (resultSet.next()) {
            primaryKeys.add(resultSet.getString("COLUMN_NAME"));
        }
        return primaryKeys;
    }

}
