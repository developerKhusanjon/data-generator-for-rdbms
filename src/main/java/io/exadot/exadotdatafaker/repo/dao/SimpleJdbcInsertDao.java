package io.exadot.exadotdatafaker.repo.dao;

import io.exadot.exadotdatafaker.controller.exceptions.ResourceNotFoundException;
import io.exadot.exadotdatafaker.service.dto.AlertResponseDto;
import io.exadot.exadotdatafaker.service.dto.datasource.DataSourceDto;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.sql.DataSource;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.Stream;


@Component
public class SimpleJdbcInsertDao {
    private final ExecutorService virtualThreadPerTaskExecutor = Executors.newVirtualThreadPerTaskExecutor();

    private DataSource buildDataSource(DataSourceDto dataSource) {
        DataSourceBuilder<?> dataSourceBuilder = DataSourceBuilder.create();
        dataSourceBuilder.driverClassName(
                dataSource.getDriver().isEmpty() ? dataSource.getDatabaseType().getDriverClassName() : dataSource.getDriver());
        dataSourceBuilder.url(dataSource.getUrl());
        dataSourceBuilder.username(dataSource.getUsername());
        dataSourceBuilder.password(dataSource.getPassword());
        return dataSourceBuilder.build();
    }

    @Transactional
    public AlertResponseDto insertAll(DataSourceDto dataSource, Stream<Stream<Map<String, Object>>> dataStream,
                                      String schema, String table, String generatedKey, String[] columns) {

        SimpleJdbcInsert simpleJdbcInsert = new SimpleJdbcInsert(buildDataSource(dataSource))
                .withSchemaName(schema).withTableName(table)
                .usingColumns(columns)
                .usingGeneratedKeyColumns(generatedKey);

        virtualThreadPerTaskExecutor
                .execute(() -> dataStream.forEach(data -> {
                            try {
                                simpleJdbcInsert.executeBatch(generateMapSqlList(data, columns));
                            } catch (Exception e) {
                                throw new ResourceNotFoundException("Failed to save data\n" + e.getMessage());
                            }
                        }
                ));

        return new AlertResponseDto("Data inserted successfully", true);
    }

    private MapSqlParameterSource[] generateMapSqlList(Stream<Map<String, Object>> objectStream, String[] columns) {
        List<MapSqlParameterSource> entries = objectStream.map(object -> {
            MapSqlParameterSource entry = new MapSqlParameterSource();
            for (String key : columns) {
                entry.addValue(key, object.get(key));
            }
            return entry;
        }).toList();

        return entries.toArray(new MapSqlParameterSource[0]);
    }
}
