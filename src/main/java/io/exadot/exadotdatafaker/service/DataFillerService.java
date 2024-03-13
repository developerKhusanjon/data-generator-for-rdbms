package io.exadot.exadotdatafaker.service;

import io.exadot.exadotdatafaker.controller.exceptions.BadRequestAlertException;
import io.exadot.exadotdatafaker.service.dto.AlertResponseDto;
import io.exadot.exadotdatafaker.service.dto.db.DBProps;

import java.lang.reflect.InvocationTargetException;

public interface DataFillerService {
    AlertResponseDto populateWithFakeData(DBProps DBProps) throws BadRequestAlertException, InvocationTargetException;

    AlertResponseDto clearFromFakeData(DBProps DBProps);
}
