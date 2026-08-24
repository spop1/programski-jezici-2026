package rs.ac.singidunum.pj.controller.recipe;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;
import rs.ac.singidunum.pj.model.recipe.ReservationModel;
import rs.ac.singidunum.pj.model.recipe.ReservationRequest;
import rs.ac.singidunum.pj.service.recipe.ReservationService;

@RestController
@RequestMapping(path = "/api/reservations")
@CrossOrigin(origins = "*")
public class ReservationController {

    private final ReservationService reservationService;

    public ReservationController(ReservationService reservationService) {
        this.reservationService = reservationService;
    }

    @GetMapping
    public List<ReservationModel> getReservation() {
        return reservationService.getAll();
    }

    @GetMapping(path = "/{id}")
    public ResponseEntity<ReservationModel> getReservationById(@PathVariable Integer id) {

        ReservationModel completedPacked = reservationService.getById(id);

        return ResponseEntity.ok(completedPacked);
    }

    @DeleteMapping(path = "/{id}")
    @ResponseStatus(code = HttpStatus.NO_CONTENT)
    public void deleteReservationById(@PathVariable Integer id) {
        reservationService.deleteById(id);
    }

    @PostMapping
    @ResponseStatus(code = HttpStatus.CREATED)
    public ReservationModel createReservation(@Valid @RequestBody ReservationRequest model) {
        return reservationService.create(model);
    }

    @PutMapping(path = "/{id}")
    public ReservationModel updateReservation(@PathVariable Integer id, @Valid @RequestBody ReservationRequest model) {
        return reservationService.update(id, model);
    }

    @PutMapping(path = "/{id}/pay")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void payReservation(@PathVariable Integer id) {
        reservationService.payById(id);
    }

    @PutMapping(path = "/{id}/pickup")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void pickupReservation(@PathVariable Integer id) {
        reservationService.pickUp(id);
    }
}
