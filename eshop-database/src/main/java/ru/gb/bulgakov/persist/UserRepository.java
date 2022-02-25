package ru.gb.bulgakov.persist;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.gb.bulgakov.persist.model.User;

public interface UserRepository extends JpaRepository<User, Long> {
}
