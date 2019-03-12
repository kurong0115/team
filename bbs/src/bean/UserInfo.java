package bean;

import java.io.Serializable;
import java.sql.Timestamp;

public class UserInfo implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Integer uid;
	private Integer time;
	private Timestamp starttime;
	private Timestamp endtime;
	
	
	public UserInfo() {
		
	}
	
	
	
	public Integer getUid() {
		return uid;
	}



	public void setUid(Integer uid) {
		this.uid = uid;
	}



	public Integer getTime() {
		return time;
	}



	public void setTime(Integer time) {
		this.time = time;
	}


	public Timestamp getStarttime() {
		return starttime;
	}



	public void setStarttime(Timestamp starttime) {
		this.starttime = starttime;
	}



	public Timestamp getEndtime() {
		return endtime;
	}



	public void setEndtime(Timestamp endtime) {
		this.endtime = endtime;
	}



	@Override
	public String toString() {
		return "UserInfo [uid=" + uid + ", time=" + time + ", starttime=" + starttime + ", endtime=" + endtime + "]";
	}



	
	
	
}
