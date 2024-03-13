package io.exadot.exadotdatafaker.repo;

import io.exadot.exadotdatafaker.entity.Field;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FieldRepository extends JpaRepository<Field, Long> {
    List<Field> findAllByTableId(Long table_id);
}
