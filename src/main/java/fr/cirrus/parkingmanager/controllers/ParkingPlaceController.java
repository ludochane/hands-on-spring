package fr.cirrus.parkingmanager.controllers;

import fr.cirrus.parkingmanager.models.ParkingPlace;
import fr.cirrus.parkingmanager.repositories.ParkingPlaceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/parkingPlaces")
public class ParkingPlaceController {

    private final ParkingPlaceRepository repository;

    @Autowired
    public ParkingPlaceController(ParkingPlaceRepository repository) {
        this.repository = repository;
    }

    @GetMapping
    public Iterable<ParkingPlace> all() {
        return this.repository.findAll();
    }

    @GetMapping("{numero}")
    public Optional<ParkingPlace> get(@PathVariable("numero") String numero) {
        return this.repository.findById(numero);
    }

    @PutMapping
    public ParkingPlace create(@RequestBody ParkingPlace parkingPlace) {
        return this.repository.save(parkingPlace);
    }

    @PostMapping
    public ParkingPlace update(@RequestBody ParkingPlace parkingPlace) {
        Optional<ParkingPlace> maybeParkingPlace = this.repository.findById(parkingPlace.getNumero());

        if (maybeParkingPlace.isPresent()) {
            return this.repository.save(parkingPlace);
        } else {
            throw new IllegalArgumentException(
                    String.format("Parking place with numero=%s does not exist", parkingPlace.getNumero())
            );
        }
    }

    @DeleteMapping
    public void delete(@RequestBody ParkingPlace parkingPlace) {
        this.repository.delete(parkingPlace);
    }

    @GetMapping("/available/{available}")
    public List<ParkingPlace> findByAvailable(@PathVariable("available") boolean available) {
        return this.repository.findParkingPlacesByAvailable(available);
    }
}
