package org.anddev.andengine.entity.layer.tiled.tmx.util.exception;

/* JADX INFO: loaded from: classes.dex */
public class TMXLoadException extends TMXException {
    private static final long serialVersionUID = -8295358631698809883L;

    public TMXLoadException() {
    }

    public TMXLoadException(String pDetailMessage, Throwable pThrowable) {
        super(pDetailMessage, pThrowable);
    }

    public TMXLoadException(String pDetailMessage) {
        super(pDetailMessage);
    }

    public TMXLoadException(Throwable pThrowable) {
        super(pThrowable);
    }
}
