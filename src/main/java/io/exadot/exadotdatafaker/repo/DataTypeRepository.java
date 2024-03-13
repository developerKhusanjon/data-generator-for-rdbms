package io.exadot.exadotdatafaker.repo;

import io.exadot.exadotdatafaker.entity.generator.DataType;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DataTypeRepository extends JpaRepository<DataType, Long> {
}
