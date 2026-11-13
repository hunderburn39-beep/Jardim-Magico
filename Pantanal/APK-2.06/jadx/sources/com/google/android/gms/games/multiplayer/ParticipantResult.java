package com.google.android.gms.games.multiplayer;

import android.os.Parcel;
import com.google.android.gms.common.internal.safeparcel.SafeParcelable;
import com.google.android.gms.games.internal.constants.MatchResult;
import com.google.android.gms.internal.fq;

/* JADX INFO: loaded from: classes.dex */
public final class ParticipantResult implements SafeParcelable {
    public static final ParticipantResultCreator CREATOR = new ParticipantResultCreator();
    public static final int MATCH_RESULT_DISAGREED = 5;
    public static final int MATCH_RESULT_DISCONNECT = 4;
    public static final int MATCH_RESULT_LOSS = 1;
    public static final int MATCH_RESULT_NONE = 3;
    public static final int MATCH_RESULT_TIE = 2;
    public static final int MATCH_RESULT_UNINITIALIZED = -1;
    public static final int MATCH_RESULT_WIN = 0;
    public static final int PLACING_UNINITIALIZED = -1;
    private final String Jg;
    private final int MF;
    private final int MG;
    private final int xH;

    public ParticipantResult(int versionCode, String participantId, int result, int placing) {
        this.xH = versionCode;
        this.Jg = (String) fq.f(participantId);
        fq.x(MatchResult.isValid(result));
        this.MF = result;
        this.MG = placing;
    }

    public ParticipantResult(String participantId, int result, int placing) {
        this(1, participantId, result, placing);
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public String getParticipantId() {
        return this.Jg;
    }

    public int getPlacing() {
        return this.MG;
    }

    public int getResult() {
        return this.MF;
    }

    public int getVersionCode() {
        return this.xH;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel out, int flags) {
        ParticipantResultCreator.a(this, out, flags);
    }
}
