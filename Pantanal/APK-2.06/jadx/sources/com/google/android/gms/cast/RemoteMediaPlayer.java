package com.google.android.gms.cast;

import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.Result;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.internal.en;
import com.google.android.gms.internal.es;
import com.google.android.gms.internal.et;
import com.google.android.gms.internal.eu;
import java.io.IOException;
import org.json.JSONObject;

/* JADX INFO: loaded from: classes.dex */
public class RemoteMediaPlayer implements Cast.MessageReceivedCallback {
    public static final int RESUME_STATE_PAUSE = 2;
    public static final int RESUME_STATE_PLAY = 1;
    public static final int RESUME_STATE_UNCHANGED = 0;
    public static final int STATUS_CANCELED = 2;
    public static final int STATUS_FAILED = 1;
    public static final int STATUS_REPLACED = 4;
    public static final int STATUS_SUCCEEDED = 0;
    public static final int STATUS_TIMED_OUT = 3;
    private OnMetadataUpdatedListener yG;
    private OnStatusUpdatedListener yH;
    private final Object li = new Object();
    private final a yF = new a();
    private final es yE = new es() { // from class: com.google.android.gms.cast.RemoteMediaPlayer.1
        @Override // com.google.android.gms.internal.es
        protected void onMetadataUpdated() {
            RemoteMediaPlayer.this.onMetadataUpdated();
        }

        @Override // com.google.android.gms.internal.es
        protected void onStatusUpdated() {
            RemoteMediaPlayer.this.onStatusUpdated();
        }
    };

    public interface MediaChannelResult extends Result {
    }

    public interface OnMetadataUpdatedListener {
        void onMetadataUpdated();
    }

    public interface OnStatusUpdatedListener {
        void onStatusUpdated();
    }

    private class a implements et {
        private GoogleApiClient yS;
        private long yT = 0;

        /* JADX INFO: renamed from: com.google.android.gms.cast.RemoteMediaPlayer$a$a, reason: collision with other inner class name */
        private final class C0031a implements ResultCallback<Status> {
            private final long yU;

            C0031a(long j) {
                this.yU = j;
            }

            @Override // com.google.android.gms.common.api.ResultCallback
            /* JADX INFO: renamed from: i, reason: merged with bridge method [inline-methods] */
            public void onResult(Status status) {
                if (status.isSuccess()) {
                    return;
                }
                RemoteMediaPlayer.this.yE.a(this.yU, status.getStatusCode());
            }
        }

        public a() {
        }

        @Override // com.google.android.gms.internal.et
        public void a(String str, String str2, long j, String str3) throws IOException {
            if (this.yS == null) {
                throw new IOException("No GoogleApiClient available");
            }
            Cast.CastApi.sendMessage(this.yS, str, str2).setResultCallback(new C0031a(j));
        }

        public void b(GoogleApiClient googleApiClient) {
            this.yS = googleApiClient;
        }

        @Override // com.google.android.gms.internal.et
        public long dD() {
            long j = this.yT + 1;
            this.yT = j;
            return j;
        }
    }

    private static abstract class b extends Cast.a<MediaChannelResult> {
        eu yW = new eu() { // from class: com.google.android.gms.cast.RemoteMediaPlayer.b.1
            @Override // com.google.android.gms.internal.eu
            public void a(long j, int i, JSONObject jSONObject) {
                b.this.b(new c(new Status(i), jSONObject));
            }

            @Override // com.google.android.gms.internal.eu
            public void l(long j) {
                b.this.b(b.this.d(new Status(4)));
            }
        };

        b() {
        }

        @Override // com.google.android.gms.common.api.a.AbstractC0032a
        /* JADX INFO: renamed from: j, reason: merged with bridge method [inline-methods] */
        public MediaChannelResult d(final Status status) {
            return new MediaChannelResult() { // from class: com.google.android.gms.cast.RemoteMediaPlayer.b.2
                @Override // com.google.android.gms.common.api.Result
                public Status getStatus() {
                    return status;
                }
            };
        }
    }

    private static final class c implements MediaChannelResult {
        private final Status wJ;
        private final JSONObject yn;

        c(Status status, JSONObject jSONObject) {
            this.wJ = status;
            this.yn = jSONObject;
        }

        @Override // com.google.android.gms.common.api.Result
        public Status getStatus() {
            return this.wJ;
        }
    }

    public RemoteMediaPlayer() {
        this.yE.a(this.yF);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void onMetadataUpdated() {
        if (this.yG != null) {
            this.yG.onMetadataUpdated();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void onStatusUpdated() {
        if (this.yH != null) {
            this.yH.onStatusUpdated();
        }
    }

    public long getApproximateStreamPosition() {
        long approximateStreamPosition;
        synchronized (this.li) {
            approximateStreamPosition = this.yE.getApproximateStreamPosition();
        }
        return approximateStreamPosition;
    }

    public MediaInfo getMediaInfo() {
        MediaInfo mediaInfo;
        synchronized (this.li) {
            mediaInfo = this.yE.getMediaInfo();
        }
        return mediaInfo;
    }

    public MediaStatus getMediaStatus() {
        MediaStatus mediaStatus;
        synchronized (this.li) {
            mediaStatus = this.yE.getMediaStatus();
        }
        return mediaStatus;
    }

    public String getNamespace() {
        return this.yE.getNamespace();
    }

    public long getStreamDuration() {
        long streamDuration;
        synchronized (this.li) {
            streamDuration = this.yE.getStreamDuration();
        }
        return streamDuration;
    }

    public PendingResult<MediaChannelResult> load(GoogleApiClient apiClient, MediaInfo mediaInfo) {
        return load(apiClient, mediaInfo, true, 0L, null);
    }

    public PendingResult<MediaChannelResult> load(GoogleApiClient apiClient, MediaInfo mediaInfo, boolean autoplay) {
        return load(apiClient, mediaInfo, autoplay, 0L, null);
    }

    public PendingResult<MediaChannelResult> load(GoogleApiClient apiClient, MediaInfo mediaInfo, boolean autoplay, long playPosition) {
        return load(apiClient, mediaInfo, autoplay, playPosition, null);
    }

    public PendingResult<MediaChannelResult> load(final GoogleApiClient apiClient, final MediaInfo mediaInfo, final boolean autoplay, final long playPosition, final JSONObject customData) {
        return apiClient.b(new b() { // from class: com.google.android.gms.cast.RemoteMediaPlayer.2
            /* JADX INFO: Access modifiers changed from: protected */
            @Override // com.google.android.gms.common.api.a.b
            public void a(en enVar) {
                synchronized (RemoteMediaPlayer.this.li) {
                    RemoteMediaPlayer.this.yF.b(apiClient);
                    try {
                        try {
                            RemoteMediaPlayer.this.yE.a(this.yW, mediaInfo, autoplay, playPosition, customData);
                            RemoteMediaPlayer.this.yF.b(null);
                        } catch (IOException e) {
                            b(d(new Status(1)));
                            RemoteMediaPlayer.this.yF.b(null);
                        }
                    } catch (Throwable th) {
                        RemoteMediaPlayer.this.yF.b(null);
                        throw th;
                    }
                }
            }
        });
    }

    @Override // com.google.android.gms.cast.Cast.MessageReceivedCallback
    public void onMessageReceived(CastDevice castDevice, String namespace, String message) {
        this.yE.U(message);
    }

    public PendingResult<MediaChannelResult> pause(GoogleApiClient apiClient) {
        return pause(apiClient, null);
    }

    public PendingResult<MediaChannelResult> pause(final GoogleApiClient apiClient, final JSONObject customData) {
        return apiClient.b(new b() { // from class: com.google.android.gms.cast.RemoteMediaPlayer.3
            /* JADX INFO: Access modifiers changed from: protected */
            @Override // com.google.android.gms.common.api.a.b
            public void a(en enVar) {
                synchronized (RemoteMediaPlayer.this.li) {
                    RemoteMediaPlayer.this.yF.b(apiClient);
                    try {
                        try {
                            RemoteMediaPlayer.this.yE.a(this.yW, customData);
                            RemoteMediaPlayer.this.yF.b(null);
                        } catch (Throwable th) {
                            RemoteMediaPlayer.this.yF.b(null);
                            throw th;
                        }
                    } catch (IOException e) {
                        b(d(new Status(1)));
                        RemoteMediaPlayer.this.yF.b(null);
                    }
                }
            }
        });
    }

    public PendingResult<MediaChannelResult> play(GoogleApiClient apiClient) {
        return play(apiClient, null);
    }

    public PendingResult<MediaChannelResult> play(final GoogleApiClient apiClient, final JSONObject customData) {
        return apiClient.b(new b() { // from class: com.google.android.gms.cast.RemoteMediaPlayer.5
            /* JADX INFO: Access modifiers changed from: protected */
            @Override // com.google.android.gms.common.api.a.b
            public void a(en enVar) {
                synchronized (RemoteMediaPlayer.this.li) {
                    RemoteMediaPlayer.this.yF.b(apiClient);
                    try {
                        try {
                            RemoteMediaPlayer.this.yE.c(this.yW, customData);
                            RemoteMediaPlayer.this.yF.b(null);
                        } catch (Throwable th) {
                            RemoteMediaPlayer.this.yF.b(null);
                            throw th;
                        }
                    } catch (IOException e) {
                        b(d(new Status(1)));
                        RemoteMediaPlayer.this.yF.b(null);
                    }
                }
            }
        });
    }

    public PendingResult<MediaChannelResult> requestStatus(final GoogleApiClient apiClient) {
        return apiClient.b(new b() { // from class: com.google.android.gms.cast.RemoteMediaPlayer.9
            /* JADX INFO: Access modifiers changed from: protected */
            @Override // com.google.android.gms.common.api.a.b
            public void a(en enVar) {
                synchronized (RemoteMediaPlayer.this.li) {
                    RemoteMediaPlayer.this.yF.b(apiClient);
                    try {
                        try {
                            RemoteMediaPlayer.this.yE.a(this.yW);
                            RemoteMediaPlayer.this.yF.b(null);
                        } catch (IOException e) {
                            b(d(new Status(1)));
                            RemoteMediaPlayer.this.yF.b(null);
                        }
                    } catch (Throwable th) {
                        RemoteMediaPlayer.this.yF.b(null);
                        throw th;
                    }
                }
            }
        });
    }

    public PendingResult<MediaChannelResult> seek(GoogleApiClient apiClient, long position) {
        return seek(apiClient, position, 0, null);
    }

    public PendingResult<MediaChannelResult> seek(GoogleApiClient apiClient, long position, int resumeState) {
        return seek(apiClient, position, resumeState, null);
    }

    public PendingResult<MediaChannelResult> seek(final GoogleApiClient apiClient, final long position, final int resumeState, final JSONObject customData) {
        return apiClient.b(new b() { // from class: com.google.android.gms.cast.RemoteMediaPlayer.6
            /* JADX INFO: Access modifiers changed from: protected */
            @Override // com.google.android.gms.common.api.a.b
            public void a(en enVar) {
                synchronized (RemoteMediaPlayer.this.li) {
                    RemoteMediaPlayer.this.yF.b(apiClient);
                    try {
                        try {
                            RemoteMediaPlayer.this.yE.a(this.yW, position, resumeState, customData);
                            RemoteMediaPlayer.this.yF.b(null);
                        } catch (IOException e) {
                            b(d(new Status(1)));
                            RemoteMediaPlayer.this.yF.b(null);
                        }
                    } catch (Throwable th) {
                        RemoteMediaPlayer.this.yF.b(null);
                        throw th;
                    }
                }
            }
        });
    }

    public void setOnMetadataUpdatedListener(OnMetadataUpdatedListener listener) {
        this.yG = listener;
    }

    public void setOnStatusUpdatedListener(OnStatusUpdatedListener listener) {
        this.yH = listener;
    }

    public PendingResult<MediaChannelResult> setStreamMute(GoogleApiClient apiClient, boolean muteState) {
        return setStreamMute(apiClient, muteState, null);
    }

    public PendingResult<MediaChannelResult> setStreamMute(final GoogleApiClient apiClient, final boolean muteState, final JSONObject customData) {
        return apiClient.b(new b() { // from class: com.google.android.gms.cast.RemoteMediaPlayer.8
            /* JADX INFO: Access modifiers changed from: protected */
            @Override // com.google.android.gms.common.api.a.b
            public void a(en enVar) {
                synchronized (RemoteMediaPlayer.this.li) {
                    RemoteMediaPlayer.this.yF.b(apiClient);
                    try {
                        RemoteMediaPlayer.this.yE.a(this.yW, muteState, customData);
                    } catch (IOException e) {
                        b(d(new Status(1)));
                    } catch (IllegalStateException e2) {
                        b(d(new Status(1)));
                    } finally {
                        RemoteMediaPlayer.this.yF.b(null);
                    }
                }
            }
        });
    }

    public PendingResult<MediaChannelResult> setStreamVolume(GoogleApiClient apiClient, double volume) throws IllegalArgumentException {
        return setStreamVolume(apiClient, volume, null);
    }

    public PendingResult<MediaChannelResult> setStreamVolume(final GoogleApiClient apiClient, final double volume, final JSONObject customData) throws IllegalArgumentException {
        if (Double.isInfinite(volume) || Double.isNaN(volume)) {
            throw new IllegalArgumentException("Volume cannot be " + volume);
        }
        return apiClient.b(new b() { // from class: com.google.android.gms.cast.RemoteMediaPlayer.7
            /* JADX INFO: Access modifiers changed from: protected */
            @Override // com.google.android.gms.common.api.a.b
            public void a(en enVar) {
                synchronized (RemoteMediaPlayer.this.li) {
                    try {
                        RemoteMediaPlayer.this.yF.b(apiClient);
                        try {
                            try {
                                try {
                                    RemoteMediaPlayer.this.yE.a(this.yW, volume, customData);
                                    RemoteMediaPlayer.this.yF.b(null);
                                } catch (IOException e) {
                                    b(d(new Status(1)));
                                    RemoteMediaPlayer.this.yF.b(null);
                                }
                            } catch (IllegalArgumentException e2) {
                                b(d(new Status(1)));
                                RemoteMediaPlayer.this.yF.b(null);
                            }
                        } catch (IllegalStateException e3) {
                            b(d(new Status(1)));
                            RemoteMediaPlayer.this.yF.b(null);
                        }
                    } catch (Throwable th) {
                        RemoteMediaPlayer.this.yF.b(null);
                        throw th;
                    }
                }
            }
        });
    }

    public PendingResult<MediaChannelResult> stop(GoogleApiClient apiClient) {
        return stop(apiClient, null);
    }

    public PendingResult<MediaChannelResult> stop(final GoogleApiClient apiClient, final JSONObject customData) {
        return apiClient.b(new b() { // from class: com.google.android.gms.cast.RemoteMediaPlayer.4
            /* JADX INFO: Access modifiers changed from: protected */
            @Override // com.google.android.gms.common.api.a.b
            public void a(en enVar) {
                synchronized (RemoteMediaPlayer.this.li) {
                    RemoteMediaPlayer.this.yF.b(apiClient);
                    try {
                        try {
                            RemoteMediaPlayer.this.yE.b(this.yW, customData);
                            RemoteMediaPlayer.this.yF.b(null);
                        } catch (Throwable th) {
                            RemoteMediaPlayer.this.yF.b(null);
                            throw th;
                        }
                    } catch (IOException e) {
                        b(d(new Status(1)));
                        RemoteMediaPlayer.this.yF.b(null);
                    }
                }
            }
        });
    }
}
