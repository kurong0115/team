package bean;

import java.io.Serializable;


public class Board implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 2768337501147357655L;
	private int boardid;
	private String boardname;
	private int parentid;
	private int sonTotal;
	

	public int getSonTotal() {
		return sonTotal;
	}

	public void setSonTotal(int sonTotal) {
		this.sonTotal = sonTotal;
	}

	public int getTopicsum() {
		return topicsum;
	}

	public void setTopicsum(int topicsum) {
		this.topicsum = topicsum;
	}

	public String getRecenttopicid() {
		return recenttopicid;
	}

	public void setRecenttopicid(String recenttopicid) {
		this.recenttopicid = recenttopicid;
	}

	public String getRecenttopictitle() {
		return recenttopictitle;
	}

	public void setRecenttopictitle(String recenttopictitle) {
		this.recenttopictitle = recenttopictitle;
	}

	public String getRecenttopicuserid() {
		return recenttopicuserid;
	}

	public void setRecenttopicuserid(String recenttopicuserid) {
		this.recenttopicuserid = recenttopicuserid;
	}

	public String getRecenttopicusername() {
		return recenttopicusername;
	}

	public void setRecenttopicusername(String recenttopicusername) {
		this.recenttopicusername = recenttopicusername;
	}



	public String getRecenttopicmodifytime() {
		return recenttopicmodifytime;
	}

	public void setRecenttopicmodifytime(String recenttopicmodifytime) {
		this.recenttopicmodifytime = recenttopicmodifytime;
	}

	public int getBoardid() {
		return boardid;
	}

	public void setBoardid(int boardid) {
		this.boardid = boardid;
	}

	public String getBoardname() {
		return boardname;
	}

	public void setBoardname(String boardname) {
		this.boardname = boardname;
	}

	public int getParentid() {
		return parentid;
	}

	public void setParentid(int parentid) {
		this.parentid = parentid;
	}

	public Board(int boardid, String boardname, int parentid) {
		super();
		this.boardid = boardid;
		this.boardname = boardname;
		this.parentid = parentid;
	}

	public Board() {
		super();
	}
	
	/**
	 *闄勫姞灞炴��
	 */
	private int topicsum;
	private String recenttopicid;
	private String recenttopictitle;
	private String recenttopicuserid;
	private String recenttopicusername;
	private String recenttopicmodifytime;


	@Override
	public String toString() {
		return "Board [boardid=" + boardid + ", boardname=" + boardname + ", parentid=" + parentid + ", sonTotal="
				+ sonTotal + ", topicsum=" + topicsum + ", recenttopicid=" + recenttopicid + ", recenttopictitle="
				+ recenttopictitle + ", recenttopicuserid=" + recenttopicuserid + ", recenttopicusername="
				+ recenttopicusername + ", recenttopicmodifytime=" + recenttopicmodifytime + "]";
	}

	

}
