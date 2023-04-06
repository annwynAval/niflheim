<?xml version="1.0" encoding="UTF-8"?>
<!DOCTYPE mapper PUBLIC "-//mybatis.org//DTD Mapper 3.0//EN" "http://mybatis.org/dtd/mybatis-3-mapper.dtd">
<mapper namespace="${packageName}.${className}">

    <select id="list" resultType="${responsePackage}.${listResponseClassName}">
        select
            *
        from ${table.tableName}
        <where>
        ${r"# TODO auto-generator 添加检索条件"}
        </where>
        order by ? desc
    </select>

    <select id="detail" resultType="${responsePackage}.${detailResponseClassName}">
        select
            *
        from ${table.tableName}
        <where>
            ${r"# TODO auto-generator 修改主键类型, 需要判断是否为空"}
            <if test="id != null">
                and ${table.primaryKey.fieldName} = ${r"#{id}"}
            </if>
        </where>
    </select>

</mapper>