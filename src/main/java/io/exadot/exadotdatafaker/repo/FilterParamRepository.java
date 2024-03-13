package io.exadot.exadotdatafaker.repo;

import io.exadot.exadotdatafaker.entity.db.FilterParam;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FilterParamRepository extends JpaRepository<FilterParam, Long> {
    List<FilterParam> findAllByFieldId(Long field_id);
    void deleteAllByFieldId(Long field_id);
}
