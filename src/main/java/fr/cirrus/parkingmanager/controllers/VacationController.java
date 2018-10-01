package fr.cirrus.parkingmanager.controllers;

import fr.cirrus.parkingmanager.models.Vacation;
import fr.cirrus.parkingmanager.repositories.VacationRepository;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("vacations/custom")
public class VacationController {

    private final VacationRepository vacationRepository;

    public VacationController(VacationRepository vacationRepository) {
        this.vacationRepository = vacationRepository;
    }

    @GetMapping("current")
    public List<Vacation> current() {
        Date now = new Date();
        return this.vacationRepository.findVacationsByEndDateAfter(now);
    }

}
