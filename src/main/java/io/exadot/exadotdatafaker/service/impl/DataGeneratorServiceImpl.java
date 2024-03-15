package io.exadot.exadotdatafaker.service.impl;

import io.exadot.exadotdatafaker.generator.DataGenerator;
import io.exadot.exadotdatafaker.service.DataGeneratorService;
import io.exadot.exadotdatafaker.service.dto.ObjectDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.Stream;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class DataGeneratorServiceImpl implements DataGeneratorService {
    private final ExecutorService virtualThreadPerTaskExecutor = Executors.newVirtualThreadPerTaskExecutor();

    @Override
    public SseEmitter generateData(ObjectDto objectDto) {
        Stream<Map<String, Object>> generatedSteamData
                = DataGenerator.generate(objectDto.getFields(), objectDto.getCount());

        return streamGeneratedData(generatedSteamData);
    }

    private SseEmitter streamGeneratedData(Stream<Map<String, Object>> stream) {
        SseEmitter sseEmitter = new SseEmitter();

        virtualThreadPerTaskExecutor.execute(() -> {
            try {
                stream.forEach(data -> {
                    try {
                        sseEmitter.send(
                                data, MediaType.APPLICATION_JSON);
                    } catch (Exception ex) {
                        sseEmitter.completeWithError(ex);
                    }
                });
            } finally {
                sseEmitter.complete();
            }
        });

        return sseEmitter;
    }

}
