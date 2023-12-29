package astropay.depositapi.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import astropay.depositapi.model.Deposit;

@RepositoryRestResource(collectionResourceRel = "deposit", path = "deposits")
public interface DepositRepository extends CustomDepositRepository, PagingAndSortingRepository<Deposit, UUID>,
        CrudRepository<Deposit, UUID>, JpaSpecificationExecutor<Deposit> {

}
