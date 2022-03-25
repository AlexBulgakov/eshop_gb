package ru.gb.bulgakov.persist;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.gb.bulgakov.persist.model.Category;

public interface CategoryRepository extends JpaRepository<Category, Long> {
}
