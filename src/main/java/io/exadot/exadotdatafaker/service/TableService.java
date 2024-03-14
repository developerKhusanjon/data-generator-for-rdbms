package io.exadot.exadotdatafaker.service;

import io.exadot.exadotdatafaker.controller.exceptions.BadRequestAlertException;
import io.exadot.exadotdatafaker.service.dto.*;
import io.exadot.exadotdatafaker.service.dto.datasource.FieldDto;
import io.exadot.exadotdatafaker.service.dto.datasource.NewTableDto;
import io.exadot.exadotdatafaker.service.dto.datasource.TableDto;
import io.exadot.exadotdatafaker.service.dto.datasource.UpdateTableDto;

import java.util.List;

public interface TableService {
    List<TableDto> findAllByDataSourceId(Long dataSourceId);

    TableDto findById(Long id) throws BadRequestAlertException;

    List<FieldDto> getTableFields(Long tableId);

    FieldDto getField(Long fieldId, Long tableId) throws BadRequestAlertException;

    TableDto createTable(NewTableDto newTableDto) throws BadRequestAlertException;

    TableDto updateTable(UpdateTableDto updateTableDto) throws BadRequestAlertException;

    FieldDto createTableField(Long tableId, FieldDto field) throws BadRequestAlertException;

    FieldDto updateTableField(FieldDto field, Long tableId) throws BadRequestAlertException;

    AlertResponseDto deleteTable(Long id);

    AlertResponseDto removeTableField(Long fieldId, Long tableId) throws BadRequestAlertException;

    boolean checkByDataSourceId(Long id);
}
