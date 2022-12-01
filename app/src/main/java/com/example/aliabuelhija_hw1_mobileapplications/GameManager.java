package com.example.aliabuelhija_hw1_mobileapplications;

import java.util.Arrays;
import java.util.Random;

public class GameManager {
    public static final int MAX_LIVES = 3;
    public static final int COLUMNS = 5;
    public static final int ROWS = 5;
    private int lives = MAX_LIVES;
    public boolean[][] activeBombs;
    public boolean[][] activeGifts;

    public boolean[] lifes;
    private int carIndex;
    boolean isHit;
    boolean finish;
    boolean gift;

    public GameManager() {
        carIndex=2;
        activeBombs=new boolean[ROWS][COLUMNS];
        activeGifts=new boolean[ROWS][COLUMNS];

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
    public boolean isGiftActive(int row,int col) {
        return activeGifts[row][col];
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
    public void updateGifts() {
        for (int i = getROWS()-1; i >=0;i--){
            for (int j = 0; j <getCOLUMNS();j++){
                if( isGiftActive(i,j) && i == getROWS()-1) {
                    activeGifts[i][j] = false;

                    if (j == carIndex) {
                        gift=true;

                    }
                }
                else if(i != getROWS()-1){
                    activeGifts[i+1][j]=activeGifts[i][j];

                }
            }
        }
    }

    public void updateNew(){
        int col =getRandom();
        for(int i = 0;i<getCOLUMNS();i++)
            activeBombs[0][i] = col == i;
        int colForGift = getRandom();
        while(col == colForGift){
            colForGift=getRandom();
        }
        for(int i = 0;i<getCOLUMNS();i++)
            activeGifts[0][i] = colForGift == i;

    }
    public void update(){
        updateGame();
        updateGifts();
        updateNew();
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

    public int getCarIndex() {
        return carIndex;
    }
    public void setGiftHit(boolean hit) {
        gift = hit;
    }

    public boolean isGift() {
        return gift;
    }
}
