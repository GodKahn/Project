package com.favorite_store.controller;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.favorite_store.model.FavoriteStoreService;
import com.store_profile.model.StoreProfileVO;

public class FavoriteStoreController extends HttpServlet {

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

			FavoriteStoreService fss = new FavoriteStoreService();

			if ("DELETE".equals(action)) {
				try {
					
					fss.delFavoriteStore(mem_num, req.getParameter("sto_num"));
					
					List<StoreProfileVO> favoStoreList = fss.getFavoStoreList(mem_num);
					req.setAttribute("favoStoreList", favoStoreList);
					
					RequestDispatcher successView = req.getRequestDispatcher("/front-end/Favorite_Store/MyFavoriteStores.jsp");
					successView.forward(req, res);
					return;
					
				} catch (Exception e) {
					
					errorMsgs.add("err");
				}
			}

			List<StoreProfileVO> favoStoreList = fss.getFavoStoreList(mem_num);
			req.setAttribute("favoStoreList", favoStoreList);

			RequestDispatcher successView = req.getRequestDispatcher("/front-end/Favorite_Store/FavoriteStore.jsp");
			successView.forward(req, res);

		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}