package com.google.android.gms.maps.model;

import android.os.RemoteException;
import com.google.android.gms.internal.fq;

/* JADX INFO: loaded from: classes.dex */
public final class IndoorLevel {
    private final com.google.android.gms.maps.model.internal.e SZ;

    public IndoorLevel(com.google.android.gms.maps.model.internal.e delegate) {
        this.SZ = (com.google.android.gms.maps.model.internal.e) fq.f(delegate);
    }

    public void activate() {
        try {
            this.SZ.activate();
        } catch (RemoteException e) {
            throw new RuntimeRemoteException(e);
        }
    }

    public boolean equals(Object other) {
        if (!(other instanceof IndoorLevel)) {
            return false;
        }
        try {
            return this.SZ.a(((IndoorLevel) other).SZ);
        } catch (RemoteException e) {
            throw new RuntimeRemoteException(e);
        }
    }

    public String getName() {
        try {
            return this.SZ.getName();
        } catch (RemoteException e) {
            throw new RuntimeRemoteException(e);
        }
    }

    public String getShortName() {
        try {
            return this.SZ.getShortName();
        } catch (RemoteException e) {
            throw new RuntimeRemoteException(e);
        }
    }

    public int hashCode() {
        try {
            return this.SZ.hashCodeRemote();
        } catch (RemoteException e) {
            throw new RuntimeRemoteException(e);
        }
    }
}
