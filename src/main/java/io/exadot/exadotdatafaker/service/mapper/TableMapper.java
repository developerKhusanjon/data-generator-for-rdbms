package io.exadot.exadotdatafaker.service.mapper;

import io.exadot.exadotdatafaker.entity.db.TableEntity;
import io.exadot.exadotdatafaker.service.dto.db.TableDto;
import org.mapstruct.Mapper;
import org.springframework.stereotype.Component;

@Mapper(componentModel = "spring", uses = FieldMapper.class)
@Component
public interface TableMapper extends EntityMapper<TableDto, TableEntity> {
}
