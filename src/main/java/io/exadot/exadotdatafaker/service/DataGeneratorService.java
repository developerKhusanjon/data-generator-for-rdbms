package io.exadot.exadotdatafaker.service;

import io.exadot.exadotdatafaker.service.dto.ObjectDto;

import java.lang.reflect.InvocationTargetException;
import java.util.Map;
import java.util.stream.Stream;

public interface DataGeneratorService {

    Stream<Map<String, Object>> generateData(ObjectDto objectDto) throws InvocationTargetException;
}
