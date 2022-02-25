package ru.gb.bulgakov.persist;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "roles")
public class Role {
    @Id
    private Long id;

    public Role() {
    }
}
