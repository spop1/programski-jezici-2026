package rs.ac.singidunum.pj.model.recipe;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class MealFilterResponse {
    
    private List<MealItem> meals;

    @Getter
    @Setter
    @NoArgsConstructor
    public static class MealItem {

    }
}
