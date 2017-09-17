package io.beanmother.testmodel;

public class Price {
    public static enum Currency {
        USD, KRW, JPY
    }

    private float amount;
    private Currency currency;

    public Price(float amount, Currency curreny) {
        this.amount = amount;
        this.currency = curreny;
    }

    public float getAmount() {
        return amount;
    }


    public Currency getCurrency() {
        return currency;
    }
}
