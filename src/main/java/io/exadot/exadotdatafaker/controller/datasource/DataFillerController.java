package io.exadot.exadotdatafaker.controller.datasource;

import io.exadot.exadotdatafaker.service.DataFillerService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/data-filler")
public class DataFillerController {

    private final DataFillerService dataFillerService;

}
