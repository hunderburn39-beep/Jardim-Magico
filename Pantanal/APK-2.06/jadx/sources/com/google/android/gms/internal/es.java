package com.google.android.gms.internal;

import android.os.Handler;
import android.os.Looper;
import android.os.SystemClock;
import com.google.android.gms.cast.MediaInfo;
import com.google.android.gms.cast.MediaStatus;
import com.tapjoy.TJAdUnitConstants;
import java.io.IOException;
import java.util.concurrent.TimeUnit;
import org.anddev.andengine.level.util.constants.LevelConstants;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/* JADX INFO: loaded from: classes.dex */
public class es extends em {
    private static final String NAMESPACE = eo.X("com.google.cast.media");
    private static final long zG = TimeUnit.HOURS.toMillis(24);
    private static final long zH = TimeUnit.HOURS.toMillis(24);
    private static final long zI = TimeUnit.HOURS.toMillis(24);
    private static final long zJ = TimeUnit.SECONDS.toMillis(1);
    private final Handler mHandler;
    private long zK;
    private MediaStatus zL;
    private final ev zM;
    private final ev zN;
    private final ev zO;
    private final ev zP;
    private final ev zQ;
    private final ev zR;
    private final ev zS;
    private final ev zT;
    private final Runnable zU;
    private boolean zV;

    private class a implements Runnable {
        private a() {
        }

        @Override // java.lang.Runnable
        public void run() {
            boolean z;
            es.this.zV = false;
            long jElapsedRealtime = SystemClock.elapsedRealtime();
            es.this.zM.d(jElapsedRealtime, 3);
            es.this.zN.d(jElapsedRealtime, 3);
            es.this.zO.d(jElapsedRealtime, 3);
            es.this.zP.d(jElapsedRealtime, 3);
            es.this.zQ.d(jElapsedRealtime, 3);
            es.this.zR.d(jElapsedRealtime, 3);
            es.this.zS.d(jElapsedRealtime, 3);
            es.this.zT.d(jElapsedRealtime, 3);
            synchronized (ev.Ab) {
                z = es.this.zM.dU() || es.this.zQ.dU() || es.this.zR.dU() || es.this.zS.dU() || es.this.zT.dU();
            }
            es.this.w(z);
        }
    }

    public es() {
        this(null);
    }

    public es(String str) {
        super(NAMESPACE, "MediaControlChannel", str);
        this.mHandler = new Handler(Looper.getMainLooper());
        this.zU = new a();
        this.zM = new ev(zH);
        this.zN = new ev(zG);
        this.zO = new ev(zG);
        this.zP = new ev(zG);
        this.zQ = new ev(zI);
        this.zR = new ev(zG);
        this.zS = new ev(zG);
        this.zT = new ev(zG);
        dS();
    }

    private void a(long j, JSONObject jSONObject) throws JSONException {
        int iA;
        boolean z = true;
        boolean zN = this.zM.n(j);
        boolean z2 = this.zQ.dU() && !this.zQ.n(j);
        if ((!this.zR.dU() || this.zR.n(j)) && (!this.zS.dU() || this.zS.n(j))) {
            z = false;
        }
        int i = z2 ? 2 : 0;
        if (z) {
            i |= 1;
        }
        if (zN || this.zL == null) {
            this.zL = new MediaStatus(jSONObject);
            this.zK = SystemClock.elapsedRealtime();
            iA = 7;
        } else {
            iA = this.zL.a(jSONObject, i);
        }
        if ((iA & 1) != 0) {
            this.zK = SystemClock.elapsedRealtime();
            onStatusUpdated();
        }
        if ((iA & 2) != 0) {
            this.zK = SystemClock.elapsedRealtime();
            onStatusUpdated();
        }
        if ((iA & 4) != 0) {
            onMetadataUpdated();
        }
        this.zM.c(j, 0);
        this.zN.c(j, 0);
        this.zO.c(j, 0);
        this.zP.c(j, 0);
        this.zQ.c(j, 0);
        this.zR.c(j, 0);
        this.zS.c(j, 0);
        this.zT.c(j, 0);
    }

    private void dS() {
        w(false);
        this.zK = 0L;
        this.zL = null;
        this.zM.clear();
        this.zQ.clear();
        this.zR.clear();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void w(boolean z) {
        if (this.zV != z) {
            this.zV = z;
            if (z) {
                this.mHandler.postDelayed(this.zU, zJ);
            } else {
                this.mHandler.removeCallbacks(this.zU);
            }
        }
    }

    @Override // com.google.android.gms.internal.em
    public final void U(String str) {
        this.yY.b("message received: %s", str);
        try {
            JSONObject jSONObject = new JSONObject(str);
            String string = jSONObject.getString("type");
            long jOptLong = jSONObject.optLong("requestId", -1L);
            if (string.equals("MEDIA_STATUS")) {
                JSONArray jSONArray = jSONObject.getJSONArray("status");
                if (jSONArray.length() > 0) {
                    a(jOptLong, jSONArray.getJSONObject(0));
                } else {
                    this.zL = null;
                    onStatusUpdated();
                    onMetadataUpdated();
                    this.zT.c(jOptLong, 0);
                }
            } else if (string.equals("INVALID_PLAYER_STATE")) {
                this.yY.d("received unexpected error: Invalid Player State.", new Object[0]);
                JSONObject jSONObjectOptJSONObject = jSONObject.optJSONObject("customData");
                this.zM.b(jOptLong, 1, jSONObjectOptJSONObject);
                this.zN.b(jOptLong, 1, jSONObjectOptJSONObject);
                this.zO.b(jOptLong, 1, jSONObjectOptJSONObject);
                this.zP.b(jOptLong, 1, jSONObjectOptJSONObject);
                this.zQ.b(jOptLong, 1, jSONObjectOptJSONObject);
                this.zR.b(jOptLong, 1, jSONObjectOptJSONObject);
                this.zS.b(jOptLong, 1, jSONObjectOptJSONObject);
                this.zT.b(jOptLong, 1, jSONObjectOptJSONObject);
            } else if (string.equals("LOAD_FAILED")) {
                this.zM.b(jOptLong, 1, jSONObject.optJSONObject("customData"));
            } else if (string.equals("LOAD_CANCELLED")) {
                this.zM.b(jOptLong, 2, jSONObject.optJSONObject("customData"));
            } else if (string.equals("INVALID_REQUEST")) {
                this.yY.d("received unexpected error: Invalid Request.", new Object[0]);
                JSONObject jSONObjectOptJSONObject2 = jSONObject.optJSONObject("customData");
                this.zM.b(jOptLong, 1, jSONObjectOptJSONObject2);
                this.zN.b(jOptLong, 1, jSONObjectOptJSONObject2);
                this.zO.b(jOptLong, 1, jSONObjectOptJSONObject2);
                this.zP.b(jOptLong, 1, jSONObjectOptJSONObject2);
                this.zQ.b(jOptLong, 1, jSONObjectOptJSONObject2);
                this.zR.b(jOptLong, 1, jSONObjectOptJSONObject2);
                this.zS.b(jOptLong, 1, jSONObjectOptJSONObject2);
                this.zT.b(jOptLong, 1, jSONObjectOptJSONObject2);
            }
        } catch (JSONException e) {
            this.yY.d("Message is malformed (%s); ignoring: %s", e.getMessage(), str);
        }
    }

    public long a(eu euVar) throws IOException {
        JSONObject jSONObject = new JSONObject();
        long jDE = dE();
        this.zT.a(jDE, euVar);
        w(true);
        try {
            jSONObject.put("requestId", jDE);
            jSONObject.put("type", "GET_STATUS");
            if (this.zL != null) {
                jSONObject.put("mediaSessionId", this.zL.dC());
            }
        } catch (JSONException e) {
        }
        a(jSONObject.toString(), jDE, (String) null);
        return jDE;
    }

    public long a(eu euVar, double d, JSONObject jSONObject) throws IllegalStateException, IOException, IllegalArgumentException {
        if (Double.isInfinite(d) || Double.isNaN(d)) {
            throw new IllegalArgumentException("Volume cannot be " + d);
        }
        JSONObject jSONObject2 = new JSONObject();
        long jDE = dE();
        this.zR.a(jDE, euVar);
        w(true);
        try {
            jSONObject2.put("requestId", jDE);
            jSONObject2.put("type", "SET_VOLUME");
            jSONObject2.put("mediaSessionId", dC());
            JSONObject jSONObject3 = new JSONObject();
            jSONObject3.put(LevelConstants.TAG_LEVEL, d);
            jSONObject2.put("volume", jSONObject3);
            if (jSONObject != null) {
                jSONObject2.put("customData", jSONObject);
            }
        } catch (JSONException e) {
        }
        a(jSONObject2.toString(), jDE, (String) null);
        return jDE;
    }

    public long a(eu euVar, long j, int i, JSONObject jSONObject) throws IllegalStateException, IOException {
        JSONObject jSONObject2 = new JSONObject();
        long jDE = dE();
        this.zQ.a(jDE, euVar);
        w(true);
        try {
            jSONObject2.put("requestId", jDE);
            jSONObject2.put("type", "SEEK");
            jSONObject2.put("mediaSessionId", dC());
            jSONObject2.put(TJAdUnitConstants.String.VIDEO_CURRENT_TIME, eo.m(j));
            if (i == 1) {
                jSONObject2.put("resumeState", "PLAYBACK_START");
            } else if (i == 2) {
                jSONObject2.put("resumeState", "PLAYBACK_PAUSE");
            }
            if (jSONObject != null) {
                jSONObject2.put("customData", jSONObject);
            }
        } catch (JSONException e) {
        }
        a(jSONObject2.toString(), jDE, (String) null);
        return jDE;
    }

    public long a(eu euVar, MediaInfo mediaInfo, boolean z, long j, JSONObject jSONObject) throws IOException {
        JSONObject jSONObject2 = new JSONObject();
        long jDE = dE();
        this.zM.a(jDE, euVar);
        w(true);
        try {
            jSONObject2.put("requestId", jDE);
            jSONObject2.put("type", "LOAD");
            jSONObject2.put("media", mediaInfo.dB());
            jSONObject2.put("autoplay", z);
            jSONObject2.put(TJAdUnitConstants.String.VIDEO_CURRENT_TIME, eo.m(j));
            if (jSONObject != null) {
                jSONObject2.put("customData", jSONObject);
            }
        } catch (JSONException e) {
        }
        a(jSONObject2.toString(), jDE, (String) null);
        return jDE;
    }

    public long a(eu euVar, JSONObject jSONObject) throws IOException {
        JSONObject jSONObject2 = new JSONObject();
        long jDE = dE();
        this.zN.a(jDE, euVar);
        w(true);
        try {
            jSONObject2.put("requestId", jDE);
            jSONObject2.put("type", "PAUSE");
            jSONObject2.put("mediaSessionId", dC());
            if (jSONObject != null) {
                jSONObject2.put("customData", jSONObject);
            }
        } catch (JSONException e) {
        }
        a(jSONObject2.toString(), jDE, (String) null);
        return jDE;
    }

    public long a(eu euVar, boolean z, JSONObject jSONObject) throws IllegalStateException, IOException {
        JSONObject jSONObject2 = new JSONObject();
        long jDE = dE();
        this.zS.a(jDE, euVar);
        w(true);
        try {
            jSONObject2.put("requestId", jDE);
            jSONObject2.put("type", "SET_VOLUME");
            jSONObject2.put("mediaSessionId", dC());
            JSONObject jSONObject3 = new JSONObject();
            jSONObject3.put("muted", z);
            jSONObject2.put("volume", jSONObject3);
            if (jSONObject != null) {
                jSONObject2.put("customData", jSONObject);
            }
        } catch (JSONException e) {
        }
        a(jSONObject2.toString(), jDE, (String) null);
        return jDE;
    }

    @Override // com.google.android.gms.internal.em
    public void a(long j, int i) {
        this.zM.c(j, i);
        this.zN.c(j, i);
        this.zO.c(j, i);
        this.zP.c(j, i);
        this.zQ.c(j, i);
        this.zR.c(j, i);
        this.zS.c(j, i);
        this.zT.c(j, i);
    }

    public long b(eu euVar, JSONObject jSONObject) throws IOException {
        JSONObject jSONObject2 = new JSONObject();
        long jDE = dE();
        this.zP.a(jDE, euVar);
        w(true);
        try {
            jSONObject2.put("requestId", jDE);
            jSONObject2.put("type", "STOP");
            jSONObject2.put("mediaSessionId", dC());
            if (jSONObject != null) {
                jSONObject2.put("customData", jSONObject);
            }
        } catch (JSONException e) {
        }
        a(jSONObject2.toString(), jDE, (String) null);
        return jDE;
    }

    public long c(eu euVar, JSONObject jSONObject) throws IllegalStateException, IOException {
        JSONObject jSONObject2 = new JSONObject();
        long jDE = dE();
        this.zO.a(jDE, euVar);
        w(true);
        try {
            jSONObject2.put("requestId", jDE);
            jSONObject2.put("type", "PLAY");
            jSONObject2.put("mediaSessionId", dC());
            if (jSONObject != null) {
                jSONObject2.put("customData", jSONObject);
            }
        } catch (JSONException e) {
        }
        a(jSONObject2.toString(), jDE, (String) null);
        return jDE;
    }

    public long dC() throws IllegalStateException {
        if (this.zL == null) {
            throw new IllegalStateException("No current media session");
        }
        return this.zL.dC();
    }

    @Override // com.google.android.gms.internal.em
    public void dF() {
        dS();
    }

    public long getApproximateStreamPosition() {
        MediaInfo mediaInfo = getMediaInfo();
        if (mediaInfo == null || this.zK == 0) {
            return 0L;
        }
        double playbackRate = this.zL.getPlaybackRate();
        long streamPosition = this.zL.getStreamPosition();
        int playerState = this.zL.getPlayerState();
        if (playbackRate == 0.0d || playerState != 2) {
            return streamPosition;
        }
        long jElapsedRealtime = SystemClock.elapsedRealtime() - this.zK;
        long j = jElapsedRealtime < 0 ? 0L : jElapsedRealtime;
        if (j == 0) {
            return streamPosition;
        }
        long streamDuration = mediaInfo.getStreamDuration();
        long j2 = streamPosition + ((long) (j * playbackRate));
        if (j2 <= streamDuration) {
            streamDuration = j2 < 0 ? 0L : j2;
        }
        return streamDuration;
    }

    public MediaInfo getMediaInfo() {
        if (this.zL == null) {
            return null;
        }
        return this.zL.getMediaInfo();
    }

    public MediaStatus getMediaStatus() {
        return this.zL;
    }

    public long getStreamDuration() {
        MediaInfo mediaInfo = getMediaInfo();
        if (mediaInfo != null) {
            return mediaInfo.getStreamDuration();
        }
        return 0L;
    }

    protected void onMetadataUpdated() {
    }

    protected void onStatusUpdated() {
    }
}
