package model;

public class Game
{
    private final Ship [][] matrixBool = new Ship[9][9];
    private final Ship[] ships;
    private final int numVessels;
    private final int numCruises;
    private final int numCarriers;
    private int sunkenShips = 0;

    /**
     * Constructor
     *
     * @param numVessels Number of vessels created
     * @param numCruises Number cruises created
     * @param numCarriers Numbers of carriers created
     */
    public Game(int numVessels, int numCruises, int numCarriers)
    {
        this.numVessels = numVessels;
        this.numCruises = numCruises;
        this.numCarriers = numCarriers;
        ships = new Ship[numVessels + numCruises + numCarriers];
        newGame();
        createShip(ships);
    }

    /**
     * Fill the ship arrays with the correspondent ship objects and 'matrixShots' with standard marks
     *
     */
    public void newGame()
    {
        for (int i = 0; i < ships.length; i++)
        {
            if(i < numVessels) ships[i] = new Ship(2);
            else if(i >= numVessels && i < (numVessels + numCruises)) ships[i] = new Ship(3);
            else ships[i] = new Ship(4);
        }
    }

    /**
     * Look for an empty space in 'matrixBool' and assign this space to the ship object
     *
     * @param ships Array with Ships objects
     */
    public void createShip(Ship[] ships)
    {
        for(int i = 0; i < ships.length; i++)
        {
            int row = (int)(Math.random()*9);
            int column = (int)(Math.random()*9);
            int direction = (int) (Math.random() * 4);        // 0 or 2 = axis Y, 1 or 3 = axis X
            boolean shipOnTheWay = false;                     // it controls conflicts with other ships when creating new ones

            switch(direction)
            {
                case 0:
                    column = adjustPosition(ships[i],column);                // see method description...

                    for(int k = 0; k < ships[i].getLength(); k++)         // checks if the positions for the new ship are free
                    {
                        if(matrixBool[row][column - k] != null)
                        {
                            shipOnTheWay = true;
                            break;
                        }
                    }
                    if(!shipOnTheWay)                                   // if there is a ship on the way of the new ship, goes to the next case trying other direction
                    {                                                   // if there isn't, modifies the matrix and adds the position inside the ship object
                        for (int j = 0; j < ships[i].getLength(); j++)
                        {
                            matrixBool[row][column - j] = ships[i];
                            ships[i].setPosition(row,column - j);
                        }
                        break;
                    }

                case 1:
                    row = adjustPosition(ships[i],row);

                    shipOnTheWay = false;
                    for(int k = 0; k < ships[i].getLength(); k++)
                    {
                        if(matrixBool[row + k][column] != null)
                        {
                            shipOnTheWay = true;
                            break;
                        }
                    }
                    if(!shipOnTheWay)
                    {
                        for (int j = 0; j < ships[i].getLength(); j++)
                        {
                            matrixBool[row + j][column] = ships[i];
                            ships[i].setPosition(row + j,column);
                        }
                        break;
                    }

                case 2:
                    column = adjustPosition(ships[i],column);

                    shipOnTheWay = false;
                    for(int k = 0; k < ships[i].getLength(); k++)
                    {
                        if(matrixBool[row][column + k] != null)
                        {
                            shipOnTheWay = true;
                            break;
                        }
                    }
                    if(!shipOnTheWay) {
                        for (int j = 0; j < ships[i].getLength(); j++)
                        {
                            matrixBool[row][column + j] = ships[i];
                            ships[i].setPosition(row,column + j);
                        }
                        break;
                    }

                case 3:
                    row = adjustPosition(ships[i],row);

                    shipOnTheWay = false;
                    for(int k = 0; k < ships[i].getLength(); k++)
                    {
                        if(matrixBool[row - k][column] != null)
                        {
                            shipOnTheWay = true;
                            break;
                        }
                    }
                    if(!shipOnTheWay) {
                        for (int j = 0; j < ships[i].getLength(); j++)
                        {
                            matrixBool[row - j][column] = ships[i];
                            ships[i].setPosition(row - j,column);
                        }
                        break;
                    }
                default:
                    i--;      // if there isn't adequate conditions to assign positions, decrements 'i' to try another loop

            }
        }
    }

    /**
     * Evaluates if the values of row or column exceed the total length of the array.
     * In that case, the values are modified to fit at the end or beginning of the row/column.
     *
     * @param ship variable that contains a Ship object
     * @param rowOrColumn The value belongs to a row or a column, it depends on who calls it
     * @return Modified int value for a row or column
     */
    public int adjustPosition(Ship ship, int rowOrColumn)
    {
        if ((rowOrColumn + ship.getLength()) > matrixBool.length - 1)
        {
            rowOrColumn = matrixBool.length - 1 - ship.getLength();
        }
        else if (rowOrColumn - ship.getLength() < 0)
        {
            rowOrColumn = ship.getLength();
        }

        return rowOrColumn;
    }

    /**
     * Checks if the position introduced by parameter belongs to a ship position
     *
     * @param pos position selected by the user (e.g 'B6')
     */
    public int launchMissile(int pos)
    {
        int posY = pos % 10;
        int posX = (pos - posY) / 10;
        int successfulShot;             // 0 means failed shot, 1 means touched but not sunk and 2 means touched and sunk

        if(matrixBool[posX][posY] != null)
        {
            successfulShot = 1;
            Ship ship = checkShips(posX,posY);                  // check which ship was hit
            ship.incrementTouches();
            ship.setDestroyedPos(pos);

            if(ship.touchedOrSunk())
            {
                successfulShot = 2;
                ship.setOnDestruction(false);
                ship.setSunk(true);
                sunkenShips++;                   // if the ship is destroyed, 'sunkenShips' is incremented;
            }
        }
        else
        {
            successfulShot = 0;
        }

        return successfulShot;
    }

    /**
     * Checks which was the ship that was hit
     * @param posX row value
     * @param posY column value
     * @return Ship object
     */
    public Ship checkShips(int posX, int posY)
    {
        int pos = (posX * 10) + posY;
        Ship auxShip = null;

        mainLoop:
        for (Ship ship : ships)
        {
            for (int p : ship.getPositions())
            {
                if (p == pos)
                {
                    auxShip = ship;             // when the loop finds the correct ship, assigns it to a new variable
                    break mainLoop;             // once the work is done, we don't need more loops
                }
            }
        }

        return auxShip;
    }

    /**
     * Checks if the number of sunken ships are equal to the total number of ships
     *
     * @return if 'true', the game ends
     */
    public boolean hasWon()
    {
        return sunkenShips == ships.length;
    }

    public Ship[] getShips ()
    {
        return ships;
    }
}
