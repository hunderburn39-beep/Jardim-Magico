package org.anddev.andengine.audio;

import java.util.ArrayList;
import org.anddev.andengine.audio.IAudioEntity;

/* JADX INFO: loaded from: classes.dex */
public abstract class BaseAudioManager<T extends IAudioEntity> implements IAudioManager<T> {
    protected final ArrayList<T> mAudioEntities = new ArrayList<>();
    protected float mMasterVolume = 1.0f;

    @Override // org.anddev.andengine.audio.IAudioManager
    public float getMasterVolume() {
        return this.mMasterVolume;
    }

    @Override // org.anddev.andengine.audio.IAudioManager
    public void setMasterVolume(float pMasterVolume) {
        this.mMasterVolume = pMasterVolume;
        ArrayList<T> audioEntities = this.mAudioEntities;
        for (int i = audioEntities.size() - 1; i >= 0; i--) {
            T audioEntity = audioEntities.get(i);
            audioEntity.onMasterVolumeChanged(pMasterVolume);
        }
    }

    @Override // org.anddev.andengine.audio.IAudioManager
    public void add(T pAudioEntity) {
        this.mAudioEntities.add(pAudioEntity);
    }

    @Override // org.anddev.andengine.audio.IAudioManager
    public void releaseAll() {
        ArrayList<T> audioEntities = this.mAudioEntities;
        for (int i = audioEntities.size() - 1; i >= 0; i--) {
            T audioEntity = audioEntities.get(i);
            audioEntity.stop();
            audioEntity.release();
        }
    }
}
