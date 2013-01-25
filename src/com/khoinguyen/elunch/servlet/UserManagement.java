package com.khoinguyen.elunch.servlet;

import com.google.appengine.api.datastore.Entity;
import com.google.gson.Gson;
import com.khoinguyen.elunch.model.User;


import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: fpta-dknguyen
 * Date: 1/25/13
 * Time: 1:19 PM
 * To change this template use File | Settings | File Templates.
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
            User.createOrUpdateUser(email, displayName);
            resp.sendRedirect("/admin/usermanagement.jsp");
        } else if (action.equals("getUsers")) {
            List<UserVO> users = getListUser();
            Gson gson = new Gson();
            resp.setContentType("application/json");
            resp.getWriter().print(gson.toJson(users));
        }
    }

    private List<UserVO> getListUser() {
        List<Entity> users = User.getUsers();
        List<UserVO> userVos = new ArrayList<UserVO>();
        if (users != null && users.size() > 0) {
            for (Entity e : users) {
                String email = e.getKey().getName();
                String name = (String) e.getProperty("displayName");
                UserVO vo = new UserVO(email, name);
                userVos.add(vo);
            }
        }
        return userVos;
    }
}

class UserVO {
    private String emailAddress;
    private String displayName;

    public UserVO(String email, String name) {
        this.emailAddress = email;
        this.displayName = name;
    }

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public String getEmailAddress() {

        return emailAddress;
    }

    public void setEmailAddress(String emailAddress) {
        this.emailAddress = emailAddress;
    }
}
