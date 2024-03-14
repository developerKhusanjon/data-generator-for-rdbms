package io.exadot.exadotdatafaker.repo;

import io.exadot.exadotdatafaker.entity.category.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<Category, Long> {
}
