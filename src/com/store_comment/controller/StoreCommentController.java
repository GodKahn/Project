package com.store_comment.controller;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.store_comment.model.StoreCommentService;

public class StoreCommentController extends HttpServlet {

	public void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		doPost(req, res);
	}

	public void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {

		req.setCharacterEncoding("UTF-8");

		try {
			String mem_num = (String) req.getSession().getAttribute("mem_num");

			if (mem_num == null || (mem_num.trim()).length() == 0) {
				res.sendRedirect(req.getContextPath() + "/front-end/index.jsp");
				return;
			}
			
			List<String> errorMsgs = new LinkedList<String>();
			req.setAttribute("errorMsgs", errorMsgs);
			
			String action = req.getParameter("action");

			StoreCommentService scs = new StoreCommentService();

			if ("DELETE".equals(action)) {
				try {
					scs.deleteMyComment(req.getParameter("com_num"));
				} catch (Exception e) {
					errorMsgs.add("err");
				}
			} else if ("EDIT".equals(action)) {
				try {
					scs.updateMyComment(req.getParameter("com_title"), Integer.valueOf(req.getParameter("stars")),
							req.getParameter("comment"), req.getParameter("com_num"));
				} catch (Exception e) {
					errorMsgs.add("err");
				}
			} else if ("LOAD".equals(action)) {
				Thread.sleep(500);
				List<List> myCommentList = scs.getMyComments(mem_num, Integer.valueOf(req.getParameter("rows")));
				req.setAttribute("myCommentList", myCommentList);
				RequestDispatcher successView = req.getRequestDispatcher("/front-end/Store_Comment/MyComment.jsp");
				successView.forward(req, res);
				return;
			}

			List<List> myCommentList = scs.getMyComments(mem_num, 1);
			req.setAttribute("myCommentList", myCommentList);
			
			RequestDispatcher successView = req.getRequestDispatcher("/front-end/Store_Comment/StoreComment.jsp");
			successView.forward(req, res);

		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}