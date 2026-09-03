package rs.ac.singidunum.pj.client;

import java.util.Collections;
import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.retry.annotation.Retry;
import rs.ac.singidunum.pj.model.recipe.MealFilterResponse;
import rs.ac.singidunum.pj.model.recipe.MealModel;
import rs.ac.singidunum.pj.model.recipe.MealResponseModel; 

@FeignClient(name = "meal-service", path = "/api/meals")
public interface MealClient {

    @Retry(name = "mealService", fallbackMethod = "getAllMealsFallback")
    @CircuitBreaker(name = "mealService", fallbackMethod = "getAllMealsFallback")
    @GetMapping
    MealResponseModel getAllMeals();

    default MealResponseModel getAllMealsFallback(Throwable ex) {
        System.err.println("Upozorenje: Meal mikroservis je pao pri dohvatanju svih jela! Greska: " + ex.getMessage());
        // Return an empty object so the frontend doesn't receive a 500 error.
        return new MealResponseModel(); 
    }

    @Retry(name = "mealService", fallbackMethod = "getMealFallback")
    @CircuitBreaker(name = "mealService", fallbackMethod = "getMealFallback") 
    @GetMapping("/{id}")
    MealModel getMealById(@PathVariable("id") String id);

    // Return an empty object to prevent the frontend from receiving a 500 Internal Server Error
    default MealModel getMealFallback(String id, Throwable ex) {
        System.err.println("Upozorenje: Meal mikroservis je pao! Vraćam privremene podatke. Greka: " + ex.getMessage());
        
        return new MealModel();
    }

   @CircuitBreaker(name = "mealService", fallbackMethod = "getMealsByIdsFallback")
    @GetMapping("/batch")
    List<MealModel> getMealsByIds(@RequestParam("ids") List<String> ids);

    default List<MealModel> getMealsByIdsFallback(List<String> ids, Throwable ex) {
        return Collections.emptyList(); 
    }

    @CircuitBreaker(name = "mealService", fallbackMethod = "searchMealsFallback")
    @GetMapping("/search")
    List<MealModel> searchMeals(@RequestParam("name") String name);

    default List<MealModel> searchMealsFallback(String name, Throwable ex) {
        return Collections.emptyList(); 
    }

    @GetMapping("/categories")
    List<String> getCategories();

    @GetMapping("/category/{name}")
    List<MealFilterResponse.MealItem> getMealsByCategory(@PathVariable("name") String name);
}