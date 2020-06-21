package kr.ac.kpu.kimkt.sliceit.ui.activity;

import android.content.Intent;
import android.icu.text.CaseMap;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import kr.ac.kpu.kimkt.sliceit.R;

public class TitleActivity  extends AppCompatActivity {
    private String TAG = TitleActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activiry_title);
        Log.d(TAG,"onCreate()");

    }

    @Override
    protected void onPause() {
        Log.d(TAG,"onPause()");
        super.onPause();
    }

    @Override
    protected void onResume() {
        Log.d(TAG,"onResume()");
        super.onResume();
    }

    public void onBtnStart(View view) {
        Log.d(TAG,"onBtnStart()");
        Intent intent = new Intent(this, MainActivity.class);

        startActivity(intent);
    }

}
