package com.tapjoy;

/* JADX INFO: loaded from: classes.dex */
public class TapjoyErrorMessage {
    private ErrorType a;
    private String b;

    public enum ErrorType {
        INTERNAL_ERROR,
        SDK_ERROR,
        SERVER_ERROR,
        INTEGRATION_ERROR,
        NETWORK_ERROR
    }

    public TapjoyErrorMessage(ErrorType type, String message) {
        this.a = type;
        this.b = message;
    }

    public ErrorType getType() {
        return this.a;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Type=" + this.a.toString());
        sb.append(";Message=" + this.b);
        return sb.toString();
    }
}
