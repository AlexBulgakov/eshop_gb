package ru.gb.bulgakov.persist;

import org.springframework.data.jpa.domain.Specification;
import ru.gb.bulgakov.persist.model.Product;

public class ProductSpecification {

    public static Specification<Product> byCategory(long categoryId) {
        return (root, query, builder) -> builder.equal(root.get("category").get("id"), categoryId);
    }

    public static Specification<Product> byBrand(long brandId) {
        return (root, query, builder) -> builder.equal(root.get("brand").get("id"), brandId);
    }

    public static Specification<Product> byName(String pattern) {
        return (root, query, builder) -> builder.like(root.get("name"), "%" + pattern + "%");
    }
}
