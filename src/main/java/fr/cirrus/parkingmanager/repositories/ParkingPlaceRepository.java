package fr.cirrus.parkingmanager.repositories;

import fr.cirrus.parkingmanager.models.ParkingPlace;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ParkingPlaceRepository extends CrudRepository<ParkingPlace, String> {

    List<ParkingPlace> findParkingPlacesByAvailable(boolean available);
}
