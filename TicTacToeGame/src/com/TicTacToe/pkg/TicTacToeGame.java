package com.TicTacToe.pkg;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;


public class TicTacToeGame extends JFrame implements ActionListener{
	private static final long serialVersionUID = 1L;

	public static final String HISTORY_FILE = "history.txt";
    
    private JButton[][] btnGrid = new JButton[5][5];
    private JButton btnNewGame = new JButton("Start Game");
    private JButton btnHistory = new JButton("Display History");
    private JButton btnAbout = new JButton("About");
    private JButton btnExit = new JButton("Exit");
    private JButton btnAddPlayer = new JButton("Add");
    
    private JComboBox<String> combName1 = new JComboBox<String>(new String[]{"Guest"});
    private JComboBox<String> combName2 = new JComboBox<String>(new String[]{"Computer"});
    private JComboBox<String> combLevel = new JComboBox<String>(new String[]{"Easy", "Medium", "Hard"});
    private JCheckBox chkFirst = new JCheckBox("Computer Goes First");
    
    private GameHistory history = new GameHistory();
    private ComputerPlayer computer;
    private GameBoard board;
    
    private AddUser addUserDlg = new AddUser();
    private GameBoard.GameBoardToPlay gameBoardToPlay = null;
    
    // used for player VS player
    private String playerName1;
    private String playerName2;
    private int currentStone;
    private int playerStone1;
    private int playerStone2;
    
    // default constructor
    public TicTacToeGame()
    {
        setTitle("5x5 Tic-Tac-Toe");
        setLayout(new BorderLayout());
        setLocation(600,400);
        
        //JPanel pnTop = new JPanel();
        JPanel pnAddPlayer = new JPanel();
        JPanel pnCommands = new JPanel();

        JPanel pn2 = new JPanel();
        JPanel pnCenter = new JPanel(new GridLayout(2, 1));
                
        JPanel pnldiffLvl = new JPanel();
        JPanel pnlChkCompFirst = new JPanel();
        JPanel pnEnd = new JPanel(new GridLayout(2, 1));
        
        CommonUtil.setBtn(btnNewGame,120);
        btnNewGame.addActionListener(this);
        pnCommands.add(btnNewGame);
        
        CommonUtil.setBtn(btnHistory,150);
        btnHistory.addActionListener(this);
        pnCommands.add(btnHistory);

        CommonUtil.setBtn(btnAbout,120);
        btnAbout.addActionListener(this);
        pnCommands.add(btnAbout);
        
        CommonUtil.setBtn(btnExit,120);
        btnExit.addActionListener(this);
        pnCommands.add(btnExit);        

        CommonUtil.setBtn(btnAddPlayer,150);
        pnAddPlayer.add(btnAddPlayer);
                
        pn2.add(CommonUtil.createLabel("Player1 : "));
        pn2.add(combName1);
        pn2.add(CommonUtil.createLabel("      VS       Player2 : "));        
        pn2.add(combName2);
        
        
        pnldiffLvl.add(CommonUtil.createLabel("Difficulty : "));
        
        pnldiffLvl.add(combLevel);
        chkFirst.setForeground(CommonUtil.BUTTON_TEXT_COLOR);
        chkFirst.setBackground(CommonUtil.BACKGROUND_COLOR);
        pnlChkCompFirst.add(chkFirst);

        combLevel.setSelectedIndex(0);
        
        pnEnd.add(pnldiffLvl);
        pnEnd.add(pnlChkCompFirst);
        
        
        pnldiffLvl.setBackground(CommonUtil.BACKGROUND_COLOR);
        pnlChkCompFirst.setBackground(CommonUtil.BACKGROUND_COLOR);
        
        btnAddPlayer.addActionListener(this);
        
        history.loadFile(HISTORY_FILE);
        combName1.setEditable(false);
        combName2.setEditable(false);
        resetPlayerNames();
        combName1.setSelectedIndex(0);
        combName2.setSelectedIndex(0);
        
        pnCommands.setBackground(CommonUtil.BACKGROUND_COLOR);
        pnAddPlayer.setBackground(CommonUtil.BACKGROUND_COLOR);
        pn2.setBackground(CommonUtil.BACKGROUND_COLOR);
        
        add(pnCommands,BorderLayout.PAGE_START);
        pnCenter.add(pnAddPlayer);
        pnCenter.add(pn2);        
        add(pnCenter,BorderLayout.CENTER);        
        add(pnEnd,BorderLayout.PAGE_END);
        
        
        gameBoardToPlay = new GameBoard.GameBoardToPlay(this); 
    }

    // reset the player names
    private void resetPlayerNames()
    {
        combName1.removeAllItems();
        combName1.addItem("Guest");
        combName2.removeAllItems();
        combName2.addItem("Computer");
        combName2.addItem("Guest");

        for (String name : history.getNames()){
            combName1.addItem(name);
            combName2.addItem(name);
        }
    }

    // handle the click event of buttons
    public void actionPerformed(ActionEvent e)
    {
        if (e.getSource() == btnExit)
        {
            history.saveFile(HISTORY_FILE);
            System.exit(0);
        }
        else if (e.getSource() == btnHistory)
        {
            String result = history.toString();
            if (result.isEmpty())
                result = "Empty history";
            JOptionPane.showMessageDialog(this, result);
        }
        else if (e.getSource() == btnNewGame)
        {
            createNewGame();
        }else if(e.getSource() == btnAbout){
        	StringBuilder msgToShow = new StringBuilder();
        	msgToShow.append("   Tic Tac Toe by TEAM BIG TOE V1.0\n\n");
        	msgToShow.append("          MEMBERS\n");
        	msgToShow.append("        - Vickey Shrestha\n");
        	msgToShow.append("        - Yoogesh Sharma\n");
        	
        	msgToShow.append("          \n copyright 2014\n");
        	JOptionPane.showMessageDialog(this, msgToShow,"About",-1);
        }else if(e.getSource() == btnAddPlayer){
        	addUserDlg.setLocationRelativeTo(this);
        	addUserDlg.setModal(true);
        	addUserDlg.reset();
        	addUserDlg.pack();
            addUserDlg.setVisible(true);
            
            if(addUserDlg.isAdded()){
            	String newUser = addUserDlg.getTxtUserName().getText().trim();
            	boolean isDuplicate = false;
            	if(newUser.length()>0){
            		//Check if user already exist..duplicate validation
            		for(int i=0;i<combName1.getItemCount();i++){
            			if(((String)combName1.getItemAt(i)).equals(newUser)){
            				isDuplicate = true;
            				break;
            			}
            		}
            		if(!isDuplicate) combName1.addItem(newUser);
            	}
            }
        }
    }
    
    // create a new game
    public void createNewGame()
    {
    	
    	String title = null;
        playerName1 = combName1.getSelectedItem().toString().trim();
        playerName2 = combName2.getSelectedItem().toString().trim();
        gameBoardToPlay.setVisible(true);

        if (playerName1.isEmpty() || playerName2.isEmpty())
        {
            JOptionPane.showMessageDialog(this, "Please choose the player name.");
            return;
        }

        for (int i = 0; i < btnGrid.length; i++)
        {
            for (int j = 0; j < btnGrid.length; j++)
            {
                btnGrid[i][j].setEnabled(true);
                btnGrid[i][j].setText("");
            }
        }

        // create the game
        board = new GameBoard();
        
        if (playerName2.equalsIgnoreCase("Computer")) // player vs computer
        {
            int lvl = 0;
            if (combLevel.getSelectedIndex() == 0)
                lvl = ComputerPlayer.EASY;
            else if (combLevel.getSelectedIndex() == 1)
                lvl = ComputerPlayer.MEDIUM;
            else
                lvl = ComputerPlayer.HARD;
            
            boolean compFirst = chkFirst.isSelected();
            currentStone = (compFirst ? GameBoard.PLAYER2 : GameBoard.PLAYER1);

            computer = new ComputerPlayer(lvl, compFirst ? GameBoard.PLAYER1 : GameBoard.PLAYER2);
            
            if (compFirst)
                title = "Computer " + combLevel.getSelectedItem().toString() + " VS " + playerName1;
            else
                title = playerName1 + " VS Computer " + combLevel.getSelectedItem().toString();
            if (compFirst)
                computer.playTurn(board);
        }
        else
        {
            playerStone1 = GameBoard.PLAYER1;
            playerStone2 = GameBoard.PLAYER2;
            currentStone = GameBoard.PLAYER1;
            title = playerName1 + " VS " + playerName2;
            computer = null;
        }        
        gameBoardToPlay.setTitle(title);
        displayBoard();
    }
    
    // display the board
    public void displayBoard()
    {
        for (int i = 0; i < btnGrid.length; i++)
        {
            for (int j = 0; j < btnGrid.length; j++)
            {
                if (board.getBoard()[i][j] == GameBoard.PLAYER1)
                {
                    btnGrid[i][j].setText("X");
                    btnGrid[i][j].setForeground(CommonUtil.BACKGROUND_COLOR);
                }
                else if (board.getBoard()[i][j] == GameBoard.PLAYER2)
                {
                    btnGrid[i][j].setText("O");
                    btnGrid[i][j].setForeground(Color.RED);
                }
            }
        }
        gameBoardToPlay.setSize(800,800);
        gameBoardToPlay.setVisible(true);
        this.setVisible(false);        
    }

    // The player click the board
    public void playerClick(int row, int col)
    {
        if (board == null)
            return;
        
        if (board.getBoard()[row][col] != 0)
        {
            JOptionPane.showMessageDialog(this, "Sorry cannot place here.");
            return;
        }

        board.placeStone(row, col, currentStone);
        displayBoard();
        if (board.findWinner() == currentStone)
        {
            gameOver(currentStone);
        }
        else if (board.isFull())
        {
            tieGame();
        }
        else if (computer == null) // player vs player
        {
            if (currentStone == GameBoard.PLAYER1)
                currentStone = GameBoard.PLAYER2;
            else
                currentStone = GameBoard.PLAYER1;
        }
        else // player vs computer
        {
            computer.playTurn(board);
            displayBoard();
            if (board.findWinner() == computer.getCompStone())
            {
                gameOver(computer.getCompStone());
            }
            else if (board.isFull())
            {
                tieGame();
            }
        }        
    }

    // game over, record the information
    private void gameOver(int winner)
    {
        for (int i = 0; i < btnGrid.length; i++)
            for (int j = 0; j < btnGrid.length; j++)
                btnGrid[i][j].setEnabled(false);
        
        if (computer != null) // vs computer
        {
            if (winner == computer.getCompStone())
                JOptionPane.showMessageDialog(this, "You lose.");
            else
                JOptionPane.showMessageDialog(this, "Congratulation! You won.");
        }
        else // player vs player
        {
            if (winner == playerStone1)
                JOptionPane.showMessageDialog(this, "Congratulation! " + 
                        playerName1 + " won.");
            else
                JOptionPane.showMessageDialog(this, "Congratulation! " + 
                        playerName2 + " won.");
        }

        if (!playerName1.equalsIgnoreCase("Guest"))
        {
            if (computer != null) // vs computer
                history.update(playerName1, winner != computer.getCompStone());
            else // vs player
                history.update(playerName1, winner == playerStone1);

            history.saveFile(HISTORY_FILE);
            resetPlayerNames();
        }

        if (computer == null && !playerName2.equalsIgnoreCase("Guest"))
        {
            history.update(playerName2, winner == playerStone2);
            history.saveFile(HISTORY_FILE);
            resetPlayerNames();
        }
        
        combName1.setSelectedItem(playerName1);
        combName2.setSelectedItem(playerName2);
        
        this.setVisible(true);
        gameBoardToPlay.setVisible(false);
    }
    
    // tie game
    private void tieGame()
    {
        for (int i = 0; i < btnGrid.length; i++)
            for (int j = 0; j < btnGrid.length; j++)
                btnGrid[i][j].setEnabled(false);
        JOptionPane.showMessageDialog(this, "TIE GAME!");
        this.setVisible(true);
        gameBoardToPlay.setVisible(false);
    }

    // main method create the new game
    public static void main(String[] args)
    {
        TicTacToeGame game = new TicTacToeGame();
        game.setSize(600, 310);
        game.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        game.setVisible(true);        
    }
    
    public JButton[][] getBtnGrid() {
		return btnGrid;
	}
}
