package fr.cirrus.parkingmanager.controllers;

import fr.cirrus.parkingmanager.models.ParkingPlace;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.List;

@RestController
public class ParkingPlaceController {

    @GetMapping("parkingPlaces/all")
    public List<ParkingPlace> all() {
        return Arrays.asList(
                new ParkingPlace("1"),
                new ParkingPlace("2")
        );
    }
}
