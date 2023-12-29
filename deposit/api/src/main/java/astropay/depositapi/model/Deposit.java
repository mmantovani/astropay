package astropay.depositapi.model;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;

@Entity
public class Deposit {

	private @Id @GeneratedValue UUID depositId;
	private Long userId;
	private BigDecimal depositAmount;
	private String depositCurrency;
	private String status;
	private LocalDateTime createdAt;
	private LocalDateTime expiresdAt;
	private String paymentMethodCode;

	public UUID getDepositId() {
		return depositId;
	}
	public void setDepositId(UUID depositId) {
		this.depositId = depositId;
	}
	public Long getUserId() {
		return userId;
	}
	public void setUserId(Long userId) {
		this.userId = userId;
	}
	public BigDecimal getDepositAmount() {
		return depositAmount;
	}
	public void setDepositAmount(BigDecimal depositAmount) {
		this.depositAmount = depositAmount;
	}
	public String getDepositCurrency() {
		return depositCurrency;
	}
	public void setDepositCurrency(String depositCurrency) {
		this.depositCurrency = depositCurrency;
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
	public LocalDateTime getExpiresdAt() {
		return expiresdAt;
	}
	public void setExpiresdAt(LocalDateTime expiresdAt) {
		this.expiresdAt = expiresdAt;
	}
	public String getPaymentMethodCode() {
		return paymentMethodCode;
	}
	public void setPaymentMethodCode(String paymentMethodCode) {
		this.paymentMethodCode = paymentMethodCode;
	}
}
