package com.annwyn.niflheim.core.models;

import lombok.Data;

import java.io.Serializable;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;


@Data
public class TableModel implements Serializable {

    /**
     * 表名
     */
    private String tableName;

    /**
     * 备注
     */
    private String remark;

    /**
     * 表字段信息
     */
    private List<TableColumnModel> tableColumnModels;

}
