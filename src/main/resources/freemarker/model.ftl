package ${packageName};

import lombok.Data;
import java.io.Serializable;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

<#list imports as import>
import ${import};
</#list>

/**
 * ${javaModel.remark}
 */
@Data
@TableName("${javaModel.tableName}")
public class ${javaModel.javaName} implements Serializable {

<#list javaModel.javaColumnModels as column>
    /**
     * ${column.remark}
     */
<#if column.primaryKey>
    <#if column.autoIncrement>
    @TableId(type=IdType.AUTO)
    <#else>
    @TableId(type=IdType.INPUT)
    </#if>
</#if>
    private ${column.columnClassName} ${column.columnName};

</#list>
}

