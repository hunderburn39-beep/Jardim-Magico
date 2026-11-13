package com.google.android.gms.games.internal;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.LocalSocket;
import android.net.LocalSocketAddress;
import android.os.Binder;
import android.os.Bundle;
import android.os.IBinder;
import android.os.Looper;
import android.os.ParcelFileDescriptor;
import android.os.RemoteException;
import android.view.View;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.common.Scopes;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.Releasable;
import com.google.android.gms.common.api.Result;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.common.api.a;
import com.google.android.gms.common.data.DataHolder;
import com.google.android.gms.games.Game;
import com.google.android.gms.games.GameBuffer;
import com.google.android.gms.games.GameEntity;
import com.google.android.gms.games.GamesMetadata;
import com.google.android.gms.games.Notifications;
import com.google.android.gms.games.Player;
import com.google.android.gms.games.PlayerBuffer;
import com.google.android.gms.games.PlayerEntity;
import com.google.android.gms.games.Players;
import com.google.android.gms.games.achievement.AchievementBuffer;
import com.google.android.gms.games.achievement.Achievements;
import com.google.android.gms.games.internal.constants.RequestType;
import com.google.android.gms.games.internal.game.Acls;
import com.google.android.gms.games.internal.game.ExtendedGameBuffer;
import com.google.android.gms.games.internal.game.GameInstanceBuffer;
import com.google.android.gms.games.internal.player.ExtendedPlayerBuffer;
import com.google.android.gms.games.internal.request.RequestUpdateOutcomes;
import com.google.android.gms.games.leaderboard.Leaderboard;
import com.google.android.gms.games.leaderboard.LeaderboardBuffer;
import com.google.android.gms.games.leaderboard.LeaderboardEntity;
import com.google.android.gms.games.leaderboard.LeaderboardScore;
import com.google.android.gms.games.leaderboard.LeaderboardScoreBuffer;
import com.google.android.gms.games.leaderboard.LeaderboardScoreEntity;
import com.google.android.gms.games.leaderboard.Leaderboards;
import com.google.android.gms.games.leaderboard.ScoreSubmissionData;
import com.google.android.gms.games.multiplayer.Invitation;
import com.google.android.gms.games.multiplayer.InvitationBuffer;
import com.google.android.gms.games.multiplayer.Invitations;
import com.google.android.gms.games.multiplayer.OnInvitationReceivedListener;
import com.google.android.gms.games.multiplayer.ParticipantResult;
import com.google.android.gms.games.multiplayer.ParticipantUtils;
import com.google.android.gms.games.multiplayer.realtime.RealTimeMessage;
import com.google.android.gms.games.multiplayer.realtime.RealTimeMessageReceivedListener;
import com.google.android.gms.games.multiplayer.realtime.RealTimeMultiplayer;
import com.google.android.gms.games.multiplayer.realtime.RealTimeSocket;
import com.google.android.gms.games.multiplayer.realtime.Room;
import com.google.android.gms.games.multiplayer.realtime.RoomBuffer;
import com.google.android.gms.games.multiplayer.realtime.RoomConfig;
import com.google.android.gms.games.multiplayer.realtime.RoomEntity;
import com.google.android.gms.games.multiplayer.realtime.RoomStatusUpdateListener;
import com.google.android.gms.games.multiplayer.realtime.RoomUpdateListener;
import com.google.android.gms.games.multiplayer.turnbased.LoadMatchesResponse;
import com.google.android.gms.games.multiplayer.turnbased.OnTurnBasedMatchUpdateReceivedListener;
import com.google.android.gms.games.multiplayer.turnbased.TurnBasedMatch;
import com.google.android.gms.games.multiplayer.turnbased.TurnBasedMatchBuffer;
import com.google.android.gms.games.multiplayer.turnbased.TurnBasedMatchConfig;
import com.google.android.gms.games.multiplayer.turnbased.TurnBasedMultiplayer;
import com.google.android.gms.games.request.GameRequest;
import com.google.android.gms.games.request.GameRequestBuffer;
import com.google.android.gms.games.request.OnRequestReceivedListener;
import com.google.android.gms.games.request.Requests;
import com.google.android.gms.internal.ff;
import com.google.android.gms.internal.fm;
import com.google.android.gms.internal.fq;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

/* JADX INFO: loaded from: classes.dex */
public final class GamesClientImpl extends ff<IGamesService> implements GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener {
    private boolean IA;
    private int IB;
    private final Binder IC;
    private final long IE;
    private final boolean IF;
    private final int IG;
    private final boolean IH;
    private final String Iu;
    private final Map<String, RealTimeSocket> Iv;
    private PlayerEntity Iw;
    private GameEntity Ix;
    private final PopupManager Iy;
    private boolean Iz;
    private final String wG;

    abstract class AbstractPeerStatusCallback extends AbstractRoomStatusCallback {
        private final ArrayList<String> II;

        AbstractPeerStatusCallback(RoomStatusUpdateListener listener, DataHolder dataHolder, String[] participantIds) {
            super(listener, dataHolder);
            this.II = new ArrayList<>();
            for (String str : participantIds) {
                this.II.add(str);
            }
        }

        @Override // com.google.android.gms.games.internal.GamesClientImpl.AbstractRoomStatusCallback
        protected void a(RoomStatusUpdateListener roomStatusUpdateListener, Room room) {
            a(roomStatusUpdateListener, room, this.II);
        }

        protected abstract void a(RoomStatusUpdateListener roomStatusUpdateListener, Room room, ArrayList<String> arrayList);
    }

    abstract class AbstractRoomCallback extends ff<IGamesService>.d<RoomUpdateListener> {
        AbstractRoomCallback(RoomUpdateListener listener, DataHolder dataHolder) {
            super(listener, dataHolder);
        }

        /* JADX INFO: Access modifiers changed from: protected */
        @Override // com.google.android.gms.internal.ff.d
        public void a(RoomUpdateListener roomUpdateListener, DataHolder dataHolder) {
            a(roomUpdateListener, GamesClientImpl.this.G(dataHolder), dataHolder.getStatusCode());
        }

        protected abstract void a(RoomUpdateListener roomUpdateListener, Room room, int i);
    }

    abstract class AbstractRoomStatusCallback extends ff<IGamesService>.d<RoomStatusUpdateListener> {
        AbstractRoomStatusCallback(RoomStatusUpdateListener listener, DataHolder dataHolder) {
            super(listener, dataHolder);
        }

        /* JADX INFO: Access modifiers changed from: protected */
        @Override // com.google.android.gms.internal.ff.d
        public void a(RoomStatusUpdateListener roomStatusUpdateListener, DataHolder dataHolder) {
            a(roomStatusUpdateListener, GamesClientImpl.this.G(dataHolder));
        }

        protected abstract void a(RoomStatusUpdateListener roomStatusUpdateListener, Room room);
    }

    final class AchievementUpdatedBinderCallback extends AbstractGamesCallbacks {
        private final a.d<Achievements.UpdateAchievementResult> wH;

        AchievementUpdatedBinderCallback(a.d<Achievements.UpdateAchievementResult> resultHolder) {
            this.wH = (a.d) fq.b(resultHolder, "Holder must not be null");
        }

        @Override // com.google.android.gms.games.internal.AbstractGamesCallbacks, com.google.android.gms.games.internal.IGamesCallbacks
        public void e(int i, String str) {
            GamesClientImpl.this.a(GamesClientImpl.this.new AchievementUpdatedCallback(this.wH, i, str));
        }
    }

    final class AchievementUpdatedCallback extends ff<IGamesService>.b<a.d<Achievements.UpdateAchievementResult>> implements Achievements.UpdateAchievementResult {
        private final String IK;
        private final Status wJ;

        AchievementUpdatedCallback(a.d<Achievements.UpdateAchievementResult> resultHolder, int statusCode, String achievementId) {
            super(resultHolder);
            this.wJ = new Status(statusCode);
            this.IK = achievementId;
        }

        /* JADX INFO: Access modifiers changed from: protected */
        @Override // com.google.android.gms.internal.ff.b
        /* JADX INFO: renamed from: c, reason: merged with bridge method [inline-methods] */
        public void a(a.d<Achievements.UpdateAchievementResult> dVar) {
            dVar.b(this);
        }

        @Override // com.google.android.gms.internal.ff.b
        protected void dx() {
        }

        @Override // com.google.android.gms.games.achievement.Achievements.UpdateAchievementResult
        public String getAchievementId() {
            return this.IK;
        }

        @Override // com.google.android.gms.common.api.Result
        public Status getStatus() {
            return this.wJ;
        }
    }

    final class AchievementsLoadedBinderCallback extends AbstractGamesCallbacks {
        private final a.d<Achievements.LoadAchievementsResult> wH;

        AchievementsLoadedBinderCallback(a.d<Achievements.LoadAchievementsResult> resultHolder) {
            this.wH = (a.d) fq.b(resultHolder, "Holder must not be null");
        }

        @Override // com.google.android.gms.games.internal.AbstractGamesCallbacks, com.google.android.gms.games.internal.IGamesCallbacks
        public void b(DataHolder dataHolder) {
            GamesClientImpl.this.a(GamesClientImpl.this.new AchievementsLoadedCallback(this.wH, dataHolder));
        }
    }

    final class AchievementsLoadedCallback extends ResultDataHolderCallback<a.d<Achievements.LoadAchievementsResult>> implements Achievements.LoadAchievementsResult {
        private final AchievementBuffer IL;

        AchievementsLoadedCallback(a.d<Achievements.LoadAchievementsResult> resultHolder, DataHolder dataHolder) {
            super(resultHolder, dataHolder);
            this.IL = new AchievementBuffer(dataHolder);
        }

        /* JADX INFO: Access modifiers changed from: protected */
        @Override // com.google.android.gms.internal.ff.d
        public void a(a.d<Achievements.LoadAchievementsResult> dVar, DataHolder dataHolder) {
            dVar.b(this);
        }

        @Override // com.google.android.gms.games.achievement.Achievements.LoadAchievementsResult
        public AchievementBuffer getAchievements() {
            return this.IL;
        }
    }

    final class ConnectedToRoomCallback extends AbstractRoomStatusCallback {
        ConnectedToRoomCallback(RoomStatusUpdateListener listener, DataHolder dataHolder) {
            super(listener, dataHolder);
        }

        @Override // com.google.android.gms.games.internal.GamesClientImpl.AbstractRoomStatusCallback
        public void a(RoomStatusUpdateListener roomStatusUpdateListener, Room room) {
            roomStatusUpdateListener.onConnectedToRoom(room);
        }
    }

    final class ContactSettingsLoadedBinderCallback extends AbstractGamesCallbacks {
        private final a.d<Notifications.ContactSettingLoadResult> wH;

        ContactSettingsLoadedBinderCallback(a.d<Notifications.ContactSettingLoadResult> holder) {
            this.wH = (a.d) fq.b(holder, "Holder must not be null");
        }

        @Override // com.google.android.gms.games.internal.AbstractGamesCallbacks, com.google.android.gms.games.internal.IGamesCallbacks
        public void B(DataHolder dataHolder) {
            GamesClientImpl.this.a(GamesClientImpl.this.new ContactSettingsLoadedCallback(this.wH, dataHolder));
        }
    }

    final class ContactSettingsLoadedCallback extends ResultDataHolderCallback<a.d<Notifications.ContactSettingLoadResult>> implements Notifications.ContactSettingLoadResult {
        ContactSettingsLoadedCallback(a.d<Notifications.ContactSettingLoadResult> resultHolder, DataHolder dataHolder) {
            super(resultHolder, dataHolder);
        }

        /* JADX INFO: Access modifiers changed from: protected */
        @Override // com.google.android.gms.internal.ff.d
        public void a(a.d<Notifications.ContactSettingLoadResult> dVar, DataHolder dataHolder) {
            dVar.b(this);
        }
    }

    final class ContactSettingsUpdatedBinderCallback extends AbstractGamesCallbacks {
        private final a.d<Status> wH;

        ContactSettingsUpdatedBinderCallback(a.d<Status> holder) {
            this.wH = (a.d) fq.b(holder, "Holder must not be null");
        }

        @Override // com.google.android.gms.games.internal.AbstractGamesCallbacks, com.google.android.gms.games.internal.IGamesCallbacks
        public void aV(int i) {
            GamesClientImpl.this.a(GamesClientImpl.this.new ContactSettingsUpdatedCallback(this.wH, i));
        }
    }

    final class ContactSettingsUpdatedCallback extends ff<IGamesService>.b<a.d<Status>> {
        private final Status wJ;

        ContactSettingsUpdatedCallback(a.d<Status> resultHolder, int statusCode) {
            super(resultHolder);
            this.wJ = new Status(statusCode);
        }

        /* JADX INFO: Access modifiers changed from: protected */
        @Override // com.google.android.gms.internal.ff.b
        /* JADX INFO: renamed from: c, reason: merged with bridge method [inline-methods] */
        public void a(a.d<Status> dVar) {
            dVar.b(this.wJ);
        }

        @Override // com.google.android.gms.internal.ff.b
        protected void dx() {
        }
    }

    final class DisconnectedFromRoomCallback extends AbstractRoomStatusCallback {
        DisconnectedFromRoomCallback(RoomStatusUpdateListener listener, DataHolder dataHolder) {
            super(listener, dataHolder);
        }

        @Override // com.google.android.gms.games.internal.GamesClientImpl.AbstractRoomStatusCallback
        public void a(RoomStatusUpdateListener roomStatusUpdateListener, Room room) {
            roomStatusUpdateListener.onDisconnectedFromRoom(room);
        }
    }

    final class ExtendedGamesLoadedBinderCallback extends AbstractGamesCallbacks {
        private final a.d<GamesMetadata.LoadExtendedGamesResult> wH;

        ExtendedGamesLoadedBinderCallback(a.d<GamesMetadata.LoadExtendedGamesResult> holder) {
            this.wH = (a.d) fq.b(holder, "Holder must not be null");
        }

        @Override // com.google.android.gms.games.internal.AbstractGamesCallbacks, com.google.android.gms.games.internal.IGamesCallbacks
        public void h(DataHolder dataHolder) {
            GamesClientImpl.this.a(GamesClientImpl.this.new ExtendedGamesLoadedCallback(this.wH, dataHolder));
        }
    }

    final class ExtendedGamesLoadedCallback extends ResultDataHolderCallback<a.d<GamesMetadata.LoadExtendedGamesResult>> implements GamesMetadata.LoadExtendedGamesResult {
        private final ExtendedGameBuffer IM;

        ExtendedGamesLoadedCallback(a.d<GamesMetadata.LoadExtendedGamesResult> resultHolder, DataHolder dataHolder) {
            super(resultHolder, dataHolder);
            this.IM = new ExtendedGameBuffer(dataHolder);
        }

        /* JADX INFO: Access modifiers changed from: protected */
        @Override // com.google.android.gms.internal.ff.d
        public void a(a.d<GamesMetadata.LoadExtendedGamesResult> dVar, DataHolder dataHolder) {
            dVar.b(this);
        }
    }

    final class ExtendedPlayersLoadedBinderCallback extends AbstractGamesCallbacks {
        private final a.d<Players.LoadExtendedPlayersResult> wH;

        ExtendedPlayersLoadedBinderCallback(a.d<Players.LoadExtendedPlayersResult> holder) {
            this.wH = (a.d) fq.b(holder, "Holder must not be null");
        }

        @Override // com.google.android.gms.games.internal.AbstractGamesCallbacks, com.google.android.gms.games.internal.IGamesCallbacks
        public void f(DataHolder dataHolder) {
            GamesClientImpl.this.a(GamesClientImpl.this.new ExtendedPlayersLoadedCallback(this.wH, dataHolder));
        }
    }

    final class ExtendedPlayersLoadedCallback extends ResultDataHolderCallback<a.d<Players.LoadExtendedPlayersResult>> implements Players.LoadExtendedPlayersResult {
        private final ExtendedPlayerBuffer IN;

        ExtendedPlayersLoadedCallback(a.d<Players.LoadExtendedPlayersResult> resultHolder, DataHolder dataHolder) {
            super(resultHolder, dataHolder);
            this.IN = new ExtendedPlayerBuffer(dataHolder);
        }

        /* JADX INFO: Access modifiers changed from: protected */
        @Override // com.google.android.gms.internal.ff.d
        public void a(a.d<Players.LoadExtendedPlayersResult> dVar, DataHolder dataHolder) {
            dVar.b(this);
        }
    }

    final class GameInstancesLoadedBinderCallback extends AbstractGamesCallbacks {
        private final a.d<GamesMetadata.LoadGameInstancesResult> wH;

        GameInstancesLoadedBinderCallback(a.d<GamesMetadata.LoadGameInstancesResult> holder) {
            this.wH = (a.d) fq.b(holder, "Holder must not be null");
        }

        @Override // com.google.android.gms.games.internal.AbstractGamesCallbacks, com.google.android.gms.games.internal.IGamesCallbacks
        public void i(DataHolder dataHolder) {
            GamesClientImpl.this.a(GamesClientImpl.this.new GameInstancesLoadedCallback(this.wH, dataHolder));
        }
    }

    final class GameInstancesLoadedCallback extends ResultDataHolderCallback<a.d<GamesMetadata.LoadGameInstancesResult>> implements GamesMetadata.LoadGameInstancesResult {
        private final GameInstanceBuffer IO;

        GameInstancesLoadedCallback(a.d<GamesMetadata.LoadGameInstancesResult> resultHolder, DataHolder dataHolder) {
            super(resultHolder, dataHolder);
            this.IO = new GameInstanceBuffer(dataHolder);
        }

        /* JADX INFO: Access modifiers changed from: protected */
        @Override // com.google.android.gms.internal.ff.d
        public void a(a.d<GamesMetadata.LoadGameInstancesResult> dVar, DataHolder dataHolder) {
            dVar.b(this);
        }
    }

    final class GameMuteStatusChangedBinderCallback extends AbstractGamesCallbacks {
        private final a.d<Notifications.GameMuteStatusChangeResult> wH;

        GameMuteStatusChangedBinderCallback(a.d<Notifications.GameMuteStatusChangeResult> holder) {
            this.wH = (a.d) fq.b(holder, "Holder must not be null");
        }

        @Override // com.google.android.gms.games.internal.AbstractGamesCallbacks, com.google.android.gms.games.internal.IGamesCallbacks
        public void a(int i, String str, boolean z) {
            GamesClientImpl.this.a(GamesClientImpl.this.new GameMuteStatusChangedCallback(this.wH, i, str, z));
        }
    }

    final class GameMuteStatusChangedCallback extends ff<IGamesService>.b<a.d<Notifications.GameMuteStatusChangeResult>> implements Notifications.GameMuteStatusChangeResult {
        private final String IP;
        private final boolean IQ;
        private final Status wJ;

        public GameMuteStatusChangedCallback(a.d<Notifications.GameMuteStatusChangeResult> resultHolder, int statusCode, String externalGameId, boolean isMuted) {
            super(resultHolder);
            this.wJ = new Status(statusCode);
            this.IP = externalGameId;
            this.IQ = isMuted;
        }

        /* JADX INFO: Access modifiers changed from: protected */
        @Override // com.google.android.gms.internal.ff.b
        /* JADX INFO: renamed from: c, reason: merged with bridge method [inline-methods] */
        public void a(a.d<Notifications.GameMuteStatusChangeResult> dVar) {
            dVar.b(this);
        }

        @Override // com.google.android.gms.internal.ff.b
        protected void dx() {
        }

        @Override // com.google.android.gms.common.api.Result
        public Status getStatus() {
            return this.wJ;
        }
    }

    final class GameMuteStatusLoadedBinderCallback extends AbstractGamesCallbacks {
        private final a.d<Notifications.GameMuteStatusLoadResult> wH;

        GameMuteStatusLoadedBinderCallback(a.d<Notifications.GameMuteStatusLoadResult> holder) {
            this.wH = (a.d) fq.b(holder, "Holder must not be null");
        }

        @Override // com.google.android.gms.games.internal.AbstractGamesCallbacks, com.google.android.gms.games.internal.IGamesCallbacks
        public void z(DataHolder dataHolder) {
            GamesClientImpl.this.a(GamesClientImpl.this.new GameMuteStatusLoadedCallback(this.wH, dataHolder));
        }
    }

    final class GameMuteStatusLoadedCallback extends ff<IGamesService>.b<a.d<Notifications.GameMuteStatusLoadResult>> implements Notifications.GameMuteStatusLoadResult {
        private final String IP;
        private final boolean IQ;
        private final Status wJ;

        public GameMuteStatusLoadedCallback(a.d<Notifications.GameMuteStatusLoadResult> resultHolder, DataHolder dataHolder) {
            super(resultHolder);
            try {
                this.wJ = new Status(dataHolder.getStatusCode());
                if (dataHolder.getCount() > 0) {
                    this.IP = dataHolder.getString("external_game_id", 0, 0);
                    this.IQ = dataHolder.getBoolean("muted", 0, 0);
                } else {
                    this.IP = null;
                    this.IQ = false;
                }
            } finally {
                dataHolder.close();
            }
        }

        /* JADX INFO: Access modifiers changed from: protected */
        @Override // com.google.android.gms.internal.ff.b
        /* JADX INFO: renamed from: c, reason: merged with bridge method [inline-methods] */
        public void a(a.d<Notifications.GameMuteStatusLoadResult> dVar) {
            dVar.b(this);
        }

        @Override // com.google.android.gms.internal.ff.b
        protected void dx() {
        }

        @Override // com.google.android.gms.common.api.Result
        public Status getStatus() {
            return this.wJ;
        }
    }

    final class GameSearchSuggestionsLoadedBinderCallback extends AbstractGamesCallbacks {
        private final a.d<GamesMetadata.LoadGameSearchSuggestionsResult> wH;

        GameSearchSuggestionsLoadedBinderCallback(a.d<GamesMetadata.LoadGameSearchSuggestionsResult> holder) {
            this.wH = (a.d) fq.b(holder, "Holder must not be null");
        }

        @Override // com.google.android.gms.games.internal.AbstractGamesCallbacks, com.google.android.gms.games.internal.IGamesCallbacks
        public void j(DataHolder dataHolder) {
            GamesClientImpl.this.a(GamesClientImpl.this.new GameSearchSuggestionsLoadedCallback(this.wH, dataHolder));
        }
    }

    final class GameSearchSuggestionsLoadedCallback extends ResultDataHolderCallback<a.d<GamesMetadata.LoadGameSearchSuggestionsResult>> implements GamesMetadata.LoadGameSearchSuggestionsResult {
        private final DataHolder IR;

        GameSearchSuggestionsLoadedCallback(a.d<GamesMetadata.LoadGameSearchSuggestionsResult> resultHolder, DataHolder data) {
            super(resultHolder, data);
            this.IR = data;
        }

        /* JADX INFO: Access modifiers changed from: protected */
        @Override // com.google.android.gms.internal.ff.d
        public void a(a.d<GamesMetadata.LoadGameSearchSuggestionsResult> dVar, DataHolder dataHolder) {
            dVar.b(this);
        }
    }

    final class GamesLoadedBinderCallback extends AbstractGamesCallbacks {
        private final a.d<GamesMetadata.LoadGamesResult> wH;

        GamesLoadedBinderCallback(a.d<GamesMetadata.LoadGamesResult> holder) {
            this.wH = (a.d) fq.b(holder, "Holder must not be null");
        }

        @Override // com.google.android.gms.games.internal.AbstractGamesCallbacks, com.google.android.gms.games.internal.IGamesCallbacks
        public void g(DataHolder dataHolder) {
            GamesClientImpl.this.a(GamesClientImpl.this.new GamesLoadedCallback(this.wH, dataHolder));
        }
    }

    final class GamesLoadedCallback extends ResultDataHolderCallback<a.d<GamesMetadata.LoadGamesResult>> implements GamesMetadata.LoadGamesResult {
        private final GameBuffer IS;

        GamesLoadedCallback(a.d<GamesMetadata.LoadGamesResult> resultHolder, DataHolder dataHolder) {
            super(resultHolder, dataHolder);
            this.IS = new GameBuffer(dataHolder);
        }

        /* JADX INFO: Access modifiers changed from: protected */
        @Override // com.google.android.gms.internal.ff.d
        public void a(a.d<GamesMetadata.LoadGamesResult> dVar, DataHolder dataHolder) {
            dVar.b(this);
        }

        @Override // com.google.android.gms.games.GamesMetadata.LoadGamesResult
        public GameBuffer getGames() {
            return this.IS;
        }
    }

    final class InvitationReceivedBinderCallback extends AbstractGamesCallbacks {
        private final OnInvitationReceivedListener IT;

        InvitationReceivedBinderCallback(OnInvitationReceivedListener listener) {
            this.IT = listener;
        }

        @Override // com.google.android.gms.games.internal.AbstractGamesCallbacks, com.google.android.gms.games.internal.IGamesCallbacks
        public void l(DataHolder dataHolder) {
            InvitationBuffer invitationBuffer = new InvitationBuffer(dataHolder);
            try {
                Invitation invitationFreeze = invitationBuffer.getCount() > 0 ? invitationBuffer.get(0).freeze() : null;
                invitationBuffer.close();
                if (invitationFreeze != null) {
                    GamesClientImpl.this.a(GamesClientImpl.this.new InvitationReceivedCallback(this.IT, invitationFreeze));
                }
            } catch (Throwable th) {
                invitationBuffer.close();
                throw th;
            }
        }

        @Override // com.google.android.gms.games.internal.AbstractGamesCallbacks, com.google.android.gms.games.internal.IGamesCallbacks
        public void onInvitationRemoved(String invitationId) {
            GamesClientImpl.this.a(GamesClientImpl.this.new InvitationRemovedCallback(this.IT, invitationId));
        }
    }

    final class InvitationReceivedCallback extends ff<IGamesService>.b<OnInvitationReceivedListener> {
        private final Invitation IU;

        InvitationReceivedCallback(OnInvitationReceivedListener listener, Invitation invitation) {
            super(listener);
            this.IU = invitation;
        }

        /* JADX INFO: Access modifiers changed from: protected */
        @Override // com.google.android.gms.internal.ff.b
        /* JADX INFO: renamed from: b, reason: merged with bridge method [inline-methods] */
        public void a(OnInvitationReceivedListener onInvitationReceivedListener) {
            onInvitationReceivedListener.onInvitationReceived(this.IU);
        }

        @Override // com.google.android.gms.internal.ff.b
        protected void dx() {
        }
    }

    final class InvitationRemovedCallback extends ff<IGamesService>.b<OnInvitationReceivedListener> {
        private final String IV;

        InvitationRemovedCallback(OnInvitationReceivedListener listener, String invitationId) {
            super(listener);
            this.IV = invitationId;
        }

        /* JADX INFO: Access modifiers changed from: protected */
        @Override // com.google.android.gms.internal.ff.b
        /* JADX INFO: renamed from: b, reason: merged with bridge method [inline-methods] */
        public void a(OnInvitationReceivedListener onInvitationReceivedListener) {
            onInvitationReceivedListener.onInvitationRemoved(this.IV);
        }

        @Override // com.google.android.gms.internal.ff.b
        protected void dx() {
        }
    }

    final class InvitationsLoadedBinderCallback extends AbstractGamesCallbacks {
        private final a.d<Invitations.LoadInvitationsResult> wH;

        InvitationsLoadedBinderCallback(a.d<Invitations.LoadInvitationsResult> resultHolder) {
            this.wH = (a.d) fq.b(resultHolder, "Holder must not be null");
        }

        @Override // com.google.android.gms.games.internal.AbstractGamesCallbacks, com.google.android.gms.games.internal.IGamesCallbacks
        public void k(DataHolder dataHolder) {
            GamesClientImpl.this.a(GamesClientImpl.this.new InvitationsLoadedCallback(this.wH, dataHolder));
        }
    }

    final class InvitationsLoadedCallback extends ResultDataHolderCallback<a.d<Invitations.LoadInvitationsResult>> implements Invitations.LoadInvitationsResult {
        private final InvitationBuffer IW;

        InvitationsLoadedCallback(a.d<Invitations.LoadInvitationsResult> resultHolder, DataHolder dataHolder) {
            super(resultHolder, dataHolder);
            this.IW = new InvitationBuffer(dataHolder);
        }

        /* JADX INFO: Access modifiers changed from: protected */
        @Override // com.google.android.gms.internal.ff.d
        public void a(a.d<Invitations.LoadInvitationsResult> dVar, DataHolder dataHolder) {
            dVar.b(this);
        }

        @Override // com.google.android.gms.games.multiplayer.Invitations.LoadInvitationsResult
        public InvitationBuffer getInvitations() {
            return this.IW;
        }
    }

    final class JoinedRoomCallback extends AbstractRoomCallback {
        public JoinedRoomCallback(RoomUpdateListener listener, DataHolder dataHolder) {
            super(listener, dataHolder);
        }

        @Override // com.google.android.gms.games.internal.GamesClientImpl.AbstractRoomCallback
        public void a(RoomUpdateListener roomUpdateListener, Room room, int i) {
            roomUpdateListener.onJoinedRoom(i, room);
        }
    }

    final class LeaderboardScoresLoadedBinderCallback extends AbstractGamesCallbacks {
        private final a.d<Leaderboards.LoadScoresResult> wH;

        LeaderboardScoresLoadedBinderCallback(a.d<Leaderboards.LoadScoresResult> resultHolder) {
            this.wH = (a.d) fq.b(resultHolder, "Holder must not be null");
        }

        @Override // com.google.android.gms.games.internal.AbstractGamesCallbacks, com.google.android.gms.games.internal.IGamesCallbacks
        public void a(DataHolder dataHolder, DataHolder dataHolder2) {
            GamesClientImpl.this.a(GamesClientImpl.this.new LeaderboardScoresLoadedCallback(this.wH, dataHolder, dataHolder2));
        }
    }

    final class LeaderboardScoresLoadedCallback extends ResultDataHolderCallback<a.d<Leaderboards.LoadScoresResult>> implements Leaderboards.LoadScoresResult {
        private final LeaderboardEntity IX;
        private final LeaderboardScoreBuffer IY;

        LeaderboardScoresLoadedCallback(a.d<Leaderboards.LoadScoresResult> resultHolder, DataHolder leaderboard, DataHolder scores) {
            super(resultHolder, scores);
            LeaderboardBuffer leaderboardBuffer = new LeaderboardBuffer(leaderboard);
            try {
                if (leaderboardBuffer.getCount() > 0) {
                    this.IX = (LeaderboardEntity) leaderboardBuffer.get(0).freeze();
                } else {
                    this.IX = null;
                }
                leaderboardBuffer.close();
                this.IY = new LeaderboardScoreBuffer(scores);
            } catch (Throwable th) {
                leaderboardBuffer.close();
                throw th;
            }
        }

        /* JADX INFO: Access modifiers changed from: protected */
        @Override // com.google.android.gms.internal.ff.d
        public void a(a.d<Leaderboards.LoadScoresResult> dVar, DataHolder dataHolder) {
            dVar.b(this);
        }

        @Override // com.google.android.gms.games.leaderboard.Leaderboards.LoadScoresResult
        public Leaderboard getLeaderboard() {
            return this.IX;
        }

        @Override // com.google.android.gms.games.leaderboard.Leaderboards.LoadScoresResult
        public LeaderboardScoreBuffer getScores() {
            return this.IY;
        }
    }

    final class LeaderboardsLoadedBinderCallback extends AbstractGamesCallbacks {
        private final a.d<Leaderboards.LeaderboardMetadataResult> wH;

        LeaderboardsLoadedBinderCallback(a.d<Leaderboards.LeaderboardMetadataResult> resultHolder) {
            this.wH = (a.d) fq.b(resultHolder, "Holder must not be null");
        }

        @Override // com.google.android.gms.games.internal.AbstractGamesCallbacks, com.google.android.gms.games.internal.IGamesCallbacks
        public void c(DataHolder dataHolder) {
            GamesClientImpl.this.a(GamesClientImpl.this.new LeaderboardsLoadedCallback(this.wH, dataHolder));
        }
    }

    final class LeaderboardsLoadedCallback extends ResultDataHolderCallback<a.d<Leaderboards.LeaderboardMetadataResult>> implements Leaderboards.LeaderboardMetadataResult {
        private final LeaderboardBuffer IZ;

        LeaderboardsLoadedCallback(a.d<Leaderboards.LeaderboardMetadataResult> resultHolder, DataHolder dataHolder) {
            super(resultHolder, dataHolder);
            this.IZ = new LeaderboardBuffer(dataHolder);
        }

        /* JADX INFO: Access modifiers changed from: protected */
        @Override // com.google.android.gms.internal.ff.d
        public void a(a.d<Leaderboards.LeaderboardMetadataResult> dVar, DataHolder dataHolder) {
            dVar.b(this);
        }

        @Override // com.google.android.gms.games.leaderboard.Leaderboards.LeaderboardMetadataResult
        public LeaderboardBuffer getLeaderboards() {
            return this.IZ;
        }
    }

    final class LeftRoomCallback extends ff<IGamesService>.b<RoomUpdateListener> {
        private final int Ah;
        private final String Ja;

        LeftRoomCallback(RoomUpdateListener listener, int statusCode, String roomId) {
            super(listener);
            this.Ah = statusCode;
            this.Ja = roomId;
        }

        @Override // com.google.android.gms.internal.ff.b
        public void a(RoomUpdateListener roomUpdateListener) {
            roomUpdateListener.onLeftRoom(this.Ah, this.Ja);
        }

        @Override // com.google.android.gms.internal.ff.b
        protected void dx() {
        }
    }

    final class MatchRemovedCallback extends ff<IGamesService>.b<OnTurnBasedMatchUpdateReceivedListener> {
        private final String Jb;

        MatchRemovedCallback(OnTurnBasedMatchUpdateReceivedListener listener, String matchId) {
            super(listener);
            this.Jb = matchId;
        }

        /* JADX INFO: Access modifiers changed from: protected */
        @Override // com.google.android.gms.internal.ff.b
        /* JADX INFO: renamed from: b, reason: merged with bridge method [inline-methods] */
        public void a(OnTurnBasedMatchUpdateReceivedListener onTurnBasedMatchUpdateReceivedListener) {
            onTurnBasedMatchUpdateReceivedListener.onTurnBasedMatchRemoved(this.Jb);
        }

        @Override // com.google.android.gms.internal.ff.b
        protected void dx() {
        }
    }

    final class MatchUpdateReceivedBinderCallback extends AbstractGamesCallbacks {
        private final OnTurnBasedMatchUpdateReceivedListener Jc;

        MatchUpdateReceivedBinderCallback(OnTurnBasedMatchUpdateReceivedListener listener) {
            this.Jc = listener;
        }

        @Override // com.google.android.gms.games.internal.AbstractGamesCallbacks, com.google.android.gms.games.internal.IGamesCallbacks
        public void onTurnBasedMatchRemoved(String matchId) {
            GamesClientImpl.this.a(GamesClientImpl.this.new MatchRemovedCallback(this.Jc, matchId));
        }

        @Override // com.google.android.gms.games.internal.AbstractGamesCallbacks, com.google.android.gms.games.internal.IGamesCallbacks
        public void r(DataHolder dataHolder) {
            TurnBasedMatchBuffer turnBasedMatchBuffer = new TurnBasedMatchBuffer(dataHolder);
            try {
                TurnBasedMatch turnBasedMatchFreeze = turnBasedMatchBuffer.getCount() > 0 ? turnBasedMatchBuffer.get(0).freeze() : null;
                turnBasedMatchBuffer.close();
                if (turnBasedMatchFreeze != null) {
                    GamesClientImpl.this.a(GamesClientImpl.this.new MatchUpdateReceivedCallback(this.Jc, turnBasedMatchFreeze));
                }
            } catch (Throwable th) {
                turnBasedMatchBuffer.close();
                throw th;
            }
        }
    }

    final class MatchUpdateReceivedCallback extends ff<IGamesService>.b<OnTurnBasedMatchUpdateReceivedListener> {
        private final TurnBasedMatch Jd;

        MatchUpdateReceivedCallback(OnTurnBasedMatchUpdateReceivedListener listener, TurnBasedMatch match) {
            super(listener);
            this.Jd = match;
        }

        /* JADX INFO: Access modifiers changed from: protected */
        @Override // com.google.android.gms.internal.ff.b
        /* JADX INFO: renamed from: b, reason: merged with bridge method [inline-methods] */
        public void a(OnTurnBasedMatchUpdateReceivedListener onTurnBasedMatchUpdateReceivedListener) {
            onTurnBasedMatchUpdateReceivedListener.onTurnBasedMatchReceived(this.Jd);
        }

        @Override // com.google.android.gms.internal.ff.b
        protected void dx() {
        }
    }

    final class MessageReceivedCallback extends ff<IGamesService>.b<RealTimeMessageReceivedListener> {
        private final RealTimeMessage Je;

        MessageReceivedCallback(RealTimeMessageReceivedListener listener, RealTimeMessage message) {
            super(listener);
            this.Je = message;
        }

        @Override // com.google.android.gms.internal.ff.b
        public void a(RealTimeMessageReceivedListener realTimeMessageReceivedListener) {
            if (realTimeMessageReceivedListener != null) {
                realTimeMessageReceivedListener.onRealTimeMessageReceived(this.Je);
            }
        }

        @Override // com.google.android.gms.internal.ff.b
        protected void dx() {
        }
    }

    final class NotifyAclLoadedBinderCallback extends AbstractGamesCallbacks {
        private final a.d<Acls.LoadAclResult> wH;

        NotifyAclLoadedBinderCallback(a.d<Acls.LoadAclResult> resultHolder) {
            this.wH = (a.d) fq.b(resultHolder, "Holder must not be null");
        }

        @Override // com.google.android.gms.games.internal.AbstractGamesCallbacks, com.google.android.gms.games.internal.IGamesCallbacks
        public void A(DataHolder dataHolder) {
            GamesClientImpl.this.a(GamesClientImpl.this.new NotifyAclLoadedCallback(this.wH, dataHolder));
        }
    }

    final class NotifyAclLoadedCallback extends ResultDataHolderCallback<a.d<Acls.LoadAclResult>> implements Acls.LoadAclResult {
        NotifyAclLoadedCallback(a.d<Acls.LoadAclResult> resultHolder, DataHolder dataHolder) {
            super(resultHolder, dataHolder);
        }

        /* JADX INFO: Access modifiers changed from: protected */
        @Override // com.google.android.gms.internal.ff.d
        public void a(a.d<Acls.LoadAclResult> dVar, DataHolder dataHolder) {
            dVar.b(this);
        }
    }

    final class NotifyAclUpdatedBinderCallback extends AbstractGamesCallbacks {
        private final a.d<Status> wH;

        NotifyAclUpdatedBinderCallback(a.d<Status> resultHolder) {
            this.wH = (a.d) fq.b(resultHolder, "Holder must not be null");
        }

        @Override // com.google.android.gms.games.internal.AbstractGamesCallbacks, com.google.android.gms.games.internal.IGamesCallbacks
        public void aU(int i) {
            GamesClientImpl.this.a(GamesClientImpl.this.new NotifyAclUpdatedCallback(this.wH, i));
        }
    }

    final class NotifyAclUpdatedCallback extends ff<IGamesService>.b<a.d<Status>> {
        private final Status wJ;

        NotifyAclUpdatedCallback(a.d<Status> resultHolder, int statusCode) {
            super(resultHolder);
            this.wJ = new Status(statusCode);
        }

        /* JADX INFO: Access modifiers changed from: protected */
        @Override // com.google.android.gms.internal.ff.b
        /* JADX INFO: renamed from: c, reason: merged with bridge method [inline-methods] */
        public void a(a.d<Status> dVar) {
            dVar.b(this.wJ);
        }

        @Override // com.google.android.gms.internal.ff.b
        protected void dx() {
        }
    }

    final class OwnerCoverPhotoUrisLoadedBinderCallback extends AbstractGamesCallbacks {
        private final a.d<Players.LoadOwnerCoverPhotoUrisResult> wH;

        OwnerCoverPhotoUrisLoadedBinderCallback(a.d<Players.LoadOwnerCoverPhotoUrisResult> holder) {
            this.wH = (a.d) fq.b(holder, "Holder must not be null");
        }

        @Override // com.google.android.gms.games.internal.AbstractGamesCallbacks, com.google.android.gms.games.internal.IGamesCallbacks
        public void c(int i, Bundle bundle) {
            bundle.setClassLoader(getClass().getClassLoader());
            GamesClientImpl.this.a(GamesClientImpl.this.new OwnerCoverPhotoUrisLoadedCallback(this.wH, i, bundle));
        }
    }

    final class OwnerCoverPhotoUrisLoadedCallback extends ff<IGamesService>.b<a.d<Players.LoadOwnerCoverPhotoUrisResult>> implements Players.LoadOwnerCoverPhotoUrisResult {
        private final Bundle Jf;
        private final Status wJ;

        OwnerCoverPhotoUrisLoadedCallback(a.d<Players.LoadOwnerCoverPhotoUrisResult> resultHolder, int statusCode, Bundle bundle) {
            super(resultHolder);
            this.wJ = new Status(statusCode);
            this.Jf = bundle;
        }

        /* JADX INFO: Access modifiers changed from: protected */
        @Override // com.google.android.gms.internal.ff.b
        /* JADX INFO: renamed from: c, reason: merged with bridge method [inline-methods] */
        public void a(a.d<Players.LoadOwnerCoverPhotoUrisResult> dVar) {
            dVar.b(this);
        }

        @Override // com.google.android.gms.internal.ff.b
        protected void dx() {
        }

        @Override // com.google.android.gms.common.api.Result
        public Status getStatus() {
            return this.wJ;
        }
    }

    final class P2PConnectedCallback extends ff<IGamesService>.b<RoomStatusUpdateListener> {
        private final String Jg;

        P2PConnectedCallback(RoomStatusUpdateListener listener, String participantId) {
            super(listener);
            this.Jg = participantId;
        }

        @Override // com.google.android.gms.internal.ff.b
        public void a(RoomStatusUpdateListener roomStatusUpdateListener) {
            if (roomStatusUpdateListener != null) {
                roomStatusUpdateListener.onP2PConnected(this.Jg);
            }
        }

        @Override // com.google.android.gms.internal.ff.b
        protected void dx() {
        }
    }

    final class P2PDisconnectedCallback extends ff<IGamesService>.b<RoomStatusUpdateListener> {
        private final String Jg;

        P2PDisconnectedCallback(RoomStatusUpdateListener listener, String participantId) {
            super(listener);
            this.Jg = participantId;
        }

        @Override // com.google.android.gms.internal.ff.b
        public void a(RoomStatusUpdateListener roomStatusUpdateListener) {
            if (roomStatusUpdateListener != null) {
                roomStatusUpdateListener.onP2PDisconnected(this.Jg);
            }
        }

        @Override // com.google.android.gms.internal.ff.b
        protected void dx() {
        }
    }

    final class PeerConnectedCallback extends AbstractPeerStatusCallback {
        PeerConnectedCallback(RoomStatusUpdateListener listener, DataHolder dataHolder, String[] participantIds) {
            super(listener, dataHolder, participantIds);
        }

        @Override // com.google.android.gms.games.internal.GamesClientImpl.AbstractPeerStatusCallback
        protected void a(RoomStatusUpdateListener roomStatusUpdateListener, Room room, ArrayList<String> arrayList) {
            roomStatusUpdateListener.onPeersConnected(room, arrayList);
        }
    }

    final class PeerDeclinedCallback extends AbstractPeerStatusCallback {
        PeerDeclinedCallback(RoomStatusUpdateListener listener, DataHolder dataHolder, String[] participantIds) {
            super(listener, dataHolder, participantIds);
        }

        @Override // com.google.android.gms.games.internal.GamesClientImpl.AbstractPeerStatusCallback
        protected void a(RoomStatusUpdateListener roomStatusUpdateListener, Room room, ArrayList<String> arrayList) {
            roomStatusUpdateListener.onPeerDeclined(room, arrayList);
        }
    }

    final class PeerDisconnectedCallback extends AbstractPeerStatusCallback {
        PeerDisconnectedCallback(RoomStatusUpdateListener listener, DataHolder dataHolder, String[] participantIds) {
            super(listener, dataHolder, participantIds);
        }

        @Override // com.google.android.gms.games.internal.GamesClientImpl.AbstractPeerStatusCallback
        protected void a(RoomStatusUpdateListener roomStatusUpdateListener, Room room, ArrayList<String> arrayList) {
            roomStatusUpdateListener.onPeersDisconnected(room, arrayList);
        }
    }

    final class PeerInvitedToRoomCallback extends AbstractPeerStatusCallback {
        PeerInvitedToRoomCallback(RoomStatusUpdateListener listener, DataHolder dataHolder, String[] participantIds) {
            super(listener, dataHolder, participantIds);
        }

        @Override // com.google.android.gms.games.internal.GamesClientImpl.AbstractPeerStatusCallback
        protected void a(RoomStatusUpdateListener roomStatusUpdateListener, Room room, ArrayList<String> arrayList) {
            roomStatusUpdateListener.onPeerInvitedToRoom(room, arrayList);
        }
    }

    final class PeerJoinedRoomCallback extends AbstractPeerStatusCallback {
        PeerJoinedRoomCallback(RoomStatusUpdateListener listener, DataHolder dataHolder, String[] participantIds) {
            super(listener, dataHolder, participantIds);
        }

        @Override // com.google.android.gms.games.internal.GamesClientImpl.AbstractPeerStatusCallback
        protected void a(RoomStatusUpdateListener roomStatusUpdateListener, Room room, ArrayList<String> arrayList) {
            roomStatusUpdateListener.onPeerJoined(room, arrayList);
        }
    }

    final class PeerLeftRoomCallback extends AbstractPeerStatusCallback {
        PeerLeftRoomCallback(RoomStatusUpdateListener listener, DataHolder dataHolder, String[] participantIds) {
            super(listener, dataHolder, participantIds);
        }

        @Override // com.google.android.gms.games.internal.GamesClientImpl.AbstractPeerStatusCallback
        protected void a(RoomStatusUpdateListener roomStatusUpdateListener, Room room, ArrayList<String> arrayList) {
            roomStatusUpdateListener.onPeerLeft(room, arrayList);
        }
    }

    final class PlayerLeaderboardScoreLoadedBinderCallback extends AbstractGamesCallbacks {
        private final a.d<Leaderboards.LoadPlayerScoreResult> wH;

        PlayerLeaderboardScoreLoadedBinderCallback(a.d<Leaderboards.LoadPlayerScoreResult> resultHolder) {
            this.wH = (a.d) fq.b(resultHolder, "Holder must not be null");
        }

        @Override // com.google.android.gms.games.internal.AbstractGamesCallbacks, com.google.android.gms.games.internal.IGamesCallbacks
        public void C(DataHolder dataHolder) {
            GamesClientImpl.this.a(GamesClientImpl.this.new PlayerLeaderboardScoreLoadedCallback(this.wH, dataHolder));
        }
    }

    final class PlayerLeaderboardScoreLoadedCallback extends ff<IGamesService>.d<a.d<Leaderboards.LoadPlayerScoreResult>> implements Leaderboards.LoadPlayerScoreResult {
        private final LeaderboardScoreEntity Jh;
        private final Status wJ;

        PlayerLeaderboardScoreLoadedCallback(a.d<Leaderboards.LoadPlayerScoreResult> resultHolder, DataHolder scoreHolder) {
            super(resultHolder, scoreHolder);
            this.wJ = new Status(scoreHolder.getStatusCode());
            LeaderboardScoreBuffer leaderboardScoreBuffer = new LeaderboardScoreBuffer(scoreHolder);
            try {
                if (leaderboardScoreBuffer.getCount() > 0) {
                    this.Jh = (LeaderboardScoreEntity) leaderboardScoreBuffer.get(0).freeze();
                } else {
                    this.Jh = null;
                }
            } finally {
                leaderboardScoreBuffer.close();
            }
        }

        /* JADX INFO: Access modifiers changed from: protected */
        @Override // com.google.android.gms.internal.ff.d
        public void a(a.d<Leaderboards.LoadPlayerScoreResult> dVar, DataHolder dataHolder) {
            dVar.b(this);
        }

        @Override // com.google.android.gms.games.leaderboard.Leaderboards.LoadPlayerScoreResult
        public LeaderboardScore getScore() {
            return this.Jh;
        }

        @Override // com.google.android.gms.common.api.Result
        public Status getStatus() {
            return this.wJ;
        }
    }

    final class PlayersLoadedBinderCallback extends AbstractGamesCallbacks {
        private final a.d<Players.LoadPlayersResult> wH;

        PlayersLoadedBinderCallback(a.d<Players.LoadPlayersResult> holder) {
            this.wH = (a.d) fq.b(holder, "Holder must not be null");
        }

        @Override // com.google.android.gms.games.internal.AbstractGamesCallbacks, com.google.android.gms.games.internal.IGamesCallbacks
        public void e(DataHolder dataHolder) {
            GamesClientImpl.this.a(GamesClientImpl.this.new PlayersLoadedCallback(this.wH, dataHolder));
        }
    }

    final class PlayersLoadedCallback extends ResultDataHolderCallback<a.d<Players.LoadPlayersResult>> implements Players.LoadPlayersResult {
        private final PlayerBuffer Ji;

        PlayersLoadedCallback(a.d<Players.LoadPlayersResult> resultHolder, DataHolder dataHolder) {
            super(resultHolder, dataHolder);
            this.Ji = new PlayerBuffer(dataHolder);
        }

        /* JADX INFO: Access modifiers changed from: protected */
        @Override // com.google.android.gms.internal.ff.d
        public void a(a.d<Players.LoadPlayersResult> dVar, DataHolder dataHolder) {
            dVar.b(this);
        }

        @Override // com.google.android.gms.games.Players.LoadPlayersResult
        public PlayerBuffer getPlayers() {
            return this.Ji;
        }
    }

    final class RealTimeMessageSentCallback extends ff<IGamesService>.b<RealTimeMultiplayer.ReliableMessageSentCallback> {
        private final int Ah;
        private final String Jj;
        private final int Jk;

        RealTimeMessageSentCallback(RealTimeMultiplayer.ReliableMessageSentCallback listener, int statusCode, int token, String recipientParticipantId) {
            super(listener);
            this.Ah = statusCode;
            this.Jk = token;
            this.Jj = recipientParticipantId;
        }

        @Override // com.google.android.gms.internal.ff.b
        public void a(RealTimeMultiplayer.ReliableMessageSentCallback reliableMessageSentCallback) {
            if (reliableMessageSentCallback != null) {
                reliableMessageSentCallback.onRealTimeMessageSent(this.Ah, this.Jk, this.Jj);
            }
        }

        @Override // com.google.android.gms.internal.ff.b
        protected void dx() {
        }
    }

    final class RealTimeReliableMessageBinderCallbacks extends AbstractGamesCallbacks {
        final RealTimeMultiplayer.ReliableMessageSentCallback Jl;

        public RealTimeReliableMessageBinderCallbacks(RealTimeMultiplayer.ReliableMessageSentCallback messageSentCallbacks) {
            this.Jl = messageSentCallbacks;
        }

        @Override // com.google.android.gms.games.internal.AbstractGamesCallbacks, com.google.android.gms.games.internal.IGamesCallbacks
        public void b(int i, int i2, String str) {
            GamesClientImpl.this.a(GamesClientImpl.this.new RealTimeMessageSentCallback(this.Jl, i, i2, str));
        }
    }

    final class RequestReceivedBinderCallback extends AbstractGamesCallbacks {
        private final OnRequestReceivedListener Jm;

        RequestReceivedBinderCallback(OnRequestReceivedListener listener) {
            this.Jm = listener;
        }

        @Override // com.google.android.gms.games.internal.AbstractGamesCallbacks, com.google.android.gms.games.internal.IGamesCallbacks
        public void m(DataHolder dataHolder) {
            GameRequestBuffer gameRequestBuffer = new GameRequestBuffer(dataHolder);
            try {
                GameRequest gameRequestFreeze = gameRequestBuffer.getCount() > 0 ? gameRequestBuffer.get(0).freeze() : null;
                gameRequestBuffer.close();
                if (gameRequestFreeze != null) {
                    GamesClientImpl.this.a(GamesClientImpl.this.new RequestReceivedCallback(this.Jm, gameRequestFreeze));
                }
            } catch (Throwable th) {
                gameRequestBuffer.close();
                throw th;
            }
        }

        @Override // com.google.android.gms.games.internal.AbstractGamesCallbacks, com.google.android.gms.games.internal.IGamesCallbacks
        public void onRequestRemoved(String requestId) {
            GamesClientImpl.this.a(GamesClientImpl.this.new RequestRemovedCallback(this.Jm, requestId));
        }
    }

    final class RequestReceivedCallback extends ff<IGamesService>.b<OnRequestReceivedListener> {
        private final GameRequest Jn;

        RequestReceivedCallback(OnRequestReceivedListener listener, GameRequest request) {
            super(listener);
            this.Jn = request;
        }

        /* JADX INFO: Access modifiers changed from: protected */
        @Override // com.google.android.gms.internal.ff.b
        /* JADX INFO: renamed from: b, reason: merged with bridge method [inline-methods] */
        public void a(OnRequestReceivedListener onRequestReceivedListener) {
            onRequestReceivedListener.onRequestReceived(this.Jn);
        }

        @Override // com.google.android.gms.internal.ff.b
        protected void dx() {
        }
    }

    final class RequestRemovedCallback extends ff<IGamesService>.b<OnRequestReceivedListener> {
        private final String Jo;

        RequestRemovedCallback(OnRequestReceivedListener listener, String requestId) {
            super(listener);
            this.Jo = requestId;
        }

        /* JADX INFO: Access modifiers changed from: protected */
        @Override // com.google.android.gms.internal.ff.b
        /* JADX INFO: renamed from: b, reason: merged with bridge method [inline-methods] */
        public void a(OnRequestReceivedListener onRequestReceivedListener) {
            onRequestReceivedListener.onRequestRemoved(this.Jo);
        }

        @Override // com.google.android.gms.internal.ff.b
        protected void dx() {
        }
    }

    final class RequestSentBinderCallbacks extends AbstractGamesCallbacks {
        private final a.d<Requests.SendRequestResult> Jp;

        public RequestSentBinderCallbacks(a.d<Requests.SendRequestResult> resultHolder) {
            this.Jp = (a.d) fq.b(resultHolder, "Holder must not be null");
        }

        @Override // com.google.android.gms.games.internal.AbstractGamesCallbacks, com.google.android.gms.games.internal.IGamesCallbacks
        public void E(DataHolder dataHolder) {
            GamesClientImpl.this.a(GamesClientImpl.this.new RequestSentCallback(this.Jp, dataHolder));
        }
    }

    final class RequestSentCallback extends ResultDataHolderCallback<a.d<Requests.SendRequestResult>> implements Requests.SendRequestResult {
        private final GameRequest Jn;

        RequestSentCallback(a.d<Requests.SendRequestResult> resultHolder, DataHolder dataHolder) {
            super(resultHolder, dataHolder);
            GameRequestBuffer gameRequestBuffer = new GameRequestBuffer(dataHolder);
            try {
                if (gameRequestBuffer.getCount() > 0) {
                    this.Jn = gameRequestBuffer.get(0).freeze();
                } else {
                    this.Jn = null;
                }
            } finally {
                gameRequestBuffer.close();
            }
        }

        /* JADX INFO: Access modifiers changed from: protected */
        @Override // com.google.android.gms.internal.ff.d
        public void a(a.d<Requests.SendRequestResult> dVar, DataHolder dataHolder) {
            dVar.b(this);
        }
    }

    final class RequestSummariesLoadedBinderCallbacks extends AbstractGamesCallbacks {
        private final a.d<Requests.LoadRequestSummariesResult> Jq;

        public RequestSummariesLoadedBinderCallbacks(a.d<Requests.LoadRequestSummariesResult> resultHolder) {
            this.Jq = (a.d) fq.b(resultHolder, "Holder must not be null");
        }

        @Override // com.google.android.gms.games.internal.AbstractGamesCallbacks, com.google.android.gms.games.internal.IGamesCallbacks
        public void F(DataHolder dataHolder) {
            GamesClientImpl.this.a(GamesClientImpl.this.new RequestSummariesLoadedCallback(this.Jq, dataHolder));
        }
    }

    final class RequestSummariesLoadedCallback extends ResultDataHolderCallback<a.d<Requests.LoadRequestSummariesResult>> implements Requests.LoadRequestSummariesResult {
        RequestSummariesLoadedCallback(a.d<Requests.LoadRequestSummariesResult> resultHolder, DataHolder dataHolder) {
            super(resultHolder, dataHolder);
        }

        /* JADX INFO: Access modifiers changed from: protected */
        @Override // com.google.android.gms.internal.ff.d
        public void a(a.d<Requests.LoadRequestSummariesResult> dVar, DataHolder dataHolder) {
            dVar.b(this);
        }
    }

    final class RequestsLoadedBinderCallbacks extends AbstractGamesCallbacks {
        private final a.d<Requests.LoadRequestsResult> Jr;

        public RequestsLoadedBinderCallbacks(a.d<Requests.LoadRequestsResult> resultHolder) {
            this.Jr = (a.d) fq.b(resultHolder, "Holder must not be null");
        }

        @Override // com.google.android.gms.games.internal.AbstractGamesCallbacks, com.google.android.gms.games.internal.IGamesCallbacks
        public void b(int i, Bundle bundle) {
            bundle.setClassLoader(getClass().getClassLoader());
            GamesClientImpl.this.a(GamesClientImpl.this.new RequestsLoadedCallback(this.Jr, new Status(i), bundle));
        }
    }

    final class RequestsLoadedCallback extends ff<IGamesService>.b<a.d<Requests.LoadRequestsResult>> implements Requests.LoadRequestsResult {
        private final Bundle Js;
        private final Status wJ;

        RequestsLoadedCallback(a.d<Requests.LoadRequestsResult> resultHolder, Status status, Bundle requestData) {
            super(resultHolder);
            this.wJ = status;
            this.Js = requestData;
        }

        /* JADX INFO: Access modifiers changed from: protected */
        @Override // com.google.android.gms.internal.ff.b
        /* JADX INFO: renamed from: c, reason: merged with bridge method [inline-methods] */
        public void a(a.d<Requests.LoadRequestsResult> dVar) {
            dVar.b(this);
        }

        @Override // com.google.android.gms.internal.ff.b
        protected void dx() {
            release();
        }

        @Override // com.google.android.gms.games.request.Requests.LoadRequestsResult
        public GameRequestBuffer getRequests(int requestType) {
            String strBd = RequestType.bd(requestType);
            if (this.Js.containsKey(strBd)) {
                return new GameRequestBuffer((DataHolder) this.Js.get(strBd));
            }
            return null;
        }

        @Override // com.google.android.gms.common.api.Result
        public Status getStatus() {
            return this.wJ;
        }

        @Override // com.google.android.gms.common.api.Releasable
        public void release() {
            Iterator<String> it = this.Js.keySet().iterator();
            while (it.hasNext()) {
                DataHolder dataHolder = (DataHolder) this.Js.getParcelable(it.next());
                if (dataHolder != null) {
                    dataHolder.close();
                }
            }
        }
    }

    final class RequestsUpdatedBinderCallbacks extends AbstractGamesCallbacks {
        private final a.d<Requests.UpdateRequestsResult> Jt;

        public RequestsUpdatedBinderCallbacks(a.d<Requests.UpdateRequestsResult> resultHolder) {
            this.Jt = (a.d) fq.b(resultHolder, "Holder must not be null");
        }

        @Override // com.google.android.gms.games.internal.AbstractGamesCallbacks, com.google.android.gms.games.internal.IGamesCallbacks
        public void D(DataHolder dataHolder) {
            GamesClientImpl.this.a(GamesClientImpl.this.new RequestsUpdatedCallback(this.Jt, dataHolder));
        }
    }

    final class RequestsUpdatedCallback extends ResultDataHolderCallback<a.d<Requests.UpdateRequestsResult>> implements Requests.UpdateRequestsResult {
        private final RequestUpdateOutcomes Ju;

        RequestsUpdatedCallback(a.d<Requests.UpdateRequestsResult> resultHolder, DataHolder dataHolder) {
            super(resultHolder, dataHolder);
            this.Ju = RequestUpdateOutcomes.J(dataHolder);
        }

        /* JADX INFO: Access modifiers changed from: protected */
        @Override // com.google.android.gms.internal.ff.d
        public void a(a.d<Requests.UpdateRequestsResult> dVar, DataHolder dataHolder) {
            dVar.b(this);
        }

        @Override // com.google.android.gms.games.request.Requests.UpdateRequestsResult
        public Set<String> getRequestIds() {
            return this.Ju.getRequestIds();
        }

        @Override // com.google.android.gms.games.request.Requests.UpdateRequestsResult
        public int getRequestOutcome(String requestId) {
            return this.Ju.getRequestOutcome(requestId);
        }
    }

    abstract class ResultDataHolderCallback<R extends a.d<?>> extends ff<IGamesService>.d<R> implements Releasable, Result {
        final DataHolder BB;
        final Status wJ;

        public ResultDataHolderCallback(R resultHolder, DataHolder dataHolder) {
            super(resultHolder, dataHolder);
            this.wJ = new Status(dataHolder.getStatusCode());
            this.BB = dataHolder;
        }

        @Override // com.google.android.gms.common.api.Result
        public Status getStatus() {
            return this.wJ;
        }

        @Override // com.google.android.gms.common.api.Releasable
        public void release() {
            if (this.BB != null) {
                this.BB.close();
            }
        }
    }

    final class RoomAutoMatchingCallback extends AbstractRoomStatusCallback {
        RoomAutoMatchingCallback(RoomStatusUpdateListener listener, DataHolder dataHolder) {
            super(listener, dataHolder);
        }

        @Override // com.google.android.gms.games.internal.GamesClientImpl.AbstractRoomStatusCallback
        public void a(RoomStatusUpdateListener roomStatusUpdateListener, Room room) {
            roomStatusUpdateListener.onRoomAutoMatching(room);
        }
    }

    final class RoomBinderCallbacks extends AbstractGamesCallbacks {
        private final RoomUpdateListener Jv;
        private final RoomStatusUpdateListener Jw;
        private final RealTimeMessageReceivedListener Jx;

        public RoomBinderCallbacks(RoomUpdateListener roomCallbacks) {
            this.Jv = (RoomUpdateListener) fq.b(roomCallbacks, "Callbacks must not be null");
            this.Jw = null;
            this.Jx = null;
        }

        public RoomBinderCallbacks(RoomUpdateListener roomCallbacks, RoomStatusUpdateListener roomStatusCallbacks, RealTimeMessageReceivedListener realTimeMessageReceivedCallbacks) {
            this.Jv = (RoomUpdateListener) fq.b(roomCallbacks, "Callbacks must not be null");
            this.Jw = roomStatusCallbacks;
            this.Jx = realTimeMessageReceivedCallbacks;
        }

        @Override // com.google.android.gms.games.internal.AbstractGamesCallbacks, com.google.android.gms.games.internal.IGamesCallbacks
        public void a(DataHolder dataHolder, String[] strArr) {
            GamesClientImpl.this.a(GamesClientImpl.this.new PeerInvitedToRoomCallback(this.Jw, dataHolder, strArr));
        }

        @Override // com.google.android.gms.games.internal.AbstractGamesCallbacks, com.google.android.gms.games.internal.IGamesCallbacks
        public void b(DataHolder dataHolder, String[] strArr) {
            GamesClientImpl.this.a(GamesClientImpl.this.new PeerJoinedRoomCallback(this.Jw, dataHolder, strArr));
        }

        @Override // com.google.android.gms.games.internal.AbstractGamesCallbacks, com.google.android.gms.games.internal.IGamesCallbacks
        public void c(DataHolder dataHolder, String[] strArr) {
            GamesClientImpl.this.a(GamesClientImpl.this.new PeerLeftRoomCallback(this.Jw, dataHolder, strArr));
        }

        @Override // com.google.android.gms.games.internal.AbstractGamesCallbacks, com.google.android.gms.games.internal.IGamesCallbacks
        public void d(DataHolder dataHolder, String[] strArr) {
            GamesClientImpl.this.a(GamesClientImpl.this.new PeerDeclinedCallback(this.Jw, dataHolder, strArr));
        }

        @Override // com.google.android.gms.games.internal.AbstractGamesCallbacks, com.google.android.gms.games.internal.IGamesCallbacks
        public void e(DataHolder dataHolder, String[] strArr) {
            GamesClientImpl.this.a(GamesClientImpl.this.new PeerConnectedCallback(this.Jw, dataHolder, strArr));
        }

        @Override // com.google.android.gms.games.internal.AbstractGamesCallbacks, com.google.android.gms.games.internal.IGamesCallbacks
        public void f(DataHolder dataHolder, String[] strArr) {
            GamesClientImpl.this.a(GamesClientImpl.this.new PeerDisconnectedCallback(this.Jw, dataHolder, strArr));
        }

        @Override // com.google.android.gms.games.internal.AbstractGamesCallbacks, com.google.android.gms.games.internal.IGamesCallbacks
        public void onLeftRoom(int statusCode, String externalRoomId) {
            GamesClientImpl.this.a(GamesClientImpl.this.new LeftRoomCallback(this.Jv, statusCode, externalRoomId));
        }

        @Override // com.google.android.gms.games.internal.AbstractGamesCallbacks, com.google.android.gms.games.internal.IGamesCallbacks
        public void onP2PConnected(String participantId) {
            GamesClientImpl.this.a(GamesClientImpl.this.new P2PConnectedCallback(this.Jw, participantId));
        }

        @Override // com.google.android.gms.games.internal.AbstractGamesCallbacks, com.google.android.gms.games.internal.IGamesCallbacks
        public void onP2PDisconnected(String participantId) {
            GamesClientImpl.this.a(GamesClientImpl.this.new P2PDisconnectedCallback(this.Jw, participantId));
        }

        @Override // com.google.android.gms.games.internal.AbstractGamesCallbacks, com.google.android.gms.games.internal.IGamesCallbacks
        public void onRealTimeMessageReceived(RealTimeMessage message) {
            GamesClientImpl.this.a(GamesClientImpl.this.new MessageReceivedCallback(this.Jx, message));
        }

        @Override // com.google.android.gms.games.internal.AbstractGamesCallbacks, com.google.android.gms.games.internal.IGamesCallbacks
        public void s(DataHolder dataHolder) {
            GamesClientImpl.this.a(GamesClientImpl.this.new RoomCreatedCallback(this.Jv, dataHolder));
        }

        @Override // com.google.android.gms.games.internal.AbstractGamesCallbacks, com.google.android.gms.games.internal.IGamesCallbacks
        public void t(DataHolder dataHolder) {
            GamesClientImpl.this.a(GamesClientImpl.this.new JoinedRoomCallback(this.Jv, dataHolder));
        }

        @Override // com.google.android.gms.games.internal.AbstractGamesCallbacks, com.google.android.gms.games.internal.IGamesCallbacks
        public void u(DataHolder dataHolder) {
            GamesClientImpl.this.a(GamesClientImpl.this.new RoomConnectingCallback(this.Jw, dataHolder));
        }

        @Override // com.google.android.gms.games.internal.AbstractGamesCallbacks, com.google.android.gms.games.internal.IGamesCallbacks
        public void v(DataHolder dataHolder) {
            GamesClientImpl.this.a(GamesClientImpl.this.new RoomAutoMatchingCallback(this.Jw, dataHolder));
        }

        @Override // com.google.android.gms.games.internal.AbstractGamesCallbacks, com.google.android.gms.games.internal.IGamesCallbacks
        public void w(DataHolder dataHolder) {
            GamesClientImpl.this.a(GamesClientImpl.this.new RoomConnectedCallback(this.Jv, dataHolder));
        }

        @Override // com.google.android.gms.games.internal.AbstractGamesCallbacks, com.google.android.gms.games.internal.IGamesCallbacks
        public void x(DataHolder dataHolder) {
            GamesClientImpl.this.a(GamesClientImpl.this.new ConnectedToRoomCallback(this.Jw, dataHolder));
        }

        @Override // com.google.android.gms.games.internal.AbstractGamesCallbacks, com.google.android.gms.games.internal.IGamesCallbacks
        public void y(DataHolder dataHolder) {
            GamesClientImpl.this.a(GamesClientImpl.this.new DisconnectedFromRoomCallback(this.Jw, dataHolder));
        }
    }

    final class RoomConnectedCallback extends AbstractRoomCallback {
        RoomConnectedCallback(RoomUpdateListener listener, DataHolder dataHolder) {
            super(listener, dataHolder);
        }

        @Override // com.google.android.gms.games.internal.GamesClientImpl.AbstractRoomCallback
        public void a(RoomUpdateListener roomUpdateListener, Room room, int i) {
            roomUpdateListener.onRoomConnected(i, room);
        }
    }

    final class RoomConnectingCallback extends AbstractRoomStatusCallback {
        RoomConnectingCallback(RoomStatusUpdateListener listener, DataHolder dataHolder) {
            super(listener, dataHolder);
        }

        @Override // com.google.android.gms.games.internal.GamesClientImpl.AbstractRoomStatusCallback
        public void a(RoomStatusUpdateListener roomStatusUpdateListener, Room room) {
            roomStatusUpdateListener.onRoomConnecting(room);
        }
    }

    final class RoomCreatedCallback extends AbstractRoomCallback {
        public RoomCreatedCallback(RoomUpdateListener listener, DataHolder dataHolder) {
            super(listener, dataHolder);
        }

        @Override // com.google.android.gms.games.internal.GamesClientImpl.AbstractRoomCallback
        public void a(RoomUpdateListener roomUpdateListener, Room room, int i) {
            roomUpdateListener.onRoomCreated(i, room);
        }
    }

    final class SignOutCompleteBinderCallbacks extends AbstractGamesCallbacks {
        private final a.d<Status> wH;

        public SignOutCompleteBinderCallbacks(a.d<Status> resultHolder) {
            this.wH = (a.d) fq.b(resultHolder, "Holder must not be null");
        }

        @Override // com.google.android.gms.games.internal.AbstractGamesCallbacks, com.google.android.gms.games.internal.IGamesCallbacks
        public void du() {
            GamesClientImpl.this.a(GamesClientImpl.this.new SignOutCompleteCallback(this.wH, new Status(0)));
        }
    }

    final class SignOutCompleteCallback extends ff<IGamesService>.b<a.d<Status>> {
        private final Status wJ;

        public SignOutCompleteCallback(a.d<Status> resultHolder, Status status) {
            super(resultHolder);
            this.wJ = status;
        }

        @Override // com.google.android.gms.internal.ff.b
        /* JADX INFO: renamed from: c, reason: merged with bridge method [inline-methods] */
        public void a(a.d<Status> dVar) {
            dVar.b(this.wJ);
        }

        @Override // com.google.android.gms.internal.ff.b
        protected void dx() {
        }
    }

    final class SubmitScoreBinderCallbacks extends AbstractGamesCallbacks {
        private final a.d<Leaderboards.SubmitScoreResult> wH;

        public SubmitScoreBinderCallbacks(a.d<Leaderboards.SubmitScoreResult> resultHolder) {
            this.wH = (a.d) fq.b(resultHolder, "Holder must not be null");
        }

        @Override // com.google.android.gms.games.internal.AbstractGamesCallbacks, com.google.android.gms.games.internal.IGamesCallbacks
        public void d(DataHolder dataHolder) {
            GamesClientImpl.this.a(GamesClientImpl.this.new SubmitScoreCallback(this.wH, dataHolder));
        }
    }

    final class SubmitScoreCallback extends ResultDataHolderCallback<a.d<Leaderboards.SubmitScoreResult>> implements Leaderboards.SubmitScoreResult {
        private final ScoreSubmissionData Jy;

        public SubmitScoreCallback(a.d<Leaderboards.SubmitScoreResult> resultHolder, DataHolder dataHolder) {
            super(resultHolder, dataHolder);
            try {
                this.Jy = new ScoreSubmissionData(dataHolder);
            } finally {
                dataHolder.close();
            }
        }

        @Override // com.google.android.gms.internal.ff.d
        public void a(a.d<Leaderboards.SubmitScoreResult> dVar, DataHolder dataHolder) {
            dVar.b(this);
        }

        @Override // com.google.android.gms.games.leaderboard.Leaderboards.SubmitScoreResult
        public ScoreSubmissionData getScoreData() {
            return this.Jy;
        }
    }

    abstract class TurnBasedMatchCallback<T extends a.d<?>> extends ResultDataHolderCallback<T> {
        final TurnBasedMatch Jd;

        TurnBasedMatchCallback(T listener, DataHolder dataHolder) {
            super(listener, dataHolder);
            TurnBasedMatchBuffer turnBasedMatchBuffer = new TurnBasedMatchBuffer(dataHolder);
            try {
                if (turnBasedMatchBuffer.getCount() > 0) {
                    this.Jd = turnBasedMatchBuffer.get(0).freeze();
                } else {
                    this.Jd = null;
                }
            } finally {
                turnBasedMatchBuffer.close();
            }
        }

        /* JADX INFO: Access modifiers changed from: protected */
        @Override // com.google.android.gms.internal.ff.d
        public void a(T t, DataHolder dataHolder) {
            k(t);
        }

        public TurnBasedMatch getMatch() {
            return this.Jd;
        }

        abstract void k(T t);
    }

    final class TurnBasedMatchCanceledBinderCallbacks extends AbstractGamesCallbacks {
        private final a.d<TurnBasedMultiplayer.CancelMatchResult> Jz;

        public TurnBasedMatchCanceledBinderCallbacks(a.d<TurnBasedMultiplayer.CancelMatchResult> resultHolder) {
            this.Jz = (a.d) fq.b(resultHolder, "Holder must not be null");
        }

        @Override // com.google.android.gms.games.internal.AbstractGamesCallbacks, com.google.android.gms.games.internal.IGamesCallbacks
        public void f(int i, String str) {
            GamesClientImpl.this.a(GamesClientImpl.this.new TurnBasedMatchCanceledCallback(this.Jz, new Status(i), str));
        }
    }

    final class TurnBasedMatchCanceledCallback extends ff<IGamesService>.b<a.d<TurnBasedMultiplayer.CancelMatchResult>> implements TurnBasedMultiplayer.CancelMatchResult {
        private final String JA;
        private final Status wJ;

        TurnBasedMatchCanceledCallback(a.d<TurnBasedMultiplayer.CancelMatchResult> resultHolder, Status status, String externalMatchId) {
            super(resultHolder);
            this.wJ = status;
            this.JA = externalMatchId;
        }

        @Override // com.google.android.gms.internal.ff.b
        /* JADX INFO: renamed from: c, reason: merged with bridge method [inline-methods] */
        public void a(a.d<TurnBasedMultiplayer.CancelMatchResult> dVar) {
            dVar.b(this);
        }

        @Override // com.google.android.gms.internal.ff.b
        protected void dx() {
        }

        @Override // com.google.android.gms.games.multiplayer.turnbased.TurnBasedMultiplayer.CancelMatchResult
        public String getMatchId() {
            return this.JA;
        }

        @Override // com.google.android.gms.common.api.Result
        public Status getStatus() {
            return this.wJ;
        }
    }

    final class TurnBasedMatchInitiatedBinderCallbacks extends AbstractGamesCallbacks {
        private final a.d<TurnBasedMultiplayer.InitiateMatchResult> JB;

        public TurnBasedMatchInitiatedBinderCallbacks(a.d<TurnBasedMultiplayer.InitiateMatchResult> resultHolder) {
            this.JB = (a.d) fq.b(resultHolder, "Holder must not be null");
        }

        @Override // com.google.android.gms.games.internal.AbstractGamesCallbacks, com.google.android.gms.games.internal.IGamesCallbacks
        public void o(DataHolder dataHolder) {
            GamesClientImpl.this.a(GamesClientImpl.this.new TurnBasedMatchInitiatedCallback(this.JB, dataHolder));
        }
    }

    final class TurnBasedMatchInitiatedCallback extends TurnBasedMatchCallback<a.d<TurnBasedMultiplayer.InitiateMatchResult>> implements TurnBasedMultiplayer.InitiateMatchResult {
        TurnBasedMatchInitiatedCallback(a.d<TurnBasedMultiplayer.InitiateMatchResult> resultHolder, DataHolder dataHolder) {
            super(resultHolder, dataHolder);
        }

        @Override // com.google.android.gms.games.internal.GamesClientImpl.TurnBasedMatchCallback
        protected void k(a.d<TurnBasedMultiplayer.InitiateMatchResult> dVar) {
            dVar.b(this);
        }
    }

    final class TurnBasedMatchLeftBinderCallbacks extends AbstractGamesCallbacks {
        private final a.d<TurnBasedMultiplayer.LeaveMatchResult> JC;

        public TurnBasedMatchLeftBinderCallbacks(a.d<TurnBasedMultiplayer.LeaveMatchResult> resultHolder) {
            this.JC = (a.d) fq.b(resultHolder, "Holder must not be null");
        }

        @Override // com.google.android.gms.games.internal.AbstractGamesCallbacks, com.google.android.gms.games.internal.IGamesCallbacks
        public void q(DataHolder dataHolder) {
            GamesClientImpl.this.a(GamesClientImpl.this.new TurnBasedMatchLeftCallback(this.JC, dataHolder));
        }
    }

    final class TurnBasedMatchLeftCallback extends TurnBasedMatchCallback<a.d<TurnBasedMultiplayer.LeaveMatchResult>> implements TurnBasedMultiplayer.LeaveMatchResult {
        TurnBasedMatchLeftCallback(a.d<TurnBasedMultiplayer.LeaveMatchResult> resultHolder, DataHolder dataHolder) {
            super(resultHolder, dataHolder);
        }

        @Override // com.google.android.gms.games.internal.GamesClientImpl.TurnBasedMatchCallback
        protected void k(a.d<TurnBasedMultiplayer.LeaveMatchResult> dVar) {
            dVar.b(this);
        }
    }

    final class TurnBasedMatchLoadedBinderCallbacks extends AbstractGamesCallbacks {
        private final a.d<TurnBasedMultiplayer.LoadMatchResult> JD;

        public TurnBasedMatchLoadedBinderCallbacks(a.d<TurnBasedMultiplayer.LoadMatchResult> resultHolder) {
            this.JD = (a.d) fq.b(resultHolder, "Holder must not be null");
        }

        @Override // com.google.android.gms.games.internal.AbstractGamesCallbacks, com.google.android.gms.games.internal.IGamesCallbacks
        public void n(DataHolder dataHolder) {
            GamesClientImpl.this.a(GamesClientImpl.this.new TurnBasedMatchLoadedCallback(this.JD, dataHolder));
        }
    }

    final class TurnBasedMatchLoadedCallback extends TurnBasedMatchCallback<a.d<TurnBasedMultiplayer.LoadMatchResult>> implements TurnBasedMultiplayer.LoadMatchResult {
        TurnBasedMatchLoadedCallback(a.d<TurnBasedMultiplayer.LoadMatchResult> resultHolder, DataHolder dataHolder) {
            super(resultHolder, dataHolder);
        }

        @Override // com.google.android.gms.games.internal.GamesClientImpl.TurnBasedMatchCallback
        protected void k(a.d<TurnBasedMultiplayer.LoadMatchResult> dVar) {
            dVar.b(this);
        }
    }

    final class TurnBasedMatchUpdatedBinderCallbacks extends AbstractGamesCallbacks {
        private final a.d<TurnBasedMultiplayer.UpdateMatchResult> JE;

        public TurnBasedMatchUpdatedBinderCallbacks(a.d<TurnBasedMultiplayer.UpdateMatchResult> resultHolder) {
            this.JE = (a.d) fq.b(resultHolder, "Holder must not be null");
        }

        @Override // com.google.android.gms.games.internal.AbstractGamesCallbacks, com.google.android.gms.games.internal.IGamesCallbacks
        public void p(DataHolder dataHolder) {
            GamesClientImpl.this.a(GamesClientImpl.this.new TurnBasedMatchUpdatedCallback(this.JE, dataHolder));
        }
    }

    final class TurnBasedMatchUpdatedCallback extends TurnBasedMatchCallback<a.d<TurnBasedMultiplayer.UpdateMatchResult>> implements TurnBasedMultiplayer.UpdateMatchResult {
        TurnBasedMatchUpdatedCallback(a.d<TurnBasedMultiplayer.UpdateMatchResult> resultHolder, DataHolder dataHolder) {
            super(resultHolder, dataHolder);
        }

        @Override // com.google.android.gms.games.internal.GamesClientImpl.TurnBasedMatchCallback
        protected void k(a.d<TurnBasedMultiplayer.UpdateMatchResult> dVar) {
            dVar.b(this);
        }
    }

    final class TurnBasedMatchesLoadedBinderCallbacks extends AbstractGamesCallbacks {
        private final a.d<TurnBasedMultiplayer.LoadMatchesResult> JF;

        public TurnBasedMatchesLoadedBinderCallbacks(a.d<TurnBasedMultiplayer.LoadMatchesResult> resultHolder) {
            this.JF = (a.d) fq.b(resultHolder, "Holder must not be null");
        }

        @Override // com.google.android.gms.games.internal.AbstractGamesCallbacks, com.google.android.gms.games.internal.IGamesCallbacks
        public void a(int i, Bundle bundle) {
            bundle.setClassLoader(getClass().getClassLoader());
            GamesClientImpl.this.a(GamesClientImpl.this.new TurnBasedMatchesLoadedCallback(this.JF, new Status(i), bundle));
        }
    }

    final class TurnBasedMatchesLoadedCallback extends ff<IGamesService>.b<a.d<TurnBasedMultiplayer.LoadMatchesResult>> implements TurnBasedMultiplayer.LoadMatchesResult {
        private final LoadMatchesResponse JG;
        private final Status wJ;

        TurnBasedMatchesLoadedCallback(a.d<TurnBasedMultiplayer.LoadMatchesResult> resultHolder, Status status, Bundle matchData) {
            super(resultHolder);
            this.wJ = status;
            this.JG = new LoadMatchesResponse(matchData);
        }

        /* JADX INFO: Access modifiers changed from: protected */
        @Override // com.google.android.gms.internal.ff.b
        /* JADX INFO: renamed from: c, reason: merged with bridge method [inline-methods] */
        public void a(a.d<TurnBasedMultiplayer.LoadMatchesResult> dVar) {
            dVar.b(this);
        }

        @Override // com.google.android.gms.internal.ff.b
        protected void dx() {
        }

        @Override // com.google.android.gms.games.multiplayer.turnbased.TurnBasedMultiplayer.LoadMatchesResult
        public LoadMatchesResponse getMatches() {
            return this.JG;
        }

        @Override // com.google.android.gms.common.api.Result
        public Status getStatus() {
            return this.wJ;
        }

        @Override // com.google.android.gms.common.api.Releasable
        public void release() {
            this.JG.close();
        }
    }

    public GamesClientImpl(Context context, Looper looper, String gamePackageName, String accountName, GoogleApiClient.ConnectionCallbacks connectedListener, GoogleApiClient.OnConnectionFailedListener connectionFailedListener, String[] scopes, int gravity, View gamesContentView, boolean isHeadless, boolean showConnectingPopup, int connectingPopupGravity, boolean retryingSignIn, int sdkVariant) {
        super(context, looper, connectedListener, connectionFailedListener, scopes);
        this.Iz = false;
        this.IA = false;
        this.Iu = gamePackageName;
        this.wG = (String) fq.f(accountName);
        this.IC = new Binder();
        this.Iv = new HashMap();
        this.Iy = PopupManager.a(this, gravity);
        f(gamesContentView);
        this.IA = showConnectingPopup;
        this.IB = connectingPopupGravity;
        this.IE = hashCode();
        this.IF = isHeadless;
        this.IH = retryingSignIn;
        this.IG = sdkVariant;
        registerConnectionCallbacks(this);
        registerConnectionFailedListener((GoogleApiClient.OnConnectionFailedListener) this);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public Room G(DataHolder dataHolder) {
        RoomBuffer roomBuffer = new RoomBuffer(dataHolder);
        try {
            return roomBuffer.getCount() > 0 ? roomBuffer.get(0).freeze() : null;
        } finally {
            roomBuffer.close();
        }
    }

    private RealTimeSocket aC(String str) {
        RealTimeSocket realTimeSocketImpl;
        try {
            ParcelFileDescriptor parcelFileDescriptorAJ = eM().aJ(str);
            if (parcelFileDescriptorAJ != null) {
                GamesLog.f("GamesClientImpl", "Created native libjingle socket.");
                realTimeSocketImpl = new LibjingleNativeSocket(parcelFileDescriptorAJ);
                this.Iv.put(str, realTimeSocketImpl);
            } else {
                GamesLog.f("GamesClientImpl", "Unable to create native libjingle socket, resorting to old socket.");
                String strAE = eM().aE(str);
                if (strAE == null) {
                    realTimeSocketImpl = null;
                } else {
                    LocalSocket localSocket = new LocalSocket();
                    try {
                        localSocket.connect(new LocalSocketAddress(strAE));
                        realTimeSocketImpl = new RealTimeSocketImpl(localSocket, str);
                        this.Iv.put(str, realTimeSocketImpl);
                    } catch (IOException e) {
                        GamesLog.h("GamesClientImpl", "connect() call failed on socket: " + e.getMessage());
                        realTimeSocketImpl = null;
                    }
                }
            }
            return realTimeSocketImpl;
        } catch (RemoteException e2) {
            GamesLog.h("GamesClientImpl", "Unable to create socket. Service died.");
            return null;
        }
    }

    private void gE() {
        Iterator<RealTimeSocket> it = this.Iv.values().iterator();
        while (it.hasNext()) {
            try {
                it.next().close();
            } catch (IOException e) {
                GamesLog.a("GamesClientImpl", "IOException:", e);
            }
        }
        this.Iv.clear();
    }

    private void gk() {
        this.Iw = null;
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.google.android.gms.internal.ff
    /* JADX INFO: renamed from: L, reason: merged with bridge method [inline-methods] */
    public IGamesService r(IBinder iBinder) {
        return IGamesService.Stub.N(iBinder);
    }

    public int a(RealTimeMultiplayer.ReliableMessageSentCallback reliableMessageSentCallback, byte[] bArr, String str, String str2) {
        try {
            return eM().a(new RealTimeReliableMessageBinderCallbacks(reliableMessageSentCallback), bArr, str, str2);
        } catch (RemoteException e) {
            GamesLog.g("GamesClientImpl", "service died");
            return -1;
        }
    }

    public int a(byte[] bArr, String str, String[] strArr) {
        fq.b(strArr, "Participant IDs must not be null");
        try {
            return eM().b(bArr, str, strArr);
        } catch (RemoteException e) {
            GamesLog.g("GamesClientImpl", "service died");
            return -1;
        }
    }

    public Intent a(int i, int i2, boolean z) {
        try {
            return eM().a(i, i2, z);
        } catch (RemoteException e) {
            GamesLog.g("GamesClientImpl", "service died");
            return null;
        }
    }

    public Intent a(int i, byte[] bArr, int i2, Bitmap bitmap, String str) {
        try {
            Intent intentA = eM().a(i, bArr, i2, str);
            fq.b(bitmap, "Must provide a non null icon");
            intentA.putExtra("com.google.android.gms.games.REQUEST_ITEM_ICON", bitmap);
            return intentA;
        } catch (RemoteException e) {
            GamesLog.g("GamesClientImpl", "service died");
            return null;
        }
    }

    public Intent a(Room room, int i) {
        try {
            return eM().a((RoomEntity) room.freeze(), i);
        } catch (RemoteException e) {
            GamesLog.g("GamesClientImpl", "service died");
            return null;
        }
    }

    @Override // com.google.android.gms.internal.ff
    protected void a(int i, IBinder iBinder, Bundle bundle) {
        if (i == 0 && bundle != null) {
            this.Iz = bundle.getBoolean("show_welcome_popup");
        }
        super.a(i, iBinder, bundle);
    }

    public void a(IBinder iBinder, Bundle bundle) {
        if (isConnected()) {
            try {
                eM().a(iBinder, bundle);
            } catch (RemoteException e) {
                GamesLog.g("GamesClientImpl", "service died");
            }
        }
    }

    public void a(a.d<Requests.LoadRequestsResult> dVar, int i, int i2, int i3) {
        try {
            eM().a(new RequestsLoadedBinderCallbacks(dVar), i, i2, i3);
        } catch (RemoteException e) {
            GamesLog.g("GamesClientImpl", "service died");
        }
    }

    public void a(a.d<GamesMetadata.LoadExtendedGamesResult> dVar, int i, int i2, boolean z, boolean z2) {
        try {
            eM().a(new ExtendedGamesLoadedBinderCallback(dVar), i, i2, z, z2);
        } catch (RemoteException e) {
            GamesLog.g("GamesClientImpl", "service died");
        }
    }

    public void a(a.d<Players.LoadPlayersResult> dVar, int i, boolean z, boolean z2) {
        try {
            eM().a(new PlayersLoadedBinderCallback(dVar), i, z, z2);
        } catch (RemoteException e) {
            GamesLog.g("GamesClientImpl", "service died");
        }
    }

    public void a(a.d<TurnBasedMultiplayer.LoadMatchesResult> dVar, int i, int[] iArr) {
        try {
            eM().a(new TurnBasedMatchesLoadedBinderCallbacks(dVar), i, iArr);
        } catch (RemoteException e) {
            GamesLog.g("GamesClientImpl", "service died");
        }
    }

    public void a(a.d<Leaderboards.LoadScoresResult> dVar, LeaderboardScoreBuffer leaderboardScoreBuffer, int i, int i2) {
        try {
            eM().a(new LeaderboardScoresLoadedBinderCallback(dVar), leaderboardScoreBuffer.hD().hE(), i, i2);
        } catch (RemoteException e) {
            GamesLog.g("GamesClientImpl", "service died");
        }
    }

    public void a(a.d<TurnBasedMultiplayer.InitiateMatchResult> dVar, TurnBasedMatchConfig turnBasedMatchConfig) {
        try {
            eM().a(new TurnBasedMatchInitiatedBinderCallbacks(dVar), turnBasedMatchConfig.getVariant(), turnBasedMatchConfig.getMinPlayers(), turnBasedMatchConfig.getInvitedPlayerIds(), turnBasedMatchConfig.getAutoMatchCriteria());
        } catch (RemoteException e) {
            GamesLog.g("GamesClientImpl", "service died");
        }
    }

    public void a(a.d<Players.LoadPlayersResult> dVar, String str) {
        try {
            eM().a(new PlayersLoadedBinderCallback(dVar), str);
        } catch (RemoteException e) {
            GamesLog.g("GamesClientImpl", "service died");
        }
    }

    public void a(a.d<Achievements.UpdateAchievementResult> dVar, String str, int i) {
        AchievementUpdatedBinderCallback achievementUpdatedBinderCallback;
        if (dVar == null) {
            achievementUpdatedBinderCallback = null;
        } else {
            try {
                achievementUpdatedBinderCallback = new AchievementUpdatedBinderCallback(dVar);
            } catch (RemoteException e) {
                GamesLog.g("GamesClientImpl", "service died");
                return;
            }
        }
        eM().a(achievementUpdatedBinderCallback, str, i, this.Iy.gU(), this.Iy.gT());
    }

    public void a(a.d<Leaderboards.LoadScoresResult> dVar, String str, int i, int i2, int i3, boolean z) {
        try {
            eM().a(new LeaderboardScoresLoadedBinderCallback(dVar), str, i, i2, i3, z);
        } catch (RemoteException e) {
            GamesLog.g("GamesClientImpl", "service died");
        }
    }

    public void a(a.d<Players.LoadPlayersResult> dVar, String str, int i, boolean z) {
        try {
            eM().a(new PlayersLoadedBinderCallback(dVar), str, i, z);
        } catch (RemoteException e) {
            GamesLog.g("GamesClientImpl", "service died");
        }
    }

    public void a(a.d<Players.LoadPlayersResult> dVar, String str, int i, boolean z, boolean z2) {
        if (!str.equals("playedWith")) {
            throw new IllegalArgumentException("Invalid player collection: " + str);
        }
        try {
            eM().d(new PlayersLoadedBinderCallback(dVar), str, i, z, z2);
        } catch (RemoteException e) {
            GamesLog.g("GamesClientImpl", "service died");
        }
    }

    public void a(a.d<GamesMetadata.LoadExtendedGamesResult> dVar, String str, int i, boolean z, boolean z2, boolean z3, boolean z4) {
        try {
            eM().a(new ExtendedGamesLoadedBinderCallback(dVar), str, i, z, z2, z3, z4);
        } catch (RemoteException e) {
            GamesLog.g("GamesClientImpl", "service died");
        }
    }

    public void a(a.d<TurnBasedMultiplayer.LoadMatchesResult> dVar, String str, int i, int[] iArr) {
        try {
            eM().a(new TurnBasedMatchesLoadedBinderCallbacks(dVar), str, i, iArr);
        } catch (RemoteException e) {
            GamesLog.g("GamesClientImpl", "service died");
        }
    }

    public void a(a.d<Leaderboards.SubmitScoreResult> dVar, String str, long j, String str2) {
        SubmitScoreBinderCallbacks submitScoreBinderCallbacks;
        if (dVar == null) {
            submitScoreBinderCallbacks = null;
        } else {
            try {
                submitScoreBinderCallbacks = new SubmitScoreBinderCallbacks(dVar);
            } catch (RemoteException e) {
                GamesLog.g("GamesClientImpl", "service died");
                return;
            }
        }
        eM().a(submitScoreBinderCallbacks, str, j, str2);
    }

    public void a(a.d<TurnBasedMultiplayer.LeaveMatchResult> dVar, String str, String str2) {
        try {
            eM().c(new TurnBasedMatchLeftBinderCallbacks(dVar), str, str2);
        } catch (RemoteException e) {
            GamesLog.g("GamesClientImpl", "service died");
        }
    }

    public void a(a.d<Leaderboards.LoadPlayerScoreResult> dVar, String str, String str2, int i, int i2) {
        try {
            eM().a(new PlayerLeaderboardScoreLoadedBinderCallback(dVar), str, str2, i, i2);
        } catch (RemoteException e) {
            GamesLog.g("GamesClientImpl", "service died");
        }
    }

    public void a(a.d<Requests.LoadRequestsResult> dVar, String str, String str2, int i, int i2, int i3) {
        try {
            eM().a(new RequestsLoadedBinderCallbacks(dVar), str, str2, i, i2, i3);
        } catch (RemoteException e) {
            GamesLog.g("GamesClientImpl", "service died");
        }
    }

    public void a(a.d<Leaderboards.LoadScoresResult> dVar, String str, String str2, int i, int i2, int i3, boolean z) {
        try {
            eM().a(new LeaderboardScoresLoadedBinderCallback(dVar), str, str2, i, i2, i3, z);
        } catch (RemoteException e) {
            GamesLog.g("GamesClientImpl", "service died");
        }
    }

    public void a(a.d<Players.LoadPlayersResult> dVar, String str, String str2, int i, boolean z, boolean z2) {
        if (!str.equals("playedWith") && !str.equals("circled")) {
            throw new IllegalArgumentException("Invalid player collection: " + str);
        }
        try {
            eM().a(new PlayersLoadedBinderCallback(dVar), str, str2, i, z, z2);
        } catch (RemoteException e) {
            GamesLog.g("GamesClientImpl", "service died");
        }
    }

    public void a(a.d<Leaderboards.LeaderboardMetadataResult> dVar, String str, String str2, boolean z) {
        try {
            eM().b(new LeaderboardsLoadedBinderCallback(dVar), str, str2, z);
        } catch (RemoteException e) {
            GamesLog.g("GamesClientImpl", "service died");
        }
    }

    public void a(a.d<Requests.UpdateRequestsResult> dVar, String str, String str2, String[] strArr) {
        try {
            eM().a(new RequestsUpdatedBinderCallbacks(dVar), str, str2, strArr);
        } catch (RemoteException e) {
            GamesLog.g("GamesClientImpl", "service died");
        }
    }

    public void a(a.d<Leaderboards.LeaderboardMetadataResult> dVar, String str, boolean z) {
        try {
            eM().c(new LeaderboardsLoadedBinderCallback(dVar), str, z);
        } catch (RemoteException e) {
            GamesLog.g("GamesClientImpl", "service died");
        }
    }

    public void a(a.d<TurnBasedMultiplayer.UpdateMatchResult> dVar, String str, byte[] bArr, String str2, ParticipantResult[] participantResultArr) {
        try {
            eM().a(new TurnBasedMatchUpdatedBinderCallbacks(dVar), str, bArr, str2, participantResultArr);
        } catch (RemoteException e) {
            GamesLog.g("GamesClientImpl", "service died");
        }
    }

    public void a(a.d<TurnBasedMultiplayer.UpdateMatchResult> dVar, String str, byte[] bArr, ParticipantResult[] participantResultArr) {
        try {
            eM().a(new TurnBasedMatchUpdatedBinderCallbacks(dVar), str, bArr, participantResultArr);
        } catch (RemoteException e) {
            GamesLog.g("GamesClientImpl", "service died");
        }
    }

    public void a(a.d<Requests.SendRequestResult> dVar, String str, String[] strArr, int i, byte[] bArr, int i2) {
        try {
            eM().a(new RequestSentBinderCallbacks(dVar), str, strArr, i, bArr, i2);
        } catch (RemoteException e) {
            GamesLog.g("GamesClientImpl", "service died");
        }
    }

    public void a(a.d<Players.LoadPlayersResult> dVar, boolean z) {
        try {
            eM().c(new PlayersLoadedBinderCallback(dVar), z);
        } catch (RemoteException e) {
            GamesLog.g("GamesClientImpl", "service died");
        }
    }

    public void a(a.d<Status> dVar, boolean z, Bundle bundle) {
        try {
            eM().a(new ContactSettingsUpdatedBinderCallback(dVar), z, bundle);
        } catch (RemoteException e) {
            GamesLog.g("GamesClientImpl", "service died");
        }
    }

    public void a(a.d<Players.LoadPlayersResult> dVar, String[] strArr) {
        try {
            eM().c(new PlayersLoadedBinderCallback(dVar), strArr);
        } catch (RemoteException e) {
            GamesLog.g("GamesClientImpl", "service died");
        }
    }

    public void a(OnInvitationReceivedListener onInvitationReceivedListener) {
        try {
            eM().a(new InvitationReceivedBinderCallback(onInvitationReceivedListener), this.IE);
        } catch (RemoteException e) {
            GamesLog.g("GamesClientImpl", "service died");
        }
    }

    public void a(RoomConfig roomConfig) {
        try {
            eM().a(new RoomBinderCallbacks(roomConfig.getRoomUpdateListener(), roomConfig.getRoomStatusUpdateListener(), roomConfig.getMessageReceivedListener()), this.IC, roomConfig.getVariant(), roomConfig.getInvitedPlayerIds(), roomConfig.getAutoMatchCriteria(), roomConfig.isSocketEnabled(), this.IE);
        } catch (RemoteException e) {
            GamesLog.g("GamesClientImpl", "service died");
        }
    }

    public void a(RoomUpdateListener roomUpdateListener, String str) {
        try {
            eM().c(new RoomBinderCallbacks(roomUpdateListener), str);
            gE();
        } catch (RemoteException e) {
            GamesLog.g("GamesClientImpl", "service died");
        }
    }

    public void a(OnTurnBasedMatchUpdateReceivedListener onTurnBasedMatchUpdateReceivedListener) {
        try {
            eM().b(new MatchUpdateReceivedBinderCallback(onTurnBasedMatchUpdateReceivedListener), this.IE);
        } catch (RemoteException e) {
            GamesLog.g("GamesClientImpl", "service died");
        }
    }

    public void a(OnRequestReceivedListener onRequestReceivedListener) {
        try {
            eM().c(new RequestReceivedBinderCallback(onRequestReceivedListener), this.IE);
        } catch (RemoteException e) {
            GamesLog.g("GamesClientImpl", "service died");
        }
    }

    @Override // com.google.android.gms.internal.ff
    protected void a(fm fmVar, ff.e eVar) throws RemoteException {
        String string = getContext().getResources().getConfiguration().locale.toString();
        Bundle bundle = new Bundle();
        bundle.putBoolean("com.google.android.gms.games.key.isHeadless", this.IF);
        bundle.putBoolean("com.google.android.gms.games.key.showConnectingPopup", this.IA);
        bundle.putInt("com.google.android.gms.games.key.connectingPopupGravity", this.IB);
        bundle.putBoolean("com.google.android.gms.games.key.retryingSignIn", this.IH);
        bundle.putInt("com.google.android.gms.games.key.sdkVariant", this.IG);
        fmVar.a(eVar, GooglePlayServicesUtil.GOOGLE_PLAY_SERVICES_VERSION_CODE, getContext().getPackageName(), this.wG, eL(), this.Iu, this.Iy.gU(), string, bundle);
    }

    public Intent aA(String str) {
        try {
            return eM().aA(str);
        } catch (RemoteException e) {
            GamesLog.g("GamesClientImpl", "service died");
            return null;
        }
    }

    public void aB(String str) {
        try {
            eM().aI(str);
        } catch (RemoteException e) {
            GamesLog.g("GamesClientImpl", "service died");
        }
    }

    public void aX(int i) {
        this.Iy.setGravity(i);
    }

    public void aY(int i) {
        try {
            eM().aY(i);
        } catch (RemoteException e) {
            GamesLog.g("GamesClientImpl", "service died");
        }
    }

    public Intent b(int i, int i2, boolean z) {
        try {
            return eM().b(i, i2, z);
        } catch (RemoteException e) {
            GamesLog.g("GamesClientImpl", "service died");
            return null;
        }
    }

    public void b(a.d<Status> dVar) {
        try {
            eM().a(new SignOutCompleteBinderCallbacks(dVar));
        } catch (RemoteException e) {
            GamesLog.g("GamesClientImpl", "service died");
        }
    }

    public void b(a.d<Players.LoadPlayersResult> dVar, int i, boolean z, boolean z2) {
        try {
            eM().b(new PlayersLoadedBinderCallback(dVar), i, z, z2);
        } catch (RemoteException e) {
            GamesLog.g("GamesClientImpl", "service died");
        }
    }

    public void b(a.d<Achievements.UpdateAchievementResult> dVar, String str) {
        AchievementUpdatedBinderCallback achievementUpdatedBinderCallback;
        if (dVar == null) {
            achievementUpdatedBinderCallback = null;
        } else {
            try {
                achievementUpdatedBinderCallback = new AchievementUpdatedBinderCallback(dVar);
            } catch (RemoteException e) {
                GamesLog.g("GamesClientImpl", "service died");
                return;
            }
        }
        eM().a(achievementUpdatedBinderCallback, str, this.Iy.gU(), this.Iy.gT());
    }

    public void b(a.d<Achievements.UpdateAchievementResult> dVar, String str, int i) {
        AchievementUpdatedBinderCallback achievementUpdatedBinderCallback;
        if (dVar == null) {
            achievementUpdatedBinderCallback = null;
        } else {
            try {
                achievementUpdatedBinderCallback = new AchievementUpdatedBinderCallback(dVar);
            } catch (RemoteException e) {
                GamesLog.g("GamesClientImpl", "service died");
                return;
            }
        }
        eM().b(achievementUpdatedBinderCallback, str, i, this.Iy.gU(), this.Iy.gT());
    }

    public void b(a.d<Leaderboards.LoadScoresResult> dVar, String str, int i, int i2, int i3, boolean z) {
        try {
            eM().b(new LeaderboardScoresLoadedBinderCallback(dVar), str, i, i2, i3, z);
        } catch (RemoteException e) {
            GamesLog.g("GamesClientImpl", "service died");
        }
    }

    public void b(a.d<GamesMetadata.LoadExtendedGamesResult> dVar, String str, int i, boolean z, boolean z2) {
        try {
            eM().a(new ExtendedGamesLoadedBinderCallback(dVar), str, i, z, z2);
        } catch (RemoteException e) {
            GamesLog.g("GamesClientImpl", "service died");
        }
    }

    public void b(a.d<TurnBasedMultiplayer.InitiateMatchResult> dVar, String str, String str2) {
        try {
            eM().d(new TurnBasedMatchInitiatedBinderCallbacks(dVar), str, str2);
        } catch (RemoteException e) {
            GamesLog.g("GamesClientImpl", "service died");
        }
    }

    public void b(a.d<Leaderboards.LoadScoresResult> dVar, String str, String str2, int i, int i2, int i3, boolean z) {
        try {
            eM().b(new LeaderboardScoresLoadedBinderCallback(dVar), str, str2, i, i2, i3, z);
        } catch (RemoteException e) {
            GamesLog.g("GamesClientImpl", "service died");
        }
    }

    public void b(a.d<Achievements.LoadAchievementsResult> dVar, String str, String str2, boolean z) {
        try {
            eM().a(new AchievementsLoadedBinderCallback(dVar), str, str2, z);
        } catch (RemoteException e) {
            GamesLog.g("GamesClientImpl", "service died");
        }
    }

    public void b(a.d<Leaderboards.LeaderboardMetadataResult> dVar, String str, boolean z) {
        try {
            eM().d(new LeaderboardsLoadedBinderCallback(dVar), str, z);
        } catch (RemoteException e) {
            GamesLog.g("GamesClientImpl", "service died");
        }
    }

    public void b(a.d<Leaderboards.LeaderboardMetadataResult> dVar, boolean z) {
        try {
            eM().b(new LeaderboardsLoadedBinderCallback(dVar), z);
        } catch (RemoteException e) {
            GamesLog.g("GamesClientImpl", "service died");
        }
    }

    public void b(a.d<Requests.UpdateRequestsResult> dVar, String[] strArr) {
        try {
            eM().a(new RequestsUpdatedBinderCallbacks(dVar), strArr);
        } catch (RemoteException e) {
            GamesLog.g("GamesClientImpl", "service died");
        }
    }

    public void b(RoomConfig roomConfig) {
        try {
            eM().a(new RoomBinderCallbacks(roomConfig.getRoomUpdateListener(), roomConfig.getRoomStatusUpdateListener(), roomConfig.getMessageReceivedListener()), this.IC, roomConfig.getInvitationId(), roomConfig.isSocketEnabled(), this.IE);
        } catch (RemoteException e) {
            GamesLog.g("GamesClientImpl", "service died");
        }
    }

    @Override // com.google.android.gms.internal.ff
    protected void b(String... strArr) {
        boolean z = false;
        boolean z2 = false;
        for (String str : strArr) {
            if (str.equals(Scopes.GAMES)) {
                z2 = true;
            } else if (str.equals("https://www.googleapis.com/auth/games.firstparty")) {
                z = true;
            }
        }
        if (z) {
            fq.a(!z2, String.format("Cannot have both %s and %s!", Scopes.GAMES, "https://www.googleapis.com/auth/games.firstparty"));
        } else {
            fq.a(z2, String.format("Games APIs requires %s to function.", Scopes.GAMES));
        }
    }

    @Override // com.google.android.gms.internal.ff
    protected String bg() {
        return "com.google.android.gms.games.service.START";
    }

    @Override // com.google.android.gms.internal.ff
    protected String bh() {
        return "com.google.android.gms.games.internal.IGamesService";
    }

    public void c(a.d<Invitations.LoadInvitationsResult> dVar, int i) {
        try {
            eM().a((IGamesCallbacks) new InvitationsLoadedBinderCallback(dVar), i);
        } catch (RemoteException e) {
            GamesLog.g("GamesClientImpl", "service died");
        }
    }

    public void c(a.d<Players.LoadPlayersResult> dVar, int i, boolean z, boolean z2) {
        try {
            eM().c(new PlayersLoadedBinderCallback(dVar), i, z, z2);
        } catch (RemoteException e) {
            GamesLog.g("GamesClientImpl", "service died");
        }
    }

    public void c(a.d<Achievements.UpdateAchievementResult> dVar, String str) {
        AchievementUpdatedBinderCallback achievementUpdatedBinderCallback;
        if (dVar == null) {
            achievementUpdatedBinderCallback = null;
        } else {
            try {
                achievementUpdatedBinderCallback = new AchievementUpdatedBinderCallback(dVar);
            } catch (RemoteException e) {
                GamesLog.g("GamesClientImpl", "service died");
                return;
            }
        }
        eM().b(achievementUpdatedBinderCallback, str, this.Iy.gU(), this.Iy.gT());
    }

    public void c(a.d<Invitations.LoadInvitationsResult> dVar, String str, int i) {
        try {
            eM().b((IGamesCallbacks) new InvitationsLoadedBinderCallback(dVar), str, i, false);
        } catch (RemoteException e) {
            GamesLog.g("GamesClientImpl", "service died");
        }
    }

    public void c(a.d<GamesMetadata.LoadExtendedGamesResult> dVar, String str, int i, boolean z, boolean z2) {
        try {
            eM().c(new ExtendedGamesLoadedBinderCallback(dVar), str, i, z, z2);
        } catch (RemoteException e) {
            GamesLog.g("GamesClientImpl", "service died");
        }
    }

    public void c(a.d<TurnBasedMultiplayer.InitiateMatchResult> dVar, String str, String str2) {
        try {
            eM().e(new TurnBasedMatchInitiatedBinderCallbacks(dVar), str, str2);
        } catch (RemoteException e) {
            GamesLog.g("GamesClientImpl", "service died");
        }
    }

    public void c(a.d<Notifications.GameMuteStatusChangeResult> dVar, String str, boolean z) {
        try {
            eM().a(new GameMuteStatusChangedBinderCallback(dVar), str, z);
        } catch (RemoteException e) {
            GamesLog.g("GamesClientImpl", "service died");
        }
    }

    public void c(a.d<Achievements.LoadAchievementsResult> dVar, boolean z) {
        try {
            eM().a(new AchievementsLoadedBinderCallback(dVar), z);
        } catch (RemoteException e) {
            GamesLog.g("GamesClientImpl", "service died");
        }
    }

    public void c(a.d<Requests.UpdateRequestsResult> dVar, String[] strArr) {
        try {
            eM().b(new RequestsUpdatedBinderCallbacks(dVar), strArr);
        } catch (RemoteException e) {
            GamesLog.g("GamesClientImpl", "service died");
        }
    }

    @Override // com.google.android.gms.internal.ff, com.google.android.gms.common.GooglePlayServicesClient, com.google.android.gms.common.api.Api.a
    public void connect() {
        gk();
        super.connect();
    }

    public int d(byte[] bArr, String str) {
        try {
            return eM().b(bArr, str, (String[]) null);
        } catch (RemoteException e) {
            GamesLog.g("GamesClientImpl", "service died");
            return -1;
        }
    }

    public void d(a.d<Players.LoadPlayersResult> dVar, int i, boolean z, boolean z2) {
        try {
            eM().e(new PlayersLoadedBinderCallback(dVar), i, z, z2);
        } catch (RemoteException e) {
            GamesLog.g("GamesClientImpl", "service died");
        }
    }

    public void d(a.d<TurnBasedMultiplayer.InitiateMatchResult> dVar, String str) {
        try {
            eM().l(new TurnBasedMatchInitiatedBinderCallbacks(dVar), str);
        } catch (RemoteException e) {
            GamesLog.g("GamesClientImpl", "service died");
        }
    }

    public void d(a.d<Requests.LoadRequestSummariesResult> dVar, String str, int i) {
        try {
            eM().a((IGamesCallbacks) new RequestSummariesLoadedBinderCallbacks(dVar), str, i);
        } catch (RemoteException e) {
            GamesLog.g("GamesClientImpl", "service died");
        }
    }

    public void d(a.d<Players.LoadPlayersResult> dVar, String str, int i, boolean z, boolean z2) {
        try {
            eM().b(new PlayersLoadedBinderCallback(dVar), str, i, z, z2);
        } catch (RemoteException e) {
            GamesLog.g("GamesClientImpl", "service died");
        }
    }

    @Override // com.google.android.gms.internal.ff, com.google.android.gms.internal.fg.b
    public Bundle dG() {
        try {
            Bundle bundleDG = eM().dG();
            if (bundleDG == null) {
                return bundleDG;
            }
            bundleDG.setClassLoader(GamesClientImpl.class.getClassLoader());
            return bundleDG;
        } catch (RemoteException e) {
            GamesLog.g("GamesClientImpl", "service died");
            return null;
        }
    }

    @Override // com.google.android.gms.internal.ff, com.google.android.gms.common.GooglePlayServicesClient, com.google.android.gms.common.api.Api.a
    public void disconnect() {
        this.Iz = false;
        if (isConnected()) {
            try {
                IGamesService iGamesServiceEM = eM();
                iGamesServiceEM.gF();
                iGamesServiceEM.o(this.IE);
            } catch (RemoteException e) {
                GamesLog.g("GamesClientImpl", "Failed to notify client disconnect.");
            }
        }
        gE();
        super.disconnect();
    }

    public void e(a.d<Players.LoadExtendedPlayersResult> dVar, int i, boolean z, boolean z2) {
        try {
            eM().d(new ExtendedPlayersLoadedBinderCallback(dVar), i, z, z2);
        } catch (RemoteException e) {
            GamesLog.g("GamesClientImpl", "service died");
        }
    }

    public void e(a.d<TurnBasedMultiplayer.InitiateMatchResult> dVar, String str) {
        try {
            eM().m(new TurnBasedMatchInitiatedBinderCallbacks(dVar), str);
        } catch (RemoteException e) {
            GamesLog.g("GamesClientImpl", "service died");
        }
    }

    public void f(View view) {
        this.Iy.g(view);
    }

    public void f(a.d<TurnBasedMultiplayer.LeaveMatchResult> dVar, String str) {
        try {
            eM().o(new TurnBasedMatchLeftBinderCallbacks(dVar), str);
        } catch (RemoteException e) {
            GamesLog.g("GamesClientImpl", "service died");
        }
    }

    public void g(a.d<GamesMetadata.LoadGamesResult> dVar) {
        try {
            eM().d(new GamesLoadedBinderCallback(dVar));
        } catch (RemoteException e) {
            GamesLog.g("GamesClientImpl", "service died");
        }
    }

    public void g(a.d<TurnBasedMultiplayer.CancelMatchResult> dVar, String str) {
        try {
            eM().n(new TurnBasedMatchCanceledBinderCallbacks(dVar), str);
        } catch (RemoteException e) {
            GamesLog.g("GamesClientImpl", "service died");
        }
    }

    public int gA() {
        try {
            return eM().gA();
        } catch (RemoteException e) {
            GamesLog.g("GamesClientImpl", "service died");
            return 2;
        }
    }

    public Intent gB() {
        try {
            return eM().gB();
        } catch (RemoteException e) {
            GamesLog.g("GamesClientImpl", "service died");
            return null;
        }
    }

    public int gC() {
        try {
            return eM().gC();
        } catch (RemoteException e) {
            GamesLog.g("GamesClientImpl", "service died");
            return 2;
        }
    }

    public int gD() {
        try {
            return eM().gD();
        } catch (RemoteException e) {
            GamesLog.g("GamesClientImpl", "service died");
            return 2;
        }
    }

    public void gF() {
        if (isConnected()) {
            try {
                eM().gF();
            } catch (RemoteException e) {
                GamesLog.g("GamesClientImpl", "service died");
            }
        }
    }

    public String gl() {
        try {
            return eM().gl();
        } catch (RemoteException e) {
            GamesLog.g("GamesClientImpl", "service died");
            return null;
        }
    }

    public String gm() {
        try {
            return eM().gm();
        } catch (RemoteException e) {
            GamesLog.g("GamesClientImpl", "service died");
            return null;
        }
    }

    public Player gn() {
        bT();
        synchronized (this) {
            if (this.Iw == null) {
                try {
                    PlayerBuffer playerBuffer = new PlayerBuffer(eM().gG());
                    try {
                        if (playerBuffer.getCount() > 0) {
                            this.Iw = (PlayerEntity) playerBuffer.get(0).freeze();
                        }
                        playerBuffer.close();
                    } catch (Throwable th) {
                        playerBuffer.close();
                        throw th;
                    }
                } catch (RemoteException e) {
                    GamesLog.g("GamesClientImpl", "service died");
                }
            }
        }
        return this.Iw;
    }

    public Game go() {
        bT();
        synchronized (this) {
            if (this.Ix == null) {
                try {
                    GameBuffer gameBuffer = new GameBuffer(eM().gI());
                    try {
                        if (gameBuffer.getCount() > 0) {
                            this.Ix = (GameEntity) gameBuffer.get(0).freeze();
                        }
                        gameBuffer.close();
                    } catch (Throwable th) {
                        gameBuffer.close();
                        throw th;
                    }
                } catch (RemoteException e) {
                    GamesLog.g("GamesClientImpl", "service died");
                }
            }
        }
        return this.Ix;
    }

    public Intent gp() {
        try {
            return eM().gp();
        } catch (RemoteException e) {
            GamesLog.g("GamesClientImpl", "service died");
            return null;
        }
    }

    public Intent gq() {
        try {
            return eM().gq();
        } catch (RemoteException e) {
            GamesLog.g("GamesClientImpl", "service died");
            return null;
        }
    }

    public Intent gr() {
        try {
            return eM().gr();
        } catch (RemoteException e) {
            GamesLog.g("GamesClientImpl", "service died");
            return null;
        }
    }

    public Intent gs() {
        try {
            return eM().gs();
        } catch (RemoteException e) {
            GamesLog.g("GamesClientImpl", "service died");
            return null;
        }
    }

    public void gt() {
        try {
            eM().p(this.IE);
        } catch (RemoteException e) {
            GamesLog.g("GamesClientImpl", "service died");
        }
    }

    public void gu() {
        try {
            eM().q(this.IE);
        } catch (RemoteException e) {
            GamesLog.g("GamesClientImpl", "service died");
        }
    }

    public void gv() {
        try {
            eM().r(this.IE);
        } catch (RemoteException e) {
            GamesLog.g("GamesClientImpl", "service died");
        }
    }

    public Intent gw() {
        try {
            return eM().gw();
        } catch (RemoteException e) {
            GamesLog.g("GamesClientImpl", "service died");
            return null;
        }
    }

    public Intent gx() {
        try {
            return eM().gx();
        } catch (RemoteException e) {
            GamesLog.g("GamesClientImpl", "service died");
            return null;
        }
    }

    public int gy() {
        try {
            return eM().gy();
        } catch (RemoteException e) {
            GamesLog.g("GamesClientImpl", "service died");
            return 4368;
        }
    }

    public String gz() {
        try {
            return eM().gz();
        } catch (RemoteException e) {
            GamesLog.g("GamesClientImpl", "service died");
            return null;
        }
    }

    public void h(a.d<Players.LoadOwnerCoverPhotoUrisResult> dVar) {
        try {
            eM().j(new OwnerCoverPhotoUrisLoadedBinderCallback(dVar));
        } catch (RemoteException e) {
            GamesLog.g("GamesClientImpl", "service died");
        }
    }

    public void h(a.d<TurnBasedMultiplayer.LoadMatchResult> dVar, String str) {
        try {
            eM().p(new TurnBasedMatchLoadedBinderCallbacks(dVar), str);
        } catch (RemoteException e) {
            GamesLog.g("GamesClientImpl", "service died");
        }
    }

    public RealTimeSocket i(String str, String str2) {
        if (str2 == null || !ParticipantUtils.aV(str2)) {
            throw new IllegalArgumentException("Bad participant ID");
        }
        RealTimeSocket realTimeSocket = this.Iv.get(str2);
        return (realTimeSocket == null || realTimeSocket.isClosed()) ? aC(str2) : realTimeSocket;
    }

    public void i(a.d<Acls.LoadAclResult> dVar) {
        try {
            eM().h(new NotifyAclLoadedBinderCallback(dVar));
        } catch (RemoteException e) {
            GamesLog.g("GamesClientImpl", "service died");
        }
    }

    public void i(a.d<GamesMetadata.LoadExtendedGamesResult> dVar, String str) {
        try {
            eM().e(new ExtendedGamesLoadedBinderCallback(dVar), str);
        } catch (RemoteException e) {
            GamesLog.g("GamesClientImpl", "service died");
        }
    }

    public void j(a.d<Notifications.ContactSettingLoadResult> dVar) {
        try {
            eM().i(new ContactSettingsLoadedBinderCallback(dVar));
        } catch (RemoteException e) {
            GamesLog.g("GamesClientImpl", "service died");
        }
    }

    public void j(a.d<GamesMetadata.LoadGameInstancesResult> dVar, String str) {
        try {
            eM().f(new GameInstancesLoadedBinderCallback(dVar), str);
        } catch (RemoteException e) {
            GamesLog.g("GamesClientImpl", "service died");
        }
    }

    public void k(a.d<GamesMetadata.LoadGameSearchSuggestionsResult> dVar, String str) {
        try {
            eM().q(new GameSearchSuggestionsLoadedBinderCallback(dVar), str);
        } catch (RemoteException e) {
            GamesLog.g("GamesClientImpl", "service died");
        }
    }

    public void l(a.d<Invitations.LoadInvitationsResult> dVar, String str) {
        try {
            eM().k(new InvitationsLoadedBinderCallback(dVar), str);
        } catch (RemoteException e) {
            GamesLog.g("GamesClientImpl", "service died");
        }
    }

    public void l(String str, int i) {
        try {
            eM().l(str, i);
        } catch (RemoteException e) {
            GamesLog.g("GamesClientImpl", "service died");
        }
    }

    public void m(a.d<Status> dVar, String str) {
        try {
            eM().j(new NotifyAclUpdatedBinderCallback(dVar), str);
        } catch (RemoteException e) {
            GamesLog.g("GamesClientImpl", "service died");
        }
    }

    public void m(String str, int i) {
        try {
            eM().m(str, i);
        } catch (RemoteException e) {
            GamesLog.g("GamesClientImpl", "service died");
        }
    }

    public void n(a.d<Notifications.GameMuteStatusLoadResult> dVar, String str) {
        try {
            eM().i(new GameMuteStatusLoadedBinderCallback(dVar), str);
        } catch (RemoteException e) {
            GamesLog.g("GamesClientImpl", "service died");
        }
    }

    @Override // com.google.android.gms.common.api.GoogleApiClient.ConnectionCallbacks
    public void onConnected(Bundle connectionHint) {
        if (this.Iz) {
            this.Iy.gS();
            this.Iz = false;
        }
    }

    @Override // com.google.android.gms.common.api.GoogleApiClient.OnConnectionFailedListener, com.google.android.gms.common.GooglePlayServicesClient.OnConnectionFailedListener
    public void onConnectionFailed(ConnectionResult result) {
        this.Iz = false;
    }

    @Override // com.google.android.gms.common.api.GoogleApiClient.ConnectionCallbacks
    public void onConnectionSuspended(int cause) {
    }
}
