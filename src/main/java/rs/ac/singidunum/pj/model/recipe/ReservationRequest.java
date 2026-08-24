package rs.ac.singidunum.pj.model.recipe;

import java.time.LocalDateTime;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ReservationRequest {    
    
    private LocalDateTime timeStart;
    
    @NotNull
    private Integer restaurantId; 
    
    @NotBlank
    private String recipeId;  
}