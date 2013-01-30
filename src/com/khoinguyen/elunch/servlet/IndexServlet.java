package com.khoinguyen.elunch.servlet;

import java.io.IOException;
import java.util.*;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.appengine.api.datastore.Entity;
import com.khoinguyen.elunch.oldmodel.Order;
import com.khoinguyen.elunch.util.DateUtil;
import com.khoinguyen.elunch.util.UserControlAccess;

public class IndexServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        UserControlAccess ucl = UserControlAccess.getInstance();
        String displayName = ucl.getUserDisplayName();
        String logoutURL = ucl.getUserService().createLogoutURL(req.getRequestURI());
        if (displayName == null) {
            resp.setContentType("text/html");
            resp.getWriter().print("You are not an user of eLunch system, please contact with your leader to proceed, thanks. <br/> <a href='" + logoutURL + "'>Logout.</a>");
            return;
        }
        List<Entity> orders = Order.getOrdersByDate(DateUtil.getSingaporeDate(new Date()));
        req.setAttribute("orders", orders);
        req.setAttribute("displayName", displayName);
        Map<String, List<Entity>> ordersBySet = new LinkedHashMap<String, List<Entity>>();
        List<Entity> setA = new ArrayList<Entity>();
        List<Entity> setB = new ArrayList<Entity>();
        List<Entity> setC = new ArrayList<Entity>();
        List<Entity> setD = new ArrayList<Entity>();
        if (orders != null && orders.size() > 0) {
            for (Entity e : orders) {
                String setType = (String) e.getProperty("set");
                if (setType.equals("A")) {
                    setA.add(e);
                } else if (setType.equals("B")) {
                    setB.add(e);
                } else if (setType.equals("C")) {
                    setC.add(e);
                } else if (setType.equals("D")) {
                    setD.add(e);
                }
            }
            ordersBySet.put("A", setA);
            ordersBySet.put("B", setB);
            ordersBySet.put("C", setC);
            ordersBySet.put("D", setD);
        }
        req.setAttribute("ordersbyset", ordersBySet);
        req.setAttribute("logoutURL", logoutURL);

        RequestDispatcher dispatcher = req.getRequestDispatcher("index.jsp");
        dispatcher.forward(req, resp);
    }
}
