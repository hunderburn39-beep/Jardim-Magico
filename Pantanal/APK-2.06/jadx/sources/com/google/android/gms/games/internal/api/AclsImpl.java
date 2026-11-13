package com.google.android.gms.games.internal.api;

import com.google.android.gms.common.api.Status;
import com.google.android.gms.games.Games;
import com.google.android.gms.games.internal.GamesClientImpl;
import com.google.android.gms.games.internal.game.Acls;

/* JADX INFO: loaded from: classes.dex */
public final class AclsImpl implements Acls {

    /* JADX INFO: renamed from: com.google.android.gms.games.internal.api.AclsImpl$2, reason: invalid class name */
    class AnonymousClass2 extends LoadNotifyAclImpl {
        /* JADX INFO: Access modifiers changed from: protected */
        @Override // com.google.android.gms.common.api.a.b
        public void a(GamesClientImpl gamesClientImpl) {
            gamesClientImpl.i(this);
        }
    }

    /* JADX INFO: renamed from: com.google.android.gms.games.internal.api.AclsImpl$3, reason: invalid class name */
    class AnonymousClass3 extends UpdateNotifyAclImpl {
        final /* synthetic */ String JY;

        /* JADX INFO: Access modifiers changed from: protected */
        @Override // com.google.android.gms.common.api.a.b
        public void a(GamesClientImpl gamesClientImpl) {
            gamesClientImpl.m(this, this.JY);
        }
    }

    private static abstract class LoadNotifyAclImpl extends Games.BaseGamesApiMethodImpl<Acls.LoadAclResult> {
        private LoadNotifyAclImpl() {
        }

        @Override // com.google.android.gms.common.api.a.AbstractC0032a
        /* JADX INFO: renamed from: x, reason: merged with bridge method [inline-methods] */
        public Acls.LoadAclResult d(Status status) {
            return AclsImpl.v(status);
        }
    }

    private static abstract class UpdateNotifyAclImpl extends Games.BaseGamesApiMethodImpl<Status> {
        private UpdateNotifyAclImpl() {
        }

        @Override // com.google.android.gms.common.api.a.AbstractC0032a
        /* JADX INFO: renamed from: f, reason: merged with bridge method [inline-methods] */
        public Status d(Status status) {
            return status;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static Acls.LoadAclResult v(final Status status) {
        return new Acls.LoadAclResult() { // from class: com.google.android.gms.games.internal.api.AclsImpl.1
            @Override // com.google.android.gms.common.api.Result
            public Status getStatus() {
                return status;
            }

            @Override // com.google.android.gms.common.api.Releasable
            public void release() {
            }
        };
    }
}
