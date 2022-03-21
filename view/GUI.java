package view;

import controller.Controller;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class GUI extends JFrame {
    private final Controller controller = new Controller();
    private final int PANEL_WIDTH = 1600;
    private final int PANEL_HEIGHT = 900;
    private final int GRID_SIZE = 9;
    private final JButton[][] buttons = new JButton[GRID_SIZE][GRID_SIZE];
    private final JButton[][] buttonsAI = new JButton[GRID_SIZE][GRID_SIZE];

                                    /* JFRAME STRUCTURE */
    private JFrame frame;
        private JPanel mainPanel;
            private JPanelBackground titlePanel;
                private JLabel titleLabel;

            private JPanelBackground gamePanel;
                private JLabel playerLabel;
                private JPanelBackground playerPanel;
                private JLabel AILabel;
                private JPanelBackground AIPanel;
                private JLabel playerShotLabel;
                private JLabel AIShotLabel;
                private JLabel winnerLabel;

    public void initComponents()
    {
                                        /* LABELS */
        titleLabel = new JLabel();
        titleLabel.setText("BATTLESHIP");
        titleLabel.setForeground(Color.red);
        titleLabel.setBounds(PANEL_WIDTH/2-250,25,500,100);
        titleLabel.setHorizontalAlignment(SwingConstants.CENTER);
        titleLabel.setFont(new Font("Monospaced",Font.BOLD,72));

        playerLabel = new JLabel("Player");
        playerLabel.setBounds(PANEL_WIDTH/2-450,25,400,50);
        playerLabel.setHorizontalAlignment(SwingConstants.CENTER);
        playerLabel.setFont(new Font("Monospaced",Font.ITALIC,48));

        AILabel = new JLabel("Computer");
        AILabel.setBounds(PANEL_WIDTH/2+50,25,400,50);
        AILabel.setHorizontalAlignment(SwingConstants.CENTER);
        AILabel.setFont(new Font("Monospaced",Font.ITALIC,48));

        playerShotLabel = new JLabel();
        playerShotLabel.setOpaque(true);
        playerShotLabel.setBackground(Color.darkGray);
        playerShotLabel.setLayout(null);
        playerShotLabel.setBounds(PANEL_WIDTH/2-450,600,400,50);
        playerShotLabel.setHorizontalAlignment(SwingConstants.CENTER);
        playerShotLabel.setFont(new Font("Monospaced",Font.PLAIN,36));

        AIShotLabel = new JLabel();
        AIShotLabel.setLayout(null);
        AIShotLabel.setOpaque(true);
        AIShotLabel.setBackground(Color.darkGray);
        AIShotLabel.setBounds(PANEL_WIDTH/2+50,600,400,50);
        AIShotLabel.setHorizontalAlignment(SwingConstants.CENTER);
        AIShotLabel.setFont(new Font("Monospaced",Font.PLAIN,36));

        winnerLabel = new JLabel();
        winnerLabel.setLayout(null);
        winnerLabel.setBounds(PANEL_WIDTH/2-450, 500, 900, 200);
        winnerLabel.setHorizontalAlignment(SwingConstants.CENTER);
        winnerLabel.setFont(new Font("Monospaced",Font.BOLD,96));
        winnerLabel.setForeground(Color.RED);
        winnerLabel.setVisible(false);


                                    /* PANELS AND FRAME */

        playerPanel = new JPanelBackground("src\\hundirFlota\\view\\ocean.jpg");
        playerPanel.setBounds(PANEL_WIDTH/2-450,100,400,400);
        playerPanel.setLayout(new GridLayout(GRID_SIZE,GRID_SIZE));

        AIPanel = new JPanelBackground("src\\hundirFlota\\view\\ocean.jpg");
        AIPanel.setBounds(PANEL_WIDTH/2+50,100,400,400);
        AIPanel.setLayout(new GridLayout(GRID_SIZE,GRID_SIZE));

        titlePanel = new JPanelBackground("src\\hundirFlota\\view\\metallicText.jpg");
        titlePanel.setLayout(null);
        titlePanel.setBounds(0,0,PANEL_WIDTH,150);
        titlePanel.add(titleLabel);

        gamePanel = new JPanelBackground("src\\hundirFlota\\view\\ships.jpg");
        gamePanel.setBounds(0,150,PANEL_WIDTH,PANEL_HEIGHT-150);
        gamePanel.add(playerLabel);
        gamePanel.add(playerPanel);
        gamePanel.add(AILabel);
        gamePanel.add(AIPanel);
        gamePanel.add(playerShotLabel);
        gamePanel.add(AIShotLabel);
        gamePanel.add(winnerLabel);
        fillPanelWithButtons();

        mainPanel = new JPanel();
        mainPanel.setLayout(null);
        mainPanel.setOpaque(true);
        mainPanel.add(titlePanel);
        mainPanel.add(gamePanel);

        frame = new JFrame();
        frame.setLayout(new BorderLayout());
        frame.setPreferredSize(new Dimension(PANEL_WIDTH,PANEL_HEIGHT));
        frame.setMinimumSize(new Dimension(PANEL_WIDTH,PANEL_HEIGHT));
        frame.setMaximumSize(new Dimension(PANEL_WIDTH,PANEL_HEIGHT));
        frame.setResizable(false);
        frame.setLocationRelativeTo(null);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.add(mainPanel);
        frame.pack();
        frame.setVisible(true);
    }

    public void fillPanelWithButtons()
    {
        for(int i = 0; i < GRID_SIZE; i++)
        {
            for(int j = 0; j < GRID_SIZE; j++)
            {
                buttons[i][j] = new JButton();
                buttons[i][j].setBackground(Color.darkGray);

                int posX = i;
                int posY = j;

                buttons[i][j].addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        buttons[posX][posY].setEnabled(false);
                        checkPos(posX,posY);
                        checkPosAI();
                        if(controller.hasWon())
                        {
                            for(int i = 0; i < GRID_SIZE; i++)
                            {
                                for(int j = 0; j < GRID_SIZE; j++)
                                {
                                    buttons[i][j].setEnabled(false);
                                }
                            }
                            playerShotLabel.setVisible(false);
                            AIShotLabel.setVisible(false);
                            winnerLabel.setText(controller.getWinner());
                            winnerLabel.setVisible(true);
                        }
                    }
                });
                playerPanel.add(buttons[i][j]);

                buttonsAI[i][j] = new JButton();
                buttonsAI[i][j].setBackground(Color.darkGray);
                buttonsAI[i][j].setEnabled(false);
                AIPanel.add(buttonsAI[i][j]);
            }
        }
    }

    public void checkPos(int posX, int posY)
    {
        int pos = posX*10+posY;

        switch(controller.launchMissile(pos))
        {
            case 0:
                buttons[posX][posY].setVisible(false);
                playerShotLabel.setText("FAILED");
                playerShotLabel.setForeground(Color.green);
                break;
            case 1:
                buttons[posX][posY].setBackground(Color.gray);
                playerShotLabel.setText("TOUCHED");
                playerShotLabel.setForeground(Color.yellow);
                break;
            case 2:
                buttons[posX][posY].setBackground(Color.gray);
                playerShotLabel.setText("TOUCHED AND SUNK");
                playerShotLabel.setForeground(Color.red);
                break;
        }
    }

    public void checkPosAI()
    {
        int successfulShot = controller.launchMissileAI();

        int pos = controller.getAIPos();
        int posY = pos % 10;
        int posX = (pos - posY) / 10;

        switch(successfulShot)
        {
            case 0:
                buttonsAI[posX][posY].setVisible(false);
                AIShotLabel.setText("FAILED");
                AIShotLabel.setForeground(Color.green);
                break;
            case 1:
                buttonsAI[posX][posY].setBackground(Color.gray);
                AIShotLabel.setText("TOUCHED");
                AIShotLabel.setForeground(Color.yellow);
                break;
            case 2:
                buttonsAI[posX][posY].setBackground(Color.gray);
                AIShotLabel.setText("TOUCHED AND SUNK");
                AIShotLabel.setForeground(Color.red);
                break;
        }
    }
}