package com.friend_list.controller;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.websocket.Session;

import org.json.JSONException;
import org.json.JSONObject;

import com.friend_list.model.FriendListService;
import com.member_profile.model.MemberProfileService;
import com.member_profile.model.MemberProfileVO;

public class FriendListController extends HttpServlet {

	public void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		doPost(req, res);
	}

	public void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {

		req.setCharacterEncoding("UTF-8");

		try {
			String inv_mem_num = (String) req.getSession().getAttribute("mem_num"); // member

			if (inv_mem_num == null || (inv_mem_num.trim()).length() == 0) {
				res.sendRedirect(req.getContextPath() + "/front-end/index.jsp");
				return;
			}

			List<String> errorMsgs = new LinkedList<String>();
			req.setAttribute("errorMsgs", errorMsgs);

			String action = req.getParameter("action");
			
			FriendListService fls = new FriendListService();

			if ("DELETE".equals(action) || "CONFIRMFRIEND".equals(action)) {
				
				try {
					
					if("DELETE".equals(action)){
						
						fls.deleteFriends(inv_mem_num, req.getParameter("invd_mem_num")); // other
						fls.deleteFriends(req.getParameter("invd_mem_num"), inv_mem_num);
						
					}
					
					Set<MemberProfileVO> friendList = fls.getFirends(inv_mem_num);
					req.setAttribute("friendList", friendList);
					
					RequestDispatcher successView = req.getRequestDispatcher("/front-end/Friend_List/FriendListShow.jsp");
					successView.forward(req, res);
					return;
					
				} catch (Exception e) {
					
					errorMsgs.add("err");
					
				}
			} else if ("ADDFRIEND".equals(action) || "CANCEL".equals(action) || "SEARCHMEMBER".equals(action)) {
				
				try {

					List<List> memberList = null;

					if ("ADDFRIEND".equals(action)) {
						
						String invd_mem_num = req.getParameter("invd_mem_num");
						
						fls.sendAddFriends(inv_mem_num, invd_mem_num);
						
						MemberProfileService mps = new MemberProfileService();
						
						if (login.test.LoginInfo.getLoginRecord().containsKey(invd_mem_num)) {
							
							if (login.test.MemberChatServer.getFriendListSessions().containsKey(invd_mem_num)) {
								
								for (Session friendSession : login.test.MemberChatServer.getFriendListSessions().get(invd_mem_num)) {
									
									if (friendSession.isOpen()) {
										
										JSONObject sendJsonObj = new JSONObject();
										
										try {
											
											sendJsonObj.put("action", "addFriendRequest");
											sendJsonObj.put("inviter", mps.getMyProfile(inv_mem_num).getMem_name());
											
										} catch (JSONException e) {
											
											e.printStackTrace();
										}
										
										friendSession.getAsyncRemote().sendText(sendJsonObj.toString());
										
									}
								
								}
								
							}
							
						}
						
					} else if ("CANCEL".equals(action)) {
						
						fls.deleteFriends(inv_mem_num, req.getParameter("invd_mem_num"));
						
					}

					if (req.getParameter("searchName") != null) {
						
						memberList = fls.getMembers(req.getParameter("searchName"), inv_mem_num);
						
						req.getSession().setAttribute("searchName", req.getParameter("searchName"));
						
					} else {
						
						memberList = fls.getMembers(req.getSession().getAttribute("searchName").toString(), inv_mem_num);
					
					}
					
					req.setAttribute("memberList", memberList);
					List<MemberProfileVO> friendCheckList = fls.getFirendChecks(inv_mem_num);
					req.setAttribute("friendCheckList", friendCheckList);
					
					RequestDispatcher successView = req.getRequestDispatcher("/front-end/Friend_List/MemberList.jsp");
					successView.forward(req, res);
					return;

				} catch (Exception e) {
					
					errorMsgs.add("err");
					
				}
			} else if ("CONFIRM".equals(action) || "REJECT".equals(action) || "REFRASHFRIENDCHECK".equals(req.getParameter("action"))) {
				try {
					
					if ("CONFIRM".equals(action)) {
						
						fls.confirmAddFriends(req.getParameter("invd_mem_num"), inv_mem_num);
						
					} else if ("REJECT".equals(action)) {
						
						fls.deleteFriends(req.getParameter("invd_mem_num"), inv_mem_num);
						
					}
					
					List<MemberProfileVO> friendCheckList = fls.getFirendChecks(inv_mem_num);
					req.setAttribute("friendCheckList", friendCheckList);
					
					RequestDispatcher successView = req.getRequestDispatcher("/front-end/Friend_List/FriendCheckShow.jsp");
					successView.forward(req, res);
					return;
					
				} catch (Exception e) {
					errorMsgs.add("err");
				}
			}

			// get friends with status 'Y'
			Set<MemberProfileVO> friendList = fls.getFirends(inv_mem_num);
			req.setAttribute("friendList", friendList);

			// get friends with status 'Confirm'
			List<MemberProfileVO> friendCheckList = fls.getFirendChecks(inv_mem_num);
			req.setAttribute("friendCheckList", friendCheckList);

			RequestDispatcher successView = req.getRequestDispatcher("/front-end/Friend_List/FriendList.jsp");
			successView.forward(req, res);
				
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	
}