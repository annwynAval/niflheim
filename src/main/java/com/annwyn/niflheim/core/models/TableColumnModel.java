package com.annwyn.niflheim.core.models;

import lombok.Data;

import java.io.Serializable;
import java.sql.JDBCType;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Set;


@Data
public class TableColumnModel implements Serializable {

    /**
     * 字段名称
     */
    private String columnName;

    /**
     * 字段类型
     */
    private JDBCType columnType;

    /**
     * 备注
     */
    private String remark;

    /**
     * 是否是主键
     */
    private boolean primaryKey = false;

    /**
     * 是否是自增
     */
    private boolean autoIncrement = false;

}
