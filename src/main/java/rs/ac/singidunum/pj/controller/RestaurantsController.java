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
import rs.ac.singidunum.pj.entity.Restaurant;
import rs.ac.singidunum.pj.repo.RestaurantsRepository;

import java.time.LocalDateTime;
import java.util.List;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.PutMapping;



@RestController
@RequestMapping(path = "/api/restaurants")
@RequiredArgsConstructor
public class RestaurantsController {

            private final RestaurantsRepository repository;

            @GetMapping
            public List<Restaurant> getRestaurants(){
                return repository.findAllByDeletedAtIsNull();
            }

            @GetMapping(path = "/{id}")
            public ResponseEntity<Restaurant> getRestaurantByid(@PathVariable Integer id) {
                return ResponseEntity.of(repository.findOneByRestaurantsIdAndDeletedAtIsNull(id));
            }            

}
