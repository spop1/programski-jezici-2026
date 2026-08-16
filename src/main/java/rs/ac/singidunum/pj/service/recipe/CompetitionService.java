package rs.ac.singidunum.pj.service.recipe;

import java.time.LocalDateTime;
import java.util.List;
import org.springframework.stereotype.Service;

import jakarta.validation.Valid;
import rs.ac.singidunum.pj.entity.Competition;
import rs.ac.singidunum.pj.entity.Restaurant;
import rs.ac.singidunum.pj.model.recipe.CompetitionModel;
import rs.ac.singidunum.pj.model.recipe.CompetitionRequest;
import rs.ac.singidunum.pj.model.recipe.MealModel;
import rs.ac.singidunum.pj.model.recipe.RestaurantModel;
import rs.ac.singidunum.pj.repo.CompetitionRepo;
import rs.ac.singidunum.pj.service.RestaurantService;

import java.util.Optional;

@Service
public class CompetitionService {
    private final CompetitionRepo competitionRepo;
    private final MealService mealService;
    private final RestaurantService restaurantService;

    public CompetitionService(CompetitionRepo competitionRepo, MealService mealService,
            RestaurantService restaurantService) {
        this.competitionRepo = competitionRepo;
        this.mealService = mealService;
        this.restaurantService = restaurantService;
    }

    public Optional<CompetitionModel> getById(Integer id) {
        return competitionRepo.findOneByCompetitionIdAndDeletedAtIsNull(id)
                .map(entity -> toModel(entity));
    }

    public List<CompetitionModel> getAll() {
        return competitionRepo.findAllByDeletedAtIsNull()
                .stream()
                .map(this::toModel)
                .toList();
    }

    // Helper method to fetch other meal fields from an external API for a specific
    // competition
    public CompetitionModel getCompetitionDetails(Integer id) {

        Competition competition = competitionRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("Takmičenje sa ID-jem " + id + " ne postoji u bazi!"));

        if (competition.getRecipeId() != null) {

            MealModel meal = mealService.getMealById(competition.getRecipeId()).orElse(null);

            competition.setRecipe(meal);
        }

        return toModel(competition);
    }

    public CompetitionModel create(CompetitionRequest request) {
        Competition newCompetition = new Competition();

        newCompetition.setName(request.getName());
        newCompetition.setTimeStart(request.getTimeStart());
        newCompetition.setRecipeId(request.getRecipeId());

        if (request.getRestaurantId() != null) {
            RestaurantModel restaurantModel = restaurantService.getById(request.getRestaurantId())
                    .orElseThrow(() -> new RuntimeException("The Restaurant not found."));

            Restaurant restaurantEntity = restaurantService.toEntity(restaurantModel);
            newCompetition.setRestaurant(restaurantEntity);
        }

        newCompetition.setCreatedAt(LocalDateTime.now());

        Competition savedEntity = competitionRepo.save(newCompetition);

        return toModel(savedEntity);
    }

    public CompetitionModel update(Integer id, CompetitionRequest model) {
        Competition existing = competitionRepo.findOneByCompetitionIdAndDeletedAtIsNull(id)
                .orElseThrow(() -> new RuntimeException("The competition was not found."));

        if (model.getName() != null && !model.getName().isBlank()) {
            existing.setName(model.getName());
        }

        if (model.getTimeStart() != null) {
            existing.setTimeStart(model.getTimeStart());
        }

        if (model.getRecipeId() != null && !model.getRecipeId().isBlank()) {
            existing.setRecipeId(model.getRecipeId());
        }

        // Bcs PATCH updates only the specified fields
        if (model.getRestaurantId() != null) {
            RestaurantModel restaurantModel = restaurantService.getById(model.getRestaurantId())
                    .orElseThrow(() -> new RuntimeException("Restaurant not found."));

            Restaurant restaurantEntity = restaurantService.toEntity(restaurantModel);

            existing.setRestaurant(restaurantEntity);
        }

        existing.setUpdatedAt(LocalDateTime.now());

        Competition savedEntity = competitionRepo.save(existing);
        return toModel(savedEntity);
    }

    public void deleteById(Integer id) {

        Competition entity = competitionRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("Competition schedule with given id not found."));

        entity.setDeletedAt(LocalDateTime.now());

        competitionRepo.save(entity);
    }

    private Competition toEntity(CompetitionModel model) {
        if (model == null)
            return null;

        Competition entity = new Competition();
        entity.setCompetitionId(model.getCompetitionId());
        entity.setName(model.getName());
        entity.setRecipeId(model.getRecipeId());

        if (model.getRestaurant() != null) {
            entity.setRestaurant(restaurantService.toEntity(model.getRestaurant()));
        }

        return entity;

    }

    private CompetitionModel toModel(Competition entity) {
        if (entity == null)
            return null;

        CompetitionModel model = new CompetitionModel();
        model.setCompetitionId(entity.getCompetitionId());
        model.setName(entity.getName());
        model.setRecipeId(entity.getRecipeId());
        model.setTimeStart(entity.getTimeStart());

        if (entity.getRestaurant() != null) {
            model.setRestaurant(restaurantService.toModel(entity.getRestaurant()));
        }
        if (entity.getRecipeId() != null && !entity.getRecipeId().isBlank()) {
            MealModel meal = mealService.getMealById(entity.getRecipeId()).orElse(null);
            model.setRecipe(meal);
        }
        return model;
    }

}
