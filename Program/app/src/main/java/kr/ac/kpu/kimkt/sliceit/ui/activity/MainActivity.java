package kr.ac.kpu.kimkt.sliceit.ui.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.res.Configuration;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import kr.ac.kpu.kimkt.sliceit.R;
import kr.ac.kpu.kimkt.sliceit.res.SoundEffects;
import kr.ac.kpu.kimkt.sliceit.ui.view.GameView;
import kr.ac.kpu.kimkt.sliceit.world.MainWorld;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = MainActivity.class.getSimpleName();
    private GameView gameView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        MainWorld.create();

        SoundEffects se = SoundEffects.get();
        se.init(this);
        se.loadAll();
        SoundEffects.get().play(R.raw.slice_it_bgm);

        gameView = new GameView(this);
        setContentView(gameView);


    }

    @Override
    protected void onPause() {
        gameView.pause();
        super.onPause();
    }

    @Override
    protected void onResume() {
        gameView.resume();
        super.onResume();
    }

    @Override
    public void onConfigurationChanged(@NonNull Configuration newConfig) {
        Log.d(TAG, "New ConfigurationL " + newConfig);
        super.onConfigurationChanged(newConfig);
    }

}
