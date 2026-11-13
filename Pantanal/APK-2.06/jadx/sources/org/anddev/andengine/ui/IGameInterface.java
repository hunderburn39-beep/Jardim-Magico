package org.anddev.andengine.ui;

import org.anddev.andengine.engine.Engine;
import org.anddev.andengine.entity.scene.Scene;

/* JADX INFO: loaded from: classes.dex */
public interface IGameInterface {
    void onLoadComplete();

    Engine onLoadEngine();

    void onLoadResources();

    Scene onLoadScene();

    void onPauseGame();

    void onResumeGame();

    void onUnloadResources();
}
