package dao;

import java.util.List;
import java.util.Map;

import utils.JDBCHelp;

public class StopDao {
	private JDBCHelp db=new JDBCHelp();
	
	/**
	 * 查询所有被禁的词语
	 * @return
	 */
	public List<Map<String,Object>> query(){
		String sql="select * from tbl_stop";
		return db.executeQuery(sql);
	}
	
	public int delWordById(String sid) {
		String sql="delete from tbl_stop where sid=?";
		return db.executeUpdate(sql, sid);
	}
}
