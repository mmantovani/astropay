package astropay.p2ptransferapi.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import astropay.p2ptransferapi.model.P2PTransfer;

@RepositoryRestResource(collectionResourceRel = "p2p-transfers", path = "p2p-transfers")
public interface P2PTransferRepository extends CustomP2PTransferRepository, PagingAndSortingRepository<P2PTransfer, UUID>,
        CrudRepository<P2PTransfer, UUID>, JpaSpecificationExecutor<P2PTransfer> {

}
