package com.heyzap.http;

import org.apache.http.Header;
import org.json.JSONArray;
import org.json.JSONObject;

/* JADX INFO: loaded from: classes.dex */
public class SyncJsonHttpResponseHandler extends JsonHttpResponseHandler {
    public JSONObject responseObj = null;
    public Throwable error = null;
    public Boolean finished = false;

    public SyncJsonHttpResponseHandler() {
        setUseSynchronousMode(true);
    }

    @Override // com.heyzap.http.JsonHttpResponseHandler
    public void onSuccess(int statusCode, Header[] headers, JSONArray response) {
        this.finished = true;
        this.error = new Throwable("Wrong response type");
        synchronized (this) {
            notifyAll();
        }
    }

    @Override // com.heyzap.http.JsonHttpResponseHandler
    public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
        this.responseObj = response;
        this.finished = true;
        synchronized (this) {
            notifyAll();
        }
    }

    @Override // com.heyzap.http.JsonHttpResponseHandler, com.heyzap.http.TextHttpResponseHandler
    public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
        this.error = throwable;
        this.finished = true;
        synchronized (this) {
            notifyAll();
        }
    }

    @Override // com.heyzap.http.JsonHttpResponseHandler
    public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
        this.error = throwable;
        this.finished = true;
        synchronized (this) {
            notifyAll();
        }
    }

    @Override // com.heyzap.http.JsonHttpResponseHandler
    public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONArray errorResponse) {
        this.error = throwable;
        this.finished = true;
        synchronized (this) {
            notifyAll();
        }
    }

    public JSONObject getResponse() {
        return this.responseObj;
    }

    public Throwable getError() {
        return this.error;
    }

    public Boolean hasError() {
        return Boolean.valueOf(this.error != null);
    }

    public Boolean isFinished() {
        return this.finished;
    }
}
