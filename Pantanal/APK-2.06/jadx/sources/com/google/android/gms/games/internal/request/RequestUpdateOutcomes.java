package com.google.android.gms.games.internal.request;

import com.google.android.gms.common.data.DataHolder;
import com.google.android.gms.games.internal.constants.RequestUpdateResultOutcome;
import com.google.android.gms.internal.fq;
import java.util.HashMap;
import java.util.Set;

/* JADX INFO: loaded from: classes.dex */
public final class RequestUpdateOutcomes {
    private static final String[] LN = {"requestId", "outcome"};
    private final int Ah;
    private final HashMap<String, Integer> LO;

    public static final class Builder {
        private HashMap<String, Integer> LO = new HashMap<>();
        private int Ah = 0;

        public Builder bm(int i) {
            this.Ah = i;
            return this;
        }

        public RequestUpdateOutcomes hB() {
            return new RequestUpdateOutcomes(this.Ah, this.LO);
        }

        public Builder s(String str, int i) {
            if (RequestUpdateResultOutcome.isValid(i)) {
                this.LO.put(str, Integer.valueOf(i));
            }
            return this;
        }
    }

    private RequestUpdateOutcomes(int statusCode, HashMap<String, Integer> outcomeMap) {
        this.Ah = statusCode;
        this.LO = outcomeMap;
    }

    public static RequestUpdateOutcomes J(DataHolder dataHolder) {
        Builder builder = new Builder();
        builder.bm(dataHolder.getStatusCode());
        int count = dataHolder.getCount();
        for (int i = 0; i < count; i++) {
            int iG = dataHolder.G(i);
            builder.s(dataHolder.getString("requestId", i, iG), dataHolder.getInteger("outcome", i, iG));
        }
        return builder.hB();
    }

    public Set<String> getRequestIds() {
        return this.LO.keySet();
    }

    public int getRequestOutcome(String requestId) {
        fq.b(this.LO.containsKey(requestId), "Request " + requestId + " was not part of the update operation!");
        return this.LO.get(requestId).intValue();
    }
}
