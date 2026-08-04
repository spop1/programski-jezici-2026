package rs.ac.singidunum.pj.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.server.ResponseStatusException;

import lombok.RequiredArgsConstructor;
import rs.ac.singidunum.pj.entity.Restaurant;
import rs.ac.singidunum.pj.repo.RestaurantRepository;

@Service
@RequiredArgsConstructor
public class RestaurantService {

    private final RestaurantRepository repository;

    public List<Restaurant> getAll() {
        return repository.findAllByDeletedAtIsNull();
    }

    public Optional<Restaurant> getById(Integer id) {
        return repository.findOneByRestaurantIdAndDeletedAtIsNull(id);
    }

    public Restaurant create(Restaurant entity) {
        // Map the incoming fields to the new entity properties
        Restaurant restaurant = new Restaurant();
        restaurant.setName(entity.getName());
        restaurant.setAddress(entity.getAddress());
        restaurant.setCreatedAt(LocalDateTime.now());
        return repository.save(restaurant);
    }
           
    public Restaurant update(Integer id, Restaurant entity) {
        // Fetch existing active restaurant from DB by ID (ignore soft-deleted)
        Restaurant restaurant = repository.findOneByRestaurantIdAndDeletedAtIsNull(id).orElseThrow();
        restaurant.setName(entity.getName());
        restaurant.setAddress(entity.getAddress());
        restaurant.setUpdatedAt(LocalDateTime.now());
        // Persist changes to the database and return the updated entithy
        return repository.save(restaurant);
    }

    // Handles HTTP DELETE request to Soft-delete a restaurant by its id
    public void deleteById(Integer id) {
        Restaurant restaurant = repository.findOneByRestaurantIdAndDeletedAtIsNull(id).orElseThrow();
        restaurant.setDeletedAt(LocalDateTime.now());
        repository.save(restaurant);
    }
}
