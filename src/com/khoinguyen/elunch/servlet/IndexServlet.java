package com.khoinguyen.elunch.servlet;

import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.appengine.api.datastore.Entity;
import com.khoinguyen.elunch.model.Order;
import com.khoinguyen.elunch.util.UserControlAccess;

public class IndexServlet extends HttpServlet {
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {

        UserControlAccess ucl = UserControlAccess.getInstance();
        String displayName = ucl.getUserDisplayName();
        if (displayName == null) {
            resp.setStatus(HttpServletResponse.SC_FORBIDDEN);
            return;
        }
        List<Entity> orders = Order.getOrdersByDate(new Date());
        req.setAttribute("orders", orders);
        req.setAttribute("displayName", displayName);
        Map<String, List<Entity>> ordersBySet = new HashMap<String, List<Entity>>();
		RequestDispatcher dispatcher = req.getRequestDispatcher("order.jsp");
		dispatcher.forward(req, resp);
	}
}
