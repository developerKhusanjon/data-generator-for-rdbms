package io.exadot.exadotdatafaker.service;


import io.exadot.exadotdatafaker.service.dto.AlertResponseDto;
import io.exadot.exadotdatafaker.service.dto.generator.DataFieldDto;
import io.exadot.exadotdatafaker.service.dto.generator.DataTypeDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface DataTypeService {
    DataTypeDto createDataType(DataTypeDto dataTypeDto);

    DataTypeDto updateDataType(DataTypeDto dataTypeDto);

    Page<DataTypeDto> findAll(Pageable pageable);

    DataTypeDto findById(Long id);

    AlertResponseDto deleteById(DataTypeDto dataTypeDto);

    List<DataFieldDto> getAllDataFields(Long dataTypeId);

    DataFieldDto getOneDataField(Long dataFieldId);

    DataFieldDto addDataField(Long dataTypeId, DataFieldDto dataFieldDto);

    DataFieldDto modifyDataField(DataFieldDto dataFieldDto);

    AlertResponseDto removeDataField()
}
