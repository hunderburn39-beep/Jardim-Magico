package com.google.android.gms.location;

import android.os.SystemClock;
import com.google.android.gms.internal.hd;

/* JADX INFO: loaded from: classes.dex */
public interface Geofence {
    public static final int GEOFENCE_TRANSITION_DWELL = 4;
    public static final int GEOFENCE_TRANSITION_ENTER = 1;
    public static final int GEOFENCE_TRANSITION_EXIT = 2;
    public static final long NEVER_EXPIRE = -1;

    public static final class Builder {
        private double NX;
        private double NY;
        private float NZ;
        private String Jo = null;
        private int NU = 0;
        private long NV = Long.MIN_VALUE;
        private short NW = -1;
        private int Oa = 0;
        private int Ob = -1;

        public Geofence build() {
            if (this.Jo == null) {
                throw new IllegalArgumentException("Request ID not set.");
            }
            if (this.NU == 0) {
                throw new IllegalArgumentException("Transitions types not set.");
            }
            if ((this.NU & 4) != 0 && this.Ob < 0) {
                throw new IllegalArgumentException("Non-negative loitering delay needs to be set when transition types include GEOFENCE_TRANSITION_DWELLING.");
            }
            if (this.NV == Long.MIN_VALUE) {
                throw new IllegalArgumentException("Expiration not set.");
            }
            if (this.NW == -1) {
                throw new IllegalArgumentException("Geofence region not set.");
            }
            if (this.Oa < 0) {
                throw new IllegalArgumentException("Notification responsiveness should be nonnegative.");
            }
            return new hd(this.Jo, this.NU, (short) 1, this.NX, this.NY, this.NZ, this.NV, this.Oa, this.Ob);
        }

        public Builder setCircularRegion(double latitude, double longitude, float radius) {
            this.NW = (short) 1;
            this.NX = latitude;
            this.NY = longitude;
            this.NZ = radius;
            return this;
        }

        public Builder setExpirationDuration(long durationMillis) {
            if (durationMillis < 0) {
                this.NV = -1L;
            } else {
                this.NV = SystemClock.elapsedRealtime() + durationMillis;
            }
            return this;
        }

        public Builder setLoiteringDelay(int loiteringDelayMs) {
            this.Ob = loiteringDelayMs;
            return this;
        }

        public Builder setNotificationResponsiveness(int notificationResponsivenessMs) {
            this.Oa = notificationResponsivenessMs;
            return this;
        }

        public Builder setRequestId(String requestId) {
            this.Jo = requestId;
            return this;
        }

        public Builder setTransitionTypes(int transitionTypes) {
            this.NU = transitionTypes;
            return this;
        }
    }

    String getRequestId();
}
