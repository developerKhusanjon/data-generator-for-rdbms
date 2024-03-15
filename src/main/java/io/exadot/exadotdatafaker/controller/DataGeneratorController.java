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
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;
import java.lang.reflect.InvocationTargetException;
import java.util.stream.Stream;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/data")
public class DataGeneratorController {

    private final DataGeneratorService dataGeneratorService;

    @Operation(description = "Generate random fake data")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "if fake data generated successfully, return generated data",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = Stream.class))}),
            @ApiResponse(responseCode = "404", description = "Not found BadRequestAlert exception",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = BadRequestAlertException.class)))})
    @PostMapping(value = "/generate")
    public ResponseEntity<SseEmitter> generate(@RequestBody @Valid ObjectDto objectDto) throws InvocationTargetException {
        return new ResponseEntity<>(dataGeneratorService.generateData(objectDto), HttpStatus.OK);
    }

}
