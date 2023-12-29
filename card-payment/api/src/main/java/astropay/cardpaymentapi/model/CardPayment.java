package astropay.cardpaymentapi.model;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;

@Entity
public class CardPayment {

	private @Id @GeneratedValue UUID paymentId;
	private Long cardId;
	private Long userId;
	private BigDecimal paymentAmount;
	private String paymentCurrency;
	private String status;
	private LocalDateTime createdAt = LocalDateTime.now();
	private String merchantName;
	private Long merchantId;
	private Integer mccCode;

	public UUID getPaymentId() {
		return paymentId;
	}
	public void setPaymentId(UUID id) {
		this.paymentId = id;
	}
	public Long getCardId() {
		return cardId;
	}
	public void setCardId(Long cardId) {
		this.cardId = cardId;
	}
	public Long getUserId() {
		return userId;
	}
	public void setUserId(Long userId) {
		this.userId = userId;
	}
	public BigDecimal getPaymentAmount() {
		return paymentAmount;
	}
	public void setPaymentAmount(BigDecimal paymentAmount) {
		this.paymentAmount = paymentAmount;
	}
	public String getPaymentCurrency() {
		return paymentCurrency;
	}
	public void setPaymentCurrency(String paymentCurrency) {
		this.paymentCurrency = paymentCurrency;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public LocalDateTime getCreatedAt() {
		return createdAt;
	}
	public void setCreatedAt(LocalDateTime createdAt) {
		this.createdAt = createdAt;
	}
	public String getMerchantName() {
		return merchantName;
	}
	public void setMerchantName(String merchantName) {
		this.merchantName = merchantName;
	}
	public Long getMerchantId() {
		return merchantId;
	}
	public void setMerchantId(Long merchantId) {
		this.merchantId = merchantId;
	}
	public Integer getMccCode() {
		return mccCode;
	}
	public void setMccCode(Integer mccCode) {
		this.mccCode = mccCode;
	}

}
