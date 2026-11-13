package org.anddev.andengine.entity.layer.tiled.tmx.util.exception;

/* JADX INFO: loaded from: classes.dex */
public class TSXLoadException extends TMXException {
    private static final long serialVersionUID = 10055223972707703L;

    public TSXLoadException() {
    }

    public TSXLoadException(String pDetailMessage, Throwable pThrowable) {
        super(pDetailMessage, pThrowable);
    }

    public TSXLoadException(String pDetailMessage) {
        super(pDetailMessage);
    }

    public TSXLoadException(Throwable pThrowable) {
        super(pThrowable);
    }
}
