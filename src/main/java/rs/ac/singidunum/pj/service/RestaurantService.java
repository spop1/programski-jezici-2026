package rs.ac.singidunum.pj.service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;
import lombok.RequiredArgsConstructor;
import rs.ac.singidunum.pj.entity.Restaurant;
import rs.ac.singidunum.pj.model.recipe.RestaurantModel;
import rs.ac.singidunum.pj.repo.RestaurantRepository;

@Service
@RequiredArgsConstructor
public class RestaurantService {

    private final RestaurantRepository repository;

    public Optional<RestaurantModel> getById(Integer id) {
        return repository.findOneByRestaurantIdAndDeletedAtIsNull(id)
                .map(entity -> toModel(entity));
    }

     public List<RestaurantModel> getAll() {
        return repository.findAllByDeletedAtIsNull()
                .stream() 
                .map(this::toModel)
                .toList();
    }

    public RestaurantModel create(RestaurantModel model) {
        Restaurant entity = toEntity(model);

        entity.setCreatedAt(LocalDateTime.now());
        Restaurant savedEntity = repository.save(entity);

        return toModel(savedEntity);
    }

    public RestaurantModel update(Integer id, RestaurantModel model) {
        Restaurant existing = repository.findOneByRestaurantIdAndDeletedAtIsNull(id)
                .orElseThrow(() -> new RuntimeException("The restaurant was not found."));

        existing.setAddress(model.getAddress());
        existing.setName(model.getName());
        existing.setUpdatedAt(LocalDateTime.now());

        Restaurant savedEntity = repository.save(existing);
        return toModel(savedEntity);
    }

    // Handles HTTP DELETE request to Soft-delete a restaurant by its id
    public void deleteById(Integer id) {
        Restaurant restaurant = repository.findOneByRestaurantIdAndDeletedAtIsNull(id).orElseThrow();
        restaurant.setDeletedAt(LocalDateTime.now());
        repository.save(restaurant);
    }

    // Mapping Restaurant to RestaurantModel
    public RestaurantModel toModel(Restaurant entity) {
        if (entity == null)
            return null;

        RestaurantModel model = new RestaurantModel();
        model.setRestaurantId(entity.getRestaurantId());
        model.setAddress(entity.getAddress());
        model.setName(entity.getName());

        return model;
    }

    // Mapping RestaurantModel to Restaurant
    public Restaurant toEntity(RestaurantModel model) {
        if (model == null)
            return null;

        Restaurant restaurant = new Restaurant();
        restaurant.setRestaurantId(model.getRestaurantId());
        restaurant.setAddress(model.getAddress());
        restaurant.setName(model.getName());

        return restaurant;
    }

    public List<RestaurantModel> getByIds(List<Integer> ids) {
        if (ids == null || ids.isEmpty()) {
            return new ArrayList<>();
        }

        return repository.findAllByRestaurantIdInAndDeletedAtIsNull(ids)
        .stream().map(this::toModel).toList();
    }

}
