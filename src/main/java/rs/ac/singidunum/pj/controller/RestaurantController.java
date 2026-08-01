package rs.ac.singidunum.pj.controller;

import lombok.RequiredArgsConstructor;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import rs.ac.singidunum.pj.entity.Restaurant;
import rs.ac.singidunum.pj.repo.RestaurantsRepository;

import java.time.LocalDateTime;
import java.util.List;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.PutMapping;



@RestController
@RequestMapping(path = "/api/restaurant")
@RequiredArgsConstructor
public class RestaurantController {

            private final RestaurantsRepository repository;

            @GetMapping
            public List<Restaurant> getRestaurant(){
                return repository.findAllByDeletedAtIsNull();
            }

            @GetMapping(path = "/{id}")
            public ResponseEntity<Restaurant> getRestaurantByid(@PathVariable Integer id) {
                return ResponseEntity.of(repository.findOneByRestaurantIdAndDeletedAtIsNull(id));
            }        
            
            // Handles HTTP DELETE request to Soft-delete a restaurant by its id
            @DeleteMapping(path = "/{id}")
            @ResponseStatus(code = HttpStatus.NO_CONTENT)
            public void deleteRestaurantById(@PathVariable Integer id) {
                Restaurant restaurant = repository
                        .findOneByRestaurantIdAndDeletedAtIsNull(id)
                        .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Restaurant is not found"));

                restaurant.setDeletedAt(LocalDateTime.now());
                repository.save(restaurant);
            }

            @PutMapping(path = "/{id}")
            public Restaurant updateRestaurant(@PathVariable Integer id, @RequestBody Restaurant entity) {
                // Fetch existing active restaurant from DB by ID (ignore soft-deleted)
                Restaurant restaurant = repository
                        .findOneByRestaurantIdAndDeletedAtIsNull(id)
                        .orElseThrow();

                // Update entity properties with incoming data from request body
                restaurant.setName(entity.getName());
                restaurant.setAddress(entity.getAddress());
                restaurant.setUpdatedAt(LocalDateTime.now());

                // Persist changes to the database and return the updated entithy
                return repository.save(restaurant);
            }

            @PostMapping
            public Restaurant createReastaurant(@RequestBody Restaurant entity) {
                // Map the incoming fields to the new entity properties
                Restaurant restaurant = new Restaurant();

                restaurant.setName(entity.getName());
                restaurant.setAddress(entity.getAddress());
                restaurant.setCreatedAt(LocalDateTime.now());

                return repository.save(restaurant);
            }

           
 }

