package com.google.android.gms.games;

import android.content.Context;
import android.content.Intent;
import android.os.Looper;
import android.view.View;
import com.google.android.gms.common.Scopes;
import com.google.android.gms.common.api.Api;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.Result;
import com.google.android.gms.common.api.Scope;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.common.api.a;
import com.google.android.gms.games.achievement.Achievements;
import com.google.android.gms.games.internal.GamesClientImpl;
import com.google.android.gms.games.internal.api.AchievementsImpl;
import com.google.android.gms.games.internal.api.AclsImpl;
import com.google.android.gms.games.internal.api.GamesMetadataImpl;
import com.google.android.gms.games.internal.api.InvitationsImpl;
import com.google.android.gms.games.internal.api.LeaderboardsImpl;
import com.google.android.gms.games.internal.api.MultiplayerImpl;
import com.google.android.gms.games.internal.api.NotificationsImpl;
import com.google.android.gms.games.internal.api.PlayersImpl;
import com.google.android.gms.games.internal.api.RealTimeMultiplayerImpl;
import com.google.android.gms.games.internal.api.RequestsImpl;
import com.google.android.gms.games.internal.api.TurnBasedMultiplayerImpl;
import com.google.android.gms.games.internal.game.Acls;
import com.google.android.gms.games.leaderboard.Leaderboards;
import com.google.android.gms.games.multiplayer.Invitations;
import com.google.android.gms.games.multiplayer.Multiplayer;
import com.google.android.gms.games.multiplayer.realtime.RealTimeMultiplayer;
import com.google.android.gms.games.multiplayer.turnbased.TurnBasedMultiplayer;
import com.google.android.gms.games.request.Requests;
import com.google.android.gms.internal.fc;
import com.google.android.gms.internal.fq;

/* JADX INFO: loaded from: classes.dex */
public final class Games {
    public static final String EXTRA_PLAYER_IDS = "players";
    static final Api.c<GamesClientImpl> wx = new Api.c<>();
    private static final Api.b<GamesClientImpl, GamesOptions> wy = new Api.b<GamesClientImpl, GamesOptions>() { // from class: com.google.android.gms.games.Games.1
        @Override // com.google.android.gms.common.api.Api.b
        public GamesClientImpl a(Context context, Looper looper, fc fcVar, GamesOptions gamesOptions, GoogleApiClient.ConnectionCallbacks connectionCallbacks, GoogleApiClient.OnConnectionFailedListener onConnectionFailedListener) {
            if (gamesOptions == null) {
                gamesOptions = new GamesOptions();
            }
            return new GamesClientImpl(context, looper, fcVar.eG(), fcVar.eC(), connectionCallbacks, onConnectionFailedListener, fcVar.eF(), fcVar.eD(), fcVar.eH(), gamesOptions.HZ, gamesOptions.Ia, gamesOptions.Ib, gamesOptions.Ic, gamesOptions.Id);
        }

        @Override // com.google.android.gms.common.api.Api.b
        public int getPriority() {
            return 1;
        }
    };
    public static final Scope SCOPE_GAMES = new Scope(Scopes.GAMES);
    public static final Api<GamesOptions> API = new Api<>(wy, wx, SCOPE_GAMES);
    public static final Scope HV = new Scope("https://www.googleapis.com/auth/games.firstparty");
    public static final Api<GamesOptions> HW = new Api<>(wy, wx, HV);
    public static final GamesMetadata GamesMetadata = new GamesMetadataImpl();
    public static final Achievements Achievements = new AchievementsImpl();
    public static final Leaderboards Leaderboards = new LeaderboardsImpl();
    public static final Invitations Invitations = new InvitationsImpl();
    public static final TurnBasedMultiplayer TurnBasedMultiplayer = new TurnBasedMultiplayerImpl();
    public static final RealTimeMultiplayer RealTimeMultiplayer = new RealTimeMultiplayerImpl();
    public static final Multiplayer HX = new MultiplayerImpl();
    public static final Players Players = new PlayersImpl();
    public static final Notifications Notifications = new NotificationsImpl();
    public static final Requests Requests = new RequestsImpl();
    public static final Acls HY = new AclsImpl();

    public static abstract class BaseGamesApiMethodImpl<R extends Result> extends a.b<R, GamesClientImpl> {
        public BaseGamesApiMethodImpl() {
            super(Games.wx);
        }
    }

    public static final class GamesOptions implements Api.ApiOptions.Optional {
        final boolean HZ;
        final boolean Ia;
        final int Ib;
        final boolean Ic;
        final int Id;

        public static final class Builder {
            boolean HZ;
            boolean Ia;
            int Ib;
            boolean Ic;
            int Id;

            private Builder() {
                this.HZ = false;
                this.Ia = true;
                this.Ib = 17;
                this.Ic = false;
                this.Id = 4368;
            }

            public GamesOptions build() {
                return new GamesOptions(this);
            }

            public Builder setSdkVariant(int variant) {
                this.Id = variant;
                return this;
            }

            public Builder setShowConnectingPopup(boolean showConnectingPopup) {
                this.Ia = showConnectingPopup;
                this.Ib = 17;
                return this;
            }

            public Builder setShowConnectingPopup(boolean showConnectingPopup, int gravity) {
                this.Ia = showConnectingPopup;
                this.Ib = gravity;
                return this;
            }
        }

        private GamesOptions() {
            this.HZ = false;
            this.Ia = true;
            this.Ib = 17;
            this.Ic = false;
            this.Id = 4368;
        }

        private GamesOptions(Builder builder) {
            this.HZ = builder.HZ;
            this.Ia = builder.Ia;
            this.Ib = builder.Ib;
            this.Ic = builder.Ic;
            this.Id = builder.Id;
        }

        public static Builder builder() {
            return new Builder();
        }
    }

    private static abstract class SignOutImpl extends BaseGamesApiMethodImpl<Status> {
        private SignOutImpl() {
        }

        @Override // com.google.android.gms.common.api.a.AbstractC0032a
        /* JADX INFO: renamed from: f, reason: merged with bridge method [inline-methods] */
        public Status d(Status status) {
            return status;
        }
    }

    private Games() {
    }

    public static GamesClientImpl c(GoogleApiClient googleApiClient) {
        fq.b(googleApiClient != null, "GoogleApiClient parameter is required.");
        fq.a(googleApiClient.isConnected(), "GoogleApiClient must be connected.");
        GamesClientImpl gamesClientImpl = (GamesClientImpl) googleApiClient.a(wx);
        fq.a(gamesClientImpl != null, "GoogleApiClient is not configured to use the Games Api. Pass Games.API into GoogleApiClient.Builder#addApi() to use this feature.");
        return gamesClientImpl;
    }

    public static String getAppId(GoogleApiClient apiClient) {
        return c(apiClient).gz();
    }

    public static String getCurrentAccountName(GoogleApiClient apiClient) {
        return c(apiClient).gl();
    }

    public static int getSdkVariant(GoogleApiClient apiClient) {
        return c(apiClient).gy();
    }

    public static Intent getSettingsIntent(GoogleApiClient apiClient) {
        return c(apiClient).gx();
    }

    public static void setGravityForPopups(GoogleApiClient apiClient, int gravity) {
        c(apiClient).aX(gravity);
    }

    public static void setViewForPopups(GoogleApiClient apiClient, View gamesContentView) {
        fq.f(gamesContentView);
        c(apiClient).f(gamesContentView);
    }

    public static PendingResult<Status> signOut(GoogleApiClient apiClient) {
        return apiClient.b(new SignOutImpl() { // from class: com.google.android.gms.games.Games.2
            /* JADX INFO: Access modifiers changed from: protected */
            @Override // com.google.android.gms.common.api.a.b
            public void a(GamesClientImpl gamesClientImpl) {
                gamesClientImpl.b(this);
            }
        });
    }
}
