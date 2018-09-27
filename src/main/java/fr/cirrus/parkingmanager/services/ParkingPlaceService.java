package fr.cirrus.parkingmanager.services;

import fr.cirrus.parkingmanager.models.ParkingPlace;

import java.util.List;
import java.util.Optional;

public interface ParkingPlaceService {
    Iterable<ParkingPlace> all();

    Optional<ParkingPlace> findByNumero(String numero);

    ParkingPlace create(ParkingPlace parkingPlace);

    ParkingPlace update(ParkingPlace parkingPlace);

    void delete(ParkingPlace parkingPlace);

    List<ParkingPlace> findByAvailable(boolean available);
}
