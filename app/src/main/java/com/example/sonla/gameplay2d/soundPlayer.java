package com.example.sonla.gameplay2d;

import android.content.Context;
import android.media.AudioAttributes;
import android.media.AudioManager;
import android.media.SoundPool;
import android.os.Build;

/**
 * Created by sonla on 12/23/2016.
 */

public class soundPlayer {
    public AudioAttributes audioAttributes;
    final int SOUND_POOL_MAX = 2;
    public static SoundPool soundPool;
    public static int hitSound;
    public static int overSound;
    public static int flySound;

    public soundPlayer(Context context) {

        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP){
            audioAttributes = new AudioAttributes.Builder()
                    .setUsage(AudioAttributes.USAGE_GAME)
                    .setContentType(AudioAttributes.CONTENT_TYPE_MUSIC)
                    .build();

                    soundPool = new SoundPool.Builder()
                            .setAudioAttributes(audioAttributes)
                            .setMaxStreams(SOUND_POOL_MAX)
                            .build();
        }else{
            //       SoundPool (int maxStreams, int streamType,  int srcQuality)
            soundPool = new SoundPool(SOUND_POOL_MAX, AudioManager.STREAM_MUSIC, 0);
        }


        hitSound = soundPool.load(context, R.raw.hit, 1);
        overSound = soundPool.load(context, R.raw.over, 1);
        flySound = soundPool.load(context, R.raw.fly, 1);

    }

    public void playHitSound() {
        soundPool.play(hitSound, 1.0f, 1.0f, 1, 0, 1.0f);
    }

    public void  playOverSound(){
        soundPool.play(overSound, 1.0f, 1.0f, 1, 0, 1.0f);
    }

    public void  playFlyrSound(){
        soundPool.play(flySound, 1.0f, 1.0f, 1, 0, 1.0f);
    }
}
