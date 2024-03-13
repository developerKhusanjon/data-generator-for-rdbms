package io.exadot.exadotdatafaker.service.mapper;

import io.exadot.exadotdatafaker.entity.db.DataSourceEntity;
import io.exadot.exadotdatafaker.service.dto.db.DataSourceDto;
import org.mapstruct.Mapper;
import org.springframework.stereotype.Component;

@Mapper(componentModel = "spring")
@Component
public interface DataSourceMapper extends EntityMapper<DataSourceDto, DataSourceEntity> {
}
