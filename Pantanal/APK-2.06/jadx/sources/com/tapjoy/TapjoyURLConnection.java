package com.tapjoy;

import com.tapjoy.internal.eh;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Map;

/* JADX INFO: loaded from: classes.dex */
public class TapjoyURLConnection {
    public static final int TYPE_GET = 0;
    public static final int TYPE_POST = 1;

    public TapjoyHttpURLResponse getRedirectFromURL(String url) {
        return getResponseFromURL(url, "", 0, true, null, null, null);
    }

    public TapjoyHttpURLResponse getResponseFromURL(String url, Map map) {
        return getResponseFromURL(url, TapjoyUtil.convertURLParams(map, false), 0);
    }

    public TapjoyHttpURLResponse getResponseFromURL(String url, Map map, int type) {
        return getResponseFromURL(url, TapjoyUtil.convertURLParams(map, false), type);
    }

    public TapjoyHttpURLResponse getResponseFromURL(String url) {
        return getResponseFromURL(url, "", 0);
    }

    public TapjoyHttpURLResponse getResponseFromURL(String url, String params) {
        return getResponseFromURL(url, params, 0);
    }

    public TapjoyHttpURLResponse getResponseFromURL(String url, String params, int type) {
        return getResponseFromURL(url, params, type, false, null, null, null);
    }

    public TapjoyHttpURLResponse getResponseFromURL(String url, Map map, Map map2, Map map3) {
        return getResponseFromURL(url, map != null ? TapjoyUtil.convertURLParams(map, false) : "", 1, false, map2, "application/x-www-form-urlencoded", TapjoyUtil.convertURLParams(map3, false));
    }

    public TapjoyHttpURLResponse getResponseFromURL(String url, Map map, Map map2, String postJsonData) {
        return getResponseFromURL(url, map != null ? TapjoyUtil.convertURLParams(map, false) : "", 1, false, map2, "application/json;charset=utf-8", postJsonData);
    }

    /* JADX WARN: Code duplicated, block: B:12:0x0057 A[Catch: Exception -> 0x007b, TryCatch #2 {Exception -> 0x007b, blocks: (B:9:0x0048, B:10:0x004b, B:12:0x0057, B:13:0x005f, B:15:0x0065, B:41:0x014d, B:43:0x0154, B:44:0x01a2, B:46:0x01bf, B:48:0x01d0, B:49:0x01d5, B:51:0x01db, B:52:0x01f2, B:53:0x01f8, B:55:0x01fe, B:56:0x0206, B:60:0x021a, B:62:0x0220, B:58:0x020e), top: B:73:0x0048, inners: #1 }] */
    /* JADX WARN: Code duplicated, block: B:15:0x0065 A[Catch: Exception -> 0x007b, LOOP:1: B:13:0x005f->B:15:0x0065, LOOP_END, TRY_LEAVE, TryCatch #2 {Exception -> 0x007b, blocks: (B:9:0x0048, B:10:0x004b, B:12:0x0057, B:13:0x005f, B:15:0x0065, B:41:0x014d, B:43:0x0154, B:44:0x01a2, B:46:0x01bf, B:48:0x01d0, B:49:0x01d5, B:51:0x01db, B:52:0x01f2, B:53:0x01f8, B:55:0x01fe, B:56:0x0206, B:60:0x021a, B:62:0x0220, B:58:0x020e), top: B:73:0x0048, inners: #1 }] */
    /* JADX WARN: Code duplicated, block: B:41:0x014d A[Catch: Exception -> 0x007b, TRY_ENTER, TryCatch #2 {Exception -> 0x007b, blocks: (B:9:0x0048, B:10:0x004b, B:12:0x0057, B:13:0x005f, B:15:0x0065, B:41:0x014d, B:43:0x0154, B:44:0x01a2, B:46:0x01bf, B:48:0x01d0, B:49:0x01d5, B:51:0x01db, B:52:0x01f2, B:53:0x01f8, B:55:0x01fe, B:56:0x0206, B:60:0x021a, B:62:0x0220, B:58:0x020e), top: B:73:0x0048, inners: #1 }] */
    /* JADX WARN: Code duplicated, block: B:43:0x0154 A[Catch: Exception -> 0x007b, TryCatch #2 {Exception -> 0x007b, blocks: (B:9:0x0048, B:10:0x004b, B:12:0x0057, B:13:0x005f, B:15:0x0065, B:41:0x014d, B:43:0x0154, B:44:0x01a2, B:46:0x01bf, B:48:0x01d0, B:49:0x01d5, B:51:0x01db, B:52:0x01f2, B:53:0x01f8, B:55:0x01fe, B:56:0x0206, B:60:0x021a, B:62:0x0220, B:58:0x020e), top: B:73:0x0048, inners: #1 }] */
    /* JADX WARN: Code duplicated, block: B:46:0x01bf A[Catch: Exception -> 0x007b, TryCatch #2 {Exception -> 0x007b, blocks: (B:9:0x0048, B:10:0x004b, B:12:0x0057, B:13:0x005f, B:15:0x0065, B:41:0x014d, B:43:0x0154, B:44:0x01a2, B:46:0x01bf, B:48:0x01d0, B:49:0x01d5, B:51:0x01db, B:52:0x01f2, B:53:0x01f8, B:55:0x01fe, B:56:0x0206, B:60:0x021a, B:62:0x0220, B:58:0x020e), top: B:73:0x0048, inners: #1 }] */
    /* JADX WARN: Code duplicated, block: B:48:0x01d0 A[Catch: Exception -> 0x007b, TryCatch #2 {Exception -> 0x007b, blocks: (B:9:0x0048, B:10:0x004b, B:12:0x0057, B:13:0x005f, B:15:0x0065, B:41:0x014d, B:43:0x0154, B:44:0x01a2, B:46:0x01bf, B:48:0x01d0, B:49:0x01d5, B:51:0x01db, B:52:0x01f2, B:53:0x01f8, B:55:0x01fe, B:56:0x0206, B:60:0x021a, B:62:0x0220, B:58:0x020e), top: B:73:0x0048, inners: #1 }] */
    /* JADX WARN: Code duplicated, block: B:51:0x01db A[Catch: Exception -> 0x007b, LOOP:2: B:49:0x01d5->B:51:0x01db, LOOP_END, TryCatch #2 {Exception -> 0x007b, blocks: (B:9:0x0048, B:10:0x004b, B:12:0x0057, B:13:0x005f, B:15:0x0065, B:41:0x014d, B:43:0x0154, B:44:0x01a2, B:46:0x01bf, B:48:0x01d0, B:49:0x01d5, B:51:0x01db, B:52:0x01f2, B:53:0x01f8, B:55:0x01fe, B:56:0x0206, B:60:0x021a, B:62:0x0220, B:58:0x020e), top: B:73:0x0048, inners: #1 }] */
    /* JADX WARN: Code duplicated, block: B:55:0x01fe A[Catch: Exception -> 0x007b, TryCatch #2 {Exception -> 0x007b, blocks: (B:9:0x0048, B:10:0x004b, B:12:0x0057, B:13:0x005f, B:15:0x0065, B:41:0x014d, B:43:0x0154, B:44:0x01a2, B:46:0x01bf, B:48:0x01d0, B:49:0x01d5, B:51:0x01db, B:52:0x01f2, B:53:0x01f8, B:55:0x01fe, B:56:0x0206, B:60:0x021a, B:62:0x0220, B:58:0x020e), top: B:73:0x0048, inners: #1 }] */
    /* JADX WARN: Code duplicated, block: B:59:0x0218 A[DONT_INVERT] */
    /* JADX WARN: Code duplicated, block: B:60:0x021a A[Catch: Exception -> 0x007b, TRY_ENTER, TryCatch #2 {Exception -> 0x007b, blocks: (B:9:0x0048, B:10:0x004b, B:12:0x0057, B:13:0x005f, B:15:0x0065, B:41:0x014d, B:43:0x0154, B:44:0x01a2, B:46:0x01bf, B:48:0x01d0, B:49:0x01d5, B:51:0x01db, B:52:0x01f2, B:53:0x01f8, B:55:0x01fe, B:56:0x0206, B:60:0x021a, B:62:0x0220, B:58:0x020e), top: B:73:0x0048, inners: #1 }] */
    /* JADX WARN: Code duplicated, block: B:68:0x0244  */
    /* JADX WARN: Code duplicated, block: B:71:0x020e A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /* JADX WARN: Code duplicated, block: B:79:0x01f2 A[EDGE_INSN: B:79:0x01f2->B:52:0x01f2 BREAK  A[LOOP:2: B:49:0x01d5->B:51:0x01db], SYNTHETIC] */
    public TapjoyHttpURLResponse getResponseFromURL(String url, String params, int type, boolean getRedirectOnly, Map map, String postContentType, String postContent) {
        BufferedReader bufferedReader;
        String headerField;
        StringBuilder sb;
        String line;
        TapjoyHttpURLResponse tapjoyHttpURLResponse = new TapjoyHttpURLResponse();
        HttpURLConnection httpURLConnection = null;
        try {
            String str = url + params;
            TapjoyLog.i("TapjoyURLConnection", "http " + (type == 0 ? "get" : "post") + ": " + str);
            HttpURLConnection httpURLConnection2 = (HttpURLConnection) eh.a(new URL(str));
            if (getRedirectOnly) {
                try {
                    httpURLConnection2.setInstanceFollowRedirects(false);
                    httpURLConnection2.setConnectTimeout(15000);
                    httpURLConnection2.setReadTimeout(30000);
                    if (map != null) {
                        for (Map.Entry entry : map.entrySet()) {
                            httpURLConnection2.setRequestProperty((String) entry.getKey(), (String) entry.getValue());
                        }
                    }
                    if (type == 1) {
                        httpURLConnection2.setRequestMethod("POST");
                        if (postContent != null) {
                            TapjoyLog.i("TapjoyURLConnection", "Content-Type: " + postContentType);
                            TapjoyLog.i("TapjoyURLConnection", "Content:");
                            TapjoyLog.i("TapjoyURLConnection", postContent);
                            httpURLConnection2.setRequestProperty("Content-Type", postContentType);
                            httpURLConnection2.setRequestProperty("Connection", TJAdUnitConstants.String.CLOSE);
                            httpURLConnection2.setDoOutput(true);
                            httpURLConnection2.setFixedLengthStreamingMode(postContent.length());
                            OutputStreamWriter outputStreamWriter = new OutputStreamWriter(httpURLConnection2.getOutputStream());
                            outputStreamWriter.write(postContent);
                            outputStreamWriter.close();
                        }
                    }
                    httpURLConnection2.connect();
                    tapjoyHttpURLResponse.statusCode = httpURLConnection2.getResponseCode();
                    tapjoyHttpURLResponse.headerFields = httpURLConnection2.getHeaderFields();
                    tapjoyHttpURLResponse.date = httpURLConnection2.getDate();
                    tapjoyHttpURLResponse.expires = httpURLConnection2.getExpiration();
                    if (!getRedirectOnly) {
                        bufferedReader = null;
                    } else {
                        bufferedReader = new BufferedReader(new InputStreamReader(httpURLConnection2.getInputStream()));
                    }
                    if (!getRedirectOnly) {
                        sb = new StringBuilder();
                        while (true) {
                            line = bufferedReader.readLine();
                            if (line != null) {
                                break;
                            }
                            sb.append(line + '\n');
                        }
                        tapjoyHttpURLResponse.response = sb.toString();
                    }
                    if (tapjoyHttpURLResponse.statusCode == 302) {
                        tapjoyHttpURLResponse.redirectURL = httpURLConnection2.getHeaderField("Location");
                    }
                    headerField = httpURLConnection2.getHeaderField("content-length");
                    if (headerField != null) {
                        try {
                            tapjoyHttpURLResponse.contentLength = Integer.valueOf(headerField).intValue();
                        } catch (Exception e) {
                            TapjoyLog.e("TapjoyURLConnection", "Exception: " + e.toString());
                        }
                        if (bufferedReader != null) {
                            bufferedReader.close();
                        }
                    } else if (bufferedReader != null) {
                        bufferedReader.close();
                    }
                } catch (Exception e2) {
                    httpURLConnection = httpURLConnection2;
                    e = e2;
                    TapjoyLog.e("TapjoyURLConnection", "Exception: " + e.toString());
                    tapjoyHttpURLResponse.statusCode = 0;
                    if (httpURLConnection != null) {
                        try {
                            if (tapjoyHttpURLResponse.response == null) {
                                BufferedReader bufferedReader2 = new BufferedReader(new InputStreamReader(httpURLConnection.getErrorStream()));
                                StringBuilder sb2 = new StringBuilder();
                                while (true) {
                                    String line2 = bufferedReader2.readLine();
                                    if (line2 == null) {
                                        break;
                                    }
                                    sb2.append(line2 + '\n');
                                }
                                tapjoyHttpURLResponse.response = sb2.toString();
                            }
                        } catch (Exception e3) {
                            TapjoyLog.e("TapjoyURLConnection", "Exception trying to get error code/content: " + e3.toString());
                        }
                    }
                }
            } else {
                httpURLConnection2.setConnectTimeout(15000);
                httpURLConnection2.setReadTimeout(30000);
                if (map != null) {
                    while (r7.hasNext()) {
                        httpURLConnection2.setRequestProperty((String) entry.getKey(), (String) entry.getValue());
                    }
                }
                if (type == 1) {
                    httpURLConnection2.setRequestMethod("POST");
                    if (postContent != null) {
                        TapjoyLog.i("TapjoyURLConnection", "Content-Type: " + postContentType);
                        TapjoyLog.i("TapjoyURLConnection", "Content:");
                        TapjoyLog.i("TapjoyURLConnection", postContent);
                        httpURLConnection2.setRequestProperty("Content-Type", postContentType);
                        httpURLConnection2.setRequestProperty("Connection", TJAdUnitConstants.String.CLOSE);
                        httpURLConnection2.setDoOutput(true);
                        httpURLConnection2.setFixedLengthStreamingMode(postContent.length());
                        OutputStreamWriter outputStreamWriter2 = new OutputStreamWriter(httpURLConnection2.getOutputStream());
                        outputStreamWriter2.write(postContent);
                        outputStreamWriter2.close();
                    }
                }
                httpURLConnection2.connect();
                tapjoyHttpURLResponse.statusCode = httpURLConnection2.getResponseCode();
                tapjoyHttpURLResponse.headerFields = httpURLConnection2.getHeaderFields();
                tapjoyHttpURLResponse.date = httpURLConnection2.getDate();
                tapjoyHttpURLResponse.expires = httpURLConnection2.getExpiration();
                if (!getRedirectOnly) {
                    bufferedReader = null;
                } else {
                    bufferedReader = new BufferedReader(new InputStreamReader(httpURLConnection2.getInputStream()));
                }
                if (!getRedirectOnly) {
                    sb = new StringBuilder();
                    while (true) {
                        line = bufferedReader.readLine();
                        if (line != null) {
                            break;
                            break;
                        }
                        sb.append(line + '\n');
                    }
                    tapjoyHttpURLResponse.response = sb.toString();
                }
                if (tapjoyHttpURLResponse.statusCode == 302) {
                    tapjoyHttpURLResponse.redirectURL = httpURLConnection2.getHeaderField("Location");
                }
                headerField = httpURLConnection2.getHeaderField("content-length");
                if (headerField != null) {
                    tapjoyHttpURLResponse.contentLength = Integer.valueOf(headerField).intValue();
                    if (bufferedReader != null) {
                        bufferedReader.close();
                    }
                } else if (bufferedReader != null) {
                    bufferedReader.close();
                }
            }
        } catch (Exception e4) {
            e = e4;
        }
        TapjoyLog.i("TapjoyURLConnection", "--------------------");
        TapjoyLog.i("TapjoyURLConnection", "response status: " + tapjoyHttpURLResponse.statusCode);
        TapjoyLog.i("TapjoyURLConnection", "response size: " + tapjoyHttpURLResponse.contentLength);
        if (tapjoyHttpURLResponse.redirectURL != null && tapjoyHttpURLResponse.redirectURL.length() > 0) {
            TapjoyLog.i("TapjoyURLConnection", "redirectURL: " + tapjoyHttpURLResponse.redirectURL);
        }
        TapjoyLog.i("TapjoyURLConnection", "--------------------");
        return tapjoyHttpURLResponse;
    }

    public String getContentLength(String url) {
        String headerField;
        try {
            String strReplaceAll = url.replaceAll(" ", "%20");
            TapjoyLog.d("TapjoyURLConnection", "requestURL: " + strReplaceAll);
            HttpURLConnection httpURLConnection = (HttpURLConnection) eh.a(new URL(strReplaceAll));
            httpURLConnection.setConnectTimeout(15000);
            httpURLConnection.setReadTimeout(30000);
            headerField = httpURLConnection.getHeaderField("content-length");
        } catch (Exception e) {
            TapjoyLog.e("TapjoyURLConnection", "Exception: " + e.toString());
            headerField = null;
        }
        TapjoyLog.d("TapjoyURLConnection", "content-length: " + headerField);
        return headerField;
    }
}
