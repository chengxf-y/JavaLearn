package com.cxf.test.qq.client;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

import com.cxf.test.util.Util;

/**
 * 
 * client私聊界面
 */
public class QQClientChatsSingleUI extends JFrame implements ActionListener{
	
	//客户端发送消息
	private MessageSender sender;

	//历史聊天区
	private JTextArea taHistory;
	
	//消息输入区
	private JTextArea taInputMessage;
	
	//发送按钮
	private JButton btnSend;
	
	//接受者信息
	private String recvInfo;
	
	public QQClientChatsSingleUI(MessageSender sender, String recvInfo) {
		this.sender = sender;
		this.recvInfo = recvInfo;
		init();
		this.setVisible(true);
	}
	
	/**
	 * 初始化界面布局
	 */
	private void init() {
		this.setTitle("QQClient  " + recvInfo);
		this.setBounds(100, 100, 800, 600);
		this.setLayout(null);
		
		//历史区
		taHistory = new JTextArea();
		taHistory.setBounds(0, 0, 600, 400);
		
		JScrollPane sp1 = new JScrollPane(taHistory);
		sp1.setBounds(0, 0, 600, 400);
		this.add(sp1);
		
		//消息输入区
		taInputMessage = new JTextArea();
		taInputMessage.setBounds(0, 420, 540, 160);
		this.add(taInputMessage);
		
		//发送按钮
		btnSend = new JButton("发送");
		btnSend.setBounds(560, 420, 100, 160);
		btnSend.addActionListener(this);
		this.add(btnSend);
		
		this.addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				QQClientChatsSingleUI.this.setVisible(false);
			}
		});
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		Object source = e.getSource();
		//发送按钮
		if (source == btnSend) {
			String str = taInputMessage.getText();
			if (str != null && !str.equals("")) {
				//发送输入的信息
				ClientChatMessage msg = new ClientChatMessage();
				msg.setRecvInfo(recvInfo);
				msg.setMessage(str);
				sender.sendMessage(msg.encodeMessage());
				taInputMessage.setText("");
				String[] ss = new String[2];
				ss[0] = Util.getSocketAddr(sender.getSocket());
				ss[1] = str;
				updateHistory(ss);
			}
		}
	}

	//更新信息到历史聊天区
	public void updateHistory(String[] ss){
		taHistory.append("[" + ss[0] + "] 说：\r\n");
		String formatStr = ss[1].replace("\n", "\n\t");
		formatStr = "\t" +formatStr + "\r\n";
		taHistory.append(formatStr);
	}
	
}