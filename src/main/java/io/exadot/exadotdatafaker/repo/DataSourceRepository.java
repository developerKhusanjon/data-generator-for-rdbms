package io.exadot.exadotdatafaker.repo;

import io.exadot.exadotdatafaker.entity.db.DataSourceEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DataSourceRepository extends JpaRepository<DataSourceEntity, Long> {
}
