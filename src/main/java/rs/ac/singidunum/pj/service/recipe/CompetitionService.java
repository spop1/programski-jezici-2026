package rs.ac.singidunum.pj.service.recipe;

import java.util.List;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import rs.ac.singidunum.pj.entity.Competition;
import rs.ac.singidunum.pj.repo.CompetitionRepo;

@Service
@RequiredArgsConstructor
public class CompetitionService {
    private final CompetitionRepo repository;
    private final MealService recipeService;

    public List<Competition> getAll() {
        List<Competition> competitionSchedules = repository.findAllByDeletedAtIsNull();

        for (Competition c: competitionSchedules) {
            c.setRecipe(recipeService.getMealById(c.getRecipeId()).orElse(null));
        }

        return competitionSchedules;
    }
}
