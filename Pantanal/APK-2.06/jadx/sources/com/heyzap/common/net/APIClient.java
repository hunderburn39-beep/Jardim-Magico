package com.heyzap.common.net;

import android.content.Context;
import com.heyzap.common.concurrency.ExecutorPool;
import com.heyzap.http.AsyncHttpClient;
import com.heyzap.http.AsyncHttpResponseHandler;
import com.heyzap.http.RequestHandle;
import com.heyzap.http.RequestParams;
import com.heyzap.http.SDKCookieStore;
import com.heyzap.internal.Logger;
import com.heyzap.internal.Utils;
import java.math.BigInteger;
import java.net.URI;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Locale;
import org.anddev.andengine.entity.layer.tiled.tmx.util.constants.TMXConstants;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.ProtocolException;
import org.apache.http.client.RedirectHandler;
import org.apache.http.cookie.Cookie;
import org.apache.http.cookie.CookieOrigin;
import org.apache.http.cookie.CookieSpec;
import org.apache.http.cookie.CookieSpecFactory;
import org.apache.http.impl.cookie.BrowserCompatSpec;
import org.apache.http.params.HttpParams;
import org.apache.http.protocol.HttpContext;

/* JADX INFO: loaded from: classes.dex */
public class APIClient {
    private static final String BASE_ENDPOINT = "/in_game_api/ads/";
    private static final String USER_AGENT = "Heyzap Android Client";
    private static AsyncHttpClient client;
    private static SDKCookieStore cookieStore;
    public static String DOMAIN = "ads.heyzap.com";
    public static final Object lock = new Object();

    static {
        client = new AsyncHttpClient();
        client = new AsyncHttpClient();
        client.setThreadPool(ExecutorPool.getInstance());
        client.getHttpClient().getCookieSpecs().register("global", new GlobalCookieSpecFactory());
        client.getHttpClient().getParams().setParameter("http.protocol.cookie-policy", "global");
        client.getHttpClient().setRedirectHandler(new RedirectHandler() { // from class: com.heyzap.common.net.APIClient.1
            @Override // org.apache.http.client.RedirectHandler
            public boolean isRedirectRequested(HttpResponse paramHttpResponse, HttpContext paramHttpContext) {
                return false;
            }

            @Override // org.apache.http.client.RedirectHandler
            public URI getLocationURI(HttpResponse paramHttpResponse, HttpContext paramHttpContext) throws ProtocolException {
                return null;
            }
        });
    }

    public static synchronized void init(Context context) {
        if (cookieStore == null) {
            cookieStore = new SDKCookieStore(context);
            client.setCookieStore(cookieStore);
        }
    }

    public static String getBaseUrl() {
        return "https://" + DOMAIN;
    }

    public static void get(Context context, String url, AsyncHttpResponseHandler responseHandler) {
        get(context, url, null, responseHandler);
    }

    public static void get(Context context, String url, RequestParams params) {
        get(context, url, params, null);
    }

    public static void get(final Context context, final String url, final RequestParams params, final AsyncHttpResponseHandler responseHandler) {
        init(context);
        ExecutorPool.getInstance().execute(new Runnable() { // from class: com.heyzap.common.net.APIClient.2
            @Override // java.lang.Runnable
            public void run() {
                RequestParams augmentedParams = APIClient.augmentParams(params, context);
                String finalUrl = APIClient.getAbsoluteUrl(url);
                APIClient.log("GET", finalUrl, augmentedParams.toString());
                APIClient.client.get(context, finalUrl, augmentedParams, responseHandler);
            }
        });
    }

    public static RequestHandle simpleGet(Context context, String url, RequestParams params, AsyncHttpResponseHandler responseHandler) {
        log("GET", url, params.toString());
        return client.get(context, url, params, responseHandler);
    }

    public static RequestHandle simplePost(Context context, String url, HttpEntity entity, String contentType, AsyncHttpResponseHandler responseHandler) {
        log("POST", url, entity != null ? entity.toString() : "");
        return client.post(context, url, entity, contentType, responseHandler);
    }

    public static RequestHandle simplePost(Context context, String url, RequestParams params, AsyncHttpResponseHandler responseHandler) {
        log("POST", url, params != null ? params.toString() : "");
        return client.post(context, url, params, responseHandler);
    }

    public static void post(Context context, String url, AsyncHttpResponseHandler responseHandler) {
        post(context, url, null, responseHandler);
    }

    public static void post(Context context, String url, RequestParams params) {
        post(context, url, params, null);
    }

    public static void post(final Context context, final String url, final RequestParams params, final AsyncHttpResponseHandler responseHandler) {
        init(context);
        ExecutorPool.getInstance().execute(new Runnable() { // from class: com.heyzap.common.net.APIClient.3
            @Override // java.lang.Runnable
            public void run() {
                RequestParams augmentedParams = APIClient.augmentParams(params, context);
                String finalUrl = APIClient.getAbsoluteUrl(url);
                APIClient.signParams(finalUrl, augmentedParams);
                APIClient.log("POST", finalUrl, augmentedParams.toString());
                APIClient.client.post(context, finalUrl, augmentedParams, responseHandler);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static void signParams(String url, RequestParams params) {
        params.add("extras", signature(url + "?" + params.toString()));
    }

    public static byte[] a(byte[] abyte0) throws NoSuchAlgorithmException {
        MessageDigest messagedigest = MessageDigest.getInstance("SHA-1");
        messagedigest.update(abyte0);
        return messagedigest.digest();
    }

    public static String b(byte[] abyte0) {
        BigInteger biginteger = new BigInteger(1, abyte0);
        return String.format(Locale.US, "%0" + (abyte0.length << 1) + TMXConstants.TAG_OBJECT_ATTRIBUTE_X, biginteger);
    }

    public static String signature(String in) {
        try {
            return b(a(in.getBytes()));
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static void cancelRequests(Context context) {
        client.cancelRequests(context, true);
    }

    public static void clearCookies(Context context) {
        cookieStore.clear();
        cookieStore = new SDKCookieStore(context);
        client.setCookieStore(cookieStore);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static String getAbsoluteUrl(String relativeUrl) {
        if (relativeUrl == null || !relativeUrl.startsWith("/")) {
            return (relativeUrl == null || !(relativeUrl.startsWith("http://") || relativeUrl.startsWith("https://"))) ? getBaseUrl() + BASE_ENDPOINT + relativeUrl : relativeUrl;
        }
        return getBaseUrl() + relativeUrl;
    }

    public static RequestParams augmentParams(RequestParams params, Context context) {
        synchronized (lock) {
            if (params == null) {
                params = new RequestParams();
                params.merge(Utils.extraParams(context));
            } else {
                params.merge(Utils.extraParams(context));
            }
            throw th;
        }
        return params;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static void log(String method, String finalUrl, String params) {
        Logger.debug(String.format("HTTP %s %s?%s", method, finalUrl, params));
    }

    private static class GlobalCookieSpec extends BrowserCompatSpec {
        private GlobalCookieSpec() {
        }

        @Override // org.apache.http.impl.cookie.CookieSpecBase, org.apache.http.cookie.CookieSpec
        public boolean match(Cookie cookie, CookieOrigin origin) {
            return true;
        }
    }

    private static class GlobalCookieSpecFactory implements CookieSpecFactory {
        private GlobalCookieSpecFactory() {
        }

        @Override // org.apache.http.cookie.CookieSpecFactory
        public CookieSpec newInstance(HttpParams httpParams) {
            return new GlobalCookieSpec();
        }
    }
}
