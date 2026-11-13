package com.heyzap.wrapper;

import java.util.concurrent.atomic.AtomicReference;

/* JADX INFO: loaded from: classes.dex */
public class FetchLock {
    public static final int LOCK_TIMEOUT = 60000;
    public static AtomicReference<LockType> lock = new AtomicReference<>();
    public static long lockExpiresAt = 0;

    public enum LockType {
        INTERSTITIAL,
        BANNER
    }

    public static synchronized boolean attemptLock(LockType lockType) {
        boolean lockSuccess;
        long now = System.currentTimeMillis();
        if (lockExpiresAt < now) {
            lock.set(null);
        }
        lockSuccess = lock.compareAndSet(null, lockType);
        if (lockSuccess) {
            lockExpiresAt = 60000 + now;
        }
        return lockSuccess;
    }

    public static boolean attemptUnlock(LockType lockType) {
        return lock.compareAndSet(lockType, null);
    }

    public static boolean isLocked(LockType lockType) {
        return lock.get() == lockType;
    }

    public static LockType getActiveLock() {
        return lock.get();
    }
}
