package rs.ac.singidunum.pj.model.recipe;

import java.util.List;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class MealResponseModel {
    private List<MealModel> meals;
    
}
