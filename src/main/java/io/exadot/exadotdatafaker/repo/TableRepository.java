package io.exadot.exadotdatafaker.repo;

import io.exadot.exadotdatafaker.entity.TableEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TableRepository extends JpaRepository<TableEntity, Long> {
    List<TableEntity> findAllByDataSourceId(Long dataSource_id);

    boolean existsByDataSourceId(Long dataSourceId);
}
