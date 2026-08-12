package rs.ac.singidunum.pj.model.recipe;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class RestaurantModel {

    private Integer restaurantId;

    @NotBlank(message = "Name cannot be empty.")
    @Pattern(regexp = "^[^<>]*$", message = "The use of '<' and '>' characters is not allowed.")
    private String name;

    @NotBlank(message = "Address cannot be empty.")
    @Pattern(regexp = "^[^<>]*$", message = "The use of '<' and '>' characters is not allowed.")
    private String address;

}
