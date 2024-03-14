package io.exadot.exadotdatafaker.service.impl;

import io.exadot.exadotdatafaker.generator.DataGenerator;
import io.exadot.exadotdatafaker.service.DataGeneratorService;
import io.exadot.exadotdatafaker.service.dto.ObjectDto;
import org.springframework.stereotype.Service;

import java.lang.reflect.InvocationTargetException;
import java.util.Map;
import java.util.stream.Stream;

@Service
public class DataGeneratorServiceImpl implements DataGeneratorService {
    @Override
    public Stream<Map<String, Object>> generateData(ObjectDto objectDto) throws InvocationTargetException {
        return DataGenerator.generate(objectDto.getFields(), objectDto.getCount());
    }
}
