package biz;

import java.sql.Timestamp;
import java.util.List;
import java.util.Map;

import bean.PageBean;
import bean.Reply;
import bean.Topic;
import bean.UserInfo;
import dao.StopDao;
import dao.UserDao;
import utils.JDBCHelp;
import utils.Myutil;

public class replyBizImpl {
	private JDBCHelp db=new JDBCHelp();
	private UserDao ud=new UserDao();
	private StopDao sd=new StopDao();
	public static UserInfo userinfo;
	/**
	 * �ظ�����
	 * @throws BizException 
	 */
	public int answer(Topic topic,UserInfo userinfo,String email) throws BizException {
		userinfo=ud.selectAll(topic.getUid());
		System.out.println(userinfo);
		//�����Ե�ʱ���ܷ���
		if(userinfo.getEndtime()!=null&&userinfo.getEndtime().after(new Timestamp(System.currentTimeMillis()))) {
			System.out.println("���ѱ�����");
			throw new BizException("���ѱ�����,���Խ���ʱ��Ϊ"+userinfo.getEndtime());			
		}
		List<Map<String,Object>> list=sd.query();
		//�жϹ���ǰ��������Ƿ�һ��,�粻,�������û��Ĵ���
		String beforeTitle=topic.getTitle();
		String beforeContent=topic.getContent();
		String afterTitle=beforeTitle;
		String afterContent=beforeContent;
		for(int i=0;i<list.size();i++) {
//			afterTitle=afterTitle.replace((String)list.get(i).get("sname"), "**");
			afterContent=afterContent.replace((String)list.get(i).get("sname"), "**");
		}
		if(!beforeContent.equals(afterContent)) {
			ud.addTime(topic.getUid());
			userinfo.setTime(userinfo.getTime()+1);
		}
//		topic=Myutil.filter(topic);
		
		//ÿ�������໰����һ��
		if(userinfo.getTime()==3) {
			ud.stopPost(userinfo.getUid());
			Myutil.sendemail(email, new Timestamp(System.currentTimeMillis()+24*60*60*1000));
		}
		
		//���������óɹ���֮�������
//		topic.setTitle(afterTitle);
		topic.setContent(afterContent);
		this.userinfo=userinfo;
		String sql="insert into tbl_reply values(null,null,?,now(),now(),?,?)";
		return db.executeUpdate(sql, topic.getContent(),topic.getUid(),topic.getTopicid());
	}
	
	/**
	 * ��ѯ���������лظ�
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
	 * �鵱ǰ�����µĻظ�����
	 */
	public int findReplyCount(  Topic topic ) {
		String sql="select count(*) as total from tbl_reply where topicid=?";
		List<Map<String,Object>> executeQuery = db.executeQuery(sql, topic.getTopicid());
		return  Integer.parseInt( executeQuery.get(0).get("total").toString() );
	}
	
	/**
	 * ɾ������
	 */
	public int delReply(Reply reply) {
		String sql="delete from tbl_reply where replyid=?";
		return db.executeUpdate(sql, reply.getReplyid());
	}
	
	/**
	 * �鵱ǰ�������������
	 */
	public int findTopicCount( Topic topic ) {
		String sql="select count(*) as total from tbl_reply where topicid=?";
		List<Map<String, Object>> map = db.executeQuery(sql, topic.getTopicid());
		return  Integer.parseInt( map.get(0).get("total").toString() );
	}
	
	public PageBean<Reply> findPageBean(  Topic topic  ) {
		PageBean<Reply> pb=new PageBean<>();
		
		//�ð���������ӵ���Ϣ
		List<Reply> list = findReply( topic);
		
		//���ӵ��ܼ�¼��
		int total=findTopicCount( topic);
		
		
		pb.setList(list);
		
		pb.setTotal((long)total);
		
		//��ҳ��
		int totalpages=total%topic.getPagesize()==0?total/topic.getPagesize():(total/topic.getPagesize()+1);
		if(totalpages==0) {
			totalpages=1;
		}
		pb.setTotalPage((long)totalpages);


		return pb;
		
	}
}
