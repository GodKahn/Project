package com.favorite_store.model;

import java.util.Date;

public class FavoriteStoreVO implements java.io.Serializable {
	
	private String mem_num ;
	private String sto_num;
	private String is_favo;
	private Date chang_time;
	
	
	public Date getChang_time() {
		return chang_time;
	}
	public void setChang_time(Date chang_time) {
		this.chang_time = chang_time;
	}
	public String getMem_num() {
		return mem_num;
	}
	public void setMem_num(String mem_num) {
		this.mem_num = mem_num;
	}
	public String getSto_num() {
		return sto_num;
	}
	public void setSto_num(String sto_num) {
		this.sto_num = sto_num;
	}
	public String getIs_favo() {
		return is_favo;
	}
	public void setIs_favo(String is_favo) {
		this.is_favo = is_favo;
	}
	
}
