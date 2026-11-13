package com.heyzap.http;

import com.heyzap.internal.Logger;
import org.apache.http.Header;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

/* JADX INFO: loaded from: classes.dex */
public class JsonHttpResponseHandler extends TextHttpResponseHandler {
    private static final String LOG_TAG = "JsonHttpResponseHandler";

    public JsonHttpResponseHandler() {
        super(AsyncHttpResponseHandler.DEFAULT_CHARSET);
    }

    public JsonHttpResponseHandler(String encoding) {
        super(encoding);
    }

    public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
    }

    public void onSuccess(int statusCode, Header[] headers, JSONArray response) {
    }

    public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
    }

    public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONArray errorResponse) {
    }

    @Override // com.heyzap.http.TextHttpResponseHandler
    public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
    }

    @Override // com.heyzap.http.TextHttpResponseHandler
    public void onSuccess(int statusCode, Header[] headers, String responseString) {
    }

    @Override // com.heyzap.http.TextHttpResponseHandler, com.heyzap.http.AsyncHttpResponseHandler
    public final void onSuccess(final int statusCode, final Header[] headers, final byte[] responseBytes) {
        if (statusCode != 204) {
            new Thread(new Runnable() { // from class: com.heyzap.http.JsonHttpResponseHandler.1
                @Override // java.lang.Runnable
                public void run() {
                    try {
                        final Object jsonResponse = JsonHttpResponseHandler.this.parseResponse(responseBytes);
                        JsonHttpResponseHandler.this.postRunnable(new Runnable() { // from class: com.heyzap.http.JsonHttpResponseHandler.1.1
                            @Override // java.lang.Runnable
                            public void run() {
                                if (jsonResponse instanceof JSONObject) {
                                    JsonHttpResponseHandler.this.onSuccess(statusCode, headers, (JSONObject) jsonResponse);
                                } else if (jsonResponse instanceof JSONArray) {
                                    JsonHttpResponseHandler.this.onSuccess(statusCode, headers, (JSONArray) jsonResponse);
                                } else {
                                    JsonHttpResponseHandler.this.onFailure(statusCode, headers, new JSONException("Unexpected response type " + jsonResponse.getClass().getName()), (JSONObject) null);
                                }
                            }
                        });
                    } catch (JSONException ex) {
                        JsonHttpResponseHandler.this.postRunnable(new Runnable() { // from class: com.heyzap.http.JsonHttpResponseHandler.1.2
                            @Override // java.lang.Runnable
                            public void run() {
                                JsonHttpResponseHandler.this.onFailure(statusCode, headers, ex, (JSONObject) null);
                            }
                        });
                    }
                }
            }).start();
        } else {
            onSuccess(statusCode, headers, new JSONObject());
        }
    }

    @Override // com.heyzap.http.TextHttpResponseHandler, com.heyzap.http.AsyncHttpResponseHandler
    public void onFailure(final int statusCode, final Header[] headers, final byte[] responseBytes, final Throwable throwable) {
        if (responseBytes != null) {
            new Thread(new Runnable() { // from class: com.heyzap.http.JsonHttpResponseHandler.2
                @Override // java.lang.Runnable
                public void run() {
                    try {
                        final Object jsonResponse = JsonHttpResponseHandler.this.parseResponse(responseBytes);
                        JsonHttpResponseHandler.this.postRunnable(new Runnable() { // from class: com.heyzap.http.JsonHttpResponseHandler.2.1
                            @Override // java.lang.Runnable
                            public void run() {
                                if (jsonResponse instanceof JSONObject) {
                                    JsonHttpResponseHandler.this.onFailure(statusCode, headers, throwable, (JSONObject) jsonResponse);
                                    return;
                                }
                                if (jsonResponse instanceof JSONArray) {
                                    JsonHttpResponseHandler.this.onFailure(statusCode, headers, throwable, (JSONArray) jsonResponse);
                                } else if (jsonResponse instanceof String) {
                                    JsonHttpResponseHandler.this.onFailure(statusCode, headers, (String) jsonResponse, throwable);
                                } else {
                                    JsonHttpResponseHandler.this.onFailure(statusCode, headers, new JSONException("Unexpected response type " + jsonResponse.getClass().getName()), (JSONObject) null);
                                }
                            }
                        });
                    } catch (JSONException ex) {
                        JsonHttpResponseHandler.this.postRunnable(new Runnable() { // from class: com.heyzap.http.JsonHttpResponseHandler.2.2
                            @Override // java.lang.Runnable
                            public void run() {
                                JsonHttpResponseHandler.this.onFailure(statusCode, headers, ex, (JSONObject) null);
                            }
                        });
                    }
                }
            }).start();
        } else {
            Logger.debug("response body is null, calling onFailure(Throwable, JSONObject)");
            onFailure(statusCode, headers, throwable, (JSONObject) null);
        }
    }

    protected Object parseResponse(byte[] responseBody) throws JSONException {
        if (responseBody == null) {
            return null;
        }
        Object result = null;
        String jsonString = getResponseString(responseBody, getCharset());
        if (jsonString != null) {
            jsonString = jsonString.trim();
            if (jsonString.startsWith("{") || jsonString.startsWith("[")) {
                result = new JSONTokener(jsonString).nextValue();
            }
        }
        if (result == null) {
            return jsonString;
        }
        return result;
    }
}
