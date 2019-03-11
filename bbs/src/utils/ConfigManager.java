package utils;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;





/**
 * 用来读取配置文件信息
 * @author Administrator
 * 单例模式:在程序运行期间一个类只会创建一个实例
 * 特点:
 * 1.构造方法私有
 * 2.私有静态的成员属性(类型是当前类的类型,唯一创建的实例)
 * 3.对外公开的获取实例的方法
 * 4.单例模式一般分为两种:一是懒汉式,还有一种是饿汉式
 * 使用场景:一般用来加载资源
 */

public class ConfigManager extends Properties{
	/**
	 * 
	 */
	private static final long serialVersionUID = 3996121909392986425L;
	public static ConfigManager cm=new ConfigManager();
	private ConfigManager() {
		InputStream is=ConfigManager.class.getClassLoader().getResourceAsStream("db.properties");
		try {
			this.load(is);
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if(is!=null) {
				try {
					is.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}
	public static ConfigManager getInstance() {
		return cm;
	}
	
	//懒汉式这个方法一定要加synchronized关键字，防止同一时刻有多个用户去创建实例对象，那就不是单例
	/*public synchronized static ConfigManager getInstance() {
		if(cm==null) {
			cm=new ConfigManager();
		}else {
			return cm;
		}
	}*/
	
	
	/*public String getValue(String key) {
		//该对象用来读取properties配置文件
		Properties properties =new Properties();
		InputStream fis=null;		
		try {
			//Java项目可以用这种方式获取文件，web项目用这种方式的话回到服务器路径里去找这个文件
			//fis=new FileInputStream("src/db.properties");
			//web项目可以用这种方式获取项目路径下的文件
			fis=ConfigManager.class.getClassLoader().getResourceAsStream("db.properties");
			//加到properties对象里面
			properties.load(fis);
			//根据属性名获取对应的属性值
			return properties.getProperty(key);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
	@Test
	public void test() {
		System.out.println(getValue("driverClass"));
	}*/
}
