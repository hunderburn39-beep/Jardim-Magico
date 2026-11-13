package com.tapjoy;

import android.content.Context;
import android.content.SharedPreferences;
import com.tapjoy.internal.en;
import java.util.Map;
import java.util.UUID;
import org.w3c.dom.Document;

/* JADX INFO: loaded from: classes.dex */
public class TJCurrency {
    private static TJGetCurrencyBalanceListener d;
    private static TJSpendCurrencyListener e;
    private static TJAwardCurrencyListener f;
    private static TJEarnedCurrencyListener g;
    String a = null;
    int b = 0;
    Context c;

    public TJCurrency(Context applicationContext) {
        this.c = applicationContext;
    }

    public void saveCurrencyBalance(int balance) {
        SharedPreferences.Editor editorEdit = this.c.getSharedPreferences(TapjoyConstants.TJC_PREFERENCE, 0).edit();
        editorEdit.putInt(TapjoyConstants.PREF_LAST_CURRENCY_BALANCE, balance);
        editorEdit.commit();
    }

    public int getLocalCurrencyBalance() {
        return this.c.getSharedPreferences(TapjoyConstants.TJC_PREFERENCE, 0).getInt(TapjoyConstants.PREF_LAST_CURRENCY_BALANCE, -9999);
    }

    public void getCurrencyBalance(TJGetCurrencyBalanceListener listener) {
        d = listener;
        new Thread(new Runnable() { // from class: com.tapjoy.TJCurrency.1
            @Override // java.lang.Runnable
            public final void run() {
                TJCurrency.this.a(new TapjoyURLConnection().getResponseFromURL(TapjoyConnectCore.getHostURL() + TapjoyConstants.TJC_GET_CURRENCY_BALANCE_URL_PATH, TapjoyConnectCore.getURLParams()));
            }
        }).start();
    }

    public void spendCurrency(int amount, TJSpendCurrencyListener listener) {
        if (amount < 0) {
            TapjoyLog.e("TJCurrency", new TapjoyErrorMessage(TapjoyErrorMessage.ErrorType.INTEGRATION_ERROR, "Amount must be a positive number for the spendCurrency API"));
            return;
        }
        this.a = String.valueOf(amount);
        e = listener;
        new Thread(new Runnable() { // from class: com.tapjoy.TJCurrency.2
            @Override // java.lang.Runnable
            public final void run() {
                Map uRLParams = TapjoyConnectCore.getURLParams();
                TapjoyUtil.safePut(uRLParams, TapjoyConstants.TJC_CURRENCY, TJCurrency.this.a, true);
                TJCurrency.this.b(new TapjoyURLConnection().getResponseFromURL(TapjoyConnectCore.getHostURL() + TapjoyConstants.TJC_SPEND_CURRENCY_URL_PATH, uRLParams));
            }
        }).start();
    }

    public void awardCurrency(int amount, TJAwardCurrencyListener listener) {
        if (amount < 0) {
            TapjoyLog.e("TJCurrency", new TapjoyErrorMessage(TapjoyErrorMessage.ErrorType.INTEGRATION_ERROR, "Amount must be a positive number for the awardCurrency API"));
            return;
        }
        this.b = amount;
        f = listener;
        new Thread(new Runnable() { // from class: com.tapjoy.TJCurrency.3
            @Override // java.lang.Runnable
            public final void run() {
                String string = UUID.randomUUID().toString();
                long jCurrentTimeMillis = System.currentTimeMillis() / 1000;
                Map genericURLParams = TapjoyConnectCore.getGenericURLParams();
                TapjoyUtil.safePut(genericURLParams, TapjoyConstants.TJC_CURRENCY, String.valueOf(TJCurrency.this.b), true);
                TapjoyUtil.safePut(genericURLParams, TapjoyConstants.TJC_GUID, string, true);
                TapjoyUtil.safePut(genericURLParams, "timestamp", String.valueOf(jCurrentTimeMillis), true);
                TapjoyUtil.safePut(genericURLParams, TapjoyConstants.TJC_VERIFIER, TapjoyConnectCore.getAwardCurrencyVerifier(jCurrentTimeMillis, TJCurrency.this.b, string), true);
                TJCurrency.this.c(new TapjoyURLConnection().getResponseFromURL(TapjoyConnectCore.getHostURL() + TapjoyConstants.TJC_AWARD_CURRENCY_URL_PATH, genericURLParams));
            }
        }).start();
    }

    public void setEarnedCurrencyListener(TJEarnedCurrencyListener listener) {
        g = listener;
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX WARN: Code duplicated, block: B:29:0x009a A[Catch: all -> 0x00a2, TRY_LEAVE, TryCatch #0 {, blocks: (B:3:0x0001, B:5:0x0005, B:7:0x000d, B:9:0x0019, B:11:0x0021, B:14:0x0039, B:19:0x004b, B:20:0x0068, B:22:0x006f, B:26:0x0077, B:34:0x00a5, B:35:0x00b4, B:27:0x0096, B:29:0x009a, B:36:0x00c3), top: B:38:0x0001, inners: #1 }] */
    public synchronized void a(TapjoyHttpURLResponse tapjoyHttpURLResponse) {
        if (tapjoyHttpURLResponse.response != null) {
            Document documentBuildDocument = TapjoyUtil.buildDocument(tapjoyHttpURLResponse.response);
            if (documentBuildDocument != null) {
                String nodeTrimValue = TapjoyUtil.getNodeTrimValue(documentBuildDocument.getElementsByTagName("Success"));
                if (nodeTrimValue != null && nodeTrimValue.equals("true")) {
                    String nodeTrimValue2 = TapjoyUtil.getNodeTrimValue(documentBuildDocument.getElementsByTagName("TapPoints"));
                    String nodeTrimValue3 = TapjoyUtil.getNodeTrimValue(documentBuildDocument.getElementsByTagName("CurrencyName"));
                    if (nodeTrimValue2 != null && nodeTrimValue3 != null) {
                        try {
                            int i = Integer.parseInt(nodeTrimValue2);
                            int localCurrencyBalance = getLocalCurrencyBalance();
                            if (g != null && localCurrencyBalance != -9999 && i > localCurrencyBalance) {
                                TapjoyLog.i("TJCurrency", "earned: " + (i - localCurrencyBalance));
                                g.onEarnedCurrency(nodeTrimValue3, i - localCurrencyBalance);
                            }
                            saveCurrencyBalance(i);
                            if (d != null) {
                                d.onGetCurrencyBalanceResponse(nodeTrimValue3, i);
                            }
                        } catch (Exception e2) {
                            TapjoyLog.e("TJCurrency", new TapjoyErrorMessage(TapjoyErrorMessage.ErrorType.SERVER_ERROR, "Error parsing XML and calling listener: " + e2.toString()));
                            if (d != null) {
                                d.onGetCurrencyBalanceResponseFailure("Failed to get currency balance");
                            }
                        }
                    } else {
                        TapjoyLog.e("TJCurrency", new TapjoyErrorMessage(TapjoyErrorMessage.ErrorType.SERVER_ERROR, "getCurrencyBalance response is invalid -- missing tags."));
                    }
                } else {
                    TapjoyLog.e("TJCurrency", new TapjoyErrorMessage(TapjoyErrorMessage.ErrorType.SERVER_ERROR, "getCurrencyBalance response is invalid -- missing <Success> tag."));
                }
            }
        } else {
            TapjoyLog.e("TJCurrency", new TapjoyErrorMessage(TapjoyErrorMessage.ErrorType.SERVER_ERROR, "getCurrencyBalance response is NULL"));
        }
        if (d != null) {
            d.onGetCurrencyBalanceResponseFailure("Failed to get currency balance");
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public synchronized void b(TapjoyHttpURLResponse tapjoyHttpURLResponse) {
        String nodeTrimValue = "Failed to spend currency";
        if (tapjoyHttpURLResponse.response != null) {
            Document documentBuildDocument = TapjoyUtil.buildDocument(tapjoyHttpURLResponse.response);
            if (documentBuildDocument != null) {
                String nodeTrimValue2 = TapjoyUtil.getNodeTrimValue(documentBuildDocument.getElementsByTagName("Success"));
                if (nodeTrimValue2 != null && nodeTrimValue2.equals("true")) {
                    String nodeTrimValue3 = TapjoyUtil.getNodeTrimValue(documentBuildDocument.getElementsByTagName("TapPoints"));
                    String nodeTrimValue4 = TapjoyUtil.getNodeTrimValue(documentBuildDocument.getElementsByTagName("CurrencyName"));
                    if (nodeTrimValue3 != null && nodeTrimValue4 != null) {
                        int i = Integer.parseInt(nodeTrimValue3);
                        saveCurrencyBalance(i);
                        if (e != null) {
                            e.onSpendCurrencyResponse(nodeTrimValue4, i);
                        }
                    } else {
                        TapjoyLog.e("TJCurrency", new TapjoyErrorMessage(TapjoyErrorMessage.ErrorType.SERVER_ERROR, "spendCurrency response is invalid -- missing tags."));
                    }
                } else if (nodeTrimValue2 != null && nodeTrimValue2.endsWith("false")) {
                    nodeTrimValue = TapjoyUtil.getNodeTrimValue(documentBuildDocument.getElementsByTagName("Message"));
                    TapjoyLog.i("TJCurrency", nodeTrimValue);
                    if ("BalanceTooLowError".equals(TapjoyUtil.getNodeTrimValue(documentBuildDocument.getElementsByTagName("MessageCode")))) {
                        en.a();
                    }
                } else {
                    TapjoyLog.e("TJCurrency", new TapjoyErrorMessage(TapjoyErrorMessage.ErrorType.SERVER_ERROR, "spendCurrency response is invalid -- missing <Success> tag."));
                }
            }
        } else {
            TapjoyLog.e("TJCurrency", new TapjoyErrorMessage(TapjoyErrorMessage.ErrorType.SERVER_ERROR, "spendCurrency response is NULL"));
        }
        if (e != null) {
            e.onSpendCurrencyResponseFailure(nodeTrimValue);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public synchronized void c(TapjoyHttpURLResponse tapjoyHttpURLResponse) {
        String nodeTrimValue = "Failed to award currency";
        if (tapjoyHttpURLResponse.response != null) {
            Document documentBuildDocument = TapjoyUtil.buildDocument(tapjoyHttpURLResponse.response);
            if (documentBuildDocument != null) {
                String nodeTrimValue2 = TapjoyUtil.getNodeTrimValue(documentBuildDocument.getElementsByTagName("Success"));
                if (nodeTrimValue2 != null && nodeTrimValue2.equals("true")) {
                    String nodeTrimValue3 = TapjoyUtil.getNodeTrimValue(documentBuildDocument.getElementsByTagName("TapPoints"));
                    String nodeTrimValue4 = TapjoyUtil.getNodeTrimValue(documentBuildDocument.getElementsByTagName("CurrencyName"));
                    if (nodeTrimValue3 != null && nodeTrimValue4 != null) {
                        int i = Integer.parseInt(nodeTrimValue3);
                        saveCurrencyBalance(i);
                        if (f != null) {
                            f.onAwardCurrencyResponse(nodeTrimValue4, i);
                        }
                    } else {
                        TapjoyLog.e("TJCurrency", new TapjoyErrorMessage(TapjoyErrorMessage.ErrorType.SERVER_ERROR, "awardCurrency response is invalid -- missing tags."));
                    }
                } else if (nodeTrimValue2 != null && nodeTrimValue2.endsWith("false")) {
                    nodeTrimValue = TapjoyUtil.getNodeTrimValue(documentBuildDocument.getElementsByTagName("Message"));
                    TapjoyLog.i("TJCurrency", nodeTrimValue);
                } else {
                    TapjoyLog.e("TJCurrency", new TapjoyErrorMessage(TapjoyErrorMessage.ErrorType.SERVER_ERROR, "awardCurrency response is invalid -- missing <Success> tag."));
                }
            }
        } else {
            TapjoyLog.e("TJCurrency", new TapjoyErrorMessage(TapjoyErrorMessage.ErrorType.SERVER_ERROR, "awardCurrency response is NULL"));
        }
        if (f != null) {
            f.onAwardCurrencyResponseFailure(nodeTrimValue);
        }
    }
}
