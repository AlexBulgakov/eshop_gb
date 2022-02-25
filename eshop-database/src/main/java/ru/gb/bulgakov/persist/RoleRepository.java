package ru.gb.bulgakov.persist;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.gb.bulgakov.persist.model.Role;

public interface RoleRepository extends JpaRepository<Role, Long> {
}
