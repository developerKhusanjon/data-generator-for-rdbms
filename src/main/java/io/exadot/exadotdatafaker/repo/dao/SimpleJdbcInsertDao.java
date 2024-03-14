package io.exadot.exadotdatafaker.repo.dao;

import io.exadot.exadotdatafaker.controller.exceptions.ResourceNotFoundException;
import io.exadot.exadotdatafaker.service.dto.AlertResponseDto;
import io.exadot.exadotdatafaker.service.dto.datasource.DataSourceDto;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.sql.DataSource;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;


@Component
public class SimpleJdbcInsertDao {

    private DataSource buildDataSource(DataSourceDto dataSource) {
        DataSourceBuilder<?> dataSourceBuilder = DataSourceBuilder.create();
        dataSourceBuilder.driverClassName(dataSource.getDriver());
        dataSourceBuilder.url(dataSource.getUrl());
        dataSourceBuilder.username(dataSource.getUsername());
        dataSourceBuilder.password(dataSource.getPassword());
        return dataSourceBuilder.build();
    }

    @Transactional
    public AlertResponseDto insertAll(DataSourceDto dataSource, Stream<Map<String, Object>> dataStream, String table, String generatedKey) {
        SimpleJdbcInsert simpleJdbcInsert = new SimpleJdbcInsert(buildDataSource(dataSource))
                .withTableName(table).usingGeneratedKeyColumns(generatedKey);

        dataStream.forEach(data -> {
                    try {
                        String[] columns = data.keySet().toArray(new String[0]);
                        simpleJdbcInsert.usingColumns(columns);
                        simpleJdbcInsert.execute(data);
                    } catch (Exception e) {
                        throw new ResourceNotFoundException("Failed to save data\n" + e.getMessage());
                    }
                }
        );

        return new AlertResponseDto("Data inserted successfully", true);
    }
}
