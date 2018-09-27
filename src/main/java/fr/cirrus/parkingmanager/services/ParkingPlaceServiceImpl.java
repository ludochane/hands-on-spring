package fr.cirrus.parkingmanager.services;

import fr.cirrus.parkingmanager.models.ParkingPlace;
import fr.cirrus.parkingmanager.repositories.ParkingPlaceRepository;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@Profile("default")
public class ParkingPlaceServiceImpl implements ParkingPlaceService {

    private final ParkingPlaceRepository repository;

    public ParkingPlaceServiceImpl(ParkingPlaceRepository repository) {
        this.repository = repository;
    }

    @Override
    public Iterable<ParkingPlace> all() {
        return this.repository.findAll();
    }

    @Override
    public Optional<ParkingPlace> findByNumero(String numero) {
        return this.repository.findById(numero);
    }

    @Override
    public ParkingPlace create(ParkingPlace parkingPlace) {
        return this.repository.save(parkingPlace);
    }

    @Override
    public ParkingPlace update(ParkingPlace parkingPlace) {
        Optional<ParkingPlace> maybeParkingPlace = this.repository.findById(parkingPlace.getNumero());

        if (maybeParkingPlace.isPresent()) {
            return this.repository.save(parkingPlace);
        } else {
            throw new IllegalArgumentException(
                    String.format("Parking place with numero=%s does not exist", parkingPlace.getNumero())
            );
        }
    }

    @Override
    public void delete(ParkingPlace parkingPlace) {
        this.repository.delete(parkingPlace);
    }

    @Override
    public List<ParkingPlace> findByAvailable(boolean available) {
        return this.repository.findParkingPlacesByAvailable(available);
    }
}
