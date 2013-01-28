package com.khoinguyen.elunch.servlet;

import com.google.appengine.api.datastore.Entity;
import com.google.gson.Gson;
import com.khoinguyen.elunch.model.Menu;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created with IntelliJ IDEA.
 * User: khoinguyen
 * Date: 1/26/13
 * Time: 10:14 AM
 * To change this template use File | Settings | File Templates.
 */
public class MenuManagementServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String action = req.getParameter("action");
        if (action != null && action.equals("getmenu")) {
            String todayMenu = Menu.getMenuHtml(new Date());
            Gson gs = new Gson();
            String json = gs.toJson(todayMenu);
            resp.setContentType("application/json");
            resp.getWriter().print(json);
            return;
        }
        String date = req.getParameter("date");
        String menu = req.getParameter("menu");
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        try {
            Date today = sdf.parse(date);
            Menu.createOrOrderMenu(today, menu);
            resp.sendRedirect("/admin/");
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }
}
