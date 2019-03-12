package biz;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import bean.PageBean;
import bean.Topic;
import bean.UserInfo;
import dao.StopDao;
import dao.UserDao;
import utils.JDBCHelp;
import utils.Myutil;

public class topicBizImpl {
	
	private UserDao ud=new UserDao();
	private StopDao sd=new StopDao();
	JDBCHelp db=new JDBCHelp();
	
	/**
	 * ��ѯ��ǰ��������topic
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
	 * ����
	 * @throws BizException 
	 */
	public int post(Topic topic) throws BizException {
		UserInfo userinfo=ud.selectAll(topic.getUid());
		System.out.println(userinfo);
		//被禁言的时候不能发帖
		if(userinfo.getEndtime().after(new Timestamp(System.currentTimeMillis()))) {
			throw new BizException("您已被禁言");
		}
		List<Map<String,Object>> list=sd.query();
		//判断过滤前后的内容是否一致,如不,则增加用户的次数
		String beforeTitle=topic.getTitle();
		String beforeContent=topic.getContent();
		String afterTitle=null;
		String afterContent=null;
		for(int i=0;i<list.size();i++) {
			afterTitle=beforeTitle.replace((String)list.get(i).get("sname"), "**");
			afterContent=beforeContent.replace((String)list.get(i).get("sname"), "**");
		}
		if(!beforeTitle.equals(afterTitle)||!beforeContent.equals(afterContent)) {
			ud.addTime(topic.getUid());
			userinfo.setTime(userinfo.getTime()+1);
		}
		
		//每发三次脏话禁言一天
		if(userinfo.getTime()%3==0) {
			ud.stopPost(userinfo.getUid());
		}
		
		//把内容设置成过滤之后的内容
		topic.setTitle(afterTitle);
		topic.setContent(afterContent);
		
		String sql="insert into tbl_topic values(null,?,?,now(),now(),?,?)";
		return db.executeUpdate(sql, topic.getTitle()
							, topic.getContent()
							, topic.getUid()
							, topic.getBoardid()
				);
	}
	
	/**
	 * ��ѯ�������ӵ�����
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
	 * ɾ����ǰ����
	 */
	public int delTopic(Topic topic) {
		String sql1="delete from tbl_reply where topicid=?";
		String sql="delete from tbl_topic where topicid=?";
		db.executeUpdate(sql1, topic.getTopicid());
		return db.executeUpdate(sql, topic.getTopicid());
	}
	
	/**
	 * �鵱ǰ�������������
	 */
	public int findTopicCount( Topic topic ) {
		String sql="select count(*) as total from tbl_topic where boardid=?";
		List<Map<String, Object>> map = db.executeQuery(sql, topic.getBoardid());
		return  Integer.parseInt( map.get(0).get("total").toString() );
	}
	
	public PageBean<Topic> findPageBean(  Topic topic  ) {
		PageBean<Topic> pb=new PageBean<>();
		
		//�ð���������ӵ���Ϣ
		List<Topic> list = findBoardTopic( topic);
		
		//���ӵ��ܼ�¼��
		int total=findTopicCount( topic);
		
		pb.setList(list);
		
		pb.setTotal((long)total);
		
		//��ҳ��
		int totalpages=total%topic.getPagesize()==0?total/topic.getPagesize():(total/topic.getPagesize()+1);
		pb.setTotalPage((long)totalpages);


		return pb;
		
	}
}
