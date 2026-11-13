package com.tapjoy.internal;

import android.app.Notification;
import android.app.PendingIntent;
import android.content.Context;
import android.graphics.Bitmap;
import android.os.Build;
import android.widget.RemoteViews;
import java.util.ArrayList;

/* JADX INFO: loaded from: classes.dex */
public final class a {
    private static final f a;

    /* JADX INFO: renamed from: com.tapjoy.internal.a$a, reason: collision with other inner class name */
    public static class C0145a {
        public int a;
        public CharSequence b;
        public PendingIntent c;
    }

    public static class b extends l {
        Bitmap a;
        Bitmap b;
        boolean c;
    }

    public static class c extends l {
        public CharSequence a;
    }

    public static class e extends l {
        ArrayList a = new ArrayList();
    }

    public interface f {
        Notification a(d dVar);
    }

    public static abstract class l {
        d d;
        public CharSequence e;
        CharSequence f;
        boolean g = false;
    }

    static class g implements f {
        g() {
        }

        @Override // com.tapjoy.internal.a.f
        public Notification a(d dVar) {
            Notification notification = dVar.r;
            notification.setLatestEventInfo(dVar.a, dVar.b, dVar.c, dVar.d);
            if (dVar.j > 0) {
                notification.flags |= 128;
            }
            return notification;
        }
    }

    static class h extends g {
        h() {
        }

        @Override // com.tapjoy.internal.a.g, com.tapjoy.internal.a.f
        public final Notification a(d dVar) {
            Notification notification = dVar.r;
            notification.setLatestEventInfo(dVar.a, dVar.b, dVar.c, dVar.d);
            Context context = dVar.a;
            CharSequence charSequence = dVar.b;
            CharSequence charSequence2 = dVar.c;
            PendingIntent pendingIntent = dVar.d;
            PendingIntent pendingIntent2 = dVar.e;
            notification.setLatestEventInfo(context, charSequence, charSequence2, pendingIntent);
            notification.fullScreenIntent = pendingIntent2;
            if (dVar.j > 0) {
                notification.flags |= 128;
            }
            return notification;
        }
    }

    static class i implements f {
        i() {
        }

        @Override // com.tapjoy.internal.a.f
        public final Notification a(d dVar) {
            Context context = dVar.a;
            Notification notification = dVar.r;
            CharSequence charSequence = dVar.b;
            CharSequence charSequence2 = dVar.c;
            CharSequence charSequence3 = dVar.h;
            RemoteViews remoteViews = dVar.f;
            int i = dVar.i;
            PendingIntent pendingIntent = dVar.d;
            return new Notification.Builder(context).setWhen(notification.when).setSmallIcon(notification.icon, notification.iconLevel).setContent(notification.contentView).setTicker(notification.tickerText, remoteViews).setSound(notification.sound, notification.audioStreamType).setVibrate(notification.vibrate).setLights(notification.ledARGB, notification.ledOnMS, notification.ledOffMS).setOngoing((notification.flags & 2) != 0).setOnlyAlertOnce((notification.flags & 8) != 0).setAutoCancel((notification.flags & 16) != 0).setDefaults(notification.defaults).setContentTitle(charSequence).setContentText(charSequence2).setContentInfo(charSequence3).setContentIntent(pendingIntent).setDeleteIntent(notification.deleteIntent).setFullScreenIntent(dVar.e, (notification.flags & 128) != 0).setLargeIcon(dVar.g).setNumber(i).getNotification();
        }
    }

    static class j implements f {
        j() {
        }

        @Override // com.tapjoy.internal.a.f
        public final Notification a(d dVar) {
            Context context = dVar.a;
            Notification notification = dVar.r;
            CharSequence charSequence = dVar.b;
            CharSequence charSequence2 = dVar.c;
            CharSequence charSequence3 = dVar.h;
            RemoteViews remoteViews = dVar.f;
            int i = dVar.i;
            PendingIntent pendingIntent = dVar.d;
            PendingIntent pendingIntent2 = dVar.e;
            return new Notification.Builder(context).setWhen(notification.when).setSmallIcon(notification.icon, notification.iconLevel).setContent(notification.contentView).setTicker(notification.tickerText, remoteViews).setSound(notification.sound, notification.audioStreamType).setVibrate(notification.vibrate).setLights(notification.ledARGB, notification.ledOnMS, notification.ledOffMS).setOngoing((notification.flags & 2) != 0).setOnlyAlertOnce((notification.flags & 8) != 0).setAutoCancel((notification.flags & 16) != 0).setDefaults(notification.defaults).setContentTitle(charSequence).setContentText(charSequence2).setContentInfo(charSequence3).setContentIntent(pendingIntent).setDeleteIntent(notification.deleteIntent).setFullScreenIntent(pendingIntent2, (notification.flags & 128) != 0).setLargeIcon(dVar.g).setNumber(i).setProgress(dVar.n, dVar.o, dVar.p).getNotification();
        }
    }

    static class k implements f {
        k() {
        }

        @Override // com.tapjoy.internal.a.f
        public final Notification a(d dVar) {
            com.tapjoy.internal.b bVar = new com.tapjoy.internal.b(dVar.a, dVar.r, dVar.b, dVar.c, dVar.h, dVar.f, dVar.i, dVar.d, dVar.e, dVar.g, dVar.n, dVar.o, dVar.p, dVar.k, dVar.j, dVar.m);
            for (C0145a c0145a : dVar.q) {
                bVar.a.addAction(c0145a.a, c0145a.b, c0145a.c);
            }
            if (dVar.l != null) {
                if (dVar.l instanceof c) {
                    c cVar = (c) dVar.l;
                    CharSequence charSequence = cVar.e;
                    boolean z = cVar.g;
                    CharSequence charSequence2 = cVar.f;
                    Notification.BigTextStyle bigTextStyleBigText = new Notification.BigTextStyle(bVar.a).setBigContentTitle(charSequence).bigText(cVar.a);
                    if (z) {
                        bigTextStyleBigText.setSummaryText(charSequence2);
                    }
                } else if (dVar.l instanceof e) {
                    e eVar = (e) dVar.l;
                    bVar.a(eVar.e, eVar.g, eVar.f, eVar.a);
                } else if (dVar.l instanceof b) {
                    b bVar2 = (b) dVar.l;
                    CharSequence charSequence3 = bVar2.e;
                    boolean z2 = bVar2.g;
                    CharSequence charSequence4 = bVar2.f;
                    Bitmap bitmap = bVar2.a;
                    Bitmap bitmap2 = bVar2.b;
                    boolean z3 = bVar2.c;
                    Notification.BigPictureStyle bigPictureStyleBigPicture = new Notification.BigPictureStyle(bVar.a).setBigContentTitle(charSequence3).bigPicture(bitmap);
                    if (z3) {
                        bigPictureStyleBigPicture.bigLargeIcon(bitmap2);
                    }
                    if (z2) {
                        bigPictureStyleBigPicture.setSummaryText(charSequence4);
                    }
                }
            }
            return bVar.a.build();
        }
    }

    static {
        if (Build.VERSION.SDK_INT >= 16) {
            a = new k();
            return;
        }
        if (Build.VERSION.SDK_INT >= 14) {
            a = new j();
            return;
        }
        if (Build.VERSION.SDK_INT >= 11) {
            a = new i();
        } else if (Build.VERSION.SDK_INT >= 9) {
            a = new h();
        } else {
            a = new g();
        }
    }

    public static class d {
        Context a;
        public CharSequence b;
        public CharSequence c;
        public PendingIntent d;
        PendingIntent e;
        RemoteViews f;
        public Bitmap g;
        CharSequence h;
        int i;
        int j;
        boolean k;
        l l;
        CharSequence m;
        int n;
        int o;
        boolean p;
        ArrayList q = new ArrayList();
        public Notification r = new Notification();

        public d(Context context) {
            this.a = context;
            this.r.when = System.currentTimeMillis();
            this.r.audioStreamType = -1;
            this.j = 0;
        }

        public final d a(l lVar) {
            if (this.l != lVar) {
                this.l = lVar;
                if (this.l != null) {
                    l lVar2 = this.l;
                    if (lVar2.d != this) {
                        lVar2.d = this;
                        if (lVar2.d != null) {
                            lVar2.d.a(lVar2);
                        }
                    }
                }
            }
            return this;
        }
    }
}
