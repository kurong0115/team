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
	 * �ж������Ƿ񱻽���
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
	 * ���ӷ��໰�Ĵ���
	 * @param user
	 */
	public void addTime(Integer uid) {
		String sql="update tbl_userinfo set time=time+1 where uid=?";
		db.executeUpdate(sql, uid);
	}
	
	/**
	 * ��ʼ����
	 * @param user
	 */
	public void stopPost(Integer uid) {
		String sql="update tbl_userinfo set starttime=?,endtime=? where uid=?";
		db.executeUpdate(sql,new Timestamp(System.currentTimeMillis()),
				new Timestamp(System.currentTimeMillis()+24*60*60*1000),uid);
	}
	
	/**
	 * �������
	 * @param user
	 */
	public int releasePost(Integer uid) {
		String sql="update tbl_userinfo set starttime=null,endtime=null where uid=?";
		return db.executeUpdate(sql, uid);
	}
	
	/**
	 * ��ѯ��ǰ�û������з�����Ϣ
	 * @param uid
	 * @return
	 */
	public UserInfo selectAll(Integer uid) {
		String sql="select * from tbl_userinfo where uid=?";
		List<Map<String,Object>> list=db.executeQuery(sql, uid);
		return Myutil.ListMapToJavaBean(list, UserInfo.class).get(0) ;
	}
	
	public void releaseAll() {
		String sql="UPDATE tbl_userinfo SET starttime=NULL,endtime=NULL WHERE endtime<NOW()";
		db.executeUpdate(sql);
	}
}
