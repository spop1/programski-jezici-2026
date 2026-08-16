package rs.ac.singidunum.pj.repo;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import rs.ac.singidunum.pj.entity.Competition;

public interface CompetitionRepo extends JpaRepository<Competition, Integer>{
    
    List<Competition> findByRestaurant_RestaurantId(Integer restaurantId);

    List<Competition> findAllByDeletedAtIsNull();

    Optional<Competition> findOneByCompetitionIdAndDeletedAtIsNull(Integer id);
}
