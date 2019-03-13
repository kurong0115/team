package biz;

import utils.JDBCHelp;

public class WordbBizImpl {
	private JDBCHelp db=new JDBCHelp();
	
	/**
	 * ÃÌº”√Ù∏–¥ 
	 * @param word
	 * @return
	 */
	public int add(String word) {
		String sql="insert into tbl_stop values(null,?)";
		return db.executeUpdate(sql, word);
	}
}
