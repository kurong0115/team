package dao;

import java.util.List;
import java.util.Map;

import utils.JDBCHelp;

public class StopDao {
	private JDBCHelp db=new JDBCHelp();
	
	public List<Map<String,Object>> query(){
		String sql="select * from tbl_stop";
		return db.executeQuery(sql);
	}
}
