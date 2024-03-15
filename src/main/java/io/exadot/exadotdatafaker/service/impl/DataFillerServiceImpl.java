package io.exadot.exadotdatafaker.service.impl;

import io.exadot.exadotdatafaker.controller.exceptions.BadRequestAlertException;
import io.exadot.exadotdatafaker.repo.dao.SimpleJdbcInsertDao;
import io.exadot.exadotdatafaker.service.dto.AlertResponseDto;
import io.exadot.exadotdatafaker.service.dto.datasource.DBProps;
import io.exadot.exadotdatafaker.generator.DataGenerator;
import io.exadot.exadotdatafaker.repo.TableRepository;
import io.exadot.exadotdatafaker.service.DataFillerService;
import io.exadot.exadotdatafaker.service.dto.datasource.FieldDto;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.lang.reflect.InvocationTargetException;

@RequiredArgsConstructor
@Service
public class DataFillerServiceImpl implements DataFillerService {

    private final TableRepository tableRepository;
    private final SimpleJdbcInsertDao simpleJdbcInsertDao;

    @Transactional
    @Override
    public AlertResponseDto populateWithFakeData(DBProps DBProps) throws BadRequestAlertException, InvocationTargetException {

        var generatedDataList = DataGenerator.generate(DBProps.getTable().getFields(), DBProps.getCount());

        String[] columns = DBProps.getTable().getFields()
                .stream().map(FieldDto::getFieldName).toList().toArray(new String[0]);

        return simpleJdbcInsertDao.insertAll(DBProps.getDataSource(), generatedDataList,
                DBProps.getTable().getSchemaName(), DBProps.getTable().getTableName(), DBProps.getTable().getGeneratedKey(), columns);
    }

    @Override
    public AlertResponseDto clearFromFakeData(DBProps DBProps) {
        return null;
    }
}
