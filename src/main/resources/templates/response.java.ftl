package ${packageName};

import lombok.Data;
import java.io.Serializable;

import io.swagger.v3.oas.annotations.media.Schema;

<#list importPackages! as pkg>
import ${pkg};
</#list>

@Data
public class ${className} implements Serializable {

    <#list table.fields as field>
    @Schema(title = "${field.comment!}")
    private ${field.propertyType.type} ${field.propertyName};

    </#list>
}


