package com.tapjoy;

/* JADX INFO: loaded from: classes.dex */
public class TJError {
    public int code;
    public String message;

    public TJError(int errorCode, String errorMessage) {
        this.code = errorCode;
        this.message = errorMessage;
    }
}
