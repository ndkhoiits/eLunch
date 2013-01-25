package com.khoinguyen.elunch.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.appengine.api.users.User;
import com.google.appengine.api.users.UserService;
import com.google.appengine.api.users.UserServiceFactory;
import com.khoinguyen.elunch.model.Order;

public class OrderServlet extends HttpServlet {
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		UserService userService = UserServiceFactory.getUserService();
		User user = userService.getCurrentUser();
		String email = user.getEmail();
		String userId = user.getUserId();
		String set = req.getParameter("set");
		String note = req.getParameter("note");
		Order.createOrUpdateOrder(email, set, note);
		resp.sendRedirect("index");
	}
}
