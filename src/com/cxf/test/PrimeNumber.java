package com.cxf.test;

import java.lang.*;


public class PrimeNumber {
	
	/**
	 * ����һ������x�ܱ�ʾΪ�������ĳ˻������ض���һ��С�ڵ���sqrt(x)�����ӣ�������ù���֤����֤��������������Ӷ�����sqrt(x)����ô�˻�����x,��ͼ���ì�ܡ� 
	 * 
	 * */
	
	public static void main(String[] args) {
		int x = 100;
		int end = (int) Math.sqrt(x);
		int [] list = {2,3,5,7};
		String p = "2,3,5,7";
		int len = 4;
		for(int i = 11; i <= 100; i ++){
			for(int k =0; k < list.length; k ++){
				int m = i % list[k];
				if (m ==0) break;
				if ( k == (list.length - 1 ) && m != 0 ){
					//System.out.println(i);
					len ++;
					p = p + "," + i;
					break;
				}
			}
		}
		int[] result = null;
		int index = 0;
		for(String i : p.split(",")){
			result = new int[len];
			result[index] = Integer.parseInt(i);
			System.out.println(i);
		}
	}

}
