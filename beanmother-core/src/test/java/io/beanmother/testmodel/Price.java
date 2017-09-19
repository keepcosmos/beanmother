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

    @Override
    public boolean equals(Object obj) {
        if (this.getClass() == obj.getClass()){
            return this.amount == ((Price) obj).amount
                    && this.currency == ((Price) obj).currency;
        }
        return false;
    }
}
