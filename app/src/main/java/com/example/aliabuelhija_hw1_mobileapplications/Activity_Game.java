package com.example.aliabuelhija_hw1_mobileapplications;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatImageButton;
import androidx.appcompat.widget.AppCompatImageView;


import android.content.Context;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.os.Handler;

import android.view.View;
import android.widget.ImageView;


import com.google.android.material.imageview.ShapeableImageView;

public class Activity_Game extends AppCompatActivity {
    private AppCompatImageButton game_BTN_left;
    private AppCompatImageButton game_BTN_right;
    private ImageView[] game_IMG_cars;
    private ImageView[][] game_IMG_bombs;
    private ImageView[][] game_IMG_gifts;

    private ShapeableImageView[] game_IMG_hearts;
    private AppCompatImageView street_IMG_background;
    final int DELAY = 1000;
    final Handler handler = new Handler();
    private GameManager gameManager;
    private SensorManager sensorManager;
    private Sensor sensor;
    private boolean sensorMode = false;
    public static final String SENSOR_MODE = "SENSOR_MODE";
    int score=0;
    private final String background = "https://opengameart.org/sites/default/files/background-1_0.png";



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);
        GameUtils.hideSystemUI(this);
        findView();
        GameUtils.setBackground(this,street_IMG_background, background);

        gameManager = new GameManager();
        sensorMode = getIntent().getExtras().getBoolean(SENSOR_MODE);

        if (sensorMode)
            moveCarBySensors();
        else{
            moveCarByButtons();
        }
        startTimer();

    }

    @Override
    protected void onPause() {
        super.onPause();
        stopTimer();
        if (sensorMode) {
        stop();
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        startTimer();
        if (sensorMode) {
           start();
        }
    }
    private void findView() {
        game_BTN_left = findViewById(R.id.game_BTN_left);
        game_BTN_right = findViewById(R.id.game_BTN_right);
        street_IMG_background = findViewById(R.id.game_IMG_background);
        initBombArr();
        initGiftArr();
        initHeartArr();
        initCarArr();
    }

    private void initCarArr() {
        game_IMG_cars = new ImageView[]{
                findViewById(R.id.game_IMG_car1),
                findViewById(R.id.game_IMG_car2),
                findViewById(R.id.game_IMG_car3),
                findViewById(R.id.game_IMG_car4),
                findViewById(R.id.game_IMG_car5),


        };
    }

    private void initHeartArr() {
        game_IMG_hearts = new ShapeableImageView[]{
                findViewById(R.id.game_IMG_heart3),
                findViewById(R.id.game_IMG_heart2),
                findViewById(R.id.game_IMG_heart1),
        };
    }

    private void initBombArr() {
        game_IMG_bombs = new ImageView[][]{
                {findViewById(R.id.game_IMG_bomb1),
                        findViewById(R.id.game_IMG_bomb2),
                        findViewById(R.id.game_IMG_bomb3),
                        findViewById(R.id.game_IMG_bomb4),
                        findViewById(R.id.game_IMG_bomb5)},
                {findViewById(R.id.game_IMG_bomb6),
                        findViewById(R.id.game_IMG_bomb7),
                        findViewById(R.id.game_IMG_bomb8),
                        findViewById(R.id.game_IMG_bomb9),
                        findViewById(R.id.game_IMG_bomb10)},
                {findViewById(R.id.game_IMG_bomb11),
                        findViewById(R.id.game_IMG_bomb12),
                        findViewById(R.id.game_IMG_bomb13),
                        findViewById(R.id.game_IMG_bomb14),
                        findViewById(R.id.game_IMG_bomb15)},
                {findViewById(R.id.game_IMG_bomb16),
                        findViewById(R.id.game_IMG_bomb17),
                        findViewById(R.id.game_IMG_bomb18),
                        findViewById(R.id.game_IMG_bomb19),
                        findViewById(R.id.game_IMG_bomb20)},
                {findViewById(R.id.game_IMG_bomb21),
                        findViewById(R.id.game_IMG_bomb22),
                        findViewById(R.id.game_IMG_bomb23),
                        findViewById(R.id.game_IMG_bomb24),
                        findViewById(R.id.game_IMG_bomb25)}
        };
    }
    private void initGiftArr() {
        game_IMG_gifts = new ImageView[][]{
                {findViewById(R.id.game_IMG_gift1),
                        findViewById(R.id.game_IMG_gift2),
                        findViewById(R.id.game_IMG_gift3),
                        findViewById(R.id.game_IMG_gift4),
                        findViewById(R.id.game_IMG_gift5)},
                {findViewById(R.id.game_IMG_gift6),
                        findViewById(R.id.game_IMG_gift7),
                        findViewById(R.id.game_IMG_gift8),
                        findViewById(R.id.game_IMG_gift9),
                        findViewById(R.id.game_IMG_gift10)},
                {findViewById(R.id.game_IMG_gift11),
                        findViewById(R.id.game_IMG_gift12),
                        findViewById(R.id.game_IMG_gift13),
                        findViewById(R.id.game_IMG_gift14),
                        findViewById(R.id.game_IMG_gift15)},
                {findViewById(R.id.game_IMG_gift16),
                        findViewById(R.id.game_IMG_gift17),
                        findViewById(R.id.game_IMG_gift18),
                        findViewById(R.id.game_IMG_gift19),
                        findViewById(R.id.game_IMG_gift20)},
                {findViewById(R.id.game_IMG_gift21),
                        findViewById(R.id.game_IMG_gift22),
                        findViewById(R.id.game_IMG_gift23),
                        findViewById(R.id.game_IMG_gift24),
                        findViewById(R.id.game_IMG_gift25)}
        };
    }


    private void refreshHearts() {
        boolean[] lifes = gameManager.getLifes();
        for (int i = 0; i < gameManager.getLifes().length; i++) {
            if (lifes[i])
                game_IMG_hearts[i].setVisibility(View.VISIBLE);
            else
                game_IMG_hearts[i].setVisibility(View.INVISIBLE);

        }
    }

    private void refreshBombUI() {
        for (int i = 0; i < gameManager.getROWS(); i++) {
            for (int j = 0; j < gameManager.getCOLUMNS(); j++) {
                if (gameManager.isActive(i, j)) {
                    game_IMG_bombs[i][j].setVisibility(View.VISIBLE);
                } else {
                    game_IMG_bombs[i][j].setVisibility(View.INVISIBLE);
                }
            }
        }
    }
    private void refreshGiftUI() {
        for (int i = 0; i < gameManager.getROWS(); i++) {
            for (int j = 0; j < gameManager.getCOLUMNS(); j++) {
                if (gameManager.isGiftActive(i,j)) {
                    game_IMG_gifts[i][j].setVisibility(View.VISIBLE);
                } else {
                    game_IMG_gifts[i][j].setVisibility(View.INVISIBLE);
                }
            }
        }
    }

    private void refreshUI() {
        gameManager.update();
        if (gameManager.isFinish()) {
            GameUtils.vibrate(this);
            refreshHearts();
            GameUtils.toast(this,"GAME OVER");
            Intent gameOverIntent = new Intent(Activity_Game.this, Activity_GameOver.class);
            Bundle bundle = new Bundle();
            bundle.putInt("score", score);
            gameOverIntent.putExtras(bundle);
            startActivity(gameOverIntent);
            this.finish();
            stopTimer();
            finish();
        } else {
            if (gameManager.isHit) {
                refreshHearts();
                GameUtils.toast(this,"WATCH OUT!!!!");
               GameUtils.vibrate(this);
                gameManager.setHit(false);
            }
          else  if (gameManager.isGift()) {
                score+=1000;
                GameUtils.toast(this,"YOU GOOOT GIFT!!!! +1000");
                GameUtils.vibrate(this);
                gameManager.setGiftHit(false);
            }


        }
        refreshGiftUI();
        refreshBombUI();
    }

    Runnable runnable = new Runnable() {
        public void run() {
            handler.postDelayed(this, DELAY);
            score+=100;
            refreshUI();
        }
    };


    private void startTimer() {
        handler.postDelayed(runnable, DELAY);

        if (sensorMode) {
            start();
        }
    }

    private void stopTimer() {
        handler.removeCallbacks(runnable);

        if (sensorMode) {
            stop();
        }
    }
    //// BUTTON MODE
    private void moveCarByButtons() {
        game_BTN_left.setOnClickListener(v -> {
            if (gameManager.getCarIndex() != 0) {
                game_IMG_cars[gameManager.getCarIndex()].setVisibility(View.INVISIBLE);
                game_IMG_cars[gameManager.getCarIndex() - 1].setVisibility(View.VISIBLE);
                gameManager.setCarIndex(gameManager.getCarIndex() - 1);
            }

        });
        game_BTN_right.setOnClickListener(view -> {
            if(gameManager.getCarIndex()!=gameManager.getCOLUMNS()-1) {
                game_IMG_cars[gameManager.getCarIndex()].setVisibility(View.INVISIBLE);
                game_IMG_cars[gameManager.getCarIndex()+1].setVisibility(View.VISIBLE);
                gameManager.setCarIndex(gameManager.getCarIndex() +1);
            }

        });
    }
    /// SENSOR MODE
    private void moveCarBySensors() {
        game_BTN_right.setVisibility(View.INVISIBLE);
        game_BTN_left.setVisibility(View.INVISIBLE);
        sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        sensor = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
    }
    private final SensorEventListener sensorEventListener = new SensorEventListener() {

        @Override
        public void onSensorChanged(SensorEvent sensorEvent) {
            moveCarBySensors(sensorEvent.values[0]);
        }

        @Override
        public void onAccuracyChanged(Sensor sensor, int i) {

        }
    };
    public void start() {
        sensorManager.registerListener(sensorEventListener, sensor, SensorManager.SENSOR_DELAY_NORMAL);
    }
    public void stop() {
        sensorManager.unregisterListener(sensorEventListener);
    }
    private void moveCarBySensors(float x) {
        game_IMG_cars[gameManager.getCarIndex()].setVisibility(View.INVISIBLE);
        if (x < -4) {
            gameManager.setCarIndex(4);
        }  else if (-3.5 < x && x < -1.5) {
            gameManager.setCarIndex(3);
        }else if (-1 < x && x< 1) {
            gameManager.setCarIndex(2);
        } else if (1.5 < x && x < 3.5) {
            gameManager.setCarIndex(1);
        } else if (4 < x) {
            gameManager.setCarIndex(0);
        }
        game_IMG_cars[gameManager.getCarIndex()].setVisibility(View.VISIBLE);
    }

}