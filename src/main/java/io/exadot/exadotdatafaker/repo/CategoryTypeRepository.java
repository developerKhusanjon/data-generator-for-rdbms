package io.exadot.exadotdatafaker.repo;

import io.exadot.exadotdatafaker.entity.category.CategoryType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface CategoryTypeRepository extends JpaRepository<CategoryType, Long> {
    List<CategoryType> findAllByCategoryId(Long category_id);

    Optional<CategoryType> findByIdAndCategoryId(Long id, Long category_id);

    void deleteByIdAndCategoryId(Long id, Long category_id);

    void deleteAllByCategoryId(Long category_id);

    boolean existsByCategoryId(Long category_id);
}
