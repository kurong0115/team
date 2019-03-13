package bean;

import java.io.Serializable;

public class User implements Serializable {
	private static final long serialVersionUID = -4750369623346428567L;

	private int uid;
	private String uname;
	private String upass;
	private String head;
	private String regtime;
	private Integer gender;
	private Integer total;
	

	
	public Integer getTotal() {
		return total;
	}

	public void setTotal(Integer total) {
		this.total = total;
	}

	public int getUid() {
		return uid;
	}

	public void setUid(int uid) {
		this.uid = uid;
	}

	public String getUname() {
		return uname;
	}

	public void setUname(String uname) {
		this.uname = uname;
	}

	public String getUpass() {
		return upass;
	}

	public void setUpass(String upass) {
		this.upass = upass;
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

	

	public Integer getGender() {
		return gender;
	}

	public void setGender(Integer gender) {
		this.gender = gender;
	}

	public User(int uid, String uname, String upass, String head,
			String regtime, int gender) {
		super();
		this.uid = uid;
		this.uname = uname;
		this.upass = upass;
		this.head = head;
		this.regtime = regtime;
		this.gender = gender;
	}

	public User() {
		super();
	}

	@Override
	public String toString() {
		return "User [uid=" + uid + ", uname=" + uname + ", upass=" + upass + ", head=" + head + ", regtime=" + regtime
				+ ", gender=" + gender + ", total=" + total + "]";
	}



}
