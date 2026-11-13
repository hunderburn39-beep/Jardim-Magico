package org.anddev.andengine.engine.handler;

import org.anddev.andengine.util.IMatcher;

/* JADX INFO: loaded from: classes.dex */
public interface IUpdateHandler {

    public interface IUpdateHandlerMatcher extends IMatcher<IUpdateHandler> {
    }

    void onUpdate(float f);

    void reset();
}
