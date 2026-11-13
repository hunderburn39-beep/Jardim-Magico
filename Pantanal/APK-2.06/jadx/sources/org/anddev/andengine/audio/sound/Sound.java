package org.anddev.andengine.audio.sound;

import org.anddev.andengine.audio.BaseAudioEntity;

/* JADX INFO: loaded from: classes.dex */
public class Sound extends BaseAudioEntity {
    private int mLoopCount;
    private float mRate;
    private final int mSoundID;
    private int mStreamID;

    Sound(SoundManager pSoundManager, int pSoundID) {
        super(pSoundManager);
        this.mStreamID = 0;
        this.mLoopCount = 0;
        this.mRate = 1.0f;
        this.mSoundID = pSoundID;
    }

    public void setLoopCount(int pLoopCount) {
        this.mLoopCount = pLoopCount;
        if (this.mStreamID != 0) {
            getAudioManager().getSoundPool().setLoop(this.mStreamID, pLoopCount);
        }
    }

    public void setRate(float pRate) {
        this.mRate = pRate;
        if (this.mStreamID != 0) {
            getAudioManager().getSoundPool().setRate(this.mStreamID, pRate);
        }
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // org.anddev.andengine.audio.BaseAudioEntity
    public SoundManager getAudioManager() {
        return (SoundManager) super.getAudioManager();
    }

    @Override // org.anddev.andengine.audio.IAudioEntity
    public void play() {
        float masterVolume = getMasterVolume();
        float leftVolume = this.mLeftVolume * masterVolume;
        float rightVolume = this.mRightVolume * masterVolume;
        this.mStreamID = getAudioManager().getSoundPool().play(this.mSoundID, leftVolume, rightVolume, 1, this.mLoopCount, this.mRate);
    }

    @Override // org.anddev.andengine.audio.IAudioEntity
    public void stop() {
        if (this.mStreamID != 0) {
            getAudioManager().getSoundPool().stop(this.mStreamID);
        }
    }

    @Override // org.anddev.andengine.audio.IAudioEntity
    public void resume() {
        if (this.mStreamID != 0) {
            getAudioManager().getSoundPool().resume(this.mStreamID);
        }
    }

    @Override // org.anddev.andengine.audio.IAudioEntity
    public void pause() {
        if (this.mStreamID != 0) {
            getAudioManager().getSoundPool().pause(this.mStreamID);
        }
    }

    @Override // org.anddev.andengine.audio.IAudioEntity
    public void release() {
    }

    @Override // org.anddev.andengine.audio.IAudioEntity
    public void setLooping(boolean pLooping) {
        setLoopCount(pLooping ? -1 : 0);
    }

    @Override // org.anddev.andengine.audio.BaseAudioEntity, org.anddev.andengine.audio.IAudioEntity
    public void setVolume(float pLeftVolume, float pRightVolume) {
        super.setVolume(pLeftVolume, pRightVolume);
        if (this.mStreamID != 0) {
            float masterVolume = getMasterVolume();
            float leftVolume = this.mLeftVolume * masterVolume;
            float rightVolume = this.mRightVolume * masterVolume;
            getAudioManager().getSoundPool().setVolume(this.mStreamID, leftVolume, rightVolume);
        }
    }

    @Override // org.anddev.andengine.audio.IAudioEntity
    public void onMasterVolumeChanged(float pMasterVolume) {
        setVolume(this.mLeftVolume, this.mRightVolume);
    }
}
