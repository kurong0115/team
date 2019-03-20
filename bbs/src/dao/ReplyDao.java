package dao;

import java.util.List;
import java.util.Map;

import utils.JDBCHelp;

public class ReplyDao {
	private JDBCHelp db=new JDBCHelp();
	/**
	 * 点赞数加一
	 * @param uid
	 * @param replyId
	 * @return
	 */
	public int agree(String topicid,String replyId) {
		String sql="update tbl_reply set agreecount=agreecount+1 where topicid=? and replyid=?";
		return db.executeUpdate(sql, topicid,replyId);
	}
	
	/**
	 * 查询回帖的点赞数
	 * @param replyId
	 * @return
	 */
	public List<Map<String,Object>> selectAgreeCount(String replyId) {
		String sql="select agreecount from tbl_reply where replyid=?";
		return db.executeQuery(sql, replyId);
	}
	
	/**
	  *   取消点赞,数目减一
	 * @param topicId
	 * @param replyId
	 * @return
	 */
	public Integer disagree(String topicId, String replyId) {
		String sql="update tbl_reply set agreecount=agreecount-1 where topicid=? and replyid=?";
		return db.executeUpdate(sql, topicId,replyId);
	}
}
