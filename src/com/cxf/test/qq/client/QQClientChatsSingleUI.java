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
 * client˽�Ľ���
 */
public class QQClientChatsSingleUI extends JFrame implements ActionListener{
	
	//�ͻ��˷�����Ϣ
	private MessageSender sender;

	//��ʷ������
	private JTextArea taHistory;
	
	//��Ϣ������
	private JTextArea taInputMessage;
	
	//���Ͱ�ť
	private JButton btnSend;
	
	//��������Ϣ
	private String recvInfo;
	
	public QQClientChatsSingleUI(MessageSender sender, String recvInfo) {
		this.sender = sender;
		this.recvInfo = recvInfo;
		init();
		this.setVisible(true);
	}
	
	/**
	 * ��ʼ�����沼��
	 */
	private void init() {
		this.setTitle("QQClient  " + recvInfo);
		this.setBounds(100, 100, 800, 600);
		this.setLayout(null);
		
		//��ʷ��
		taHistory = new JTextArea();
		taHistory.setBounds(0, 0, 600, 400);
		
		JScrollPane sp1 = new JScrollPane(taHistory);
		sp1.setBounds(0, 0, 600, 400);
		this.add(sp1);
		
		//��Ϣ������
		taInputMessage = new JTextArea();
		taInputMessage.setBounds(0, 420, 540, 160);
		this.add(taInputMessage);
		
		//���Ͱ�ť
		btnSend = new JButton("����");
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
		//���Ͱ�ť
		if (source == btnSend) {
			String str = taInputMessage.getText();
			if (str != null && !str.equals("")) {
				//�����������Ϣ
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

	//������Ϣ����ʷ������
	public void updateHistory(String[] ss){
		taHistory.append("[" + ss[0] + "] ˵��\r\n");
		String formatStr = ss[1].replace("\n", "\n\t");
		formatStr = "\t" +formatStr + "\r\n";
		taHistory.append(formatStr);
	}
	
}