package io.exadot.exadotdatafaker.controller.datasource;

import io.exadot.exadotdatafaker.controller.exceptions.BadRequestAlertException;
import io.exadot.exadotdatafaker.service.TableService;
import io.exadot.exadotdatafaker.service.dto.*;
import io.exadot.exadotdatafaker.service.dto.datasource.FieldDto;
import io.exadot.exadotdatafaker.service.dto.datasource.NewTableDto;
import io.exadot.exadotdatafaker.service.dto.datasource.TableDto;
import io.exadot.exadotdatafaker.service.dto.datasource.UpdateTableDto;
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
@RequestMapping("/api/v1/tables")
public class TableController {

    private final TableService tableService;

    @Operation(description = "Create table")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "if table created successfully, return created table properties",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = TableDto.class))}),
            @ApiResponse(responseCode = "409", description = "required fields",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = Map.class),
                            examples = {@ExampleObject(value = "{name:'name is required'...}")})),
            @ApiResponse(responseCode = "400", description = "Other situations throw  BadRequestAlert exception",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = BadRequestAlertException.class)))})
    @PostMapping
    public ResponseEntity<TableDto> createTable(@RequestBody @Valid NewTableDto newTableDto) throws BadRequestAlertException {
        return new ResponseEntity<>(tableService.createTable(newTableDto), HttpStatus.CREATED);
    }

    @Operation(description = "Update table")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "if table updated successfully, return updated table properties",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = TableDto.class))}),
            @ApiResponse(responseCode = "409", description = "required fields",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = Map.class),
                            examples = {@ExampleObject(value = "{name:'name is required'...}")})),
            @ApiResponse(responseCode = "400", description = "Other situations throw  BadRequestAlert exception",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = BadRequestAlertException.class)))})
    @PutMapping
    public ResponseEntity<TableDto> updateTable(@RequestBody @Valid UpdateTableDto updateTableDto) throws BadRequestAlertException {
        return ResponseEntity.ok(tableService.updateTable(updateTableDto));
    }

    @Operation(description = "Create table field")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "if table field created successfully, return created field properties",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = FieldDto.class))}),
            @ApiResponse(responseCode = "409", description = "required fields",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = Map.class),
                            examples = {@ExampleObject(value = "{name:'name is required'...}")})),
            @ApiResponse(responseCode = "400", description = "Other situations throw  BadRequestAlert exception",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = BadRequestAlertException.class)))})
    @PostMapping("/{id}/fields")
    public ResponseEntity<FieldDto> createTableField(@PathVariable Long id, @RequestBody @Valid FieldDto field) throws BadRequestAlertException {
        if(field.getId() != null){
            throw new BadRequestAlertException("Field Id must be null", "Field", "id");
        }
        return new ResponseEntity<>(tableService.createTableField(id, field), HttpStatus.CREATED);
    }

    @Operation(description = "Update table field")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "if table field updated successfully, return updated field properties",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = FieldDto.class))}),
            @ApiResponse(responseCode = "409", description = "required fields",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = Map.class),
                            examples = {@ExampleObject(value = "{name:'name is required'...}")})),
            @ApiResponse(responseCode = "400", description = "Other situations throw  BadRequestAlert exception",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = BadRequestAlertException.class)))})
    @PutMapping("/{id}/fields")
    public ResponseEntity<FieldDto> updateTableField(@PathVariable Long id, @RequestBody @Valid FieldDto field) throws BadRequestAlertException {
        if(field.getId() == null){
            throw new BadRequestAlertException("Field Id must not be null", "Field", "id");
        }

        return ResponseEntity.ok(tableService.updateTableField(field, id));
    }

    @Operation(description = "Get one table")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "if table fetched successfully, return fetched data",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = TableDto.class))}),
            @ApiResponse(responseCode = "404", description = "Not found BadRequestAlert exception",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = BadRequestAlertException.class)))})
    @GetMapping("/{id}")
    public ResponseEntity<TableDto> getTable(@PathVariable("id") Long id) throws BadRequestAlertException {
        return ResponseEntity.ok(tableService.findById(id));
    }

    @Operation(description = "Get table fields")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "if table fields fetched successfully, return fetched data",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = FieldDto.class))}),
            @ApiResponse(responseCode = "404", description = "Not found BadRequestAlert exception",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = BadRequestAlertException.class)))})
    @GetMapping("/{id}/fields")
    public ResponseEntity<List<FieldDto>> getTableFields(@PathVariable Long id) {
        return ResponseEntity.ok(tableService.getTableFields(id));
    }

    @Operation(description = "Get table field")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "if table field fetched successfully, return fetched data",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = FieldDto.class))}),
            @ApiResponse(responseCode = "404", description = "Not found BadRequestAlert exception",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = BadRequestAlertException.class)))})
    @GetMapping("/{id}/fields/{fieldId}")
    public ResponseEntity<FieldDto> getField(@PathVariable Long id, @PathVariable Long fieldId) throws BadRequestAlertException {
        return ResponseEntity.ok(tableService.getField(id, fieldId));
    }

    @Operation(description = "Delete table")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "if table deleted successfully, return Alert Notification",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = AlertResponseDto.class))}),
            @ApiResponse(responseCode = "404", description = "Not found BadRequestAlert exception",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = BadRequestAlertException.class)))})
    @DeleteMapping("/{id}")
    public ResponseEntity<AlertResponseDto> deleteTable(@PathVariable("id") Long id) throws BadRequestAlertException {
        return new ResponseEntity<>(tableService.deleteTable(id), HttpStatus.OK);
    }

    @Operation(description = "Delete table field")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "if table field deleted successfully, return Alert Notification",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = AlertResponseDto.class))}),
            @ApiResponse(responseCode = "404", description = "Not found BadRequestAlert exception",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = BadRequestAlertException.class)))})
    @DeleteMapping("/{id}/fields/{fieldId}")
    public ResponseEntity<AlertResponseDto> removeTableField(@PathVariable Long id, @PathVariable Long fieldId) throws BadRequestAlertException {
        return ResponseEntity.ok(tableService.removeTableField(id, fieldId));
    }

}
