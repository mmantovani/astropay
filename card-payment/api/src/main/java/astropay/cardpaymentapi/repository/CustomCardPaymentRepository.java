package astropay.cardpaymentapi.repository;

import java.time.LocalDateTime;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RestResource;
import org.springframework.format.annotation.DateTimeFormat;

import astropay.cardpaymentapi.model.CardPayment;

public interface CustomCardPaymentRepository {

	@RestResource(path = "q")
	public Page<CardPayment> findByUserIdAndStatusAndFromAndTo(
	        @Param("user_id") Long userId,
	        @Param("status") String status,
	        @Param("from") @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") LocalDateTime from,
	        @Param("to") @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") LocalDateTime to,
	        Pageable pageable, Sort sort);
}
