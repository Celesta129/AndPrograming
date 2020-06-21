package kr.ac.kpu.kimkt.sliceit.res;

import android.content.Context;
import android.media.AudioAttributes;
import android.media.AudioManager;
import android.media.SoundPool;
import android.os.Build;

import java.util.HashMap;

import kr.ac.kpu.kimkt.sliceit.R;

public class SoundEffects {
    private Context context;
    private static SoundEffects singleton;
    private SoundPool soundPool;

    public static SoundEffects get() {
        if (singleton == null) {
            singleton = new SoundEffects();
        }
        return singleton;
    }

    public void init(Context context){
        this.context = context;
        AudioAttributes audioAttributes = null;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            audioAttributes = new AudioAttributes.Builder()
                    .setUsage(AudioAttributes.USAGE_GAME)
                    .setContentType(AudioAttributes.CONTENT_TYPE_MUSIC)
                    .build();

            this.soundPool = new SoundPool.Builder()
                    .setAudioAttributes(audioAttributes)
                    .setMaxStreams(3)
                    .build();
        }
        else{
            this.soundPool = new SoundPool(3, AudioManager.STREAM_MUSIC, 0);
        }

    }
    private static final int[] SOUND_IDS = {
            R.raw.slice_it_bgm,
    };
    private HashMap<Integer, Integer> soundIDMap = new HashMap<>();

    public void loadAll(){
        for(int resID: SOUND_IDS){
            int soundID = soundPool.load(context, resID, 1);
            soundIDMap.put(resID, soundID);
        }
    }
    public int play(int resID){
        int soundID = soundIDMap.get(resID);
        int streamID = soundPool.play(soundID, 1f, 1f, 1, 0, 1f);

        return streamID;
    }
}
