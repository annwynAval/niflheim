package ${packageName};

import lombok.Data;
import java.io.Serializable;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.TableField;

<#list importPackages! as pkg>
import ${pkg};
</#list>

/**
 * ${table.comment}
 */
@Data
@TableName("${table.schemaName!}.${table.tableName}")
public class ${className} implements Serializable {

<#list table.fields as field>
    /**
     * ${field.comment}
     */
    <#if field.primaryKey>
        <#if field.autoincrement>
    @TableId(type=IdType.AUTO)
        <#else>
    @TableId(type=IdType.INPUT)
        </#if>
    <#else>
        <#if field.keyWord>
    @TableField("${field.fieldName}")
        </#if>
    </#if>
    private ${field.propertyType.type} ${field.propertyName};

</#list>
}

