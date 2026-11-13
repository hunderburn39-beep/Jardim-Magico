package com.google.android.gms.games.leaderboard;

import com.google.android.gms.common.data.DataHolder;
import com.google.android.gms.games.internal.constants.TimeSpan;
import com.google.android.gms.internal.fo;
import com.google.android.gms.internal.fq;
import java.util.HashMap;

/* JADX INFO: loaded from: classes.dex */
public final class ScoreSubmissionData {
    private static final String[] LN = {"leaderboardId", "playerId", "timeSpan", "hasResult", "rawScore", "formattedScore", "newBest", "scoreTag"};
    private int Ah;
    private String Ie;
    private String LP;
    private HashMap<Integer, Result> Mt = new HashMap<>();

    public static final class Result {
        public final String formattedScore;
        public final boolean newBest;
        public final long rawScore;
        public final String scoreTag;

        public Result(long rawScore, String formattedScore, String scoreTag, boolean newBest) {
            this.rawScore = rawScore;
            this.formattedScore = formattedScore;
            this.scoreTag = scoreTag;
            this.newBest = newBest;
        }

        public String toString() {
            return fo.e(this).a("RawScore", Long.valueOf(this.rawScore)).a("FormattedScore", this.formattedScore).a("ScoreTag", this.scoreTag).a("NewBest", Boolean.valueOf(this.newBest)).toString();
        }
    }

    public ScoreSubmissionData(DataHolder dataHolder) {
        this.Ah = dataHolder.getStatusCode();
        int count = dataHolder.getCount();
        fq.z(count == 3);
        for (int i = 0; i < count; i++) {
            int iG = dataHolder.G(i);
            if (i == 0) {
                this.LP = dataHolder.getString("leaderboardId", i, iG);
                this.Ie = dataHolder.getString("playerId", i, iG);
            }
            if (dataHolder.getBoolean("hasResult", i, iG)) {
                a(new Result(dataHolder.getLong("rawScore", i, iG), dataHolder.getString("formattedScore", i, iG), dataHolder.getString("scoreTag", i, iG), dataHolder.getBoolean("newBest", i, iG)), dataHolder.getInteger("timeSpan", i, iG));
            }
        }
    }

    private void a(Result result, int i) {
        this.Mt.put(Integer.valueOf(i), result);
    }

    public String getLeaderboardId() {
        return this.LP;
    }

    public String getPlayerId() {
        return this.Ie;
    }

    public Result getScoreResult(int timeSpan) {
        return this.Mt.get(Integer.valueOf(timeSpan));
    }

    public String toString() {
        fo.a aVarA = fo.e(this).a("PlayerId", this.Ie).a("StatusCode", Integer.valueOf(this.Ah));
        int i = 0;
        while (true) {
            int i2 = i;
            if (i2 >= 3) {
                return aVarA.toString();
            }
            Result result = this.Mt.get(Integer.valueOf(i2));
            aVarA.a("TimesSpan", TimeSpan.bd(i2));
            aVarA.a("Result", result == null ? "null" : result.toString());
            i = i2 + 1;
        }
    }
}
