package com.cxf.test.screenbroadcast;

import java.awt.AWTException;
import java.awt.Rectangle;
import java.awt.Robot;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class Screen {
	public static BufferedImage screenCatch() throws AWTException, IOException {
		Robot robot = new Robot();
		Rectangle rect = new Rectangle(0, 0, 1600, 900);
		BufferedImage img = robot.createScreenCapture(rect);
		ImageIO.write(img, "jpg", new File("d:/broadcast/sc.jpg"));
		return img;
	}
	
	public static void main(String[] args) throws AWTException, IOException {
		screenCatch();
	}
}
