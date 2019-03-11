package biz;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import bean.PageBean;
import bean.Topic;
import utils.JDBCHelp;
import utils.Myutil;

public class topicBizImpl {
	
	
	JDBCHelp db=new JDBCHelp();
	
	/**
	 * 查询当前板块的所有topic
	 */
	public List<Topic> findBoardTopic(Topic topic) {
		StringBuffer sql=new StringBuffer();
		
		sql.append(" select * from (select a.topicid,title,content,publishtime,modifytime,uid,uname,boardid, total as replycount ");
		sql.append(" from		      (		     select topicid,title,content,date_format(publishtime,'%Y-%m-%d %H:%i:%s') as publishtime,date_format(modifytime,'%Y-%m-%d %H:%i:%s') as  modifytime,  tbl_user.uid,  uname,boardid ");
		sql.append(" from tbl_topic  inner join tbl_user on tbl_topic.uid=tbl_user.uid where boardid=?  order by modifytime desc limit ?,?) a ");
		sql.append(" left join  (select topicid, count(*) as total from tbl_reply group by topicid) b on a.topicid=b.topicid   order by publishtime desc )  d");
		List<Object> params=new ArrayList<>();
		params.add(topic.getBoardid());
		int start=(topic.getPages()-1)*topic.getPagesize();
		
		params.add(start);
		params.add(topic.getPagesize());
		
		List<Map<String,Object>> executeQuery = db.executeQuery(sql.toString(), params);
		return Myutil.ListMapToJavaBean(executeQuery, Topic.class);
				
	}
	/**
	 * 发帖
	 */
	public int post(Topic topic) {
		String sql="insert into tbl_topic values(null,?,?,now(),now(),?,?)";
		return db.executeUpdate(sql, topic.getTitle()
							, topic.getContent()
							, topic.getUid()
							, topic.getBoardid()
				);
	}
	
	/**
	 * 查询单个帖子的详情
	 */
	public Topic topicdetail(Topic topic) {
		String sql="select * from ("+
				"select topicid,title,content,\r\n" + 
				"       date_format(publishtime,'%Y-%m-%d %H:%i:%s') as publishtime,\r\n" + 
				"       date_format(modifytime,'%Y-%m-%d %H:%i:%s') as  modifytime, \r\n" + 
				"       tbl_topic.uid, \r\n" + 
				"       uname,\r\n" + 
				"       head,\r\n" + 
				"       date_format(regtime,'%Y-%m-%d %H:%i:%s') as  regtime, \r\n" + 
				"       boardid   \r\n" + 
				" from tbl_topic \r\n" + 
				" inner join tbl_user\r\n" + 
				" on tbl_topic.uid=tbl_user.uid\r\n" + 
				 " where topicid=?) a";
		List<Map<String,Object>> executeQuery = db.executeQuery(sql, topic.getTopicid());
		
		return Myutil.ListMapToJavaBean(executeQuery, Topic.class).get(0);
	}
	
	/**
	 * 删除当前帖子
	 */
	public int delTopic(Topic topic) {
		String sql1="delete from tbl_reply where topicid=?";
		String sql="delete from tbl_topic where topicid=?";
		db.executeUpdate(sql1, topic.getTopicid());
		return db.executeUpdate(sql, topic.getTopicid());
	}
	
	/**
	 * 查当前版块下贴子数量
	 */
	public int findTopicCount( Topic topic ) {
		String sql="select count(*) as total from tbl_topic where boardid=?";
		List<Map<String, Object>> map = db.executeQuery(sql, topic.getBoardid());
		return  Integer.parseInt( map.get(0).get("total").toString() );
	}
	
	public PageBean<Topic> findPageBean(  Topic topic  ) {
		PageBean<Topic> pb=new PageBean<>();
		
		//该板块所有帖子的信息
		List<Topic> list = findBoardTopic( topic);
		
		//帖子的总记录数
		int total=findTopicCount( topic);
		
		pb.setList(list);
		
		pb.setTotal((long)total);
		
		//总页数
		int totalpages=total%topic.getPagesize()==0?total/topic.getPagesize():(total/topic.getPagesize()+1);
		pb.setTotalPage((long)totalpages);


		return pb;
		
	}
}
