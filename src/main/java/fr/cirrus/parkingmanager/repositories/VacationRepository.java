package fr.cirrus.parkingmanager.repositories;

import fr.cirrus.parkingmanager.models.Vacation;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.util.Date;
import java.util.List;

@RepositoryRestResource
public interface VacationRepository extends PagingAndSortingRepository<Vacation, Long> {

    List<Vacation> findVacationsByStartDateBeforeAndEndDateAfter(Date now1, Date now2);

    List<Vacation> findVacationsByEndDateAfter(Date now);

}
