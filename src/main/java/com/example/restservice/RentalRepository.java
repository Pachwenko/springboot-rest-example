package com.example.restservice;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

public interface RentalRepository extends JpaRepository<Rental, Long> {
  List<Rental> findByAvailable(boolean available);

  List<Rental> findByOwnerContaining(String owner);

  List<Rental> findByDescriptionContaining(String description);
}