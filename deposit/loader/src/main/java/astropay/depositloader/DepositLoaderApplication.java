package astropay.depositloader;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.databind.ObjectMapper;

@SpringBootApplication
public class DepositLoaderApplication implements CommandLineRunner {

    private static final int DEFAULT_COUNT = 10;
    private static final String URI = "http://localhost:8082/deposits";
    private static final int MAX_USERS = 1000;
    private static final String[] currencies = { "USD", "EUR", "GBP", "BRL" };
    private static final String[] statuses = { "PENDING", "COMPLETED", "CANCELED" };
    private static final String[] paymentMethods = { "BANK_TRANSFER", "BOLETO" };

    @Autowired
    ObjectMapper objectMapper;


    public static void main(String[] args) {
        SpringApplication.run(DepositLoaderApplication.class, args);
    }


	@Override
    public void run(String... args) {

	    int count = args.length < 1 ? DEFAULT_COUNT : Integer.valueOf(args[0]);
        System.out.println("Creating " + count + " objects...");
        load(count);
        System.out.println("Done!");
    }


    public void load(int count) {

        RestTemplate restTemplate = new RestTemplateBuilder()
                .additionalMessageConverters(new MappingJackson2HttpMessageConverter(objectMapper))
                .build();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        for (int i = 0; i < count; i++) {
            if ((i + 1) % 1000 == 0) {
                System.out.println("Loading " + (i + 1));
            }
            Payload payload = createRandomPayload();
            ResponseEntity<Payload> result = restTemplate.postForEntity(URI, payload, Payload.class);
            if (result.getStatusCode() != HttpStatus.CREATED) {
                System.out.println("ERROR: call to POST returned " + result.getStatusCode());
                System.exit(-1);
            }
        }
    }


    public static Payload createRandomPayload() {
        Payload payload = new Payload(
                null,
                Long.valueOf(getRandomNumber(1,  MAX_USERS)),
                new BigDecimal(getRandomNumber(1, 1000)),
                currencies[getRandomNumber(0, currencies.length - 1)],
                statuses[getRandomNumber(0, statuses.length - 1)],
                LocalDateTime.now(),
                LocalDateTime.now().plusHours(12),
                paymentMethods[getRandomNumber(0, paymentMethods.length - 1)]);
        return payload;
    }


    public static int getRandomNumber(int min, int max) {
        return (int) ((Math.random() * (max + 1 - min)) + min);
    }


    /*
     * The payload of Deposit API
     */
    public record Payload(
            String depositId,
            Long userId,
            BigDecimal depositAmount,
            String depositCurrency,
            String status,
            LocalDateTime createdAt,
            LocalDateTime expiresAt,
            String paymentMethodCode) {
    };

}
