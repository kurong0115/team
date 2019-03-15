package biz;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import bean.TblAdmin;
import dao.StopDao;
import dao.UserDao;
import utils.JDBCHelp;
import utils.Myutil;

public class adminBizImpl {
	private UserDao ud=new UserDao();
	private JDBCHelp db=new JDBCHelp();
	private StopDao sd=new StopDao();
	/**
	 * 管理员登录
	 * @param admin
	 * @return
	 */
	public TblAdmin login(TblAdmin admin) {
		String sql="select * from tbl_admin where raname=? and rapwd=?";
		List<Object> params=new ArrayList<>();
		params.add(admin.getRaname());
		params.add(admin.getRapwd());

		List<Map<String,Object>> list = db.executeQuery(sql, params);
		List<TblAdmin> loginAdmin = Myutil.ListMapToJavaBean(list, TblAdmin.class);

		if(loginAdmin.size()>0) {
			return loginAdmin.get(0);
		}else {
			return null;			
		}
	}
	
	/**
	 * 根据用户的uid来解除禁言
	 * @param uid
	 * @return
	 */
	public Integer releaseById(Integer uid) {
		return ud.releasePost(uid);
	}
	
	/**
	 * 查询所有的敏感词
	 * @return
	 */
	public List<Map<String, Object>> findAllWords() {		
		return sd.query();
	}
	
	
	public int delWordById(String sid) {
		return sd.delWordById(sid);
	}
}
