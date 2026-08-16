package rs.ac.singidunum.pj.model.recipe;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class MealCategoryResponse {
    
    private List<CategoryItem> meals;

    @Getter
    @Setter
    @NoArgsConstructor
    public static class CategoryItem {
        private String strCategory;
    }
}
