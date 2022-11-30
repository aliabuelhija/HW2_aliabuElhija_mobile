package com.example.aliabuelhija_hw1_mobileapplications;

import java.util.Arrays;
import java.util.Random;

public class GameManager {
    public static final int MAX_LIVES = 3;
    public static final int COLUMNS = 3;
    public static final int ROWS = 5;
    private int lives = MAX_LIVES;
    public boolean[][] activeBombs;
    public boolean[] lifes;
    private int carIndex;
    boolean isHit;
    boolean finish;

    public GameManager() {
        carIndex=1;
        activeBombs=new boolean[ROWS][COLUMNS];
        initLives();
    }
    public void initLives(){
        lifes=new boolean[3];
        Arrays.fill(lifes, true);
    }
    public void reduceLives() {
        lives--;
    }

    public  int getROWS() {
        return ROWS;
    }

    public  int getCOLUMNS() {
        return COLUMNS;
    }


    public int getRandom(){
        Random rand = new Random();
        return rand.nextInt(COLUMNS);
    }
    public boolean isActive(int row,int col) {
        return activeBombs[row][col];
    }
    public void updateGame() {
        for (int i = getROWS()-1; i >=0;i--){
            for (int j = 0; j <getCOLUMNS();j++){
                if( isActive(i,j) && i == getROWS()-1) {
                    activeBombs[i][j] = false;

                    if (j == carIndex) {
                        isHit = true;
                        reduceLives();
                        lifes[lives] = false;

                        if (lives == 0)
                            finish = true;


                    }
                }
                else if(i != getROWS()-1){
                    activeBombs[i+1][j]=activeBombs[i][j];

                }
            }
        }
    }
    public void updateNewBomb(){
        int col =getRandom();
        for(int i = 0;i<getCOLUMNS();i++){
            activeBombs[0][i]= col == i;
        }
    }
    public void update(){
        updateGame();
        updateNewBomb();
    }

    public void setHit(boolean hit) {
        isHit = hit;
    }

    public boolean[] getLifes() {
        return lifes;
    }

    public boolean isFinish() {
        return finish;
    }

    public void setCarIndex(int carIndex) {
        this.carIndex = carIndex;
    }
}
