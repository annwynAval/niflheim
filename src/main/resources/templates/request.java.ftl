package ${packageName};

import lombok.Data;
import java.io.Serializable;

import io.swagger.v3.oas.annotations.media.Schema;

<#if paginationRequest>
import lombok.EqualsAndHashCode;
import ${queryPackageName}.PaginationRequest;

@Data
@Schema(title = "${table.comment!}")
@EqualsAndHashCode(callSuper = true)
public class ${className} extends PaginationRequest implements Serializable {

}
<#elseif saveRequest>

<#list importPackages! as pkg>
import ${pkg};
</#list>

@Data
@Schema(title = "${table.comment!}")
public class ${className} implements Serializable {

    <#list table.fields as field>
    @Schema(title = "${field.comment!}")
    private ${field.propertyType.type} ${field.propertyName};

    </#list>

}

</#if>


