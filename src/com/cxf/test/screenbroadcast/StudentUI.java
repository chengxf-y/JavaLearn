package com.cxf.test.screenbroadcast;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;

public class StudentUI extends JFrame{
	
	private JLabel lblIcon;
	
	public StudentUI() {
		init();
	}

	private void init() {
		this.setTitle("Ñ§Éú¶Ë");
		this.setBounds(0, 0, 1600, 900);
		this.setLayout(null);
		
		lblIcon = new JLabel();
		lblIcon.setBounds(0, 0, 1600, 900);
		
		ImageIcon icon = new ImageIcon("d:/broadcast/student.jpg");
		lblIcon.setIcon(icon);
		this.add(lblIcon);
		this.setVisible(true);
	}
	
	public void updataIcon(byte[] screenData) {
		ImageIcon icon = new ImageIcon(screenData);
		lblIcon.setIcon(icon);
	}
}
