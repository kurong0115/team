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
	 * ����fastJSON���߰�List<Map>תΪList<javabase>����
	 * @param mList
	 * @param t
	 * @return
	 */

	public static <T> List<T> ListMapToJavaBean(List<Map<String, Object>> mList,Class<T> t){
		List<T> tList=new ArrayList<T>();
		for (Map<String,Object> map: mList) {
			//��map����תΪjson�ַ���
			String strJson=JSON.toJSONString(map);
			//��json�ַ���תΪjsonobject����
			JSONObject json = JSONObject.parseObject(strJson);
			//��jsonobject����ת��javabase����
			tList.add(JSON.toJavaObject(json, t));
		}
		return tList;
	}
	
	/**
	 * ����fastJSON���߰�Map<String,String[]>תΪjavabase����
	 * @param mList �����������������map����
	 * @param t JavaBean����
	 * @return
	 */
	public static<T> T MapToJavaBean(Map<String, String[]> mMap,Class<T> t) {
		//��Map<String, String[]>תΪMap<String, String>
		Set<Entry<String, String[]>> entrySet = mMap.entrySet();
		Map<String, String> map = new HashMap<>();
		for (Entry<String, String[]> entry : entrySet) {
			map.put(entry.getKey(), entry.getValue()[0]);
		}
		//��map����תΪjson�ַ���
		String strJson=JSON.toJSONString(map);
		//��json�ַ���תΪjsonobject����
		JSONObject json = JSONObject.parseObject(strJson);
		
		return JSON.toJavaObject(json, t);
	}
	
	/**
	 * ��֤����
	 */
	
	public static BufferedImage createImgCode(HttpSession session) {
		//����ͼƬ�������
		BufferedImage bi=new BufferedImage(60, 22, BufferedImage.TYPE_INT_RGB);
		//��ȡ����
		Graphics g=bi.getGraphics();
		//���û�����ɫ
		g.setColor(new Color(187,255,255));
		//����ɫ��䵽ָ��λ�úʹ�С
		g.fillRect(0, 0, 60, 22);
		
		//�����������
		Random r=new Random();
		
		g.setColor(Color.BLACK);
		//������������ߣ�ʹͼ���е���֤�벻�ױ���������̽��
		/*for(int i=0;i<20;i++) {
			int x=r.nextInt(68);
			int y=r.nextInt(22);
			int x1=r.nextInt(5);
			int y1=r.nextInt(5);
			g.setColor(new Color(r.nextInt(255), r.nextInt(255), r.nextInt(255)));
			//x��yΪ�������꣬x1��y1Ϊ�յ������
			g.drawLine(x, y, x+x1, y+y1);
		}*/
		
		//׼��һ������ַ�������������ɵ���֤�룩
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
        //���ڴ�����ɵ���֤���ַ�
		StringBuffer s=new StringBuffer();
		//�������4���ַ�
		for(int i=0,len=c.length;i<4;i++) {
			//��ȡ����±�
			int index=r.nextInt(len);
			//���û�����ɫ
			g.setColor(new Color(r.nextInt(80), r.nextInt(150), r.nextInt(200)));
			//���ַ���������ָ����λ��
			g.drawString(c[index]+"",4+i*15,18);
			//�����ɵ��ַ��浽�ַ�������������
			s.append(c[index]);
		}
		session.setAttribute("code", s.toString());
		return bi;
	}
}
