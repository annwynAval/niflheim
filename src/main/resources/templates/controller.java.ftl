package ${packageName};


import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.tags.Tag;

import lombok.extern.slf4j.Slf4j;
import javax.annotation.Resource;
import org.springframework.web.bind.annotation.*;
import org.springframework.validation.annotation.Validated;

<#if table.primaryKey.propertyType.packageName ??>
import ${table.primaryKey.propertyType.packageName};
</#if>
<#list importPackages! as pkg>
import ${pkg};
</#list>


@Slf4j
@Validated
@RestController
@Tag(name = "${table.comment!}")
@RequestMapping("${requestUrl}")
public class ${className} {

    @Resource
    private ${serviceClassName} ${servicePropertyName};

    @PostMapping("/list")
    @Operation(summary = "分页列表")
    public QueryResponse<PaginationResponse<${listResponseClassName}>> list(@RequestBody ${listRequestClassName} request) {
        return this.${servicePropertyName}.list(request);
    }

    @PostMapping("/detail/{id}")
    @Operation(summary = "获取详情信息")
    @Parameters({
        @Parameter(description = "${table.primaryKey.comment!}", name = "id", required = true),
    })
    public QueryResponse<${detailResponseClassName}> detail(@PathVariable ${table.primaryKey.propertyType.type} id) {
        return this.${servicePropertyName}.detail(id);
    }

    @PostMapping("/save")
    @Operation(summary = "添加信息")
    public QueryResponse<String> save(@RequestBody ${saveRequestClassName} request) {
        return this.${servicePropertyName}.save(request);
    }

    @PostMapping("/update/{id}")
    @Operation(summary = "更新信息")
    @Parameters({
        @Parameter(description = "${table.primaryKey.comment!}", name = "id", required = true),
    })
    public QueryResponse<String> update(@PathVariable ${table.primaryKey.propertyType.type} id, @RequestBody ${saveRequestClassName} request) {
        return this.${servicePropertyName}.update(id, request);
    }

    @PostMapping("/delete/{id}")
    @Operation(summary = "删除信息")
    @Parameters({
        @Parameter(description = "${table.primaryKey.comment!}", name = "id", required = true),
    })
    public QueryResponse<String> delete(@PathVariable ${table.primaryKey.propertyType.type} id) {
        return this.${servicePropertyName}.delete(id);
    }

}