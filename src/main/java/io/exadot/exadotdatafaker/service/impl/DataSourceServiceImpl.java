package io.exadot.exadotdatafaker.service.impl;

import io.exadot.exadotdatafaker.controller.exceptions.BadRequestAlertException;
import io.exadot.exadotdatafaker.entity.db.DataSourceEntity;
import io.exadot.exadotdatafaker.repo.DataSourceRepository;
import io.exadot.exadotdatafaker.service.DataSourceService;
import io.exadot.exadotdatafaker.service.TableService;
import io.exadot.exadotdatafaker.service.dto.AlertResponseDto;
import io.exadot.exadotdatafaker.service.dto.db.DataSourceDto;
import io.exadot.exadotdatafaker.service.dto.db.TableDto;
import io.exadot.exadotdatafaker.service.mapper.DataSourceMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class DataSourceServiceImpl implements DataSourceService {

    private final DataSourceRepository dataSourceRepository;
    private final DataSourceMapper dataSourceMapper;
    private final TableService tableService;

    @Override
    public DataSourceDto createDataSource(DataSourceDto dataSourceDto) {
        DataSourceEntity dataSourceEntity = dataSourceMapper.toEntity(dataSourceDto);
        dataSourceEntity.setDriver(dataSourceDto.getDatabaseType().getDriverClassName());

        return dataSourceMapper.toDto(dataSourceRepository.save(dataSourceEntity));
    }

    @Override
    public DataSourceDto updateDataSource(DataSourceDto dataSourceDto) throws BadRequestAlertException {
        Optional<DataSourceEntity> dataSourceEntity = dataSourceRepository.findById(dataSourceDto.getId());
        if (dataSourceEntity.isEmpty())
            throw new BadRequestAlertException("DataSource not found", "DataSource", "id - " + dataSourceDto.getId(), HttpStatus.NOT_FOUND);

        dataSourceEntity.get().setName(dataSourceDto.getName());
        dataSourceEntity.get().setDescription(dataSourceDto.getDescription());
        dataSourceEntity.get().setUrl(dataSourceDto.getUrl());
        dataSourceEntity.get().setUsername(dataSourceDto.getUsername());
        dataSourceEntity.get().setPassword(dataSourceDto.getPassword());
        dataSourceEntity.get().setDatabaseType(dataSourceDto.getDatabaseType());
        dataSourceEntity.get().setDriver(dataSourceDto.getDatabaseType().getDriverClassName());

        return dataSourceMapper.toDto(dataSourceRepository.save(dataSourceEntity.get()));
    }

    @Override
    public List<DataSourceDto> findAll() {
        return dataSourceMapper.toDto(dataSourceRepository.findAll());
    }

    @Override
    public DataSourceDto findById(Long id) throws BadRequestAlertException {
        Optional<DataSourceEntity> dataSourceEntity = dataSourceRepository.findById(id);
        if (dataSourceEntity.isEmpty())
            throw new BadRequestAlertException("DataSource not found", "DataSource", "id - " + id, HttpStatus.NOT_FOUND);

        return dataSourceMapper.toDto(dataSourceEntity.get());
    }

    @Override
    public List<TableDto> getAllTablesByDataSourceId(Long dataSourceId) {
        return tableService.findAllByDataSourceId(dataSourceId);
    }

    @Transactional
    @Override
    public AlertResponseDto clearDataSource(Long id) {
        tableService.findAllByDataSourceId(id).forEach(td -> tableService.deleteTable(td.getId()));
        return new AlertResponseDto("DataSource cleared successfully", true);
    }

    @Override
    public AlertResponseDto deleteDataSource(Long id) throws BadRequestAlertException {
        if (tableService.checkByDataSourceId(id))
            throw new BadRequestAlertException(
                    "Datasource tables must be migrated or cleared before deleting datasource", "Datasource - Tables", "id - " + id, HttpStatus.CONFLICT);

        dataSourceRepository.deleteById(id);
        return new AlertResponseDto("DataSource deleted successfully", true);
    }
}
