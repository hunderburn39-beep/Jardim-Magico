package com.google.android.gms.games.internal.api;

import android.os.Bundle;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.common.api.a;
import com.google.android.gms.games.Games;
import com.google.android.gms.games.Notifications;
import com.google.android.gms.games.internal.GamesClientImpl;

/* JADX INFO: loaded from: classes.dex */
public final class NotificationsImpl implements Notifications {

    /* JADX INFO: renamed from: com.google.android.gms.games.internal.api.NotificationsImpl$1, reason: invalid class name */
    class AnonymousClass1 extends Games.BaseGamesApiMethodImpl<Notifications.GameMuteStatusChangeResult> {
        final /* synthetic */ String KB;

        @Override // com.google.android.gms.common.api.a.AbstractC0032a
        /* JADX INFO: renamed from: H, reason: merged with bridge method [inline-methods] */
        public Notifications.GameMuteStatusChangeResult d(final Status status) {
            return new Notifications.GameMuteStatusChangeResult() { // from class: com.google.android.gms.games.internal.api.NotificationsImpl.1.1
                @Override // com.google.android.gms.common.api.Result
                public Status getStatus() {
                    return status;
                }
            };
        }

        /* JADX INFO: Access modifiers changed from: protected */
        @Override // com.google.android.gms.common.api.a.b
        public void a(GamesClientImpl gamesClientImpl) {
            gamesClientImpl.c((a.d<Notifications.GameMuteStatusChangeResult>) this, this.KB, true);
        }
    }

    /* JADX INFO: renamed from: com.google.android.gms.games.internal.api.NotificationsImpl$2, reason: invalid class name */
    class AnonymousClass2 extends Games.BaseGamesApiMethodImpl<Notifications.GameMuteStatusChangeResult> {
        final /* synthetic */ String KB;

        @Override // com.google.android.gms.common.api.a.AbstractC0032a
        /* JADX INFO: renamed from: H, reason: merged with bridge method [inline-methods] */
        public Notifications.GameMuteStatusChangeResult d(final Status status) {
            return new Notifications.GameMuteStatusChangeResult() { // from class: com.google.android.gms.games.internal.api.NotificationsImpl.2.1
                @Override // com.google.android.gms.common.api.Result
                public Status getStatus() {
                    return status;
                }
            };
        }

        /* JADX INFO: Access modifiers changed from: protected */
        @Override // com.google.android.gms.common.api.a.b
        public void a(GamesClientImpl gamesClientImpl) {
            gamesClientImpl.c((a.d<Notifications.GameMuteStatusChangeResult>) this, this.KB, false);
        }
    }

    /* JADX INFO: renamed from: com.google.android.gms.games.internal.api.NotificationsImpl$3, reason: invalid class name */
    class AnonymousClass3 extends Games.BaseGamesApiMethodImpl<Notifications.GameMuteStatusLoadResult> {
        final /* synthetic */ String KB;

        @Override // com.google.android.gms.common.api.a.AbstractC0032a
        /* JADX INFO: renamed from: I, reason: merged with bridge method [inline-methods] */
        public Notifications.GameMuteStatusLoadResult d(final Status status) {
            return new Notifications.GameMuteStatusLoadResult() { // from class: com.google.android.gms.games.internal.api.NotificationsImpl.3.1
                @Override // com.google.android.gms.common.api.Result
                public Status getStatus() {
                    return status;
                }
            };
        }

        /* JADX INFO: Access modifiers changed from: protected */
        @Override // com.google.android.gms.common.api.a.b
        public void a(GamesClientImpl gamesClientImpl) {
            gamesClientImpl.n(this, this.KB);
        }
    }

    /* JADX INFO: renamed from: com.google.android.gms.games.internal.api.NotificationsImpl$4, reason: invalid class name */
    class AnonymousClass4 extends ContactSettingLoadImpl {
        /* JADX INFO: Access modifiers changed from: protected */
        @Override // com.google.android.gms.common.api.a.b
        public void a(GamesClientImpl gamesClientImpl) {
            gamesClientImpl.j(this);
        }
    }

    /* JADX INFO: renamed from: com.google.android.gms.games.internal.api.NotificationsImpl$5, reason: invalid class name */
    class AnonymousClass5 extends ContactSettingUpdateImpl {
        final /* synthetic */ boolean KF;
        final /* synthetic */ Bundle KG;

        /* JADX INFO: Access modifiers changed from: protected */
        @Override // com.google.android.gms.common.api.a.b
        public void a(GamesClientImpl gamesClientImpl) {
            gamesClientImpl.a(this, this.KF, this.KG);
        }
    }

    private static abstract class ContactSettingLoadImpl extends Games.BaseGamesApiMethodImpl<Notifications.ContactSettingLoadResult> {
        private ContactSettingLoadImpl() {
        }

        @Override // com.google.android.gms.common.api.a.AbstractC0032a
        /* JADX INFO: renamed from: J, reason: merged with bridge method [inline-methods] */
        public Notifications.ContactSettingLoadResult d(final Status status) {
            return new Notifications.ContactSettingLoadResult() { // from class: com.google.android.gms.games.internal.api.NotificationsImpl.ContactSettingLoadImpl.1
                @Override // com.google.android.gms.common.api.Result
                public Status getStatus() {
                    return status;
                }
            };
        }
    }

    private static abstract class ContactSettingUpdateImpl extends Games.BaseGamesApiMethodImpl<Status> {
        private ContactSettingUpdateImpl() {
        }

        @Override // com.google.android.gms.common.api.a.AbstractC0032a
        /* JADX INFO: renamed from: f, reason: merged with bridge method [inline-methods] */
        public Status d(Status status) {
            return status;
        }
    }

    @Override // com.google.android.gms.games.Notifications
    public void clear(GoogleApiClient apiClient, int notificationTypes) {
        Games.c(apiClient).aY(notificationTypes);
    }

    @Override // com.google.android.gms.games.Notifications
    public void clearAll(GoogleApiClient apiClient) {
        clear(apiClient, 7);
    }
}
