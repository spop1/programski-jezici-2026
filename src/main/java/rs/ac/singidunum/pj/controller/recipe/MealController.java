package rs.ac.singidunum.pj.controller.recipe;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import rs.ac.singidunum.pj.model.recipe.MealModel;
import rs.ac.singidunum.pj.model.recipe.MealResponseModel;
import rs.ac.singidunum.pj.service.recipe.MealService;

import java.util.List;

@RestController
@RequestMapping("/api/meals")
@RequiredArgsConstructor
public class MealController {

    private final MealService service;

    @GetMapping
    public ResponseEntity<MealResponseModel> getAllMeals() {
        return service.getAll()
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping("/{id}")
    public ResponseEntity<MealModel> getMealById(@PathVariable String id) {
        return service.getMealById(id)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping("/search")
    public ResponseEntity<List<MealModel>> searchMeals(@RequestParam(defaultValue = "Flan") String name) {
        List<MealModel> meals = service.searchMealsByName(name);
        return ResponseEntity.ok(meals);
    }
}