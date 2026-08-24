package rs.ac.singidunum.pj.repo;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import rs.ac.singidunum.pj.entity.Restaurant;
public interface RestaurantRepository extends JpaRepository<Restaurant, Integer> {

    List<Restaurant> findAllByDeletedAtIsNull();
    Optional<Restaurant> findOneByRestaurantIdAndDeletedAtIsNull(Integer id);
    List<Restaurant> findAllByRestaurantIdInAndDeletedAtIsNull(List<Integer> ids);
    List<Restaurant> findByNameContainingIgnoreCaseAndDeletedAtIsNull(String name);

}

