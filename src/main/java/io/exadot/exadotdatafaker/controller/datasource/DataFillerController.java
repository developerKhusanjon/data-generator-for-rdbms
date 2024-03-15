package io.exadot.exadotdatafaker.controller.datasource;

import io.exadot.exadotdatafaker.controller.exceptions.BadRequestAlertException;
import io.exadot.exadotdatafaker.service.DataFillerService;
import io.exadot.exadotdatafaker.service.dto.AlertResponseDto;
import io.exadot.exadotdatafaker.service.dto.datasource.DBProps;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.lang.reflect.InvocationTargetException;
import java.util.Map;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/data-filler")
public class DataFillerController {

    private final DataFillerService dataFillerService;

    @Operation(description = "Populate with fake data")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "if tables populated successfully, return success alert",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = AlertResponseDto.class))}),
            @ApiResponse(responseCode = "409", description = "required fields",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = Map.class),
                            examples = {@ExampleObject(value = "{name:'name is required'...}")})),
            @ApiResponse(responseCode = "400", description = "Other situations throw  BadRequestAlert exception",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = BadRequestAlertException.class)))})
    @PostMapping("/fill")
    public ResponseEntity<AlertResponseDto> fillWithData(@RequestBody @Valid DBProps dbProps) throws BadRequestAlertException, InvocationTargetException {
        return ResponseEntity.ok(dataFillerService.populateWithFakeData(dbProps));
    }

}
