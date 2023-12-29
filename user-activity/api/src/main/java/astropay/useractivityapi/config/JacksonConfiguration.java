package astropay.useractivityapi.config;

import java.time.format.DateTimeFormatter;

import org.springframework.boot.autoconfigure.jackson.Jackson2ObjectMapperBuilderCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;

@Configuration
public class JacksonConfiguration {

    @Bean
    public Jackson2ObjectMapperBuilderCustomizer jsonCustomizer() {
        return new Jackson2ObjectMapperBuilderCustomizer() {
            @Override
            public void customize(Jackson2ObjectMapperBuilder builder) {

            	builder.propertyNamingStrategy(PropertyNamingStrategies.SNAKE_CASE);

            	// formatter
                DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
                DateTimeFormatter dateTimeFormatter =  DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

                // deserializers
                builder.deserializers(new LocalDateDeserializer(dateFormatter));
                builder.deserializers(new LocalDateTimeDeserializer(dateTimeFormatter));

                // serializers
                builder.serializers(new LocalDateSerializer(dateFormatter));
                builder.serializers(new LocalDateTimeSerializer(dateTimeFormatter));

            }
        };
    }
}
