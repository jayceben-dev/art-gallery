package com.example.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.entity.Artwork;

public interface ArtworkRepository extends JpaRepository<Artwork, Long> {

}
