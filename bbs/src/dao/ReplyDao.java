package dao;

import java.util.List;
import java.util.Map;

import utils.JDBCHelp;

public class ReplyDao {
	private JDBCHelp db=new JDBCHelp();
	/**
	 * ��������һ
	 * @param uid
	 * @param replyId
	 * @return
	 */
	public int agree(String topicid,String replyId) {
		String sql="update tbl_reply set agreecount=agreecount+1 where topicid=? and replyid=?";
		return db.executeUpdate(sql, topicid,replyId);
	}
	
	/**
	 * ��ѯ�����ĵ�����
	 * @param replyId
	 * @return
	 */
	public List<Map<String,Object>> selectAgreeCount(String replyId) {
		String sql="select agreecount from tbl_reply where replyid=?";
		return db.executeQuery(sql, replyId);
	}
	
	/**
	  *   ȡ������,��Ŀ��һ
	 * @param topicId
	 * @param replyId
	 * @return
	 */
	public Integer disagree(String topicId, String replyId) {
		String sql="update tbl_reply set agreecount=agreecount-1 where topicid=? and replyid=?";
		return db.executeUpdate(sql, topicId,replyId);
	}
}
