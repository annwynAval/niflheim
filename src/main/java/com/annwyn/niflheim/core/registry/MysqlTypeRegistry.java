package com.annwyn.niflheim.core.registry;

import com.mysql.cj.MysqlType;

import java.sql.JDBCType;
import java.util.Arrays;

public class MysqlTypeRegistry extends AbstractTypeRegistry {

    @Override
    protected void registerJdbcTypes() {
        Arrays.stream(MysqlType.values()).forEach(item -> this.registerJdbcType(item.getName(), JDBCType.valueOf(item.getJdbcType())));
    }
}
