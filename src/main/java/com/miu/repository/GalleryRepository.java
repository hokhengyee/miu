package com.miu.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.miu.domain.Gallery;

/**
 * Spring Data JPA repository for the Gallery entity.
 */
public interface GalleryRepository extends JpaRepository<Gallery, Long> {

}
