package fr.cirrus.parkingmanager.controllers;

import fr.cirrus.parkingmanager.models.ParkingPlace;
import fr.cirrus.parkingmanager.services.ParkingService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("parking")
public class UserParkingPlaceController {

    private final ParkingService parkingService;

    public UserParkingPlaceController(ParkingService parkingService) {
        this.parkingService = parkingService;
    }

    @GetMapping("status")
    public List<ParkingPlace> status() {
        return this.parkingService.currentAttribution();
    }

}
