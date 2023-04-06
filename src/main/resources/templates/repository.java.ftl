package ${packageName};

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Repository;
import java.util.List;

<#list importPackages! as pkg>
import ${pkg};
</#list>

@Repository
public class ${className} extends ServiceImpl<${mapperClassName}, ${modelClassName}> {

    /**
     * 分页列表
     * @param request .
     * @return .
     */
    public List<${listResponseClassName}> list(${listRequestClassName} request) {
        return this.baseMapper.list(request);
    }

    /**
     * 获取详情信息
     * @param id ${table.primaryKey.comment!}
     * @return .
     */
    public ${detailResponseClassName} detail(${table.primaryKey.propertyType.type} id) {
        return this.baseMapper.detail(id);
    }

}
