package com.friend_list.model;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.member_profile.model.MemberProfileVO;

public class FriendListService {

	private FriendListDAO_interface dao;

	public FriendListService() {
		dao = new FriendListDAO();
	}

	public FriendListVO sendAddFriends(String inv_mem_num, String invd_mem_num) {

		FriendListVO friendlistVO = new FriendListVO();

		friendlistVO.setInv_mem_num(inv_mem_num);
		friendlistVO.setInvd_mem_num(invd_mem_num);
		friendlistVO.setIsfriend("CONFIRM");

		try {
			dao.insert(friendlistVO);
		} catch (RuntimeException e) {
			dao.update(friendlistVO);
		}
		return friendlistVO;
	}

	public FriendListVO deleteFriends(String inv_mem_num, String invd_mem_num) {

		FriendListVO friendlistVO = new FriendListVO();

		friendlistVO.setInv_mem_num(inv_mem_num);
		friendlistVO.setInvd_mem_num(invd_mem_num);
		friendlistVO.setIsfriend("N");

		dao.update(friendlistVO);

		return friendlistVO;
	}

	public FriendListVO confirmAddFriends(String inv_mem_num, String invd_mem_num) {

		FriendListVO friendlistVO = new FriendListVO();

		friendlistVO.setInv_mem_num(inv_mem_num);
		friendlistVO.setInvd_mem_num(invd_mem_num);
		friendlistVO.setIsfriend("Y");
		dao.update(friendlistVO);

		return friendlistVO;
	}

	public Set<MemberProfileVO> getFirends(String inv_mem_num) {

		FriendListVO friendlistVO = new FriendListVO();
		Set<MemberProfileVO> friendlist = new HashSet<MemberProfileVO>();

		friendlistVO.setInv_mem_num(inv_mem_num);
		friendlistVO.setIsfriend("Y");

		for (MemberProfileVO member : dao.getFromInvd(friendlistVO)) {
			friendlist.add(member);
		}

		for (MemberProfileVO member : dao.getFromInv(friendlistVO)) {
			friendlist.add(member);
		}

		return friendlist;
	}

	public List<MemberProfileVO> getFirendChecks(String inv_mem_num) {

		FriendListVO friendlistVO = new FriendListVO();

		friendlistVO.setInv_mem_num(inv_mem_num);
		friendlistVO.setIsfriend("CONFIRM");

		return dao.getFromInvd(friendlistVO);
	}

	public List<List> getMembers(String name, String inv_mem_num) {

		FriendListVO friendlistVO = new FriendListVO();
		List<List> memberList = new ArrayList<List>();

		friendlistVO.setInv_mem_num(inv_mem_num);
		friendlistVO.setIsfriend("Y");

		memberList.add(dao.getMembers(name, friendlistVO));

		friendlistVO.setIsfriend("CONFIRM");

		memberList.add(dao.getFromInv(friendlistVO));

		return memberList;
	}
	
}
