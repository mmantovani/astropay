curl -i -H "Content-Type:application/json" \
    -d '{
    "user_id": 113411,
    "deposit_amount": 12.99,
    "deposit_currency": "GBP",
    "status": "PENDING",
    "created_at": "2023-12-02 13:54:30",
    "expires_at": "2023-12-04 13:54:30",
    "payment_method_code": "BANK_TRANSFER"
    }' \
    http://localhost:8080/deposits
