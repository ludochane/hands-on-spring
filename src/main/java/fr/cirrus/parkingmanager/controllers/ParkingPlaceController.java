package fr.cirrus.parkingmanager.controllers;

import fr.cirrus.parkingmanager.models.ParkingPlace;
import fr.cirrus.parkingmanager.services.ParkingPlaceService;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/parkingPlaces")
public class ParkingPlaceController {

    private final ParkingPlaceService service;

    public ParkingPlaceController(ParkingPlaceService service) {
        this.service = service;
    }

    @GetMapping
    public Iterable<ParkingPlace> all() {
        return this.service.all();
    }

    @GetMapping("{numero}")
    public Optional<ParkingPlace> get(@PathVariable("numero") String numero) {
        return this.service.findByNumero(numero);
    }

    @PutMapping
    public ParkingPlace create(@RequestBody ParkingPlace parkingPlace) {
        return this.service.create(parkingPlace);
    }

    @PostMapping
    public ParkingPlace update(@RequestBody ParkingPlace parkingPlace) {
        return this.service.update(parkingPlace);
    }

    @DeleteMapping
    public void delete(@RequestBody ParkingPlace parkingPlace) {
        this.service.delete(parkingPlace);
    }

    @GetMapping("/available/{available}")
    public List<ParkingPlace> findByAvailable(@PathVariable("available") boolean available) {
        return this.service.findByAvailable(available);
    }
}
