package bean;

import java.io.Serializable;

public class Reply extends PageBean<Reply> implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 5850807114503161488L;

	private Integer replyid;

	private String content;
	private String publishtime;
	private String modifytime;
	private Integer uid;
	private Integer topicid;

	public Integer getReplyid() {
		return replyid;
	}

	public void setReplyid(Integer replyid) {
		this.replyid = replyid;
	}

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

	public String getUname() {
		return uname;
	}

	public void setUname(String uname) {
		this.uname = uname;
	}

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

	public Reply() {
		super();
	}

	private String uname;
	private String head;
	private String regtime;

	@Override
	public String toString() {
		return "Reply [replyid=" + replyid + ", content=" + content + ", publishtime=" + publishtime + ", modifytime="
				+ modifytime + ", uid=" + uid + ", topicid=" + topicid + ", uname=" + uname + ", head=" + head
				+ ", regtime=" + regtime + "]";
	}

	

}
