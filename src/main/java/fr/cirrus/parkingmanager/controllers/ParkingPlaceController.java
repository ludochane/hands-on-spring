package fr.cirrus.parkingmanager.controllers;

import fr.cirrus.parkingmanager.models.ParkingPlace;
import fr.cirrus.parkingmanager.repositories.ParkingPlaceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
@RequestMapping("/parkingPlaces")
public class ParkingPlaceController {

    private final ParkingPlaceRepository repository;

    @Autowired
    public ParkingPlaceController(ParkingPlaceRepository repository) {
        this.repository = repository;
    }

    @GetMapping("")
    public Iterable<ParkingPlace> all() {
        return this.repository.findAll();
    }

    @GetMapping("{numero}")
    public Optional<ParkingPlace> get(@PathVariable("numero") String numero) {
        return this.repository.findById(numero);
    }
}
