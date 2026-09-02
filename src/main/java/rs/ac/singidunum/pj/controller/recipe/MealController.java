package rs.ac.singidunum.pj.controller.recipe;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import rs.ac.singidunum.pj.client.MealClient;
import rs.ac.singidunum.pj.model.recipe.MealFilterResponse;
import rs.ac.singidunum.pj.model.recipe.MealModel;
import rs.ac.singidunum.pj.model.recipe.MealResponseModel;

@RestController
@RequestMapping("/api/meals")
@CrossOrigin
@RequiredArgsConstructor
public class MealController {

    private final MealClient mealClient;


    @GetMapping
    public ResponseEntity<MealResponseModel> getAllMeals() {
        MealResponseModel response = mealClient.getAllMeals();
        return response != null ? ResponseEntity.ok(response) : ResponseEntity.notFound().build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<MealModel> getMealById(@PathVariable String id) {
        MealModel meal = mealClient.getMealById(id);
        return meal != null ? ResponseEntity.ok(meal) : ResponseEntity.notFound().build();
    }

    @GetMapping("/batch")
    public ResponseEntity<List<MealModel>> getByIds(@RequestParam List<String> ids) {
        return ResponseEntity.ok(mealClient.getMealsByIds(ids));
    }

    @GetMapping("/search")
    public List<MealModel> searchMeals(@RequestParam String name) {
        return mealClient.searchMeals(name);
    }

    @GetMapping("/categories")
    public ResponseEntity<List<String>> getCategories() {
        return ResponseEntity.ok(mealClient.getCategories());
    }

    @GetMapping("/category/{name}")
    public ResponseEntity<List<MealFilterResponse.MealItem>> getMealsByCategory(@PathVariable String name) {
        return ResponseEntity.ok(mealClient.getMealsByCategory(name));
    }
}
