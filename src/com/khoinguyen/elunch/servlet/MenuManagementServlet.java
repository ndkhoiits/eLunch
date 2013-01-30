package com.khoinguyen.elunch.servlet;

import com.google.gson.Gson;
import com.khoinguyen.elunch.model.Menu;
import com.khoinguyen.elunch.util.DateUtil;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class MenuManagementServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String action = req.getParameter("action");
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        Date today = DateUtil.getSingaporeDate();
        if (action != null && action.equals("getmenu")) {
            String todayMenu = Menu.getMenuAsHtml(today);
            Gson gs = new Gson();
            String json = gs.toJson(todayMenu);
            resp.setContentType("application/json");
            resp.getWriter().print(json);
            return;
        }
        String date = req.getParameter("date");
        String menu = req.getParameter("menu");
        resp.sendRedirect("/admin/");
        Menu menuEntity = new Menu(menu, date);
        menuEntity.save();
    }
}
