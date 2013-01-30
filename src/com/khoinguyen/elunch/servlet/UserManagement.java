package com.khoinguyen.elunch.servlet;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.khoinguyen.elunch.model.Users;


import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

/**
 * @author <a href="mailto:ndkhoi168@gmail.com">Khoi NGUYEN</a>
 */
public class UserManagement extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String email = req.getParameter("email");
        String displayName = req.getParameter("displayName");
        String action = req.getParameter("action");
        if (action == null) {
            return;
        }
        if (action.equals("add")) {
            Users entity = new Users();
            entity.setEmail(email);
            entity.setDisplayName(displayName);
            entity.save();
            resp.sendRedirect("/admin/usermanagement.jsp");
        } else if (action.equals("getUsers")) {
            List<Users> entity = Users.getUsers();
            Gson gson = new GsonBuilder().serializeNulls().create();
            resp.setContentType("application/json");
            resp.getWriter().print(gson.toJson(entity));
        } else if (action.equalsIgnoreCase("forksubscribe")) {

            List<Users> users = Users.getUsers();
            for (Users e : users) {
                e.setSubscribe(true);
                e.save();
            }
        }
    }
}