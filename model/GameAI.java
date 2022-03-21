package model;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public class GameAI extends Game
{
    private Set<Integer> triedPos = new HashSet<>();
    private final Ship[] ships;
    private int lastPos;

    public GameAI(int numVessels, int numCruises, int numCarriers)
    {
        super(numVessels, numCruises, numCarriers);
        ships = getShips();
    }

    public boolean hasWon()
    {
        return super.hasWon();
    }

    public int smartShot(Ship ship)
    {
        boolean axisX;
        int direction = (int)(Math.random() * 4);
        Integer[] destroyedPos = ship.getDestroyedPos().toArray(new Integer[0]);
        int pos = destroyedPos[0];  // It gets the last number of the ArrayList

        if(ship.getNumTouches() == 1) {
            for(int i = 0; i < 1; i++) {
                switch (direction) {
                    case 0:
                        if (!triedPos.contains(pos - 10)) {
                            pos -= 10;
                            break;
                        }
                    case 1:
                        if (!triedPos.contains(pos + 1)) {
                            pos += 1;
                            break;
                        }
                    case 2:
                        if (!triedPos.contains(pos + 10)) {
                            pos += 10;
                            break;
                        }
                    case 3:
                        if (!triedPos.contains(pos - 1)) {
                            pos -= 1;
                            break;
                        }
                    default:
                        direction = (int)(Math.random() * 4);
                        i--;
                }
            }
        }
        else
        {
            Arrays.sort(destroyedPos);
            int last = destroyedPos[destroyedPos.length-1];
            int first = destroyedPos[0];
            axisX = (first - last < 10 && first - last > -10);

            if(axisX)
            {
                if(!triedPos.contains(first - 1)) pos = first - 1;
                else pos = last + 1;
            }
            else
            {
                if(!triedPos.contains(first - 10)) pos = first - 10;
                else pos = last + 10;
            }
        }

        if(!checkRange(pos)) pos = smartShot(ship);

        return pos;
    }

    public int getRandomPos()
    {
        int pos;
        do
        {
            int posX = (int)(Math.random() * 9);
            int posY = (int)(Math.random() * 9);
            pos = (posX * 10) + posY;
        }
        while(triedPos.contains(pos));

        return pos;
    }

    public boolean checkRange(int pos)  // Eliminable
    {
        boolean valid = true;
        int posY = pos % 10;
        int posX = (pos - posY) / 10;

        if(posY < 0 || posY > 8) valid = false;
        else if (posX < 0 || posX > 8) valid = false;

        return valid;
    }

    public Ship chooseShip()
    {
        Ship ship = null;

        for (int i = 0; i < ships.length; i++)
        {
            if(ships[i].isOnDestruction())
            {
                ship = ships[i];
                break;
            }

        }

        return ship;
    }

    public int launchMissileAI()
    {
        Ship ship = chooseShip();
        int pos;

        if(ship == null)
        {
            pos = getRandomPos();
        }
        else
        {
            pos = smartShot(ship);
        }

        triedPos.add(pos);
        int successfulShot = super.launchMissile(pos);
        lastPos = pos;
        return successfulShot;
    }

    public int getAIPos()
    {
        return lastPos;
    }
}
