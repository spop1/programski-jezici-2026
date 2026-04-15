package rs.ac.singidunum.pj.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import rs.ac.singidunum.pj.entity.Cinema;
import rs.ac.singidunum.pj.repo.CinemaRepository;

import java.util.List;

@RestController
@RequestMapping(path = "/api/cinema")
@RequiredArgsConstructor

public class CinemaController {
    private final CinemaRepository repository;

    @GetMapping
    public List<Cinema> getCinemas() {
        return repository.findAll();
    }
}
