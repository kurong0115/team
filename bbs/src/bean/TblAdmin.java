package bean;

import java.io.Serializable;

public class TblAdmin implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -4623297058080466294L;
	
	private Integer raid;
	private String raname;
	private String rapwd;
	public Integer getRaid() {
		return raid;
	}
	public void setRaid(Integer raid) {
		this.raid = raid;
	}
	public String getRaname() {
		return raname;
	}
	public void setRaname(String raname) {
		this.raname = raname;
	}
	public String getRapwd() {
		return rapwd;
	}
	public void setRapwd(String rapwd) {
		this.rapwd = rapwd;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	@Override
	public String toString() {
		return "resadmin [raid=" + raid + ", raname=" + raname + ", rapwd=" + rapwd + "]";
	}
	
	
}
