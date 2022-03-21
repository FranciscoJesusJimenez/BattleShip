package controller;

import model.Game;
import model.GameAI;

public class Controller
{
    private final Game model;
    private final GameAI modelAI;
    private boolean hasWon = false;
    private String winner = "";

    public Controller()
    {
        model = new Game(2,2,2);
        modelAI = new GameAI(2,2,2);
    }

    public int launchMissile(int pos)
    {
        return model.launchMissile(pos);
    }

    public int launchMissileAI()
    {
        return modelAI.launchMissileAI();
    }

    public int getAIPos()
    {
        return modelAI.getAIPos();
    }

    public boolean hasWon()
    {
        if(model.hasWon())
        {
            hasWon = true;
            winner = "Player has won";
        }
        else if (modelAI.hasWon())
        {
            hasWon = true;
            winner = "AI has won";
        }
        return hasWon;
    }
    public String getWinner()
    {
        return winner;
    }
}
