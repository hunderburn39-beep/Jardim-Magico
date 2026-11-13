package com.google.android.gms.games.internal;

import android.os.ParcelFileDescriptor;
import com.google.android.gms.games.multiplayer.realtime.RealTimeSocket;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/* JADX INFO: loaded from: classes.dex */
public final class LibjingleNativeSocket implements RealTimeSocket {
    private static final String TAG = LibjingleNativeSocket.class.getSimpleName();
    private final ParcelFileDescriptor Cj;
    private final InputStream JI;
    private final OutputStream JJ;

    LibjingleNativeSocket(ParcelFileDescriptor parcelFileDescriptor) {
        this.Cj = parcelFileDescriptor;
        this.JI = new ParcelFileDescriptor.AutoCloseInputStream(parcelFileDescriptor);
        this.JJ = new ParcelFileDescriptor.AutoCloseOutputStream(parcelFileDescriptor);
    }

    @Override // com.google.android.gms.games.multiplayer.realtime.RealTimeSocket
    public void close() throws IOException {
        this.Cj.close();
    }

    @Override // com.google.android.gms.games.multiplayer.realtime.RealTimeSocket
    public InputStream getInputStream() throws IOException {
        return this.JI;
    }

    @Override // com.google.android.gms.games.multiplayer.realtime.RealTimeSocket
    public OutputStream getOutputStream() throws IOException {
        return this.JJ;
    }

    @Override // com.google.android.gms.games.multiplayer.realtime.RealTimeSocket
    public ParcelFileDescriptor getParcelFileDescriptor() throws IOException {
        return this.Cj;
    }

    @Override // com.google.android.gms.games.multiplayer.realtime.RealTimeSocket
    public boolean isClosed() {
        try {
            this.JI.available();
            return false;
        } catch (IOException e) {
            return true;
        }
    }
}
