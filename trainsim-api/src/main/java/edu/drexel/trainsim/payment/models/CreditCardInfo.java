package edu.drexel.trainsim.payment.models;

public class CreditCardInfo
{
    private String cardNumber;
    private String cvv;

    public CreditCardInfo(String cardNumber, String cvv) {
        this.cardNumber = cardNumber;
        this.cvv = cvv;
    }

    public String getCardNumber() {
        return cardNumber;
    }

    public String getCsv() {
        return cvv;
    }

    public boolean validate() {
        return cardNumber.length() == 16
                && cardNumber.chars().allMatch(value -> value >= '0' && value <= '9')
                && cvv.length() == 3
                && cvv.chars().allMatch(value -> value >= '0' && value <= '9');
    }
}
