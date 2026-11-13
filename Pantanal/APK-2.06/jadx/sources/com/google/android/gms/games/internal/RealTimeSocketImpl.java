package com.google.android.gms.games.internal;

import android.net.LocalSocket;
import android.os.Parcel;
import android.os.ParcelFileDescriptor;
import com.google.android.gms.games.multiplayer.realtime.RealTimeSocket;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/* JADX INFO: loaded from: classes.dex */
final class RealTimeSocketImpl implements RealTimeSocket {
    private ParcelFileDescriptor Cj;
    private final LocalSocket JP;
    private final String Jg;

    RealTimeSocketImpl(LocalSocket localSocket, String participantId) {
        this.JP = localSocket;
        this.Jg = participantId;
    }

    @Override // com.google.android.gms.games.multiplayer.realtime.RealTimeSocket
    public void close() throws IOException {
        this.JP.close();
    }

    @Override // com.google.android.gms.games.multiplayer.realtime.RealTimeSocket
    public InputStream getInputStream() throws IOException {
        return this.JP.getInputStream();
    }

    @Override // com.google.android.gms.games.multiplayer.realtime.RealTimeSocket
    public OutputStream getOutputStream() throws IOException {
        return this.JP.getOutputStream();
    }

    @Override // com.google.android.gms.games.multiplayer.realtime.RealTimeSocket
    public ParcelFileDescriptor getParcelFileDescriptor() throws IOException {
        if (this.Cj == null && !isClosed()) {
            Parcel parcelObtain = Parcel.obtain();
            parcelObtain.writeFileDescriptor(this.JP.getFileDescriptor());
            parcelObtain.setDataPosition(0);
            this.Cj = parcelObtain.readFileDescriptor();
        }
        return this.Cj;
    }

    @Override // com.google.android.gms.games.multiplayer.realtime.RealTimeSocket
    public boolean isClosed() {
        return (this.JP.isConnected() || this.JP.isBound()) ? false : true;
    }
}
