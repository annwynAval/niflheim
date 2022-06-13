//package com.annwyn.niflheim.core.registry;
//
//import oracle.jdbc.OracleType;
//
//import java.sql.JDBCType;
//import java.util.Arrays;
//
//public class OracleTypeRegistry extends AbstractTypeRegistry {
//
//    @Override
//    protected void registerJdbcTypes() {
//        Arrays.stream(OracleType.values()).forEach(item -> {
//            this.registerJdbcType(item.getName(), JDBCType.valueOf(item.getVendorTypeNumber()));
//        });
//    }
//}
