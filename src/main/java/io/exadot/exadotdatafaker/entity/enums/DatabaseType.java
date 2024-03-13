package io.exadot.exadotdatafaker.entity.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum DatabaseType {
    POSTGRES("org.postgresql.Driver"),
    MYSQL("com.mysql.jdbc.Driver"),
    SQLSERVER("com.microsoft.sqlserver.jdbc.SQLServerDriver"),
    H2("org.h2.Driver");

    private final String driverClassName;
}
