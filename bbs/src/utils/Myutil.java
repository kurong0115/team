package utils;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Random;
import java.util.Set;

import javax.servlet.http.HttpSession;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

public class Myutil {
	/**
	 * 借助fastJSON工具把List<Map>转为List<javabase>对象
	 * @param mList
	 * @param t
	 * @return
	 */

	public static <T> List<T> ListMapToJavaBean(List<Map<String, Object>> mList,Class<T> t){
		List<T> tList=new ArrayList<T>();
		for (Map<String,Object> map: mList) {
			//把map对象转为json字符串
			String strJson=JSON.toJSONString(map);
			//把json字符串转为jsonobject对象
			JSONObject json = JSONObject.parseObject(strJson);
			//把jsonobject对象转成javabase对象
			tList.add(JSON.toJavaObject(json, t));
		}
		return tList;
	}
	
	/**
	 * 借助fastJSON工具把Map<String,String[]>转为javabase对象
	 * @param mList 包含所有请求参数的map集合
	 * @param t JavaBean类型
	 * @return
	 */
	public static<T> T MapToJavaBean(Map<String, String[]> mMap,Class<T> t) {
		//把Map<String, String[]>转为Map<String, String>
		Set<Entry<String, String[]>> entrySet = mMap.entrySet();
		Map<String, String> map = new HashMap<>();
		for (Entry<String, String[]> entry : entrySet) {
			map.put(entry.getKey(), entry.getValue()[0]);
		}
		//把map对象转为json字符串
		String strJson=JSON.toJSONString(map);
		//把json字符串转为jsonobject对象
		JSONObject json = JSONObject.parseObject(strJson);
		
		return JSON.toJavaObject(json, t);
	}
	
	/**
	 * 验证功能
	 */
	
	public static BufferedImage createImgCode(HttpSession session) {
		//创建图片缓冲对象
		BufferedImage bi=new BufferedImage(60, 22, BufferedImage.TYPE_INT_RGB);
		//获取画笔
		Graphics g=bi.getGraphics();
		//设置画笔颜色
		g.setColor(new Color(187,255,255));
		//把颜色填充到指定位置和大小
		g.fillRect(0, 0, 60, 22);
		
		//创建随机对象
		Random r=new Random();
		
		g.setColor(Color.BLACK);
		//随机产生干扰线，使图像中的认证码不易被其他程序探测
		/*for(int i=0;i<20;i++) {
			int x=r.nextInt(68);
			int y=r.nextInt(22);
			int x1=r.nextInt(5);
			int y1=r.nextInt(5);
			g.setColor(new Color(r.nextInt(255), r.nextInt(255), r.nextInt(255)));
			//x，y为起点的坐标，x1，y1为终点的坐标
			g.drawLine(x, y, x+x1, y+y1);
		}*/
		
		//准备一个随机字符（用于随机生成的验证码）
		String a="";
        for(char i='A';i<'Z';i++) {
 			a+=i;
 		}
        for(char i='a';i<'z';i++) {
  			a+=i;
  		}
        for(char i='0';i<'9';i++) {
  			a+=i;
  		}
        char[] c=a.toCharArray();
        //用于存放生成的验证码字符
		StringBuffer s=new StringBuffer();
		//随机生成4个字符
		for(int i=0,len=c.length;i<4;i++) {
			//获取随机下标
			int index=r.nextInt(len);
			//设置画笔颜色
			g.setColor(new Color(r.nextInt(80), r.nextInt(150), r.nextInt(200)));
			//把字符画到画布指定的位置
			g.drawString(c[index]+"",4+i*15,18);
			//把生成的字符存到字符串缓冲区备用
			s.append(c[index]);
		}
		session.setAttribute("code", s.toString());
		return bi;
	}
}
