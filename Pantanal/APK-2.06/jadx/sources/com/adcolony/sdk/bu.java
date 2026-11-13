package com.adcolony.sdk;

/* JADX INFO: loaded from: classes.dex */
class bu extends Exception {
    public bu() {
        super("We could not load your default GCM_senderId. Have you added your google-service.json file to the project?");
    }
}
