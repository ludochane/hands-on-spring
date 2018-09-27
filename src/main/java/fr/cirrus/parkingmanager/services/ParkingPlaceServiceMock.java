package fr.cirrus.parkingmanager.services;

import fr.cirrus.parkingmanager.models.ParkingPlace;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Service
@Profile("mockexécutions ")
public class ParkingPlaceServiceMock implements ParkingPlaceService {
    @Override
    public Iterable<ParkingPlace> all() {
        return Arrays.asList(new ParkingPlace("1"));
    }

    @Override
    public Optional<ParkingPlace> findByNumero(String numero) {
        return Optional.empty();
    }

    @Override
    public ParkingPlace create(ParkingPlace parkingPlace) {
        return parkingPlace;
    }

    @Override
    public ParkingPlace update(ParkingPlace parkingPlace) {
        return parkingPlace;
    }

    @Override
    public void delete(ParkingPlace parkingPlace) {

    }

    @Override
    public List<ParkingPlace> findByAvailable(boolean available) {
        return Arrays.asList(new ParkingPlace("1"));
    }
}
