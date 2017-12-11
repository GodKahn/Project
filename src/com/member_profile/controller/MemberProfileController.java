package com.member_profile.controller;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.member_profile.model.MemberProfileService;
import com.member_profile.model.MemberProfileVO;

@MultipartConfig(fileSizeThreshold = 1 * 1024 * 1024, maxFileSize = 5 * 1024 * 1024, maxRequestSize = 5 * 5 * 1024
		* 1024)

public class MemberProfileController extends HttpServlet {

	public void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		doPost(req, res);
	}

	public void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {

		req.setCharacterEncoding("UTF-8");

		try {

			String mem_num = (String) req.getSession().getAttribute("mem_num");

			if (mem_num == null || (mem_num.trim()).length() == 0) {
				res.sendRedirect("/BA104G2/Login.jsp");
				return;
			}

			List<String> errorMsgs = new LinkedList<String>();
			req.setAttribute("errorMsgs", errorMsgs);

			String action = req.getParameter("action");

			MemberProfileService mps = new MemberProfileService();

			if ("EDITE".equals(action)) {
				
				if (req.getParameter("mem_name") != null && req.getParameter("mem_name").trim().length() > 0) {
					
					Pattern addressPattern = Pattern.compile("([^\\x00-\\x40\\x5B-\\x60\\x7B-\\x7F])+");
					Matcher addressmatcher = addressPattern.matcher(req.getParameter("mem_name"));
					
		            if (!addressmatcher.matches()) {
		            	
		            	errorMsgs.add("名字格式錯誤");
		            }
				}
				
				if (req.getParameter("mobile") != null && req.getParameter("mobile").trim().length() > 0) {
					
					Pattern addressPattern = Pattern.compile("^09\\d{2}-?\\d{3}-?\\d{3}$");
					Matcher addressmatcher = addressPattern.matcher(req.getParameter("mobile"));
					
		            if (!addressmatcher.matches()) {
		            	
		            	errorMsgs.add("手機格式錯誤");
		            }
				}
				
				if (req.getParameter("email") != null && req.getParameter("email").trim().length() > 0) {
					
					Pattern addressPattern = Pattern.compile("^\\w+((-\\w+)|(\\.\\w+))*\\@[A-Za-z0-9]+((\\.|-)[A-Za-z0-9]+)*\\.[A-Za-z]+$");
					Matcher addressmatcher = addressPattern.matcher(req.getParameter("email"));
					
		            if (!addressmatcher.matches()) {
		            	
		            	errorMsgs.add("信箱格式錯誤");
		            }
					
				}
				
				if (req.getParameter("address") != null && req.getParameter("address").trim().length() > 0) {
					
					Pattern addressPattern = Pattern.compile("(\\D+?[縣市])(\\D+?[路街巷])([0-9]+?[號])(.*+)");
					Matcher addressmatcher = addressPattern.matcher(req.getParameter("address"));
					
		            if (!addressmatcher.matches()) {
		            	
		            	errorMsgs.add("地址格式錯誤");
		            }
					
				} 
				
				if (req.getParameter("mem_pwd") != null && req.getParameter("mem_pwd").trim().length() > 0) {
					
		            if (!mps.getMyProfile(mem_num).getMem_pwd().equals(req.getParameter("mem_pwdConfirm"))) {
		            	System.out.println(req.getParameter("mem_pwdConfirm"));
		            	errorMsgs.add("密碼錯誤");
		            }
					
				} 
				
				if (errorMsgs.size() == 0) {
					
					mps.editeMyProfile(req.getParameter("mem_name"), req.getParameter("mem_pwd"),
							req.getParameter("mobile"), req.getParameter("email"), req.getParameter("address"),
							req.getPart("mem_pic"), mem_num, null);
					
				}
				
			}
			

			req.setAttribute("myProfile", mps.getMyProfile(mem_num));

			RequestDispatcher successView = req.getRequestDispatcher("/front-end/Member_Profile/MemberProfile.jsp");
			successView.forward(req, res);

		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}