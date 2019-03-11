package dao;

import java.sql.Timestamp;
import java.util.List;
import java.util.Map;

import bean.UserInfo;
import utils.JDBCHelp;
import utils.Myutil;

public class UserDao {
	private JDBCHelp db=new JDBCHelp();
	
	/**
	 * 判断现在是否被禁言
	 * @param uid
	 * @return
	 */
	public boolean isPost(Integer uid) {
		String sql="select * from tbl_userinfo where uid=?";
		List<Map<String,Object>> list=db.executeQuery(sql, uid);
		if(list.get(0).get("canpost").equals("false")) {
			return false;
		}
		return true;
	}
	
	/**
	 * 增加发脏话的次数
	 * @param user
	 */
	public void addTime(Integer uid) {
		String sql="update tbl_userinfo set time=time+1 where uid=?";
		db.executeUpdate(sql, uid);
	}
	
	/**
	 * 开始禁言
	 * @param user
	 */
	public void stopPost(Integer uid) {
		String sql="update tbl_userinfo set canpost=?,starttime=?,endtime=? where uid=?";
		db.executeUpdate(sql, "false",new Timestamp(System.currentTimeMillis()),
				new Timestamp(System.currentTimeMillis()+24*60*60*1000),uid);
	}
	
	/**
	 * 解除禁言
	 * @param user
	 */
	public void releasePost(Integer uid) {
		String sql="update tbl_userinfo set canpost=?,starttime=null,endtime=null where uid=?";
		db.executeUpdate(sql, "true",uid);
	}
	
	/**
	 * 查询当前用户的所有发言信息
	 * @param uid
	 * @return
	 */
	public UserInfo selectAll(Integer uid) {
		String sql="select * from tbl_userinfo where uid=?";
		List<Map<String,Object>> list=db.executeQuery(sql, uid);
		return Myutil.ListMapToJavaBean(list, UserInfo.class).get(0) ;
	}
}
