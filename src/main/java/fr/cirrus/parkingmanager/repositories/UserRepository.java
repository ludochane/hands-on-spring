package fr.cirrus.parkingmanager.repositories;

import fr.cirrus.parkingmanager.models.User;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource
public interface UserRepository extends PagingAndSortingRepository<User, Long> {

}
