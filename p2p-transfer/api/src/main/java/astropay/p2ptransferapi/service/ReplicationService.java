package astropay.p2ptransferapi.service;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.data.rest.core.event.AbstractRepositoryEventListener;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.databind.ObjectMapper;

import astropay.p2ptransferapi.model.P2PTransfer;

@Service
public class ReplicationService extends AbstractRepositoryEventListener<P2PTransfer> {

    private static final Logger logger = LoggerFactory.getLogger(ReplicationService.class);

    private static final String URI = "http://localhost:8084/user-activities";

    private final RestTemplate restTemplate;


    public ReplicationService(ObjectMapper objectMapper) {
        restTemplate = new RestTemplateBuilder()
                .additionalMessageConverters(new MappingJackson2HttpMessageConverter(objectMapper))
                .build();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
    }


    @Override
    public void onAfterCreate(P2PTransfer entity) {

        logger.debug("Replicating p2p transfer with id {}", entity.getTransferId());
        Payload payload = createPayload(entity);
        ResponseEntity<Payload> result = restTemplate.postForEntity(URI, payload, Payload.class);
        if (result.getStatusCode() != HttpStatus.CREATED) {
            logger.error("Call to " + URI + " returned code " + result.getStatusCode());
        }
    }


    public Payload createPayload(P2PTransfer entity) {
        Payload payload = new Payload(
                entity.getTransferId().toString(),
                "P2P_TRANSFER",
                entity.getSenderId(),
                entity.getTransferAmount(),
                entity.getTransferCurrency(),
                entity.getStatus(),
                entity.getCreatedAt(),
                entity.getRecipientId(),
                entity.getComment()
                );

        return payload;
    }


    /*
     * A payload to represent User Activity properties relevant to a P2P Transfer
     */
    public record Payload(
            String activityId,
            String activityType,
            Long userId,
            BigDecimal amount,
            String currency,
            String status,
            LocalDateTime createdAt,
            Long recipientId,
            String comment) {
    };
}
