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
import rs.ac.singidunum.pj.model.recipe.CompetitionModel;
import rs.ac.singidunum.pj.model.recipe.CompetitionRequest;
import rs.ac.singidunum.pj.service.recipe.CompetitionService;

@RestController
@RequestMapping(path = "/api/competition")
@CrossOrigin(origins = "*")
public class CompetitionController {

    private final CompetitionService competitionService;

    public CompetitionController(CompetitionService competitionService) {
        this.competitionService = competitionService;
    }

    @GetMapping
    public List<CompetitionModel> getCompetition() {
        return competitionService.getAll();
    }

    @GetMapping(path = "/{id}")
    public ResponseEntity<CompetitionModel> getCompetitiontById(@PathVariable Integer id) {
       
       CompetitionModel completedPacked = competitionService.getCompetitionDetails(id);

        return ResponseEntity.ok(completedPacked);
    }

    @DeleteMapping(path = "/{id}")
            @ResponseStatus(code = HttpStatus.NO_CONTENT)
            public void deleteCompetitiontById(@PathVariable Integer id) {
                competitionService.deleteById(id);
            }

            @PutMapping(path = "/{id}")
            public CompetitionModel updateCompetition(@PathVariable Integer id, @Valid @RequestBody CompetitionRequest model) {
                return competitionService.update(id, model);
            }

            @PostMapping
            @ResponseStatus(code = HttpStatus.CREATED)
            public CompetitionModel createCompetition(@Valid @RequestBody CompetitionRequest model) {
                return competitionService.create(model);
            }

}
