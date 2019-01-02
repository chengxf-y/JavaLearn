package com.cxf.test.qq.client;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;


public class QQClientChatsUI extends JFrame implements ActionListener{
	
	//维护和所有人对话的窗口对象
	private Map<String, QQClientChatsSingleUI> sessions = new HashMap<String, QQClientChatsSingleUI>();
	
	//客户端发送消息
	private MessageSender sender;

	//历史聊天区
	private JTextArea taHistory;
	
	//好友列表
	private JList<String> lstFrients;
	
	//消息输入区
	private JTextArea taInputMessage;
	
	//发送按钮
	private JButton btnSend;
	
	//刷新好友列表按钮
	private JButton btnRefresh;
	
	public QQClientChatsUI(MessageSender sender) {
		this.sender = sender;
		init();
		this.setVisible(true);
	}
	
	/**
	 * 初始化界面布局
	 */
	private void init() {
		this.setTitle("QQClient");
		this.setBounds(100, 100, 800, 600);
		this.setLayout(null);
		
		//历史区
		taHistory = new JTextArea();
		taHistory.setBounds(0, 0, 600, 400);
		
		JScrollPane sp1 = new JScrollPane(taHistory);
		sp1.setBounds(0, 0, 600, 400);
		this.add(sp1);
		
		//好友列表
		lstFrients = new JList<String>();
		lstFrients.setBounds(620, 0, 160, 400);
		lstFrients.addMouseListener(new MouseAdapter() {

			@Override
			public void mouseClicked(MouseEvent e) {
				//双击鼠标
				if (e.getClickCount() == 2) {
					String str = lstFrients.getSelectedValue();
					QQClientChatsSingleUI ui = sessions.get(str);
					if (ui == null) {
						ui = new QQClientChatsSingleUI(sender, str);
						sessions.put(str, ui);
					}
					ui.setVisible(true);
				}
			}
		});
		this.add(lstFrients);
		
		//消息输入区
		taInputMessage = new JTextArea();
		taInputMessage.setBounds(0, 420, 540, 160);
		this.add(taInputMessage);
		
		//发送按钮
		btnSend = new JButton("发送");
		btnSend.setBounds(560, 420, 100, 160);
		btnSend.addActionListener(this);
		this.add(btnSend);
		
		//刷新好友列表
		btnRefresh = new JButton("刷新");
		btnRefresh.setBounds(680, 420, 100, 160);
		btnRefresh.addActionListener(this);
		this.add(btnRefresh);
		
		this.addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				System.exit(-1);
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
				ClientChatsMessage msg = new ClientChatsMessage();
				msg.setMessage(str);
				sender.sendMessage(msg.encodeMessage());
				taInputMessage.setText("");
			}
		}else if (source == btnRefresh) {
		//刷新按钮
			//发送刷新操作
			ClientRefreshFriendMessage msg = new ClientRefreshFriendMessage();
			sender.sendMessage(msg.encodeMessage());
		}
		
	}

	/**
	 * 刷新好友列表
	 */
	public void refreshFriendList(List<String> list) {
		DefaultListModel<String> listModle = new DefaultListModel<String>();
		for (String string : list) {
			listModle.addElement(string);
		}
		lstFrients.setModel(listModle);
	}
	
	public void updateHistory(String[] ss){
		taHistory.append("[" + ss[0] + "] 说：\r\n");
		String formatStr = ss[1].replace("\n", "\n\t");
		formatStr = "\t" +formatStr + "\r\n";
		taHistory.append(formatStr);
	}
	
	public QQClientChatsSingleUI recvMessageUI(String senderAddr){
		QQClientChatsSingleUI ui = sessions.get(senderAddr);
		if (ui == null) {
			ui = new QQClientChatsSingleUI(sender, senderAddr);
			sessions.put(senderAddr, ui);
		}
		ui.setVisible(true);
		return ui;
	}
	
}
