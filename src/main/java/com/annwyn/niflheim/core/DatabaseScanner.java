package com.annwyn.niflheim.core;

import com.annwyn.niflheim.configuration.properties.NiflheimProperties;
import com.annwyn.niflheim.core.exception.ServiceException;
import com.annwyn.niflheim.core.models.TableColumnModel;
import com.annwyn.niflheim.core.models.TableModel;
import com.annwyn.niflheim.core.registry.AbstractTypeRegistry;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import javax.sql.DataSource;
import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;




@Component
public class DatabaseScanner {

    private final Logger logger = LoggerFactory.getLogger(DatabaseScanner.class);

    @Resource
    private DataSource dataSource;

    @Resource
    private AbstractTypeRegistry abstractTypeRegistry;

    @Resource
    private NiflheimProperties niflheimProperties;

    /**
     * 扫描表, 获取表结构信息
     * @return .
     */
    public List<TableModel> scanTables() {
        try (Connection connection = this.dataSource.getConnection()) {
            return this.scanTables(connection, connection.getCatalog());
        } catch(SQLException e) {
            throw new ServiceException("获取表结构失败", e);
        }
    }

    /**
     * 扫描表, 获取表结构信息
     * @param connection .
     * @param catalog .
     * @return .
     */
    private List<TableModel> scanTables(Connection connection, String catalog) {
        final List<TableModel> tableModels = new ArrayList<>();
        try (ResultSet resultSet = connection.getMetaData().getTables(catalog, null, null, null)) {
            while (resultSet.next()) {
                final String tableName = resultSet.getString("TABLE_NAME");
                if(!this.judgeNeedScan(tableName)) { // 判断是否要扫描表
                    continue;
                }

                final TableModel tableModel = new TableModel();
                tableModel.setTableName(tableName);
                tableModel.setRemark(resultSet.getString("REMARKS"));
                tableModel.setTableColumnModels(this.scanTableColumns(connection, catalog, tableName));
                tableModels.add(tableModel);

                this.logger.debug("扫描表完成. 当前表: {}", tableModel.getTableName());
            }
            return tableModels;
        } catch(SQLException e) {
            throw new ServiceException("获取表结构失败", e);
        }
    }

    /**
     * 扫描表中的字段信息, 构建字段结构信息
     * @param connection .
     * @param catalog .
     * @return .
     * @throws SQLException .
     */
    private List<TableColumnModel> scanTableColumns(Connection connection, String catalog, String tableName) throws SQLException {
        final List<TableColumnModel> tableColumnModels = new ArrayList<>();
        final Set<String> primaryKeys = this.scanTablePrimaryKey(connection, catalog, tableName);

        try (ResultSet resultSet = connection.getMetaData().getColumns(catalog, null, tableName, null)) {
            while (resultSet.next()) {
                final TableColumnModel tableColumnModel = new TableColumnModel();
                tableColumnModel.setColumnName(resultSet.getString("COLUMN_NAME"));
                tableColumnModel.setColumnType(this.abstractTypeRegistry.getJdbcType(resultSet.getString("TYPE_NAME")));
                tableColumnModel.setRemark(resultSet.getString("REMARKS"));
                tableColumnModel.setAutoIncrement(resultSet.getBoolean("IS_AUTOINCREMENT"));
                tableColumnModel.setPrimaryKey(primaryKeys.contains(tableColumnModel.getColumnName()));
                tableColumnModels.add(tableColumnModel);
            }
        }
        return tableColumnModels;
    }

    /**
     * 扫描表中的主键信息
     * @param connection .
     * @param catalog .
     * @return .
     */
    private Set<String> scanTablePrimaryKey(Connection connection, String catalog, String tableName) {
        final Set<String> tablePrimaryKeys = new HashSet<>();
        try (ResultSet resultSet = connection.getMetaData().getPrimaryKeys(catalog, null, tableName)) {
            while (resultSet.next()) {
                tablePrimaryKeys.add(resultSet.getString("COLUMN_NAME"));
            }
        } catch(SQLException e) {
            throw new ServiceException(String.format("扫描表中主键失败. 当前表: %s", tableName), e);
        }
        return tablePrimaryKeys;
    }

    /**
     * 判断当前表是否需要转换
     * @param currentTableName .
     * @return .
     */
    private boolean judgeNeedScan(String currentTableName) {
        // 优先以includeTable进行判断, 再以exclusionTable进行判断
        if(!CollectionUtils.isEmpty(this.niflheimProperties.getIncludeTables())) {
            return this.niflheimProperties.getIncludeTables().contains(currentTableName);
        }

        if(!CollectionUtils.isEmpty(this.niflheimProperties.getExclusionTables())) {
            return !this.niflheimProperties.getExclusionTables().contains(currentTableName);
        }

        if(!CollectionUtils.isEmpty(this.niflheimProperties.getStartWithTables())) {
            return this.niflheimProperties.getStartWithTables().stream().map(currentTableName::startsWith)
                    .findAny().orElse(false);
        }

        return true;
    }
}
