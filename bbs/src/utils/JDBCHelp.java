package utils;
/**
 * JDBC帮助类(封装了最基本的增删改查操作)
 * @author Administrator
 *
 */

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;




public class JDBCHelp {
	static {
		try {
			Class.forName(ConfigManager.getInstance().getProperty("driverClass"));
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}
	//建立连接并获取连接对象
	public Connection getConnection() throws SQLException {
		String url=ConfigManager.getInstance().getProperty("url");
//		String user=ConfigManager.getInstance().getProperty("user");
//		String password=ConfigManager.getInstance().getProperty("password");
		return DriverManager.getConnection(url,ConfigManager.getInstance());
	}
	/**
	 * 给预编译语句中的占位符设置参数
	 * @param ps
	 * @param params
	 * @throws SQLException
	 */
	public void setParams(PreparedStatement ps,Object...params) throws SQLException {
		if(params!=null) {
			for(int i=0;i<params.length;i++) {
				ps.setObject(i+1, params[i]);
			}
		}
	}
	public void setParams(PreparedStatement ps,List<Object> params) throws SQLException {
		if(params!=null) {
			for(int i=0;i<params.size();i++) {
				ps.setObject(i+1, params.get(i));
			}
		}
	}
	/**
	 * 释放资源
	 */
	public void close(Connection con,PreparedStatement ps) {
		if(ps!=null) {
			try {
				ps.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		if(con!=null) {
			try {
				con.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
	public void close(Connection con,PreparedStatement ps,ResultSet rs) {
		if(rs!=null) {
			try {
				rs.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		if(ps!=null) {
			try {
				ps.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		if(con!=null) {
			try {
				con.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
	/**
	 * 通用的增删改方法
	 */
	public  int executeUpdate(String sql,Object...params) {
		Connection con=null;
		PreparedStatement ps=null;
		try {
			con=getConnection();
			ps=con.prepareStatement(sql);
			setParams(ps, params);
			return ps.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			close(con,ps);
		}
		return 0;
	}
	
	public  int executeUpdate(String sql,List<Object> params) {
		Connection con=null;
		PreparedStatement ps=null;
		try {
			con=getConnection();
			ps=con.prepareStatement(sql);
			setParams(ps, params);
			return ps.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			close(con,ps);
		}
		return 0;
	}
	/**
	 * 通用的查找操作
	 */
	public List<Map<String,Object>> executeQuery(String sql,Object...params){
		Connection con=null;
		PreparedStatement ps=null;
		ResultSet rs=null;
		//用来保存以map结构存放的记录
		List<Map<String,Object>> list = new ArrayList<>();
		try {
			con=getConnection();
			ps=con.prepareStatement(sql);
			setParams(ps, params);
			rs = ps.executeQuery();
			//获取数据库表结构的元数据(描述数据库表结构的数据，就是可以获取到表结构的相关信息)
			ResultSetMetaData metaData = rs.getMetaData();
			//获取每一条记录的列数(字段数)
			int count=metaData.getColumnCount();
			
			while(rs.next()) {
				//用来存放记录的map集合，一条记录以字段的方式存放在map里面，列名为key，值为value
				Map<String,Object> map=new HashMap<>();
				for(int i=0;i<count;i++) {
					//getColumnName():获取列名
					map.put(metaData.getColumnName(i+1), rs.getObject(metaData.getColumnName(i+1)));
				}
				list.add(map);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			close(con,ps,rs);
		}
		return list;
	}
	public List<Map<String,Object>> executeQuery(String sql,List<Object> params){
		Connection con=null;
		PreparedStatement ps=null;
		ResultSet rs=null;
		List<Map<String,Object>> list = new ArrayList<>();
		try {
			con=getConnection();
			ps=con.prepareStatement(sql);
			setParams(ps, params);
			rs = ps.executeQuery();
			ResultSetMetaData metaData = rs.getMetaData();
			int count=metaData.getColumnCount();
			while(rs.next()) {
				Map<String,Object> map=new HashMap<>();
				for(int i=0;i<count;i++) {
					map.put(metaData.getColumnName(i+1), rs.getObject(metaData.getColumnName(i+1)));
				}
				list.add(map);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			close(con,ps,rs);
		}
		return list;
	}
}
