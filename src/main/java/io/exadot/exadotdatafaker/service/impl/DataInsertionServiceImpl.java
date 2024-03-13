package io.exadot.exadotdatafaker.service.impl;

import io.exadot.exadotdatafaker.controller.exceptions.BadRequestAlertException;
import io.exadot.exadotdatafaker.repo.dao.SimpleJdbcInsertDao;
import io.exadot.exadotdatafaker.service.dto.AlertResponseDto;
import io.exadot.exadotdatafaker.service.dto.Props;
import io.exadot.exadotdatafaker.entity.Field;
import io.exadot.exadotdatafaker.entity.TableEntity;
import io.exadot.exadotdatafaker.generator.DataGenerator;
import io.exadot.exadotdatafaker.repo.TableRepository;
import io.exadot.exadotdatafaker.service.DataInsertionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.lang.reflect.InvocationTargetException;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class DataInsertionServiceImpl implements DataInsertionService {

    private final TableRepository tableRepository;
    private final SimpleJdbcInsertDao simpleJdbcInsertDao;

    @Override
    public AlertResponseDto populateWithFakeData(Props props) throws BadRequestAlertException, InvocationTargetException {

        var generatedDataList = DataGenerator.generate(props.getTable().getFields(), props.getCount());

        return simpleJdbcInsertDao.insertAll(
                props.getDataSource(), generatedDataList, props.getTable().getTableName(), props.getTable().getGeneratedKey());
    }

    @Override
    public AlertResponseDto clearFromFakeData(Props props) {
        return null;
    }
}
