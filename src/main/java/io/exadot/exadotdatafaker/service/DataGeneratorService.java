package io.exadot.exadotdatafaker.service;

import io.exadot.exadotdatafaker.service.dto.ObjectDto;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

public interface DataGeneratorService {

    SseEmitter generateData(ObjectDto objectDto);
}
