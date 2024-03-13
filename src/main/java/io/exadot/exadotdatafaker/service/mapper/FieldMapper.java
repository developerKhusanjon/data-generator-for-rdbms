package io.exadot.exadotdatafaker.service.mapper;

import io.exadot.exadotdatafaker.entity.Field;
import io.exadot.exadotdatafaker.service.dto.FieldDto;
import org.mapstruct.Mapper;
import org.springframework.stereotype.Component;

@Mapper(componentModel = "spring")
@Component
public interface FieldMapper extends EntityMapper<FieldDto, Field> {
}
