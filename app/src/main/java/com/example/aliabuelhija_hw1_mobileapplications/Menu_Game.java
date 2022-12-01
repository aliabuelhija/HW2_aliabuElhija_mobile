package com.example.aliabuelhija_hw1_mobileapplications;


import android.content.Intent;
import android.os.Bundle;


import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatImageView;

import com.google.android.material.button.MaterialButton;

public class Menu_Game  extends AppCompatActivity {
    private MaterialButton menu_BTN_button;
    private MaterialButton menu_BTN_sensor;
    private MaterialButton menu_BTN_highScores;
    private AppCompatImageView menu_IMG_background;
    private final String background = "https://opengameart.org/sites/default/files/background-1_0.png";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.menu_game);
        GameUtils.hideSystemUI(this);
        findView();
        GameUtils.setBackground(this,menu_IMG_background, background);

        clicked();

    }

    private void clicked() {
        menu_BTN_button.setOnClickListener(view -> moveToGame(false));
        menu_BTN_sensor.setOnClickListener(view -> moveToGame(true));
        menu_BTN_highScores.setOnClickListener(view -> moveToScores());
    }

    private void findView() {
        menu_BTN_button = findViewById(R.id.menu_BTN_button);
        menu_BTN_sensor = findViewById(R.id.menu_BTN_sensor);
        menu_BTN_highScores = findViewById(R.id.menu_BTN_highScores);
        menu_IMG_background = findViewById(R.id.menu_IMG_background);

    }
    public void moveToGame(boolean sensorMode) {
        Intent gameIntent = new Intent(this, Activity_Game.class);

        Bundle bundle = new Bundle();
        bundle.putBoolean(Activity_Game.SENSOR_MODE, sensorMode);

        gameIntent.putExtras(bundle);
        startActivity(gameIntent);
    }
    public void moveToScores() {
        Intent ScoreIntent = new Intent(this,Activity_TOP10.class);

        startActivity(ScoreIntent);
    }
}
