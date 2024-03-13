package io.exadot.exadotdatafaker.service;

import io.exadot.exadotdatafaker.controller.exceptions.BadRequestAlertException;
import io.exadot.exadotdatafaker.service.dto.AlertResponseDto;
import io.exadot.exadotdatafaker.service.dto.db.DataSourceDto;
import io.exadot.exadotdatafaker.service.dto.db.TableDto;

import java.util.List;

public interface DataSourceService {
    DataSourceDto createDataSource(DataSourceDto dataSourceDto);

    DataSourceDto updateDataSource(DataSourceDto dataSourceDto) throws BadRequestAlertException;

    List<DataSourceDto> findAll();

    DataSourceDto findById(Long id) throws BadRequestAlertException;

    List<TableDto> getAllTablesByDataSourceId(Long dataSourceId);

    AlertResponseDto clearDataSource(Long id);

    AlertResponseDto deleteDataSource(Long id) throws BadRequestAlertException;
}
