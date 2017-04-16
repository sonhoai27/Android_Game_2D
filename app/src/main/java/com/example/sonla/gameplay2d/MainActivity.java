package com.example.sonla.gameplay2d;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Point;
import android.os.Handler;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.*;
import android.widget.*;

import java.util.Timer;
import java.util.TimerTask;

import static android.R.attr.*;

public class MainActivity extends AppCompatActivity {
    private TextView tapToStar;
    private TextView scoreLabel, timeLabel, levelLabel;
    private ImageView box, box_bg, black, orange, pink;

    LinearLayout linearLayout;
    RelativeLayout quitGame;

    private int boxY;

    private int orangeX, orangeY, pinkX, pinkY, blackX, blackY;

    //initialive class

    private Handler handler = new Handler();
    private Timer timer = new Timer();

    private RelativeLayout relativeLayout;

    private soundPlayer soundPlayer;

    private boolean action_flg = false;
    private boolean star_flg = false;

    private int frameHieght, boxSize;

    private int screenWidth;
    private int screenHeight;

    private int score = 0;

    private int speedBox, speedOrange, speedBlack, speedPink;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        AnhXa();

        //move to screen

        box_bg.setVisibility(View.VISIBLE);
        box.setVisibility(View.INVISIBLE);
        scoreLabel.setVisibility(View.INVISIBLE);
        timeLabel.setVisibility(View.INVISIBLE);
        levelLabel.setVisibility(View.INVISIBLE);
        relativeLayout.setVisibility(View.INVISIBLE);

        soundPlayer = new soundPlayer(this);

        //get screen size
        WindowManager wm = getWindowManager();
        Display display = wm.getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        screenWidth = size.x;
        screenHeight = size.y;

        speedBox = Math.round(screenHeight / 60F);
        speedOrange = Math.round(screenWidth / 60F);
        speedPink = Math.round(screenWidth / 36F);
        speedBlack = Math.round(screenWidth / 45F);


        orange.setX(-80);
        orange.setY(-80);
        pink.setX(-80);
        pink.setY(-80);
        black.setX(-80);
        black.setY(-80);


    }

    public void AnhXa() {
        scoreLabel = (TextView) findViewById(R.id.scoreLabel);
        tapToStar = (TextView) findViewById(R.id.starLabel);
        box = (ImageView) findViewById(R.id.box);
        box_bg = (ImageView) findViewById(R.id.box_bg);
        black = (ImageView) findViewById(R.id.black);
        orange = (ImageView) findViewById(R.id.orange);
        pink = (ImageView) findViewById(R.id.pink);
        quitGame = (RelativeLayout) findViewById(R.id.quitGame);
        timeLabel = (TextView) findViewById(R.id.timeLabel);
        levelLabel = (TextView) findViewById(R.id.levelLabel);
        relativeLayout = (RelativeLayout) findViewById(R.id.layoutBg);

        linearLayout = (LinearLayout) findViewById(R.id.activity_main);
    }

    public void changePost() {

        hitCheck();

        //orange

        orangeX -= speedOrange;
        if (orangeX < 0) {
            orangeX = screenWidth + 20;
            orangeY = (int) Math.floor(Math.random() * (frameHieght - orange.getHeight()));
        }
        orange.setX(orangeX);
        orange.setY(orangeY);

        //black

        blackX -= speedBlack;

        if (blackX < 0) {
            blackX = screenWidth + 5;
            blackY = (int) Math.floor(Math.random() * (frameHieght - black.getHeight()));
        }

        black.setX(blackX);
        black.setY(blackY);

        pinkX -= speedPink;
        if (pinkX < 0) {
            pinkX = screenWidth + 500;
            pinkY = (int) Math.floor(Math.random() * (frameHieght - pink.getHeight()));
        }

        pink.setX(pinkX);
        pink.setY(pinkY);

//        cloudX -= 20;
//        if(cloudX < 0){
//            cloudX = screenWidth + 100;
//            cloudY =  (int) Math.floor(Math.random() * (frameHieght - cloud.getHeight()));
//           }
//
//        cloud.setX(cloudX);
//        cloud.setY(cloudY);

//move box
        if (action_flg == true) {

            //touch
            boxY -= speedBox;
        } else {
            //release
            boxY += speedBox;
        }
        if (boxY < 0) boxY = 0;

        if (boxY > frameHieght - boxSize) boxY = frameHieght - boxSize;
        box.setY(boxY);

        scoreLabel.setText("Score: " + score);
    }

    public void hitCheck() {
        //if the center of the ball is in the box, it counts as a hit
        //orange

        int orangeCenterX = orangeX + orange.getWidth() / 2;
        int orangeCenterY = orangeY + orange.getHeight() / 2;

        // 0 <= orangeCenterX <= boxWidth;
        //boxY <= orangeCenterY <= boxY + boxHeight;

        if (0 <= orangeCenterX && orangeCenterX <= boxSize
                &&
                boxY <= orangeCenterY && orangeCenterY <= boxY + boxSize) {

            score += 5;
            orangeX = -10;
            soundPlayer.playHitSound();
        }

        //pink

        int pinkCenterX = pinkX + pink.getWidth() / 2;
        int pinkCenterY = pinkY + pink.getHeight() / 2;

        // 0 <= orangeCenterX <= boxWidth;
        //boxY <= orangeCenterY <= boxY + boxHeight;

        if (0 <= pinkCenterX && pinkCenterX <= boxSize
                &&
                boxY <= pinkCenterY && pinkCenterY <= boxY + boxSize) {

            score += 10;
            pinkX = -10;
            soundPlayer.playHitSound();
        }

        int blackCenterX = blackX + black.getWidth() / 2;
        int blackCenterY = blackY + black.getHeight() / 2;

        // 0 <= orangeCenterX <= boxWidth;
        //boxY <= orangeCenterY <= boxY + boxHeight;

        if (0 <= blackCenterX && blackCenterX <= boxSize
                &&
                boxY <= blackCenterY && blackCenterY <= boxY + boxSize) {


            //game over
            timer.cancel();
            timer = null;

            soundPlayer.playOverSound();

            showGameOver();

            //
        }
    }

    public void showGameOver() {
        AlertDialog.Builder dialog = new AlertDialog.Builder(this);
        dialog.setCancelable(false);
        dialog.setPositiveButton("Try again", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Intent intent = getIntent();
                intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                finish();
                startActivity(intent);
                overridePendingTransition(0, 0);

            }
        });

        int hightScoreLabel;
        SharedPreferences settings = getSharedPreferences("GAME_DATA", Context.MODE_PRIVATE);
        int hightScore = settings.getInt("HIGHT_SCORE", 0);
        if(score > hightScore){
            hightScoreLabel = score;
            SharedPreferences.Editor editor = settings.edit();
            editor.putInt("HIGHT_SCORE", score);
            editor.commit();
        }else{
            hightScoreLabel = hightScore;
        }
        dialog.setTitle("Game Over");
        dialog.setMessage("Your Score: " + score + "\nHight Score: " + hightScore);
        AlertDialog alertDialog = dialog.create();
        alertDialog.show();


    }

    public boolean onTouchEvent(MotionEvent me) {
        box.setVisibility(View.VISIBLE);

        if (star_flg == false) {

            star_flg = true;

            FrameLayout frameLayout = (FrameLayout) findViewById(R.id.frame);
            frameHieght = frameLayout.getHeight();

            boxY = (int) box.getY();
            boxSize = box.getHeight();

            relativeLayout.setVisibility(View.VISIBLE);
            levelLabel.setVisibility(View.VISIBLE);
            timeLabel.setVisibility(View.VISIBLE);
            scoreLabel.setVisibility(View.VISIBLE);
            tapToStar.setVisibility(View.GONE);
            box_bg.setVisibility(View.GONE);
            quitGame.setVisibility(View.GONE);
            timer.schedule(new TimerTask() {
                @Override
                public void run() {
                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            changePost();
                        }
                    });
                }
            }, 0, 20);

        } else {
            if (me.getAction() == MotionEvent.ACTION_DOWN) {
                action_flg = true;
                soundPlayer.playFlyrSound();
            } else if (me.getAction() == MotionEvent.ACTION_UP) {
                action_flg = false;
            }
        }

        return true;
    }

    @Override
    public boolean dispatchKeyEvent(KeyEvent event) {
        if (event.getAction() == KeyEvent.ACTION_DOWN) {
            switch (event.getKeyCode()) {
                case KeyEvent.KEYCODE_BACK:
                    return true;
            }
        }

        return super.dispatchKeyEvent(event);
    }

    public void quitGame(View view){
        Intent intent = new Intent(getApplicationContext(), ActivityStart.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
        finish();
        startActivity(intent);
        overridePendingTransition(0, 0);
    }
}
