package com.fyber.utils;

import com.fyber.exceptions.IdException;
import java.util.regex.Pattern;

/* JADX INFO: compiled from: IdValidator.java */
/* JADX INFO: loaded from: classes.dex */
public final class k {
    private static Pattern a = Pattern.compile("^[A-Z0-9_]+$");

    public static void a(String str) throws IdException {
        if (StringUtils.nullOrEmpty(str)) {
            throw new IdException("An ID cannot be null or empty.");
        }
        if (!a.matcher(str).find()) {
            throw new IdException("An ID can only contain uppercase letters, numbers and the _ underscore symbol.");
        }
    }
}
