package io.exadot.exadotdatafaker.service;


import io.exadot.exadotdatafaker.controller.exceptions.BadRequestAlertException;
import io.exadot.exadotdatafaker.service.dto.AlertResponseDto;
import io.exadot.exadotdatafaker.service.dto.category.CategoryTypeDto;
import io.exadot.exadotdatafaker.service.dto.category.CategoryDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface CategoryService {
    CategoryDto createCategory(CategoryDto categoryDto);

    CategoryDto updateCategory(CategoryDto categoryDto);

    Page<CategoryDto> findAllCategories(Pageable pageable);

    CategoryDto findOneCategory(Long id) throws BadRequestAlertException;

    AlertResponseDto clearCategory(Long id);

    AlertResponseDto deleteCategory(Long id) throws BadRequestAlertException;

    List<CategoryTypeDto> getAllCategoryTypes(Long categoryId);

    CategoryTypeDto getOneCategoryType(Long categoryId, Long categoryTypeId) throws BadRequestAlertException;

    CategoryTypeDto addCategoryType(Long categoryId, CategoryTypeDto categoryTypeDto) throws BadRequestAlertException;

    CategoryTypeDto modifyCategoryType(Long categoryId, CategoryTypeDto categoryTypeDto) throws BadRequestAlertException;

    AlertResponseDto removeCategoryType(Long categoryId, Long categoryTypeId);
}
