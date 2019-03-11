package biz;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import bean.User;
import utils.JDBCHelp;
import utils.Myutil;

public class bbsUserBizImpl {

	JDBCHelp db=new JDBCHelp();
	
	//��ѯ�û�����¼
	public List<User> userLogin(User user) {
		String sql="select uid,uname,upass,head,gender,date_format(regtime,'%Y-%m-%d %H:%i:%s') as regtime from tbl_user where uname=? and upass=?";
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
		String sql="insert into tbl_user values(null,?,?,?,sysdate(),?)";
		List<Object> params =new ArrayList<>();
		params.add(user.getUname());
		params.add(user.getUpass());
		params.add(user.getHead());
		params.add(user.getGender());
		
		int executeUpdate = db.executeUpdate(sql, params);
		if(executeUpdate>0) {
			return 1;
		}else {
			return 0;
		}
	}
	
	/**
	 * ��ѯ�û�
	 * @return
	 */
	public List<Map<String, Object>> findUSer() {
		String sql="select uid,uname,head,date_format(regtime,'%Y-%m-%d %H:%i:%s') as regtime,gender from tbl_user";
		return db.executeQuery(sql);
	}
}