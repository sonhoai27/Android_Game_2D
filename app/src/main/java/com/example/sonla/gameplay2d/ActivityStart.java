package com.example.sonla.gameplay2d;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;


public class ActivityStart extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);


    }

//    public boolean onTouchEvent(MotionEvent me){
//        if(clickPlay == false){
//            if(me.getAction() == MotionEvent.ACTION_DOWN){
//                play.setImageResource(R.drawable.play_hover);
//                play.getLayoutParams().height = 280;
//                play.getLayoutParams().width = 280;
//            } if (me.getAction() == MotionEvent.ACTION_UP) {
//                play.setImageResource(R.drawable.play);
//                play.getLayoutParams().height = 300;
//                play.getLayoutParams().width = 300;
//            }
//        }
//        return true;
//    }

    public void startGame(View view){
       Intent intent = new Intent(getApplicationContext(), MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
        finish();
        startActivity(intent);
        overridePendingTransition(0, 0);
    }


//    @Override
//    public boolean dispatchKeyEvent(KeyEvent event) {
//        if (event.getAction() == KeyEvent.ACTION_DOWN) {
//            switch (event.getKeyCode()) {
//                case KeyEvent.KEYCODE_BACK:
//                    return true;
//            }
//        }
//
//        return super.dispatchKeyEvent(event);
//    }

}
