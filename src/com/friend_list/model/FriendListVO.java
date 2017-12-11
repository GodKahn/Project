package com.friend_list.model;

import java.util.Date;

public class FriendListVO implements java.io.Serializable {
	
	private String inv_mem_num ;
	private String invd_mem_num;
	private String isfriend;
	private Date join_time;
	
	public Date getJoin_time() {
		return join_time;
	}
	public void setJoin_time(Date join_time) {
		this.join_time = join_time;
	}
	public String getInv_mem_num() {
		return inv_mem_num;
	}
	public void setInv_mem_num(String inv_mem_num) {
		this.inv_mem_num = inv_mem_num;
	}
	public String getInvd_mem_num() {
		return invd_mem_num;
	}
	public void setInvd_mem_num(String invd_mem_num) {
		this.invd_mem_num = invd_mem_num;
	}
	public String getIsfriend() {
		return isfriend;
	}
	public void setIsfriend(String isfriend) {
		this.isfriend = isfriend;
	}
	
}
