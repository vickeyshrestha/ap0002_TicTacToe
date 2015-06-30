package com.TicTacToe.pkg;

import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;


public class GameBoard
{
    public static final int PLAYER1 = 1;
    public static final int PLAYER2 = 2;
    
    public static final int BOARD_ROW_SIZE = 5;
    public static final int BOARD_COL_SIZE = 5;
    
    
    private int[][] board;

    // Constructor
    public GameBoard()
    {
        board = new int[BOARD_ROW_SIZE][BOARD_COL_SIZE];
    }
    
    // Return the board
    public int[][] getBoard()
    {
        return board;
    }
    
    // Return the stone at (r,c)
    public int getStone(int r, int c)
    {
        return board[r][c];
    }
    
    // Place the stone to the place
    public void placeStone(int r, int c, int stone)
    {
        board[r][c] = stone;
    }
    
    // check whether the board is full
    public boolean isFull()
    {
        for (int r = 0; r < board.length; r++) {
            for (int c = 0; c < board.length; c++)
                if (board[r][c] == 0)
                    return false;
        }
        return true;
    }
    
    // get max possible line length at (r,c) for the player, 
    //  the direction: roff(row offset) and coff(column offset)
    public int getMaxPossibleLineLength(int r, int c, int roff, int coff, int player)
    {
        if (r < 0 || r >= board.length ||
            c < 0 || c >= board.length)
            return 0;
        if (board[r][c] != 0 && board[r][c] != player)
            return 0;
        
        int length = 1;

        int currrow = r + roff;
        int currcol = c + coff;
        
        while (currrow >= 0 && currrow < board.length &&
               currcol >= 0 && currcol < board.length &&
               (board[currrow][currcol] == player || board[currrow][currcol] == 0))
        {
            length++;
            currrow += roff;
            currcol += coff;
        }
        
        currrow = r - roff;
        currcol = c - coff;
        while (currrow >= 0 && currrow < board.length &&
               currcol >= 0 && currcol < board.length &&
               (board[currrow][currcol] == player || board[currrow][currcol] == 0))
        {
             length++;
             currrow -= roff;
             currcol -= coff;
        }
        
        return length;    
    }
    
    // get the line length at (r, c), the direction: roff(row offset) and coff(column offset)
    public int getLineLength(int r, int c, int roff, int coff)
    {
        if (r < 0 || r >= board.length ||
            c < 0 || c >= board.length)
            return 0;
        if (board[r][c] == 0)
            return 0;

        int length = 1;
        
        int currrow = r + roff;
        int currcol = c + coff;
        
        while (currrow >= 0 && currrow < board.length &&
               currcol >= 0 && currcol < board.length &&
               board[currrow][currcol] == board[r][c])
        {
            length++;
            currrow += roff;
            currcol += coff;
        }
        
        currrow = r - roff;
        currcol = c - coff;
        while (currrow >= 0 && currrow < board.length &&
               currcol >= 0 && currcol < board.length &&
               board[currrow][currcol] == board[r][c])
        {
             length++;
             currrow -= roff;
             currcol -= coff;
        }
        
        return length;    
    }
    
    // get the max line length at (r, c)
    public int getMaxLineLength(int r, int c)
    {
        if (r < 0 || r >= board.length ||
            c < 0 || c >= board.length)
            return 0;
        if (board[r][c] == 0)
            return 0;
        int maxline = 0;
        
        // check 4 directions, row, column, 2 diagonals
        int[] offr = {0, 1, 1, 1};
        int[] offc = {1, 0, 1, -1};
        
        for (int i = 0; i < 4; i++) {
            int length = getLineLength(r, c, offr[i], offc[i]);
            
            if (length > maxline)
                maxline = length;            
        }        
        
        return maxline;
    }
    
    // find winner, return 0 is no winner
    public int findWinner()
    {
        for (int r = 0; r < board.length; r++) {
            for (int c = 0; c < board.length; c++)
                if (getMaxLineLength(r, c) == 4)
                    return board[r][c];
        }
        
        return 0;
    }
    
    public static class GameBoardToPlay extends JFrame implements ActionListener{

		private static final long serialVersionUID = 1L;
		
		private TicTacToeGame ticTacToeGame = null;
		
		public GameBoardToPlay(final TicTacToeGame ticTacToeGame){
			this.ticTacToeGame = ticTacToeGame;
	        setLayout(new BorderLayout());
	        JButton[][] btnGrid = ticTacToeGame.getBtnGrid();
	        JPanel pnCenter = new JPanel(new GridLayout(5, 5));
	        
	        for (int i = 0; i < BOARD_ROW_SIZE; i++)
	        {
	            for (int j = 0; j < BOARD_COL_SIZE; j++)
	            {
	                btnGrid[i][j] = new JButton();
	                btnGrid[i][j].addActionListener(this);
	                btnGrid[i][j].setFont(new Font(Font.DIALOG, Font.BOLD, 50));
	                pnCenter.add(btnGrid[i][j]);
	            }
	        }
	        add(pnCenter, BorderLayout.CENTER);
	        
	        this.addWindowListener(new WindowAdapter() {
	        	@Override
	        	public void windowClosing(WindowEvent e) {
	                setVisible(false);
	                ticTacToeGame.setVisible(true);
	        		super.windowClosing(e);
	        	}
			});
		}
		
		@Override
		public void actionPerformed(ActionEvent e) {
            for (int i = 0; i < BOARD_ROW_SIZE; i++)
            {
                for (int j = 0; j < BOARD_COL_SIZE; j++)
                {
                    if (e.getSource() == ticTacToeGame.getBtnGrid()[i][j])
                    {
                    	ticTacToeGame.playerClick(i, j);
                        return;
                    }                        
                }
            }
		}
    }
}
