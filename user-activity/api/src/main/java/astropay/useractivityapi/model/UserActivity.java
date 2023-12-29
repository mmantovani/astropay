package astropay.useractivityapi.model;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;

@Entity
public class UserActivity {

    public enum Type {
        CARD_PAYMENT, DEPOSIT, P2P_TRANSFER
    }

	private @Id UUID activityId;

    @Column(length = 16, nullable = false, unique = false, updatable = false)
    @Enumerated(EnumType.STRING)
	private UserActivity.Type activityType;

    @Column(nullable = true, unique = false, updatable = false)
	private Long cardId;

    @Column(nullable = true, unique = false, updatable = false)
	private Long userId;

    @Column(nullable = false, unique = false, updatable = false)
	private BigDecimal amount;

    @Column(length = 3, nullable = false, unique = false, updatable = false)
	private String currency;

    @Column(length = 16, nullable = false, unique = false)
	private String status;

    @Column(nullable = false, unique = false, updatable = false)
    private LocalDateTime createdAt;

    @Column(nullable = true, unique = false, updatable = false)
    private LocalDateTime expiresAt;

    @Column(length = 32, nullable = true, unique = false, updatable = false)
	private String merchantName;

    @Column(nullable = true, unique = false, updatable = false)
    private Long merchantId;

    @Column(nullable = true, unique = false, updatable = false)
	private Integer mccCode;

    @Column(length = 16, nullable = true, unique = false, updatable = false)
    private String paymentMethodCode;

    @Column(length = 32, nullable = true, unique = false, updatable = false)
    private String senderId;

    @Column(length = 32, nullable = true, unique = false, updatable = false)
    private String recipientId;

    @Column(length = 64, nullable = true, unique = false, updatable = false)
    private String comment;


    // getters & setters

    public UUID getActivityId() {
        return activityId;
    }

    public void setActivityId(UUID activityId) {
        this.activityId = activityId;
    }

    public UserActivity.Type getActivityType() {
        return activityType;
    }

    public void setActivityType(UserActivity.Type activityType) {
        this.activityType = activityType;
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

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
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

    public LocalDateTime getExpiresAt() {
        return expiresAt;
    }

    public void setExpiresAt(LocalDateTime expiresAt) {
        this.expiresAt = expiresAt;
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

    public String getPaymentMethodCode() {
        return paymentMethodCode;
    }

    public void setPaymentMethodCode(String paymentMethodCode) {
        this.paymentMethodCode = paymentMethodCode;
    }

    public String getSenderId() {
        return senderId;
    }

    public void setSenderId(String senderId) {
        this.senderId = senderId;
    }

    public String getRecipientId() {
        return recipientId;
    }

    public void setRecipientId(String recipientId) {
        this.recipientId = recipientId;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

}
