package model;

import java.util.HashSet;
import java.util.Set;

public class Ship
{
    private final int length;
    private final int[] positions;
    private final Set<Integer> destroyedPos = new HashSet<>();
    private int numTouches = 0;
    private boolean onDestruction = false;
    private boolean sunk = false;

    public Ship(int length)
    {
        this.length = length;
        positions = new int[length];
    }

    public int getNumTouches() {
        return numTouches;
    }

    public void incrementTouches() {
        numTouches++;
    }

    public void setPosition(int posX, int posY)
    {
        int i = 0;
        while(positions[i] != 0 && i < length) i++;
        positions[i] = (posX * 10) + posY;
    }

    public void setDestroyedPos(int pos)
    {
        if(!isOnDestruction()) onDestruction = true;
        destroyedPos.add(pos);
    }

    public boolean isOnDestruction() {
        return onDestruction;
    }

    public int getLength()
    {
        return length;
    }

    public int[] getPositions()
    {
        return positions;
    }

    public Set<Integer> getDestroyedPos()
    {
        return destroyedPos;
    }

    public boolean touchedOrSunk()
    {
        if(numTouches == length)
        {
            onDestruction = false;
            sunk = true;
        }
        return numTouches == length;
    }

    public void setOnDestruction(boolean onDestruction) {
        this.onDestruction = onDestruction;
    }

    public void setSunk(boolean sunk)
    {
        this.sunk = sunk;
    }
}
