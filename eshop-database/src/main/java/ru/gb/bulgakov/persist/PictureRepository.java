package ru.gb.bulgakov.persist;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.gb.bulgakov.persist.model.Picture;

public interface PictureRepository extends JpaRepository<Picture, Long> {
}
