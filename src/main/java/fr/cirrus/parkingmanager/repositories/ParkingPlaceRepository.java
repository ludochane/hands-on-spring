package fr.cirrus.parkingmanager.repositories;

import fr.cirrus.parkingmanager.models.ParkingPlace;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ParkingPlaceRepository extends CrudRepository<ParkingPlace, String> {

}
