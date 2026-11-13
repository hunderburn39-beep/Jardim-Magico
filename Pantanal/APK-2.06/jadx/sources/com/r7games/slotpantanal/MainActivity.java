package com.r7games.slotpantanal;

import android.annotation.TargetApi;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.net.ConnectivityManager;
import android.os.Build;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.support.v4.media.TransportMediator;
import android.util.Log;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.InterstitialAd;
import com.google.android.gms.analytics.GoogleAnalytics;
import com.google.android.gms.analytics.HitBuilders;
import com.google.android.gms.analytics.Tracker;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.r7games.slotpantanal.google.services.GBaseGameMainActivity;
import com.tapjoy.TJAdUnitConstants;
import com.tapjoy.TapjoyConstants;
import java.io.IOException;
import java.lang.reflect.Array;
import java.nio.channels.IllegalSelectorException;
import java.text.DecimalFormat;
import java.util.Arrays;
import java.util.Collections;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;
import org.anddev.andengine.audio.sound.Sound;
import org.anddev.andengine.audio.sound.SoundFactory;
import org.anddev.andengine.engine.Engine;
import org.anddev.andengine.engine.camera.Camera;
import org.anddev.andengine.engine.handler.timer.ITimerCallback;
import org.anddev.andengine.engine.handler.timer.TimerHandler;
import org.anddev.andengine.engine.options.EngineOptions;
import org.anddev.andengine.engine.options.resolutionpolicy.FillResolutionPolicy;
import org.anddev.andengine.entity.IEntity;
import org.anddev.andengine.entity.modifier.IEntityModifier;
import org.anddev.andengine.entity.modifier.LoopEntityModifier;
import org.anddev.andengine.entity.modifier.PathModifier;
import org.anddev.andengine.entity.modifier.RotationModifier;
import org.anddev.andengine.entity.modifier.SequenceEntityModifier;
import org.anddev.andengine.entity.scene.Scene;
import org.anddev.andengine.entity.sprite.AnimatedSprite;
import org.anddev.andengine.entity.sprite.Sprite;
import org.anddev.andengine.entity.sprite.TiledSprite;
import org.anddev.andengine.entity.text.ChangeableText;
import org.anddev.andengine.input.touch.TouchEvent;
import org.anddev.andengine.opengl.font.Font;
import org.anddev.andengine.opengl.texture.TextureOptions;
import org.anddev.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlas;
import org.anddev.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlasTextureRegionFactory;
import org.anddev.andengine.opengl.texture.region.TextureRegion;
import org.anddev.andengine.opengl.texture.region.TiledTextureRegion;
import org.anddev.andengine.util.HorizontalAlign;
import org.anddev.andengine.util.modifier.IModifier;

/* JADX INFO: loaded from: classes.dex */
public class MainActivity extends GBaseGameMainActivity {
    public static final int cameraHeight = 480;
    public static final int cameraWidth = 800;
    public Scene aboboraScene;
    public Double acumMax;
    public ChangeableText acumMaxText;
    public Double acumMed;
    public ChangeableText acumMedText;
    public Double acumMin;
    public ChangeableText acumMinText;
    public Sound acumuladoSound;
    public Double acupor;
    InterstitialAd adMob_interstitial;
    public AnimatedSprite[] anbAnim;
    public BitmapTextureAtlas anbAnimAtlas;
    public TiledTextureRegion anbAnimRegion;
    public boolean[] anbAnimTouched;
    public BitmapTextureAtlas anbBgAtlas;
    public TextureRegion anbBgRegion;
    public Sprite anbBgSpr;
    public int anbBns;
    public ChangeableText anbBonus;
    public boolean anbCanTouch;
    public ChangeableText anbCredito;
    public ChangeableText anbPremio;
    public int anbPrizeIndex;
    public Integer[] anbPrizeValues;
    public Integer[] anbPrizes;
    public AnimatedSprite[] animSlot;
    public BitmapTextureAtlas animSlotAtlas;
    public TiledTextureRegion animSlotRegion;
    public AnimatedSprite[][] animSlotWin;
    public Integer apBet;
    public Integer aposta;
    public ChangeableText apostaText;
    public Sound bWinSound;
    public Sprite background;
    public BitmapTextureAtlas backgroundAtlas;
    public TextureRegion backgroundRegion;
    public Font betFont;
    public BitmapTextureAtlas betFontAtlas;
    public ChangeableText betText;
    public Sound bonusEndSound;
    public Sound bonusSelSound;
    public boolean bonusShow;
    public Sound bonusSound;
    public int[] bonusToCall;
    private AnimatedSprite btSom;
    private BitmapTextureAtlas btSomAtlas;
    private TiledTextureRegion btSomRegion;
    public Sprite btnAposta;
    public TextureRegion btnApostaRegion;
    public BitmapTextureAtlas btnAtlas;
    public BitmapTextureAtlas btnAtlas2;
    public Sprite btnAuto;
    public TextureRegion btnAutoRegion;
    public Sprite btnInfo;
    public BitmapTextureAtlas btnInfoAtlas;
    public TextureRegion btnInfoRegion;
    public Sprite btnJoga;
    public TextureRegion btnJogaRegion;
    public Sprite btnLinha;
    public TextureRegion btnLinhaRegion;
    public Sprite btnMenu;
    public TextureRegion btnMenuRegion;
    public Sprite btnParar;
    public TextureRegion btnPararRegion;
    public Sprite btnTabela;
    public TextureRegion btnTabelaRegion;
    public Sprite btnVoltar;
    public TextureRegion btnVoltarRegion;
    public Font cFont;
    public BitmapTextureAtlas cFontAtlas;
    public AnimatedSprite[] caldAnim;
    public BitmapTextureAtlas caldAnimAtlas;
    public TiledTextureRegion caldAnimRegion;
    public boolean[] caldAnimTouched;
    public BitmapTextureAtlas caldBgAtlas;
    public TextureRegion caldBgRegion;
    public Sprite caldBgSpr;
    public int caldBns;
    public ChangeableText caldBonus;
    public boolean caldCanTouch;
    public ChangeableText caldCredito;
    public ChangeableText caldPremio;
    public int caldPrizeIndex;
    public Integer[] caldPrizeValues;
    public Integer[] caldPrizes;
    public Scene caldeiraoScene;
    public Camera camera;
    public boolean canDouble;
    public boolean canSkip;
    public boolean canTouch;
    public Integer credito;
    public ChangeableText creditoText;
    public Integer dPremio;
    public AnimatedSprite[] dadoAnim;
    public TiledTextureRegion dadoAnimRegion;
    public BitmapTextureAtlas dadoAtlas;
    public TextureRegion dadoBgRegion;
    public Sprite dadoBgSpr;
    public boolean dadoCanTouch;
    private LoopEntityModifier dadoModifier;
    public ChangeableText dadoPremio;
    public Scene dadosScene;
    public BitmapTextureAtlas dobraAtlas;
    public Sprite dobraBg;
    public BitmapTextureAtlas dobraBgAtlas;
    public TextureRegion dobraBgRegion;
    public boolean dobraCanTouch;
    public ChangeableText dobraCredito;
    public ChangeableText dobraPremio;
    public TiledTextureRegion dobraRegion;
    public Scene dobraScene;
    public AnimatedSprite dobraSpr;
    public Sprite dobrouSpr;
    SharedPreferences.Editor ed;
    public boolean enterMenu;
    public long[] frameDuration;
    public boolean jogaAuto;
    public BitmapTextureAtlas jogaAutoAtlas;
    public TextureRegion jogaAutoRegion;
    public Sprite jogaAutoSpr;
    public Sprite[] line;
    public BitmapTextureAtlas line15Atlas;
    public BitmapTextureAtlas line1Atlas;
    public BitmapTextureAtlas line20Atlas;
    public BitmapTextureAtlas line25Atlas;
    public BitmapTextureAtlas line5Atlas;
    public BitmapTextureAtlas line9Atlas;
    public Integer lineBet;
    public Sprite[] lineCrop;
    public TextureRegion[] lineCropRegion;
    public Font lineFont;
    public BitmapTextureAtlas lineFontAtlas;
    public TextureRegion[] lineRegion;
    public ChangeableText lineText;
    public Sound linhaSound;
    public AnimatedSprite[] mAnim;
    public BitmapTextureAtlas mAnimAtlas;
    public TiledTextureRegion mAnimRegion;
    public boolean[] mAnimTouch;
    public Font mFont;
    public BitmapTextureAtlas mFontAtlas;
    Runnable mRunnable;
    public AnimatedSprite[] miAnim;
    public AnimatedSprite[] moraAnim;
    public boolean[] moraAnimTouched;
    public BitmapTextureAtlas moraBgAtlas;
    public TextureRegion moraBgRegion;
    public Sprite moraBgSpr;
    public int moraBns;
    public ChangeableText moraBonus;
    public boolean moraCanTouch;
    public ChangeableText moraCredito;
    public ChangeableText[] moraNum;
    public ChangeableText moraPremio;
    public Integer[] moraPrizeValues;
    public Integer[] moraPrizes;
    public Sound moranSound;
    public Scene morangoScene;
    public BitmapTextureAtlas numAtlas;
    public TextureRegion[] numRegion;
    public Sprite[] numSpr;
    Scene.IOnSceneTouchListener oldTouch;
    public Font pFont;
    public BitmapTextureAtlas pFontAtlas;
    public TextureRegion pagoRegion;
    public Sprite pagoSpr;
    public ChangeableText pagoText;
    public PathModifier.Path[] path;
    public Sound playSound;
    public int[][] plna;
    public Integer premio;
    public Sound premioSound;
    public ChangeableText premioText;
    public Integer rBonus;
    public Random rand;
    public BitmapTextureAtlas roletaAtlas;
    public BitmapTextureAtlas roletaBgAtlas;
    public TextureRegion roletaBgRegion;
    public Sprite roletaBgSpr;
    public boolean roletaCanTouch;
    public ChangeableText roletaPremio;
    public TextureRegion roletaRegion;
    public Scene roletaScene;
    public Sprite roletaSpr;
    private LoopEntityModifier rotationModifier;
    public boolean saiAcum;
    public Scene scene;
    public int[][] slotLinhas;
    public int[][] slotTileValues;
    public TiledSprite[][] slots;
    public BitmapTextureAtlas slotsAtlas;
    public TiledTextureRegion slotsRegion;
    SharedPreferences sp;
    private Sprite splash;
    private BitmapTextureAtlas splashAtlas;
    private TextureRegion splashRegion;
    public Scene splashScene;
    Tracker t;
    public BitmapTextureAtlas[] tabelaAtlas;
    public TextureRegion[] tabelaRegion;
    public Scene tabelaScene;
    public Sprite[] tabelaSpr;
    final String TAG = PainelScreen.TAG;
    int dobraQtd = 0;
    private double score = 0.0d;
    boolean min = false;
    boolean med = false;
    boolean max = false;
    public boolean bonusFim = false;
    public boolean premiacao = false;
    public boolean verificando = false;
    public int morangosEscolha = 0;
    public int dobraEscolha = 0;
    public int numMaxBet = 0;
    public int bonusReplay = 0;
    boolean pago = false;
    boolean fimTorneio = false;
    long jogadas = 0;
    boolean SoundON = true;
    public boolean podePagar = false;
    public Database dbase = new Database(this);
    public GeneratorNew gen = new GeneratorNew();
    public Utils util = new Utils();
    String idioma = "en";
    public int escolha = 0;
    public int bloqueado = 50;
    Handler mHandler = new Handler();
    boolean show_admob_interstitial = false;
    int demoStep = 0;
    boolean showDemo = false;
    int curTab = -1;
    Timer changeTab = new Timer();

    public MainActivity() {
        long[] localObject = {180, 180};
        this.frameDuration = localObject;
        Integer[] localObject1 = {0, 1, 2, 3, 8};
        this.caldPrizes = localObject1;
        Integer[] localObject2 = {50, 75, 100, 150};
        this.caldPrizeValues = localObject2;
        Integer[] localObject3 = {3, 4, 5, 6, 7};
        this.anbPrizes = localObject3;
        Integer[] localObject4 = {0, 0, 0, 150, 200, 250, Integer.valueOf(TJAdUnitConstants.DEFAULT_VOLUME_CHECK_INTERVAL), 1000};
        this.anbPrizeValues = localObject4;
        Integer[] localObject5 = {0, 0, 1, 1, 2, 2, 3, 3, 4, 4, 5, 6};
        this.moraPrizes = localObject5;
        Integer[] localObject6 = {2, 5, 10, 20, 50, 100, 0};
        this.moraPrizeValues = localObject6;
    }

    private void InitDobra() {
        float f = 412.0f;
        float f2 = 88.0f;
        this.dobraBgAtlas = new BitmapTextureAtlas(1024, 512, TextureOptions.DEFAULT);
        this.dobraBgRegion = BitmapTextureAtlasTextureRegionFactory.createFromAsset(this.dobraBgAtlas, this, "05_telad.png", 0, 0);
        this.btnVoltarRegion = BitmapTextureAtlasTextureRegionFactory.createFromAsset(this.dobraBgAtlas, this, "voltar.png", 800, 0);
        this.mAnimAtlas = new BitmapTextureAtlas(1024, 1024, TextureOptions.DEFAULT);
        this.mAnimRegion = BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(this.mAnimAtlas, this, "05_mii.png", 0, 0, 5, 8);
        this.mEngine.getTextureManager().loadTexture(this.dobraBgAtlas);
        this.mEngine.getTextureManager().loadTexture(this.mAnimAtlas);
        this.dobraBg = new Sprite(BitmapDescriptorFactory.HUE_RED, BitmapDescriptorFactory.HUE_RED, this.dobraBgRegion);
        this.btnVoltar = new Sprite(560.0f, f, this.btnVoltarRegion) { // from class: com.r7games.slotpantanal.MainActivity.1
            @Override // org.anddev.andengine.entity.shape.Shape, org.anddev.andengine.entity.scene.Scene.ITouchArea
            public boolean onAreaTouched(TouchEvent paramAnonymousTouchEvent, float paramAnonymousFloat1, float paramAnonymousFloat2) {
                int k;
                if (paramAnonymousTouchEvent.getAction() == 0 && MainActivity.this.dobraCanTouch) {
                    MainActivity.this.dobraCanTouch = false;
                    if (MainActivity.this.premio.intValue() <= 0) {
                        MainActivity.this.ExitDobra();
                    }
                    int j = MainActivity.this.credito.intValue();
                    int i = MainActivity.this.premio.intValue();
                    while (true) {
                        if (i > 5000) {
                            k = 5000;
                        } else if (i > 1000) {
                            k = 1000;
                        } else if (i > 500) {
                            k = TJAdUnitConstants.DEFAULT_VOLUME_CHECK_INTERVAL;
                        } else if (i > 100) {
                            k = 100;
                        } else if (i > 50) {
                            k = 50;
                        } else if (i > 10) {
                            k = 10;
                        } else {
                            k = 1;
                        }
                        MainActivity mainActivity = MainActivity.this;
                        mainActivity.credito = Integer.valueOf(mainActivity.credito.intValue() + MainActivity.this.premio.intValue());
                        MainActivity.this.SetDobraCredito(MainActivity.this.credito);
                        MainActivity.this.premio = 0;
                        MainActivity.this.SetDobraPremio(MainActivity.this.premio);
                        j += k;
                        i -= k;
                        try {
                            MainActivity.this.SetDobraPremio(Integer.valueOf(i));
                            MainActivity.this.SetDobraCredito(Integer.valueOf(j));
                            if (i < 1) {
                                break;
                            }
                            Thread.sleep(50L);
                        } catch (Exception localException) {
                            localException.printStackTrace();
                        }
                    }
                    MainActivity.this.mRunnable = new Runnable() { // from class: com.r7games.slotpantanal.MainActivity.1.1
                        @Override // java.lang.Runnable
                        public void run() {
                            MainActivity.this.ExitDobra();
                        }
                    };
                    MainActivity.this.mHandler.postDelayed(MainActivity.this.mRunnable, 1500L);
                }
                return false;
            }
        };
        this.dobraCanTouch = true;
        this.mAnimTouch = new boolean[4];
        this.mAnimTouch[0] = true;
        this.mAnimTouch[1] = true;
        this.mAnimTouch[2] = true;
        this.mAnimTouch[3] = true;
        this.mAnim = new AnimatedSprite[4];
        this.mAnim[0] = new AnimatedSprite(94.0f, f2, this.mAnimRegion) { // from class: com.r7games.slotpantanal.MainActivity.2
            @Override // org.anddev.andengine.entity.shape.Shape, org.anddev.andengine.entity.scene.Scene.ITouchArea
            public boolean onAreaTouched(TouchEvent paramAnonymousTouchEvent, float paramAnonymousFloat1, float paramAnonymousFloat2) {
                if (paramAnonymousTouchEvent.getAction() == 0 && MainActivity.this.mAnimTouch[0] && MainActivity.this.dobraCanTouch) {
                    MainActivity.this.mAnimTouch[0] = false;
                    MainActivity.this.ClickDobra(0);
                }
                return false;
            }
        };
        this.mAnim[1] = new AnimatedSprite(253.0f, f2, this.mAnimRegion) { // from class: com.r7games.slotpantanal.MainActivity.3
            @Override // org.anddev.andengine.entity.shape.Shape, org.anddev.andengine.entity.scene.Scene.ITouchArea
            public boolean onAreaTouched(TouchEvent paramAnonymousTouchEvent, float paramAnonymousFloat1, float paramAnonymousFloat2) {
                if (paramAnonymousTouchEvent.getAction() == 0 && MainActivity.this.mAnimTouch[1] && MainActivity.this.dobraCanTouch) {
                    MainActivity.this.mAnimTouch[1] = false;
                    MainActivity.this.ClickDobra(1);
                }
                return false;
            }
        };
        this.mAnim[2] = new AnimatedSprite(f, f2, this.mAnimRegion) { // from class: com.r7games.slotpantanal.MainActivity.4
            @Override // org.anddev.andengine.entity.shape.Shape, org.anddev.andengine.entity.scene.Scene.ITouchArea
            public boolean onAreaTouched(TouchEvent paramAnonymousTouchEvent, float paramAnonymousFloat1, float paramAnonymousFloat2) {
                if (paramAnonymousTouchEvent.getAction() == 0 && MainActivity.this.mAnimTouch[2] && MainActivity.this.dobraCanTouch) {
                    MainActivity.this.mAnimTouch[2] = false;
                    MainActivity.this.ClickDobra(2);
                }
                return false;
            }
        };
        this.mAnim[3] = new AnimatedSprite(571.0f, f2, this.mAnimRegion) { // from class: com.r7games.slotpantanal.MainActivity.5
            @Override // org.anddev.andengine.entity.shape.Shape, org.anddev.andengine.entity.scene.Scene.ITouchArea
            public boolean onAreaTouched(TouchEvent paramAnonymousTouchEvent, float paramAnonymousFloat1, float paramAnonymousFloat2) {
                if (paramAnonymousTouchEvent.getAction() == 0 && MainActivity.this.mAnimTouch[3] && MainActivity.this.dobraCanTouch) {
                    MainActivity.this.mAnimTouch[3] = false;
                    MainActivity.this.ClickDobra(3);
                }
                return false;
            }
        };
        this.miAnim = new AnimatedSprite[4];
        this.miAnim[0] = new AnimatedSprite(94.0f, 88.0f, this.mAnimRegion.deepCopy());
        this.miAnim[1] = new AnimatedSprite(253.0f, 88.0f, this.mAnimRegion.deepCopy());
        this.miAnim[2] = new AnimatedSprite(412.0f, 88.0f, this.mAnimRegion.deepCopy());
        this.miAnim[3] = new AnimatedSprite(571.0f, 88.0f, this.mAnimRegion.deepCopy());
        this.dobraCredito = new ChangeableText(215.0f, 13.0f, this.cFont, "0", 8);
        this.dobraPremio = new ChangeableText(500.0f, 13.0f, this.cFont, "0", 8);
    }

    private String formatDouble(Double paramDouble) {
        DecimalFormat localDecimalFormat = new DecimalFormat();
        localDecimalFormat.setMinimumFractionDigits(2);
        localDecimalFormat.setMaximumFractionDigits(2);
        localDecimalFormat.setMinimumIntegerDigits(1);
        localDecimalFormat.setMaximumIntegerDigits(4);
        localDecimalFormat.setGroupingSize(6);
        return localDecimalFormat.format(paramDouble);
    }

    public void AnimSlot() {
        for (int i = 0; i < 6; i++) {
            if (this.scene.getChildIndex(this.animSlot[i]) < 0) {
                this.scene.attachChild(this.animSlot[i]);
            }
        }
        SetDbValues();
        SetPago(0);
        RemoveLines();
        RemoveDobraSpr();
        removeAnimSlotWin();
        this.bonusShow = false;
        this.canDouble = false;
        long[] arrayOfLong = new long[10];
        arrayOfLong[0] = 70;
        arrayOfLong[2] = 70;
        this.animSlot[0].animate(arrayOfLong, 1, new AnimatedSprite.IAnimationListener() { // from class: com.r7games.slotpantanal.MainActivity.6
            @Override // org.anddev.andengine.entity.sprite.AnimatedSprite.IAnimationListener
            public void onAnimationEnd(AnimatedSprite paramAnonymousAnimatedSprite) {
                MainActivity.this.runOnUpdateThread(new Runnable() { // from class: com.r7games.slotpantanal.MainActivity.6.1
                    @Override // java.lang.Runnable
                    public void run() {
                        MainActivity.this.removeAnimSlot(0);
                        MainActivity.this.canSkip = true;
                    }
                });
            }
        });
        long[] arrayOfLong2 = new long[10];
        arrayOfLong2[1] = 70;
        arrayOfLong2[3] = 70;
        arrayOfLong2[5] = 70;
        this.animSlot[1].animate(arrayOfLong2, 1, new AnimatedSprite.IAnimationListener() { // from class: com.r7games.slotpantanal.MainActivity.7
            @Override // org.anddev.andengine.entity.sprite.AnimatedSprite.IAnimationListener
            public void onAnimationEnd(AnimatedSprite paramAnonymousAnimatedSprite) {
                MainActivity.this.runOnUpdateThread(new Runnable() { // from class: com.r7games.slotpantanal.MainActivity.7.1
                    @Override // java.lang.Runnable
                    public void run() {
                        MainActivity.this.removeAnimSlot(1);
                    }
                });
            }
        });
        long[] arrayOfLong3 = new long[10];
        arrayOfLong3[2] = 70;
        arrayOfLong3[4] = 70;
        arrayOfLong3[6] = 70;
        arrayOfLong3[8] = 70;
        this.animSlot[2].animate(arrayOfLong3, 1, new AnimatedSprite.IAnimationListener() { // from class: com.r7games.slotpantanal.MainActivity.8
            @Override // org.anddev.andengine.entity.sprite.AnimatedSprite.IAnimationListener
            public void onAnimationEnd(AnimatedSprite paramAnonymousAnimatedSprite) {
                MainActivity.this.runOnUpdateThread(new Runnable() { // from class: com.r7games.slotpantanal.MainActivity.8.1
                    @Override // java.lang.Runnable
                    public void run() {
                        MainActivity.this.removeAnimSlot(2);
                    }
                });
            }
        });
        this.animSlot[3].animate(new long[]{0, 70, 0, 70, 0, 70, 0, 70, 0, 70}, 1, new AnimatedSprite.IAnimationListener() { // from class: com.r7games.slotpantanal.MainActivity.9
            @Override // org.anddev.andengine.entity.sprite.AnimatedSprite.IAnimationListener
            public void onAnimationEnd(AnimatedSprite paramAnonymousAnimatedSprite) {
                MainActivity.this.runOnUpdateThread(new Runnable() { // from class: com.r7games.slotpantanal.MainActivity.9.1
                    @Override // java.lang.Runnable
                    public void run() {
                        MainActivity.this.removeAnimSlot(3);
                    }
                });
            }
        });
        this.animSlot[4].animate(new long[]{70, 0, 70, 0, 70, 0, 70, 0, 70, 70}, 1, new AnimatedSprite.IAnimationListener() { // from class: com.r7games.slotpantanal.MainActivity.10
            @Override // org.anddev.andengine.entity.sprite.AnimatedSprite.IAnimationListener
            public void onAnimationEnd(AnimatedSprite paramAnonymousAnimatedSprite) {
                MainActivity.this.runOnUpdateThread(new Runnable() { // from class: com.r7games.slotpantanal.MainActivity.10.1
                    @Override // java.lang.Runnable
                    public void run() {
                        MainActivity.this.removeAnimSlot(4);
                    }
                });
            }
        });
        this.animSlot[5].animate(new long[]{70, 70, 70, 70, 0, 0, 70, 70, 70, 70}, 1, new AnonymousClass11());
    }

    /* JADX INFO: renamed from: com.r7games.slotpantanal.MainActivity$11, reason: invalid class name */
    class AnonymousClass11 implements AnimatedSprite.IAnimationListener {
        AnonymousClass11() {
        }

        @Override // org.anddev.andengine.entity.sprite.AnimatedSprite.IAnimationListener
        public void onAnimationEnd(AnimatedSprite paramAnonymousAnimatedSprite) {
            MainActivity.this.runOnUpdateThread(new Runnable() { // from class: com.r7games.slotpantanal.MainActivity.11.1
                @Override // java.lang.Runnable
                public void run() {
                    if (!MainActivity.this.jogaAuto) {
                        MainActivity.this.canTouch = true;
                    }
                    if (MainActivity.this.saiAcum) {
                        MainActivity.this.canTouch = false;
                        MainActivity.this.canDouble = false;
                    }
                    MainActivity.this.removeAnimSlot(5);
                    try {
                        MainActivity.this.Verifica();
                    } catch (Exception localException) {
                        localException.printStackTrace();
                    }
                    if (MainActivity.this.aposta.intValue() > MainActivity.this.premio.intValue()) {
                        MainActivity.this.SetAcumulado(MainActivity.this.aposta.intValue() - MainActivity.this.premio.intValue());
                    }
                    MainActivity.this.mRunnable = new Runnable() { // from class: com.r7games.slotpantanal.MainActivity.11.1.1
                        @Override // java.lang.Runnable
                        public void run() {
                            MainActivity.this.verificando = false;
                        }
                    };
                    MainActivity.this.mHandler.postDelayed(MainActivity.this.mRunnable, 200L);
                }
            });
        }
    }

    public void CallBonus() {
        this.bonusShow = true;
        this.jogaAuto = false;
        RemoveAuto();
        this.canTouch = false;
        if (this.bonusToCall[0] <= 0) {
            if (this.bonusToCall[1] <= 0) {
                if (this.bonusToCall[2] <= 0) {
                    if (this.bonusToCall[3] > 0) {
                        ShowDados();
                        return;
                    } else {
                        this.bonusFim = true;
                        return;
                    }
                }
                ShowRoleta();
                return;
            }
            ShowCaldeirao();
            return;
        }
        ShowMorango();
    }

    public void ClickDobra(int paramInt) {
        boolean dobrou;
        this.dbase.SetReplayStatus(0);
        try {
            this.dobraScene.attachChild(this.miAnim[paramInt]);
        } catch (IllegalStateException e) {
            e.printStackTrace();
        }
        this.dobraCanTouch = false;
        int dobra = this.rand.nextInt(10);
        if (!this.gen.downDificult(0)) {
            if (dobra >= 7 && this.dobraQtd < 3) {
                dobrou = true;
            } else {
                dobrou = false;
            }
        } else if (dobra >= 5) {
            dobrou = true;
        } else {
            dobrou = false;
        }
        if (!dobrou || this.premio.intValue() >= 100000) {
            this.dobraEscolha = 0;
            this.dobraQtd = 0;
            if (this.SoundON) {
                this.bonusEndSound.play();
            }
            long[] arrayOfLong = new long[40];
            arrayOfLong[26] = 90;
            arrayOfLong[27] = 90;
            arrayOfLong[28] = 90;
            arrayOfLong[29] = 90;
            arrayOfLong[30] = 90;
            arrayOfLong[31] = 90;
            arrayOfLong[32] = 90;
            arrayOfLong[33] = 90;
            arrayOfLong[34] = 90;
            arrayOfLong[35] = 90;
            arrayOfLong[36] = 90;
            arrayOfLong[37] = 90;
            this.premio = 0;
            SetDobraPremio(this.premio);
            this.miAnim[paramInt].animate(arrayOfLong, false, new AnimatedSprite.IAnimationListener() { // from class: com.r7games.slotpantanal.MainActivity.12
                @Override // org.anddev.andengine.entity.sprite.AnimatedSprite.IAnimationListener
                public void onAnimationEnd(AnimatedSprite paramAnonymousAnimatedSprite) {
                    MainActivity.this.mRunnable = new Runnable() { // from class: com.r7games.slotpantanal.MainActivity.12.1
                        @Override // java.lang.Runnable
                        public void run() {
                            MainActivity.this.ExitDobra();
                        }
                    };
                    MainActivity.this.mHandler.postDelayed(MainActivity.this.mRunnable, 1500L);
                }
            });
            return;
        }
        this.dobraQtd++;
        if (this.SoundON) {
            this.bWinSound.play();
        }
        long[] arrayOfLong2 = new long[40];
        arrayOfLong2[12] = 90;
        arrayOfLong2[13] = 90;
        arrayOfLong2[14] = 90;
        arrayOfLong2[15] = 90;
        arrayOfLong2[16] = 90;
        arrayOfLong2[17] = 90;
        arrayOfLong2[18] = 90;
        arrayOfLong2[19] = 90;
        arrayOfLong2[20] = 90;
        arrayOfLong2[21] = 90;
        arrayOfLong2[22] = 90;
        arrayOfLong2[23] = 90;
        arrayOfLong2[24] = 90;
        arrayOfLong2[25] = 90;
        this.miAnim[paramInt].animate(arrayOfLong2, false, new AnimatedSprite.IAnimationListener() { // from class: com.r7games.slotpantanal.MainActivity.13
            @Override // org.anddev.andengine.entity.sprite.AnimatedSprite.IAnimationListener
            public void onAnimationEnd(AnimatedSprite paramAnonymousAnimatedSprite) {
                MainActivity.this.dobraCanTouch = true;
                MainActivity mainActivity = MainActivity.this;
                mainActivity.premio = Integer.valueOf(mainActivity.premio.intValue() * 2);
                MainActivity.this.SetDobraPremio(MainActivity.this.premio);
            }
        });
    }

    public void CreateMora(final int paramInt, float paramFloat1, float paramFloat2) {
        this.moraAnim[paramInt] = new AnimatedSprite(paramFloat1, paramFloat2, this.mAnimRegion.deepCopy()) { // from class: com.r7games.slotpantanal.MainActivity.14
            @Override // org.anddev.andengine.entity.shape.Shape, org.anddev.andengine.entity.scene.Scene.ITouchArea
            public boolean onAreaTouched(TouchEvent paramAnonymousTouchEvent, float paramAnonymousFloat1, float paramAnonymousFloat2) {
                if (paramAnonymousTouchEvent.getAction() == 0 && MainActivity.this.moraCanTouch && !MainActivity.this.moraAnimTouched[paramInt]) {
                    MainActivity.this.moraAnimTouched[paramInt] = true;
                    MainActivity.this.EscolheMorango(paramInt);
                    return false;
                }
                return false;
            }
        };
    }

    public void DrawAll() {
        System.out.println("Draw");
        DrawBackground();
        DrawSlots();
        DrawLines(this.lineBet.intValue());
        DrawButtons();
        DrawFont();
        ShowPago();
    }

    public void DrawBackground() {
        this.scene.attachChild(this.background);
    }

    public void DrawButtons() {
        this.scene.attachChild(this.btnMenu);
        this.scene.registerTouchArea(this.btnMenu);
        this.scene.attachChild(this.btSom);
        this.scene.registerTouchArea(this.btSom);
        if (this.sp.getBoolean("SoundON", true)) {
            this.btSom.setCurrentTileIndex(1);
        } else {
            this.SoundON = false;
            this.btSom.setCurrentTileIndex(0);
        }
        this.scene.attachChild(this.btnInfo);
        this.scene.registerTouchArea(this.btnInfo);
        this.scene.attachChild(this.btnJoga);
        this.scene.attachChild(this.btnAuto);
        this.scene.attachChild(this.btnAposta);
        this.scene.attachChild(this.btnLinha);
        this.scene.attachChild(this.btnTabela);
        this.scene.registerTouchArea(this.btnJoga);
        this.scene.registerTouchArea(this.btnAuto);
        this.scene.registerTouchArea(this.btnAposta);
        this.scene.registerTouchArea(this.btnLinha);
        this.scene.registerTouchArea(this.btnTabela);
    }

    public void DrawFont() {
        this.scene.attachChild(this.lineText);
        this.scene.attachChild(this.betText);
        this.scene.attachChild(this.apostaText);
        this.scene.attachChild(this.creditoText);
        this.scene.attachChild(this.premioText);
        this.scene.attachChild(this.acumMinText);
        this.scene.attachChild(this.acumMedText);
        this.scene.attachChild(this.acumMaxText);
        SetAposta();
        SetCredito();
        this.show_admob_interstitial = true;
        SetPremio();
        SetAcumMin();
        SetAcumMed();
        SetAcumMax();
    }

    public void DrawLineCrop(int paramInt) {
        for (int i = 0; i < paramInt; i++) {
            if (this.scene.getChildIndex(this.lineCrop[i]) < 0) {
                try {
                    this.scene.attachChild(this.lineCrop[i]);
                } catch (Exception e) {
                }
            }
        }
    }

    public void DrawLines(int paramInt) {
        for (int i = 0; i < paramInt; i++) {
            try {
                this.scene.attachChild(this.line[i]);
            } catch (Exception e) {
            }
        }
    }

    public void DrawSlots() {
        for (int j = 0; j < 5; j++) {
            for (int i = 0; i < 3; i++) {
                try {
                    this.scene.attachChild(this.slots[j][i]);
                } catch (Exception e) {
                }
            }
        }
        for (int i2 = 0; i2 < 5; i2++) {
            SetSlot(i2, this.rand.nextInt(10), this.rand.nextInt(10), this.rand.nextInt(10));
        }
    }

    public void EscolheAbobora(final int paramInt) {
        this.dbase.SetBonusToCall(0);
        if (this.SoundON) {
            this.bonusSelSound.play();
        }
        this.anbCanTouch = false;
        this.anbAnimTouched[paramInt] = true;
        long[] arrayOfLong = {0, 0, 0, 0, 0, 0, 50, 50, 50, 50, 50, 50, 50, 50, 50, 50, 50, 80, 50, 50, 50, 50, 50, 50, 50, 50, 50, 50, 50, 50, 50, 50, 50, 50, 50, 50};
        this.anbAnim[paramInt].animate(arrayOfLong, 0, new AnimatedSprite.IAnimationListener() { // from class: com.r7games.slotpantanal.MainActivity.15
            @Override // org.anddev.andengine.entity.sprite.AnimatedSprite.IAnimationListener
            public void onAnimationEnd(AnimatedSprite paramAnonymousAnimatedSprite) {
                MainActivity.this.SetAnbNum(paramInt);
            }
        });
    }

    public void EscolheCaldeirao(final int paramInt) {
        this.dbase.SetReplayStatus(0);
        if (this.SoundON) {
            this.bonusSelSound.play();
        }
        this.caldCanTouch = false;
        this.caldAnimTouched[paramInt] = true;
        long[] arrayOfLong = new long[25];
        arrayOfLong[16] = 80;
        arrayOfLong[17] = 80;
        arrayOfLong[18] = 80;
        arrayOfLong[19] = 80;
        arrayOfLong[20] = 80;
        arrayOfLong[21] = 80;
        arrayOfLong[22] = 80;
        arrayOfLong[23] = 80;
        arrayOfLong[24] = 80;
        this.caldAnim[paramInt].animate(arrayOfLong, 0, new AnimatedSprite.IAnimationListener() { // from class: com.r7games.slotpantanal.MainActivity.16
            @Override // org.anddev.andengine.entity.sprite.AnimatedSprite.IAnimationListener
            public void onAnimationEnd(AnimatedSprite paramAnonymousAnimatedSprite) {
                MainActivity.this.SetCalNum(paramInt);
            }
        });
    }

    public void EscolheDado(final int paramInt) {
        this.dbase.SetReplayStatus(0);
        if (this.SoundON) {
            this.bonusSelSound.play();
        }
        this.dadoCanTouch = false;
        this.dadoAnim[paramInt].animate(15L);
        IEntityModifier[] arrayOfIEntityModifier = {new PathModifier(0.7f, this.path[paramInt])};
        this.dadoModifier = new LoopEntityModifier(new SequenceEntityModifier(arrayOfIEntityModifier), 1, new IEntityModifier.IEntityModifierListener() { // from class: com.r7games.slotpantanal.MainActivity.17
            @Override // org.anddev.andengine.util.modifier.IModifier.IModifierListener
            public void onModifierFinished(IModifier<IEntity> paramAnonymousIModifier, IEntity paramAnonymousIEntity) {
                MainActivity.this.SetDado(paramInt);
            }

            @Override // org.anddev.andengine.util.modifier.IModifier.IModifierListener
            public void onModifierStarted(IModifier<IEntity> paramAnonymousIModifier, IEntity paramAnonymousIEntity) {
            }
        });
        this.dadoAnim[paramInt].registerEntityModifier(this.dadoModifier);
    }

    public void EscolheMorango(final int paramInt) {
        this.morangosEscolha++;
        this.moraCanTouch = false;
        Integer localInteger = Integer.valueOf(this.moraPrizeValues[this.moraPrizes[paramInt].intValue()].intValue() * this.apBet.intValue() * this.bonusToCall[0]);
        if (this.moraPrizes[paramInt].intValue() >= 6 || localInteger.intValue() == 0) {
            this.dbase.SetReplayStatus(0);
            Log.d("MAINACTIVITY", "IF MORAPRIZES");
            if (this.SoundON) {
                this.bonusEndSound.play();
            }
            this.moraCanTouch = false;
            long[] arrayOfLong = new long[40];
            arrayOfLong[26] = 90;
            arrayOfLong[27] = 90;
            arrayOfLong[28] = 90;
            arrayOfLong[29] = 90;
            arrayOfLong[30] = 90;
            arrayOfLong[31] = 90;
            arrayOfLong[32] = 90;
            arrayOfLong[33] = 90;
            arrayOfLong[34] = 90;
            arrayOfLong[35] = 90;
            arrayOfLong[36] = 90;
            arrayOfLong[37] = 90;
            this.moraAnim[paramInt].animate(arrayOfLong, 0, new AnimatedSprite.IAnimationListener() { // from class: com.r7games.slotpantanal.MainActivity.18
                @Override // org.anddev.andengine.entity.sprite.AnimatedSprite.IAnimationListener
                public void onAnimationEnd(AnimatedSprite paramAnonymousAnimatedSprite) {
                    MainActivity.this.mRunnable = new Runnable() { // from class: com.r7games.slotpantanal.MainActivity.18.1
                        @Override // java.lang.Runnable
                        public void run() {
                            MainActivity.this.MorangoSaida();
                        }
                    };
                    MainActivity.this.mHandler.postDelayed(MainActivity.this.mRunnable, 100L);
                }
            });
            return;
        }
        Log.d("MAINACTIVITY", "ELSE MORAPRIZES");
        long[] arrayOfLong2 = new long[40];
        arrayOfLong2[12] = 90;
        arrayOfLong2[13] = 90;
        arrayOfLong2[14] = 90;
        arrayOfLong2[15] = 90;
        arrayOfLong2[16] = 90;
        arrayOfLong2[17] = 90;
        arrayOfLong2[18] = 90;
        arrayOfLong2[19] = 90;
        arrayOfLong2[20] = 90;
        arrayOfLong2[21] = 90;
        arrayOfLong2[22] = 90;
        arrayOfLong2[23] = 90;
        arrayOfLong2[24] = 90;
        if (this.SoundON) {
            this.bWinSound.play();
        }
        this.moraAnim[paramInt].animate(arrayOfLong2, 0, new AnimatedSprite.IAnimationListener() { // from class: com.r7games.slotpantanal.MainActivity.19
            @Override // org.anddev.andengine.entity.sprite.AnimatedSprite.IAnimationListener
            public void onAnimationEnd(AnimatedSprite paramAnonymousAnimatedSprite) {
                MainActivity.this.SetMorango(paramInt);
                MainActivity.this.moraCanTouch = true;
            }
        });
    }

    public void ExitAbobora() {
        runOnUpdateThread(new Runnable() { // from class: com.r7games.slotpantanal.MainActivity.20
            @Override // java.lang.Runnable
            public void run() {
                MainActivity.this.aboboraScene.detachChildren();
                MainActivity.this.aboboraScene.detachSelf();
            }
        });
        this.anbCanTouch = true;
        this.anbAnimTouched[0] = false;
        this.anbAnimTouched[1] = false;
        this.anbAnimTouched[2] = false;
        this.anbAnimTouched[3] = false;
        this.anbAnimTouched[4] = false;
        this.canTouch = false;
        this.bonusShow = false;
        this.canDouble = true;
        this.bonusToCall[1] = 0;
        this.mEngine.setScene(this.scene);
        SetPremio();
        this.mRunnable = new Runnable() { // from class: com.r7games.slotpantanal.MainActivity.21
            @Override // java.lang.Runnable
            public void run() {
                MainActivity.this.CallBonus();
            }
        };
        this.mHandler.postDelayed(this.mRunnable, 200L);
    }

    public void ExitCaldeirao(int paramInt) {
        runOnUpdateThread(new Runnable() { // from class: com.r7games.slotpantanal.MainActivity.22
            @Override // java.lang.Runnable
            public void run() {
                MainActivity.this.caldeiraoScene.detachChildren();
                MainActivity.this.caldeiraoScene.detachSelf();
            }
        });
        this.caldCanTouch = true;
        this.caldAnimTouched[0] = false;
        this.caldAnimTouched[1] = false;
        this.caldAnimTouched[2] = false;
        this.caldAnimTouched[3] = false;
        this.caldAnimTouched[4] = false;
        if (paramInt != 0) {
            ShowAbobora();
            return;
        }
        this.canTouch = false;
        this.canDouble = true;
        this.bonusShow = false;
        this.bonusToCall[1] = 0;
        this.mEngine.setScene(this.scene);
        SetPremio();
        this.mRunnable = new Runnable() { // from class: com.r7games.slotpantanal.MainActivity.23
            @Override // java.lang.Runnable
            public void run() {
                MainActivity.this.CallBonus();
            }
        };
        this.mHandler.postDelayed(this.mRunnable, 200L);
    }

    public void ExitDados() {
        runOnUpdateThread(new Runnable() { // from class: com.r7games.slotpantanal.MainActivity.24
            @Override // java.lang.Runnable
            public void run() {
                MainActivity.this.dadosScene.detachChildren();
                MainActivity.this.dadosScene.detachSelf();
            }
        });
        this.dadoCanTouch = true;
        this.mEngine.setScene(this.scene);
        this.canTouch = false;
        this.canDouble = true;
        this.bonusShow = false;
        SetPremio();
        this.mRunnable = new Runnable() { // from class: com.r7games.slotpantanal.MainActivity.25
            @Override // java.lang.Runnable
            public void run() {
                MainActivity.this.CallBonus();
            }
        };
        this.mHandler.postDelayed(this.mRunnable, 200L);
    }

    public void ExitDobra() {
        runOnUpdateThread(new Runnable() { // from class: com.r7games.slotpantanal.MainActivity.26
            @Override // java.lang.Runnable
            public void run() {
                MainActivity.this.dobraScene.clearTouchAreas();
                MainActivity.this.dobraScene.detachChildren();
                MainActivity.this.dobraScene.detachSelf();
                MainActivity.this.dobraCanTouch = true;
                for (int i = 0; i < 4; i++) {
                    MainActivity.this.miAnim[i].stopAnimation(0);
                    MainActivity.this.mAnimTouch[i] = true;
                }
            }
        });
        this.canDouble = false;
        this.canTouch = true;
        this.mEngine.setScene(this.scene);
        RemoveDobraSpr();
        SetPremio();
        SetCredito();
    }

    public void ExitMorango() {
        runOnUpdateThread(new Runnable() { // from class: com.r7games.slotpantanal.MainActivity.27
            @Override // java.lang.Runnable
            public void run() {
                MainActivity.this.morangoScene.detachChildren();
                MainActivity.this.morangoScene.detachSelf();
            }
        });
        this.moraCanTouch = true;
        for (int i = 0; i < 12; i++) {
            this.moraAnimTouched[i] = false;
        }
        this.moranSound.stop();
        this.canTouch = false;
        this.bonusShow = false;
        this.morangosEscolha = 0;
        this.canDouble = true;
        this.bonusToCall[0] = 0;
        this.mEngine.setScene(this.scene);
        SetPremio();
        this.mRunnable = new Runnable() { // from class: com.r7games.slotpantanal.MainActivity.28
            @Override // java.lang.Runnable
            public void run() {
                MainActivity.this.CallBonus();
            }
        };
        this.mHandler.postDelayed(this.mRunnable, 200L);
    }

    public void ExitRoleta() {
        runOnUpdateThread(new Runnable() { // from class: com.r7games.slotpantanal.MainActivity.29
            @Override // java.lang.Runnable
            public void run() {
                MainActivity.this.roletaScene.detachChildren();
                MainActivity.this.roletaScene.detachSelf();
                MainActivity.this.roletaSpr.unregisterEntityModifier(MainActivity.this.rotationModifier);
            }
        });
        this.roletaCanTouch = true;
        this.mEngine.setScene(this.scene);
        this.canTouch = false;
        this.canDouble = true;
        this.bonusShow = false;
        SetPremio();
        this.mRunnable = new Runnable() { // from class: com.r7games.slotpantanal.MainActivity.30
            @Override // java.lang.Runnable
            public void run() {
                MainActivity.this.CallBonus();
            }
        };
        this.mHandler.postDelayed(this.mRunnable, 200L);
    }

    public void GenerateNumber(int statusReplay, String[] replay) {
        this.podePagar = this.jogadas >= 239;
        this.gen.Generate(this.apBet.intValue(), this.lineBet.intValue(), this.credito.intValue(), this.bloqueado, replay, statusReplay, this.podePagar);
        System.arraycopy(this.gen.slotVal, 0, this.slotTileValues, 0, this.gen.slotVal.length);
        try {
            this.score += ((double) this.aposta.intValue()) * 1.0E-4d;
            this.ed.putLong("score_halloween", (long) this.score);
            this.ed.apply();
            this.jogadas++;
            Log.i(PainelScreen.TAG, "Score: " + this.score);
            Log.i(PainelScreen.TAG, "Jogadas: " + this.jogadas);
            Log.i(PainelScreen.TAG, "Minimo: " + this.min);
            Log.i(PainelScreen.TAG, "Médio: " + this.med);
            Log.i(PainelScreen.TAG, "Maximo: " + this.max);
            Log.i(PainelScreen.TAG, "Pode Pagar: " + this.podePagar);
        } catch (Exception e) {
        }
    }

    public void GetDbValues() {
        this.credito = Integer.valueOf(this.dbase.GetCredito());
        this.acumMin = this.dbase.GetAcumulado(0);
        this.acumMed = this.dbase.GetAcumulado(1);
        this.acumMax = this.dbase.GetAcumulado(2);
        this.dbase.GetAdmin();
        this.acupor = Double.valueOf(5.0d);
    }

    public void InitAbobora() {
        float f = 84.0f;
        this.anbBgAtlas = new BitmapTextureAtlas(1024, 512, TextureOptions.DEFAULT);
        this.anbBgRegion = BitmapTextureAtlasTextureRegionFactory.createFromAsset(this.anbBgAtlas, this, "05_telab2.png", 0, 0);
        this.anbAnimAtlas = new BitmapTextureAtlas(1024, 2048, TextureOptions.DEFAULT);
        this.anbAnimRegion = BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(this.anbAnimAtlas, this, "05_anb.png", 0, 0, 6, 6);
        this.mEngine.getTextureManager().loadTexture(this.anbBgAtlas);
        this.mEngine.getTextureManager().loadTexture(this.anbAnimAtlas);
        this.anbCanTouch = true;
        this.anbAnimTouched = new boolean[5];
        this.anbAnimTouched[0] = false;
        this.anbAnimTouched[1] = false;
        this.anbAnimTouched[2] = false;
        this.anbAnimTouched[3] = false;
        this.anbAnimTouched[4] = false;
        this.anbBns = this.apBet.intValue() * this.bonusToCall[1];
        this.anbBgSpr = new Sprite(BitmapDescriptorFactory.HUE_RED, BitmapDescriptorFactory.HUE_RED, this.anbBgRegion);
        this.anbAnim = new AnimatedSprite[5];
        this.anbAnim[0] = new AnimatedSprite(25.0f, f, this.anbAnimRegion.deepCopy()) { // from class: com.r7games.slotpantanal.MainActivity.31
            @Override // org.anddev.andengine.entity.shape.Shape, org.anddev.andengine.entity.scene.Scene.ITouchArea
            public boolean onAreaTouched(TouchEvent paramAnonymousTouchEvent, float paramAnonymousFloat1, float paramAnonymousFloat2) {
                if (paramAnonymousTouchEvent.getAction() == 0 && MainActivity.this.anbCanTouch) {
                    MainActivity.this.EscolheAbobora(0);
                }
                return false;
            }
        };
        this.anbAnim[1] = new AnimatedSprite(175.0f, f, this.anbAnimRegion.deepCopy()) { // from class: com.r7games.slotpantanal.MainActivity.32
            @Override // org.anddev.andengine.entity.shape.Shape, org.anddev.andengine.entity.scene.Scene.ITouchArea
            public boolean onAreaTouched(TouchEvent paramAnonymousTouchEvent, float paramAnonymousFloat1, float paramAnonymousFloat2) {
                if (paramAnonymousTouchEvent.getAction() == 0 && MainActivity.this.anbCanTouch) {
                    MainActivity.this.EscolheAbobora(1);
                    return false;
                }
                return false;
            }
        };
        this.anbAnim[2] = new AnimatedSprite(325.0f, f, this.anbAnimRegion.deepCopy()) { // from class: com.r7games.slotpantanal.MainActivity.33
            @Override // org.anddev.andengine.entity.shape.Shape, org.anddev.andengine.entity.scene.Scene.ITouchArea
            public boolean onAreaTouched(TouchEvent paramAnonymousTouchEvent, float paramAnonymousFloat1, float paramAnonymousFloat2) {
                if (paramAnonymousTouchEvent.getAction() == 0 && MainActivity.this.anbCanTouch) {
                    MainActivity.this.EscolheAbobora(2);
                    return false;
                }
                return false;
            }
        };
        this.anbAnim[3] = new AnimatedSprite(475.0f, f, this.anbAnimRegion.deepCopy()) { // from class: com.r7games.slotpantanal.MainActivity.34
            @Override // org.anddev.andengine.entity.shape.Shape, org.anddev.andengine.entity.scene.Scene.ITouchArea
            public boolean onAreaTouched(TouchEvent paramAnonymousTouchEvent, float paramAnonymousFloat1, float paramAnonymousFloat2) {
                if (paramAnonymousTouchEvent.getAction() == 0 && MainActivity.this.anbCanTouch) {
                    MainActivity.this.EscolheAbobora(3);
                    return false;
                }
                return false;
            }
        };
        this.anbAnim[4] = new AnimatedSprite(625.0f, f, this.anbAnimRegion.deepCopy()) { // from class: com.r7games.slotpantanal.MainActivity.35
            @Override // org.anddev.andengine.entity.shape.Shape, org.anddev.andengine.entity.scene.Scene.ITouchArea
            public boolean onAreaTouched(TouchEvent paramAnonymousTouchEvent, float paramAnonymousFloat1, float paramAnonymousFloat2) {
                if (paramAnonymousTouchEvent.getAction() == 0 && MainActivity.this.anbCanTouch) {
                    MainActivity.this.EscolheAbobora(4);
                    return false;
                }
                return false;
            }
        };
        this.anbBonus = new ChangeableText(200.0f, 372.0f, this.cFont, "0", 8);
        this.anbCredito = new ChangeableText(200.0f, 410.0f, this.cFont, "0", 8);
        this.anbPremio = new ChangeableText(200.0f, 446.0f, this.cFont, "0", 8);
    }

    public void InitAll() {
        InitBackground();
        InitSound();
        InitSlots();
        InitAninSlotWin();
        InitLines();
        GetDbValues();
        InitFont();
        InitButtons();
        InitAubra();
        InitTabela();
        InitDobra();
        InitRoleta();
        InitDados();
        InitCaldeirao();
        InitAbobora();
        InitMorango();
    }

    public void InitAninSlotWin() {
        int[] arrayOfInt = {5, 3};
        this.animSlotWin = (AnimatedSprite[][]) Array.newInstance((Class<?>) AnimatedSprite.class, arrayOfInt);
        for (int j = 0; j < 5; j++) {
            for (int i = 0; i < 3; i++) {
                this.animSlotWin[j][i] = new AnimatedSprite(BitmapDescriptorFactory.HUE_RED, BitmapDescriptorFactory.HUE_RED, this.slotsRegion.deepCopy());
            }
        }
    }

    public void InitAubra() {
        this.jogaAutoAtlas = new BitmapTextureAtlas(256, 16, TextureOptions.DEFAULT);
        this.jogaAutoRegion = BitmapTextureAtlasTextureRegionFactory.createFromAsset(this.jogaAutoAtlas, this, "05_auto.png", 0, 0);
        this.pagoRegion = BitmapTextureAtlasTextureRegionFactory.createFromAsset(this.jogaAutoAtlas, this, "pago.png", 152, 0);
        this.dobraAtlas = new BitmapTextureAtlas(128, 64, TextureOptions.DEFAULT);
        this.dobraRegion = BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(this.dobraAtlas, this, "05_dobra.png", 0, 0, 1, 2);
        this.mEngine.getTextureManager().loadTexture(this.jogaAutoAtlas);
        this.mEngine.getTextureManager().loadTexture(this.dobraAtlas);
        this.jogaAutoSpr = new Sprite(333.0f, 52.0f, this.jogaAutoRegion);
        this.pagoSpr = new Sprite(53.0f, 57.0f, this.pagoRegion);
        this.pFontAtlas = new BitmapTextureAtlas(256, 256, TextureOptions.BILINEAR_PREMULTIPLYALPHA);
        this.pFont = new Font(this.pFontAtlas, Typeface.create(Typeface.DEFAULT, 3), 18.0f, true, -1);
        this.mEngine.getTextureManager().loadTexture(this.pFontAtlas);
        this.mEngine.getFontManager().loadFont(this.pFont);
        this.pagoText = new ChangeableText(100.0f, 52.0f, this.pFont, "0", 8);
        this.dobraSpr = new AnimatedSprite(175.0f, 50.0f, this.dobraRegion);
    }

    public void InitBackground() {
        this.backgroundAtlas = new BitmapTextureAtlas(1024, 1024, TextureOptions.DEFAULT);
        this.backgroundRegion = BitmapTextureAtlasTextureRegionFactory.createFromAsset(this.backgroundAtlas, this, "05_telabot.png", 0, 0);
        this.mEngine.getTextureManager().loadTexture(this.backgroundAtlas);
        this.background = new Sprite(BitmapDescriptorFactory.HUE_RED, BitmapDescriptorFactory.HUE_RED, this.backgroundRegion);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void NovaJogada() {
        Log.i("Jogando", "Realizando uma nova jogada");
        this.canTouch = false;
        this.bonusFim = false;
        this.credito = Integer.valueOf(this.credito.intValue() - this.aposta.intValue());
        RemoveDobraSpr();
        SetCredito();
        GenerateNumber(0, null);
        AnimSlot();
        if (this.SoundON) {
            this.playSound.play();
        }
        SetCredito();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void ParadaAntecipada() {
        Log.i("Jogando", "Parada antecipada");
        this.canSkip = false;
        runOnUpdateThread(new Runnable() { // from class: com.r7games.slotpantanal.MainActivity.36
            @Override // java.lang.Runnable
            public void run() {
                for (int i = 0; i < 5; i++) {
                    if (MainActivity.this.animSlot[i].isAnimationRunning()) {
                        MainActivity.this.removeAnimSlot(i);
                    }
                }
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void CreditarJogada() {
        Log.i("Jogando", "Creditando o valor ganho");
        RemoveDobraSpr();
        this.credito = Integer.valueOf(this.credito.intValue() + this.premio.intValue());
        this.dbase.SetReplayStatus(0);
        SetPago(this.premio.intValue());
        this.premio = 0;
        SetPremio();
        SetCredito();
        this.canDouble = false;
        this.premiacao = false;
        this.canTouch = true;
    }

    public void InitButtons() {
        float f = BitmapDescriptorFactory.HUE_RED;
        float f2 = 713.0f;
        this.btnAtlas = new BitmapTextureAtlas(512, 128, TextureOptions.NEAREST_PREMULTIPLYALPHA);
        this.btnAtlas2 = new BitmapTextureAtlas(512, 128, TextureOptions.NEAREST_PREMULTIPLYALPHA);
        this.btnJogaRegion = BitmapTextureAtlasTextureRegionFactory.createFromAsset(this.btnAtlas, this, "joga.png", 0, 0);
        this.btnInfoAtlas = new BitmapTextureAtlas(512, 128, TextureOptions.NEAREST_PREMULTIPLYALPHA);
        this.btnAutoRegion = BitmapTextureAtlasTextureRegionFactory.createFromAsset(this.btnAtlas, this, "btnauto.png", 87, 0);
        this.btnApostaRegion = BitmapTextureAtlasTextureRegionFactory.createFromAsset(this.btnAtlas, this, "aposta.png", 174, 0);
        this.btnLinhaRegion = BitmapTextureAtlasTextureRegionFactory.createFromAsset(this.btnAtlas, this, "linha.png", 261, 0);
        this.btnTabelaRegion = BitmapTextureAtlasTextureRegionFactory.createFromAsset(this.btnAtlas, this, "tabela.png", 348, 0);
        this.btnMenuRegion = BitmapTextureAtlasTextureRegionFactory.createFromAsset(this.btnAtlas2, this, "btnmenu.png", 0, 0);
        this.btnInfoRegion = BitmapTextureAtlasTextureRegionFactory.createFromAsset(this.btnInfoAtlas, this, "info.png", 0, 0);
        this.btSomAtlas = new BitmapTextureAtlas(64, 64, TextureOptions.DEFAULT);
        this.btSomRegion = BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(this.btSomAtlas, this, "som.png", 0, 0, 1, 2);
        this.mEngine.getTextureManager().loadTexture(this.btSomAtlas);
        this.mEngine.getTextureManager().loadTexture(this.btnInfoAtlas);
        this.mEngine.getTextureManager().loadTexture(this.btnAtlas);
        this.mEngine.getTextureManager().loadTexture(this.btnAtlas2);
        this.btnJoga = new Sprite(f2, 384.0f, this.btnJogaRegion) { // from class: com.r7games.slotpantanal.MainActivity.37
            @Override // org.anddev.andengine.entity.shape.Shape, org.anddev.andengine.entity.scene.Scene.ITouchArea
            public boolean onAreaTouched(TouchEvent paramAnonymousTouchEvent, float paramAnonymousFloat1, float paramAnonymousFloat2) {
                if (!MainActivity.this.jogaAuto && !MainActivity.this.fimTorneio && !MainActivity.this.verificando && MainActivity.this.bonusToCall[0] <= 0 && MainActivity.this.bonusToCall[1] <= 0 && MainActivity.this.bonusToCall[2] <= 0 && MainActivity.this.bonusToCall[3] <= 0) {
                    if (paramAnonymousTouchEvent.getAction() != 0 || !MainActivity.this.canTouch || MainActivity.this.credito.intValue() < MainActivity.this.aposta.intValue() || MainActivity.this.jogaAuto) {
                        if (paramAnonymousTouchEvent.getAction() != 0 || MainActivity.this.canTouch || !MainActivity.this.canDouble || MainActivity.this.jogaAuto) {
                            if (paramAnonymousTouchEvent.getAction() == 0 && MainActivity.this.canSkip && !MainActivity.this.jogaAuto) {
                                MainActivity.this.ParadaAntecipada();
                            }
                        } else if (!MainActivity.this.premiacao) {
                            MainActivity.this.NovaJogada();
                        } else {
                            MainActivity.this.CreditarJogada();
                        }
                    } else if (!MainActivity.this.premiacao) {
                        MainActivity.this.NovaJogada();
                    } else {
                        MainActivity.this.CreditarJogada();
                    }
                    if (MainActivity.this.bonusFim) {
                        MainActivity.this.canTouch = true;
                    }
                    MainActivity.this.SetDbValues();
                }
                return false;
            }
        };
        this.btnMenu = new Sprite(f, f, this.btnMenuRegion) { // from class: com.r7games.slotpantanal.MainActivity.38
            @Override // org.anddev.andengine.entity.shape.Shape, org.anddev.andengine.entity.scene.Scene.ITouchArea
            public boolean onAreaTouched(TouchEvent paramAnonymousTouchEvent, float paramAnonymousFloat1, float paramAnonymousFloat2) {
                if (paramAnonymousTouchEvent.getAction() == 0) {
                    try {
                        if (MainActivity.this.adMob_interstitial != null) {
                            MainActivity.this.runOnUiThread(new Runnable() { // from class: com.r7games.slotpantanal.MainActivity.38.1
                                @Override // java.lang.Runnable
                                public void run() {
                                    if (MainActivity.this.adMob_interstitial.isLoaded() && MainActivity.this.show_admob_interstitial) {
                                        Log.i("ADMOB", "AD IS LOAD");
                                        MainActivity.this.adMob_interstitial.show();
                                    } else {
                                        Log.i("ADMOB", "AD IS NOT LOAD");
                                        MainActivity.this.ShowMenu();
                                    }
                                }
                            });
                            MainActivity.this.adMob_interstitial.setAdListener(new AdListener() { // from class: com.r7games.slotpantanal.MainActivity.38.2
                                @Override // com.google.android.gms.ads.AdListener
                                public void onAdClosed() {
                                    MainActivity.this.ShowMenu();
                                }
                            });
                        } else {
                            MainActivity.this.ShowMenu();
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
        this.btSom = new AnimatedSprite(5.0f, 444.0f, this.btSomRegion.deepCopy()) { // from class: com.r7games.slotpantanal.MainActivity.39
            @Override // org.anddev.andengine.entity.shape.Shape, org.anddev.andengine.entity.scene.Scene.ITouchArea
            public boolean onAreaTouched(TouchEvent touchEvent, float n, float n2) {
                if (touchEvent.getAction() == 0) {
                    if (MainActivity.this.btSom.getCurrentTileIndex() == 1) {
                        MainActivity.this.btSom.setCurrentTileIndex(0);
                        MainActivity.this.SoundON = false;
                        MainActivity.this.ed.putBoolean("SoundON", MainActivity.this.SoundON);
                        MainActivity.this.ed.apply();
                    } else {
                        MainActivity.this.btSom.setCurrentTileIndex(1);
                        MainActivity.this.SoundON = true;
                        MainActivity.this.ed.putBoolean("SoundON", MainActivity.this.SoundON);
                        MainActivity.this.ed.apply();
                    }
                }
                return false;
            }
        };
        this.btnInfo = new Sprite(f, 407.0f, this.btnInfoRegion) { // from class: com.r7games.slotpantanal.MainActivity.40
            @Override // org.anddev.andengine.entity.shape.Shape, org.anddev.andengine.entity.scene.Scene.ITouchArea
            public boolean onAreaTouched(TouchEvent paramAnonymousTouchEvent, float paramAnonymousFloat1, float paramAnonymousFloat2) {
                if (paramAnonymousTouchEvent.getAction() == 0 && MainActivity.this.canTouch) {
                    MainActivity.this.ShowTabela();
                    return false;
                }
                return false;
            }
        };
        this.btnAuto = new Sprite(f2, 288.0f, this.btnAutoRegion) { // from class: com.r7games.slotpantanal.MainActivity.41
            @Override // org.anddev.andengine.entity.shape.Shape, org.anddev.andengine.entity.scene.Scene.ITouchArea
            public boolean onAreaTouched(TouchEvent paramAnonymousTouchEvent, float paramAnonymousFloat1, float paramAnonymousFloat2) {
                if (paramAnonymousTouchEvent.getAction() != 0 || !MainActivity.this.canTouch || MainActivity.this.jogaAuto || MainActivity.this.credito.intValue() < MainActivity.this.aposta.intValue()) {
                    if (paramAnonymousTouchEvent.getAction() == 0 && !MainActivity.this.canTouch && MainActivity.this.jogaAuto && MainActivity.this.animSlot[5].isAnimationRunning()) {
                        MainActivity.this.RemoveAuto();
                        MainActivity.this.jogaAuto = false;
                    }
                } else {
                    MainActivity.this.canTouch = false;
                    MainActivity.this.jogaAuto = true;
                    MainActivity.this.ShowAuto();
                    MainActivity.this.JogaAuto();
                }
                return false;
            }
        };
        this.btnAposta = new Sprite(f2, 192.0f, this.btnApostaRegion) { // from class: com.r7games.slotpantanal.MainActivity.42
            @Override // org.anddev.andengine.entity.shape.Shape, org.anddev.andengine.entity.scene.Scene.ITouchArea
            public boolean onAreaTouched(TouchEvent paramAnonymousTouchEvent, float paramAnonymousFloat1, float paramAnonymousFloat2) {
                if (paramAnonymousTouchEvent.getAction() == 0 && MainActivity.this.canTouch) {
                    MainActivity mainActivity = MainActivity.this;
                    mainActivity.apBet = Integer.valueOf(mainActivity.apBet.intValue() + 1);
                    if (MainActivity.this.apBet.intValue() > 40) {
                        MainActivity.this.apBet = 1;
                    }
                    MainActivity.this.SetBet();
                    if (MainActivity.this.SoundON) {
                        MainActivity.this.linhaSound.play();
                        return false;
                    }
                    return false;
                }
                return false;
            }
        };
        this.btnLinha = new Sprite(f2, 96.0f, this.btnLinhaRegion) { // from class: com.r7games.slotpantanal.MainActivity.43
            @Override // org.anddev.andengine.entity.shape.Shape, org.anddev.andengine.entity.scene.Scene.ITouchArea
            public boolean onAreaTouched(TouchEvent paramAnonymousTouchEvent, float paramAnonymousFloat1, float paramAnonymousFloat2) {
                if (!MainActivity.this.jogaAuto) {
                    if (paramAnonymousTouchEvent.getAction() == 0 && !MainActivity.this.canTouch && MainActivity.this.canDouble && !MainActivity.this.jogaAuto) {
                        MainActivity.this.ShowDobra();
                    } else {
                        if (paramAnonymousTouchEvent.getAction() == 0 && MainActivity.this.canTouch) {
                            switch (MainActivity.this.lineBet.intValue()) {
                                case 1:
                                    try {
                                        MainActivity.this.RemoveLines();
                                        MainActivity.this.RemoveLineCrop();
                                        Thread.sleep(200L);
                                        MainActivity.this.lineBet = 5;
                                    } catch (Exception localException1) {
                                        localException1.printStackTrace();
                                    }
                                    break;
                                case 5:
                                    try {
                                        MainActivity.this.RemoveLines();
                                        MainActivity.this.RemoveLineCrop();
                                        Thread.sleep(200L);
                                        MainActivity.this.lineBet = 9;
                                    } catch (Exception localException2) {
                                        localException2.printStackTrace();
                                    }
                                    break;
                                case 9:
                                    try {
                                        MainActivity.this.RemoveLines();
                                        MainActivity.this.RemoveLineCrop();
                                        Thread.sleep(200L);
                                        MainActivity.this.lineBet = 15;
                                    } catch (Exception localException3) {
                                        localException3.printStackTrace();
                                    }
                                    break;
                                case 15:
                                    try {
                                        MainActivity.this.RemoveLines();
                                        MainActivity.this.RemoveLineCrop();
                                        Thread.sleep(200L);
                                        MainActivity.this.lineBet = 25;
                                    } catch (Exception localException4) {
                                        localException4.printStackTrace();
                                    }
                                    break;
                                case 25:
                                    try {
                                        MainActivity.this.RemoveLines();
                                        MainActivity.this.RemoveLineCrop();
                                        Thread.sleep(200L);
                                        MainActivity.this.lineBet = 1;
                                    } catch (Exception localException5) {
                                        localException5.printStackTrace();
                                    }
                                    break;
                            }
                        }
                        if (MainActivity.this.SoundON) {
                            MainActivity.this.linhaSound.play();
                        }
                        MainActivity.this.SetLine();
                        MainActivity.this.SetAposta();
                    }
                }
                return false;
            }
        };
        this.btnTabela = new Sprite(f2, f, this.btnTabelaRegion) { // from class: com.r7games.slotpantanal.MainActivity.44
            @Override // org.anddev.andengine.entity.shape.Shape, org.anddev.andengine.entity.scene.Scene.ITouchArea
            public boolean onAreaTouched(TouchEvent paramAnonymousTouchEvent, float paramAnonymousFloat1, float paramAnonymousFloat2) {
                if (paramAnonymousTouchEvent.getAction() == 0 && MainActivity.this.canTouch) {
                    MainActivity.this.apBet = 40;
                }
                MainActivity.this.SetBet();
                try {
                    MainActivity.this.RemoveLines();
                    MainActivity.this.RemoveLineCrop();
                    Thread.sleep(200L);
                    MainActivity.this.lineBet = 25;
                } catch (Exception localException4) {
                    localException4.printStackTrace();
                }
                if (MainActivity.this.SoundON) {
                    MainActivity.this.linhaSound.play();
                }
                MainActivity.this.SetLine();
                MainActivity.this.SetAposta();
                return false;
            }
        };
    }

    public void InitCaldeirao() {
        float f = 84.0f;
        this.caldBgAtlas = new BitmapTextureAtlas(1024, 512, TextureOptions.DEFAULT);
        this.caldBgRegion = BitmapTextureAtlasTextureRegionFactory.createFromAsset(this.caldBgAtlas, this, "05_telab2b.png", 0, 0);
        this.caldAnimAtlas = new BitmapTextureAtlas(1024, 2048, TextureOptions.DEFAULT);
        this.caldAnimRegion = BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(this.caldAnimAtlas, this, "05_cal.png", 0, 0, 5, 5);
        this.numAtlas = new BitmapTextureAtlas(512, 256, TextureOptions.DEFAULT);
        this.numRegion = new TextureRegion[9];
        this.numRegion[0] = BitmapTextureAtlasTextureRegionFactory.createFromAsset(this.numAtlas, this, "05_50.png", 0, 0);
        this.numRegion[1] = BitmapTextureAtlasTextureRegionFactory.createFromAsset(this.numAtlas, this, "05_75.png", 77, 0);
        this.numRegion[2] = BitmapTextureAtlasTextureRegionFactory.createFromAsset(this.numAtlas, this, "05_100.png", 156, 0);
        this.numRegion[3] = BitmapTextureAtlasTextureRegionFactory.createFromAsset(this.numAtlas, this, "05_150.png", 272, 0);
        this.numRegion[4] = BitmapTextureAtlasTextureRegionFactory.createFromAsset(this.numAtlas, this, "05_200.png", 0, 51);
        this.numRegion[5] = BitmapTextureAtlasTextureRegionFactory.createFromAsset(this.numAtlas, this, "05_250.png", 96, 51);
        this.numRegion[6] = BitmapTextureAtlasTextureRegionFactory.createFromAsset(this.numAtlas, this, "05_500.png", 192, 51);
        this.numRegion[7] = BitmapTextureAtlasTextureRegionFactory.createFromAsset(this.numAtlas, this, "05_1000.png", 286, 51);
        this.numRegion[8] = BitmapTextureAtlasTextureRegionFactory.createFromAsset(this.numAtlas, this, "05_bonus.png", 0, 93);
        this.mEngine.getTextureManager().loadTexture(this.caldBgAtlas);
        this.mEngine.getTextureManager().loadTexture(this.caldAnimAtlas);
        this.mEngine.getTextureManager().loadTexture(this.numAtlas);
        this.caldCanTouch = true;
        this.caldAnimTouched = new boolean[5];
        this.caldAnimTouched[0] = false;
        this.caldAnimTouched[1] = false;
        this.caldAnimTouched[2] = false;
        this.caldAnimTouched[3] = false;
        this.caldAnimTouched[4] = false;
        this.caldBns = this.apBet.intValue() * this.bonusToCall[1];
        this.caldPrizeIndex = 8;
        this.caldBgSpr = new Sprite(BitmapDescriptorFactory.HUE_RED, BitmapDescriptorFactory.HUE_RED, this.caldBgRegion);
        this.caldAnim = new AnimatedSprite[5];
        this.caldAnim[0] = new AnimatedSprite(25.0f, f, this.caldAnimRegion.deepCopy()) { // from class: com.r7games.slotpantanal.MainActivity.45
            @Override // org.anddev.andengine.entity.shape.Shape, org.anddev.andengine.entity.scene.Scene.ITouchArea
            public boolean onAreaTouched(TouchEvent paramAnonymousTouchEvent, float paramAnonymousFloat1, float paramAnonymousFloat2) {
                if (paramAnonymousTouchEvent.getAction() == 0 && MainActivity.this.caldCanTouch) {
                    MainActivity.this.EscolheCaldeirao(0);
                }
                return false;
            }
        };
        this.caldAnim[1] = new AnimatedSprite(175.0f, f, this.caldAnimRegion.deepCopy()) { // from class: com.r7games.slotpantanal.MainActivity.46
            @Override // org.anddev.andengine.entity.shape.Shape, org.anddev.andengine.entity.scene.Scene.ITouchArea
            public boolean onAreaTouched(TouchEvent paramAnonymousTouchEvent, float paramAnonymousFloat1, float paramAnonymousFloat2) {
                if (paramAnonymousTouchEvent.getAction() == 0 && MainActivity.this.caldCanTouch) {
                    MainActivity.this.EscolheCaldeirao(1);
                    return false;
                }
                return false;
            }
        };
        this.caldAnim[2] = new AnimatedSprite(325.0f, f, this.caldAnimRegion.deepCopy()) { // from class: com.r7games.slotpantanal.MainActivity.47
            @Override // org.anddev.andengine.entity.shape.Shape, org.anddev.andengine.entity.scene.Scene.ITouchArea
            public boolean onAreaTouched(TouchEvent paramAnonymousTouchEvent, float paramAnonymousFloat1, float paramAnonymousFloat2) {
                if (paramAnonymousTouchEvent.getAction() == 0 && MainActivity.this.caldCanTouch) {
                    MainActivity.this.EscolheCaldeirao(2);
                    return false;
                }
                return false;
            }
        };
        this.caldAnim[3] = new AnimatedSprite(475.0f, f, this.caldAnimRegion.deepCopy()) { // from class: com.r7games.slotpantanal.MainActivity.48
            @Override // org.anddev.andengine.entity.shape.Shape, org.anddev.andengine.entity.scene.Scene.ITouchArea
            public boolean onAreaTouched(TouchEvent paramAnonymousTouchEvent, float paramAnonymousFloat1, float paramAnonymousFloat2) {
                if (paramAnonymousTouchEvent.getAction() == 0 && MainActivity.this.caldCanTouch) {
                    MainActivity.this.EscolheCaldeirao(3);
                    return false;
                }
                return false;
            }
        };
        this.caldAnim[4] = new AnimatedSprite(625.0f, f, this.caldAnimRegion.deepCopy()) { // from class: com.r7games.slotpantanal.MainActivity.49
            @Override // org.anddev.andengine.entity.shape.Shape, org.anddev.andengine.entity.scene.Scene.ITouchArea
            public boolean onAreaTouched(TouchEvent paramAnonymousTouchEvent, float paramAnonymousFloat1, float paramAnonymousFloat2) {
                if (paramAnonymousTouchEvent.getAction() == 0 && MainActivity.this.caldCanTouch) {
                    MainActivity.this.EscolheCaldeirao(4);
                    return false;
                }
                return false;
            }
        };
        this.numSpr = new Sprite[9];
        for (int i = 0; i < 9; i++) {
            this.numSpr[i] = new Sprite(BitmapDescriptorFactory.HUE_RED, BitmapDescriptorFactory.HUE_RED, this.numRegion[i]);
        }
        this.caldBonus = new ChangeableText(200.0f, 369.0f, this.cFont, "0", 8);
        this.caldCredito = new ChangeableText(200.0f, 407.0f, this.cFont, "0", 8);
        this.caldPremio = new ChangeableText(200.0f, 443.0f, this.cFont, "0", 8);
    }

    public void InitDados() {
        float f = 88.0f;
        this.dadoAtlas = new BitmapTextureAtlas(1024, 2048, TextureOptions.DEFAULT);
        this.dadoAnimRegion = BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(this.dadoAtlas, this, "dado.png", 0, 0, 8, 9);
        this.dadoBgRegion = BitmapTextureAtlasTextureRegionFactory.createFromAsset(this.dadoAtlas, this, "05_telab3.png", 0, 1152);
        this.mEngine.getTextureManager().loadTexture(this.dadoAtlas);
        this.dadoBgSpr = new Sprite(BitmapDescriptorFactory.HUE_RED, BitmapDescriptorFactory.HUE_RED, this.dadoBgRegion);
        this.dadoCanTouch = true;
        this.path = new PathModifier.Path[5];
        this.path[0] = new PathModifier.Path(2).to(26.0f, 88.0f).to(26.0f, 265.0f);
        this.path[1] = new PathModifier.Path(2).to(180.0f, 88.0f).to(180.0f, 265.0f);
        this.path[2] = new PathModifier.Path(2).to(334.0f, 88.0f).to(334.0f, 265.0f);
        this.path[3] = new PathModifier.Path(2).to(488.0f, 88.0f).to(488.0f, 265.0f);
        this.path[4] = new PathModifier.Path(2).to(642.0f, 88.0f).to(642.0f, 265.0f);
        this.dadoAnim = new AnimatedSprite[5];
        this.dadoAnim[0] = new AnimatedSprite(26.0f, f, this.dadoAnimRegion.deepCopy()) { // from class: com.r7games.slotpantanal.MainActivity.50
            @Override // org.anddev.andengine.entity.shape.Shape, org.anddev.andengine.entity.scene.Scene.ITouchArea
            public boolean onAreaTouched(TouchEvent paramAnonymousTouchEvent, float paramAnonymousFloat1, float paramAnonymousFloat2) {
                if (paramAnonymousTouchEvent.getAction() == 0 && MainActivity.this.dadoCanTouch) {
                    MainActivity.this.EscolheDado(0);
                }
                return false;
            }
        };
        this.dadoAnim[1] = new AnimatedSprite(180.0f, f, this.dadoAnimRegion.deepCopy()) { // from class: com.r7games.slotpantanal.MainActivity.51
            @Override // org.anddev.andengine.entity.shape.Shape, org.anddev.andengine.entity.scene.Scene.ITouchArea
            public boolean onAreaTouched(TouchEvent paramAnonymousTouchEvent, float paramAnonymousFloat1, float paramAnonymousFloat2) {
                if (paramAnonymousTouchEvent.getAction() == 0 && MainActivity.this.dadoCanTouch) {
                    MainActivity.this.EscolheDado(1);
                    return false;
                }
                return false;
            }
        };
        this.dadoAnim[2] = new AnimatedSprite(334.0f, f, this.dadoAnimRegion.deepCopy()) { // from class: com.r7games.slotpantanal.MainActivity.52
            @Override // org.anddev.andengine.entity.shape.Shape, org.anddev.andengine.entity.scene.Scene.ITouchArea
            public boolean onAreaTouched(TouchEvent paramAnonymousTouchEvent, float paramAnonymousFloat1, float paramAnonymousFloat2) {
                if (paramAnonymousTouchEvent.getAction() == 0 && MainActivity.this.dadoCanTouch) {
                    MainActivity.this.EscolheDado(2);
                    return false;
                }
                return false;
            }
        };
        this.dadoAnim[3] = new AnimatedSprite(488.0f, f, this.dadoAnimRegion.deepCopy()) { // from class: com.r7games.slotpantanal.MainActivity.53
            @Override // org.anddev.andengine.entity.shape.Shape, org.anddev.andengine.entity.scene.Scene.ITouchArea
            public boolean onAreaTouched(TouchEvent paramAnonymousTouchEvent, float paramAnonymousFloat1, float paramAnonymousFloat2) {
                if (paramAnonymousTouchEvent.getAction() == 0 && MainActivity.this.dadoCanTouch) {
                    MainActivity.this.EscolheDado(3);
                    return false;
                }
                return false;
            }
        };
        this.dadoAnim[4] = new AnimatedSprite(642.0f, f, this.dadoAnimRegion.deepCopy()) { // from class: com.r7games.slotpantanal.MainActivity.54
            @Override // org.anddev.andengine.entity.shape.Shape, org.anddev.andengine.entity.scene.Scene.ITouchArea
            public boolean onAreaTouched(TouchEvent paramAnonymousTouchEvent, float paramAnonymousFloat1, float paramAnonymousFloat2) {
                if (paramAnonymousTouchEvent.getAction() == 0 && MainActivity.this.dadoCanTouch) {
                    MainActivity.this.EscolheDado(4);
                    return false;
                }
                return false;
            }
        };
        this.dadoPremio = new ChangeableText(650.0f, 415.0f, this.cFont, "0", 8);
    }

    public void InitFont() {
        this.lineFontAtlas = new BitmapTextureAtlas(256, 256, TextureOptions.BILINEAR_PREMULTIPLYALPHA);
        this.lineFont = new Font(this.lineFontAtlas, Typeface.create(Typeface.DEFAULT, 1), 26.0f, true, -1);
        this.betFontAtlas = new BitmapTextureAtlas(256, 256, TextureOptions.BILINEAR_PREMULTIPLYALPHA);
        this.betFont = new Font(this.betFontAtlas, Typeface.create(Typeface.DEFAULT, 1), 20.0f, true, -1);
        this.cFontAtlas = new BitmapTextureAtlas(256, 256, TextureOptions.BILINEAR_PREMULTIPLYALPHA);
        this.cFont = new Font(this.cFontAtlas, Typeface.create(Typeface.DEFAULT, 1), 28.0f, true, -1);
        this.mEngine.getTextureManager().loadTexture(this.lineFontAtlas);
        this.mEngine.getTextureManager().loadTexture(this.betFontAtlas);
        this.mEngine.getTextureManager().loadTexture(this.cFontAtlas);
        this.mEngine.getFontManager().loadFont(this.lineFont);
        this.mEngine.getFontManager().loadFont(this.betFont);
        this.mEngine.getFontManager().loadFont(this.cFont);
        this.lineText = new ChangeableText(748.0f, 132.0f, this.lineFont, this.lineBet.toString(), HorizontalAlign.CENTER, 2);
        this.betText = new ChangeableText(545.0f, 44.0f, this.betFont, this.apBet.toString(), HorizontalAlign.CENTER, 4);
        this.apostaText = new ChangeableText(630.0f, 12.0f, this.cFont, this.aposta.toString(), HorizontalAlign.CENTER, 4);
        this.creditoText = new ChangeableText(185.0f, 12.0f, this.cFont, this.credito.toString(), HorizontalAlign.CENTER, 8);
        this.premioText = new ChangeableText(435.0f, 12.0f, this.cFont, this.premio.toString(), HorizontalAlign.CENTER, 8);
        this.acumMinText = new ChangeableText(100.0f, 414.0f, this.cFont, formatDouble(this.acumMin), HorizontalAlign.CENTER, 8);
        this.acumMedText = new ChangeableText(335.0f, 414.0f, this.cFont, formatDouble(this.acumMed), HorizontalAlign.CENTER, 8);
        this.acumMaxText = new ChangeableText(555.0f, 414.0f, this.cFont, formatDouble(this.acumMax), HorizontalAlign.CENTER, 8);
    }

    public void InitReplay() {
        String str = this.dbase.GetReplaySlots();
        String[] replay = str.split("#");
        this.lineBet = Integer.valueOf(replay[17]);
        this.apBet = Integer.valueOf(replay[16]);
        SetAposta();
        SetBet();
        SetLine();
        if (this.bonusReplay > 0) {
            this.bonusToCall[1] = this.bonusReplay;
            this.mRunnable = new Runnable() { // from class: com.r7games.slotpantanal.MainActivity.55
                @Override // java.lang.Runnable
                public void run() {
                    MainActivity.this.ShowAbobora();
                }
            };
            this.mHandler.postDelayed(this.mRunnable, 200L);
        } else {
            GenerateNumber(1, replay);
            try {
                AnimSlot();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public void InitLines() {
        this.lineBet = 1;
        this.apBet = 1;
        this.aposta = Integer.valueOf(this.lineBet.intValue() * this.apBet.intValue());
        this.premio = 0;
        this.bonusToCall = new int[4];
        this.bonusShow = false;
        this.lineRegion = new TextureRegion[25];
        this.lineCropRegion = new TextureRegion[25];
        this.line1Atlas = new BitmapTextureAtlas(1024, 32, TextureOptions.DEFAULT);
        this.lineRegion[0] = BitmapTextureAtlasTextureRegionFactory.createFromAsset(this.line1Atlas, this, "05_lin1.png", 0, 0);
        this.lineCropRegion[0] = BitmapTextureAtlasTextureRegionFactory.createFromAsset(this.line1Atlas, this, "05_lin1c.png", 659, 0);
        this.line5Atlas = new BitmapTextureAtlas(2048, 512, TextureOptions.DEFAULT);
        this.lineRegion[1] = BitmapTextureAtlasTextureRegionFactory.createFromAsset(this.line5Atlas, this, "05_lin2.png", 0, 0);
        this.lineRegion[2] = BitmapTextureAtlasTextureRegionFactory.createFromAsset(this.line5Atlas, this, "05_lin3.png", 659, 0);
        this.lineRegion[3] = BitmapTextureAtlasTextureRegionFactory.createFromAsset(this.line5Atlas, this, "05_lin4.png", 1318, 0);
        this.lineRegion[4] = BitmapTextureAtlasTextureRegionFactory.createFromAsset(this.line5Atlas, this, "05_lin5.png", 0, 22);
        this.lineCropRegion[1] = BitmapTextureAtlasTextureRegionFactory.createFromAsset(this.line5Atlas, this, "05_lin2c.png", 0, 309);
        this.lineCropRegion[2] = BitmapTextureAtlasTextureRegionFactory.createFromAsset(this.line5Atlas, this, "05_lin3c.png", 43, 309);
        this.lineCropRegion[3] = BitmapTextureAtlasTextureRegionFactory.createFromAsset(this.line5Atlas, this, "05_lin4c.png", 84, 309);
        this.lineCropRegion[4] = BitmapTextureAtlasTextureRegionFactory.createFromAsset(this.line5Atlas, this, "05_lin5c.png", TransportMediator.KEYCODE_MEDIA_PAUSE, 309);
        this.line9Atlas = new BitmapTextureAtlas(2048, 512, TextureOptions.DEFAULT);
        this.lineRegion[5] = BitmapTextureAtlasTextureRegionFactory.createFromAsset(this.line9Atlas, this, "05_lin6.png", 0, 0);
        this.lineRegion[6] = BitmapTextureAtlasTextureRegionFactory.createFromAsset(this.line9Atlas, this, "05_lin7.png", 659, 0);
        this.lineRegion[7] = BitmapTextureAtlasTextureRegionFactory.createFromAsset(this.line9Atlas, this, "05_lin8.png", 1318, 0);
        this.lineRegion[8] = BitmapTextureAtlasTextureRegionFactory.createFromAsset(this.line9Atlas, this, "05_lin9.png", 0, 263);
        this.lineCropRegion[5] = BitmapTextureAtlasTextureRegionFactory.createFromAsset(this.line9Atlas, this, "05_lin6c.png", 0, 459);
        this.lineCropRegion[6] = BitmapTextureAtlasTextureRegionFactory.createFromAsset(this.line9Atlas, this, "05_lin7c.png", 43, 459);
        this.lineCropRegion[7] = BitmapTextureAtlasTextureRegionFactory.createFromAsset(this.line9Atlas, this, "05_lin8c.png", 84, 459);
        this.lineCropRegion[8] = BitmapTextureAtlasTextureRegionFactory.createFromAsset(this.line9Atlas, this, "05_lin9c.png", TransportMediator.KEYCODE_MEDIA_PAUSE, 459);
        this.line15Atlas = new BitmapTextureAtlas(2048, 1024, TextureOptions.DEFAULT);
        this.lineRegion[9] = BitmapTextureAtlasTextureRegionFactory.createFromAsset(this.line15Atlas, this, "05_lin10.png", 0, 0);
        this.lineRegion[10] = BitmapTextureAtlasTextureRegionFactory.createFromAsset(this.line15Atlas, this, "05_lin11.png", 659, 0);
        this.lineRegion[11] = BitmapTextureAtlasTextureRegionFactory.createFromAsset(this.line15Atlas, this, "05_lin12.png", 1317, 0);
        this.lineRegion[12] = BitmapTextureAtlasTextureRegionFactory.createFromAsset(this.line15Atlas, this, "05_lin13.png", 0, 275);
        this.lineRegion[13] = BitmapTextureAtlasTextureRegionFactory.createFromAsset(this.line15Atlas, this, "05_lin14.png", 658, 275);
        this.lineRegion[14] = BitmapTextureAtlasTextureRegionFactory.createFromAsset(this.line15Atlas, this, "05_lin15.png", 1316, 275);
        this.lineCropRegion[9] = BitmapTextureAtlasTextureRegionFactory.createFromAsset(this.line15Atlas, this, "05_lin10c.png", 0, 543);
        this.lineCropRegion[10] = BitmapTextureAtlasTextureRegionFactory.createFromAsset(this.line15Atlas, this, "05_lin11c.png", 41, 543);
        this.lineCropRegion[11] = BitmapTextureAtlasTextureRegionFactory.createFromAsset(this.line15Atlas, this, "05_lin12c.png", 81, 543);
        this.lineCropRegion[12] = BitmapTextureAtlasTextureRegionFactory.createFromAsset(this.line15Atlas, this, "05_lin13c.png", 121, 543);
        this.lineCropRegion[13] = BitmapTextureAtlasTextureRegionFactory.createFromAsset(this.line15Atlas, this, "05_lin14c.png", 162, 543);
        this.lineCropRegion[14] = BitmapTextureAtlasTextureRegionFactory.createFromAsset(this.line15Atlas, this, "05_lin15c.png", 203, 543);
        this.line20Atlas = new BitmapTextureAtlas(2048, 1024, TextureOptions.DEFAULT);
        this.lineRegion[15] = BitmapTextureAtlasTextureRegionFactory.createFromAsset(this.line20Atlas, this, "05_lin16.png", 0, 0);
        this.lineRegion[16] = BitmapTextureAtlasTextureRegionFactory.createFromAsset(this.line20Atlas, this, "05_lin17.png", 658, 0);
        this.lineRegion[17] = BitmapTextureAtlasTextureRegionFactory.createFromAsset(this.line20Atlas, this, "05_lin18.png", 1316, 0);
        this.lineRegion[18] = BitmapTextureAtlasTextureRegionFactory.createFromAsset(this.line20Atlas, this, "05_lin19.png", 0, 236);
        this.lineRegion[19] = BitmapTextureAtlasTextureRegionFactory.createFromAsset(this.line20Atlas, this, "05_lin20.png", 658, 236);
        this.lineCropRegion[15] = BitmapTextureAtlasTextureRegionFactory.createFromAsset(this.line20Atlas, this, "05_lin16c.png", 0, 482);
        this.lineCropRegion[16] = BitmapTextureAtlasTextureRegionFactory.createFromAsset(this.line20Atlas, this, "05_lin17c.png", 41, 482);
        this.lineCropRegion[17] = BitmapTextureAtlasTextureRegionFactory.createFromAsset(this.line20Atlas, this, "05_lin18c.png", 82, 482);
        this.lineCropRegion[18] = BitmapTextureAtlasTextureRegionFactory.createFromAsset(this.line20Atlas, this, "05_lin19c.png", 122, 482);
        this.lineCropRegion[19] = BitmapTextureAtlasTextureRegionFactory.createFromAsset(this.line20Atlas, this, "05_lin20c.png", 161, 482);
        this.line25Atlas = new BitmapTextureAtlas(256, 512, TextureOptions.DEFAULT);
        this.lineRegion[20] = BitmapTextureAtlasTextureRegionFactory.createFromAsset(this.line25Atlas, this, "05_lin21.png", 0, 0);
        this.lineRegion[21] = BitmapTextureAtlasTextureRegionFactory.createFromAsset(this.line25Atlas, this, "05_lin22.png", 34, 0);
        this.lineRegion[22] = BitmapTextureAtlasTextureRegionFactory.createFromAsset(this.line25Atlas, this, "05_lin23.png", 69, 0);
        this.lineRegion[23] = BitmapTextureAtlasTextureRegionFactory.createFromAsset(this.line25Atlas, this, "05_lin24.png", 103, 0);
        this.lineRegion[24] = BitmapTextureAtlasTextureRegionFactory.createFromAsset(this.line25Atlas, this, "05_lin25.png", 137, 0);
        this.lineCropRegion[20] = BitmapTextureAtlasTextureRegionFactory.createFromAsset(this.line25Atlas, this, "05_lin21c.png", 0, 333);
        this.lineCropRegion[21] = BitmapTextureAtlasTextureRegionFactory.createFromAsset(this.line25Atlas, this, "05_lin22c.png", 41, 333);
        this.lineCropRegion[22] = BitmapTextureAtlasTextureRegionFactory.createFromAsset(this.line25Atlas, this, "05_lin23c.png", 82, 333);
        this.lineCropRegion[23] = BitmapTextureAtlasTextureRegionFactory.createFromAsset(this.line25Atlas, this, "05_lin24c.png", 123, 333);
        this.lineCropRegion[24] = BitmapTextureAtlasTextureRegionFactory.createFromAsset(this.line25Atlas, this, "05_lin25c.png", 164, 333);
        this.mEngine.getTextureManager().loadTexture(this.line1Atlas);
        this.mEngine.getTextureManager().loadTexture(this.line5Atlas);
        this.mEngine.getTextureManager().loadTexture(this.line9Atlas);
        this.mEngine.getTextureManager().loadTexture(this.line15Atlas);
        this.mEngine.getTextureManager().loadTexture(this.line20Atlas);
        this.mEngine.getTextureManager().loadTexture(this.line25Atlas);
        int[] arrayOfInt = {25, 5};
        this.plna = (int[][]) Array.newInstance((Class<?>) Integer.TYPE, arrayOfInt);
        this.line = new Sprite[25];
        this.line[0] = new Sprite(6.0f, 219.0f, this.lineRegion[0]);
        this.line[1] = new Sprite(6.0f, 121.0f, this.lineRegion[1]);
        this.line[2] = new Sprite(6.0f, 309.0f, this.lineRegion[2]);
        this.line[3] = new Sprite(6.0f, 50.0f, this.lineRegion[3]);
        this.line[4] = new Sprite(6.0f, 118.0f, this.lineRegion[4]);
        this.line[5] = new Sprite(6.0f, 97.0f, this.lineRegion[5]);
        this.line[6] = new Sprite(6.0f, 103.0f, this.lineRegion[6]);
        this.line[7] = new Sprite(6.0f, 161.0f, this.lineRegion[7]);
        this.line[8] = new Sprite(6.0f, 99.0f, this.lineRegion[8]);
        this.line[9] = new Sprite(7.0f, 85.0f, this.lineRegion[9]);
        this.line[10] = new Sprite(52.0f, 118.0f, this.lineRegion[10]);
        this.line[11] = new Sprite(52.0f, 81.0f, this.lineRegion[11]);
        this.line[12] = new Sprite(52.0f, 102.0f, this.lineRegion[12]);
        this.line[13] = new Sprite(52.0f, 87.0f, this.lineRegion[13]);
        this.line[14] = new Sprite(52.0f, 102.0f, this.lineRegion[14]);
        this.line[15] = new Sprite(51.0f, 118.0f, this.lineRegion[15]);
        this.line[16] = new Sprite(52.0f, 184.0f, this.lineRegion[16]);
        this.line[17] = new Sprite(52.0f, 56.0f, this.lineRegion[17]);
        this.line[18] = new Sprite(51.0f, 166.0f, this.lineRegion[18]);
        this.line[19] = new Sprite(52.0f, 122.0f, this.lineRegion[19]);
        this.line[20] = new Sprite(96.0f, 74.0f, this.lineRegion[20]);
        this.line[21] = new Sprite(215.0f, 74.0f, this.lineRegion[21]);
        this.line[22] = new Sprite(342.0f, 74.0f, this.lineRegion[22]);
        this.line[23] = new Sprite(464.0f, 74.0f, this.lineRegion[23]);
        this.line[24] = new Sprite(589.0f, 74.0f, this.lineRegion[24]);
        this.lineCrop = new Sprite[25];
        this.lineCrop[0] = new Sprite(6.0f, 218.0f, this.lineCropRegion[0]);
        this.lineCrop[1] = new Sprite(7.0f, 122.0f, this.lineCropRegion[1]);
        this.lineCrop[2] = new Sprite(7.0f, 309.0f, this.lineCropRegion[2]);
        this.lineCrop[3] = new Sprite(8.0f, 50.0f, this.lineCropRegion[3]);
        this.lineCrop[4] = new Sprite(7.0f, 378.0f, this.lineCropRegion[4]);
        this.lineCrop[5] = new Sprite(7.0f, 97.0f, this.lineCropRegion[5]);
        this.lineCrop[6] = new Sprite(7.0f, 344.0f, this.lineCropRegion[6]);
        this.lineCrop[7] = new Sprite(7.0f, 162.0f, this.lineCropRegion[7]);
        this.lineCrop[8] = new Sprite(7.0f, 273.0f, this.lineCropRegion[8]);
        this.lineCrop[9] = new Sprite(7.0f, 251.0f, this.lineCropRegion[9]);
        this.lineCrop[10] = new Sprite(669.0f, 290.0f, this.lineCropRegion[10]);
        this.lineCrop[11] = new Sprite(669.0f, 82.0f, this.lineCropRegion[11]);
        this.lineCrop[12] = new Sprite(669.0f, 349.0f, this.lineCropRegion[12]);
        this.lineCrop[13] = new Sprite(669.0f, 327.0f, this.lineCropRegion[13]);
        this.lineCrop[14] = new Sprite(669.0f, 103.0f, this.lineCropRegion[14]);
        this.lineCrop[15] = new Sprite(669.0f, 247.0f, this.lineCropRegion[15]);
        this.lineCrop[16] = new Sprite(669.0f, 185.0f, this.lineCropRegion[16]);
        this.lineCrop[17] = new Sprite(669.0f, 56.0f, this.lineCropRegion[17]);
        this.lineCrop[18] = new Sprite(670.0f, 369.0f, this.lineCropRegion[18]);
        this.lineCrop[19] = new Sprite(669.0f, 123.0f, this.lineCropRegion[19]);
        this.lineCrop[20] = new Sprite(89.0f, 384.0f, this.lineCropRegion[20]);
        this.lineCrop[21] = new Sprite(210.0f, 386.0f, this.lineCropRegion[21]);
        this.lineCrop[22] = new Sprite(339.0f, 385.0f, this.lineCropRegion[22]);
        this.lineCrop[23] = new Sprite(460.0f, 385.0f, this.lineCropRegion[23]);
        this.lineCrop[24] = new Sprite(583.0f, 385.0f, this.lineCropRegion[24]);
    }

    public void InitMorango() {
        this.moraBgAtlas = new BitmapTextureAtlas(1024, 512, TextureOptions.DEFAULT);
        this.moraBgRegion = BitmapTextureAtlasTextureRegionFactory.createFromAsset(this.moraBgAtlas, this, "05_telab.png", 0, 0);
        this.mFontAtlas = new BitmapTextureAtlas(256, 256, TextureOptions.BILINEAR_PREMULTIPLYALPHA);
        this.mFont = new Font(this.mFontAtlas, Typeface.create(Typeface.DEFAULT, 1), 36.0f, true, -16777216);
        this.mEngine.getTextureManager().loadTexture(this.moraBgAtlas);
        this.mEngine.getTextureManager().loadTexture(this.mFontAtlas);
        this.mEngine.getFontManager().loadFont(this.mFont);
        this.moraCanTouch = true;
        this.moraAnimTouched = new boolean[12];
        for (int i = 0; i < 12; i++) {
            this.moraAnimTouched[i] = false;
        }
        this.moraBns = 0;
        this.moraBgSpr = new Sprite(BitmapDescriptorFactory.HUE_RED, BitmapDescriptorFactory.HUE_RED, this.moraBgRegion);
        this.moraAnim = new AnimatedSprite[12];
        CreateMora(0, 94.0f, 79.0f);
        CreateMora(1, 253.0f, 79.0f);
        CreateMora(2, 412.0f, 79.0f);
        CreateMora(3, 571.0f, 79.0f);
        CreateMora(4, 94.0f, 206.0f);
        CreateMora(5, 253.0f, 206.0f);
        CreateMora(6, 412.0f, 206.0f);
        CreateMora(7, 571.0f, 206.0f);
        CreateMora(8, 94.0f, 333.0f);
        CreateMora(9, 253.0f, 333.0f);
        CreateMora(10, 412.0f, 333.0f);
        CreateMora(11, 571.0f, 333.0f);
        this.moraCredito = new ChangeableText(213.0f, 8.0f, this.cFont, "0", 8);
        this.moraPremio = new ChangeableText(463.0f, 8.0f, this.cFont, "0", 8);
        this.moraBonus = new ChangeableText(700.0f, 8.0f, this.cFont, "0", 8);
        this.moraNum = new ChangeableText[12];
        for (int i2 = 0; i2 < 12; i2++) {
            this.moraNum[i2] = new ChangeableText(BitmapDescriptorFactory.HUE_RED, BitmapDescriptorFactory.HUE_RED, this.mFont, "0", 8);
        }
    }

    public void InitRoleta() {
        this.roletaBgAtlas = new BitmapTextureAtlas(1024, 512, TextureOptions.DEFAULT);
        this.roletaBgRegion = BitmapTextureAtlasTextureRegionFactory.createFromAsset(this.roletaBgAtlas, this, "05_tela25b2.png", 0, 0);
        this.btnPararRegion = BitmapTextureAtlasTextureRegionFactory.createFromAsset(this.roletaBgAtlas, this, "parar.png", 800, 0);
        this.roletaAtlas = new BitmapTextureAtlas(512, 512, TextureOptions.BILINEAR);
        this.roletaRegion = BitmapTextureAtlasTextureRegionFactory.createFromAsset(this.roletaAtlas, this, "05_roleta.png", 0, 0);
        this.mEngine.getTextureManager().loadTexture(this.roletaBgAtlas);
        this.mEngine.getTextureManager().loadTexture(this.roletaAtlas);
        this.roletaCanTouch = true;
        this.roletaBgSpr = new Sprite(BitmapDescriptorFactory.HUE_RED, BitmapDescriptorFactory.HUE_RED, this.roletaBgRegion);
        this.btnParar = new Sprite(656.0f, 365.0f, this.btnPararRegion) { // from class: com.r7games.slotpantanal.MainActivity.56
            @Override // org.anddev.andengine.entity.shape.Shape, org.anddev.andengine.entity.scene.Scene.ITouchArea
            public boolean onAreaTouched(TouchEvent paramAnonymousTouchEvent, float paramAnonymousFloat1, float paramAnonymousFloat2) {
                if (paramAnonymousTouchEvent.getAction() == 0 && MainActivity.this.roletaCanTouch) {
                    MainActivity.this.roletaCanTouch = false;
                    MainActivity.this.ParaRoleta();
                    MainActivity.this.dbase.SetReplayStatus(0);
                    if (MainActivity.this.SoundON) {
                        MainActivity.this.linhaSound.play();
                    }
                }
                return false;
            }
        };
        this.roletaSpr = new Sprite(75.0f, 110.0f, this.roletaRegion);
        IEntityModifier[] arrayOfIEntityModifier = {new RotationModifier(0.5f, BitmapDescriptorFactory.HUE_RED, -360.0f)};
        this.rotationModifier = new LoopEntityModifier(new SequenceEntityModifier(arrayOfIEntityModifier));
        this.roletaPremio = new ChangeableText(688.0f, 260.0f, this.cFont, "0", 8);
    }

    public void InitSlots() {
        this.rand = new Random();
        this.gen.Init(this);
        this.canTouch = true;
        this.canSkip = false;
        this.canDouble = false;
        int[] arrayOfInt = {5, 3};
        this.slotTileValues = (int[][]) Array.newInstance((Class<?>) Integer.TYPE, arrayOfInt);
        this.slotsAtlas = new BitmapTextureAtlas(1024, 1024, TextureOptions.DEFAULT);
        this.slotsRegion = BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(this.slotsAtlas, this, "05_slots.png", 0, 0, 5, 10);
        this.mEngine.getTextureManager().loadTexture(this.slotsAtlas);
        int[] arrayOfInt2 = {5, 3};
        this.slots = (TiledSprite[][]) Array.newInstance((Class<?>) TiledSprite.class, arrayOfInt2);
        this.slots[0][0] = new TiledSprite(50.0f, 75.0f, this.slotsRegion.deepCopy());
        this.slots[0][1] = new TiledSprite(50.0f, 177.0f, this.slotsRegion.deepCopy());
        this.slots[0][2] = new TiledSprite(50.0f, 279.0f, this.slotsRegion.deepCopy());
        this.slots[1][0] = new TiledSprite(177.0f, 75.0f, this.slotsRegion.deepCopy());
        this.slots[1][1] = new TiledSprite(177.0f, 177.0f, this.slotsRegion.deepCopy());
        this.slots[1][2] = new TiledSprite(177.0f, 279.0f, this.slotsRegion.deepCopy());
        this.slots[2][0] = new TiledSprite(301.0f, 75.0f, this.slotsRegion.deepCopy());
        this.slots[2][1] = new TiledSprite(301.0f, 177.0f, this.slotsRegion.deepCopy());
        this.slots[2][2] = new TiledSprite(301.0f, 279.0f, this.slotsRegion.deepCopy());
        this.slots[3][0] = new TiledSprite(425.0f, 75.0f, this.slotsRegion.deepCopy());
        this.slots[3][1] = new TiledSprite(425.0f, 177.0f, this.slotsRegion.deepCopy());
        this.slots[3][2] = new TiledSprite(425.0f, 279.0f, this.slotsRegion.deepCopy());
        this.slots[4][0] = new TiledSprite(551.0f, 75.0f, this.slotsRegion.deepCopy());
        this.slots[4][1] = new TiledSprite(551.0f, 177.0f, this.slotsRegion.deepCopy());
        this.slots[4][2] = new TiledSprite(551.0f, 279.0f, this.slotsRegion.deepCopy());
        this.animSlotAtlas = new BitmapTextureAtlas(1024, 1024, TextureOptions.DEFAULT);
        this.animSlotRegion = BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(this.animSlotAtlas, this, "05_ti.png", 0, 0, 5, 2);
        this.mEngine.getTextureManager().loadTexture(this.animSlotAtlas);
        this.animSlot = new AnimatedSprite[6];
        this.animSlot[0] = new AnimatedSprite(50.0f, 75.0f, this.animSlotRegion.deepCopy());
        this.animSlot[1] = new AnimatedSprite(177.0f, 75.0f, this.animSlotRegion.deepCopy());
        this.animSlot[2] = new AnimatedSprite(301.0f, 75.0f, this.animSlotRegion.deepCopy());
        this.animSlot[3] = new AnimatedSprite(425.0f, 75.0f, this.animSlotRegion.deepCopy());
        this.animSlot[4] = new AnimatedSprite(551.0f, 75.0f, this.animSlotRegion.deepCopy());
        this.animSlot[5] = new AnimatedSprite(800.0f, 480.0f, this.animSlotRegion.deepCopy());
        int[] arrayOfInt3 = {25, 5};
        this.slotLinhas = (int[][]) Array.newInstance((Class<?>) Integer.TYPE, arrayOfInt3);
        this.slotLinhas[0][0] = 1;
        this.slotLinhas[0][1] = 1;
        this.slotLinhas[0][2] = 1;
        this.slotLinhas[0][3] = 1;
        this.slotLinhas[0][4] = 1;
        this.slotLinhas[1][0] = 0;
        this.slotLinhas[1][1] = 0;
        this.slotLinhas[1][2] = 0;
        this.slotLinhas[1][3] = 0;
        this.slotLinhas[1][4] = 0;
        this.slotLinhas[2][0] = 2;
        this.slotLinhas[2][1] = 2;
        this.slotLinhas[2][2] = 2;
        this.slotLinhas[2][3] = 2;
        this.slotLinhas[2][4] = 2;
        this.slotLinhas[3][0] = 0;
        this.slotLinhas[3][1] = 1;
        this.slotLinhas[3][2] = 2;
        this.slotLinhas[3][3] = 1;
        this.slotLinhas[3][4] = 0;
        this.slotLinhas[4][0] = 2;
        this.slotLinhas[4][1] = 1;
        this.slotLinhas[4][2] = 0;
        this.slotLinhas[4][3] = 1;
        this.slotLinhas[4][4] = 2;
        this.slotLinhas[5][0] = 0;
        this.slotLinhas[5][1] = 0;
        this.slotLinhas[5][2] = 1;
        this.slotLinhas[5][3] = 2;
        this.slotLinhas[5][4] = 2;
        this.slotLinhas[6][0] = 2;
        this.slotLinhas[6][1] = 2;
        this.slotLinhas[6][2] = 1;
        this.slotLinhas[6][3] = 0;
        this.slotLinhas[6][4] = 0;
        this.slotLinhas[7][0] = 1;
        this.slotLinhas[7][1] = 2;
        this.slotLinhas[7][2] = 2;
        this.slotLinhas[7][3] = 2;
        this.slotLinhas[7][4] = 1;
        this.slotLinhas[8][0] = 1;
        this.slotLinhas[8][1] = 0;
        this.slotLinhas[8][2] = 0;
        this.slotLinhas[8][3] = 0;
        this.slotLinhas[8][4] = 1;
        this.slotLinhas[9][0] = 1;
        this.slotLinhas[9][1] = 0;
        this.slotLinhas[9][2] = 1;
        this.slotLinhas[9][3] = 2;
        this.slotLinhas[9][4] = 1;
        this.slotLinhas[10][0] = 1;
        this.slotLinhas[10][1] = 2;
        this.slotLinhas[10][2] = 1;
        this.slotLinhas[10][3] = 0;
        this.slotLinhas[10][4] = 1;
        this.slotLinhas[11][0] = 2;
        this.slotLinhas[11][1] = 1;
        this.slotLinhas[11][2] = 0;
        this.slotLinhas[11][3] = 0;
        this.slotLinhas[11][4] = 0;
        this.slotLinhas[12][0] = 0;
        this.slotLinhas[12][1] = 1;
        this.slotLinhas[12][2] = 2;
        this.slotLinhas[12][3] = 2;
        this.slotLinhas[12][4] = 2;
        this.slotLinhas[13][0] = 0;
        this.slotLinhas[13][1] = 0;
        this.slotLinhas[13][2] = 0;
        this.slotLinhas[13][3] = 1;
        this.slotLinhas[13][4] = 2;
        this.slotLinhas[14][0] = 2;
        this.slotLinhas[14][1] = 2;
        this.slotLinhas[14][2] = 2;
        this.slotLinhas[14][3] = 1;
        this.slotLinhas[14][4] = 0;
        this.slotLinhas[15][0] = 0;
        this.slotLinhas[15][1] = 0;
        this.slotLinhas[15][2] = 0;
        this.slotLinhas[15][3] = 0;
        this.slotLinhas[15][4] = 1;
        this.slotLinhas[16][0] = 2;
        this.slotLinhas[16][1] = 2;
        this.slotLinhas[16][2] = 2;
        this.slotLinhas[16][3] = 2;
        this.slotLinhas[16][4] = 1;
        this.slotLinhas[17][0] = 1;
        this.slotLinhas[17][1] = 0;
        this.slotLinhas[17][2] = 0;
        this.slotLinhas[17][3] = 0;
        this.slotLinhas[17][4] = 0;
        this.slotLinhas[18][0] = 1;
        this.slotLinhas[18][1] = 2;
        this.slotLinhas[18][2] = 2;
        this.slotLinhas[18][3] = 2;
        this.slotLinhas[18][4] = 2;
        this.slotLinhas[19][0] = 2;
        this.slotLinhas[19][1] = 1;
        this.slotLinhas[19][2] = 1;
        this.slotLinhas[19][3] = 1;
        this.slotLinhas[19][4] = 0;
        this.slotLinhas[20][0] = 0;
        this.slotLinhas[20][1] = 1;
        this.slotLinhas[20][2] = 2;
        this.slotLinhas[21][0] = 0;
        this.slotLinhas[21][1] = 1;
        this.slotLinhas[21][2] = 2;
        this.slotLinhas[22][0] = 0;
        this.slotLinhas[22][1] = 1;
        this.slotLinhas[22][2] = 2;
        this.slotLinhas[23][0] = 0;
        this.slotLinhas[23][1] = 1;
        this.slotLinhas[23][2] = 2;
        this.slotLinhas[24][0] = 0;
        this.slotLinhas[24][1] = 1;
        this.slotLinhas[24][2] = 2;
    }

    /* JADX WARN: Failed to build post-dominance tree
    java.lang.ArrayIndexOutOfBoundsException: Index 5 out of bounds for length 5
    	at jadx.core.dex.visitors.blocks.DominatorTree.build(DominatorTree.java:68)
    	at jadx.core.dex.visitors.blocks.PostDominatorTree.compute(PostDominatorTree.java:32)
    	at jadx.core.dex.visitors.blocks.BlockProcessor.processBlocksTree(BlockProcessor.java:93)
    	at jadx.core.dex.visitors.blocks.BlockProcessor.visit(BlockProcessor.java:50)
     */
    public void InitSound() {
        try {
            this.playSound = SoundFactory.createSoundFromAsset(this.mEngine.getSoundManager(), this, "sons/slots.mp3");
            this.linhaSound = SoundFactory.createSoundFromAsset(this.mEngine.getSoundManager(), this, "sons/premio.mp3");
            this.premioSound = SoundFactory.createSoundFromAsset(this.mEngine.getSoundManager(), this, "sons/premio4.mp3");
            this.bonusSound = SoundFactory.createSoundFromAsset(this.mEngine.getSoundManager(), this, "sons/bonus11.mp3");
            this.moranSound = SoundFactory.createSoundFromAsset(this.mEngine.getSoundManager(), this, "sons/bonus1.mp3");
            this.bWinSound = SoundFactory.createSoundFromAsset(this.mEngine.getSoundManager(), this, "sons/premiobonus.mp3");
            this.bonusEndSound = SoundFactory.createSoundFromAsset(this.mEngine.getSoundManager(), this, "sons/fimdobonus.mp3");
            this.bonusSelSound = SoundFactory.createSoundFromAsset(this.mEngine.getSoundManager(), this, "sons/bonus2.mp3");
            this.acumuladoSound = SoundFactory.createSoundFromAsset(this.mEngine.getSoundManager(), this, "sons/acumulado.mp3");
        } catch (IOException localIOException) {
            while (true) {
                localIOException.printStackTrace();
            }
        }
    }

    public void InitTabela() {
        this.tabelaAtlas = new BitmapTextureAtlas[3];
        this.tabelaAtlas[0] = new BitmapTextureAtlas(1024, 512, TextureOptions.DEFAULT);
        this.tabelaAtlas[1] = new BitmapTextureAtlas(1024, 512, TextureOptions.DEFAULT);
        this.tabelaAtlas[2] = new BitmapTextureAtlas(1024, 512, TextureOptions.DEFAULT);
        this.tabelaRegion = new TextureRegion[3];
        this.tabelaRegion[0] = BitmapTextureAtlasTextureRegionFactory.createFromAsset(this.tabelaAtlas[0], this, "05_tabela1.png", 0, 0);
        this.tabelaRegion[1] = BitmapTextureAtlasTextureRegionFactory.createFromAsset(this.tabelaAtlas[1], this, "05_tabela2.png", 0, 0);
        this.tabelaRegion[2] = BitmapTextureAtlasTextureRegionFactory.createFromAsset(this.tabelaAtlas[2], this, "05_tabela3x.png", 0, 0);
        this.mEngine.getTextureManager().loadTexture(this.tabelaAtlas[0]);
        this.mEngine.getTextureManager().loadTexture(this.tabelaAtlas[1]);
        this.mEngine.getTextureManager().loadTexture(this.tabelaAtlas[2]);
        this.tabelaSpr = new Sprite[3];
        this.tabelaSpr[0] = new Sprite(BitmapDescriptorFactory.HUE_RED, BitmapDescriptorFactory.HUE_RED, this.tabelaRegion[0]);
        this.tabelaSpr[1] = new Sprite(BitmapDescriptorFactory.HUE_RED, BitmapDescriptorFactory.HUE_RED, this.tabelaRegion[1]);
        this.tabelaSpr[2] = new Sprite(BitmapDescriptorFactory.HUE_RED, BitmapDescriptorFactory.HUE_RED, this.tabelaRegion[2]);
    }

    public void JogaAuto() {
        try {
            this.canTouch = false;
            this.credito = Integer.valueOf(this.credito.intValue() - this.aposta.intValue());
            SetCredito();
            GenerateNumber(0, null);
            try {
                AnimSlot();
            } catch (Exception e) {
                e.printStackTrace();
            }
            if (this.SoundON) {
                this.playSound.play();
            }
            if (this.credito.intValue() < this.aposta.intValue()) {
                RemoveAuto();
                this.jogaAuto = false;
            }
            this.mRunnable = new Runnable() { // from class: com.r7games.slotpantanal.MainActivity.57
                @Override // java.lang.Runnable
                public void run() {
                    if (!MainActivity.this.jogaAuto || MainActivity.this.credito.intValue() < MainActivity.this.aposta.intValue()) {
                        MainActivity.this.jogaAuto = false;
                        return;
                    }
                    MainActivity mainActivity = MainActivity.this;
                    mainActivity.credito = Integer.valueOf(mainActivity.credito.intValue() + MainActivity.this.premio.intValue());
                    MainActivity.this.dbase.SetReplayStatus(0);
                    MainActivity.this.SetPago(MainActivity.this.premio.intValue());
                    MainActivity.this.premio = 0;
                    MainActivity.this.SetPremio();
                    MainActivity.this.SetCredito();
                    MainActivity.this.JogaAuto();
                }
            };
            this.mHandler.postDelayed(this.mRunnable, 3000L);
        } catch (Exception e2) {
            e2.printStackTrace();
        }
    }

    @TargetApi(19)
    void hide_navigation_bar() {
        if (Build.VERSION.SDK_INT >= 19) {
            getWindow().getDecorView().setSystemUiVisibility(5894);
        }
    }

    public void MorangoSaida() {
        int j;
        int k = this.premio.intValue();
        int i = this.moraBns;
        this.premio = Integer.valueOf(this.premio.intValue() + this.moraBns);
        this.moraBns = 0;
        SetMoraPremio(this.premio);
        SetMoraBonus(Integer.valueOf(this.moraBns));
        while (true) {
            if (i > 5000) {
                j = 5000;
            } else if (i > 1000) {
                j = 1000;
            } else if (i > 500) {
                j = TJAdUnitConstants.DEFAULT_VOLUME_CHECK_INTERVAL;
            } else if (i > 100) {
                j = 100;
            } else if (i > 50) {
                j = 50;
            } else if (i > 10) {
                j = 10;
            } else {
                j = 1;
            }
            if (i >= 1) {
                k += j;
                i -= j;
                try {
                    SetMoraPremio(Integer.valueOf(k));
                    SetMoraBonus(Integer.valueOf(i));
                    Thread.sleep(50L);
                } catch (Exception localException) {
                    localException.printStackTrace();
                }
            } else {
                this.mRunnable = new Runnable() { // from class: com.r7games.slotpantanal.MainActivity.58
                    @Override // java.lang.Runnable
                    public void run() {
                        MainActivity.this.ExitMorango();
                    }
                };
                this.mHandler.postDelayed(this.mRunnable, 2500L);
                return;
            }
        }
    }

    public void MostraAbobora() {
        int k;
        Log.i("CALDEIRAO", "anbPrizeValues[anbPrizeIndex] " + this.anbPrizeValues[this.anbPrizeIndex]);
        int bonus = this.anbPrizeValues[this.anbPrizeIndex].intValue() * this.anbBns;
        while (true) {
            if (bonus > 5000) {
                k = 5000;
            } else if (bonus > 1000) {
                k = 1000;
            } else if (bonus > 500) {
                k = TJAdUnitConstants.DEFAULT_VOLUME_CHECK_INTERVAL;
            } else if (bonus > 100) {
                k = 100;
            } else if (bonus > 50) {
                k = 50;
            } else if (bonus > 10) {
                k = 10;
            } else {
                k = 1;
            }
            try {
                this.premio = Integer.valueOf(this.premio.intValue() + k);
                bonus -= k;
                SetAnbBonus(Integer.valueOf(bonus));
                SetAnbPremio(this.premio);
                if (bonus >= 1) {
                    Thread.sleep(50L);
                } else {
                    this.mRunnable = new Runnable() { // from class: com.r7games.slotpantanal.MainActivity.59
                        @Override // java.lang.Runnable
                        public void run() {
                            MainActivity.this.ExitAbobora();
                        }
                    };
                    this.mHandler.postDelayed(this.mRunnable, 2500L);
                    return;
                }
            } catch (Exception e) {
            }
        }
    }

    public void MostraCaldeirao() {
        int k;
        if (this.caldPrizeIndex >= 7) {
            this.mRunnable = new Runnable() { // from class: com.r7games.slotpantanal.MainActivity.60
                @Override // java.lang.Runnable
                public void run() {
                    MainActivity.this.ExitCaldeirao(1);
                }
            };
            this.mHandler.postDelayed(this.mRunnable, 2500L);
            return;
        }
        int bonus = this.caldPrizeValues[this.caldPrizeIndex].intValue() * this.caldBns;
        SetCaldBonus(Integer.valueOf(bonus));
        while (true) {
            if (bonus > 5000) {
                k = 5000;
            } else if (bonus > 1000) {
                k = 1000;
            } else if (bonus > 500) {
                k = TJAdUnitConstants.DEFAULT_VOLUME_CHECK_INTERVAL;
            } else if (bonus > 100) {
                k = 100;
            } else if (bonus > 50) {
                k = 50;
            } else if (bonus > 10) {
                k = 10;
            } else {
                k = 1;
            }
            try {
                this.premio = Integer.valueOf(this.premio.intValue() + k);
                bonus -= k;
                SetCaldBonus(Integer.valueOf(bonus));
                SetCaldPremio(this.premio);
                if (bonus >= 1) {
                    Thread.sleep(50L);
                } else {
                    this.mRunnable = new Runnable() { // from class: com.r7games.slotpantanal.MainActivity.61
                        @Override // java.lang.Runnable
                        public void run() {
                            MainActivity.this.ExitCaldeirao(0);
                        }
                    };
                    this.mHandler.postDelayed(this.mRunnable, 2500L);
                    return;
                }
            } catch (Exception localException) {
                localException.printStackTrace();
            }
        }
    }

    public void MostraCaldeiraoAll() {
        runOnUpdateThread(new Runnable() { // from class: com.r7games.slotpantanal.MainActivity.62
            @Override // java.lang.Runnable
            public void run() {
                for (int x = 0; x < 5; x++) {
                    if (MainActivity.this.escolha != x) {
                        int i = MainActivity.this.caldPrizes[x].intValue();
                        float f1 = MainActivity.this.caldAnim[x].getX() + ((MainActivity.this.caldAnim[x].getWidth() / 2.0f) - (MainActivity.this.numSpr[i].getWidth() / 2.0f));
                        float f2 = 145.0f;
                        if (i == 8) {
                            f2 = 78.0f;
                        }
                        MainActivity.this.numSpr[i].setPosition(f1, f2);
                        try {
                            MainActivity.this.caldeiraoScene.attachChild(MainActivity.this.numSpr[i]);
                        } catch (IllegalStateException e) {
                        }
                    }
                }
            }
        });
    }

    public void MostraAboboraAll() {
        runOnUpdateThread(new Runnable() { // from class: com.r7games.slotpantanal.MainActivity.63
            @Override // java.lang.Runnable
            public void run() {
                for (int x = 0; x < 5; x++) {
                    if (MainActivity.this.escolha != x) {
                        int i = MainActivity.this.anbPrizes[x].intValue();
                        float f2 = MainActivity.this.anbAnim[x].getX() + ((MainActivity.this.anbAnim[x].getWidth() / 2.0f) - (MainActivity.this.numSpr[i].getWidth() / 2.0f));
                        MainActivity.this.numSpr[i].setPosition(f2, 128.0f);
                        try {
                            MainActivity.this.aboboraScene.attachChild(MainActivity.this.numSpr[i]);
                        } catch (IllegalStateException e) {
                        }
                    }
                }
            }
        });
    }

    public int NumDado() {
        int i;
        int j = 0;
        if (!this.gen.downDificult(0)) {
            i = this.rand.nextInt(80);
        } else {
            i = this.rand.nextInt(100);
        }
        if (i >= 20) {
            if (i >= 40) {
                if (i >= 60) {
                    if (i >= 80) {
                        if (i >= 90) {
                            if (i <= 100) {
                                j = 5;
                            }
                        } else {
                            j = 4;
                        }
                    } else {
                        j = 3;
                    }
                } else {
                    j = 2;
                }
            } else {
                j = 1;
            }
        } else {
            j = 0;
        }
        this.dPremio = Integer.valueOf(this.premio.intValue() * (j + 1));
        switch (j) {
            case 0:
                return 35;
            case 1:
                return 8;
            case 2:
                return 17;
            case 3:
                return 53;
            case 4:
                return 26;
            case 5:
                return 71;
            default:
                return 0;
        }
    }

    public float NumRoleta() {
        int i;
        int j = 0;
        if (this.gen.downDificult(0) && this.premio.intValue() <= 10000) {
            i = this.rand.nextInt(100);
        } else {
            i = this.rand.nextInt(60);
        }
        if (i >= 60) {
            if (i >= 70) {
                if (i >= 80) {
                    if (i >= 90) {
                        if (i <= 100) {
                            j = this.rand.nextInt(2) + 22;
                        }
                    } else {
                        j = this.rand.nextInt(4) + 18;
                    }
                } else {
                    j = this.rand.nextInt(5) + 13;
                }
            } else {
                j = this.rand.nextInt(6) + 7;
            }
        } else {
            j = this.rand.nextInt(7);
        }
        this.rBonus = Integer.valueOf(this.premio.intValue() * (j + 1));
        switch (j) {
            case 1:
                return -16.0f;
            case 2:
                return -30.0f;
            case 3:
                return -44.0f;
            case 4:
                return -60.0f;
            case 5:
                return -74.0f;
            case 6:
                return -91.0f;
            case 7:
                return -108.0f;
            case 8:
                return -121.0f;
            case 9:
                return -136.0f;
            case 10:
                return -152.0f;
            case 11:
                return -168.0f;
            case 12:
                return -181.0f;
            case 13:
                return -195.0f;
            case 14:
                return -209.0f;
            case 15:
                return -222.0f;
            case 16:
                return -239.0f;
            case 17:
                return -254.0f;
            case 18:
                return -269.0f;
            case 19:
                return -282.0f;
            case TapjoyConstants.MEDIATION_TIMEOUT_INTERVAL /* 20 */:
                return -299.0f;
            case 21:
                return -315.0f;
            case 22:
                return -330.0f;
            case 23:
                return -346.0f;
            default:
                return BitmapDescriptorFactory.HUE_RED;
        }
    }

    /* JADX WARN: Failed to build post-dominance tree
    java.lang.ArrayIndexOutOfBoundsException: Index 12 out of bounds for length 12
    	at jadx.core.dex.visitors.blocks.DominatorTree.build(DominatorTree.java:68)
    	at jadx.core.dex.visitors.blocks.PostDominatorTree.compute(PostDominatorTree.java:32)
    	at jadx.core.dex.visitors.blocks.BlockProcessor.processBlocksTree(BlockProcessor.java:93)
    	at jadx.core.dex.visitors.blocks.BlockProcessor.visit(BlockProcessor.java:50)
     */
    public void ParaRoleta() {
        this.roletaSpr.setRotation(NumRoleta());
        this.roletaSpr.unregisterEntityModifier(this.rotationModifier);
        int k = this.rBonus.intValue();
        try {
            Thread.sleep(500L);
            for (int i = 0; i < 50; i++) {
                SetRoletaPremio(Integer.valueOf(k));
                Thread.sleep(50L);
            }
            SetRoletaPremio(this.rBonus);
            this.mRunnable = new Runnable() { // from class: com.r7games.slotpantanal.MainActivity.64
                @Override // java.lang.Runnable
                public void run() {
                    MainActivity.this.premio = MainActivity.this.rBonus;
                    MainActivity.this.rBonus = 0;
                    MainActivity.this.ExitRoleta();
                }
            };
            this.mHandler.postDelayed(this.mRunnable, 500L);
        } catch (Exception localException) {
            while (true) {
                localException.printStackTrace();
            }
        }
    }

    public void RemoveAuto() {
        runOnUpdateThread(new Runnable() { // from class: com.r7games.slotpantanal.MainActivity.65
            @Override // java.lang.Runnable
            public void run() {
                try {
                    MainActivity.this.scene.detachChild(MainActivity.this.jogaAutoSpr);
                    MainActivity.this.mHandler.removeCallbacks(MainActivity.this.mRunnable);
                } catch (Exception localException) {
                    localException.printStackTrace();
                }
            }
        });
    }

    public void RemoveDobraSpr() {
        runOnUpdateThread(new Runnable() { // from class: com.r7games.slotpantanal.MainActivity.66
            @Override // java.lang.Runnable
            public void run() {
                if (MainActivity.this.scene.getChildIndex(MainActivity.this.dobraSpr) >= 0) {
                    MainActivity.this.scene.detachChild(MainActivity.this.dobraSpr);
                }
            }
        });
    }

    public void RemoveLineCrop() {
        runOnUpdateThread(new Runnable() { // from class: com.r7games.slotpantanal.MainActivity.67
            @Override // java.lang.Runnable
            public void run() {
                for (int i = 0; i < MainActivity.this.lineBet.intValue(); i++) {
                    MainActivity.this.scene.detachChild(MainActivity.this.lineCrop[i]);
                }
            }
        });
    }

    public void RemoveLines() {
        runOnUpdateThread(new Runnable() { // from class: com.r7games.slotpantanal.MainActivity.68
            @Override // java.lang.Runnable
            public void run() {
                for (int i = 0; i < MainActivity.this.lineBet.intValue(); i++) {
                    if (MainActivity.this.scene.getChildIndex(MainActivity.this.line[i]) >= 0) {
                        MainActivity.this.scene.detachChild(MainActivity.this.line[i]);
                    }
                }
            }
        });
    }

    public void SaiAcumualdoMax() {
        try {
            int i = this.premio.intValue();
            Double localDouble = this.acumMax;
            this.premio = Integer.valueOf((localDouble.intValue() * 100) + i);
            SetPremio();
            this.acumMax = Double.valueOf(this.dbase.acum3ini);
            this.dbase.SetAcumulado(2, this.acumMax);
            SetAcumMax();
            this.saiAcum = false;
            this.canDouble = true;
            this.verificando = false;
            this.jogaAuto = false;
            RemoveAuto();
            this.ed.putBoolean("max", false);
            this.max = false;
            this.podePagar = false;
            this.ed.putInt("travar", 1);
            this.ed.apply();
            SetDbValues();
            this.dbase.SetAcumefect(Double.valueOf(0.0d));
        } catch (Exception localException) {
            localException.printStackTrace();
        }
    }

    public void SaiAcumualdoMed() {
        try {
            int j = this.premio.intValue();
            Double localDouble = this.acumMed;
            this.premio = Integer.valueOf((localDouble.intValue() * 100) + j);
            SetPremio();
            this.acumMed = Double.valueOf(this.dbase.acum2ini);
            this.dbase.SetAcumulado(1, this.acumMed);
            SetAcumMed();
            this.saiAcum = false;
            this.verificando = false;
            this.jogaAuto = false;
            RemoveAuto();
            this.canDouble = true;
            this.ed.putBoolean("med", false);
            this.med = false;
            this.podePagar = false;
            this.ed.putInt("travar", 1);
            this.ed.apply();
            SetDbValues();
            this.dbase.SetAcumefect(Double.valueOf(0.0d));
        } catch (Exception localException) {
            localException.printStackTrace();
        }
    }

    public void SaiAcumualdoMin() {
        try {
            int j = this.premio.intValue();
            Double localDouble = this.acumMin;
            this.premio = Integer.valueOf((localDouble.intValue() * 100) + j);
            SetPremio();
            this.acumMin = Double.valueOf(this.dbase.acum1ini);
            this.dbase.SetAcumulado(0, this.acumMin);
            SetAcumMin();
            this.saiAcum = false;
            this.canDouble = true;
            this.verificando = false;
            this.jogaAuto = false;
            RemoveAuto();
            SetDbValues();
            this.dbase.SetAcumefect(Double.valueOf(0.0d));
        } catch (Exception localException) {
            localException.printStackTrace();
        }
    }

    public void SetAcumMax() {
        this.acumMaxText.setText(formatDouble(this.acumMax));
        this.acumMaxText.setPosition(584.0f - (this.acumMaxText.getWidth() / 2.0f), 414.0f);
    }

    public void SetAcumMed() {
        this.acumMedText.setText(formatDouble(this.acumMed));
        this.acumMedText.setPosition(366.0f - (this.acumMedText.getWidth() / 2.0f), 414.0f);
    }

    public void SetAcumMin() {
        this.acumMinText.setText(formatDouble(this.acumMin));
        this.acumMinText.setPosition(131.0f - (this.acumMinText.getWidth() / 2.0f), 414.0f);
    }

    public void SetAcumulado(int paramInt) {
        Double localDouble = Double.valueOf(((double) paramInt) / 1000.0d);
        if (this.acumMin.doubleValue() < this.dbase.acum1max) {
            this.acumMin = Double.valueOf(this.acumMin.doubleValue() + ((localDouble.doubleValue() / 100.0d) * this.acupor.doubleValue()));
        }
        if (this.acumMed.doubleValue() < this.dbase.acum2max) {
            this.acumMed = Double.valueOf(this.acumMed.doubleValue() + ((localDouble.doubleValue() / 100.0d) * this.acupor.doubleValue()));
        }
        if (this.acumMax.doubleValue() < this.dbase.acum3max) {
            this.acumMax = Double.valueOf(this.acumMax.doubleValue() + ((localDouble.doubleValue() / 100.0d) * this.acupor.doubleValue()));
        }
        this.dbase.SetAcumulado(0, this.acumMin);
        this.dbase.SetAcumulado(1, this.acumMed);
        this.dbase.SetAcumulado(2, this.acumMax);
        SetAcumMin();
        SetAcumMed();
        SetAcumMax();
        this.dbase.SetAcumefect(Double.valueOf(this.dbase.GetAcumefect().doubleValue() + ((((double) this.aposta.intValue()) * 4.0d) / 100.0d)));
    }

    public void SetAnbBonus(Integer paramInteger) {
        this.anbBonus.setText(paramInteger.toString());
        this.anbBonus.setPosition(206.0f - (this.anbBonus.getWidth() / 2.0f), 369.0f);
    }

    public void SetAnbCredito(Integer paramInteger) {
        this.anbCredito.setText(paramInteger.toString());
        this.anbCredito.setPosition(206.0f - (this.anbCredito.getWidth() / 2.0f), 407.0f);
    }

    public void SetAnbNum(int paramInt) {
        this.escolha = paramInt;
        int i = this.anbPrizes[paramInt].intValue();
        this.anbPrizeIndex = i;
        float f2 = this.anbAnim[paramInt].getX() + ((this.anbAnim[paramInt].getWidth() / 2.0f) - (this.numSpr[i].getWidth() / 2.0f));
        float f1 = 128.0f;
        if (i == 8) {
            f1 = 78.0f;
        }
        this.numSpr[i].setPosition(f2, f1);
        try {
            this.aboboraScene.attachChild(this.numSpr[i]);
        } catch (IllegalStateException e) {
        }
        this.mRunnable = new Runnable() { // from class: com.r7games.slotpantanal.MainActivity.69
            @Override // java.lang.Runnable
            public void run() {
                MainActivity.this.MostraAboboraAll();
            }
        };
        this.mHandler.postDelayed(this.mRunnable, 200L);
        this.mRunnable = new Runnable() { // from class: com.r7games.slotpantanal.MainActivity.70
            @Override // java.lang.Runnable
            public void run() {
                MainActivity.this.MostraAbobora();
            }
        };
        this.mHandler.postDelayed(this.mRunnable, 200L);
    }

    public void SetAnbPremio(Integer paramInteger) {
        this.anbPremio.setText(paramInteger.toString());
        this.anbPremio.setPosition(206.0f - (this.anbPremio.getWidth() / 2.0f), 443.0f);
    }

    public void SetAposta() {
        this.aposta = Integer.valueOf(this.lineBet.intValue() * this.apBet.intValue());
        this.apostaText.setText(this.aposta.toString());
        this.apostaText.setPosition(637.0f - (this.apostaText.getWidth() / 2.0f), 12.0f);
    }

    public void SetBet() {
        if (this.apBet.toString().length() >= 2) {
            this.betText.setPosition(540.0f, 44.0f);
        } else {
            this.betText.setPosition(545.0f, 44.0f);
        }
        this.betText.setText(this.apBet.toString());
        SetAposta();
    }

    public void SetCalNum(int paramInt) {
        this.escolha = paramInt;
        boolean trocado = false;
        if (!this.gen.downDificult(7) && this.caldPrizes[paramInt].intValue() > 2) {
            for (int h = 0; h < 5 && !trocado; h++) {
                if (this.caldPrizes[h].intValue() < 3) {
                    int tmp = this.caldPrizes[paramInt].intValue();
                    this.caldPrizes[paramInt] = this.caldPrizes[h];
                    this.caldPrizes[h] = Integer.valueOf(tmp);
                    trocado = true;
                }
            }
        }
        int i = this.caldPrizes[paramInt].intValue();
        this.caldPrizeIndex = i;
        float f1 = this.caldAnim[paramInt].getX() + ((this.caldAnim[paramInt].getWidth() / 2.0f) - (this.numSpr[i].getWidth() / 2.0f));
        float f2 = 145.0f;
        if (i == 8) {
            f2 = 78.0f;
        }
        this.numSpr[i].setPosition(f1, f2);
        try {
            this.caldeiraoScene.attachChild(this.numSpr[i]);
        } catch (IllegalStateException e) {
        }
        this.mRunnable = new Runnable() { // from class: com.r7games.slotpantanal.MainActivity.71
            @Override // java.lang.Runnable
            public void run() {
                MainActivity.this.MostraCaldeiraoAll();
            }
        };
        this.mHandler.postDelayed(this.mRunnable, 200L);
        this.mRunnable = new Runnable() { // from class: com.r7games.slotpantanal.MainActivity.72
            @Override // java.lang.Runnable
            public void run() {
                MainActivity.this.MostraCaldeirao();
            }
        };
        this.mHandler.postDelayed(this.mRunnable, 200L);
    }

    public void SetCaldBonus(Integer paramInteger) {
        this.caldBonus.setText(paramInteger.toString());
        this.caldBonus.setPosition(206.0f - (this.caldBonus.getWidth() / 2.0f), 369.0f);
    }

    public void SetCaldCredito(Integer paramInteger) {
        this.caldCredito.setText(paramInteger.toString());
        this.caldCredito.setPosition(206.0f - (this.caldCredito.getWidth() / 2.0f), 407.0f);
    }

    public void SetCaldPremio(Integer paramInteger) {
        this.caldPremio.setText(paramInteger.toString());
        this.caldPremio.setPosition(206.0f - (this.caldPremio.getWidth() / 2.0f), 443.0f);
    }

    public void SetCredito() {
        this.creditoText.setText(this.credito.toString());
        this.creditoText.setPosition(192.0f - (this.creditoText.getWidth() / 2.0f), 12.0f);
    }

    public void SetDado(int paramInt) {
        this.dadoAnim[paramInt].stopAnimation(NumDado());
        SetDadoPremio(this.dPremio);
        this.premio = this.dPremio;
        this.mRunnable = new Runnable() { // from class: com.r7games.slotpantanal.MainActivity.73
            @Override // java.lang.Runnable
            public void run() {
                MainActivity.this.ExitDados();
            }
        };
        this.mHandler.postDelayed(this.mRunnable, 2500L);
    }

    public void SetDadoPremio(Integer paramInteger) {
        this.dadoPremio.setText(paramInteger.toString());
        this.dadoPremio.setPosition(655.0f - (this.dadoPremio.getWidth() / 2.0f), 415.0f);
    }

    public void SetDbValues() {
        this.dbase.SetCredito(this.credito.intValue());
    }

    public void SetDobraCredito(Integer paramInteger) {
        this.dobraCredito.setText(paramInteger.toString());
        this.dobraCredito.setPosition(218.0f - (this.dobraCredito.getWidth() / 2.0f), 11.0f);
    }

    public void SetDobraPremio(Integer paramInteger) {
        this.dobraPremio.setText(paramInteger.toString());
        this.dobraPremio.setPosition(505.0f - (this.dobraPremio.getWidth() / 2.0f), 11.0f);
    }

    public void SetLine() {
        if (this.lineBet.toString().length() >= 2) {
            this.lineText.setPosition(740.0f, 132.0f);
        } else {
            this.lineText.setPosition(748.0f, 132.0f);
        }
        this.lineText.setText(this.lineBet.toString());
        DrawLines(this.lineBet.intValue());
        DrawLineCrop(this.lineBet.intValue());
    }

    public void SetMoraBonus(Integer paramInteger) {
        this.moraBonus.setText(paramInteger.toString());
        this.moraBonus.setPosition(705.0f - (this.moraBonus.getWidth() / 2.0f), 8.0f);
    }

    public void SetMoraCredito(Integer paramInteger) {
        this.moraCredito.setText(paramInteger.toString());
        this.moraCredito.setPosition(221.0f - (this.moraCredito.getWidth() / 2.0f), 8.0f);
    }

    public void SetMoraPremio(Integer paramInteger) {
        this.moraPremio.setText(paramInteger.toString());
        this.moraPremio.setPosition(470.0f - (this.moraPremio.getWidth() / 2.0f), 8.0f);
    }

    public void SetMorango(int paramInt) {
        Integer localInteger = Integer.valueOf(this.moraPrizeValues[this.moraPrizes[paramInt].intValue()].intValue() * this.apBet.intValue() * this.bonusToCall[0]);
        this.moraNum[paramInt].setText(String.valueOf(localInteger));
        float f1 = (75.0f + this.moraAnim[paramInt].getX()) - (this.moraNum[paramInt].getWidth() / 2.0f);
        float f2 = (60.0f + this.moraAnim[paramInt].getY()) - (this.moraNum[paramInt].getHeight() / 2.0f);
        try {
            this.morangoScene.attachChild(this.moraNum[paramInt]);
        } catch (Exception e) {
        }
        this.moraNum[paramInt].setPosition(f1 - 3.0f, f2 - 3.0f);
        this.moraBns += localInteger.intValue();
        SetMoraBonus(Integer.valueOf(this.moraBns));
    }

    public void SetPago(int paramInt) {
        this.pagoText.setText(String.valueOf(paramInt));
    }

    public void SetPremio() {
        this.premioText.setText(this.premio.toString());
        this.premioText.setPosition(442.0f - (this.premioText.getWidth() / 2.0f), 12.0f);
        if (this.premio.intValue() > 0) {
            this.premiacao = true;
        }
    }

    public void SetRoletaPremio(Integer paramInteger) {
        this.roletaPremio.setText(paramInteger.toString());
        this.roletaPremio.setPosition(695.0f - (this.roletaPremio.getWidth() / 2.0f), 259.0f);
    }

    public void SetSlot(int paramInt1, int paramInt2, int paramInt3, int paramInt4) {
        this.slots[paramInt1][0].setCurrentTileIndex(0, paramInt2);
        this.slots[paramInt1][1].setCurrentTileIndex(1, paramInt3);
        this.slots[paramInt1][2].setCurrentTileIndex(2, paramInt4);
    }

    public void ShowAbobora() {
        this.dbase.SetBonusToCall(this.bonusToCall[1]);
        this.mEngine.setScene(this.aboboraScene);
        runOnUpdateThread(new Runnable() { // from class: com.r7games.slotpantanal.MainActivity.74
            @Override // java.lang.Runnable
            public void run() {
                MainActivity.this.aboboraScene.detachSelf();
            }
        });
        Collections.shuffle(Arrays.asList(this.anbPrizes));
        if (this.aboboraScene.getChildIndex(this.anbBgSpr) < 0) {
            this.aboboraScene.attachChild(this.anbBgSpr);
        }
        long[] arrayOfLong = new long[36];
        arrayOfLong[0] = 50;
        arrayOfLong[1] = 50;
        arrayOfLong[2] = 50;
        arrayOfLong[3] = 50;
        arrayOfLong[4] = 50;
        arrayOfLong[5] = 50;
        for (int i = 0; i < 5; i++) {
            try {
                this.aboboraScene.attachChild(this.anbAnim[i]);
            } catch (Exception e) {
            }
            this.anbAnim[i].animate(arrayOfLong, true);
            this.aboboraScene.registerTouchArea(this.anbAnim[i]);
        }
        if (this.aboboraScene.getChildIndex(this.anbBonus) < 0) {
            this.aboboraScene.attachChild(this.anbBonus);
        }
        if (this.aboboraScene.getChildIndex(this.anbCredito) < 0) {
            this.aboboraScene.attachChild(this.anbCredito);
        }
        if (this.aboboraScene.getChildIndex(this.anbPremio) < 0) {
            this.aboboraScene.attachChild(this.anbPremio);
        }
        this.anbBns = this.apBet.intValue() * this.bonusToCall[1];
        SetAnbBonus(Integer.valueOf(this.anbBns));
        SetAnbCredito(this.credito);
        SetAnbPremio(this.premio);
    }

    public void ShowAnimSlotWin(int[][] paramArrayOfInt, int paramInt1, int paramInt2) {
        int[] arrayOfInt = {(paramInt2 * 5) + 3, (paramInt2 * 5) + 4};
        for (int i = 0; i < 5; i++) {
            if (paramInt1 < 20) {
                if (paramArrayOfInt[paramInt1][i] == paramInt2) {
                    float f3 = this.slots[i][this.slotLinhas[paramInt1][i]].getX();
                    float f4 = this.slots[i][this.slotLinhas[paramInt1][i]].getY();
                    this.animSlotWin[i][this.slotLinhas[paramInt1][i]].setPosition(f3, f4);
                    if (this.scene.getChildIndex(this.animSlotWin[i][this.slotLinhas[paramInt1][i]]) < 0) {
                        this.scene.attachChild(this.animSlotWin[i][this.slotLinhas[paramInt1][i]]);
                    }
                    this.animSlotWin[i][this.slotLinhas[paramInt1][i]].animate(this.frameDuration, arrayOfInt, -1);
                }
            } else {
                for (int j = 0; j < 3; j++) {
                    float f1 = this.slots[paramInt1 - 20][this.slotLinhas[paramInt1][j]].getX();
                    float f2 = this.slots[paramInt1 - 20][this.slotLinhas[paramInt1][j]].getY();
                    this.animSlotWin[paramInt1 - 20][this.slotLinhas[paramInt1][j]].setPosition(f1, f2);
                    if (this.scene.getChildIndex(this.animSlotWin[paramInt1 - 20][this.slotLinhas[paramInt1][j]]) < 0) {
                        this.scene.attachChild(this.animSlotWin[paramInt1 - 20][this.slotLinhas[paramInt1][j]]);
                    }
                    this.animSlotWin[paramInt1 - 20][this.slotLinhas[paramInt1][j]].animate(this.frameDuration, arrayOfInt, -1);
                }
            }
        }
    }

    public void ShowAuto() {
        if (this.scene.getChildIndex(this.jogaAutoSpr) < 0) {
            try {
                this.scene.attachChild(this.jogaAutoSpr);
            } catch (IllegalStateException e) {
                Log.d("CLEAN", "ME RECUPEREI DE UM ERRO");
            }
        }
    }

    public void ShowCaldeirao() {
        this.bonusShow = true;
        this.mEngine.setScene(this.caldeiraoScene);
        runOnUpdateThread(new Runnable() { // from class: com.r7games.slotpantanal.MainActivity.75
            @Override // java.lang.Runnable
            public void run() {
                MainActivity.this.caldeiraoScene.detachSelf();
            }
        });
        Collections.shuffle(Arrays.asList(this.caldPrizes));
        if (this.caldeiraoScene.getChildIndex(this.caldBgSpr) < 0) {
            try {
                this.caldeiraoScene.attachChild(this.caldBgSpr);
            } catch (IllegalStateException e) {
                Log.d("CLEAN", "ME RECUPEREI DE UM ERRO");
            }
        }
        long[] arrayOfLong = new long[25];
        for (int i = 0; i < 16; i++) {
            arrayOfLong[i] = 80;
        }
        for (int i2 = 0; i2 < 5; i2++) {
            if (this.caldeiraoScene.getChildIndex(this.caldAnim[i2]) < 0) {
                try {
                    this.caldeiraoScene.attachChild(this.caldAnim[i2]);
                } catch (IllegalStateException e2) {
                    Log.d("CLEAN", "ME RECUPEREI DE UM ERRO");
                }
            }
            this.caldAnim[i2].animate(arrayOfLong, true);
            this.caldeiraoScene.registerTouchArea(this.caldAnim[i2]);
        }
        try {
            this.caldeiraoScene.attachChild(this.caldBonus);
        } catch (IllegalStateException e3) {
            Log.d("CLEAN", "ME RECUPEREI DE UM ERRO");
        }
        try {
            this.caldeiraoScene.attachChild(this.caldCredito);
        } catch (IllegalStateException e4) {
            Log.d("CLEAN", "ME RECUPEREI DE UM ERRO");
        }
        try {
            this.caldeiraoScene.attachChild(this.caldPremio);
        } catch (IllegalStateException e5) {
            Log.d("CLEAN", "ME RECUPEREI DE UM ERRO");
        }
        this.caldBns = this.apBet.intValue() * this.bonusToCall[1];
        SetCaldBonus(Integer.valueOf(this.caldBns));
        SetCaldCredito(this.credito);
        SetCaldPremio(this.premio);
    }

    public void ShowDados() {
        int[] arrayOfInt = this.bonusToCall;
        arrayOfInt[3] = arrayOfInt[3] - 1;
        this.bonusShow = true;
        this.mEngine.setScene(this.dadosScene);
        runOnUpdateThread(new Runnable() { // from class: com.r7games.slotpantanal.MainActivity.76
            @Override // java.lang.Runnable
            public void run() {
                MainActivity.this.dadosScene.detachSelf();
            }
        });
        this.dadoAnim[0].setPosition(26.0f, 88.0f);
        this.dadoAnim[1].setPosition(180.0f, 88.0f);
        this.dadoAnim[2].setPosition(334.0f, 88.0f);
        this.dadoAnim[3].setPosition(488.0f, 88.0f);
        this.dadoAnim[4].setPosition(642.0f, 88.0f);
        if (this.dadosScene.getChildIndex(this.dadoBgSpr) < 0) {
            this.dadosScene.attachChild(this.dadoBgSpr);
        }
        for (int i = 0; i < 5; i++) {
            try {
                this.dadosScene.attachChild(this.dadoAnim[i]);
            } catch (IllegalStateException e) {
                Log.d("CLEAN", "ME RECUPEREI DE UM ERRO");
            }
            this.dadoAnim[i].animate(80L, true);
            this.dadosScene.registerTouchArea(this.dadoAnim[i]);
        }
        try {
            this.dadosScene.attachChild(this.dadoPremio);
        } catch (IllegalStateException e2) {
            Log.d("CLEAN", "ME RECUPEREI DE UM ERRO");
        }
        SetDadoPremio(this.premio);
    }

    public void ShowDobra() {
        runOnUpdateThread(new Runnable() { // from class: com.r7games.slotpantanal.MainActivity.77
            @Override // java.lang.Runnable
            public void run() {
                MainActivity.this.dobraScene.detachSelf();
            }
        });
        this.mEngine.setScene(this.dobraScene);
        if (this.dobraScene.getChildIndex(this.dobraBg) < 0) {
            this.dobraScene.attachChild(this.dobraBg);
        }
        long[] arrayOfLong = new long[40];
        arrayOfLong[0] = 90;
        arrayOfLong[1] = 90;
        arrayOfLong[2] = 90;
        arrayOfLong[3] = 90;
        arrayOfLong[4] = 90;
        arrayOfLong[5] = 90;
        arrayOfLong[6] = 90;
        arrayOfLong[7] = 90;
        arrayOfLong[8] = 90;
        arrayOfLong[9] = 90;
        arrayOfLong[10] = 90;
        arrayOfLong[11] = 90;
        for (int i = 0; i < 4; i++) {
            if (this.dobraScene.getChildIndex(this.mAnim[i]) < 0) {
                this.dobraScene.attachChild(this.mAnim[i]);
            }
            this.mAnim[i].animate(arrayOfLong, true);
            this.dobraScene.registerTouchArea(this.mAnim[i]);
        }
        try {
            this.dobraScene.attachChild(this.btnVoltar);
        } catch (IllegalSelectorException e) {
            Log.d("CLEAN", "ME RECUPEREI DE UM ERRO");
        }
        try {
            this.dobraScene.attachChild(this.dobraCredito);
        } catch (IllegalSelectorException e2) {
            Log.d("CLEAN", "ME RECUPEREI DE UM ERRO");
        }
        try {
            this.dobraScene.attachChild(this.dobraPremio);
        } catch (IllegalSelectorException e3) {
            Log.d("CLEAN", "ME RECUPEREI DE UM ERRO");
        }
        SetDobraCredito(this.credito);
        SetDobraPremio(this.premio);
        this.dobraScene.registerTouchArea(this.btnVoltar);
    }

    public void ShowDobraSpr() {
        this.canDouble = true;
        if (this.bonusToCall[0] > 0 || this.bonusToCall[1] > 0 || this.bonusToCall[2] > 0 || this.bonusToCall[3] > 0) {
            this.canDouble = false;
            this.jogaAuto = false;
            this.mRunnable = new Runnable() { // from class: com.r7games.slotpantanal.MainActivity.78
                @Override // java.lang.Runnable
                public void run() {
                    if (!MainActivity.this.bonusShow) {
                        if (MainActivity.this.SoundON) {
                            MainActivity.this.bonusSound.play();
                        }
                        MainActivity.this.CallBonus();
                    }
                }
            };
            this.mHandler.postDelayed(this.mRunnable, 1500L);
        }
        if (this.scene.getChildIndex(this.dobraSpr) < 0) {
            try {
                this.scene.attachChild(this.dobraSpr);
            } catch (IllegalSelectorException e) {
                Log.d("CLEAN", "ME RECUPEREI DE UM ERRO");
            }
            this.dobraSpr.animate(500L);
        }
        if (this.SoundON) {
            this.premioSound.play();
        }
    }

    public void ShowLine(int paramInt) {
        if (this.scene.getChildIndex(this.line[paramInt]) < 0) {
            try {
                this.scene.attachChild(this.line[paramInt]);
            } catch (IllegalSelectorException e) {
                Log.d("CLEAN", "ME RECUPEREI DE UM ERRO");
            }
            this.canTouch = false;
            ShowDobraSpr();
        }
    }

    public void ShowMenu() {
        this.credito = Integer.valueOf(this.credito.intValue() + this.premio.intValue());
        this.dbase.SetCredito(this.credito.intValue());
        this.premio = 0;
        this.dbase.SetReplayStatus(0);
        this.ed.putInt("lineBet", this.lineBet.intValue());
        this.ed.putInt("apBet", this.apBet.intValue());
        this.ed.apply();
        Intent localIntent = new Intent(this, (Class<?>) PainelScreen.class);
        localIntent.putExtra("notload", true);
        startActivity(localIntent);
        finish();
    }

    public void ShowMorango() {
        this.bonusShow = true;
        runOnUpdateThread(new Runnable() { // from class: com.r7games.slotpantanal.MainActivity.79
            @Override // java.lang.Runnable
            public void run() {
                MainActivity.this.morangoScene.detachSelf();
            }
        });
        this.mEngine.setScene(this.morangoScene);
        this.moranSound.setLooping(true);
        if (this.SoundON) {
            this.moranSound.play();
        }
        if (this.morangoScene.getChildIndex(this.moraBgSpr) < 0) {
            try {
                this.morangoScene.attachChild(this.moraBgSpr);
            } catch (IllegalStateException e) {
                Log.d("CLEAN", "ME RECUPEREI DE UM ERRO");
            }
        }
        long[] arrayOfLong = new long[40];
        arrayOfLong[0] = 90;
        arrayOfLong[1] = 90;
        arrayOfLong[2] = 90;
        arrayOfLong[3] = 90;
        arrayOfLong[4] = 90;
        arrayOfLong[5] = 90;
        arrayOfLong[6] = 90;
        arrayOfLong[7] = 90;
        arrayOfLong[8] = 90;
        arrayOfLong[9] = 90;
        arrayOfLong[10] = 90;
        arrayOfLong[11] = 90;
        for (int i = 0; i < 12; i++) {
            if (this.morangoScene.getChildIndex(this.moraAnim[i]) < 0) {
                this.morangoScene.attachChild(this.moraAnim[i]);
            }
            this.morangoScene.registerTouchArea(this.moraAnim[i]);
            this.moraAnim[i].animate(arrayOfLong, true);
        }
        if (this.morangoScene.getChildIndex(this.moraCredito) < 0) {
            this.morangoScene.attachChild(this.moraCredito);
        }
        if (this.morangoScene.getChildIndex(this.moraPremio) < 0) {
            this.morangoScene.attachChild(this.moraPremio);
        }
        if (this.morangoScene.getChildIndex(this.moraBonus) < 0) {
            this.morangoScene.attachChild(this.moraBonus);
        }
        SetMoraCredito(this.credito);
        SetMoraPremio(this.premio);
        SetMoraBonus(Integer.valueOf(this.moraBns));
        this.moraPrizes[0] = 0;
        this.moraPrizes[1] = 0;
        this.moraPrizes[2] = 1;
        this.moraPrizes[3] = 1;
        this.moraPrizes[4] = 2;
        this.moraPrizes[5] = 2;
        this.moraPrizes[6] = 3;
        this.moraPrizes[7] = 3;
        this.moraPrizes[8] = 4;
        this.moraPrizes[9] = 4;
        this.moraPrizes[10] = 5;
        this.moraPrizes[11] = 6;
        if (this.gen.downDificult(0)) {
            this.moraPrizes[0] = 0;
            this.moraPrizes[1] = 0;
            this.moraPrizes[2] = 1;
            this.moraPrizes[3] = 1;
            this.moraPrizes[4] = 2;
            this.moraPrizes[5] = 2;
            this.moraPrizes[6] = 3;
            this.moraPrizes[7] = 3;
            this.moraPrizes[8] = 4;
            this.moraPrizes[9] = 4;
            this.moraPrizes[10] = 5;
            this.moraPrizes[11] = 6;
        }
        if (!this.gen.downDificult(0) && this.moraBns > 0) {
            if (this.bonusToCall[0] == 2) {
                this.moraPrizes[0] = 0;
                this.moraPrizes[1] = 0;
                this.moraPrizes[2] = 1;
                this.moraPrizes[3] = 1;
                this.moraPrizes[4] = 2;
                this.moraPrizes[5] = 2;
                this.moraPrizes[6] = 3;
                this.moraPrizes[7] = 3;
                this.moraPrizes[8] = 4;
                this.moraPrizes[9] = 6;
                this.moraPrizes[10] = 6;
                this.moraPrizes[11] = 6;
            }
            if (this.bonusToCall[0] > 2) {
                this.moraPrizes[0] = 0;
                this.moraPrizes[1] = 0;
                this.moraPrizes[2] = 1;
                this.moraPrizes[3] = 1;
                this.moraPrizes[4] = 2;
                this.moraPrizes[5] = 2;
                this.moraPrizes[6] = 3;
                this.moraPrizes[7] = 3;
                this.moraPrizes[8] = 6;
                this.moraPrizes[9] = 6;
                this.moraPrizes[10] = 6;
                this.moraPrizes[11] = 6;
            }
        }
        Collections.shuffle(Arrays.asList(this.moraPrizes));
    }

    public void ShowPago() {
        if (this.scene.getChildIndex(this.pagoSpr) < 0) {
            try {
                this.scene.attachChild(this.pagoSpr);
            } catch (IllegalStateException e) {
                Log.d("CLEAN", "ME RECUPEREI DE UM ERRO");
            }
        }
        if (this.scene.getChildIndex(this.pagoText) < 0) {
            try {
                this.scene.attachChild(this.pagoText);
            } catch (IllegalStateException e2) {
                Log.d("CLEAN", "ME RECUPEREI DE UM ERRO");
            }
        }
    }

    public void ShowRoleta() {
        int[] arrayOfInt = this.bonusToCall;
        arrayOfInt[2] = arrayOfInt[2] - 1;
        this.mEngine.setScene(this.roletaScene);
        runOnUpdateThread(new Runnable() { // from class: com.r7games.slotpantanal.MainActivity.80
            @Override // java.lang.Runnable
            public void run() {
                MainActivity.this.roletaScene.detachSelf();
            }
        });
        this.roletaSpr.registerEntityModifier(this.rotationModifier);
        if (this.roletaScene.getChildIndex(this.roletaBgSpr) < 0) {
            this.roletaScene.attachChild(this.roletaBgSpr);
        }
        if (this.roletaScene.getChildIndex(this.btnParar) < 0) {
            this.roletaScene.attachChild(this.btnParar);
        }
        if (this.roletaScene.getChildIndex(this.roletaSpr) < 0) {
            this.roletaScene.attachChild(this.roletaSpr);
        }
        if (this.roletaScene.getChildIndex(this.roletaPremio) < 0) {
            this.roletaScene.attachChild(this.roletaPremio);
        }
        SetRoletaPremio(this.premio);
        this.roletaScene.registerTouchArea(this.btnParar);
        this.roletaScene.registerTouchArea(this.roletaSpr);
    }

    public void ShowTabela() {
        this.changeTab.cancel();
        this.changeTab.purge();
        switch (this.curTab) {
            case -1:
                this.canTouch = false;
                this.mEngine.setScene(this.tabelaScene);
                this.tabelaScene.detachChild(this.tabelaSpr[0]);
                this.tabelaScene.attachChild(this.tabelaSpr[0]);
                this.curTab = 0;
                this.tabelaScene.setOnSceneTouchListener(new Scene.IOnSceneTouchListener() { // from class: com.r7games.slotpantanal.MainActivity.81
                    @Override // org.anddev.andengine.entity.scene.Scene.IOnSceneTouchListener
                    public boolean onSceneTouchEvent(Scene arg0, TouchEvent arg1) {
                        if (arg1.isActionDown()) {
                            MainActivity.this.changeTab.cancel();
                            MainActivity.this.changeTab.purge();
                            MainActivity.this.mRunnable = new Runnable() { // from class: com.r7games.slotpantanal.MainActivity.81.1
                                @Override // java.lang.Runnable
                                public void run() {
                                    MainActivity.this.ShowTabela();
                                }
                            };
                            MainActivity.this.mHandler.postDelayed(MainActivity.this.mRunnable, 10L);
                            return true;
                        }
                        return true;
                    }
                });
                this.changeTab = new Timer();
                this.changeTab.schedule(new TimerTask() { // from class: com.r7games.slotpantanal.MainActivity.82
                    @Override // java.util.TimerTask, java.lang.Runnable
                    public void run() {
                        MainActivity.this.ShowTabela();
                    }
                }, 30000L);
                break;
            case 0:
                this.tabelaScene.detachChild(this.tabelaSpr[0]);
                this.tabelaScene.attachChild(this.tabelaSpr[1]);
                this.curTab = 1;
                this.changeTab = new Timer();
                this.changeTab.schedule(new TimerTask() { // from class: com.r7games.slotpantanal.MainActivity.83
                    @Override // java.util.TimerTask, java.lang.Runnable
                    public void run() {
                        MainActivity.this.ShowTabela();
                    }
                }, 3000L);
                break;
            case 1:
                this.tabelaScene.detachChild(this.tabelaSpr[1]);
                this.tabelaScene.attachChild(this.tabelaSpr[2]);
                this.curTab = 2;
                this.changeTab = new Timer();
                this.changeTab.schedule(new TimerTask() { // from class: com.r7games.slotpantanal.MainActivity.84
                    @Override // java.util.TimerTask, java.lang.Runnable
                    public void run() {
                        MainActivity.this.ShowTabela();
                    }
                }, 3000L);
                break;
            case 2:
                this.tabelaScene.setOnSceneTouchListener(null);
                this.tabelaScene.detachChild(this.tabelaSpr[2]);
                this.mEngine.setScene(this.scene);
                this.curTab = -1;
                this.canTouch = true;
                break;
        }
    }

    public void Verifica() {
        this.verificando = true;
        this.bonusToCall[0] = 0;
        this.bonusToCall[1] = 0;
        this.bonusToCall[2] = 0;
        this.bonusToCall[3] = 0;
        int i = this.lineBet.intValue();
        if (i >= 20) {
            i = 20;
        }
        for (int j = 0; j < i; j++) {
            for (int i1 = 0; i1 < 5; i1++) {
                int i4 = this.slotLinhas[j][i1];
                this.plna[j][i1] = this.slotTileValues[i1][i4];
            }
            int i2 = 0;
            if (this.plna[j][0] == 0) {
                i2 = 1;
            }
            if (this.plna[j][1] == 0) {
                i2 += 2;
            }
            if (this.plna[j][2] == 0) {
                i2 += 4;
            }
            if (this.plna[j][3] == 0) {
                i2 += 8;
            }
            if (this.plna[j][4] == 0) {
                i2 += 16;
            }
            if (i2 != 7 && i2 != 11 && i2 != 19 && i2 != 14 && i2 != 22 && i2 != 26 && i2 != 28 && i2 != 25 && i2 != 21 && i2 != 13) {
                if (i2 != 15 && i2 != 29 && i2 != 30 && i2 != 23 && i2 != 27) {
                    if (i2 == 31) {
                        this.premio = Integer.valueOf(this.premio.intValue() + (this.apBet.intValue() * 200));
                        ShowAnimSlotWin(this.plna, j, 0);
                        ShowLine(j);
                    }
                } else {
                    this.premio = Integer.valueOf(this.premio.intValue() + (this.apBet.intValue() * 50));
                    ShowAnimSlotWin(this.plna, j, 0);
                    ShowLine(j);
                }
            } else {
                this.premio = Integer.valueOf(this.premio.intValue() + (this.apBet.intValue() * 10));
                ShowAnimSlotWin(this.plna, j, 0);
                ShowLine(j);
            }
            SetPremio();
            int i3 = 0;
            if (this.plna[j][0] == 1) {
                i3 = 1;
            }
            if (this.plna[j][1] == 1) {
                i3 += 2;
            }
            if (this.plna[j][2] == 1) {
                i3 += 4;
            }
            if (this.plna[j][3] == 1) {
                i3 += 8;
            }
            if (this.plna[j][4] == 1) {
                i3 += 16;
            }
            if (i3 != 7 && i3 != 14 && i3 != 28) {
                if (i3 != 15 && i3 != 29 && i3 != 30 && i3 != 23 && i3 != 27) {
                    if (i3 == 31) {
                        this.premio = Integer.valueOf(this.premio.intValue() + (this.apBet.intValue() * 150));
                        ShowAnimSlotWin(this.plna, j, 1);
                        ShowLine(j);
                    }
                } else {
                    this.premio = Integer.valueOf(this.premio.intValue() + (this.apBet.intValue() * 100));
                    ShowAnimSlotWin(this.plna, j, 1);
                    ShowLine(j);
                }
            } else {
                this.premio = Integer.valueOf(this.premio.intValue() + (this.apBet.intValue() * 7));
                ShowAnimSlotWin(this.plna, j, 1);
                ShowLine(j);
            }
            SetPremio();
            int i5 = 0;
            if (this.plna[j][0] == 2) {
                i5 = 1;
            }
            if (this.plna[j][1] == 2) {
                i5 += 2;
            }
            if (this.plna[j][2] == 2) {
                i5 += 4;
            }
            if (this.plna[j][3] == 2) {
                i5 += 8;
            }
            if (this.plna[j][4] == 2) {
                i5 += 16;
            }
            if (i5 != 14) {
                if (i5 != 7 && i5 != 28) {
                    if (i5 != 15 && i5 != 29 && i5 != 30 && i5 != 23 && i5 != 27) {
                        if (i5 == 31) {
                            this.premio = Integer.valueOf(this.premio.intValue() + (this.apBet.intValue() * 75));
                            ShowAnimSlotWin(this.plna, j, 2);
                            ShowLine(j);
                        }
                    } else {
                        this.premio = Integer.valueOf(this.premio.intValue() + (this.apBet.intValue() * 30));
                        ShowAnimSlotWin(this.plna, j, 2);
                        ShowLine(j);
                    }
                } else {
                    this.premio = Integer.valueOf(this.premio.intValue() + (this.apBet.intValue() * 5));
                    ShowAnimSlotWin(this.plna, j, 2);
                    ShowLine(j);
                }
            } else {
                this.premio = Integer.valueOf(this.premio.intValue() + (this.apBet.intValue() * 2));
                ShowAnimSlotWin(this.plna, j, 2);
                ShowLine(j);
            }
            SetPremio();
            int i6 = 0;
            if (this.plna[j][0] == 3) {
                i6 = 1;
            }
            if (this.plna[j][1] == 3) {
                i6 += 2;
            }
            if (this.plna[j][2] == 3) {
                i6 += 4;
            }
            if (this.plna[j][3] == 3) {
                i6 += 8;
            }
            if (this.plna[j][4] == 3) {
                i6 += 16;
            }
            if (i6 != 15 && i6 != 29 && i6 != 30 && i6 != 23 && i6 != 27) {
                if (i6 == 31) {
                    this.premio = Integer.valueOf(this.premio.intValue() + (this.apBet.intValue() * 30));
                    ShowAnimSlotWin(this.plna, j, 3);
                    ShowLine(j);
                }
            } else {
                this.premio = Integer.valueOf(this.premio.intValue() + (this.apBet.intValue() * 2));
                ShowAnimSlotWin(this.plna, j, 3);
                ShowLine(j);
            }
            SetPremio();
            int i7 = 0;
            if (this.plna[j][0] == 4) {
                i7 = 1;
            }
            if (this.plna[j][1] == 4) {
                i7 += 2;
            }
            if (this.plna[j][2] == 4) {
                i7 += 4;
            }
            if (this.plna[j][3] == 4) {
                i7 += 8;
            }
            if (this.plna[j][4] == 4) {
                i7 += 16;
            }
            if (i7 != 7 && i7 != 14 && i7 != 28) {
                if (i7 != 15 && i7 != 29 && i7 != 30 && i7 != 23 && i7 != 27) {
                    if (i7 == 31) {
                        this.premio = Integer.valueOf(this.premio.intValue() + (this.apBet.intValue() * 2000));
                        ShowAnimSlotWin(this.plna, j, 4);
                        ShowLine(j);
                        this.ed.putBoolean("med", false);
                        this.med = false;
                        this.podePagar = false;
                        this.ed.putInt("travar", 1);
                        this.ed.apply();
                    }
                } else {
                    int[] arrayOfInt5 = this.bonusToCall;
                    arrayOfInt5[0] = arrayOfInt5[0] + 2;
                    ShowAnimSlotWin(this.plna, j, 4);
                    ShowLine(j);
                }
            } else {
                int[] arrayOfInt6 = this.bonusToCall;
                arrayOfInt6[0] = arrayOfInt6[0] + 1;
                ShowAnimSlotWin(this.plna, j, 4);
                ShowLine(j);
            }
            SetPremio();
            int i8 = 0;
            if (this.plna[j][0] == 5) {
                i8 = 1;
            }
            if (this.plna[j][1] == 5) {
                i8 += 2;
            }
            if (this.plna[j][2] == 5) {
                i8 += 4;
            }
            if (this.plna[j][3] == 5) {
                i8 += 8;
            }
            if (this.plna[j][4] == 5) {
                i8 += 16;
            }
            if (i8 != 14) {
                if (i8 != 7 && i8 != 28) {
                    if (i8 != 15 && i8 != 29 && i8 != 30 && i8 != 23 && i8 != 27) {
                        if (i8 == 31) {
                            this.premio = Integer.valueOf(this.premio.intValue() + (this.apBet.intValue() * 30));
                            ShowAnimSlotWin(this.plna, j, 5);
                            ShowLine(j);
                        }
                    } else {
                        this.premio = Integer.valueOf(this.premio.intValue() + (this.apBet.intValue() * 7));
                        ShowAnimSlotWin(this.plna, j, 5);
                        ShowLine(j);
                    }
                } else {
                    this.premio = Integer.valueOf(this.premio.intValue() + (this.apBet.intValue() * 5));
                    ShowAnimSlotWin(this.plna, j, 5);
                    ShowLine(j);
                }
            } else {
                this.premio = Integer.valueOf(this.premio.intValue() + (this.apBet.intValue() * 2));
                ShowAnimSlotWin(this.plna, j, 5);
                ShowLine(j);
            }
            SetPremio();
            int i9 = 0;
            if (this.plna[j][0] == 6) {
                i9 = 1;
            }
            if (this.plna[j][1] == 6) {
                i9 += 2;
            }
            if (this.plna[j][2] == 6) {
                i9 += 4;
            }
            if (this.plna[j][3] == 6) {
                i9 += 8;
            }
            if (this.plna[j][4] == 6) {
                i9 += 16;
            }
            if (i9 != 7 && i9 != 11 && i9 != 19 && i9 != 14 && i9 != 22 && i9 != 26 && i9 != 28 && i9 != 25 && i9 != 21 && i9 != 13) {
                if (i9 != 15 && i9 != 29 && i9 != 30 && i9 != 23 && i9 != 27) {
                    if (i9 == 31) {
                        ShowAnimSlotWin(this.plna, j, 6);
                        ShowLine(j);
                        if (this.SoundON) {
                            this.acumuladoSound.play();
                        }
                        this.saiAcum = true;
                        this.canTouch = false;
                        this.canDouble = false;
                        this.jogaAuto = false;
                        RemoveAuto();
                        if (this.lineBet.intValue() == 25 && this.apBet.intValue() >= 2) {
                            this.mRunnable = new Runnable() { // from class: com.r7games.slotpantanal.MainActivity.85
                                @Override // java.lang.Runnable
                                public void run() {
                                    MainActivity.this.SaiAcumualdoMax();
                                }
                            };
                        }
                        this.mHandler.postDelayed(this.mRunnable, 200L);
                    }
                } else {
                    ShowAnimSlotWin(this.plna, j, 6);
                    ShowLine(j);
                    if (this.SoundON) {
                        this.acumuladoSound.play();
                    }
                    this.saiAcum = true;
                    this.canTouch = false;
                    this.canDouble = false;
                    this.jogaAuto = false;
                    RemoveAuto();
                    if (this.lineBet.intValue() == 25 && this.apBet.intValue() >= 2) {
                        this.mRunnable = new Runnable() { // from class: com.r7games.slotpantanal.MainActivity.86
                            @Override // java.lang.Runnable
                            public void run() {
                                MainActivity.this.SaiAcumualdoMin();
                            }
                        };
                    }
                    this.mHandler.postDelayed(this.mRunnable, 200L);
                }
            } else {
                this.premio = Integer.valueOf(this.premio.intValue() + (this.apBet.intValue() * 200));
                ShowAnimSlotWin(this.plna, j, 6);
                ShowLine(j);
            }
            SetPremio();
            int i10 = 0;
            if (this.plna[j][0] == 7) {
                i10 = 1;
            }
            if (this.plna[j][1] == 7) {
                i10 += 2;
            }
            if (this.plna[j][2] == 7) {
                i10 += 4;
            }
            if (this.plna[j][3] == 7) {
                i10 += 8;
            }
            if (this.plna[j][4] == 7) {
                i10 += 16;
            }
            if (i10 != 11 && i10 != 19 && i10 != 22 && i10 != 26 && i10 != 25 && i10 != 21 && i10 != 13) {
                if (i10 != 7 && i10 != 14 && i10 != 28) {
                    if (i10 != 15 && i10 != 29 && i10 != 30 && i10 != 23 && i10 != 27) {
                        if (i10 == 31) {
                            ShowAnimSlotWin(this.plna, j, 7);
                            ShowLine(j);
                            if (this.SoundON) {
                                this.acumuladoSound.play();
                            }
                            this.saiAcum = true;
                            this.canTouch = false;
                            this.canDouble = false;
                            this.jogaAuto = false;
                            RemoveAuto();
                            if (this.lineBet.intValue() == 25 && this.apBet.intValue() >= 2) {
                                this.mRunnable = new Runnable() { // from class: com.r7games.slotpantanal.MainActivity.87
                                    @Override // java.lang.Runnable
                                    public void run() {
                                        MainActivity.this.SaiAcumualdoMed();
                                    }
                                };
                            }
                            this.mHandler.postDelayed(this.mRunnable, 200L);
                        }
                    } else {
                        this.premio = Integer.valueOf(this.premio.intValue() + (this.apBet.intValue() * 1000));
                        ShowAnimSlotWin(this.plna, j, 7);
                        ShowLine(j);
                    }
                } else {
                    this.premio = Integer.valueOf(this.premio.intValue() + (this.apBet.intValue() * TJAdUnitConstants.DEFAULT_VOLUME_CHECK_INTERVAL));
                    ShowAnimSlotWin(this.plna, j, 7);
                    ShowLine(j);
                }
            } else {
                this.premio = Integer.valueOf(this.premio.intValue() + (this.apBet.intValue() * 200));
                ShowAnimSlotWin(this.plna, j, 7);
                ShowLine(j);
            }
            SetPremio();
            int i11 = 0;
            if (this.plna[j][0] == 8) {
                i11 = 1;
            }
            if (this.plna[j][1] == 8) {
                i11 += 2;
            }
            if (this.plna[j][2] == 8) {
                i11 += 4;
            }
            if (this.plna[j][3] == 8) {
                i11 += 8;
            }
            if (this.plna[j][4] == 8) {
                i11 += 16;
            }
            if (i11 != 7 && i11 != 28) {
                if (i11 != 15 && i11 != 29 && i11 != 30 && i11 != 23 && i11 != 27) {
                    if (i11 == 31) {
                        this.premio = Integer.valueOf(this.premio.intValue() + (this.apBet.intValue() * 2000));
                        ShowAnimSlotWin(this.plna, j, 8);
                        ShowLine(j);
                        this.ed.putBoolean("med", false);
                        this.med = false;
                        this.podePagar = false;
                        this.ed.putInt("travar", 1);
                        this.ed.apply();
                    }
                } else {
                    this.premio = Integer.valueOf(this.premio.intValue() + (this.apBet.intValue() * TJAdUnitConstants.DEFAULT_VOLUME_CHECK_INTERVAL));
                    ShowAnimSlotWin(this.plna, j, 8);
                    ShowLine(j);
                }
            } else {
                this.premio = Integer.valueOf(this.premio.intValue() + (this.apBet.intValue() * 250));
                ShowAnimSlotWin(this.plna, j, 8);
                ShowLine(j);
            }
            SetPremio();
            int i12 = 0;
            if (this.plna[j][0] == 9) {
                i12 = 1;
            }
            if (this.plna[j][1] == 9) {
                i12 += 2;
            }
            if (this.plna[j][2] == 9) {
                i12 += 4;
            }
            if (this.plna[j][3] == 9) {
                i12 += 8;
            }
            if (this.plna[j][4] == 9) {
                i12 += 16;
            }
            if (i12 != 7 && i12 != 28) {
                if (i12 != 23) {
                    if (i12 != 29) {
                        if (i12 != 15 && i12 != 30) {
                            if (i12 == 31) {
                                int[] arrayOfInt7 = this.bonusToCall;
                                arrayOfInt7[1] = arrayOfInt7[1] + 3;
                                ShowAnimSlotWin(this.plna, j, 9);
                                ShowLine(j);
                            }
                        } else {
                            int[] arrayOfInt8 = this.bonusToCall;
                            arrayOfInt8[1] = arrayOfInt8[1] + 2;
                            ShowAnimSlotWin(this.plna, j, 9);
                            ShowLine(j);
                        }
                    } else {
                        int[] arrayOfInt9 = this.bonusToCall;
                        arrayOfInt9[1] = arrayOfInt9[1] + 1;
                        this.plna[j][0] = 0;
                        ShowAnimSlotWin(this.plna, j, 9);
                        ShowLine(j);
                    }
                } else {
                    int[] arrayOfInt10 = this.bonusToCall;
                    arrayOfInt10[1] = arrayOfInt10[1] + 1;
                    this.plna[j][4] = 0;
                    ShowAnimSlotWin(this.plna, j, 9);
                    ShowLine(j);
                }
            } else {
                int[] arrayOfInt11 = this.bonusToCall;
                arrayOfInt11[1] = arrayOfInt11[1] + 1;
                ShowAnimSlotWin(this.plna, j, 9);
                ShowLine(j);
            }
            SetPremio();
        }
        if (this.lineBet.intValue() >= 20) {
            for (int i13 = 0; i13 < 5; i13++) {
                for (int j2 = 0; j2 < 3; j2++) {
                    int i14 = this.slotLinhas[i13 + 20][j2];
                    this.plna[i13 + 20][j2] = this.slotTileValues[i13][i14];
                }
                int j3 = 0;
                if (this.plna[i13 + 20][0] == 0) {
                    j3 = 1;
                }
                if (this.plna[i13 + 20][1] == 0) {
                    j3 += 2;
                }
                if (this.plna[i13 + 20][2] == 0) {
                    j3 += 4;
                }
                if (j3 == 7) {
                    this.premio = Integer.valueOf(this.premio.intValue() + (this.apBet.intValue() * 100));
                    ShowAnimSlotWin(this.plna, i13 + 20, 0);
                    ShowLine(i13 + 20);
                }
                int j4 = 0;
                if (this.plna[i13 + 20][0] == 1) {
                    j4 = 1;
                }
                if (this.plna[i13 + 20][1] == 1) {
                    j4 += 2;
                }
                if (this.plna[i13 + 20][2] == 1) {
                    j4 += 4;
                }
                if (j4 == 7) {
                    this.canTouch = false;
                    this.premio = Integer.valueOf(this.premio.intValue() + (this.apBet.intValue() * 70));
                    int[] arrayOfInt1 = this.bonusToCall;
                    arrayOfInt1[2] = arrayOfInt1[2] + 1;
                    ShowAnimSlotWin(this.plna, i13 + 20, 1);
                    ShowLine(i13 + 20);
                }
                int k = 0;
                if (this.plna[i13 + 20][0] == 2) {
                    k = 1;
                }
                if (this.plna[i13 + 20][1] == 2) {
                    k += 2;
                }
                if (this.plna[i13 + 20][2] == 2) {
                    k += 4;
                }
                if (k == 7) {
                    this.canTouch = false;
                    this.premio = Integer.valueOf(this.premio.intValue() + (this.apBet.intValue() * 50));
                    int[] arrayOfInt2 = this.bonusToCall;
                    arrayOfInt2[3] = arrayOfInt2[3] + 1;
                    ShowAnimSlotWin(this.plna, i13 + 20, 2);
                    ShowLine(i13 + 20);
                }
                int m = 0;
                if (this.plna[i13 + 20][0] == 3) {
                    m = 1;
                }
                if (this.plna[i13 + 20][1] == 3) {
                    m += 2;
                }
                if (this.plna[i13 + 20][2] == 3) {
                    m += 4;
                }
                if (m == 7) {
                    this.premio = Integer.valueOf(this.premio.intValue() + (this.apBet.intValue() * 20));
                    ShowAnimSlotWin(this.plna, i13 + 20, 3);
                    ShowLine(i13 + 20);
                }
                int m2 = 0;
                if (this.plna[i13 + 20][0] == 4) {
                    m2 = 1;
                }
                if (this.plna[i13 + 20][1] == 4) {
                    m2 += 2;
                }
                if (this.plna[i13 + 20][2] == 4) {
                    m2 += 4;
                }
                if (m2 == 7) {
                    int[] arrayOfInt3 = this.bonusToCall;
                    arrayOfInt3[0] = arrayOfInt3[0] + 1;
                    ShowAnimSlotWin(this.plna, i13 + 20, 4);
                    ShowLine(i13 + 20);
                }
                int n = 0;
                if (this.plna[i13 + 20][0] == 5) {
                    n = 1;
                }
                if (this.plna[i13 + 20][1] == 5) {
                    n += 2;
                }
                if (this.plna[i13 + 20][2] == 5) {
                    n += 4;
                }
                if (n == 7) {
                    this.premio = Integer.valueOf(this.premio.intValue() + (this.apBet.intValue() * 20));
                    ShowAnimSlotWin(this.plna, i13 + 20, 5);
                    ShowLine(i13 + 20);
                }
                int n2 = 0;
                if (this.plna[i13 + 20][0] == 6) {
                    n2 = 1;
                }
                if (this.plna[i13 + 20][1] == 6) {
                    n2 += 2;
                }
                if (this.plna[i13 + 20][2] == 6) {
                    n2 += 4;
                }
                if (n2 == 7) {
                    this.premio = Integer.valueOf(this.premio.intValue() + (this.apBet.intValue() * 300));
                    ShowAnimSlotWin(this.plna, i13 + 20, 6);
                    ShowLine(i13 + 20);
                }
                int n3 = 0;
                if (this.plna[i13 + 20][0] == 7) {
                    n3 = 1;
                }
                if (this.plna[i13 + 20][1] == 7) {
                    n3 += 2;
                }
                if (this.plna[i13 + 20][2] == 7) {
                    n3 += 4;
                }
                if (n3 == 7) {
                    this.premio = Integer.valueOf(this.premio.intValue() + (this.apBet.intValue() * 250));
                    ShowAnimSlotWin(this.plna, i13 + 20, 7);
                    ShowLine(i13 + 20);
                }
                int n4 = 0;
                if (this.plna[i13 + 20][0] == 8) {
                    n4 = 1;
                }
                if (this.plna[i13 + 20][1] == 8) {
                    n4 += 2;
                }
                if (this.plna[i13 + 20][2] == 8) {
                    n4 += 4;
                }
                if (n4 == 7) {
                    this.premio = Integer.valueOf(this.premio.intValue() + (this.apBet.intValue() * 200));
                    ShowAnimSlotWin(this.plna, i13 + 20, 8);
                    ShowLine(i13 + 20);
                }
                int n5 = 0;
                if (this.plna[i13 + 20][0] == 9) {
                    n5 = 1;
                }
                if (this.plna[i13 + 20][1] == 9) {
                    n5 += 2;
                }
                if (this.plna[i13 + 20][2] == 9) {
                    n5 += 4;
                }
                if (n5 == 7) {
                    int[] arrayOfInt4 = this.bonusToCall;
                    arrayOfInt4[1] = arrayOfInt4[1] + 1;
                    ShowAnimSlotWin(this.plna, i13 + 20, 9);
                    ShowLine(i13 + 20);
                }
                SetPremio();
            }
        }
    }

    @Override // org.anddev.andengine.ui.activity.BaseGameActivity, android.app.Activity
    public void onDestroy() {
        super.onDestroy();
    }

    @Override // com.r7games.slotpantanal.google.services.GBaseGameMainActivity, org.anddev.andengine.ui.IGameInterface
    public void onLoadComplete() {
        try {
            add_admob_interstitial();
        } catch (Exception localException) {
            localException.printStackTrace();
        }
        this.med = this.sp.getBoolean("med", false);
        this.max = this.sp.getBoolean("max", false);
        this.score = this.sp.getLong("score_halloween", 0L);
        if (((ConnectivityManager) getSystemService("connectivity")).getActiveNetworkInfo() != null) {
            this.t = ((GoogleAnalyticsApp) getApplication()).getTracker(GoogleAnalyticsApp.TrackerName.APP_TRACKER);
            this.t.setScreenName("MainActivity");
            this.t.send(new HitBuilders.ScreenViewBuilder().build());
        }
        this.bloqueado = this.dbase.GetBloqueado();
        this.mEngine.registerUpdateHandler(new TimerHandler(0.01f, new ITimerCallback() { // from class: com.r7games.slotpantanal.MainActivity.88
            @Override // org.anddev.andengine.engine.handler.timer.ITimerCallback
            public void onTimePassed(TimerHandler paramAnonymousTimerHandler) {
                MainActivity.this.mEngine.unregisterUpdateHandler(paramAnonymousTimerHandler);
                MainActivity.this.scene = new Scene();
                MainActivity.this.tabelaScene = new Scene();
                MainActivity.this.dobraScene = new Scene();
                MainActivity.this.roletaScene = new Scene();
                MainActivity.this.dadosScene = new Scene();
                MainActivity.this.caldeiraoScene = new Scene();
                MainActivity.this.aboboraScene = new Scene();
                MainActivity.this.morangoScene = new Scene();
                MainActivity.this.bonusReplay = MainActivity.this.dbase.GetBonusToCall();
                MainActivity.this.InitAll();
                MainActivity.this.DrawAll();
                if ((MainActivity.this.dbase.GetReplayStatus() == 1 || MainActivity.this.bonusReplay > 0) && MainActivity.this.credito.intValue() > 0) {
                    MainActivity.this.InitReplay();
                } else {
                    MainActivity.this.lineBet = Integer.valueOf(MainActivity.this.sp.getInt("lineBet", 1));
                    MainActivity.this.apBet = Integer.valueOf(MainActivity.this.sp.getInt("apBet", 1));
                    if (MainActivity.this.apBet.intValue() > 40) {
                        MainActivity.this.apBet = 1;
                    }
                    if (MainActivity.this.lineBet.intValue() > 25) {
                        MainActivity.this.lineBet = 1;
                    }
                    MainActivity.this.SetAposta();
                    MainActivity.this.SetBet();
                    MainActivity.this.SetLine();
                    MainActivity.this.RemoveLines();
                }
                MainActivity.this.mEngine.setScene(MainActivity.this.scene);
            }
        }));
    }

    protected void displayInterstitial() {
        runOnUiThread(new Runnable() { // from class: com.r7games.slotpantanal.MainActivity.89
            @Override // java.lang.Runnable
            public void run() {
                if (MainActivity.this.adMob_interstitial.isLoaded() && MainActivity.this.show_admob_interstitial) {
                    Log.i("ADMOB", "AD IS LOAD");
                    MainActivity.this.adMob_interstitial.show();
                } else {
                    Log.i("ADMOB", "AD IS NOT LOAD");
                }
            }
        });
        this.adMob_interstitial.setAdListener(new AdListener() { // from class: com.r7games.slotpantanal.MainActivity.90
            @Override // com.google.android.gms.ads.AdListener
            public void onAdClosed() {
                MainActivity.this.show_admob_interstitial = false;
            }
        });
    }

    void add_admob_interstitial() {
        this.adMob_interstitial = new InterstitialAd(this);
        this.adMob_interstitial.setAdUnitId(getString(R.string.adMob_interstitial));
        AdRequest.Builder builder = new AdRequest.Builder();
        this.adMob_interstitial.loadAd(builder.build());
    }

    @Override // com.r7games.slotpantanal.google.services.GBaseGameMainActivity, org.anddev.andengine.ui.IGameInterface
    public Engine onLoadEngine() {
        this.camera = new Camera(BitmapDescriptorFactory.HUE_RED, BitmapDescriptorFactory.HUE_RED, 800.0f, 480.0f);
        return new Engine(new EngineOptions(true, EngineOptions.ScreenOrientation.LANDSCAPE, new FillResolutionPolicy(), this.camera).setNeedsSound(true));
    }

    @Override // com.r7games.slotpantanal.google.services.GBaseGameMainActivity, org.anddev.andengine.ui.IGameInterface
    public void onLoadResources() {
        this.idioma = this.util.idioma();
        this.sp = PreferenceManager.getDefaultSharedPreferences(this);
        this.ed = this.sp.edit();
        getWindow().setFlags(1024, 1024);
        hide_navigation_bar();
        this.splashAtlas = new BitmapTextureAtlas(1024, 1024);
        this.splashRegion = BitmapTextureAtlasTextureRegionFactory.createFromAsset(this.splashAtlas, this, "05_splash.png", 0, 0);
        this.mEngine.getTextureManager().loadTexture(this.splashAtlas);
        this.splash = new Sprite(BitmapDescriptorFactory.HUE_RED, BitmapDescriptorFactory.HUE_RED, this.splashRegion);
    }

    @Override // com.r7games.slotpantanal.google.services.GBaseGameMainActivity, org.anddev.andengine.ui.IGameInterface
    public Scene onLoadScene() {
        this.splashScene = new Scene();
        this.splashScene.attachChild(this.splash);
        return this.splashScene;
    }

    @Override // org.anddev.andengine.ui.activity.BaseGameActivity, android.app.Activity
    public void onPause() {
        super.onPause();
        RemoveAuto();
        this.jogaAuto = false;
        hide_navigation_bar();
    }

    @Override // org.anddev.andengine.ui.activity.BaseGameActivity, android.app.Activity
    public void onResume() {
        super.onResume();
        hide_navigation_bar();
    }

    public void removeAnimSlot(int paramInt) {
        this.scene.detachChild(this.animSlot[paramInt]);
        if (paramInt < 5) {
            SetSlot(paramInt, this.slotTileValues[paramInt][0], this.slotTileValues[paramInt][1], this.slotTileValues[paramInt][2]);
        }
    }

    public void removeAnimSlotWin() {
        runOnUpdateThread(new Runnable() { // from class: com.r7games.slotpantanal.MainActivity.91
            @Override // java.lang.Runnable
            public void run() {
                for (int j = 0; j < 5; j++) {
                    for (int i = 0; i < 3; i++) {
                        if (MainActivity.this.scene.getChildIndex(MainActivity.this.animSlotWin[j][i]) >= 0) {
                            MainActivity.this.scene.detachChild(MainActivity.this.animSlotWin[j][i]);
                        }
                    }
                }
            }
        });
    }

    @Override // android.app.Activity
    public void onBackPressed() {
        confirmation();
    }

    /* JADX INFO: renamed from: com.r7games.slotpantanal.MainActivity$92, reason: invalid class name */
    class AnonymousClass92 implements Runnable {
        AnonymousClass92() {
        }

        /* JADX INFO: renamed from: com.r7games.slotpantanal.MainActivity$92$1, reason: invalid class name */
        class AnonymousClass1 implements Runnable {
            AnonymousClass1() {
            }

            @Override // java.lang.Runnable
            public void run() {
                DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() { // from class: com.r7games.slotpantanal.MainActivity.92.1.1
                    @Override // android.content.DialogInterface.OnClickListener
                    public void onClick(DialogInterface dialog, int which) {
                        switch (which) {
                            case -2:
                                MainActivity.this.getWindow().setFlags(1024, 1024);
                                MainActivity.this.hide_navigation_bar();
                                break;
                            case -1:
                                try {
                                    if (MainActivity.this.adMob_interstitial != null) {
                                        MainActivity.this.runOnUiThread(new Runnable() { // from class: com.r7games.slotpantanal.MainActivity.92.1.1.1
                                            @Override // java.lang.Runnable
                                            public void run() {
                                                if (MainActivity.this.adMob_interstitial.isLoaded() && MainActivity.this.show_admob_interstitial) {
                                                    Log.i("ADMOB", "AD IS LOAD");
                                                    MainActivity.this.adMob_interstitial.show();
                                                } else {
                                                    Log.i("ADMOB", "AD IS NOT LOAD");
                                                    MainActivity.this.ShowMenu();
                                                }
                                            }
                                        });
                                        MainActivity.this.adMob_interstitial.setAdListener(new AdListener() { // from class: com.r7games.slotpantanal.MainActivity.92.1.1.2
                                            @Override // com.google.android.gms.ads.AdListener
                                            public void onAdClosed() {
                                                MainActivity.this.ShowMenu();
                                            }
                                        });
                                    } else {
                                        MainActivity.this.ShowMenu();
                                    }
                                } catch (Exception e) {
                                    e.printStackTrace();
                                    return;
                                }
                                break;
                        }
                    }
                };
                String ok = "Yes";
                String nok = "No";
                String texto = "You want to leave the game?";
                if (!MainActivity.this.idioma.equalsIgnoreCase("pt")) {
                    if (MainActivity.this.idioma.equalsIgnoreCase("es")) {
                        ok = "Sí";
                        nok = "No";
                        texto = "¿Quieres dejar el juego?";
                    }
                } else {
                    ok = "Sim";
                    nok = "Não";
                    texto = "Você realmente deseja sair do jogo?";
                }
                AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                builder.setMessage(texto).setPositiveButton(ok, dialogClickListener).setNegativeButton(nok, dialogClickListener).show();
            }
        }

        @Override // java.lang.Runnable
        public void run() {
            MainActivity.this.runOnUiThread(new AnonymousClass1());
        }
    }

    public void confirmation() {
        runOnUpdateThread(new AnonymousClass92());
    }

    @Override // com.r7games.slotpantanal.google.services.GBaseGameMainActivity, android.app.Activity
    protected void onStart() {
        super.onStart();
        GoogleAnalytics.getInstance(this).reportActivityStart(this);
    }

    @Override // com.r7games.slotpantanal.google.services.GBaseGameMainActivity, android.app.Activity
    protected void onStop() {
        super.onStop();
        GoogleAnalytics.getInstance(this).reportActivityStop(this);
    }
}
