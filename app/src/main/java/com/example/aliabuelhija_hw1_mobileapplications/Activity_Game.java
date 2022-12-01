package com.example.aliabuelhija_hw1_mobileapplications;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatImageButton;
import androidx.appcompat.widget.AppCompatImageView;

import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.VibrationEffect;
import android.os.Vibrator;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import com.google.android.material.imageview.ShapeableImageView;

public class Activity_Game extends AppCompatActivity {
    private AppCompatImageButton game_BTN_left;
    private AppCompatImageButton game_BTN_right;
    private ImageView[] game_IMG_cars;
    private ImageView[][] game_IMG_bombs;
    private ShapeableImageView[] game_IMG_hearts;
    private AppCompatImageView street_IMG_background;
    final int DELAY = 500;
    final Handler handler = new Handler();
    private GameManager gameManager;


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);
        findView();
        Glide
                .with(this)
                .load("https://opengameart.org/sites/default/files/background-1_0.png").into((street_IMG_background));


        gameManager = new GameManager();
        initViews();
        startTimer();
    }

    @Override
    protected void onPause() {
        super.onPause();
        stopTimer();
    }

    private void initViews() {
        game_BTN_left.setOnClickListener(v -> {
            if (game_IMG_cars[1].isShown()) {
                game_IMG_cars[0].setVisibility(View.VISIBLE);
                game_IMG_cars[1].setVisibility(View.INVISIBLE);
                gameManager.setCarIndex(0);
            } else if (game_IMG_cars[2].isShown()) {
                game_IMG_cars[1].setVisibility(View.VISIBLE);
                game_IMG_cars[2].setVisibility(View.INVISIBLE);
                gameManager.setCarIndex(1);

            }
        });
        game_BTN_right.setOnClickListener(view -> {
            if (game_IMG_cars[0].isShown()) {
                game_IMG_cars[0].setVisibility(View.INVISIBLE);
                game_IMG_cars[1].setVisibility(View.VISIBLE);
                game_IMG_cars[2].setVisibility(View.INVISIBLE);
                gameManager.setCarIndex(1);

            } else if (game_IMG_cars[1].isShown()) {
                game_IMG_cars[0].setVisibility(View.INVISIBLE);
                game_IMG_cars[1].setVisibility(View.INVISIBLE);
                game_IMG_cars[2].setVisibility(View.VISIBLE);
                gameManager.setCarIndex(2);

            }
        });
    }

    private void findView() {
        game_BTN_left = findViewById(R.id.game_BTN_left);
        game_BTN_right = findViewById(R.id.game_BTN_right);
        street_IMG_background=findViewById(R.id.street_IMG_background);
        initBombArr();
        initHeartArr();
        initCarArr();
    }

    private void initCarArr() {
        game_IMG_cars = new ImageView[]{
                findViewById(R.id.game_IMG_carLeft),
                findViewById(R.id.game_IMG_carCenter),
                findViewById(R.id.game_IMG_carRight),
        };
    }

    private void initHeartArr() {
        game_IMG_hearts = new ShapeableImageView[]{
                findViewById(R.id.game_IMG_heart3),
                findViewById(R.id.game_IMG_heart2),
                findViewById(R.id.game_IMG_heart1),
        };
    }

    private void initBombArr()
    {
        game_IMG_bombs = new ImageView[][]{
                {findViewById(R.id.game_IMG_bomb1),
                        findViewById(R.id.game_IMG_bomb2),
                        findViewById(R.id.game_IMG_bomb3)},

                {findViewById(R.id.game_IMG_bomb4),
                        findViewById(R.id.game_IMG_bomb5),
                        findViewById(R.id.game_IMG_bomb6)},

                {findViewById(R.id.game_IMG_bomb7),
                        findViewById(R.id.game_IMG_bomb8),
                        findViewById(R.id.game_IMG_bomb9)},

                {findViewById(R.id.game_IMG_bomb10),
                        findViewById(R.id.game_IMG_bomb11),
                        findViewById(R.id.game_IMG_bomb12)},

                {findViewById(R.id.game_IMG_bomb13),
                        findViewById(R.id.game_IMG_bomb14),
                        findViewById(R.id.game_IMG_bomb15)}
        };
    }
    private void refreshHearts() {
        boolean []lifes=gameManager.getLifes();
        for (int i =0;i<gameManager.getLifes().length;i++){
            if (lifes[i])
                game_IMG_hearts[i].setVisibility(View.VISIBLE);
            else
                game_IMG_hearts[i].setVisibility(View.INVISIBLE);

        }
    }

    private void refreshBombUI() {
        for (int i =0;i<gameManager.getROWS();i++){
            for (int j=0;j<gameManager.getCOLUMNS();j++){
                if(gameManager.isActive(i,j)) {
                    game_IMG_bombs[i][j].setVisibility(View.VISIBLE);
                }
                else{
                    game_IMG_bombs[i][j].setVisibility(View.INVISIBLE);
                }
            }
        }
    }
    private void refreshUI(){
        gameManager.update();
        if(gameManager.isFinish()){
            vibrate();
            refreshHearts();
            refreshBombUI();
            Toast
                    .makeText(this, "Game Over", Toast.LENGTH_SHORT)
                    .show();
            stopTimer();
            finish();
        }
        else{
            if(gameManager.isHit){
                refreshHearts();
                Toast
                        .makeText(this, "OH HIT", Toast.LENGTH_SHORT)
                        .show();
                vibrate();
                gameManager.setHit(false);
            }
        }
        refreshBombUI();
    }
    Runnable runnable = new Runnable() {
        public void run() {
            handler.postDelayed(this, DELAY);
            refreshUI();
        }
    };


    private void startTimer()
    {
        handler.postDelayed(runnable, DELAY);

    }

    private void stopTimer() {
        handler.removeCallbacks(runnable);
    }
    private void vibrate() {
        Vibrator v = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
        // Vibrate for 500 milliseconds
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            v.vibrate(VibrationEffect.createOneShot(500, VibrationEffect.DEFAULT_AMPLITUDE));
        } else {
            //deprecated in API 26
            v.vibrate(500);
        }
    }

}
