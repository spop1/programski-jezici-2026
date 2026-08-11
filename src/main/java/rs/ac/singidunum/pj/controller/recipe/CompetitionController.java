package rs.ac.singidunum.pj.controller.recipe;

import java.util.List;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import rs.ac.singidunum.pj.entity.Competition;

import lombok.RequiredArgsConstructor;
import rs.ac.singidunum.pj.service.recipe.CompetitionService;

@RestController
@RequiredArgsConstructor
@RequestMapping(path = "/api/competition")
public class CompetitionController {
    private final CompetitionService competitionService;

    @GetMapping
    public List<Competition> getAll() {
        return competitionService.getAll();
    }
}
