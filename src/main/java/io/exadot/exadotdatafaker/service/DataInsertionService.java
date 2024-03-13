package io.exadot.exadotdatafaker.service;

import io.exadot.exadotdatafaker.controller.exceptions.BadRequestAlertException;
import io.exadot.exadotdatafaker.service.dto.AlertResponseDto;
import io.exadot.exadotdatafaker.service.dto.Props;

import java.lang.reflect.InvocationTargetException;

public interface DataInsertionService {
    AlertResponseDto populateWithFakeData(Props props) throws BadRequestAlertException, InvocationTargetException;

    AlertResponseDto clearFromFakeData(Props props);
}
