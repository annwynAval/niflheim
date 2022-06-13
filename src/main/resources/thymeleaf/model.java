package [(${packageName})];

import lombok.Data;
import java.io.Serializable;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

[# th:each="import:${imports}"]
import [(${import})];
[/]

/**
 * [(${javaModel.remark})]
 */
@Data
@TableName("[(${javaModel.tableName})]")
public class [(${javaModel.javaName})] implements Serializable {

[# th:each="column:${javaModel.javaColumnModels}"]
    /**
     * [(${column.remark})]
     */[# th:if="${column.primaryKey}"]@TableId(type = [# th:if="${column.autoIncrement}"]IdType.AUTO[/][# th:else]IdType.INPUT[/])[/]
    private [(${column.columnClassName})] [(${column.columnName})];

[/]
}

