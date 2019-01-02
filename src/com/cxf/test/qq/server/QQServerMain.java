package com.cxf.test.qq.server;

import java.io.IOException;

public class QQServerMain {
	public static void main(String[] args) throws IOException {
		QQServer.getInstance().start();
	}
}
