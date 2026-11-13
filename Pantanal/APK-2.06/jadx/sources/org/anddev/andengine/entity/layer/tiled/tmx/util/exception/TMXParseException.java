package org.anddev.andengine.entity.layer.tiled.tmx.util.exception;

import org.xml.sax.SAXException;

/* JADX INFO: loaded from: classes.dex */
public class TMXParseException extends SAXException {
    private static final long serialVersionUID = 2213964295487921492L;

    public TMXParseException() {
    }

    public TMXParseException(String pDetailMessage) {
        super(pDetailMessage);
    }

    public TMXParseException(Exception pException) {
        super(pException);
    }

    public TMXParseException(String pMessage, Exception pException) {
        super(pMessage, pException);
    }
}
