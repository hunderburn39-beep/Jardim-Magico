package org.anddev.andengine.audio.music;

import android.media.MediaPlayer;
import org.anddev.andengine.audio.BaseAudioEntity;

/* JADX INFO: loaded from: classes.dex */
public class Music extends BaseAudioEntity {
    private final MediaPlayer mMediaPlayer;

    Music(MusicManager pMusicManager, MediaPlayer pMediaPlayer) {
        super(pMusicManager);
        this.mMediaPlayer = pMediaPlayer;
    }

    public boolean isPlaying() {
        return this.mMediaPlayer.isPlaying();
    }

    public MediaPlayer getMediaPlayer() {
        return this.mMediaPlayer;
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // org.anddev.andengine.audio.BaseAudioEntity
    public MusicManager getAudioManager() {
        return (MusicManager) super.getAudioManager();
    }

    @Override // org.anddev.andengine.audio.IAudioEntity
    public void play() {
        this.mMediaPlayer.start();
    }

    @Override // org.anddev.andengine.audio.IAudioEntity
    public void stop() {
        this.mMediaPlayer.stop();
    }

    @Override // org.anddev.andengine.audio.IAudioEntity
    public void resume() {
        this.mMediaPlayer.start();
    }

    @Override // org.anddev.andengine.audio.IAudioEntity
    public void pause() {
        this.mMediaPlayer.pause();
    }

    @Override // org.anddev.andengine.audio.IAudioEntity
    public void release() {
        this.mMediaPlayer.release();
    }

    @Override // org.anddev.andengine.audio.IAudioEntity
    public void setLooping(boolean pLooping) {
        this.mMediaPlayer.setLooping(pLooping);
    }

    @Override // org.anddev.andengine.audio.BaseAudioEntity, org.anddev.andengine.audio.IAudioEntity
    public void setVolume(float pLeftVolume, float pRightVolume) {
        super.setVolume(pLeftVolume, pRightVolume);
        float masterVolume = getAudioManager().getMasterVolume();
        float actualLeftVolume = pLeftVolume * masterVolume;
        float actualRightVolume = pRightVolume * masterVolume;
        this.mMediaPlayer.setVolume(actualLeftVolume, actualRightVolume);
    }

    @Override // org.anddev.andengine.audio.IAudioEntity
    public void onMasterVolumeChanged(float pMasterVolume) {
        setVolume(this.mLeftVolume, this.mRightVolume);
    }

    public void seekTo(int pMilliseconds) {
        this.mMediaPlayer.seekTo(pMilliseconds);
    }

    public void setOnCompletionListener(MediaPlayer.OnCompletionListener pOnCompletionListener) {
        this.mMediaPlayer.setOnCompletionListener(pOnCompletionListener);
    }
}
