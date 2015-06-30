package com.TicTacToe.pkg;

import java.io.File;
import java.io.IOException;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Map;
import java.util.Scanner;
import java.util.TreeMap;


public class GameHistory
{
    class HistoryNode
    {
        String name;
        int win;
        int loss;
    }
    
    private Map<String, HistoryNode> history;
    
    // construct the game history
    public GameHistory()
    {
        history = new TreeMap<String, HistoryNode>();
    }
    
    // load the history from the file
    public void loadFile(String fname)
    {
        try
        {
            Scanner file = new Scanner(new File(fname));
            while (file.hasNextInt())
            {
                HistoryNode n = new HistoryNode();
                n. win = file.nextInt();
                n. loss = file.nextInt();
                n. name = file.nextLine().trim();
                history.put(n.name, n);
            }
            file.close();
        }
        catch (IOException e)
        {
            
        }
    }
    
    // get name list
    public ArrayList<String> getNames()
    {
        return new ArrayList<String>(history.keySet());
    }
    
    // save the history to file
    public void saveFile(String fname)
    {
        try
        {
            PrintStream file = new PrintStream(fname);
            for (HistoryNode n : history.values())
            {
                file.println(n.win + " " + n.loss + " " + n.name);
            }
            file.close();
        }
        catch (IOException e)
        {
            
        }
    }
    
    // return the description
    public String toString()
    {
        String result = "";
        for (HistoryNode n : history.values())
        {
            result += (n.name + ": " + n.win + " wins and "
                    + n.loss + " losses");
            result += "\n";
        }
        return result;
    }
    
    // update the history
    public void update(String name, boolean win)
    {
        if (name.isEmpty()) // guest?
            return;
        
        if (!history.containsKey(name))
        {
            HistoryNode node = new HistoryNode();
            node.name = name;
            node.loss = win ? 0 : 1;
            node.win = win ? 1 : 0;
            history.put(name, node);
        }
        else
        {
            if (win)
                history.get(name).win++;
            else
                history.get(name).loss++;
            
        }        
    }
}

