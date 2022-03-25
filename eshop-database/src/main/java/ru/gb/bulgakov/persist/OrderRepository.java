package ru.gb.bulgakov.persist;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.gb.bulgakov.persist.model.Order;

public interface OrderRepository extends JpaRepository<Order, Long> {
}
