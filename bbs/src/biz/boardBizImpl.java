package biz;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import bean.Board;
import utils.JDBCHelp;
import utils.Myutil;

public class boardBizImpl {
	JDBCHelp db=new JDBCHelp();
	/**
	 * ȡ�����а����Ϣ
	 */
	
	public Map<Integer ,List<Board>> findAllBoard() {
		StringBuffer sql = new StringBuffer();
	
		sql.append("select * from (select a.boardid,boardname,parentid, total AS topicsum, topicid AS recenttopicid,title AS recenttopictitle,date_format(modifytime,'%Y-%m-%d %H:%I:%S') as recenttopicmodifytime,uname AS recenttopicusername");
		sql.append(" from (select tbl_board.boardid,boardname,parentid , count( topicid ) as total from tbl_board left join tbl_topic on tbl_board.boardid=tbl_topic.boardid group by tbl_board.boardid,boardname,parentid ) a ");
		sql.append(" left join (select topicid,title,a.modifytime,uname,a.boardid from (	select topicid, title, modifytime, uname, boardid from tbl_topic left join tbl_user on tbl_topic.uid=tbl_user.uid ) a,");
		sql.append(" ( select boardid,max(modifytime) as modifytime from tbl_topic group by boardid ) b 	where  a.boardid=b.boardid and a.modifytime=b.modifytime ) b");
		sql.append(" on a.boardid=b.boardid) c"); 
		
		List<Map<String,Object>> executeQuery = db.executeQuery(sql.toString());
		List<Board> list = Myutil.ListMapToJavaBean(executeQuery, Board.class);
		
		Map<Integer ,List<Board>> map=new HashMap<Integer ,List<Board>>();
		
		//ѭ�����е�list 
		//ȡ��ÿ��baord�е�parentid,
		//����parentid��map�в��Ƿ�����������id ���Ӱ���б�,
		//�����,�����board�浽����Ӱ���б���
		//���û��,�򴴽�һ��List<Board>,�����board�浽List<Board>,�ٽ����List<Board>�浽map��
		
		for (Board board : list) {
			List<Board> sonList=null;
			if(map.containsKey(board.getParentid())) {
				sonList=map.get(board.getParentid());
			}else {
				sonList=new ArrayList<Board>();
			}
			sonList.add(board);
			map.put(board.getParentid(), sonList);		
		}
		return map;
	}
}