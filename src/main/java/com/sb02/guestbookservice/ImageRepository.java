package com.sb02.guestbookservice;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ImageRepository extends JpaRepository<Image, Long> {

  Optional<Image> findByGuestbookId(Long id);

}
