package com.annwyn.niflheim.core.models;

import lombok.Data;

import java.io.Serializable;



@Data
public class JavaColumnModel implements Serializable {

    /**
     * java字段名称
     */
    private String columnName;

    /**
     * java字段类型
     */
    private Class<?> columnClass;

    /**
     * java字段类型名称
     */
    private String columnClassName;

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
