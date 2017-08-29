package com.javarush.task.task37.task3712;

/**
 * Created by ua053202 on 29.08.2017.
 */
public abstract class Game {
    abstract void prepareForTheGame();
    abstract void playGame();
    abstract void congratulateWinner();

    public void run()
    {
        prepareForTheGame();
        playGame();
        congratulateWinner();
    }
}
