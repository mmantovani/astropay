package astropay.useractivityapi.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import astropay.useractivityapi.model.UserActivity;

@RepositoryRestResource(collectionResourceRel = "user-activities", path = "user-activities")
public interface UserActivityRepository extends CustomUserActivityRepository, PagingAndSortingRepository<UserActivity, UUID>,
        CrudRepository<UserActivity, UUID>, JpaSpecificationExecutor<UserActivity> {

}
