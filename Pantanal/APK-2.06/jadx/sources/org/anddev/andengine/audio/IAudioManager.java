package org.anddev.andengine.audio;

import org.anddev.andengine.audio.IAudioEntity;

/* JADX INFO: loaded from: classes.dex */
public interface IAudioManager<T extends IAudioEntity> {
    void add(T t);

    float getMasterVolume();

    void releaseAll();

    void setMasterVolume(float f);
}
