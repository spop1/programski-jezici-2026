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
import rs.ac.singidunum.pj.repo.RestaurantRepository;
import rs.ac.singidunum.pj.service.RestaurantService;

import java.time.LocalDateTime;
import java.util.List;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.PutMapping;



@RestController
@RequestMapping(path = "/api/restaurant")
@RequiredArgsConstructor
public class RestaurantController {

            private final RestaurantService service;

            @GetMapping
            public List<Restaurant> getRestaurants(){
                return service.getAll();
            }

            @GetMapping(path = "/{id}")
            public ResponseEntity<Restaurant> getRestaurantByid(@PathVariable Integer id) {
                return ResponseEntity.of(service.getById(id));
            }        
            
            @DeleteMapping(path = "/{id}")
            @ResponseStatus(code = HttpStatus.NO_CONTENT)
            public void deleteRestaurantById(@PathVariable Integer id) {
                service.deleteById(id);
            }

            @PutMapping(path = "/{id}")
            public Restaurant updateRestaurant(@PathVariable Integer id, @RequestBody Restaurant entity) {
                return service.update(id, entity);
            }

            @PostMapping
            public Restaurant createReastaurant(@RequestBody Restaurant entity) {
                return service.create(entity);
            }

           
 }

