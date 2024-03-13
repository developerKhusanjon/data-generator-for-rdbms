package io.exadot.exadotdatafaker.service.mapper;

import io.exadot.exadotdatafaker.entity.generator.DataField;
import io.exadot.exadotdatafaker.service.dto.generator.DataFieldDto;
import org.mapstruct.Mapper;
import org.springframework.stereotype.Component;

@Mapper(componentModel = "spring")
@Component
public interface DataFieldMapper extends EntityMapper<DataFieldDto, DataField> {
}
