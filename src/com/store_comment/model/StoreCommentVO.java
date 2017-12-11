package com.store_comment.model;

import java.util.Date;

public class StoreCommentVO implements java.io.Serializable {

	private String com_num;
	private String com_title;
	private String sto_num;
	private String mem_num;
	private Integer stars;
	private String commentt;
	private Date com_time;
	private String status;
	private String or_num;
	
	
	public String getOr_num() {
		return or_num;
	}

	public void setOr_num(String or_num) {
		this.or_num = or_num;
	}
	
	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getCom_num() {
		return com_num;
	}

	public void setCom_num(String com_num) {
		this.com_num = com_num;
	}

	public String getCom_title() {
		return com_title;
	}

	public void setCom_title(String com_title) {
		this.com_title = com_title;
	}

	public String getSto_num() {
		return sto_num;
	}

	public void setSto_num(String sto_num) {
		this.sto_num = sto_num;
	}

	public String getMem_num() {
		return mem_num;
	}

	public void setMem_num(String mem_num) {
		this.mem_num = mem_num;
	}

	public Integer getStars() {
		return stars;
	}

	public void setStars(Integer stars) {
		this.stars = stars;
	}

	public String getCommentt() {
		return commentt;
	}

	public void setCommentt(String commentt) {
		this.commentt = commentt;
	}

	public Date getCom_time() {
		return com_time;
	}

	public void setCom_time(Date com_time) {
		this.com_time = com_time;
	}
}
