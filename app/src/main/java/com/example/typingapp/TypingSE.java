package com.example.typingapp;

import android.content.Context;
import android.media.AudioManager;
import android.media.SoundPool;

class TypingSE {
    private SoundPool soundPool;
    private int SEOfIncorrectAnswer_id;
    private Context context;

    TypingSE(Context context) {
        soundPool = new SoundPool(2, AudioManager.STREAM_MUSIC, 0);
        this.context = context;
    }

    void setSEOfIncorrectAnswer() {
        SEOfIncorrectAnswer_id = soundPool.load(context, R.raw.incorrect, 1);
    }

    void playSEOfIncorrectAnswer() {
        soundPool.play(SEOfIncorrectAnswer_id, 1, 1, 0, 0, 1);
    }

    void releaseSoundPool() {
        soundPool.release();
    }
}
