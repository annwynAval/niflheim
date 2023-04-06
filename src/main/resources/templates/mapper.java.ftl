package ${packageName};

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
<#list importPackages! as pkg>
import ${pkg};
</#list>


public interface ${className} extends BaseMapper<${modelClassName}> {

    /**
     * 分页列表
     * @param request .
     * @return .
     */
    List<${listResponseClassName}> list(@Param("request") ${listRequestClassName} request);

    /**
     * 获取详情信息
     * @param id ${table.primaryKey.comment!}
     * @return .
     */
    ${detailResponseClassName} detail(@Param("id") ${table.primaryKey.propertyType.type} id);
}
