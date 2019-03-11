package bean;

import java.io.Serializable;

public class Topic extends PageBean<Topic> implements Serializable {
	private static final long serialVersionUID = -4666196229407434784L;
	private Integer topicid;
	private String title;
	private String content;
	private String publishtime;
	private String modifytime;
	private Integer boardid;

	private Integer uid;

	public Integer getUid() {
		return uid;
	}

	public void setUid(Integer uid) {
		this.uid = uid;
	}

	public Integer getTopicid() {
		return topicid;
	}

	public void setTopicid(Integer topicid) {
		this.topicid = topicid;
	}


	public void setBoardid(Integer boardid) {
		this.boardid = boardid;
	}

	public int getReplycount() {
		return replycount;
	}

	public void setReplycount(int replycount) {
		this.replycount = replycount;
	}

	public String getUname() {
		return uname;
	}

	public void setUname(String uname) {
		this.uname = uname;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getPublishtime() {
		return publishtime;
	}

	public void setPublishtime(String publishtime) {
		this.publishtime = publishtime;
	}

	public String getModifytime() {
		return modifytime;
	}

	public void setModifytime(String modifytime) {
		this.modifytime = modifytime;
	}

	public int getBoardid() {
		return boardid;
	}

	public void setBoardid(int boardid) {
		this.boardid = boardid;
	}

	private String uname;
	private String head;
	private String regtime;
	private int replycount;

	public String getHead() {
		return head;
	}

	public void setHead(String head) {
		this.head = head;
	}

	public String getRegtime() {
		return regtime;
	}

	public void setRegtime(String regtime) {
		this.regtime = regtime;
	}

	public Topic() {
		super();
	}

	@Override
	public String toString() {
		return "Topic [boardid=" + boardid + ", content=" + content + ", modifytime=" + modifytime + ", publishtime="
				+ publishtime + ", replycount=" + replycount + ", title=" + title + ", topicid=" + topicid + ", uname="
				+ uname + ", uid=" + uid + "]\n";
	}

}
