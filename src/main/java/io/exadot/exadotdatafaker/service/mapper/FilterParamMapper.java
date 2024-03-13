package io.exadot.exadotdatafaker.service.mapper;

import io.exadot.exadotdatafaker.entity.FilterParam;
import io.exadot.exadotdatafaker.service.dto.FilterParamsDto;
import org.mapstruct.Mapper;
import org.springframework.stereotype.Component;

@Mapper(componentModel = "spring")
@Component
public interface FilterParamMapper extends EntityMapper<FilterParamsDto, FilterParam> {
}
