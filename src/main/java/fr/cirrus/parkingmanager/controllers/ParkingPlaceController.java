package fr.cirrus.parkingmanager.controllers;

import fr.cirrus.parkingmanager.models.ParkingPlace;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.List;

@RestController
@RequestMapping("/parkingPlaces")
public class ParkingPlaceController {

    @GetMapping("")
    public List<ParkingPlace> all() {
        return Arrays.asList(
                new ParkingPlace("1"),
                new ParkingPlace("2")
        );
    }

    @GetMapping("{numero}")
    public ParkingPlace get(@PathVariable("numero") String numero) {
        return new ParkingPlace(numero);
    }
}
