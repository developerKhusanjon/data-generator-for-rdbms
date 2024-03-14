package io.exadot.exadotdatafaker.controller;

import io.exadot.exadotdatafaker.controller.exceptions.BadRequestAlertException;
import io.exadot.exadotdatafaker.service.DataGeneratorService;
import io.exadot.exadotdatafaker.service.dto.ObjectDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
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
import java.util.stream.Stream;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/data-generator")
public class DataGeneratorController {

    private final DataGeneratorService dataGeneratorService;

    @Operation(description = "Generate random fake data")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "if fake data generated successfully, return generated data",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = Stream.class))}),
            @ApiResponse(responseCode = "404", description = "Not found BadRequestAlert exception",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = BadRequestAlertException.class)))})
    @PostMapping("/generate")
    public ResponseEntity<Stream<Map<String, Object>>> generate(@RequestBody @Valid ObjectDto objectDto) throws InvocationTargetException {
        return ResponseEntity.ok(dataGeneratorService.generateData(objectDto));
    }
}
