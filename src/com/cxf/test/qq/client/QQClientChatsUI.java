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
	
	//ά���������˶Ի��Ĵ��ڶ���
	private Map<String, QQClientChatsSingleUI> sessions = new HashMap<String, QQClientChatsSingleUI>();
	
	//�ͻ��˷�����Ϣ
	private MessageSender sender;

	//��ʷ������
	private JTextArea taHistory;
	
	//�����б�
	private JList<String> lstFrients;
	
	//��Ϣ������
	private JTextArea taInputMessage;
	
	//���Ͱ�ť
	private JButton btnSend;
	
	//ˢ�º����б�ť
	private JButton btnRefresh;
	
	public QQClientChatsUI(MessageSender sender) {
		this.sender = sender;
		init();
		this.setVisible(true);
	}
	
	/**
	 * ��ʼ�����沼��
	 */
	private void init() {
		this.setTitle("QQClient");
		this.setBounds(100, 100, 800, 600);
		this.setLayout(null);
		
		//��ʷ��
		taHistory = new JTextArea();
		taHistory.setBounds(0, 0, 600, 400);
		
		JScrollPane sp1 = new JScrollPane(taHistory);
		sp1.setBounds(0, 0, 600, 400);
		this.add(sp1);
		
		//�����б�
		lstFrients = new JList<String>();
		lstFrients.setBounds(620, 0, 160, 400);
		lstFrients.addMouseListener(new MouseAdapter() {

			@Override
			public void mouseClicked(MouseEvent e) {
				//˫�����
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
		
		//��Ϣ������
		taInputMessage = new JTextArea();
		taInputMessage.setBounds(0, 420, 540, 160);
		this.add(taInputMessage);
		
		//���Ͱ�ť
		btnSend = new JButton("����");
		btnSend.setBounds(560, 420, 100, 160);
		btnSend.addActionListener(this);
		this.add(btnSend);
		
		//ˢ�º����б�
		btnRefresh = new JButton("ˢ��");
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
		//���Ͱ�ť
		if (source == btnSend) {
			String str = taInputMessage.getText();
			if (str != null && !str.equals("")) {
				//�����������Ϣ
				ClientChatsMessage msg = new ClientChatsMessage();
				msg.setMessage(str);
				sender.sendMessage(msg.encodeMessage());
				taInputMessage.setText("");
			}
		}else if (source == btnRefresh) {
		//ˢ�°�ť
			//����ˢ�²���
			ClientRefreshFriendMessage msg = new ClientRefreshFriendMessage();
			sender.sendMessage(msg.encodeMessage());
		}
		
	}

	/**
	 * ˢ�º����б�
	 */
	public void refreshFriendList(List<String> list) {
		DefaultListModel<String> listModle = new DefaultListModel<String>();
		for (String string : list) {
			listModle.addElement(string);
		}
		lstFrients.setModel(listModle);
	}
	
	public void updateHistory(String[] ss){
		taHistory.append("[" + ss[0] + "] ˵��\r\n");
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
