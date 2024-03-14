package io.exadot.exadotdatafaker.service.mapper;

import io.exadot.exadotdatafaker.entity.datasource.FilterParam;
import io.exadot.exadotdatafaker.service.dto.datasource.FilterParamsDto;
import org.mapstruct.Mapper;
import org.springframework.stereotype.Component;

@Mapper(componentModel = "spring")
@Component
public interface FilterParamMapper extends EntityMapper<FilterParamsDto, FilterParam> {
}
