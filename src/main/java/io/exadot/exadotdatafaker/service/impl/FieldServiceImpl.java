package io.exadot.exadotdatafaker.service.impl;

import io.exadot.exadotdatafaker.controller.exceptions.BadRequestAlertException;
import io.exadot.exadotdatafaker.entity.db.Field;
import io.exadot.exadotdatafaker.entity.db.FilterParam;
import io.exadot.exadotdatafaker.entity.db.TableEntity;
import io.exadot.exadotdatafaker.repo.FieldRepository;
import io.exadot.exadotdatafaker.repo.FilterParamRepository;
import io.exadot.exadotdatafaker.repo.TableRepository;
import io.exadot.exadotdatafaker.service.FieldService;
import io.exadot.exadotdatafaker.service.dto.AlertResponseDto;
import io.exadot.exadotdatafaker.service.dto.db.FieldDto;
import io.exadot.exadotdatafaker.service.dto.enums.FilterParamStatus;
import io.exadot.exadotdatafaker.service.mapper.FieldMapper;
import io.exadot.exadotdatafaker.service.mapper.FilterParamMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class FieldServiceImpl implements FieldService {

    private final TableRepository tableRepository;
    private final FieldRepository fieldRepository;
    private final FilterParamRepository filterParamRepository;
    private final FieldMapper fieldMapper;
    private final FilterParamMapper filterParamMapper;

    @Override
    public List<FieldDto> findAllByTableId(Long tableId) {
        return fieldMapper.toDto(fieldRepository.findAllByTableId(tableId));
    }

    @Override
    public FieldDto findById(Long id, Long tableId) throws BadRequestAlertException {
        Optional<Field> field = fieldRepository.findByIdAndTableId(id, tableId);
        if (field.isEmpty())
            throw new BadRequestAlertException(
                    "Field not exist in this table", "Field", "fieldId - " + id + "   tableId - " + tableId, HttpStatus.NOT_FOUND);

        FieldDto fieldDto = fieldMapper.toDto(field.get());
        fieldDto.setFilterParams(
                filterParamMapper.toDto(filterParamRepository.findAllByFieldId(id)));

        return fieldDto;
    }

    @Transactional
    @Override
    public FieldDto addField(FieldDto fieldDto, Long tableId) throws BadRequestAlertException {
        Optional<TableEntity> table = tableRepository.findById(tableId);
        if (table.isEmpty())
            throw new BadRequestAlertException("Table not found", "Table", "id - " + tableId, HttpStatus.NOT_FOUND);

        Field field = fieldRepository.save(fieldMapper.toEntity(fieldDto));
        List<FilterParam> filterParams = filterParamMapper.toEntity(fieldDto.getFilterParams());

        saveFilterParams(field, filterParams);
        return fieldMapper.toDto(field);
    }

    @Transactional
    @Override
    public FieldDto updateField(FieldDto fieldDto, Long tableId) throws BadRequestAlertException {
        Optional<Field> field = fieldRepository.findByIdAndTableId(fieldDto.getId(), tableId);
        if (field.isEmpty())
            throw new BadRequestAlertException(
                    "Field not exist in this table", "Field", "fieldId - " + fieldDto.getId() + "   tableId - " + tableId, HttpStatus.NOT_FOUND);

        field.get().setFieldName(fieldDto.getFieldName());
        field.get().setFieldStatus(fieldDto.getFieldStatus());
        field.get().setGenerateBaseType(fieldDto.getGenerateBaseType());
        field.get().setGenerateValue(fieldDto.getGenerateValue());

        Field updateField = fieldRepository.save(field.get());
        List<FilterParam> filterParams = filterParamMapper.toEntity(fieldDto.getFilterParams());

        updateFilterParams(updateField, filterParams);

        return fieldMapper.toDto(updateField);
    }

    @Transactional
    @Override
    public AlertResponseDto deleteById(Long id) {
        filterParamRepository.deleteAllByFieldId(id);
        fieldRepository.deleteById(id);
        return new AlertResponseDto("Field deleted successfully", true);
    }

    @Override
    public boolean checkByIdAndTableId(Long fieldId, Long tableId) {
        return fieldRepository.existsByIdAndTableId(fieldId, tableId);
    }

    private void saveFilterParams(Field field, List<FilterParam> filterParams) throws BadRequestAlertException {
        for (var filter : filterParams) {
            if (filter.getFilterParamStatus() == FilterParamStatus.NEW && filter.getId() == null) {
                filter.setField(field);
                filter.setFilterParamStatus(FilterParamStatus.ACTIVE);
                filterParamRepository.save(filter);
            } else throw new BadRequestAlertException(
                    "FilterStatus must be NEW and filterId must be null for NEW status", "Filter", "id" + field.getId(), HttpStatus.BAD_REQUEST);
        }
    }

    private void updateFilterParams(Field field, List<FilterParam> filterParams) throws BadRequestAlertException {
        for (var filter : filterParams) {
            switch (filter.getFilterParamStatus()) {
                case REMOVED -> {
                    if (filter.getId() == null)
                        throw new BadRequestAlertException("Filter id must not be null for REMOVED status", "Filter", "id - null", HttpStatus.BAD_REQUEST);
                    filterParamRepository.deleteById(filter.getId());
                }
                case NEW -> {
                    if (filter.getId() != null)
                        throw new BadRequestAlertException("Filter id must be null for NEW status", "Filter", "id" + field.getId(), HttpStatus.BAD_REQUEST);
                    filter.setField(field);
                    filter.setFilterParamStatus(FilterParamStatus.ACTIVE);
                    filterParamRepository.save(filter);
                }
                case CHANGED -> {
                    if (filter.getId() == null)
                        throw new BadRequestAlertException("Filter id must not be null for CHANGED status", "Filter", "id - null", HttpStatus.BAD_REQUEST);
                    filter.setField(field);
                    filter.setFilterParamStatus(FilterParamStatus.ACTIVE);
                    filterParamRepository.save(filter);
                }
            }
        }
    }
}
