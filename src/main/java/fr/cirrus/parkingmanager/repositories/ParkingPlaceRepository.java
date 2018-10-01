package fr.cirrus.parkingmanager.repositories;

import fr.cirrus.parkingmanager.models.ParkingPlace;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource
public interface ParkingPlaceRepository extends PagingAndSortingRepository<ParkingPlace, Long> {

}
