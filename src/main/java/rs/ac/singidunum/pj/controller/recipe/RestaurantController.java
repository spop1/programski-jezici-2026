package rs.ac.singidunum.pj.controller.recipe;

import lombok.RequiredArgsConstructor;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;
import rs.ac.singidunum.pj.model.recipe.RestaurantModel;
import rs.ac.singidunum.pj.service.recipe.RestaurantService;

import java.util.List;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.PutMapping;


@CrossOrigin
@RestController
@RequestMapping(path = "/api/restaurant")
@RequiredArgsConstructor
public class RestaurantController {

            private final RestaurantService service;

            @GetMapping
            public List<RestaurantModel> getRestaurants(@RequestParam(required = false) String name){
                if(name != null && !name.isBlank()){
                    return service.searchRestaurants(name);
                }
                return service.getAll();
            }

            @GetMapping(path = "/{id}")
            public ResponseEntity<RestaurantModel> getRestaurantById(@PathVariable Integer id) {
                return ResponseEntity.of(service.getById(id));
            }        
            
            @DeleteMapping(path = "/{id}")
            @ResponseStatus(code = HttpStatus.NO_CONTENT)
            public void deleteRestaurantById(@PathVariable Integer id) {
                service.deleteById(id);
            }

            @PutMapping(path = "/{id}")
            public RestaurantModel updateRestaurant(@PathVariable Integer id, @Valid @RequestBody RestaurantModel model) {
                return service.update(id, model);
            }

            @PostMapping
            @ResponseStatus(code = HttpStatus.CREATED)
            public RestaurantModel createReastaurant(@Valid @RequestBody RestaurantModel model) {
                return service.create(model);
            }

           
 }

