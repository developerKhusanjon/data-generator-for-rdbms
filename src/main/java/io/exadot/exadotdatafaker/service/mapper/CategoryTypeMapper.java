package io.exadot.exadotdatafaker.service.mapper;

import io.exadot.exadotdatafaker.entity.category.CategoryType;
import io.exadot.exadotdatafaker.service.dto.category.CategoryTypeDto;
import org.mapstruct.Mapper;
import org.springframework.stereotype.Component;

@Mapper(componentModel = "spring")
@Component
public interface CategoryTypeMapper extends EntityMapper<CategoryTypeDto, CategoryType> {
}
