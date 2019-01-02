package com.cxf.test;

import java.net.URL;
import java.io.*;
import java.util.*;
import java.util.concurrent.*;
import java.util.regex.*;
import java.nio.charset.*;

class URLCrawler 
{
	public static void main(String[] args) throws Exception {
		ConcurrentLinkedQueue<String> urls = new ConcurrentLinkedQueue<>();
		urls.add( "http://www.dstang.com" );
		int cnt=0;
		while(!urls.isEmpty()){
			String url = urls.poll();
			System.out.println(url);
			new Thread( ()-> {
				try{
					//����ǰ���URL�Ƿ����ظ�
					if (!urls.contains(url)) {
						
						//���ñ����ʽΪ"utf-8"
						String content = download(new URL(url), "utf-8");
						//System.out.println(content);
						if (!content.isEmpty()){
							List<String> moreUrl = parse( content );
							urls.addAll(moreUrl);
							System.out.println(moreUrl);
							
							//����URL�а���������list����ȥ��
							List<String> email = parseEmail(content);
							System.out.println("URL��" + url.toString() + " �����������ַ���£�\n" + email);
							
							//�ж�URL�ǲ�����ҳ
							boolean html = isHtml(content);
							if (html) {
								System.out.println("URL��" + url.toString() + " ��html��ҳ");
							}
						}
					}
				}catch(Exception ex){
					ex.printStackTrace();
				}
			}).start();
			
			if(cnt++>5) break;
			try{ Thread.sleep(4000); }catch(InterruptedException ex){}
		}
	}

	static List<String> parse(String text) {
		String patternString = 
			"\\s*href\\s*=\\s*(\"([^\"]*\")|(\'[^\']*\')|([^\'\">\\s]+))\\s*"; 
 		Pattern pattern = Pattern.compile(patternString, 
			Pattern.CASE_INSENSITIVE  );  //  Pattern.MULTILINE
		Matcher matcher = pattern.matcher( text );
		List<String> list = new ArrayList<>();
		while (matcher.find()) {
			String href = matcher.group(1);
			href = href.replaceAll("\'","").replaceAll("\"","");
			if(href.startsWith("http:") )
				list.add(href); 
		}
		return list;
	}

	static List<String> parseEmail(String text) {
		String regex = "[a-zA-Z0-9_-]+@\\w+\\.[a-z]+(\\.[a-z]+)?";
 		Pattern pattern = Pattern.compile(regex, 
			Pattern.CASE_INSENSITIVE  );  //  Pattern.MULTILINE
		Matcher matcher = pattern.matcher( text );
		List<String> list = new ArrayList<>();
		while (matcher.find()) {
			String mail = matcher.group(0);
			
			//���list�в������������ַ�������
			if(!list.contains(mail)){
				list.add(mail);
			}
		}
		return list;
	}
	
	static boolean isHtml (String text) {
		//String regex = "<!DOCTYPE (.+)>";
		String regex = "<HTML>";
 		Pattern pattern = Pattern.compile(regex, 
			Pattern.CASE_INSENSITIVE  );  //  Pattern.MULTILINE
		Matcher matcher = pattern.matcher( text );
		if (matcher.find()) {
			return true;
		}
		return false;
	}
	
	static String download( URL url, String charset) throws Exception {
		try(
				InputStream input = url.openStream();
				ByteArrayOutputStream output = new ByteArrayOutputStream()
				)
		{
			byte[] data = new byte[1024];
			int length;
			while((length=input.read(data))!=-1){
				output.write(data,0,length);
			}
			byte[] content = output.toByteArray();
			return new String(content, Charset.forName(charset));
		} catch(IOException e) {
			//e.printStackTrace();
			System.out.println("��ЧURL��" + url.toString());
		}
		return "";
	}
}


