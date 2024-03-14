package io.exadot.exadotdatafaker.repo;

import io.exadot.exadotdatafaker.entity.datasource.Field;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface FieldRepository extends JpaRepository<Field, Long> {
    List<Field> findAllByTableId(Long table_id);

    Optional<Field> findByIdAndTableId(Long id, Long table_id);

    boolean existsByIdAndTableId(Long id, Long table_id);
}
