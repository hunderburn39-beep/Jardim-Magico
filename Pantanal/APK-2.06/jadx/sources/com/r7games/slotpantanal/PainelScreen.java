package com.r7games.slotpantanal;

import android.accounts.AccountManager;
import android.annotation.TargetApi;
import android.app.AlertDialog;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.ConnectivityManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.SystemClock;
import android.preference.PreferenceManager;
import android.provider.Settings;
import android.telephony.TelephonyManager;
import android.util.Log;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.InterstitialAd;
import com.google.android.gms.analytics.GoogleAnalytics;
import com.google.android.gms.analytics.HitBuilders;
import com.google.android.gms.analytics.Tracker;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.games.Games;
import com.google.android.gms.games.GamesActivityResultCodes;
import com.google.android.gms.games.leaderboard.Leaderboards;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.heyzap.sdk.ads.HeyzapAds;
import com.heyzap.sdk.ads.IncentivizedAd;
import com.r7games.slotpantanal.google.services.GBaseGameActivity;
import com.r7games.slotpantanal.utils.IabHelper;
import com.r7games.slotpantanal.utils.IabResult;
import com.r7games.slotpantanal.utils.Inventory;
import com.r7games.slotpantanal.utils.Purchase;
import com.tapjoy.TJActionRequest;
import com.tapjoy.TJConnectListener;
import com.tapjoy.TJEarnedCurrencyListener;
import com.tapjoy.TJError;
import com.tapjoy.TJGetCurrencyBalanceListener;
import com.tapjoy.TJPlacement;
import com.tapjoy.TJPlacementListener;
import com.tapjoy.TJSpendCurrencyListener;
import com.tapjoy.TJVideoListener;
import com.tapjoy.Tapjoy;
import com.tapjoy.TapjoyConnectFlag;
import com.tapjoy.TapjoyConstants;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Hashtable;
import java.util.List;
import org.anddev.andengine.engine.Engine;
import org.anddev.andengine.engine.camera.Camera;
import org.anddev.andengine.engine.handler.timer.ITimerCallback;
import org.anddev.andengine.engine.handler.timer.TimerHandler;
import org.anddev.andengine.engine.options.EngineOptions;
import org.anddev.andengine.engine.options.resolutionpolicy.FillResolutionPolicy;
import org.anddev.andengine.entity.scene.Scene;
import org.anddev.andengine.entity.sprite.Sprite;
import org.anddev.andengine.input.touch.TouchEvent;
import org.anddev.andengine.opengl.texture.TextureOptions;
import org.anddev.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlas;
import org.anddev.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlasTextureRegionFactory;
import org.anddev.andengine.opengl.texture.region.TextureRegion;
import org.anddev.andengine.util.Debug;

/* JADX INFO: loaded from: classes.dex */
public class PainelScreen extends GBaseGameActivity implements TJGetCurrencyBalanceListener, TJPlacementListener {
    public static final String TAG = "Halloween Slot";
    public static final int cameraHeight = 480;
    public static final int cameraWidth = 800;
    public static final String credits_100000 = "creditos_100000";
    public static final String credits_150000 = "creditos_150000";
    public static final String credits_300000 = "creditos_300000";
    public static final String credits_50000 = "creditos_50000";
    public static final String credits_500000 = "creditos_500000";
    public Sprite[] AjudaButton;
    public Scene AjudaScene;
    public Scene CreditoScene;
    private TJPlacement NoShowPlacement;
    InterstitialAd adMob_interstitial;
    AdView adView;
    public Sprite background;
    public BitmapTextureAtlas backgroundAtlas;
    public TextureRegion backgroundRegion;
    public Sprite bgAjuda;
    public BitmapTextureAtlas bgAjudaAtlas;
    public TextureRegion bgAjudaRegion;
    public Sprite bgCredito;
    public BitmapTextureAtlas bgCreditoAtlas;
    public TextureRegion bgCreditoRegion;
    public BitmapTextureAtlas btnSmallAtlas;
    public TextureRegion btnSmallRegion;
    public Sprite[] button;
    public BitmapTextureAtlas buttonAtlas;
    public Sprite[] buttonCredito;
    public TextureRegion buttonRegion;
    public Camera camera;
    public Context context;
    public String dispositivo;
    SharedPreferences.Editor ed;
    private TJPlacement extraPlacement;
    String game;
    AccountManager mAccountManager;
    public IabHelper mHelper;
    Runnable mRunnable;
    public Intent mail;
    private TJPlacement offerwallPlacement;
    String orderId;
    String playerId;
    public Scene scene;
    long score;
    SharedPreferences sp;
    private Sprite splash;
    private BitmapTextureAtlas splashAtlas;
    private TextureRegion splashRegion;
    public Scene splashScene;
    public Tracker t;
    public Uri uriUrl;
    private boolean tapjoyOn = false;
    private int tentativa = 0;
    Handler mHandler = new Handler();
    boolean verificando = false;
    boolean solicitado = false;
    String tmCoutry = "";
    final int REQUEST_ACHIEVEMENTS = 1;
    boolean show_leaderboard = false;
    boolean show_conquistas = false;
    public Utils util = new Utils();
    Boolean sincronizado = false;
    int creditoAtual = 0;
    String titulo = "";
    String msg = "";
    String data = "";
    String idioma = "en";
    int tipoCredito = 0;
    Boolean notload = false;
    public Database dbase = new Database(this);
    boolean earnedPoints = false;
    boolean isDownload = false;
    public int amount = 0;
    public int credits_buy = 0;
    public int numAppsBefore = 0;
    public int numAppsAfter = 0;
    private boolean earnedCurrency = false;
    boolean show_admob_interstitial = true;
    public IabHelper.OnIabPurchaseFinishedListener mPurchaseFinishedListener = new IabHelper.OnIabPurchaseFinishedListener() { // from class: com.r7games.slotpantanal.PainelScreen.1
        @Override // com.r7games.slotpantanal.utils.IabHelper.OnIabPurchaseFinishedListener
        public void onIabPurchaseFinished(IabResult result, Purchase purchase) {
            if (result.isFailure()) {
                Debug.i("Purchase has failed");
                Log.i("BILLING", "Falhou a compra");
                return;
            }
            PainelScreen.this.orderId = purchase.getOrderId();
            PainelScreen.this.game = purchase.getPackageName();
            Thread localThread = new Thread(new Runnable() { // from class: com.r7games.slotpantanal.PainelScreen.1.1
                @Override // java.lang.Runnable
                public void run() {
                    PainelScreen.this.util.SendMessageArray("salvartransacao.php?idprod=" + PainelScreen.this.dispositivo + "&orderid=" + PainelScreen.this.orderId + "&game=" + PainelScreen.this.game);
                }
            });
            localThread.start();
            Log.i("BILLING", "SKU: " + purchase.getSku());
            PainelScreen.this.consumeItem();
        }
    };
    IabHelper.QueryInventoryFinishedListener mReceivedInventoryListener = new IabHelper.QueryInventoryFinishedListener() { // from class: com.r7games.slotpantanal.PainelScreen.2
        @Override // com.r7games.slotpantanal.utils.IabHelper.QueryInventoryFinishedListener
        public void onQueryInventoryFinished(IabResult result, Inventory inventory) {
            if (result.isFailure()) {
                Log.i("BILLING", "FALHA: " + result.getMessage());
                return;
            }
            List<String> sku = new ArrayList<>();
            sku.add(PainelScreen.credits_50000);
            sku.add(PainelScreen.credits_100000);
            sku.add(PainelScreen.credits_150000);
            sku.add(PainelScreen.credits_300000);
            sku.add(PainelScreen.credits_500000);
            for (String curProdId : sku) {
                Purchase purchase = inventory.getPurchase(curProdId);
                if (purchase != null) {
                    Log.i("BILLING", "ITEM: " + curProdId);
                    if (curProdId.equalsIgnoreCase(PainelScreen.credits_50000)) {
                        PainelScreen.this.credits_buy = 50000;
                    } else if (curProdId.equalsIgnoreCase(PainelScreen.credits_100000)) {
                        PainelScreen.this.credits_buy = 100000;
                    } else if (curProdId.equalsIgnoreCase(PainelScreen.credits_150000)) {
                        PainelScreen.this.credits_buy = 150000;
                    } else if (curProdId.equalsIgnoreCase(PainelScreen.credits_300000)) {
                        PainelScreen.this.credits_buy = 300000;
                    } else if (curProdId.equalsIgnoreCase(PainelScreen.credits_500000)) {
                        PainelScreen.this.credits_buy = 500000;
                    }
                    Log.i("BILLING", "CREDITS_BUY: " + PainelScreen.this.credits_buy);
                    PainelScreen.this.mHelper.consumeAsync(purchase, PainelScreen.this.mConsumeFinishedListener);
                }
            }
        }
    };
    IabHelper.OnConsumeFinishedListener mConsumeFinishedListener = new IabHelper.OnConsumeFinishedListener() { // from class: com.r7games.slotpantanal.PainelScreen.3
        @Override // com.r7games.slotpantanal.utils.IabHelper.OnConsumeFinishedListener
        public void onConsumeFinished(Purchase purchase, IabResult result) {
            if (result.isSuccess()) {
                PainelScreen.this.creditoAtual = PainelScreen.this.dbase.GetCredito() + PainelScreen.this.credits_buy;
                PainelScreen.this.dbase.SetCredito(PainelScreen.this.creditoAtual);
                Log.i("BILLING", "RECOMPENSA DADA: " + PainelScreen.this.creditoAtual);
                if (PainelScreen.this.idioma.equalsIgnoreCase("pt")) {
                    PainelScreen.this.titulo = "Parabéns você comprou " + PainelScreen.this.credits_buy + " Créditos";
                    PainelScreen.this.msg = "Seus créditos foram computados com sucesso!";
                } else if (PainelScreen.this.idioma.equalsIgnoreCase("es")) {
                    PainelScreen.this.titulo = "Enhorabuena que compraron " + PainelScreen.this.credits_buy + " créditos";
                    PainelScreen.this.msg = "Sus créditos se han calculado correctamente!";
                } else {
                    PainelScreen.this.titulo = "Congratulations you bought " + PainelScreen.this.credits_buy + " credits";
                    PainelScreen.this.msg = "His credits have been successfully computed!";
                }
                PainelScreen.this.mensagem();
                PainelScreen.this.t.send(new HitBuilders.EventBuilder().setCategory("Distribuição Créditos").setAction("Comprou " + PainelScreen.this.credits_buy + " Creditos").setLabel("Ofertas de venda").setValue(PainelScreen.this.credits_buy).build());
                PainelScreen.this.credits_buy = 0;
                return;
            }
            Log.i("BILLING", "FALHA RECOMPENSA");
        }
    };

    public void DrawBacground() {
        this.scene.attachChild(this.background);
    }

    public void DrawButtons() {
        for (int i = 0; i < 5; i++) {
            this.scene.attachChild(this.button[i]);
            this.scene.registerTouchArea(this.button[i]);
        }
    }

    public void InitBackground() {
        this.backgroundAtlas = new BitmapTextureAtlas(1024, 512, TextureOptions.DEFAULT);
        this.backgroundRegion = BitmapTextureAtlasTextureRegionFactory.createFromAsset(this.backgroundAtlas, this, "painel.png", 0, 0);
        this.mEngine.getTextureManager().loadTexture(this.backgroundAtlas);
        this.background = new Sprite(BitmapDescriptorFactory.HUE_RED, BitmapDescriptorFactory.HUE_RED, this.backgroundRegion);
    }

    public void mensagem() {
        runOnUpdateThread(new Runnable() { // from class: com.r7games.slotpantanal.PainelScreen.4
            @Override // java.lang.Runnable
            public void run() {
                PainelScreen.this.runOnUiThread(new Runnable() { // from class: com.r7games.slotpantanal.PainelScreen.4.1
                    @Override // java.lang.Runnable
                    public void run() {
                        String botao;
                        PainelScreen.this.hide_navigation_bar();
                        if (!PainelScreen.this.idioma.equalsIgnoreCase("pt")) {
                            if (PainelScreen.this.idioma.equalsIgnoreCase("es")) {
                                botao = "Cerca";
                            } else {
                                botao = "Close";
                            }
                        } else {
                            botao = "Fechar";
                        }
                        AlertDialog.Builder builder = new AlertDialog.Builder(PainelScreen.this);
                        builder.setTitle(PainelScreen.this.titulo).setMessage(PainelScreen.this.msg).setNeutralButton(botao, (DialogInterface.OnClickListener) null).show();
                    }
                });
            }
        });
    }

    @Override // org.anddev.andengine.ui.activity.BaseGameActivity, android.app.Activity, android.view.Window.Callback
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        if (hasFocus) {
            hide_navigation_bar();
        }
    }

    public void InitButtons() {
        this.buttonAtlas = new BitmapTextureAtlas(512, 64, TextureOptions.DEFAULT);
        this.buttonRegion = BitmapTextureAtlasTextureRegionFactory.createFromAsset(this.buttonAtlas, this, "buttonp.png", 0, 0);
        this.mEngine.getTextureManager().loadTexture(this.buttonAtlas);
        this.button = new Sprite[7];
        this.button[0] = new Sprite(292.0f, 238.0f, this.buttonRegion) { // from class: com.r7games.slotpantanal.PainelScreen.5
            @Override // org.anddev.andengine.entity.shape.Shape, org.anddev.andengine.entity.scene.Scene.ITouchArea
            public boolean onAreaTouched(TouchEvent paramAnonymousTouchEvent, float paramAnonymousFloat1, float paramAnonymousFloat2) {
                if (paramAnonymousTouchEvent.getAction() == 0) {
                    PainelScreen.this.startActivity(new Intent(PainelScreen.this, (Class<?>) MainActivity.class));
                }
                PainelScreen.this.finish();
                return false;
            }
        };
        this.button[1] = new Sprite(61.0f, 327.0f, this.buttonRegion) { // from class: com.r7games.slotpantanal.PainelScreen.6
            @Override // org.anddev.andengine.entity.shape.Shape, org.anddev.andengine.entity.scene.Scene.ITouchArea
            public boolean onAreaTouched(TouchEvent paramAnonymousTouchEvent, float paramAnonymousFloat1, float paramAnonymousFloat2) {
                if (paramAnonymousTouchEvent.getAction() == 0) {
                    PainelScreen.this.ShowCreditoScren();
                    return false;
                }
                return false;
            }
        };
        this.button[2] = new Sprite(398.0f, 324.0f, this.buttonRegion) { // from class: com.r7games.slotpantanal.PainelScreen.7
            @Override // org.anddev.andengine.entity.shape.Shape, org.anddev.andengine.entity.scene.Scene.ITouchArea
            public boolean onAreaTouched(TouchEvent paramAnonymousTouchEvent, float paramAnonymousFloat1, float paramAnonymousFloat2) {
                if (paramAnonymousTouchEvent.getAction() == 0) {
                    PainelScreen.this.ShowAjudaScren();
                    return false;
                }
                return false;
            }
        };
        this.button[3] = new Sprite(527.0f, 325.0f, this.buttonRegion) { // from class: com.r7games.slotpantanal.PainelScreen.8
            @Override // org.anddev.andengine.entity.shape.Shape, org.anddev.andengine.entity.scene.Scene.ITouchArea
            public boolean onAreaTouched(TouchEvent paramAnonymousTouchEvent, float paramAnonymousFloat1, float paramAnonymousFloat2) {
                if (paramAnonymousTouchEvent.getAction() == 0) {
                    PainelScreen.this.show_leaderboard = true;
                    if (!PainelScreen.this.getApiClient().isConnected()) {
                        PainelScreen.this.beginUserInitiatedSignIn();
                        return false;
                    }
                    PainelScreen.this.onSignInSucceeded();
                    return false;
                }
                return false;
            }
        };
        this.button[4] = new Sprite(573.0f, 415.0f, this.buttonRegion) { // from class: com.r7games.slotpantanal.PainelScreen.9
            @Override // org.anddev.andengine.entity.shape.Shape, org.anddev.andengine.entity.scene.Scene.ITouchArea
            public boolean onAreaTouched(TouchEvent paramAnonymousTouchEvent, float paramAnonymousFloat1, float paramAnonymousFloat2) {
                try {
                    PainelScreen.this.getIntent().putExtra("notload", false);
                } catch (Exception e) {
                }
                PainelScreen.this.finish();
                System.exit(0);
                return false;
            }
        };
    }

    void add_admob_interstitial() {
        this.adMob_interstitial = new InterstitialAd(this);
        this.adMob_interstitial.setAdUnitId(getString(R.string.adMob_painel));
        AdRequest.Builder builder = new AdRequest.Builder();
        this.adMob_interstitial.loadAd(builder.build());
    }

    @Override // com.r7games.slotpantanal.google.services.GBaseGameActivity, org.anddev.andengine.ui.IGameInterface
    public void onLoadComplete() {
        try {
            add_admob_interstitial();
        } catch (Exception localException) {
            localException.printStackTrace();
        }
        try {
            connectToTapjoy();
        } catch (Exception e) {
            this.tapjoyOn = false;
        }
        this.t = ((GoogleAnalyticsApp) getApplication()).getTracker(GoogleAnalyticsApp.TrackerName.APP_TRACKER);
        this.t.setScreenName("PainelScreen");
        this.t.send(new HitBuilders.AppViewBuilder().build());
        Log.i(TAG, "Iniciando");
        TelephonyManager tm = (TelephonyManager) getSystemService("phone");
        this.tmCoutry = this.sp.getString("pais", "default");
        if (this.tmCoutry.equalsIgnoreCase("default")) {
            if (tm.getNetworkCountryIso().equalsIgnoreCase("br") || this.idioma.equalsIgnoreCase("pt")) {
                this.ed.putString("pais", "br");
                this.ed.apply();
            } else {
                this.ed.putString("pais", "us");
                this.ed.apply();
            }
        }
        try {
            HeyzapAds.start("3842e57fd588a06d155655b041bd3099", this);
            IncentivizedAd.fetch();
            setupCallbacks();
        } catch (Exception e2) {
            e2.printStackTrace();
        }
        this.creditoAtual = this.dbase.GetCredito();
        try {
            this.dispositivo = this.dbase.GetPwd();
            if (this.dispositivo.equalsIgnoreCase("13251aad66f09546d33aaac8222adbc0") || this.dispositivo == null || this.dispositivo.equalsIgnoreCase("PN")) {
                String tmDevice = tm.getDeviceId();
                String tmSerial = tm.getSimSerialNumber();
                String androidId = Settings.Secure.getString(getContentResolver(), TapjoyConstants.TJC_ANDROID_ID);
                this.dispositivo = String.valueOf("PN") + this.util.md5(String.valueOf(tmDevice) + tmSerial + androidId);
                this.dbase.WriteNewPasswd(this.dispositivo);
            }
        } catch (Exception localException2) {
            localException2.printStackTrace();
            this.dispositivo = String.valueOf("PN") + this.util.md5(Settings.Secure.getString(getContentResolver(), TapjoyConstants.TJC_ANDROID_ID));
            this.dbase.WriteNewPasswd(this.dispositivo);
        }
        if (this.dispositivo == null || this.dispositivo.equalsIgnoreCase("null") || this.dispositivo.equalsIgnoreCase("PN")) {
            this.dispositivo = String.valueOf("PN") + this.util.md5(Settings.Secure.getString(getContentResolver(), TapjoyConstants.TJC_ANDROID_ID));
            if (this.dispositivo == null || this.dispositivo.equalsIgnoreCase("null") || this.dispositivo.equalsIgnoreCase("PN")) {
                Calendar c = Calendar.getInstance();
                SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
                String strDate = sdf.format(c.getTime());
                this.dispositivo = String.valueOf("PN") + this.util.md5(strDate);
            }
            this.dbase.WriteNewPasswd(this.dispositivo);
        }
        EnviarLeitura(0);
        String payload = getIntent().getStringExtra(Tapjoy.INTENT_EXTRA_PUSH_PAYLOAD);
        if (payload != null) {
            Log.i(TAG, "Tapjoy push notification with payload: " + payload);
            this.creditoAtual += Integer.valueOf(payload).intValue();
            this.dbase.SetCredito(this.creditoAtual);
        }
        runOnUiThread(new Runnable() { // from class: com.r7games.slotpantanal.PainelScreen.10
            @Override // java.lang.Runnable
            public void run() {
                PainelScreen.this.adView = (AdView) PainelScreen.this.findViewById(R.id.adViewId);
                AdRequest adRequest = new AdRequest.Builder().build();
                PainelScreen.this.adView.loadAd(adRequest);
                PainelScreen.this.adView.setBackgroundColor(0);
            }
        });
        this.mEngine.registerUpdateHandler(new TimerHandler(0.01f, new ITimerCallback() { // from class: com.r7games.slotpantanal.PainelScreen.11
            @Override // org.anddev.andengine.engine.handler.timer.ITimerCallback
            public void onTimePassed(TimerHandler paramAnonymousTimerHandler) {
                PainelScreen.this.mEngine.unregisterUpdateHandler(paramAnonymousTimerHandler);
                PainelScreen.this.scene = new Scene();
                PainelScreen.this.CreditoScene = new Scene();
                PainelScreen.this.AjudaScene = new Scene();
                PainelScreen.this.InitAll();
                PainelScreen.this.DrawAll();
                if (PainelScreen.this.notload.booleanValue()) {
                    PainelScreen.this.mEngine.setScene(PainelScreen.this.scene);
                    return;
                }
                PainelScreen.this.mRunnable = new Runnable() { // from class: com.r7games.slotpantanal.PainelScreen.11.1
                    @Override // java.lang.Runnable
                    public void run() {
                        PainelScreen.this.mEngine.setScene(PainelScreen.this.scene);
                    }
                };
                PainelScreen.this.mHandler.postDelayed(PainelScreen.this.mRunnable, 2000L);
            }
        }));
    }

    protected void ShowCreditoScren() {
        this.mEngine.setScene(this.CreditoScene);
        runOnUpdateThread(new Runnable() { // from class: com.r7games.slotpantanal.PainelScreen.12
            @Override // java.lang.Runnable
            public void run() {
                PainelScreen.this.CreditoScene.detachSelf();
            }
        });
        try {
            this.CreditoScene.attachChild(this.bgCredito);
        } catch (IllegalStateException e) {
            Log.d("ERROR CLEAN", "ME RECUPEREI DE UM ERRO");
            runOnUpdateThread(new Runnable() { // from class: com.r7games.slotpantanal.PainelScreen.13
                @Override // java.lang.Runnable
                public void run() {
                    PainelScreen.this.CreditoScene.detachChild(PainelScreen.this.bgCredito);
                    PainelScreen.this.CreditoScene.attachChild(PainelScreen.this.bgCredito);
                }
            });
        }
        for (int i = 0; i < 9; i++) {
            this.CreditoScene.detachChild(this.buttonCredito[i]);
            this.CreditoScene.attachChild(this.buttonCredito[i]);
            this.CreditoScene.registerTouchArea(this.buttonCredito[i]);
        }
        if (this.tmCoutry.equalsIgnoreCase("br")) {
            giveARewardBr();
        }
    }

    protected void exitCreditoScreen() {
        this.numAppsBefore = 0;
        try {
            runOnUpdateThread(new Runnable() { // from class: com.r7games.slotpantanal.PainelScreen.14
                @Override // java.lang.Runnable
                public void run() {
                    PainelScreen.this.CreditoScene.clearTouchAreas();
                    PainelScreen.this.CreditoScene.detachChildren();
                    PainelScreen.this.CreditoScene.detachSelf();
                }
            });
            this.mEngine.setScene(this.scene);
        } catch (Exception e) {
            e.printStackTrace();
        }
        try {
            if (this.tapjoyOn) {
                Tapjoy.getCurrencyBalance(this);
            }
        } catch (Exception e2) {
            e2.printStackTrace();
        }
    }

    protected void ShowAjudaScren() {
        this.mEngine.setScene(this.AjudaScene);
        runOnUpdateThread(new Runnable() { // from class: com.r7games.slotpantanal.PainelScreen.15
            @Override // java.lang.Runnable
            public void run() {
                PainelScreen.this.AjudaScene.detachSelf();
            }
        });
        try {
            this.AjudaScene.attachChild(this.bgAjuda);
        } catch (IllegalStateException e) {
            Log.d("ERROR CLEAN", "ME RECUPEREI DE UM ERRO");
            runOnUpdateThread(new Runnable() { // from class: com.r7games.slotpantanal.PainelScreen.16
                @Override // java.lang.Runnable
                public void run() {
                    PainelScreen.this.AjudaScene.detachChild(PainelScreen.this.bgAjuda);
                    PainelScreen.this.AjudaScene.attachChild(PainelScreen.this.bgAjuda);
                }
            });
        }
        for (int i = 0; i < 3; i++) {
            this.AjudaScene.detachChild(this.AjudaButton[i]);
            this.AjudaScene.attachChild(this.AjudaButton[i]);
            this.AjudaScene.registerTouchArea(this.AjudaButton[i]);
        }
    }

    protected void exitAjudaScreen() {
        runOnUpdateThread(new Runnable() { // from class: com.r7games.slotpantanal.PainelScreen.17
            @Override // java.lang.Runnable
            public void run() {
                PainelScreen.this.AjudaScene.clearTouchAreas();
                PainelScreen.this.AjudaScene.detachChildren();
                PainelScreen.this.AjudaScene.detachSelf();
            }
        });
        this.mEngine.setScene(this.scene);
    }

    protected void DrawAll() {
        DrawBacground();
        DrawButtons();
    }

    protected void InitAll() {
        InitBackground();
        InitButtons();
        InitCredito();
        InitAjuda();
    }

    @Override // com.r7games.slotpantanal.google.services.GBaseGameActivity, android.app.Activity
    protected void onStart() {
        super.onStart();
        GoogleAnalytics.getInstance(this).reportActivityStart(this);
        Tapjoy.onActivityStart(this);
    }

    @Override // com.r7games.slotpantanal.google.services.GBaseGameActivity, android.app.Activity
    protected void onStop() {
        super.onStop();
        GoogleAnalytics.getInstance(this).reportActivityStop(this);
        Tapjoy.onActivityStop(this);
    }

    public boolean isOnline() {
        ConnectivityManager localConnectivityManager = (ConnectivityManager) getSystemService("connectivity");
        return localConnectivityManager.getActiveNetworkInfo() != null && localConnectivityManager.getActiveNetworkInfo().isConnectedOrConnecting();
    }

    @Override // org.anddev.andengine.ui.activity.BaseGameActivity, android.app.Activity
    public void onResume() {
        super.onResume();
        hide_navigation_bar();
    }

    @Override // com.r7games.slotpantanal.google.services.GBaseGameActivity, org.anddev.andengine.ui.IGameInterface
    public Engine onLoadEngine() {
        this.camera = new Camera(BitmapDescriptorFactory.HUE_RED, BitmapDescriptorFactory.HUE_RED, 800.0f, 480.0f);
        return new Engine(new EngineOptions(true, EngineOptions.ScreenOrientation.LANDSCAPE, new FillResolutionPolicy(), this.camera).setNeedsSound(true));
    }

    @Override // com.r7games.slotpantanal.google.services.GBaseGameActivity, org.anddev.andengine.ui.IGameInterface
    public void onLoadResources() {
        this.idioma = this.util.idioma();
        getWindow().setFlags(1024, 1024);
        hide_navigation_bar();
        this.splashAtlas = new BitmapTextureAtlas(1024, 1024);
        this.splashRegion = BitmapTextureAtlasTextureRegionFactory.createFromAsset(this.splashAtlas, this, "splash_logo.png", 0, 0);
        this.mEngine.getTextureManager().loadTexture(this.splashAtlas);
        this.splash = new Sprite(BitmapDescriptorFactory.HUE_RED, BitmapDescriptorFactory.HUE_RED, this.splashRegion);
    }

    @TargetApi(19)
    void hide_navigation_bar() {
        if (Build.VERSION.SDK_INT >= 19) {
            getWindow().getDecorView().setSystemUiVisibility(5894);
        }
    }

    @Override // com.r7games.slotpantanal.google.services.GBaseGameActivity, org.anddev.andengine.ui.IGameInterface
    public Scene onLoadScene() {
        this.splashScene = new Scene();
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            this.notload = Boolean.valueOf(extras.getBoolean("notload"));
            if (!this.notload.booleanValue()) {
                this.splashScene.attachChild(this.splash);
            }
        } else {
            this.splashScene.attachChild(this.splash);
        }
        return this.splashScene;
    }

    public String getSoftwareVersion() {
        PackageInfo packageInfo = null;
        try {
            packageInfo = getPackageManager().getPackageInfo(getPackageName(), 0);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return packageInfo.versionName;
    }

    public void EnviarLeitura(final int action) {
        Thread localThread = new Thread(new Runnable() { // from class: com.r7games.slotpantanal.PainelScreen.18
            @Override // java.lang.Runnable
            public void run() {
                try {
                    Double localDouble = Double.valueOf(Math.round(PainelScreen.this.dbase.GetAcumulado(0).doubleValue()));
                    String acum1 = String.valueOf(localDouble.intValue());
                    Double localDouble2 = Double.valueOf(Math.round(PainelScreen.this.dbase.GetAcumulado(1).doubleValue()));
                    String acum2 = String.valueOf(localDouble2.intValue());
                    Double localDouble3 = Double.valueOf(Math.round(PainelScreen.this.dbase.GetAcumulado(2).doubleValue()));
                    String acum3 = String.valueOf(localDouble3.intValue());
                    Log.i(PainelScreen.TAG, "Enviando Leitura");
                    String PhoneModel = "Smartphone";
                    try {
                        PhoneModel = Build.MODEL;
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    String[] retorno = PainelScreen.this.util.SendMessageArray("verificar_halloween.php?idprod=" + PainelScreen.this.dispositivo + "&halloween=" + PainelScreen.this.creditoAtual + "&action=" + action + "&emails=NOVA VERSÃO&travar=" + PainelScreen.this.sp.getInt("travar", 0) + "&versao=" + PainelScreen.this.getSoftwareVersion() + "&acum1=" + acum1 + "&acum2=" + acum2 + "&acum3=" + acum3 + "&player=" + PainelScreen.this.sp.getString("player", "null") + "&score2=" + PainelScreen.this.score + "&jogadascore=0&totaljogadas=0&model=" + PhoneModel);
                    Log.i("MATEMATICA", "SINC DESBLOQUEIO MED: " + retorno[9]);
                    Log.i("MATEMATICA", "SINC DESBLOQUEIO MAX: " + retorno[10]);
                    Log.i("MATEMATICA", "SINC SORTEIO: " + retorno[7]);
                    if (retorno[11].equalsIgnoreCase("OK")) {
                        PainelScreen.this.ed.putInt("travar", 0);
                        PainelScreen.this.ed.apply();
                    }
                    if (retorno[9].equalsIgnoreCase("OK")) {
                        PainelScreen.this.ed.putBoolean("med", true);
                        PainelScreen.this.ed.putInt("sorteio", Integer.valueOf(retorno[7]).intValue());
                        PainelScreen.this.ed.apply();
                        Log.i("MATEMATICA", "DESBLOQUEADO MED");
                    } else if (retorno[10].equalsIgnoreCase("OK")) {
                        PainelScreen.this.ed.putBoolean("max", true);
                        PainelScreen.this.ed.putInt("sorteio", Integer.valueOf(retorno[7]).intValue());
                        PainelScreen.this.ed.apply();
                        Log.i("MATEMATICA", "DESBLOQUEADO MAX");
                    }
                    PainelScreen.this.dbase.SetBloqueado(Integer.valueOf(retorno[4]).intValue());
                    PainelScreen.this.dbase.SetIncrentoDificul(Integer.valueOf(retorno[5]).intValue());
                    if (action == 0 && retorno[0].equalsIgnoreCase("OK")) {
                        int valor = Integer.valueOf(retorno[1]).intValue();
                        PainelScreen.this.creditoAtual += valor;
                        PainelScreen.this.dbase.SetCredito(PainelScreen.this.creditoAtual);
                        PainelScreen.this.titulo = retorno[2];
                        PainelScreen.this.msg = retorno[3];
                        PainelScreen.this.mensagem();
                    }
                } catch (Exception e2) {
                }
            }
        });
        localThread.start();
    }

    @Override // com.r7games.slotpantanal.google.services.GBaseGameActivity, org.anddev.andengine.ui.activity.LayoutGameActivity
    protected int getLayoutID() {
        return R.layout.game_layout;
    }

    @Override // com.r7games.slotpantanal.google.services.GBaseGameActivity, org.anddev.andengine.ui.activity.LayoutGameActivity
    protected int getRenderSurfaceViewID() {
        return R.id.SurfaceViewId;
    }

    @Override // com.r7games.slotpantanal.google.services.GBaseGameActivity, com.r7games.slotpantanal.google.services.GGameHelper.GameHelperListener
    public void onSignInSucceeded() {
        if (this.show_leaderboard) {
            this.score = this.sp.getLong("score_halloween", 0L);
            Games.Leaderboards.submitScore(getApiClient(), getString(R.string.leaderboard_highscore_total), this.score);
            startActivityForResult(Games.Leaderboards.getLeaderboardIntent(getApiClient(), getString(R.string.leaderboard_highscore_total)), 9999);
        }
        if (this.show_conquistas) {
            startActivityForResult(Games.Achievements.getAchievementsIntent(getApiClient()), 1);
            this.show_conquistas = false;
        }
        Games.Leaderboards.loadCurrentPlayerLeaderboardScore(getApiClient(), getString(R.string.leaderboard_highscore_total), 2, 0).setResultCallback(new ResultCallback<Leaderboards.LoadPlayerScoreResult>() { // from class: com.r7games.slotpantanal.PainelScreen.19
            @Override // com.google.android.gms.common.api.ResultCallback
            public void onResult(Leaderboards.LoadPlayerScoreResult scoreResult) {
                if (scoreResult != null) {
                    try {
                        if (scoreResult.getStatus().getStatusCode() == 0 && scoreResult.getScore() != null) {
                            Long scorePlay = Long.valueOf(scoreResult.getScore().getRawScore());
                            if (scorePlay.longValue() < PainelScreen.this.score) {
                                Games.Leaderboards.submitScore(PainelScreen.this.getApiClient(), PainelScreen.this.getString(R.string.leaderboard_highscore_total), PainelScreen.this.score);
                            } else {
                                PainelScreen.this.score = scorePlay.longValue();
                                PainelScreen.this.ed.putLong("score_halloween", scorePlay.longValue());
                                PainelScreen.this.ed.apply();
                            }
                        }
                    } catch (Exception localException) {
                        localException.printStackTrace();
                    }
                }
            }
        });
        this.show_leaderboard = false;
        try {
            this.playerId = Games.Players.getCurrentPlayerId(getApiClient());
            if (this.playerId != null) {
                this.ed.putString("player", this.playerId);
                this.ed.apply();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        EnviarLeitura(0);
    }

    @Override // org.anddev.andengine.ui.activity.BaseGameActivity, android.app.Activity
    public void onDestroy() {
        super.onDestroy();
        HeyzapAds.shutdown();
        if (this.mHelper != null) {
            this.mHelper.dispose();
        }
        this.mHelper = null;
    }

    @Override // com.r7games.slotpantanal.google.services.GBaseGameActivity, com.r7games.slotpantanal.google.services.GGameHelper.GameHelperListener
    public void onSignInFailed() {
        this.show_leaderboard = false;
        this.show_conquistas = false;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void connectToTapjoy() {
        Hashtable<String, Object> connectFlags = new Hashtable<>();
        connectFlags.put(TapjoyConnectFlag.ENABLE_LOGGING, "true");
        Tapjoy.setGcmSender("408072206894");
        Tapjoy.connect(getApplicationContext(), "Zqor2d7vQL2ZJCjPFPDO9wECNvG8c4zLk2qsBs5uvlnb0RNlkDxHR3UXzfMG", connectFlags, new TJConnectListener() { // from class: com.r7games.slotpantanal.PainelScreen.20
            @Override // com.tapjoy.TJConnectListener
            public void onConnectSuccess() {
                PainelScreen.this.onConnectSuccess();
            }

            @Override // com.tapjoy.TJConnectListener
            public void onConnectFailure() {
                PainelScreen.this.onConnectFail();
            }
        });
    }

    public void onConnectSuccess() {
        this.tapjoyOn = true;
        callShowReward();
        callShowOffers();
        callNoShowReward();
        Tapjoy.getCurrencyBalance(this);
        Tapjoy.setEarnedCurrencyListener(new TJEarnedCurrencyListener() { // from class: com.r7games.slotpantanal.PainelScreen.21
            @Override // com.tapjoy.TJEarnedCurrencyListener
            public void onEarnedCurrency(String currencyName, int amount) {
                PainelScreen.this.earnedCurrency = true;
                if (PainelScreen.this.idioma.equalsIgnoreCase("pt")) {
                    PainelScreen.this.titulo = "Parabéns você ganhou Créditos";
                    PainelScreen.this.msg = "Por completar a oferta acabou de ganhar " + amount + " Créditos";
                } else if (PainelScreen.this.idioma.equalsIgnoreCase("es")) {
                    PainelScreen.this.titulo = "Enhorabuena que ganaron Créditos";
                    PainelScreen.this.msg = "Para completar la oferta acaba de ganar " + amount + " Créditos";
                } else {
                    PainelScreen.this.titulo = "Congratulations you won Credits";
                    PainelScreen.this.msg = "For completing the offer just won " + amount + " Credits";
                }
                PainelScreen.this.mensagem();
            }
        });
        Tapjoy.setVideoListener(new TJVideoListener() { // from class: com.r7games.slotpantanal.PainelScreen.22
            @Override // com.tapjoy.TJVideoListener
            public void onVideoStart() {
                Log.i(PainelScreen.TAG, "video has started");
            }

            @Override // com.tapjoy.TJVideoListener
            public void onVideoError(int statusCode) {
                Log.i(PainelScreen.TAG, "there was an error with the video: " + statusCode);
            }

            @Override // com.tapjoy.TJVideoListener
            public void onVideoComplete() {
                Log.i(PainelScreen.TAG, "video has completed");
                Tapjoy.getCurrencyBalance(PainelScreen.this);
            }
        });
    }

    public void onConnectFail() {
        this.tapjoyOn = false;
        this.tentativa++;
        if (this.tentativa >= 3) {
            this.tentativa = 0;
        }
        Log.e(TAG, "Tapjoy connect call failed");
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void callShowOffers() {
        this.offerwallPlacement = new TJPlacement(this, "ShowOffers", new TJPlacementListener() { // from class: com.r7games.slotpantanal.PainelScreen.23
            @Override // com.tapjoy.TJPlacementListener
            public void onRequestSuccess(TJPlacement placement) {
                Log.i(PainelScreen.TAG, "onRequestSuccess for placement " + placement.getName());
                if (!placement.isContentAvailable()) {
                    Log.i(PainelScreen.TAG, "No Offerwall content available");
                }
            }

            @Override // com.tapjoy.TJPlacementListener
            public void onRequestFailure(TJPlacement placement, TJError error) {
                Log.i(PainelScreen.TAG, "Offerwall error: " + error.message);
            }

            @Override // com.tapjoy.TJPlacementListener
            public void onContentReady(TJPlacement placement) {
                Log.i(PainelScreen.TAG, "onContentReady for placement " + placement.getName());
            }

            @Override // com.tapjoy.TJPlacementListener
            public void onContentShow(TJPlacement placement) {
                Log.i(PainelScreen.TAG, "onContentShow for placement " + placement.getName());
            }

            @Override // com.tapjoy.TJPlacementListener
            public void onContentDismiss(TJPlacement placement) {
                Log.i(PainelScreen.TAG, "onContentDismiss for placement " + placement.getName());
            }

            @Override // com.tapjoy.TJPlacementListener
            public void onPurchaseRequest(TJPlacement placement, TJActionRequest request, String productId) {
            }

            @Override // com.tapjoy.TJPlacementListener
            public void onRewardRequest(TJPlacement placement, TJActionRequest request, String itemId, int quantity) {
            }
        });
        this.offerwallPlacement.requestContent();
    }

    private void callNoShowReward() {
        this.NoShowPlacement = new TJPlacement(this, "NoShowReward", new TJPlacementListener() { // from class: com.r7games.slotpantanal.PainelScreen.24
            @Override // com.tapjoy.TJPlacementListener
            public void onRequestSuccess(TJPlacement placement) {
                Log.i(PainelScreen.TAG, "onRequestSuccess for placement " + placement.getName());
                if (!placement.isContentAvailable()) {
                    Log.i(PainelScreen.TAG, "No diario content available");
                }
            }

            @Override // com.tapjoy.TJPlacementListener
            public void onRequestFailure(TJPlacement placement, TJError error) {
                Log.i(PainelScreen.TAG, "Reward error: " + error.message);
            }

            @Override // com.tapjoy.TJPlacementListener
            public void onContentReady(TJPlacement placement) {
                Log.i(PainelScreen.TAG, "onContentReady for placement " + placement.getName());
            }

            @Override // com.tapjoy.TJPlacementListener
            public void onContentShow(TJPlacement placement) {
                Log.i(PainelScreen.TAG, "onContentShow for placement " + placement.getName());
            }

            @Override // com.tapjoy.TJPlacementListener
            public void onContentDismiss(TJPlacement placement) {
                Log.i(PainelScreen.TAG, "onContentDismiss for placement " + placement.getName());
            }

            @Override // com.tapjoy.TJPlacementListener
            public void onPurchaseRequest(TJPlacement placement, TJActionRequest request, String productId) {
            }

            @Override // com.tapjoy.TJPlacementListener
            public void onRewardRequest(TJPlacement placement, TJActionRequest request, String itemId, int quantity) {
                PainelScreen.this.creditoAtual += quantity;
                PainelScreen.this.dbase.SetCredito(PainelScreen.this.creditoAtual);
                PainelScreen.this.t.send(new HitBuilders.EventBuilder().setCategory("Distribuição Créditos").setAction("Ganhou " + quantity + " Creditos").setLabel("Oferta Extra").setValue(quantity).build());
            }
        });
        this.NoShowPlacement.requestContent();
    }

    private void callShowReward() {
        this.extraPlacement = new TJPlacement(this, "ShowReward", new TJPlacementListener() { // from class: com.r7games.slotpantanal.PainelScreen.25
            @Override // com.tapjoy.TJPlacementListener
            public void onRequestSuccess(TJPlacement placement) {
                Log.i(PainelScreen.TAG, "onRequestSuccess for placement " + placement.getName());
                if (!placement.isContentAvailable()) {
                    Log.i(PainelScreen.TAG, "No Offerwall content available");
                }
            }

            @Override // com.tapjoy.TJPlacementListener
            public void onRequestFailure(TJPlacement placement, TJError error) {
                Log.i(PainelScreen.TAG, "Reward error: " + error.message);
            }

            @Override // com.tapjoy.TJPlacementListener
            public void onContentReady(TJPlacement placement) {
                Log.i(PainelScreen.TAG, "onContentReady for placement " + placement.getName());
                placement.showContent();
            }

            @Override // com.tapjoy.TJPlacementListener
            public void onContentShow(TJPlacement placement) {
                Log.i(PainelScreen.TAG, "onContentShow for placement " + placement.getName());
            }

            @Override // com.tapjoy.TJPlacementListener
            public void onContentDismiss(TJPlacement placement) {
                Log.i(PainelScreen.TAG, "onContentDismiss for placement " + placement.getName());
            }

            @Override // com.tapjoy.TJPlacementListener
            public void onPurchaseRequest(TJPlacement placement, TJActionRequest request, String productId) {
            }

            @Override // com.tapjoy.TJPlacementListener
            public void onRewardRequest(TJPlacement placement, TJActionRequest request, String itemId, int quantity) {
                PainelScreen.this.creditoAtual += quantity;
                PainelScreen.this.dbase.SetCredito(PainelScreen.this.creditoAtual);
                PainelScreen.this.t.send(new HitBuilders.EventBuilder().setCategory("Distribuição Créditos").setAction("Ganhou " + quantity + " Creditos").setLabel("Oferta promocional").setValue(quantity).build());
            }
        });
        this.extraPlacement.requestContent();
    }

    private void callSpendCurrency(int valor) {
        Tapjoy.spendCurrency(valor, new TJSpendCurrencyListener() { // from class: com.r7games.slotpantanal.PainelScreen.26
            @Override // com.tapjoy.TJSpendCurrencyListener
            public void onSpendCurrencyResponse(String currencyName, int balance) {
            }

            @Override // com.tapjoy.TJSpendCurrencyListener
            public void onSpendCurrencyResponseFailure(String error) {
            }
        });
    }

    @Override // com.tapjoy.TJGetCurrencyBalanceListener
    public void onGetCurrencyBalanceResponse(String currencyName, int balance) {
        Log.i(TAG, "currencyName: " + currencyName);
        Log.i(TAG, "balance: " + balance);
        if (balance > 0) {
            this.creditoAtual += balance;
            this.dbase.SetCredito(this.creditoAtual);
            callSpendCurrency(balance);
            this.t.send(new HitBuilders.EventBuilder().setCategory("Distribuição Créditos").setAction("Ganhou " + balance + " Creditos").setLabel("Oferta TapJoy").setValue(balance).build());
        }
        if (this.earnedCurrency) {
            this.earnedCurrency = false;
        }
    }

    @Override // com.tapjoy.TJGetCurrencyBalanceListener
    public void onGetCurrencyBalanceResponseFailure(String error) {
    }

    @Override // com.tapjoy.TJPlacementListener
    public void onRequestSuccess(TJPlacement placement) {
        Log.i(TAG, "Tapjoy on request success, contentAvailable: " + placement.isContentAvailable());
    }

    @Override // com.tapjoy.TJPlacementListener
    public void onRequestFailure(TJPlacement placement, TJError error) {
        Log.i(TAG, "Tapjoy send event 'video_unit' failed with error: " + error.message);
    }

    @Override // com.tapjoy.TJPlacementListener
    public void onContentReady(TJPlacement placement) {
    }

    @Override // com.tapjoy.TJPlacementListener
    public void onContentShow(TJPlacement placement) {
    }

    @Override // com.tapjoy.TJPlacementListener
    public void onContentDismiss(TJPlacement placement) {
        Log.i(TAG, "Tapjoy direct play content did disappear");
        Tapjoy.getCurrencyBalance(this);
    }

    @Override // com.tapjoy.TJPlacementListener
    public void onPurchaseRequest(TJPlacement placement, TJActionRequest request, String productId) {
    }

    @Override // com.tapjoy.TJPlacementListener
    public void onRewardRequest(TJPlacement placement, TJActionRequest request, String itemId, int quantity) {
    }

    @Override // android.app.Activity
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        String payload = getIntent().getStringExtra(Tapjoy.INTENT_EXTRA_PUSH_PAYLOAD);
        if (payload != null) {
            Log.i(TAG, "Tapjoy push notification with payload: " + payload);
            this.creditoAtual += Integer.valueOf(payload).intValue();
            this.dbase.SetCredito(this.creditoAtual);
            this.t.send(new HitBuilders.EventBuilder().setCategory("Distribuição Créditos").setAction("Ganhou " + Integer.valueOf(payload) + " Creditos").setLabel("Oferta Push").setValue(Integer.valueOf(payload).intValue()).build());
        }
    }

    public void InitAjuda() {
        this.bgAjudaAtlas = new BitmapTextureAtlas(1024, 512, TextureOptions.DEFAULT);
        this.bgAjudaRegion = BitmapTextureAtlasTextureRegionFactory.createFromAsset(this.bgAjudaAtlas, this, "ajuda.png", 0, 0);
        this.mEngine.getTextureManager().loadTexture(this.bgAjudaAtlas);
        this.bgAjuda = new Sprite(BitmapDescriptorFactory.HUE_RED, BitmapDescriptorFactory.HUE_RED, this.bgAjudaRegion);
        this.AjudaButton = new Sprite[3];
        this.AjudaButton[0] = new Sprite(15.0f, 434.0f, this.buttonRegion) { // from class: com.r7games.slotpantanal.PainelScreen.27
            @Override // org.anddev.andengine.entity.shape.Shape, org.anddev.andengine.entity.scene.Scene.ITouchArea
            public boolean onAreaTouched(TouchEvent paramAnonymousTouchEvent, float paramAnonymousFloat1, float paramAnonymousFloat2) {
                if (paramAnonymousTouchEvent.getAction() == 0) {
                    PainelScreen.this.exitAjudaScreen();
                    return false;
                }
                return false;
            }
        };
        this.AjudaButton[1] = new Sprite(268.0f, 385.0f, this.btnSmallRegion) { // from class: com.r7games.slotpantanal.PainelScreen.28
            @Override // org.anddev.andengine.entity.shape.Shape, org.anddev.andengine.entity.scene.Scene.ITouchArea
            public boolean onAreaTouched(TouchEvent paramAnonymousTouchEvent, float paramAnonymousFloat1, float paramAnonymousFloat2) {
                if (paramAnonymousTouchEvent.getAction() == 0) {
                    PainelScreen.this.mail = new Intent("android.intent.action.SEND");
                }
                PainelScreen.this.mail.setType("message/rfc822");
                PainelScreen.this.mail.putExtra("android.intent.extra.EMAIL", new String[]{"r7developers@gmail.com"});
                PainelScreen.this.mail.putExtra("android.intent.extra.SUBJECT", "Help - Halloween Slot");
                try {
                    PainelScreen.this.startActivity(Intent.createChooser(PainelScreen.this.mail, "Send mail..."));
                } catch (ActivityNotFoundException e) {
                    Log.i("Mail", "Error Send Mail");
                }
                return false;
            }
        };
        this.AjudaButton[2] = new Sprite(711.0f, 258.0f, this.btnSmallRegion) { // from class: com.r7games.slotpantanal.PainelScreen.29
            @Override // org.anddev.andengine.entity.shape.Shape, org.anddev.andengine.entity.scene.Scene.ITouchArea
            public boolean onAreaTouched(TouchEvent paramAnonymousTouchEvent, float paramAnonymousFloat1, float paramAnonymousFloat2) {
                if (paramAnonymousTouchEvent.getAction() == 0) {
                    PainelScreen.this.uriUrl = Uri.parse("https://www.facebook.com/pages/R7-Developers/1589453101278736");
                }
                Intent launchBrowser = new Intent("android.intent.action.VIEW", PainelScreen.this.uriUrl);
                PainelScreen.this.startActivity(launchBrowser);
                return false;
            }
        };
    }

    public void InitCredito() {
        float f = 271.0f;
        float f2 = 316.0f;
        float f3 = 719.0f;
        this.bgCreditoAtlas = new BitmapTextureAtlas(1024, 512, TextureOptions.DEFAULT);
        this.bgCreditoRegion = BitmapTextureAtlasTextureRegionFactory.createFromAsset(this.bgCreditoAtlas, this, "creditos.png", 0, 0);
        this.mEngine.getTextureManager().loadTexture(this.bgCreditoAtlas);
        this.bgCredito = new Sprite(BitmapDescriptorFactory.HUE_RED, BitmapDescriptorFactory.HUE_RED, this.bgCreditoRegion);
        this.btnSmallAtlas = new BitmapTextureAtlas(64, 64, TextureOptions.DEFAULT);
        this.btnSmallRegion = BitmapTextureAtlasTextureRegionFactory.createFromAsset(this.btnSmallAtlas, this, "btnsmall.png", 0, 0);
        this.mEngine.getTextureManager().loadTexture(this.btnSmallAtlas);
        this.buttonCredito = new Sprite[9];
        this.buttonCredito[0] = new Sprite(15.0f, 434.0f, this.buttonRegion) { // from class: com.r7games.slotpantanal.PainelScreen.30
            @Override // org.anddev.andengine.entity.shape.Shape, org.anddev.andengine.entity.scene.Scene.ITouchArea
            public boolean onAreaTouched(TouchEvent paramAnonymousTouchEvent, float paramAnonymousFloat1, float paramAnonymousFloat2) {
                if (paramAnonymousTouchEvent.getAction() == 0) {
                    try {
                        if (PainelScreen.this.adMob_interstitial != null) {
                            PainelScreen.this.runOnUiThread(new Runnable() { // from class: com.r7games.slotpantanal.PainelScreen.30.1
                                @Override // java.lang.Runnable
                                public void run() {
                                    if (PainelScreen.this.adMob_interstitial.isLoaded() && PainelScreen.this.show_admob_interstitial) {
                                        Log.i("ADMOB", "AD IS LOAD");
                                        PainelScreen.this.adMob_interstitial.show();
                                    } else {
                                        Log.i("ADMOB", "AD IS NOT LOAD");
                                        PainelScreen.this.exitCreditoScreen();
                                    }
                                }
                            });
                            PainelScreen.this.adMob_interstitial.setAdListener(new AdListener() { // from class: com.r7games.slotpantanal.PainelScreen.30.2
                                @Override // com.google.android.gms.ads.AdListener
                                public void onAdClosed() {
                                    PainelScreen.this.exitCreditoScreen();
                                }
                            });
                        } else {
                            PainelScreen.this.exitCreditoScreen();
                        }
                        return false;
                    } catch (Exception e) {
                        e.printStackTrace();
                        return false;
                    }
                }
                return false;
            }
        };
        this.buttonCredito[1] = new Sprite(f2, 184.0f, this.btnSmallRegion) { // from class: com.r7games.slotpantanal.PainelScreen.31
            @Override // org.anddev.andengine.entity.shape.Shape, org.anddev.andengine.entity.scene.Scene.ITouchArea
            public boolean onAreaTouched(TouchEvent paramAnonymousTouchEvent, float paramAnonymousFloat1, float paramAnonymousFloat2) {
                if (paramAnonymousTouchEvent.getAction() == 0) {
                    try {
                        if (PainelScreen.this.tentativa < 2) {
                            if (PainelScreen.this.tapjoyOn) {
                                if (PainelScreen.this.offerwallPlacement != null) {
                                    if (PainelScreen.this.offerwallPlacement.isContentReady()) {
                                        PainelScreen.this.offerwallPlacement.showContent();
                                    }
                                } else {
                                    PainelScreen.this.callShowOffers();
                                }
                            } else {
                                PainelScreen.this.connectToTapjoy();
                            }
                        } else {
                            PainelScreen.this.semConexao();
                        }
                    } catch (Exception localException) {
                        localException.printStackTrace();
                    }
                }
                return false;
            }
        };
        this.buttonCredito[2] = new Sprite(f2, f, this.btnSmallRegion) { // from class: com.r7games.slotpantanal.PainelScreen.32
            @Override // org.anddev.andengine.entity.shape.Shape, org.anddev.andengine.entity.scene.Scene.ITouchArea
            public boolean onAreaTouched(TouchEvent paramAnonymousTouchEvent, float paramAnonymousFloat1, float paramAnonymousFloat2) {
                if (paramAnonymousTouchEvent.getAction() == 0) {
                    if (!PainelScreen.this.verificando || !PainelScreen.this.solicitado) {
                        if (PainelScreen.this.creditoAtual >= 5000) {
                            if (PainelScreen.this.idioma.equalsIgnoreCase("pt")) {
                                PainelScreen.this.titulo = "Você não pode solicitar agora";
                                PainelScreen.this.msg = "Solicite quando seus créditos estiverem abaixo de 5000 Créditos";
                            } else if (PainelScreen.this.idioma.equalsIgnoreCase("es")) {
                                PainelScreen.this.titulo = "No se puede aplicar ahora";
                                PainelScreen.this.msg = "Pregunte cuãndo sus créditos están por debajo de 5000 Créditos";
                            } else {
                                PainelScreen.this.titulo = "You can not apply now";
                                PainelScreen.this.msg = "Ask when your credits are below 5000 Credits";
                            }
                            PainelScreen.this.mensagem();
                            return false;
                        }
                        Log.d("REQUISIÇÃO", "VERIFICANDO");
                        PainelScreen.this.verificando = true;
                        Thread localThread = new Thread(new Runnable() { // from class: com.r7games.slotpantanal.PainelScreen.32.1
                            @Override // java.lang.Runnable
                            public void run() {
                                String[] retorno = PainelScreen.this.util.SendMessageArray("verificar_diario.php?idprod=" + PainelScreen.this.dispositivo);
                                if (retorno == null) {
                                    PainelScreen.this.semConexao();
                                    PainelScreen.this.verificando = false;
                                    return;
                                }
                                if (retorno[0].equalsIgnoreCase("OK")) {
                                    PainelScreen.this.creditoAtual += 30000;
                                    PainelScreen.this.dbase.SetCredito(PainelScreen.this.creditoAtual);
                                    if (PainelScreen.this.idioma.equalsIgnoreCase("pt")) {
                                        PainelScreen.this.titulo = "Parabéns ganhou  30000  créditos diários";
                                        PainelScreen.this.msg = "Boa diversão e aproveite também as outras opções de conquistar seus créditos";
                                    } else if (PainelScreen.this.idioma.equalsIgnoreCase("es")) {
                                        PainelScreen.this.titulo = "Felicidades ganaron 30000 de crédito de cada día";
                                        PainelScreen.this.msg = "Buena diversión y también disfrutar de las otras opciones de ganar sus créditos";
                                    } else {
                                        PainelScreen.this.titulo = "Congratulations won 30000 daily credit";
                                        PainelScreen.this.msg = "Good fun and also enjoy the other options to earn their credits";
                                    }
                                    PainelScreen.this.mensagem();
                                    PainelScreen.this.solicitado = true;
                                    PainelScreen.this.verificando = false;
                                    Log.d("REQUISIÇÃO", "VERIFICOU!");
                                    return;
                                }
                                if (PainelScreen.this.idioma.equalsIgnoreCase("pt")) {
                                    PainelScreen.this.titulo = "Você já solicitou hoje";
                                    PainelScreen.this.msg = "Solicite mais amanhã ou complete as tarefas em Créditos Grátis";
                                } else if (PainelScreen.this.idioma.equalsIgnoreCase("es")) {
                                    PainelScreen.this.titulo = "Â¿Ha aplicado hoy";
                                    PainelScreen.this.msg = "Solicitar mãs tareas de maã±ana o totales, Free Credits";
                                } else {
                                    PainelScreen.this.titulo = "Have you applied today";
                                    PainelScreen.this.msg = "Request more tomorrow or complete tasks in Free Credits";
                                }
                                PainelScreen.this.mensagem();
                                PainelScreen.this.solicitado = true;
                                PainelScreen.this.verificando = false;
                            }
                        });
                        localThread.start();
                        return false;
                    }
                    return false;
                }
                return false;
            }
        };
        this.buttonCredito[3] = new Sprite(f2, 360.0f, this.btnSmallRegion) { // from class: com.r7games.slotpantanal.PainelScreen.33
            @Override // org.anddev.andengine.entity.shape.Shape, org.anddev.andengine.entity.scene.Scene.ITouchArea
            public boolean onAreaTouched(TouchEvent paramAnonymousTouchEvent, float paramAnonymousFloat1, float paramAnonymousFloat2) {
                if (paramAnonymousTouchEvent.getAction() == 0) {
                    try {
                        if (!IncentivizedAd.isAvailable().booleanValue()) {
                            if (PainelScreen.this.NoShowPlacement.isContentReady()) {
                                PainelScreen.this.NoShowPlacement.showContent();
                            }
                            IncentivizedAd.fetch();
                            return false;
                        }
                        IncentivizedAd.display(PainelScreen.this);
                        Log.i("HEYZAP", "IncentivizedAd Display");
                        return false;
                    } catch (Exception e) {
                        e.printStackTrace();
                        return false;
                    }
                }
                return false;
            }
        };
        this.buttonCredito[4] = new Sprite(f3, 151.0f, this.btnSmallRegion) { // from class: com.r7games.slotpantanal.PainelScreen.34
            @Override // org.anddev.andengine.entity.shape.Shape, org.anddev.andengine.entity.scene.Scene.ITouchArea
            public boolean onAreaTouched(TouchEvent paramAnonymousTouchEvent, float paramAnonymousFloat1, float paramAnonymousFloat2) {
                if (paramAnonymousTouchEvent.getAction() == 0) {
                    try {
                        PainelScreen.this.mHelper.launchPurchaseFlow(PainelScreen.this, PainelScreen.credits_500000, GamesActivityResultCodes.RESULT_RECONNECT_REQUIRED, PainelScreen.this.mPurchaseFinishedListener, "token_buy_credits_500000");
                        return false;
                    } catch (Exception localException) {
                        localException.printStackTrace();
                        return false;
                    }
                }
                return false;
            }
        };
        this.buttonCredito[5] = new Sprite(f3, 210.0f, this.btnSmallRegion) { // from class: com.r7games.slotpantanal.PainelScreen.35
            @Override // org.anddev.andengine.entity.shape.Shape, org.anddev.andengine.entity.scene.Scene.ITouchArea
            public boolean onAreaTouched(TouchEvent paramAnonymousTouchEvent, float paramAnonymousFloat1, float paramAnonymousFloat2) {
                if (paramAnonymousTouchEvent.getAction() == 0) {
                    try {
                        PainelScreen.this.mHelper.launchPurchaseFlow(PainelScreen.this, PainelScreen.credits_300000, GamesActivityResultCodes.RESULT_SIGN_IN_FAILED, PainelScreen.this.mPurchaseFinishedListener, "token_buy_credits_300000");
                        return false;
                    } catch (Exception localException) {
                        localException.printStackTrace();
                        return false;
                    }
                }
                return false;
            }
        };
        this.buttonCredito[6] = new Sprite(f3, f, this.btnSmallRegion) { // from class: com.r7games.slotpantanal.PainelScreen.36
            @Override // org.anddev.andengine.entity.shape.Shape, org.anddev.andengine.entity.scene.Scene.ITouchArea
            public boolean onAreaTouched(TouchEvent paramAnonymousTouchEvent, float paramAnonymousFloat1, float paramAnonymousFloat2) {
                if (paramAnonymousTouchEvent.getAction() == 0) {
                    try {
                        PainelScreen.this.mHelper.launchPurchaseFlow(PainelScreen.this, PainelScreen.credits_150000, GamesActivityResultCodes.RESULT_LICENSE_FAILED, PainelScreen.this.mPurchaseFinishedListener, "token_buy_credits_150000");
                        return false;
                    } catch (Exception localException) {
                        localException.printStackTrace();
                        return false;
                    }
                }
                return false;
            }
        };
        this.buttonCredito[7] = new Sprite(f3, 331.0f, this.btnSmallRegion) { // from class: com.r7games.slotpantanal.PainelScreen.37
            @Override // org.anddev.andengine.entity.shape.Shape, org.anddev.andengine.entity.scene.Scene.ITouchArea
            public boolean onAreaTouched(TouchEvent paramAnonymousTouchEvent, float paramAnonymousFloat1, float paramAnonymousFloat2) {
                if (paramAnonymousTouchEvent.getAction() == 0) {
                    try {
                        PainelScreen.this.mHelper.launchPurchaseFlow(PainelScreen.this, PainelScreen.credits_100000, GamesActivityResultCodes.RESULT_APP_MISCONFIGURED, PainelScreen.this.mPurchaseFinishedListener, "token_buy_credits_100000");
                        return false;
                    } catch (Exception localException) {
                        localException.printStackTrace();
                        return false;
                    }
                }
                return false;
            }
        };
        this.buttonCredito[8] = new Sprite(f3, 390.0f, this.btnSmallRegion) { // from class: com.r7games.slotpantanal.PainelScreen.38
            @Override // org.anddev.andengine.entity.shape.Shape, org.anddev.andengine.entity.scene.Scene.ITouchArea
            public boolean onAreaTouched(TouchEvent paramAnonymousTouchEvent, float paramAnonymousFloat1, float paramAnonymousFloat2) {
                if (paramAnonymousTouchEvent.getAction() == 0) {
                    try {
                        PainelScreen.this.mHelper.launchPurchaseFlow(PainelScreen.this, PainelScreen.credits_50000, GamesActivityResultCodes.RESULT_LEFT_ROOM, PainelScreen.this.mPurchaseFinishedListener, "token_buy_credits_50000");
                        return false;
                    } catch (Exception localException) {
                        localException.printStackTrace();
                        return false;
                    }
                }
                return false;
            }
        };
    }

    @Override // com.r7games.slotpantanal.google.services.GBaseGameActivity, org.anddev.andengine.ui.activity.BaseGameActivity, android.app.Activity
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        this.sp = PreferenceManager.getDefaultSharedPreferences(this);
        this.ed = this.sp.edit();
        this.score = this.sp.getLong("score_halloween", 0L);
        long time = SystemClock.elapsedRealtime();
        Log.d("TIME", "CLOCK: " + time);
        try {
            getWindow().addFlags(128);
            Log.i("BILLING", "ONCREATE");
            this.mHelper = new IabHelper(this, "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAnAppEk/HExrtMW/lj1uMzUXMxZWjlllTLkXtWyhHlFKsIa9u5Etdtn1aSfshnguUAnD9SG0EE9+Z2dfK9ZZZ0TeCxYwsNB9Dce13pKwNP8pT2NV9gbXpPHzclh1DuTjLtwwUrpcTyu8RMKG8mfftZZvNMvL3kw1dAiGwGZPq04JuH6PgrcIUB1IW0xYwFHC4r/31rCwz+J9pUdnueB6RPf4COK52UhfwZJ6BkujmgD8Ca2bBKD74rnryze+JmiBSipfW+O+VBRqv4wSsu1m1IJkIZCiPpU09SWSUYlNqQN3edNRDF9Md8b2rxAk4/au6oJPuKguVYS+sokY6X2si6wIDAQAB");
            this.mHelper.startSetup(new IabHelper.OnIabSetupFinishedListener() { // from class: com.r7games.slotpantanal.PainelScreen.39
                @Override // com.r7games.slotpantanal.utils.IabHelper.OnIabSetupFinishedListener
                public void onIabSetupFinished(IabResult result) {
                    if (!result.isSuccess()) {
                        Debug.i("In-app Billing setup failed: " + result);
                    } else {
                        Debug.i("In-app Billing is set up OK");
                        PainelScreen.this.consumeItem();
                    }
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override // com.r7games.slotpantanal.google.services.GBaseGameActivity, android.app.Activity
    protected void onActivityResult(int requestCode, int responseCode, Intent intent) {
        if (!this.mHelper.handleActivityResult(requestCode, responseCode, intent)) {
            super.onActivityResult(requestCode, responseCode, intent);
        }
        super.onActivityResult(requestCode, responseCode, intent);
    }

    public void consumeItem() {
        this.mHelper.queryInventoryAsync(this.mReceivedInventoryListener);
    }

    protected void setupCallbacks() {
        HeyzapAds.OnStatusListener statusListener = new HeyzapAds.OnStatusListener() { // from class: com.r7games.slotpantanal.PainelScreen.40
            @Override // com.heyzap.sdk.ads.HeyzapAds.OnStatusListener
            public void onAvailable(String tag) {
                Log.i("HEYZAP", "Existe um ad para exibir");
            }

            @Override // com.heyzap.sdk.ads.HeyzapAds.OnStatusListener
            public void onShow(String tag) {
                PainelScreen.this.numAppsAfter = 0;
                Log.i("HEYZAP", "Sucesso em mostrar o ad");
                PackageManager pm = PainelScreen.this.getPackageManager();
                List<ApplicationInfo> packages = pm.getInstalledApplications(128);
                for (ApplicationInfo packageInfo : packages) {
                    Log.d(PainelScreen.TAG, "Installed package :" + packageInfo.packageName);
                    PainelScreen.this.numAppsAfter++;
                }
                PainelScreen.this.ed.putInt("numAppsAfter", PainelScreen.this.numAppsAfter);
                PainelScreen.this.ed.putBoolean("video", true);
                PainelScreen.this.ed.apply();
                Log.i("Oferta Video", "Num apps After: " + PainelScreen.this.numAppsAfter);
                PainelScreen.this.numAppsBefore = 0;
            }

            @Override // com.heyzap.sdk.ads.HeyzapAds.OnStatusListener
            public void onClick(String tag) {
                Log.i("HEYZAP", "Clicou em um ad");
            }

            @Override // com.heyzap.sdk.ads.HeyzapAds.OnStatusListener
            public void onHide(String tag) {
                Log.i("HEYZAP", "Retornou para o meu app");
            }

            @Override // com.heyzap.sdk.ads.HeyzapAds.OnStatusListener
            public void onFailedToFetch(String tag) {
            }

            @Override // com.heyzap.sdk.ads.HeyzapAds.OnStatusListener
            public void onFailedToShow(String tag) {
            }

            @Override // com.heyzap.sdk.ads.HeyzapAds.OnStatusListener
            public void onAudioStarted() {
                Log.i("HEYZAP", "Iniciou um som no ad");
            }

            @Override // com.heyzap.sdk.ads.HeyzapAds.OnStatusListener
            public void onAudioFinished() {
                Log.i("HEYZAP", "Terminou um som no ad");
            }
        };
        IncentivizedAd.setOnStatusListener(statusListener);
        com.heyzap.sdk.ads.InterstitialAd.setOnStatusListener(statusListener);
        HeyzapAds.OnIncentiveResultListener incentiveResultListener = new HeyzapAds.OnIncentiveResultListener() { // from class: com.r7games.slotpantanal.PainelScreen.41
            @Override // com.heyzap.sdk.ads.HeyzapAds.OnIncentiveResultListener
            public void onComplete(String tag) {
                if (PainelScreen.this.tmCoutry.equalsIgnoreCase("br")) {
                    PainelScreen.this.giveARewardBr();
                } else {
                    PainelScreen.this.giveAReward();
                }
                try {
                    IncentivizedAd.fetch();
                    com.heyzap.sdk.ads.InterstitialAd.fetch();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override // com.heyzap.sdk.ads.HeyzapAds.OnIncentiveResultListener
            public void onIncomplete(String tag) {
                PainelScreen.this.doNothing();
            }
        };
        IncentivizedAd.setOnIncentiveResultListener(incentiveResultListener);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void giveARewardBr() {
        this.numAppsBefore = 0;
        PackageManager pm = getPackageManager();
        List<ApplicationInfo> packages = pm.getInstalledApplications(128);
        for (ApplicationInfo packageInfo : packages) {
            Log.d(TAG, "Installed package :" + packageInfo.packageName);
            this.numAppsBefore++;
        }
        Log.i("Oferta Video", "Num apps After: " + this.numAppsAfter);
        Log.i("Oferta Video", "Num apps Before: " + this.numAppsBefore);
        if (this.numAppsAfter == 0) {
            this.numAppsAfter = this.sp.getInt("numAppsAfter", 0);
        }
        if (this.sp.getBoolean("video", false)) {
            if (this.numAppsBefore > this.numAppsAfter) {
                this.creditoAtual += 5000;
                this.dbase.SetCredito(this.creditoAtual);
                this.numAppsAfter = 0;
                this.numAppsBefore = 0;
                this.ed.putBoolean("video", false);
                this.ed.putInt("numAppsAfter", 0);
                this.ed.apply();
                this.t.send(new HitBuilders.EventBuilder().setCategory("Distribuição Créditos").setAction("Ganhou").setLabel("Oferta Video").setValue(5000L).build());
                if (this.idioma.equalsIgnoreCase("pt")) {
                    this.titulo = "Parabéns completou a tarefa";
                    this.msg = "Ganhou 5000 créditos";
                } else if (this.idioma.equalsIgnoreCase("es")) {
                    this.titulo = "Felicidades completaron la tarea";
                    this.msg = "Ganó 5000 créditos";
                } else {
                    this.titulo = "Congratulations completed the task";
                    this.msg = "By watching the video won 5000 credits";
                }
                mensagem();
                return;
            }
            doNothing();
            this.numAppsAfter = 0;
            this.numAppsBefore = 0;
            this.ed.putBoolean("video", false);
            this.ed.putInt("numAppsAfter", 0);
            this.ed.apply();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void giveAReward() {
        this.creditoAtual += 5000;
        this.dbase.SetCredito(this.creditoAtual);
        this.t.send(new HitBuilders.EventBuilder().setCategory("Distribuição Créditos").setAction("Ganhou").setLabel("Oferta Video").setValue(5000L).build());
        if (this.idioma.equalsIgnoreCase("pt")) {
            this.titulo = "Parabéns completou a tarefa";
            this.msg = "Ganhou 5000 créditos";
        } else if (this.idioma.equalsIgnoreCase("es")) {
            this.titulo = "Felicidades completaron la tarea";
            this.msg = "Ganó 5000 créditos";
        } else {
            this.titulo = "Congratulations completed the task";
            this.msg = "By watching the video won 5000 credits";
        }
        mensagem();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void doNothing() {
        runOnUiThread(new Runnable() { // from class: com.r7games.slotpantanal.PainelScreen.42
            @Override // java.lang.Runnable
            public void run() {
                if (PainelScreen.this.idioma.equalsIgnoreCase("pt")) {
                    PainelScreen.this.titulo = "Você não completou a tarefa completamente";
                    PainelScreen.this.msg = "Assista o video completamente";
                } else if (PainelScreen.this.idioma.equalsIgnoreCase("es")) {
                    PainelScreen.this.titulo = "No ha completado las tareas";
                    PainelScreen.this.msg = "Mira el video";
                } else {
                    PainelScreen.this.titulo = "You have not completed the tasks";
                    PainelScreen.this.msg = "Watch the video";
                }
                if (PainelScreen.this.tmCoutry.equalsIgnoreCase("br")) {
                    PainelScreen.this.titulo = "Você não completou a tarefa completamente";
                    PainelScreen.this.msg = "Assita o video e baixe o app na próxima para completar a tarefa";
                }
                PainelScreen.this.mensagem();
            }
        });
    }

    public void semOfertas() {
        if (this.idioma.equalsIgnoreCase("pt")) {
            this.titulo = "Sem ofertas por enquanto";
            this.msg = "Não existem ofertas nesse momento, tente novamente mais tarde";
        } else if (this.idioma.equalsIgnoreCase("es")) {
            this.titulo = "No hay ofertas por ahora";
            this.msg = "No hay ofertas en este momento, por favor intente de nuevo";
        } else {
            this.titulo = "No offers for now";
            this.msg = "No offers at this time, please try again later";
        }
        mensagem();
    }

    public void semConexao() {
        if (this.idioma.equalsIgnoreCase("pt")) {
            this.titulo = "Sem conexão com Internet";
            this.msg = "Você está jogando offline, para obter mais créditos se conecte na internet";
        } else if (this.idioma.equalsIgnoreCase("es")) {
            this.titulo = "No hay conexión a Internet";
            this.msg = "Usted está jugando fuera de lã\u00adnea por mãs créditos para conectarse a Internet";
        } else {
            this.titulo = "No Internet connection";
            this.msg = "You are playing offline for more credits to connect the Internet";
        }
        mensagem();
    }
}
