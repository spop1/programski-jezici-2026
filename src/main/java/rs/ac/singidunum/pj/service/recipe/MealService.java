package rs.ac.singidunum.pj.service.recipe;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;
import rs.ac.singidunum.pj.model.recipe.MealModel;
import rs.ac.singidunum.pj.model.recipe.MealResponseModel;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
public class MealService {

    private final RestClient client;

    public MealService(
            @Value("${meal.api.base-url:https://www.themealdb.com/api/json/v1/1}") String baseUrl) {

        this.client = RestClient.builder()
                .baseUrl(baseUrl)
                .defaultHeader("Accept", "application/json")
                .build();
    }

    public Optional<MealResponseModel> getAll() {
        try {
            MealResponseModel response = client.get()
                    .uri("/search.php?s=") // An empty 's=' returns the default recipe set
                    .retrieve() // Sends the request and retrieves the HTTP response
                    .body(MealResponseModel.class); // Deserializes the JSON into a MealResponseModel instance

            return Optional.ofNullable(response);
        } catch (Exception e) {
            System.err.println("Error in getAll: " + e.getMessage());
            return Optional.empty(); // Safely return an empty Optional instead of null
        }
    }

    public List<MealModel> searchMealsByName(String name) {
        try {
            MealResponseModel response = client.get()
                    .uri("/search.php?s={name}", name)
                    .retrieve()
                    .body(MealResponseModel.class);

            if (response != null && response.getMeals() != null) {
                return response.getMeals();
            }
        } catch (Exception e) {
            System.err.println("Error in searchMealsByName: " + e.getMessage());
        }

        return Collections.emptyList(); 
    }

    public Optional<MealModel> getMealById(String id) {
        try {
            MealResponseModel response = client.get()
                    .uri("/lookup.php?i={id}", id) 
                    .retrieve()
                    .body(MealResponseModel.class);

            if (response != null && response.getMeals() != null && !response.getMeals().isEmpty()) {
                return Optional.of(response.getMeals().get(0));
            }
        } catch (Exception e) {
            System.err.println("Error in getMealById for ID " + id + ": " + e.getMessage());
        }

        return Optional.empty(); 
    }
}