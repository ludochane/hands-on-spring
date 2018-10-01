package fr.cirrus.parkingmanager.services;

import fr.cirrus.parkingmanager.models.ParkingPlace;
import fr.cirrus.parkingmanager.models.User;
import fr.cirrus.parkingmanager.models.Vacation;
import fr.cirrus.parkingmanager.repositories.ParkingPlaceRepository;
import fr.cirrus.parkingmanager.repositories.UserRepository;
import fr.cirrus.parkingmanager.repositories.VacationRepository;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
public class ParkingService {

    private final ParkingPlaceRepository parkingPlaceRepository;
    private final VacationRepository vacationRepository;
    private final UserRepository userRepository;

    public ParkingService(ParkingPlaceRepository parkingPlaceRepository,
                          VacationRepository vacationRepository,
                          UserRepository userRepository) {
        this.parkingPlaceRepository = parkingPlaceRepository;
        this.vacationRepository = vacationRepository;
        this.userRepository = userRepository;
    }

    public List<ParkingPlace> currentAttribution() {
        Iterable<ParkingPlace> parkingPlaces = this.parkingPlaceRepository.findAll();
        List<User> usersInVacation = usersInVacation();
        List<ParkingPlace> freeParkingPlaces = freeParkingPlaces(parkingPlaces, usersInVacation);
        List<User> usersWithoutAParkingPlace = usersWithoutAParkingPlace(parkingPlaces);

        List<ParkingPlace> occupiedParkingPlaces = occupiedParkingPlaces(parkingPlaces, usersInVacation);
        List<ParkingPlace> updatedFreeParkingPlaces = assignFreeParkingPlaces(freeParkingPlaces, usersWithoutAParkingPlace);
        ArrayList<ParkingPlace> allParkingPlaces = new ArrayList<>();
        allParkingPlaces.addAll(occupiedParkingPlaces);
        allParkingPlaces.addAll(updatedFreeParkingPlaces);

        return allParkingPlaces;
    }

    private List<ParkingPlace> assignFreeParkingPlaces(List<ParkingPlace> freeParkingPlaces, List<User> usersWithoutAParkingPlace) {
        AtomicInteger nextUserIndex = new AtomicInteger();
        int numberOfUsersWithoutParking = usersWithoutAParkingPlace.size();
        return freeParkingPlaces.stream()
                .map(freeParkingPlace -> {
                    if (nextUserIndex.intValue() < numberOfUsersWithoutParking - 1) {
                        freeParkingPlace.setUser(usersWithoutAParkingPlace.get(nextUserIndex.getAndIncrement()));
                    }
                    return freeParkingPlace;
                })
                .collect(Collectors.toList());
    }

    private List<User> usersWithoutAParkingPlace(Iterable<ParkingPlace> parkingPlaces) {
        Iterable<User> usersByPriority = this.userRepository.findAll(Sort.by(Sort.Order.asc("priority")));
        List<User> usersWithAParkingPlace = usersWithAParkingPlace(parkingPlaces);

        return StreamSupport.stream(usersByPriority.spliterator(), false)
                .filter(user -> !usersWithAParkingPlace.contains(user))
                .collect(Collectors.toList());
    }

    private List<User> usersWithAParkingPlace(Iterable<ParkingPlace> parkingPlaces) {
        return StreamSupport.stream(parkingPlaces.spliterator(), false)
                .map(parkingPlace -> parkingPlace.getUser())
                .collect(Collectors.toList());
    }

    private List<ParkingPlace> freeParkingPlaces(Iterable<ParkingPlace> parkingPlaces, List<User> usersInVacation) {
        return StreamSupport.stream(parkingPlaces.spliterator(), false)
                .filter(parkingPlace -> usersInVacation.contains(parkingPlace.getUser()))
                .collect(Collectors.toList());
    }

    private List<ParkingPlace> occupiedParkingPlaces(Iterable<ParkingPlace> parkingPlaces, List<User> usersInVacation) {
        return StreamSupport.stream(parkingPlaces.spliterator(), false)
                .filter(parkingPlace -> !usersInVacation.contains(parkingPlace.getUser()))
                .collect(Collectors.toList());
    }

    private List<User> usersInVacation() {
        Date now = new Date();
        List<Vacation> vacations = this.vacationRepository.findVacationsByStartDateBeforeAndEndDateAfter(now, now);
        return vacations.stream()
                .map(Vacation::getUser)
                .collect(Collectors.toList());
    }

}
