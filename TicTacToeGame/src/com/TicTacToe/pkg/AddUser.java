package com.TicTacToe.pkg;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;


public class AddUser extends JDialog implements ActionListener {
	
	private static final long serialVersionUID = 1L;
	
	private boolean isAdded;
	
	private JButton btnAdd = new JButton("Add");
	private JButton btnCancel = new JButton("Cancel");
	private JTextField txtUserName = new JTextField(10);
	
	public AddUser() {
        setTitle("Add a New User");
        setLayout(new BorderLayout());
        JPanel pnTop = new JPanel(new GridLayout(2, 1));
        
        JPanel pn1 = new JPanel();
        pn1.add(new JLabel("New User :"));
        pn1.add(txtUserName);
        pnTop.add(pn1);
        
        JPanel pn2 = new JPanel();        
        pn2.add(btnAdd);
        pn2.add(btnCancel);
        
        btnAdd.addActionListener(this);
        btnCancel.addActionListener(this);
        pnTop.add(pn2);
        
        add(pnTop, BorderLayout.NORTH);
	}
	
	public void reset(){
		this.isAdded = false;
		this.txtUserName.setText("");
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
        if (e.getSource() == btnCancel){
            isAdded = false;
        }else if(e.getSource() == btnAdd){
        	isAdded = true;
        }
        this.setVisible(false);
	}

	/**
	 * @return the isAdded
	 */
	public boolean isAdded() {
		return isAdded;
	}

	/**
	 * @return the txtUserName
	 */
	public JTextField getTxtUserName() {
		return txtUserName;
	}

}
