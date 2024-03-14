package io.exadot.exadotdatafaker.service.mapper;

import io.exadot.exadotdatafaker.entity.category.Category;
import io.exadot.exadotdatafaker.service.dto.category.CategoryDto;
import org.mapstruct.Mapper;
import org.springframework.stereotype.Component;

@Mapper(componentModel = "spring", uses = CategoryTypeMapper.class)
@Component
public interface CategoryMapper extends EntityMapper<CategoryDto, Category> {
}
