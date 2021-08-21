package edu.drexel.trainsim.payment.models;

public class PaymentResponse {
    public enum Status {
        PAYMENT_SUCCEED (0,"Payment succeed"),
        PAYMENT_FAILED (1,"Payment failed"),
        CREDIT_CARD_VALIDATION_FAILED (2,"Credit card validation failed");

        private final int code;
        private final String message;

        Status(int code, String message) {
            this.code = code;
            this.message = message;
        }

        public int getCode() {
            return this.code;
        }

        public String getMessage() {
            return this.message;
        }
    }

    private final int code;
    private final String message;

    public PaymentResponse(Status s) {
        this.code = s.code;
        this.message = s.message;
    }

    public int getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }
}
