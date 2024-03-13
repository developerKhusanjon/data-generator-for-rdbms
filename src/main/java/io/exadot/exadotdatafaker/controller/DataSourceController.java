package io.exadot.exadotdatafaker.controller;

import io.exadot.exadotdatafaker.controller.exceptions.BadRequestAlertException;
import io.exadot.exadotdatafaker.service.DataSourceService;
import io.exadot.exadotdatafaker.service.dto.AlertResponseDto;
import io.exadot.exadotdatafaker.service.dto.db.DataSourceDto;
import io.exadot.exadotdatafaker.service.dto.db.TableDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/data-sources")
public class DataSourceController {

    private final DataSourceService dataSourceService;

    @Operation(description = "Create datasource")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "if datasource created successfully, return created datasource properties",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = DataSourceDto.class))}),
            @ApiResponse(responseCode = "409", description = "required fields",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = Map.class),
                            examples = {@ExampleObject(value = "{name:'name is required'...}")})),
            @ApiResponse(responseCode = "400", description = "Other situations throw  BadRequestAlert exception",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = BadRequestAlertException.class)))})
    @PostMapping
    public ResponseEntity<DataSourceDto> createDataSource(@RequestBody @Valid DataSourceDto dataSourceDto) throws BadRequestAlertException {
        if(dataSourceDto.getId() != null){
            throw new BadRequestAlertException("Id must be null", "DataSource","id");
        }
        return new ResponseEntity<>(dataSourceService.createDataSource(dataSourceDto), HttpStatus.CREATED);
    }

    @Operation(description = "Update datasource")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "if datasource updated successfully, return updated datasource properties",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = DataSourceDto.class))}),
            @ApiResponse(responseCode = "409", description = "required fields",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = Map.class),
                            examples = {@ExampleObject(value = "{name:'name is required'...}")})),
            @ApiResponse(responseCode = "400", description = "Other situations throw  BadRequestAlert exception",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = BadRequestAlertException.class)))})
    @PutMapping
    public ResponseEntity<DataSourceDto> updateDataSource(@RequestBody @Valid DataSourceDto dataSourceDto) throws BadRequestAlertException {
        if(dataSourceDto.getId() == null){
            throw new BadRequestAlertException("Id must not be null", "DataSource","id");
        }
        return new ResponseEntity<>(dataSourceService.createDataSource(dataSourceDto), HttpStatus.CREATED);
    }

    @Operation(description = "Get all datasources")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "if datasources fetched successfully, return fetched data",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = DataSourceDto.class))}),
            @ApiResponse(responseCode = "404", description = "Not found BadRequestAlert exception",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = BadRequestAlertException.class)))})
    @GetMapping
    public ResponseEntity<List<DataSourceDto>> getAllDataSources() {
        return ResponseEntity.ok(dataSourceService.findAll());
    }

    @Operation(description = "Get one datasource")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "if datasource fetched successfully, return fetched data",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = DataSourceDto.class))}),
            @ApiResponse(responseCode = "404", description = "Not found BadRequestAlert exception",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = BadRequestAlertException.class)))})
    @GetMapping("/{id}")
    public ResponseEntity<DataSourceDto> getDataSource(@PathVariable("id") Long id) throws BadRequestAlertException {
        return ResponseEntity.ok(dataSourceService.findById(id));
    }

    @Operation(description = "Get all tables by datasource id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "if tables fetched successfully, return fetched data",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = TableDto.class))}),
            @ApiResponse(responseCode = "404", description = "Not found BadRequestAlert exception",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = BadRequestAlertException.class)))})
    @GetMapping("/{id}/tables/")
    public ResponseEntity<List<TableDto>> getAllTables(@PathVariable("id") Long id) {
        return ResponseEntity.ok(dataSourceService.getAllTablesByDataSourceId(id));
    }

    @Operation(description = "Clear datasource")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "if datasource cleared successfully, return Alert Notification",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = AlertResponseDto.class))}),
            @ApiResponse(responseCode = "404", description = "Not found BadRequestAlert exception",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = BadRequestAlertException.class)))})
    @DeleteMapping("/clear/{id}")
    public ResponseEntity<AlertResponseDto> clearDataSource(@PathVariable("id") Long id) throws BadRequestAlertException {
        return new ResponseEntity<>(dataSourceService.clearDataSource(id), HttpStatus.OK);
    }

    @Operation(description = "Delete datasource")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "if datasource deleted successfully, return Alert Notification",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = AlertResponseDto.class))}),
            @ApiResponse(responseCode = "404", description = "Not found BadRequestAlert exception",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = BadRequestAlertException.class)))})
    @DeleteMapping("/{id}")
    public ResponseEntity<AlertResponseDto> deleteDataSource(@PathVariable("id") Long id) throws BadRequestAlertException {
        return new ResponseEntity<>(dataSourceService.deleteDataSource(id), HttpStatus.OK);
    }
}
