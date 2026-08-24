package rs.ac.singidunum.pj.service.recipe;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import rs.ac.singidunum.pj.exceptions.InvalidOperationException;
import rs.ac.singidunum.pj.exceptions.ResourceNotFoundExeption;
import rs.ac.singidunum.pj.entity.Reservation;
import rs.ac.singidunum.pj.entity.Restaurant;
import rs.ac.singidunum.pj.model.recipe.ReservationModel;
import rs.ac.singidunum.pj.model.recipe.ReservationRequest;
import rs.ac.singidunum.pj.model.recipe.MealModel;
import rs.ac.singidunum.pj.model.recipe.RestaurantModel;
import rs.ac.singidunum.pj.repo.ReservationRepo;
import rs.ac.singidunum.pj.service.RestaurantService;
import java.util.HashMap;
import java.util.Optional;

@Service
public class ReservationService {
    private final ReservationRepo reservationRepo;
    private final MealService mealService;
    private final RestaurantService restaurantService;

    public ReservationService(ReservationRepo reservationRepo, MealService mealService,
            RestaurantService restaurantService) {
        this.reservationRepo = reservationRepo;
        this.mealService = mealService;
        this.restaurantService = restaurantService;
    }

    public ReservationModel getById(Integer id) {
        return reservationRepo.findOneByReservationIdAndDeletedAtIsNull(id)
                .map(entity -> toModel(entity)).orElseThrow(
                        () -> new ResourceNotFoundExeption("Reservation with ID " + id + " not found."));
    }

    public List<ReservationModel> getAll() {
        List<Reservation> reservation = reservationRepo.findAllByDeletedAtIsNull();
        List<Integer> ids = reservation.stream().filter(r -> r.getRestaurant() != null)
                .map(r -> r.getRestaurant().getRestaurantId()).toList();

        List<RestaurantModel> restaurants = restaurantService.getByIds(ids);

        Map<Integer, RestaurantModel> restaurantMap = new HashMap<>();
        for (RestaurantModel r : restaurants) {
            restaurantMap.put(r.getRestaurantId(), r);
        }

        List<String> mealsIds = reservation.stream().filter(m -> m.getRecipeId() != null)
                .map(Reservation::getRecipeId).distinct().toList();

        List<MealModel> meals = mealService.getByIds(mealsIds);

        Map<String, MealModel> mealMap = new HashMap<>();
        for (MealModel m : meals) {
            mealMap.put(m.getId(), m);
        }

        return reservation.stream()
                .map(c -> toModelWithRestaurantAndMeal(c, restaurantMap, mealMap)).toList();
    }

    public ReservationModel create(ReservationRequest request) {
        Reservation newReservation = new Reservation();

        newReservation.setTimeStart(LocalDateTime.now());
        newReservation.setRecipeId(request.getRecipeId());
        newReservation.setStatus("NOT_PAID");

        if (request.getRestaurantId() != null) {
            RestaurantModel restaurantModel = restaurantService.getById(request.getRestaurantId())
                    .orElseThrow(() -> new ResourceNotFoundExeption(
                            "Restaurant with ID " + request.getRestaurantId() + " not found."));

            Restaurant restaurantEntity = restaurantService.toEntity(restaurantModel);
            newReservation.setRestaurant(restaurantEntity);
        }

        newReservation.setCreatedAt(LocalDateTime.now().plusMinutes(30));

        Reservation savedEntity = reservationRepo.save(newReservation);

        return toModel(savedEntity);
    }

    public ReservationModel update(Integer id, ReservationRequest model) {
        Reservation existing = reservationRepo.findOneByReservationIdAndDeletedAtIsNull(id)
                .orElseThrow(() -> new ResourceNotFoundExeption("Reservation with ID " + id + " not found."));

        if (model.getTimeStart() != null) {
            existing.setTimeStart(LocalDateTime.now().plusMinutes(30));
        }

        if (model.getRecipeId() != null && !model.getRecipeId().isBlank()) {
            existing.setRecipeId(model.getRecipeId());
        }

        // Bcs PATCH updates only the specified fields
        if (model.getRestaurantId() != null) {
            RestaurantModel restaurantModel = restaurantService.getById(model.getRestaurantId())
                    .orElseThrow(() -> new ResourceNotFoundExeption(
                            "Restaurant with ID " + model.getRestaurantId() + " not found."));

            Restaurant restaurantEntity = restaurantService.toEntity(restaurantModel);

            existing.setRestaurant(restaurantEntity);
        }

        existing.setUpdatedAt(LocalDateTime.now());

        Reservation savedEntity = reservationRepo.save(existing);
        return toModel(savedEntity);
    }

    public void payById(Integer id) {
        Reservation reservation = reservationRepo.findOneByReservationIdAndDeletedAtIsNull(id)
                .orElseThrow(() -> new ResourceNotFoundExeption("Reservation with ID " + id + " not found."));

        if ("PAID".equals(reservation.getStatus()) || "TAKE_OVER".equals(reservation.getStatus())) {
            throw new InvalidOperationException("This reservation is paid already.");
        }

        reservation.setStatus("PAID");
        reservationRepo.save(reservation);
    }

    public void pickUp(Integer id) {
        Reservation reservation = reservationRepo.findById(id).orElseThrow(
                () -> new ResourceNotFoundExeption("Resource with id " + id + " not found."));

        if (!"PAID".equals(reservation.getStatus())) {
            throw new InvalidOperationException("The reservation must be paid before take over.");
        }

        reservation.setStatus("TAKE_OVER");
        reservationRepo.save(reservation);
    }

    public void deleteById(Integer id) {

        Reservation entity = reservationRepo.findOneByReservationIdAndDeletedAtIsNull(id).orElseThrow(
                () -> new ResourceNotFoundExeption("Reservation with ID " + id + " not found."));

        if (entity.getStatus().equals("PAID") || entity.getStatus().equals("TAKE_OVER"))
            throw new InvalidOperationException("RESERVATION_HAS_BEEN_PAID");

        entity.setDeletedAt(LocalDateTime.now());
        reservationRepo.save(entity);
    }

    private Reservation toEntity(ReservationModel model) {
        if (model == null)
            return null;

        Reservation entity = new Reservation();
        entity.setReservationId(model.getReservationId());
        // entity.setName(model.getName());
        entity.setRecipeId(model.getRecipeId());

        if (model.getRestaurant() != null) {
            entity.setRestaurant(restaurantService.toEntity(model.getRestaurant()));
        }

        return entity;

    }

    private ReservationModel toModel(Reservation entity) {
        if (entity == null)
            return null;

        ReservationModel model = new ReservationModel();

        model.setReservationId(entity.getReservationId());
        model.setRecipeId(entity.getRecipeId());
        model.setTimeStart(entity.getTimeStart());
        model.setStatus(entity.getStatus());
        model.setCreatedAt(entity.getCreatedAt());

        if (entity.getRestaurant() != null) {
            model.setRestaurant(restaurantService.toModel(entity.getRestaurant()));
        }
        if (entity.getRecipeId() != null && !entity.getRecipeId().isBlank()) {
            MealModel meal = mealService.getMealById(entity.getRecipeId()).orElse(null);
            model.setRecipe(meal);
        }
        return model;
    }

    // Optimized for getAll()
    private ReservationModel toModelWithRestaurantAndMeal(
            Reservation entity, Map<Integer, RestaurantModel> restaurantMap,
            Map<String, MealModel> mealMap) {

        ReservationModel model = new ReservationModel();

        model.setReservationId(entity.getReservationId());
        model.setTimeStart(entity.getTimeStart());
        model.setRecipeId(entity.getRecipeId());
        model.setStatus(entity.getStatus());
        model.setCreatedAt(entity.getCreatedAt());

        if (entity.getRestaurant() != null) {
            RestaurantModel restaurant = restaurantMap.get(entity.getRestaurant().getRestaurantId());
            model.setRestaurant(restaurant);
        }

        if (entity.getRecipeId() != null && !entity.getRecipeId().isBlank()) {
            MealModel meal = mealMap.get(entity.getRecipeId());
            model.setRecipe(meal);
        }

        return model;
    }

}
