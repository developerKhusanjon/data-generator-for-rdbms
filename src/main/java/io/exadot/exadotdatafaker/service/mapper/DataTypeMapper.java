package io.exadot.exadotdatafaker.service.mapper;

import io.exadot.exadotdatafaker.entity.generator.DataType;
import io.exadot.exadotdatafaker.service.dto.generator.DataTypeDto;
import org.mapstruct.Mapper;
import org.springframework.stereotype.Component;

@Mapper(componentModel = "spring", uses = DataFieldMapper.class)
@Component
public interface DataTypeMapper extends EntityMapper<DataTypeDto, DataType> {
}
