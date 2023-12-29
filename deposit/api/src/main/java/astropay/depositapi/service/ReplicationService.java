package astropay.depositapi.service;

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

import astropay.depositapi.model.Deposit;

@Service
public class ReplicationService extends AbstractRepositoryEventListener<Deposit> {

    private static final Logger logger = LoggerFactory.getLogger(ReplicationService.class);

    private static final String URI = "http://localhost:8080/user-activities";

    private final RestTemplate restTemplate;


    public ReplicationService(ObjectMapper objectMapper) {
        restTemplate = new RestTemplateBuilder()
                .additionalMessageConverters(new MappingJackson2HttpMessageConverter(objectMapper))
                .build();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
    }


    @Override
    public void onAfterCreate(Deposit entity) {

        logger.debug("Replicating deposit with id {}", entity.getDepositId());
        Payload payload = createPayload(entity);
        ResponseEntity<Payload> result = restTemplate.postForEntity(URI, payload, Payload.class);
        if (result.getStatusCode() != HttpStatus.CREATED) {
            logger.error("Call to " + URI + " returned code " + result.getStatusCode());
        }
    }


    public Payload createPayload(Deposit entity) {
        Payload payload = new Payload(
                entity.getDepositId().toString(),
                "DEPOSIT",
                entity.getUserId(),
                entity.getDepositAmount(),
                entity.getDepositCurrency(),
                entity.getStatus(),
                entity.getCreatedAt(),
                entity.getExpiresdAt(),
                entity.getPaymentMethodCode()
                );

        return payload;
    }


    /*
     * A payload to represent User Activity properties relevant to a Deposit
     */
    public record Payload(
            String activityId,
            String activityType,
            Long userId,
            BigDecimal amount,
            String currency,
            String status,
            LocalDateTime createdAt,
            LocalDateTime expiresAt,
            String paymentMethodCode) {
    };
}
