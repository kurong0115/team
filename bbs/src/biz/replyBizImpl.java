package biz;

import java.util.List;
import java.util.Map;

import bean.PageBean;
import bean.Reply;
import bean.Topic;
import utils.JDBCHelp;
import utils.Myutil;

public class replyBizImpl {
	JDBCHelp db=new JDBCHelp();
	/**
	 * 回复帖子
	 */
	public int answer(Topic topic) {
		String sql="insert into tbl_reply values(null,null,?,now(),now(),?,?)";
		return db.executeUpdate(sql, topic.getContent(),topic.getUid(),topic.getTopicid());
	}
	
	/**
	 * 查询该帖的所有回复
	 */
	public List<Reply> findReply(Topic topic) {
		StringBuffer sql=new StringBuffer();
		sql.append("select replyid,content,date_format(publishtime,'%Y-%m-%d %H:%i:%s') as publishtime,\r\n" + 
				"    date_format(modifytime,'%Y-%m-%d %H:%i:%s') as modifytime, tbl_reply.uid, topicid,\r\n" + 
				"    uname,\r\n" + 
				"    head,\r\n" + 
				"    date_format(regtime,'%Y-%m-%d %H:%i:%i') as  regtime\r\n" + 
				"from tbl_reply\r\n" + 
				"inner join tbl_user\r\n" + 
				"on tbl_reply.uid=tbl_user.uid\r\n" + 
				"where topicid=?\r\n" + 
				"order by modifytime desc limit ?,?\r\n");
		int start=(topic.getPages()-1)*topic.getPagesize();
		List<Map<String,Object>> executeQuery = db.executeQuery(sql.toString(), topic.getTopicid(),start,topic.getPagesize());
		return Myutil.ListMapToJavaBean(executeQuery, Reply.class);
	}
	
	/**
	 * 查当前帖子下的回复数量
	 */
	public int findReplyCount(  Topic topic ) {
		String sql="select count(*) as total from tbl_reply where topicid=?";
		List<Map<String,Object>> executeQuery = db.executeQuery(sql, topic.getTopicid());
		return  Integer.parseInt( executeQuery.get(0).get("total").toString() );
	}
	
	/**
	 * 删除回帖
	 */
	public int delReply(Reply reply) {
		String sql="delete from tbl_reply where replyid=?";
		return db.executeUpdate(sql, reply.getReplyid());
	}
	
	/**
	 * 查当前版块下贴子数量
	 */
	public int findTopicCount( Topic topic ) {
		String sql="select count(*) as total from tbl_reply where topicid=?";
		List<Map<String, Object>> map = db.executeQuery(sql, topic.getTopicid());
		return  Integer.parseInt( map.get(0).get("total").toString() );
	}
	
	public PageBean<Reply> findPageBean(  Topic topic  ) {
		PageBean<Reply> pb=new PageBean<>();
		
		//该板块所有帖子的信息
		List<Reply> list = findReply( topic);
		
		//帖子的总记录数
		int total=findTopicCount( topic);
		
		
		pb.setList(list);
		
		pb.setTotal((long)total);
		
		//总页数
		int totalpages=total%topic.getPagesize()==0?total/topic.getPagesize():(total/topic.getPagesize()+1);
		if(totalpages==0) {
			totalpages=1;
		}
		pb.setTotalPage((long)totalpages);


		return pb;
		
	}
}
