package com.annwyn.niflheim.core.models;


import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class JavaModel implements Serializable {

    /**
     * java类名称
     */
    private String javaName;

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
    private List<JavaColumnModel> javaColumnModels;
}
