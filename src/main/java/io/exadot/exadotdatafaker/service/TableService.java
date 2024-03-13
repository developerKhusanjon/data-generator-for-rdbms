package io.exadot.exadotdatafaker.service;

import io.exadot.exadotdatafaker.controller.exceptions.BadRequestAlertException;
import io.exadot.exadotdatafaker.service.dto.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface TableService {
    List<TableDto> findAllByDataSourceId(Long dataSourceId);

    TableDto findById(Long id) throws BadRequestAlertException;

    List<FieldDto> getTableFields(Long tableId);

    FieldDto getField(Long fieldId) throws BadRequestAlertException;

    TableDto createTable(NewTableDto newTableDto) throws BadRequestAlertException;

    TableDto updateTable(UpdateTableDto updateTableDto) throws BadRequestAlertException;

    FieldDto createTableField(Long tableId, FieldDto field) throws BadRequestAlertException;

    FieldDto updateTableField(FieldDto field) throws BadRequestAlertException;

    AlertResponseDto deleteTable(Long id);

    AlertResponseDto removeTableField(Long fieldId);

    boolean checkByDataSourceId(Long id);
}
