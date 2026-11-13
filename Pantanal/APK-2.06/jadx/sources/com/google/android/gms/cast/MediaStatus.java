package com.google.android.gms.cast;

import com.fyber.ads.videos.RewardedVideoActivity;
import com.google.android.gms.internal.eo;
import com.tapjoy.TJAdUnitConstants;
import org.anddev.andengine.level.util.constants.LevelConstants;
import org.json.JSONException;
import org.json.JSONObject;

/* JADX INFO: loaded from: classes.dex */
public final class MediaStatus {
    public static final long COMMAND_PAUSE = 1;
    public static final long COMMAND_SEEK = 2;
    public static final long COMMAND_SET_VOLUME = 4;
    public static final long COMMAND_SKIP_BACKWARD = 32;
    public static final long COMMAND_SKIP_FORWARD = 16;
    public static final long COMMAND_TOGGLE_MUTE = 8;
    public static final int IDLE_REASON_CANCELED = 2;
    public static final int IDLE_REASON_ERROR = 4;
    public static final int IDLE_REASON_FINISHED = 1;
    public static final int IDLE_REASON_INTERRUPTED = 3;
    public static final int IDLE_REASON_NONE = 0;
    public static final int PLAYER_STATE_BUFFERING = 4;
    public static final int PLAYER_STATE_IDLE = 1;
    public static final int PLAYER_STATE_PAUSED = 3;
    public static final int PLAYER_STATE_PLAYING = 2;
    public static final int PLAYER_STATE_UNKNOWN = 0;
    private long yA;
    private long yB;
    private double yC;
    private boolean yD;
    private JSONObject yn;
    private MediaInfo yo;
    private long yw;
    private double yx;
    private int yy;
    private int yz;

    public MediaStatus(JSONObject json) throws JSONException {
        a(json, 0);
    }

    public int a(JSONObject jSONObject, int i) throws JSONException {
        int i2;
        int i3;
        int i4 = 2;
        long j = jSONObject.getLong("mediaSessionId");
        if (j != this.yw) {
            this.yw = j;
            i2 = 1;
        } else {
            i2 = 0;
        }
        if (jSONObject.has("playerState")) {
            String string = jSONObject.getString("playerState");
            if (string.equals("IDLE")) {
                i3 = 1;
            } else if (string.equals("PLAYING")) {
                i3 = 2;
            } else if (string.equals("PAUSED")) {
                i3 = 3;
            } else {
                i3 = string.equals("BUFFERING") ? 4 : 0;
            }
            if (i3 != this.yy) {
                this.yy = i3;
                i2 |= 2;
            }
            if (i3 == 1 && jSONObject.has("idleReason")) {
                String string2 = jSONObject.getString("idleReason");
                if (!string2.equals("CANCELLED")) {
                    if (string2.equals("INTERRUPTED")) {
                        i4 = 3;
                    } else if (string2.equals("FINISHED")) {
                        i4 = 1;
                    } else {
                        i4 = string2.equals(RewardedVideoActivity.REQUEST_STATUS_PARAMETER_ERROR) ? 4 : 0;
                    }
                }
                if (i4 != this.yz) {
                    this.yz = i4;
                    i2 |= 2;
                }
            }
        }
        if (jSONObject.has("playbackRate")) {
            double d = jSONObject.getDouble("playbackRate");
            if (this.yx != d) {
                this.yx = d;
                i2 |= 2;
            }
        }
        if (jSONObject.has(TJAdUnitConstants.String.VIDEO_CURRENT_TIME) && (i & 2) == 0) {
            long jB = eo.b(jSONObject.getDouble(TJAdUnitConstants.String.VIDEO_CURRENT_TIME));
            if (jB != this.yA) {
                this.yA = jB;
                i2 |= 2;
            }
        }
        if (jSONObject.has("supportedMediaCommands")) {
            long j2 = jSONObject.getLong("supportedMediaCommands");
            if (j2 != this.yB) {
                this.yB = j2;
                i2 |= 2;
            }
        }
        if (jSONObject.has("volume") && (i & 1) == 0) {
            JSONObject jSONObject2 = jSONObject.getJSONObject("volume");
            double d2 = jSONObject2.getDouble(LevelConstants.TAG_LEVEL);
            if (d2 != this.yC) {
                this.yC = d2;
                i2 |= 2;
            }
            boolean z = jSONObject2.getBoolean("muted");
            if (z != this.yD) {
                this.yD = z;
                i2 |= 2;
            }
        }
        if (jSONObject.has("customData")) {
            this.yn = jSONObject.getJSONObject("customData");
            i2 |= 2;
        }
        if (!jSONObject.has("media")) {
            return i2;
        }
        JSONObject jSONObject3 = jSONObject.getJSONObject("media");
        this.yo = new MediaInfo(jSONObject3);
        int i5 = i2 | 2;
        return jSONObject3.has("metadata") ? i5 | 4 : i5;
    }

    public long dC() {
        return this.yw;
    }

    public JSONObject getCustomData() {
        return this.yn;
    }

    public int getIdleReason() {
        return this.yz;
    }

    public MediaInfo getMediaInfo() {
        return this.yo;
    }

    public double getPlaybackRate() {
        return this.yx;
    }

    public int getPlayerState() {
        return this.yy;
    }

    public long getStreamPosition() {
        return this.yA;
    }

    public double getStreamVolume() {
        return this.yC;
    }

    public boolean isMediaCommandSupported(long mediaCommand) {
        return (this.yB & mediaCommand) != 0;
    }

    public boolean isMute() {
        return this.yD;
    }
}
