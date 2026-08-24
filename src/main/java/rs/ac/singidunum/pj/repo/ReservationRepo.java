package rs.ac.singidunum.pj.repo;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import rs.ac.singidunum.pj.entity.Reservation;

public interface ReservationRepo extends JpaRepository<Reservation, Integer>{
    
    List<Reservation> findByRestaurant_RestaurantId(Integer restaurantId);

    List<Reservation> findAllByDeletedAtIsNull();

    Optional<Reservation> findOneByReservationIdAndDeletedAtIsNull(Integer id);



}
