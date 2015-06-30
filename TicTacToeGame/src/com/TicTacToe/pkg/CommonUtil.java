package com.TicTacToe.pkg;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;

import javax.swing.JButton;
import javax.swing.JLabel;

public class CommonUtil {

	public static final Color BACKGROUND_COLOR= Color.BLUE.darker();
	public static final Color BUTTON_BACKGROUND_COLOR = Color.RED.darker();
	public static final Color BUTTON_TEXT_COLOR = Color.WHITE;

	public static void setBtn(JButton btn,int btnWidth){
		btn.setPreferredSize(new Dimension(btnWidth,60));
		btn.setFont(new Font("Arial", Font.BOLD, 15));
		btn.setBackground(BUTTON_BACKGROUND_COLOR);
		btn.setForeground(BUTTON_TEXT_COLOR);
	}
	
	public static JLabel createLabel(String caption){
        JLabel label = new JLabel();
        label.setForeground(BUTTON_TEXT_COLOR);
        label.setText(caption);
        return label;
	}

}
