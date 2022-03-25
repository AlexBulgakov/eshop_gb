package ru.gb.bulgakov.persist;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.gb.bulgakov.persist.model.Product;

public interface ProductRepository extends JpaRepository<Product, Long> {
}
