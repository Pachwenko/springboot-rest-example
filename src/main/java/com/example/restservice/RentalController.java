package com.example.restservice;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

// Using * as the cross origin allows the frontend to access the backend
// but it is also a security risk. Ideally, we would use a specific domain and enforce it here AND elsewhere in this api.
@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api")
public class RentalController {
	@Autowired
	RentalRepository rentalRepository;

	@GetMapping("/rentals")
	public ResponseEntity<List<Rental>> getAllRentals(@RequestParam(required = false) String description) {
		try {
			List<Rental> rentals = new ArrayList<Rental>();
			if (description == null)
				rentalRepository.findAll().forEach(rentals::add);
			else
				rentalRepository.findByDescriptionContaining(description).forEach(rentals::add);
			if (rentals.isEmpty()) {
				return new ResponseEntity<>(HttpStatus.NO_CONTENT);
			}
			return new ResponseEntity<>(rentals, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@GetMapping("/rentals/{id}")
	public ResponseEntity<Rental> getRentalById(@PathVariable("id") long id) {
		Optional<Rental> rentalData = rentalRepository.findById(id);
		if (rentalData.isPresent()) {
			return new ResponseEntity<>(rentalData.get(), HttpStatus.OK);
		} else {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}

	@PostMapping("/rentals")
	public ResponseEntity<Rental> createRental(@RequestBody Rental rental) {
		try {
			Rental _rental = rentalRepository
					.save(new Rental(rental.getOwner(), rental.getDescription(), false));
			return new ResponseEntity<>(_rental, HttpStatus.CREATED);
		} catch (Exception e) {
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@PutMapping("/rentals/{id}")
	public ResponseEntity<Rental> updateRental(@PathVariable("id") long id, @RequestBody Rental rental) {
		Optional<Rental> rentalData = rentalRepository.findById(id);
		if (rentalData.isPresent()) {
			Rental _rental = rentalData.get();
			_rental.setOwner(rental.getOwner());
			_rental.setDescription(rental.getDescription());
			_rental.setAvailable(rental.isAvailable());
			return new ResponseEntity<>(rentalRepository.save(_rental), HttpStatus.OK);
		} else {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}

	@DeleteMapping("/rentals/{id}")
	public ResponseEntity<HttpStatus> deleteRental(@PathVariable("id") long id) {
		try {
			rentalRepository.deleteById(id);
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	// Probably don't want to be doing this, but you can use it for development
	@DeleteMapping("/rentals")
	public ResponseEntity<HttpStatus> deleteAllRentals() {
		try {
			rentalRepository.deleteAll();
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@GetMapping("/rentals/available")
	public ResponseEntity<List<Rental>> findByAvailable() {
		try {
			List<Rental> rentals = rentalRepository.findByAvailable(true);
			if (rentals.isEmpty()) {
				return new ResponseEntity<>(HttpStatus.NO_CONTENT);
			}
			return new ResponseEntity<>(rentals, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
}
