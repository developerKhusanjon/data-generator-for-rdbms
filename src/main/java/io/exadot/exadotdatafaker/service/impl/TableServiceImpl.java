package io.exadot.exadotdatafaker.service.impl;

import io.exadot.exadotdatafaker.controller.exceptions.BadRequestAlertException;
import io.exadot.exadotdatafaker.entity.datasource.TableEntity;
import io.exadot.exadotdatafaker.repo.TableRepository;
import io.exadot.exadotdatafaker.service.FieldService;
import io.exadot.exadotdatafaker.service.TableService;
import io.exadot.exadotdatafaker.service.dto.*;
import io.exadot.exadotdatafaker.service.dto.datasource.FieldDto;
import io.exadot.exadotdatafaker.service.dto.datasource.NewTableDto;
import io.exadot.exadotdatafaker.service.dto.datasource.TableDto;
import io.exadot.exadotdatafaker.service.dto.datasource.UpdateTableDto;
import io.exadot.exadotdatafaker.service.mapper.TableMapper;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class TableServiceImpl implements TableService {

    private final TableRepository tableRepository;
    private final FieldService fieldService;
    private final TableMapper tableMapper;


    @Override
    public List<TableDto> findAllByDataSourceId(Long dataSourceId) {
        return tableMapper.toDto(tableRepository.findAllByDataSourceId(dataSourceId));
    }

    @Override
    public TableDto findById(Long id) throws BadRequestAlertException {
        var table = tableRepository.findById(id);
        if (table.isEmpty())
            throw new BadRequestAlertException(
                    "Table not found", "Table", "id - " + id, HttpStatus.NOT_FOUND);

        TableDto tableDto = tableMapper.toDto(table.get());
        tableDto.setFields(fieldService.findAllByTableId(id));

        return tableDto;
    }

    @Override
    public List<FieldDto> getTableFields(Long tableId) {
        return fieldService.findAllByTableId(tableId);
    }

    @Override
    public FieldDto getField(Long fieldId, Long tableId) throws BadRequestAlertException {
        return fieldService.findById(fieldId, tableId);
    }

    @Override
    public TableDto createTable(NewTableDto newTableDto) throws BadRequestAlertException {
        TableEntity tableEntity = TableEntity.builder()
                .tableName(newTableDto.getTableName())
                .dataSourceId(newTableDto.getDataSourceId())
                .generatedKey(newTableDto.getGeneratedKey())
                .build();

        return  tableMapper.toDto(tableRepository.save(tableEntity));
    }

    @Override
    public TableDto updateTable(UpdateTableDto updateTableDto) throws BadRequestAlertException {
        Optional<TableEntity> tableEntity = tableRepository.findById(updateTableDto.getId());
        if (tableEntity.isEmpty())
            throw new BadRequestAlertException("Table not found", "Table", "id - " + updateTableDto.getId(), HttpStatus.NOT_FOUND);

        tableEntity.get().setTableName(updateTableDto.getTableName());
        tableEntity.get().setGeneratedKey(updateTableDto.getGeneratedKey());
        tableEntity.get().setDataSourceId(updateTableDto.getDataSourceId());

        return tableMapper.toDto(tableRepository.save(tableEntity.get()));
    }

    @Override
    public FieldDto createTableField(Long tableId, FieldDto field) throws BadRequestAlertException {
        return fieldService.addField(field, tableId);
    }

    @Override
    public FieldDto updateTableField(FieldDto field, Long tableId) throws BadRequestAlertException {
        return fieldService.updateField(field, tableId);
    }

    @Transactional
    @Override
    public AlertResponseDto deleteTable(Long id) {
        fieldService.findAllByTableId(id).forEach(fd -> fieldService.deleteById(fd.getId()));
        tableRepository.deleteById(id);
        return new AlertResponseDto("Table deleted successfully", true);
    }

    @Override
    public AlertResponseDto removeTableField(Long fieldId, Long tableId) throws BadRequestAlertException {
        if (fieldService.checkByIdAndTableId(fieldId, tableId))
            throw new BadRequestAlertException(
                    "Field not exist in this table", "Field", "fieldId - " + fieldId + "   tableId - " + tableId, HttpStatus.NOT_FOUND);
        return fieldService.deleteById(fieldId);
    }

    @Override
    public boolean checkByDataSourceId(Long id) {
        return tableRepository.existsByDataSourceId(id);
    }
}
