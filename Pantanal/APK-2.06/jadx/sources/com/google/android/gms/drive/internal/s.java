package com.google.android.gms.drive.internal;

import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.os.RemoteException;
import android.util.Log;
import android.util.Pair;
import com.google.android.gms.drive.events.DriveEvent;
import com.google.android.gms.internal.fq;

/* JADX INFO: loaded from: classes.dex */
public class s<C extends DriveEvent> extends w.a {
    private final int ES;
    private final a<C> FA;
    private final DriveEvent.Listener<C> Fz;

    private static class a<E extends DriveEvent> extends Handler {
        private a(Looper looper) {
            super(looper);
        }

        public void a(DriveEvent.Listener<E> listener, E e) {
            sendMessage(obtainMessage(1, new Pair(listener, e)));
        }

        /* JADX WARN: Multi-variable type inference failed */
        @Override // android.os.Handler
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 1:
                    Pair pair = (Pair) msg.obj;
                    ((DriveEvent.Listener) pair.first).onEvent((DriveEvent) pair.second);
                    break;
                default:
                    Log.wtf("EventCallback", "Don't know how to handle this event");
                    break;
            }
        }
    }

    public s(Looper looper, int i, DriveEvent.Listener<C> listener) {
        this.ES = i;
        this.Fz = listener;
        this.FA = new a<>(looper);
    }

    @Override // com.google.android.gms.drive.internal.w
    public void a(OnEventResponse onEventResponse) throws RemoteException {
        fq.x(this.ES == onEventResponse.getEventType());
        switch (onEventResponse.getEventType()) {
            case 1:
                this.FA.a(this.Fz, onEventResponse.fL());
                break;
            case 2:
                this.FA.a(this.Fz, onEventResponse.fM());
                break;
            default:
                Log.w("EventCallback", "Unexpected event type:" + onEventResponse.getEventType());
                break;
        }
    }
}
