package io.exadot.exadotdatafaker.service;

import io.exadot.exadotdatafaker.controller.exceptions.BadRequestAlertException;
import io.exadot.exadotdatafaker.service.dto.AlertResponseDto;
import io.exadot.exadotdatafaker.service.dto.db.FieldDto;

import java.util.List;

public interface FieldService {

    List<FieldDto> findAllByTableId(Long tableId);

    FieldDto findById(Long id, Long tableId) throws BadRequestAlertException;

    FieldDto addField(FieldDto fieldDto, Long tableId) throws BadRequestAlertException;

    FieldDto updateField(FieldDto fieldDto, Long tableId) throws BadRequestAlertException;

    AlertResponseDto deleteById(Long id);

    boolean checkByIdAndTableId(Long fieldId, Long tableId);
}
