package astropay.cardpaymentapi.service;

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

import astropay.cardpaymentapi.model.CardPayment;

@Service
public class ReplicationService extends AbstractRepositoryEventListener<CardPayment> {

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
    public void onAfterCreate(CardPayment entity) {

        logger.debug("Replicating card payment with id {}", entity.getPaymentId());
        Payload payload = createPayload(entity);
        ResponseEntity<Payload> result = restTemplate.postForEntity(URI, payload, Payload.class);
        if (result.getStatusCode() != HttpStatus.CREATED) {
            logger.error("Call to " + URI + " returned code " + result.getStatusCode());
        }
    }


    public Payload createPayload(CardPayment entity) {
        Payload payload = new Payload(
                entity.getPaymentId().toString(),
                "CARD_PAYMENT",
                entity.getCardId(),
                entity.getUserId(),
                entity.getPaymentAmount(),
                entity.getPaymentCurrency(),
                entity.getStatus(),
                entity.getCreatedAt(),
                entity.getMerchantName(),
                entity.getMerchantId(),
                entity.getMccCode()
                );

        return payload;
    }


    /*
     * A payload to represent User Activity properties relevant to a Card Payment
     */
    public record Payload(
            String activityId,
            String activityType,
            Long cardId,
            Long userId,
            BigDecimal amount,
            String currency,
            String status,
            LocalDateTime createdAt,
            String merchantName,
            Long merchantId,
            Integer mccCode) {
    };
}
