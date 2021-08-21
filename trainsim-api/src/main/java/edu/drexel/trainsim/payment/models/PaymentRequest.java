package edu.drexel.trainsim.payment.models;

public class PaymentRequest {
    private double paymentAmount;
    private CreditCardInfo creditCardInfo;

    public double getPaymentAmount() {
        return paymentAmount;
    }

    public CreditCardInfo getCreditCardInfo() {
        return creditCardInfo;
    }

    public PaymentRequest(double paymentAmount, CreditCardInfo creditCardInfo) {
        this.paymentAmount = paymentAmount;
        this.creditCardInfo = creditCardInfo;
    }
}
