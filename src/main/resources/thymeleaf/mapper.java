package [(${packageName})];

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
[# th:each="import:${imports}"]
import [(${import})];
[/]

public interface [(${javaModel.javaName})]Mapper extends BaseMapper<[(${javaModel.javaName})]> {
}
