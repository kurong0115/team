package biz;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import bean.User;
import utils.JDBCHelp;
import utils.Myutil;

public class bbsUserBizImpl {

	JDBCHelp db=new JDBCHelp();
	
	
	//�û��޸ĸ�����Ϣ
	public Integer personalChange(String uname,String gender,String email,String uid) {
		//�Ȳ鿴��һЩû�иı�
		String sql = "UPDATE tbl_user SET uname = ? ,gender = ?,email = ? where uid = ?";
		return db.executeUpdate(sql, uname,gender,email,uid);
	}
	//�û��޸�����
		public Integer pwdchange(Integer uid ,String upass) {
			String sql = "UPDATE `bbs`.`tbl_user` SET `upass` = ? WHERE `uid` = ?; ";
			return db.executeUpdate(sql, upass,uid);		
		}
	
	//��ѯ�û����¼
	public List<User> userLogin(User user) {
		String sql="select uid,uname,upass,head,gender,date_format(regtime,'%Y-%m-%d %H:%i:%s') as regtime,email from tbl_user where uname=? and upass=?";
		List<Object> params =new ArrayList<>();
		params.add(user.getUname());
		params.add(user.getUpass());
		List<Map<String,Object>> executeQuery = db.executeQuery(sql, params);
		return (List<User>) Myutil.ListMapToJavaBean(executeQuery, User.class);
	}
	
	//����ע����û����Ƿ����
	public int isUserName(User user) {
		String sql="select * from tbl_user where uname=?";
		List<Map<String,Object>> executeQuery = db.executeQuery(sql, user.getUname());
		if(executeQuery.size()>0) {
			return 1;
		}else {
			return 0;
		}
	}
	
	//ע���û�
	public int regUser(User user) {
		String sql="insert into tbl_user values(null,?,?,?,sysdate(),?,?)";
		List<Object> params =new ArrayList<>();
		params.add(user.getUname());
		params.add(user.getUpass());
		params.add(user.getHead());
		params.add(user.getGender());
		params.add(user.getEmail());
		int executeUpdate = db.executeUpdate(sql, params);
		if(executeUpdate>0) {
			return 1;
		}else {
			return 0;
		}
	}
	
	public void addExpendInfo(User user) {
		String sql="insert into tbl_userinfo values(?,0,null,null)";
		db.executeUpdate(sql, user.getUid());
	}
	/**
	 * ��ѯ�û�
	 * @return
	 */
	public List<Map<String, Object>> findUSer() {
		String sql="select uid,uname,head,date_format(regtime,'%Y-%m-%d %H:%i:%s') as regtime,gender from tbl_user";
		return db.executeQuery(sql);
	}

	public List<Map<String, Object>> findUserInfo() {
		String sql="select a.*,b.uname from tbl_userinfo a ,tbl_user b where a.uid=b.uid";
		return db.executeQuery(sql);
	}
	
	public List<Map<String,Object>> getBasicInfo(String uname,String upass){
		String sql="select uid from tbl_user where uname=? and upass=?";
		return db.executeQuery(sql, uname,upass);
	}
	
	public int resetpwd(String upass,String uname) {
		String sql = "UPDATE tbl_user SET upass = ? WHERE uname = ?";
		return db.executeUpdate(sql, upass,uname);
	}
}

