package bean;

import java.io.Serializable;

public class collect implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 9047418453919102001L;
	private int cid;
	private int topicid;
	private int boardid;
	private String boardname;
	private int uid;
	
	public int getCid() {
		return cid;
	}
	public void setCid(int cid) {
		this.cid = cid;
	}
	public int getTopicid() {
		return topicid;
	}
	public void setTopicid(int topicid) {
		this.topicid = topicid;
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
	public int getUid() {
		return uid;
	}
	public void setUid(int uid) {
		this.uid = uid;
	}
	
	
	private String sendname;
	private String title;
	private String collectime;
	private String sendid;

	public String getCollectime() {
		return collectime;
	}
	public void setCollectime(String collectime) {
		this.collectime = collectime;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getSendname() {
		return sendname;
	}
	public void setSendname(String sendname) {
		this.sendname = sendname;
	}
	public String getSendid() {
		return sendid;
	}
	public void setSendid(String sendid) {
		this.sendid = sendid;
	}
	@Override
	public String toString() {
		return "collect [cid=" + cid + ", topicid=" + topicid + ", boardid=" + boardid + ", boardname=" + boardname
				+ ", uid=" + uid + ", sendname=" + sendname + ", title=" + title + ", collectime=" + collectime
				+ ", sendid=" + sendid + "]";
	}
	

	
}
