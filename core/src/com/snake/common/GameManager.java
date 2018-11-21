package com.snake.common;

public class GameManager {

    // == constants ==
    public static final GameManager INSTANCE = new GameManager();

    // == attributes ==
    private GameState state = GameState.READY;

    // == constructor ==
    private GameManager(){}

    // == public methods ==
    public boolean isReady(){
        return state.isReady();
    }

    public boolean isPlaying(){
        return state.isPlaying();
    }

    public boolean isGameOver(){
        return state.isGameOver();
    }

    public void setPlaying(){
        state = GameState.PLAYING;
    }

    public void setGameOver(){
        state = GameState.GAME_OVER;
    }
}
