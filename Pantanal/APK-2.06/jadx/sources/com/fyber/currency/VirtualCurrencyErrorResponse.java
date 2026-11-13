package com.fyber.currency;

import com.fyber.b.k;

/* JADX INFO: loaded from: classes.dex */
public class VirtualCurrencyErrorResponse implements k.a {
    private final ErrorType a;
    private final String b;
    private final String c;

    public enum ErrorType {
        ERROR_INVALID_RESPONSE,
        ERROR_INVALID_RESPONSE_SIGNATURE,
        SERVER_RETURNED_ERROR,
        ERROR_OTHER
    }

    public VirtualCurrencyErrorResponse(ErrorType errorType, String str, String str2) {
        this.a = errorType;
        this.b = str;
        this.c = str2;
    }

    public ErrorType getError() {
        return this.a;
    }

    public String getErrorCode() {
        return this.b;
    }

    public String getErrorMessage() {
        return this.c != null ? this.c : "";
    }
}
