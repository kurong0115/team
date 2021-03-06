package biz;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

import bean.PageBean;
import bean.Reply;
import bean.Topic;
import bean.UserInfo;
import dao.ReplyDao;
import dao.StopDao;
import dao.UserDao;
import utils.JDBCHelp;
import utils.Myutil;

public class replyBizImpl {
	private JDBCHelp db=new JDBCHelp();
	private UserDao ud=new UserDao();
	private StopDao sd=new StopDao();
	private ReplyDao rd=new ReplyDao();
	private SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	private  UserInfo info;
	
	public UserInfo getUserinfo() {
		return info;
	}

	public void setUserinfo(UserInfo info) {
		this.info = info;
	}

	/**
	 * 回复帖子
	 * @throws BizException 
	 */
	public int answer(Topic topic,String email) throws BizException {
		UserInfo userinfo=ud.selectAll(topic.getUid());
		System.out.println("调用answer方法时的userinfo"+userinfo);
		if(userinfo.getEndtime()!=null&&userinfo.getStarttime()!=null) {
			if(userinfo.getEndtime().before(new Timestamp(System.currentTimeMillis()))) {
				ud.releasePost(topic.getUid());
				userinfo.setStarttime(null);
				userinfo.setEndtime(null);
				userinfo.setTime(0);
			}
			if(userinfo.getEndtime()!=null&&userinfo.getEndtime().after(new Timestamp(System.currentTimeMillis()))) {
				System.out.println("您已被禁言");
				throw new BizException("您已被禁言,禁言结束时间为"+sdf.format(new Date(userinfo.getEndtime().getTime())));			
			}
		}
		this.setUserinfo(userinfo);
		System.out.println("传递后的"+this.getUserinfo());
		//被禁言的时候不能发帖
/*		if(userinfo.getEndtime()!=null&&userinfo.getEndtime().after(new Timestamp(System.currentTimeMillis()))) {
			System.out.println("您已被禁言");
			throw new BizException("您已被禁言,禁言结束时间为"+userinfo.getEndtime());			
		}
*/
		List<Map<String,Object>> list=sd.query();
		//判断过滤前后的内容是否一致,如不,则增加用户的次数
//		String beforeTitle=topic.getTitle();
		String beforeContent=topic.getContent();
//		String afterTitle=beforeTitle;
		String afterContent=beforeContent;
		for(int i=0;i<list.size();i++) {
//			afterTitle=afterTitle.replace((String)list.get(i).get("sname"), "**");
			afterContent=afterContent.replace((String)list.get(i).get("sname"), "**");
		}
		if(!beforeContent.equals(afterContent)) {
			ud.addTime(topic.getUid());
//			userinfo.setTime(userinfo.getTime()+1);
			info.setTime(info.getTime()+1);
		}
//		topic=Myutil.filter(topic);
		
		//每发三次脏话禁言一天
		if(info.getTime()==3) {
			ud.stopPost(userinfo.getUid());
			new Thread() {
				public void run() {
					Myutil.sendemail(email, new Timestamp(System.currentTimeMillis()+24*60*60*1000));
				}; {};
			}.start();
		}
		
		//把内容设置成过滤之后的内容
//		topic.setTitle(afterTitle);
		topic.setContent(afterContent);
//		this.userinfo=userinfo;
		String sql="insert into tbl_reply values(null,null,?,now(),now(),?,?,0)";
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
				"    date_format(regtime,'%Y-%m-%d %H:%i:%i') as  regtime,agreecount\r\n" + 
				"    agreecount,\r\n"+
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
	
	/**
	 * 点赞数加一
	 * @param uid
	 * @param replyId
	 * @return
	 */
	public int agree(String topicId, String replyId) {
		return rd.agree(topicId, replyId);
	}
	
	/**
	 * 根据回帖Id查询点赞的数目
	 * @param replyId
	 * @return
	 */
	public int selectAgreeCount(String replyId) {
		List<Map<String,Object>> list=rd.selectAgreeCount(replyId);
		return Integer.valueOf(list.get(0).get("agreecount").toString());
	}
	
	/**
	 * 赞的数目减一
	 * @param topicId
	 * @param replyId
	 * @return
	 */
	public Integer disagree(String topicId, String replyId) {
		return rd.disagree(topicId, replyId);
	}
}
