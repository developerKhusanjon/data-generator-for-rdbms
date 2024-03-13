package io.exadot.exadotdatafaker.service.impl;

import io.exadot.exadotdatafaker.controller.exceptions.BadRequestAlertException;
import io.exadot.exadotdatafaker.entity.TableEntity;
import io.exadot.exadotdatafaker.repo.TableRepository;
import io.exadot.exadotdatafaker.service.FieldService;
import io.exadot.exadotdatafaker.service.TableService;
import io.exadot.exadotdatafaker.service.dto.*;
import io.exadot.exadotdatafaker.service.mapper.TableMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
    public FieldDto getField(Long fieldId) throws BadRequestAlertException {
        return fieldService.findById(fieldId);
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
    public FieldDto updateTableField(FieldDto field) throws BadRequestAlertException {
        return fieldService.updateField(field);
    }

    @Transactional
    @Override
    public AlertResponseDto deleteTable(Long id) {
        fieldService.findAllByTableId(id).forEach(fd -> fieldService.deleteById(fd.getId()));
        tableRepository.deleteById(id);
        return new AlertResponseDto("Table deleted successfully", true);
    }

    @Override
    public AlertResponseDto removeTableField(Long fieldId) {
        return fieldService.deleteById(fieldId);
    }

    @Override
    public boolean checkByDataSourceId(Long id) {
        return tableRepository.existsByDataSourceId(id);
    }

//    private void saveFilterParams(TableEntity table, List<Field> fields) throws BadRequestAlertException {
//        for (var field : fields) {
//            switch (field.getFieldStatus()) {
//                case DELETE -> {
//                    if (field.getId() == null || field.getId() <= 0)
//                        throw new BadRequestAlertException("Field id must not be null and must be positive", "Field",  "id", HttpStatus.BAD_REQUEST);
//                    fieldService.deleteById(fi)
//                }
//                case NEW -> {
//                    if (filter.getId() != null)
//                        throw new BadRequestAlertException("Filter id must be null for NEW status", "Filter", "Id", HttpStatus.BAD_REQUEST);
//                    filter.setField(field);
//                    filter.setFilterParamStatus(FilterParamStatus.ACTIVE);
//                    filterParamRepository.save(filter);
//                }
//                case CHANGED -> {
//                    if (filter.getId() == null)
//                        throw new BadRequestAlertException("Filter id must not be null for CHANGED status", "Filter", "Id", HttpStatus.BAD_REQUEST);
//                    filter.setField(field);
//                    filter.setFilterParamStatus(FilterParamStatus.ACTIVE);
//                    filterParamRepository.save(filter);
//                }
//            }
//        }
//    }
}
