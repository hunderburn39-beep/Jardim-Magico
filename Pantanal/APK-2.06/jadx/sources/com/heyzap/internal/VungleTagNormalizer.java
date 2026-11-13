package com.heyzap.internal;

/* JADX INFO: loaded from: classes.dex */
public class VungleTagNormalizer {
    public String normalize(String tag) {
        if (tag == null) {
            return null;
        }
        return tag.replaceAll("[^-_a-zA-Z0-9]", "");
    }
}
