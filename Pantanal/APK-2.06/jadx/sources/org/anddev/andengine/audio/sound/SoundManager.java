package org.anddev.andengine.audio.sound;

import android.media.SoundPool;
import org.anddev.andengine.audio.BaseAudioManager;

/* JADX INFO: loaded from: classes.dex */
public class SoundManager extends BaseAudioManager<Sound> {
    private static final int MAX_SIMULTANEOUS_STREAMS_DEFAULT = 5;
    private final SoundPool mSoundPool;

    public SoundManager() {
        this(5);
    }

    public SoundManager(int pMaxSimultaneousStreams) {
        this.mSoundPool = new SoundPool(pMaxSimultaneousStreams, 3, 0);
    }

    SoundPool getSoundPool() {
        return this.mSoundPool;
    }

    @Override // org.anddev.andengine.audio.BaseAudioManager, org.anddev.andengine.audio.IAudioManager
    public void releaseAll() {
        super.releaseAll();
        this.mSoundPool.release();
    }
}
