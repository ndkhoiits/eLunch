package com.khoinguyen.elunch.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.khoinguyen.elunch.oldmodel.Order;
import com.khoinguyen.elunch.util.UserControlAccess;

public class OrderServlet extends HttpServlet {
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
        UserControlAccess acl = UserControlAccess.getInstance();
        String displayName = acl.getUserDisplayName();
		String set = req.getParameter("set");
		String note = req.getParameter("note");
		Order.createOrUpdateOrder(displayName, set, note);
		resp.sendRedirect("index");
	}
}
