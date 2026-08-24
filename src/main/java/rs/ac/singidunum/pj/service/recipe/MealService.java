package rs.ac.singidunum.pj.service.recipe;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

import org.springframework.cache.annotation.Cacheable;
import rs.ac.singidunum.pj.model.recipe.MealCategoryResponse;
import rs.ac.singidunum.pj.model.recipe.MealFilterResponse;
import rs.ac.singidunum.pj.model.recipe.MealModel;
import rs.ac.singidunum.pj.model.recipe.MealResponseModel;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class MealService {

    private final RestClient client;

    public MealService(
            @Value("${meal.api.base-url:https://www.themealdb.com/api/json/v1/1}") String baseUrl) {

        this.client = RestClient.builder()
                .baseUrl(baseUrl)
                .defaultHeader("User-Agent", "Mozilla/5.0")
                .build();
    }

    @Cacheable("allMeals")
    public Optional<MealResponseModel> getAll() {
        try {
            MealResponseModel response = client.get()
                    .uri("/search.php?s=") 
                    .retrieve() 
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
        System.out.println(">>> I'm trying to grap recipe for ID: '" + id + "'");
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
            e.printStackTrace();
        }

        return Optional.empty(); 
    }

    // Call the parallel stream for getMealById()
    public List<MealModel> getByIds(List<String> ids) {
   
        return ids.parallelStream().map(this::getMealById)
                .filter(Optional::isPresent).map(Optional::get).toList();
    }

     public List<String> getAllCategories() {
        MealCategoryResponse response = client.get()
        .uri("/list.php?c=list")
        .retrieve()
        .body(MealCategoryResponse.class);

        if (response != null && response.getMeals() != null) {
            return response.getMeals().stream()
            .map(MealCategoryResponse.CategoryItem::getStrCategory)
            .collect(Collectors.toList());
        }
        return Collections.emptyList();
    }

    public List<MealFilterResponse.MealItem> getMealsByCategory(String categoryName) {
        MealFilterResponse response = client.get()
        .uri("/filter.php?c={c}", categoryName)
        .retrieve()
        .body(MealFilterResponse.class);

        if (response != null && response.getMeals() != null) {
            return response.getMeals();
        }
        return Collections.emptyList();
    }

}