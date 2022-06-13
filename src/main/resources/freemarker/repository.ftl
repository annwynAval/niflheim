package ${packageName};

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Repository;

<#list imports as import>
import ${import};
</#list>

@Repository
public class ${javaModel.javaName}Repository extends ServiceImpl<${javaModel.javaName}Mapper, ${javaModel.javaName}> {

}
