package rs.ac.singidunum.pj.model.recipe;

import java.time.LocalDateTime;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
@Getter
@Setter
@NoArgsConstructor
public class CompetitionModel {
    
    private Integer competitionId;

    @NotBlank(message = "Recipe Id is required")
    @Pattern(regexp = "^[^<>]*$", message = "The use of '<' and '>' character is not allowed.")
    private String recipeId;
    private String name;

    private MealModel recipe;

    @NotNull(message = "Restaurant data is required")
    @Valid
    private RestaurantModel restaurant;

    @NotNull(message = "Start time is required")
    private LocalDateTime timeStart;

}
