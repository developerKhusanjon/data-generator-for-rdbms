package io.exadot.exadotdatafaker.service.impl;

import io.exadot.exadotdatafaker.controller.exceptions.BadRequestAlertException;
import io.exadot.exadotdatafaker.entity.category.CategoryType;
import io.exadot.exadotdatafaker.entity.category.Category;
import io.exadot.exadotdatafaker.repo.CategoryTypeRepository;
import io.exadot.exadotdatafaker.repo.CategoryRepository;
import io.exadot.exadotdatafaker.service.CategoryService;
import io.exadot.exadotdatafaker.service.dto.AlertResponseDto;
import io.exadot.exadotdatafaker.service.dto.category.CategoryTypeDto;
import io.exadot.exadotdatafaker.service.dto.category.CategoryDto;
import io.exadot.exadotdatafaker.service.mapper.CategoryTypeMapper;
import io.exadot.exadotdatafaker.service.mapper.CategoryMapper;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository categoryRepository;
    private final CategoryTypeRepository categoryTypeRepository;
    private final CategoryMapper categoryMapper;
    private final CategoryTypeMapper categoryTypeMapper;

    @Override
    public CategoryDto createCategory(CategoryDto categoryDto) {
        return categoryMapper.toDto(categoryRepository.save(categoryMapper.toEntity(categoryDto)));
    }

    @Override
    public CategoryDto updateCategory(CategoryDto categoryDto) {
        return categoryMapper.toDto(categoryRepository.save(categoryMapper.toEntity(categoryDto)));
    }

    @Override
    public Page<CategoryDto> findAllCategories(Pageable pageable) {
        return categoryRepository.findAll(pageable).map(categoryMapper::toDto);
    }

    @Override
    public CategoryDto findOneCategory(Long id) throws BadRequestAlertException {
        Optional<Category> category = categoryRepository.findById(id);
        if (category.isEmpty())
            throw new BadRequestAlertException("Category not found", "Category", "id - " + id, HttpStatus.NOT_FOUND);
        return categoryMapper.toDto(category.get());
    }

    @Override
    public AlertResponseDto clearCategory(Long id) {
        categoryTypeRepository.deleteAllByCategoryId(id);
        return new AlertResponseDto("Category cleared successfully", true);
    }

    @Transactional
    @Override
    public AlertResponseDto deleteCategory(Long id) throws BadRequestAlertException {
        if (categoryTypeRepository.existsByCategoryId(id))
            throw new BadRequestAlertException(
                    "Category types must be migrated or cleared before deleting category", "Category - clear types!", "id - " + id, HttpStatus.CONFLICT);

        categoryRepository.deleteById(id);
        return new AlertResponseDto("Category deleted successfully", true);
    }

    @Override
    public List<CategoryTypeDto> getAllCategoryTypes(Long categoryId) {
        return categoryTypeMapper.toDto(categoryTypeRepository.findAllByCategoryId(categoryId));
    }

    @Override
    public CategoryTypeDto getOneCategoryType(Long categoryId, Long categoryTypeId) throws BadRequestAlertException {
        Optional<CategoryType> categoryType = categoryTypeRepository.findByIdAndCategoryId(categoryTypeId, categoryId);
        if (categoryType.isEmpty())
            throw new BadRequestAlertException(
                    "CategoryType not exist in this Category", "CategoryType", "categoryId - " + categoryId + "   categoryTypeId - " + categoryTypeId, HttpStatus.CONFLICT);
        return categoryTypeMapper.toDto(categoryType.get());
    }

    @Override
    public CategoryTypeDto addCategoryType(Long categoryId, CategoryTypeDto categoryTypeDto) throws BadRequestAlertException {
        Optional<Category> category = categoryRepository.findById(categoryId);
        if (category.isEmpty())
            throw new BadRequestAlertException("Category not found", "Category", "id - " + categoryId, HttpStatus.NOT_FOUND);

        CategoryType categoryType = categoryTypeMapper.toEntity(categoryTypeDto);
        categoryType.setCategory(category.get());

        return categoryTypeMapper.toDto(categoryTypeRepository.save(categoryType));
    }

    @Override
    public CategoryTypeDto modifyCategoryType(Long categoryId, CategoryTypeDto categoryTypeDto) throws BadRequestAlertException {
        Optional<CategoryType> categoryType = categoryTypeRepository.findByIdAndCategoryId(categoryTypeDto.getId(), categoryId);
        if (categoryType.isEmpty())
            throw new BadRequestAlertException(
                    "CategoryType not exist in this Category", "CategoryType", "categoryId - " + categoryId + "   categoryTypeId - " + categoryTypeDto.getId(), HttpStatus.CONFLICT);

        categoryType.get().setName(categoryTypeDto.getName());
        categoryType.get().setDescription(categoryTypeDto.getDescription());
        categoryType.get().setHasParams(categoryTypeDto.getHasParams());
        categoryType.get().setParamsMaxCount(categoryTypeDto.getParamsMaxCount());

        return categoryTypeMapper.toDto(categoryTypeRepository.save(categoryType.get()));
    }

    @Override
    public AlertResponseDto removeCategoryType(Long categoryId, Long categoryTypeId) {
        categoryTypeRepository.deleteByIdAndCategoryId(categoryTypeId, categoryId);
        return new AlertResponseDto("CategoryType removed successfully", true);
    }
}
