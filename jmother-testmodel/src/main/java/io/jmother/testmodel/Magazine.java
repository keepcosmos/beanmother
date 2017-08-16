package io.jmother.testmodel;

public class Magazine extends Book {

    private boolean subscription;

    public boolean isSubscription() {
        return subscription;
    }

    public void setSubscription(boolean subscription) {
        this.subscription = subscription;
    }
}
