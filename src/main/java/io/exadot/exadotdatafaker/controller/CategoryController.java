package io.exadot.exadotdatafaker.controller;

import io.exadot.exadotdatafaker.controller.exceptions.BadRequestAlertException;
import io.exadot.exadotdatafaker.service.CategoryService;
import io.exadot.exadotdatafaker.service.dto.AlertResponseDto;
import io.exadot.exadotdatafaker.service.dto.category.CategoryDto;
import io.exadot.exadotdatafaker.service.dto.category.CategoryTypeDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/categories")
public class CategoryController {

    private final CategoryService categoryService;

    @Operation(description = "Create category")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "if category created successfully, return created category properties",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = CategoryDto.class))}),
            @ApiResponse(responseCode = "409", description = "required fields",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = Map.class),
                            examples = {@ExampleObject(value = "{name:'name is required'...}")})),
            @ApiResponse(responseCode = "400", description = "Other situations throw  BadRequestAlert exception",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = BadRequestAlertException.class)))})
    @PostMapping
    public ResponseEntity<CategoryDto> createCategory(@RequestBody @Valid CategoryDto categoryDto) throws BadRequestAlertException {
        if(categoryDto.getId() != null){
            throw new BadRequestAlertException("Category Id must be null", "Category","id");
        }
        return new ResponseEntity<>(categoryService.createCategory(categoryDto), HttpStatus.CREATED);
    }

    @Operation(description = "Update category")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "if category updated successfully, return updated category properties",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = CategoryDto.class))}),
            @ApiResponse(responseCode = "409", description = "required fields",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = Map.class),
                            examples = {@ExampleObject(value = "{name:'name is required'...}")})),
            @ApiResponse(responseCode = "400", description = "Other situations throw  BadRequestAlert exception",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = BadRequestAlertException.class)))})
    @PutMapping
    public ResponseEntity<CategoryDto> updateCategory(@RequestBody @Valid CategoryDto categoryDto) throws BadRequestAlertException {
        if(categoryDto.getId() == null){
            throw new BadRequestAlertException("Category Id must mot be null", "Category","id");
        }
        return new ResponseEntity<>(categoryService.createCategory(categoryDto), HttpStatus.OK);
    }

    @Operation(description = "Add category type")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "if category type created successfully, return created type properties",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = CategoryTypeDto.class))}),
            @ApiResponse(responseCode = "409", description = "required fields",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = Map.class),
                            examples = {@ExampleObject(value = "{name:'name is required'...}")})),
            @ApiResponse(responseCode = "400", description = "Other situations throw  BadRequestAlert exception",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = BadRequestAlertException.class)))})
    @PostMapping("/{id}/types")
    public ResponseEntity<CategoryTypeDto> addCategoryType(@PathVariable Long id, @RequestBody @Valid CategoryTypeDto categoryTypeDto) throws BadRequestAlertException {
        if(categoryTypeDto.getId() != null){
            throw new BadRequestAlertException("CategoryType Id must be null", "CategoryType","id");
        }
        return new ResponseEntity<>(categoryService.addCategoryType(id, categoryTypeDto), HttpStatus.CREATED);
    }

    @Operation(description = "Update category type")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "if category type updated successfully, return updated type properties",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = CategoryTypeDto.class))}),
            @ApiResponse(responseCode = "409", description = "required fields",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = Map.class),
                            examples = {@ExampleObject(value = "{name:'name is required'...}")})),
            @ApiResponse(responseCode = "400", description = "Other situations throw  BadRequestAlert exception",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = BadRequestAlertException.class)))})
    @PutMapping("/{id}/types")
    public ResponseEntity<CategoryTypeDto> modifyCategoryType(@PathVariable Long id, @RequestBody @Valid CategoryTypeDto categoryTypeDto) throws BadRequestAlertException {
        if(categoryTypeDto.getId() == null){
            throw new BadRequestAlertException("CategoryType Id must not be null", "CategoryType","id");
        }
        return new ResponseEntity<>(categoryService.modifyCategoryType(id, categoryTypeDto), HttpStatus.OK);
    }

    @Operation(description = "Get all categories")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "if categories fetched successfully, return fetched data",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = CategoryDto.class))}),
            @ApiResponse(responseCode = "404", description = "Not found BadRequestAlert exception",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = BadRequestAlertException.class)))})
    @GetMapping
    public ResponseEntity<Page<CategoryDto>> getAllCategories(Pageable pageable) {
        return ResponseEntity.ok(categoryService.findAllCategories(pageable));
    }

    @Operation(description = "Get one category")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "if category fetched successfully, return fetched data",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = CategoryDto.class))}),
            @ApiResponse(responseCode = "404", description = "Not found BadRequestAlert exception",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = BadRequestAlertException.class)))})
    @GetMapping("/{id}")
    public ResponseEntity<CategoryDto> getCategory(@PathVariable("id") Long id) throws BadRequestAlertException {
        return ResponseEntity.ok(categoryService.findOneCategory(id));
    }

    @Operation(description = "Get category types")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "if category types fetched successfully, return fetched data",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = CategoryTypeDto.class))}),
            @ApiResponse(responseCode = "404", description = "Not found BadRequestAlert exception",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = BadRequestAlertException.class)))})
    @GetMapping("/{id}/types")
    public ResponseEntity<List<CategoryTypeDto>> getCategoryTypes(@PathVariable Long id) {
        return ResponseEntity.ok(categoryService.getAllCategoryTypes(id));
    }

    @Operation(description = "Get category type")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "if category type fetched successfully, return fetched data",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = CategoryTypeDto.class))}),
            @ApiResponse(responseCode = "404", description = "Not found BadRequestAlert exception",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = BadRequestAlertException.class)))})
    @GetMapping("/{id}/types/{categoryTypeId}")
    public ResponseEntity<CategoryTypeDto> getCategoryType(@PathVariable Long id, @PathVariable Long categoryTypeId) throws BadRequestAlertException {
        return ResponseEntity.ok(categoryService.getOneCategoryType(id, categoryTypeId));
    }

    @Operation(description = "Clear category")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "if category cleared successfully, return Alert Notification",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = AlertResponseDto.class))}),
            @ApiResponse(responseCode = "404", description = "Not found BadRequestAlert exception",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = BadRequestAlertException.class)))})
    @DeleteMapping("/{id}/clear")
    public ResponseEntity<AlertResponseDto> clearCategory(@PathVariable("id") Long id) {
        return new ResponseEntity<>(categoryService.clearCategory(id), HttpStatus.OK);
    }

    @Operation(description = "Delete category")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "if category deleted successfully, return Alert Notification",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = AlertResponseDto.class))}),
            @ApiResponse(responseCode = "404", description = "Not found BadRequestAlert exception",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = BadRequestAlertException.class)))})
    @DeleteMapping("/{id}")
    public ResponseEntity<AlertResponseDto> deleteCategory(@PathVariable("id") Long id) throws BadRequestAlertException {
        return new ResponseEntity<>(categoryService.deleteCategory(id), HttpStatus.OK);
    }

    @Operation(description = "Remove category type")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "if category type removed successfully, return Alert Notification",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = AlertResponseDto.class))}),
            @ApiResponse(responseCode = "404", description = "Not found BadRequestAlert exception",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = BadRequestAlertException.class)))})
    @DeleteMapping("/{id}/types/{categoryTypeId}")
    public ResponseEntity<AlertResponseDto> removeCategoryType(@PathVariable Long id, @PathVariable Long categoryTypeId) {
        return ResponseEntity.ok(categoryService.removeCategoryType(id, categoryTypeId));
    }
}
