package ${packageName};

<#list importPackages! as pkg>
import ${pkg};
</#list>

public interface ${className} {

    /**
     * 分页列表
     * @param request .
     * @return .
     */
    QueryResponse<PaginationResponse<${listResponseClassName}>> list(${listRequestClassName} request);

    /**
     * 获取详情信息
     * @param id ${table.primaryKey.comment!}
     * @return .
     */
    QueryResponse<${detailResponseClassName}> detail(${table.primaryKey.propertyType.type} id);

    /**
     * 添加信息
     * @param request .
     * @return .
     */
    QueryResponse<String> save(${saveRequestClassName} request);

    /**
     * 更新信息
     * @param id ${table.primaryKey.comment!}
     * @param request .
     * @return .
     */
    QueryResponse<String> update(${table.primaryKey.propertyType.type} id, ${saveRequestClassName} request);

    /**
     * 删除信息
     * @param id ${table.primaryKey.comment!}
     * @return .
     */
    QueryResponse<String> delete(@PathVariable ${table.primaryKey.propertyType.type} id);

}