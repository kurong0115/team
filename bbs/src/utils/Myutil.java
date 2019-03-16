package utils;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Properties;
import java.util.Random;
import java.util.Set;

import javax.mail.Message;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.servlet.http.HttpSession;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

import bean.Topic;
import dao.StopDao;
import dao.UserDao;

public class Myutil {
	private static StopDao sd=new StopDao();
	private static UserDao ud=new UserDao();
	/**
	 * 借助fastJSON工具把List<Map>转为List<javabase>对象
	 * 
	 * @param mList
	 * @param t
	 * @return
	 */	
	public static <T> List<T> ListMapToJavaBean(List<Map<String, Object>> mList, Class<T> t) {
		List<T> tList = new ArrayList<T>();
		for (Map<String, Object> map : mList) {
			// 把map对象转为json字符串
			String strJson = JSON.toJSONString(map);
			// 把json字符串转为jsonobject对象
			JSONObject json = JSONObject.parseObject(strJson);
			// 把jsonobject对象转成javabase对象
			tList.add(JSON.toJavaObject(json, t));
		}
		return tList;
	}

	/**
	 * 借助fastJSON工具把Map<String,String[]>转为javabase对象
	 * 
	 * @param mList 包含所有请求参数的map集合
	 * @param t     JavaBean类型
	 * @return
	 */
	public static <T> T MapToJavaBean(Map<String, String[]> mMap, Class<T> t) {
		// 把Map<String, String[]>转为Map<String, String>
		Set<Entry<String, String[]>> entrySet = mMap.entrySet();
		Map<String, String> map = new HashMap<>();
		for (Entry<String, String[]> entry : entrySet) {
			map.put(entry.getKey(), entry.getValue()[0]);
		}
		// 把map对象转为json字符串
		String strJson = JSON.toJSONString(map);
		// 把json字符串转为jsonobject对象
		JSONObject json = JSONObject.parseObject(strJson);

		return JSON.toJavaObject(json, t);
	}

	/**
	 * 验证功能
	 */

	public static BufferedImage createImgCode(HttpSession session) {
		// 创建图片缓冲对象
		BufferedImage bi = new BufferedImage(60, 22, BufferedImage.TYPE_INT_RGB);
		// 获取画笔
		Graphics g = bi.getGraphics();
		// 设置画笔颜色
		g.setColor(new Color(187, 255, 255));
		// 把颜色填充到指定位置和大小
		g.fillRect(0, 0, 60, 22);

		// 创建随机对象
		Random r = new Random();

		g.setColor(Color.BLACK);
		// 随机产生干扰线，使图像中的认证码不易被其他程序探测
		/*
		 * for(int i=0;i<20;i++) { int x=r.nextInt(68); int y=r.nextInt(22); int
		 * x1=r.nextInt(5); int y1=r.nextInt(5); g.setColor(new Color(r.nextInt(255),
		 * r.nextInt(255), r.nextInt(255))); //x，y为起点的坐标，x1，y1为终点的坐标 g.drawLine(x, y,
		 * x+x1, y+y1); }
		 */

		// 准备一个随机字符（用于随机生成的验证码）
		String a = "";
		for (char i = 'A'; i < 'Z'; i++) {
			a += i;
		}
		for (char i = 'a'; i < 'z'; i++) {
			a += i;
		}
		for (char i = '0'; i < '9'; i++) {
			a += i;
		}
		char[] c = a.toCharArray();
		// 用于存放生成的验证码字符
		StringBuffer s = new StringBuffer();
		// 随机生成4个字符
		for (int i = 0, len = c.length; i < 4; i++) {
			// 获取随机下标
			int index = r.nextInt(len);
			// 设置画笔颜色
			g.setColor(new Color(r.nextInt(80), r.nextInt(150), r.nextInt(200)));
			// 把字符画到画布指定的位置
			g.drawString(c[index] + "", 4 + i * 15, 18);
			// 把生成的字符存到字符串缓冲区备用
			s.append(c[index]);
		}
		session.setAttribute("code", s.toString());
		return bi;
	}
/**
 * 发送邮件
 * @param email
 * @return
 */
	public static String sendemail(String email) {
		// 随机生成验证码
	
		String code = String.valueOf((int)(Math.random()*9+1)*100000);
		// 以下变量为用户根据自己的情况设置
		String smtphost = "smtp.qq.com"; // 发送邮件服务器
		String user = "869872053@qq.com"; // 邮件服务器登录用户名
		String password = "fmsktqzlvvngbeae"; // 邮件服务器登录密码
		String from = "869872053@qq.com"; // 发送人邮件地址
		String to = email; // 接受人邮件地址
		String subject = "BBS"; // 邮件标题
		// 邮件内容
		String body = "亲爱的用户，这是来自BBS的密码重置验证码：" + code + "请注意保护好个人信息！";
		// 以下为发送程序，用户无需改动
		try {
			// 初始化Properties类对象
			Properties props = new Properties();
			// 设置mail.smtp.host属性
			props.put("mail.smtp.host", smtphost);
			// 设置使用验证
			props.put("mail.smtp.auth", "true");
			// 获取非共享的session对象
			Session ssn = Session.getInstance(props, null);
			// 创建一个默认的MimeMessage对象。
			MimeMessage message = new MimeMessage(ssn);
			// 创建InternetAddress对象
			InternetAddress fromAddress = new InternetAddress(from);
			// 设置From: 头部的header字段
			message.setFrom(fromAddress);
			// 创建InternetAddress对象
			InternetAddress toAddress = new InternetAddress(to);
			// 设置 To: 头部的header字段
			message.addRecipient(Message.RecipientType.TO, toAddress);
			// 设置 Subject: header字段
			message.setSubject(subject);
			// 现在设置的实际消息
			message.setText(body);
			// 定义发送协议
			Transport transport = ssn.getTransport("smtp");
			// 建立与服务器的链接
			transport.connect(smtphost, user, password);

			// 发送邮件
			transport.sendMessage(message, message.getRecipients(Message.RecipientType.TO));
			// transport.send(message);
			// 关闭邮件传输
			transport.close();
			System.out.println("邮件发送成功");
		} catch (Exception m) // 捕获异常
		{
			System.out.println("邮件系统异常");
			m.printStackTrace();
		}
		return code;
	}
	
	public static void sendemail(String email,Timestamp time) {
		// 以下变量为用户根据自己的情况设置
		String smtphost = "smtp.qq.com"; // 发送邮件服务器
		String user = "869872053@qq.com"; // 邮件服务器登录用户名
		String password = "fmsktqzlvvngbeae"; // 邮件服务器登录密码
		String from = "869872053@qq.com"; // 发送人邮件地址
		String to = email; // 接受人邮件地址
		String subject = "BBS"; // 邮件标题
		// 邮件内容
		String body = "亲爱的用户,你已被禁言,禁言截止时间" + time + "请注意保护好个人信息！";
		// 以下为发送程序，用户无需改动
		try {
			// 初始化Properties类对象
			Properties props = new Properties();
			// 设置mail.smtp.host属性
			props.put("mail.smtp.host", smtphost);
			// 设置使用验证
			props.put("mail.smtp.auth", "true");
			// 获取非共享的session对象
			Session ssn = Session.getInstance(props, null);
			// 创建一个默认的MimeMessage对象。
			MimeMessage message = new MimeMessage(ssn);
			// 创建InternetAddress对象
			InternetAddress fromAddress = new InternetAddress(from);
			// 设置From: 头部的header字段
			message.setFrom(fromAddress);
			// 创建InternetAddress对象
			InternetAddress toAddress = new InternetAddress(to);
			// 设置 To: 头部的header字段
			message.addRecipient(Message.RecipientType.TO, toAddress);
			// 设置 Subject: header字段
			message.setSubject(subject);
			// 现在设置的实际消息
			message.setText(body);
			// 定义发送协议
			Transport transport = ssn.getTransport("smtp");
			// 建立与服务器的链接
			transport.connect(smtphost, user, password);

			// 发送邮件
			transport.sendMessage(message, message.getRecipients(Message.RecipientType.TO));
			// transport.send(message);
			// 关闭邮件传输
			transport.close();
			System.out.println("邮件发送成功");
		} catch (Exception m) // 捕获异常
		{
			System.out.println("邮件系统异常");
			m.printStackTrace();
		}
	}
	
	/**
	 * 判读传进来的数据是否存在敏感词
	 * @param topic
	 * @return
	 */
	public static Topic filter(Topic topic) {
		String beforeTitle=topic.getTitle();
		String beforeContent=topic.getContent();
		String afterTitle=beforeTitle;
		String afterContent=beforeContent;
		List<Map<String,Object>> list=sd.query();
		for(int i=0;i<list.size();i++) {
			afterTitle=afterTitle.replace((String)list.get(i).get("sname"), "**");
			afterContent=afterContent.replace((String)list.get(i).get("sname"), "**");
		}
		if(!beforeTitle.equals(afterTitle)||!beforeContent.equals(afterContent)) {
			ud.addTime(topic.getUid());
		}
		topic.setTitle(afterTitle);
		topic.setContent(afterContent);
		return topic;
	}
}
