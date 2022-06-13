package ${packageName};

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
<#list imports as import>
import ${import};
</#list>


public interface ${javaModel.javaName}Mapper extends BaseMapper<${javaModel.javaName}> {
}
