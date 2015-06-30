package com.TicTacToe.pkg;


public class ComputerPlayer
{
    public static int EASY = 1;
    public static int MEDIUM = 2;
    public static int HARD = 3;
    
    private int compStone;
    private int level;
    
    // construct the computer player
    public ComputerPlayer(int lvl, int compStone)
    {
        this.compStone = compStone;
        this.level = lvl;
    }
    
    // return the computer stone
    public int getCompStone()
    {
        return compStone;
    }
    
    // computer's turn
    public void playTurn(GameBoard board)
    {
        if (level == EASY)
            easyLevel(board);
        else if (level == MEDIUM)
            mediumLevel(board);
        else
            hardLevel(board);
    }    
    
    // hard level player
    private void hardLevel(GameBoard board)
    {
        int userStone = GameBoard.PLAYER1;
        if (userStone == compStone)
            userStone = GameBoard.PLAYER2;
        
        // 4 directions, row, column, 2 diagonals
        int[] offr = {0, 1, 1, 1};
        int[] offc = {1, 0, 1, -1};   
        
        
        int[] stones = {compStone, userStone};
        
        for (int length = 4; length >= 1; length--)
        {
            // 2 round, first round find good position for computer
            //   second round find good position for the user
            for (int p = 0; p < stones.length; p++)
            {
                int rowFound = -1;
                int colFound = -1;
                int maxValue = 0;
            
                // find computer's best position
                for (int i = 0; i < board.getBoard().length; i++)
                    for (int j = 0; j < board.getBoard().length; j++)
                    {
                        if (board.getBoard()[i][j] != 0)
                            continue;

                        // try to place
                        board.placeStone(i, j, stones[p]);
                        
                        boolean fitlength = false;
                        for (int r = 0; r < 4; r++)
                        {
                            if (board.getLineLength(i, j, offr[r], offc[r]) == length &&
                                board.getMaxPossibleLineLength(i, j, offr[r], offc[r], stones[p]) >= 4)
                                fitlength = true;
                        }
                        
                        if (fitlength)
                        {
                            int value = 0;
                            value += calcValue(board, i, j, stones[p]);  
                            value += calcValue(board, i, j, stones[(p+1)%stones.length]);    
                            if (value > maxValue)
                            {
                                maxValue = value;
                                rowFound = i;
                                colFound = j;                                
                            }
                        }
                        
                        // recover
                        board.placeStone(i, j, 0);                        
                    } 
            
                // if found 
                if (rowFound >= 0)
                {
                    board.placeStone(rowFound, colFound, compStone);
                    return;
                }
            }
        }


        easyLevel(board);
    }
    
    // calculate value for the position and stone
    private int calcValue(GameBoard board, int row, int col, int stone)
    {
        int[] offr = {0, 1, 1, 1};
        int[] offc = {1, 0, 1, -1};   
        board.placeStone(row, col, stone);

        int value = 0;
        for (int r = 0; r < 4; r++)
        {
            if (board.getMaxPossibleLineLength(row, col, offr[r], offc[r], stone) >= 4)
                value += board.getLineLength(row, col, offr[r], offc[r]);
        }        
        
        return value;
    }

    // medium level player
    private void mediumLevel(GameBoard board)
    {
        int userStone = GameBoard.PLAYER1;
        if (userStone == compStone)
            userStone = GameBoard.PLAYER2;
        
        // find winning position
        for (int i = 0; i < board.getBoard().length; i++)
            for (int j = 0; j < board.getBoard().length; j++)
                if (board.getBoard()[i][j] == 0)
                {
                    // try to place
                    board.placeStone(i, j, compStone);
                    if (board.getMaxLineLength(i, j) == 4)
                        return;
                    // recover
                    board.placeStone(i, j, 0);
                } 
        
        // find enemy winning position
        for (int i = 0; i < board.getBoard().length; i++)
            for (int j = 0; j < board.getBoard().length; j++)
                if (board.getBoard()[i][j] == 0)
                {
                    // try to place
                    board.placeStone(i, j, userStone);
                    if (board.getMaxLineLength(i, j) == 4)
                    {
                        // place here to stop the user
                        board.placeStone(i, j, compStone);
                        return;
                    }
                    // recover
                    board.placeStone(i, j, 0);
                } 
        
        // find line length 3 or 2
        for (int length = 3; length >= 2; length--)
        {
            for (int i = 0; i < board.getBoard().length; i++)
                for (int j = 0; j < board.getBoard().length; j++)
                    if (board.getBoard()[i][j] == 0)
                    {
                        // try to place
                        board.placeStone(i, j, compStone);
                        if (board.getMaxLineLength(i, j) == length)
                            return;
                        // recover
                        board.placeStone(i, j, 0);
                    } 
        }
        
        easyLevel(board);
    }

    // easy level computer
    private void easyLevel(GameBoard board)
    {
        int r = 0;
        int c = 0;
        
        do
        {
            r = (int)(5 * Math.random());
            c = (int)(5 * Math.random());
        } while (board.getBoard()[r][c] != 0);
        
        board.placeStone(r, c, compStone);
    }

}
