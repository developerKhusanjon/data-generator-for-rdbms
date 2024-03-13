package io.exadot.exadotdatafaker.repo;

import io.exadot.exadotdatafaker.entity.generator.DataField;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DataFieldRepository extends JpaRepository<DataField, Long> {
}
