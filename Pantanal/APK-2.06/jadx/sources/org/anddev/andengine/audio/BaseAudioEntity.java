package org.anddev.andengine.audio;

/* JADX INFO: loaded from: classes.dex */
public abstract class BaseAudioEntity implements IAudioEntity {
    private final IAudioManager<? extends IAudioEntity> mAudioManager;
    protected float mLeftVolume = 1.0f;
    protected float mRightVolume = 1.0f;

    public BaseAudioEntity(IAudioManager<? extends IAudioEntity> pAudioManager) {
        this.mAudioManager = pAudioManager;
    }

    protected IAudioManager<? extends IAudioEntity> getAudioManager() {
        return this.mAudioManager;
    }

    public float getActualLeftVolume() {
        return this.mLeftVolume * getMasterVolume();
    }

    public float getActualRightVolume() {
        return this.mRightVolume * getMasterVolume();
    }

    protected float getMasterVolume() {
        return this.mAudioManager.getMasterVolume();
    }

    @Override // org.anddev.andengine.audio.IAudioEntity
    public float getVolume() {
        return (this.mLeftVolume + this.mRightVolume) * 0.5f;
    }

    @Override // org.anddev.andengine.audio.IAudioEntity
    public float getLeftVolume() {
        return this.mLeftVolume;
    }

    @Override // org.anddev.andengine.audio.IAudioEntity
    public float getRightVolume() {
        return this.mRightVolume;
    }

    @Override // org.anddev.andengine.audio.IAudioEntity
    public final void setVolume(float pVolume) {
        setVolume(pVolume, pVolume);
    }

    @Override // org.anddev.andengine.audio.IAudioEntity
    public void setVolume(float pLeftVolume, float pRightVolume) {
        this.mLeftVolume = pLeftVolume;
        this.mRightVolume = pRightVolume;
    }
}
