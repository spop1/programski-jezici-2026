package rs.ac.singidunum.pj.model.recipe;

import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@NoArgsConstructor
@Getter
@Setter
public class MealModel {

    @JsonProperty("idMeal")
    private String id;

    @JsonProperty("strMeal")
    private String name;

    @JsonProperty("strCategory")
    private String category;

    @JsonProperty("strArea")
    private String area;

    @JsonProperty("strInstructions")
    private String instructions;

    @JsonProperty("strMealThumb")
    private String thumbnail;

    @JsonProperty("strYoutube")
    private String youtubeUrl;

    @JsonProperty("strSource")
    private String sourceUrl;

    // Clean list for frontend response
    private List<IngredientModel> ingredients = new ArrayList<>();

    // Temporary maps for dynamic ingredients/measures
    private final Map<String, String> rawIngredients = new HashMap<>();
    private final Map<String, String> rawMeasures = new HashMap<>();

    // Jackson calls this for unmapped JSON fields
    @JsonAnySetter
    public void handleUnknownProperty(String key, Object value) {
        if (value == null)
            return;

        String val = value.toString().trim();
        if (val.isEmpty())
            return;

        if (key.startsWith("strIngredient")) {

            // remove "strIngredient" to leave only the index number for matching with
            // measures
            rawIngredients.put(key.replace("strIngredient", ""), val);
        } else if (key.startsWith("strMeasure")) {
            rawMeasures.put(key.replace("strMeasure", ""), val);
        }
    }

    // Matches ingredient indexes with measure indexes
    public List<IngredientModel> getIngredients() {
        if (ingredients.isEmpty() && !rawIngredients.isEmpty()) {
            for (Map.Entry<String, String> entry : rawIngredients.entrySet()) {
                String index = entry.getKey();
                String ingredientName = entry.getValue();

                // If the index exists, return its value, otherwise return ""
                String measure = rawMeasures.getOrDefault(index, "");

                ingredients.add(new IngredientModel(ingredientName, measure));
            }
        }
        return ingredients;
    }
}