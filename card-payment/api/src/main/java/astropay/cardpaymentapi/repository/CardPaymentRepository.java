package astropay.cardpaymentapi.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import astropay.cardpaymentapi.model.CardPayment;

@RepositoryRestResource(collectionResourceRel = "card-payments", path = "card-payments")
public interface CardPaymentRepository extends CustomCardPaymentRepository, PagingAndSortingRepository<CardPayment, UUID>,
        CrudRepository<CardPayment, UUID>, JpaSpecificationExecutor<CardPayment> {

}
