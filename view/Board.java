package view;

import javax.swing.*;

public class Board extends JFrame
{
    private final char[] letters = {'A','B','C','D','E','F','G','H','I'};

    public void showBoard(String[][] matrixShots)
    {
        for(int k = 0; k <= letters.length; k++)
        {
            if(k > 0) System.out.print(" "+k+" ");
            else System.out.print("  ");
        }

        System.out.println();

        for(int i = 0; i < matrixShots.length; i++)
        {
            System.out.print(letters[i]+" ");

            for(int j = 0; j < matrixShots.length; j++)
            {
                System.out.print(matrixShots[i][j]);
            }
            System.out.println();
        }
    }


    public void afterShot(boolean shot, boolean user)
    {
        if(user) System.out.println("USUARIO:");
        else System.out.println("MAQUINA:");
        if(shot)
        {
            /*if(sunk)
            {
                System.out.println("Touched and sunk!!!!");
            }
            else
            {
                System.out.println("Touched!!!");
            }*/
        }
        else
        {
            System.out.println("You shot the water!!");
        }
    }
}
