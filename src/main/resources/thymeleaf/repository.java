package [(${packageName})];

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Repository;

[# th:each="import:${imports}"]
import [(${import})];
[/]


@Repository
public class [(${javaModel.javaName})]Repository extends ServiceImpl<[(${javaModel.javaName})]Mapper, [(${javaModel.javaName})]> {

}
