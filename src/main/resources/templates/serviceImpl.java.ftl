package ${packageName};

import java.util.List;
import java.util.Objects;
import ${basePackageName}.utils.Conditions;
import javax.annotation.Resource;
import com.github.pagehelper.PageHelper;
import org.springframework.stereotype.Service;

<#list importPackages! as pkg>
import ${pkg};
</#list>

@Service
public class ${className} implements ${serviceClassName} {

    @Resource
    private ${mapstructClassName} ${mapstructPropertyName};

    @Resource
    private ${repositoryClassName} ${repositoryPropertyName};

    public QueryResponse<PaginationResponse<${listResponseClassName}>> list(${listRequestClassName} request) {
        PageHelper.startPage(request.getIndex(), request.getSize());
        List<${listResponseClassName}> responses = this.${repositoryPropertyName}.list(request);
        return QueryResponse.success(PaginationResponse.newInstance(responses));
    }

    public QueryResponse<${detailResponseClassName}> detail(${table.primaryKey.propertyType.type} id) {
        ${detailResponseClassName} response = this.${repositoryPropertyName}.detail(id);
        return QueryResponse.success(response);
    }

    public QueryResponse<String> save(${saveRequestClassName} request) {
        ${modelClassName} entity = this.${mapstructPropertyName}.saveRequest2Entity(request);
        // TODO auto-generator 添加主键
        this.${repositoryPropertyName}.save(entity);
        return QueryResponse.success();
    }

    public QueryResponse<String> update(${table.primaryKey.propertyType.type} id, ${saveRequestClassName} request) {
        ${modelClassName} entity = this.${repositoryPropertyName}.getById(id);
        Conditions.checkExpression(Objects.isNull(entity), "信息不存在, 无法更新");

        this.${mapstructPropertyName}.updateRequest2Entity(entity, request);
        this.${repositoryPropertyName}.updateById(entity);
        return QueryResponse.success();
    }

    public QueryResponse<String> delete(${table.primaryKey.propertyType.type} id) {
        ${modelClassName} entity = this.${repositoryPropertyName}.getById(id);
        Conditions.checkExpression(Objects.isNull(entity), "信息不存在, 无法更新");

        this.${repositoryPropertyName}.removeById(id);
        return QueryResponse.success();
    }

}
