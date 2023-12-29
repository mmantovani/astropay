package astropay.p2ptransferapi.model;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;

@Entity
public class P2PTransfer {

	private @Id @GeneratedValue UUID transferId;
	private Long senderId;
	private Long recipientId;
	private BigDecimal transferAmount;
	private String transferCurrency;
	private String status;
	private String comment;
	private LocalDateTime createdAt;

    public UUID getTransferId() {
        return transferId;
    }
    public void setTransferId(UUID transferId) {
        this.transferId = transferId;
    }
    public Long getSenderId() {
        return senderId;
    }
    public void setSenderId(Long senderId) {
        this.senderId = senderId;
    }
    public Long getRecipientId() {
        return recipientId;
    }
    public void setRecipientId(Long recipientId) {
        this.recipientId = recipientId;
    }
    public BigDecimal getTransferAmount() {
        return transferAmount;
    }
    public void setTransferAmount(BigDecimal transferAmount) {
        this.transferAmount = transferAmount;
    }
    public String getTransferCurrency() {
        return transferCurrency;
    }
    public void setTransferCurrency(String transferCurrency) {
        this.transferCurrency = transferCurrency;
    }
    public String getStatus() {
        return status;
    }
    public void setStatus(String status) {
        this.status = status;
    }
    public String getComment() {
        return comment;
    }
    public void setComment(String comment) {
        this.comment = comment;
    }
    public LocalDateTime getCreatedAt() {
        return createdAt;
    }
    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }
}
