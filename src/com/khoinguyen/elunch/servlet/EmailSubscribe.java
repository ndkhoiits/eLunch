package com.khoinguyen.elunch.servlet;

import com.google.appengine.api.datastore.Entity;
import com.khoinguyen.elunch.model.User;
import com.khoinguyen.elunch.util.UserControlAccess;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * Created with IntelliJ IDEA.
 * User: khoinguyen
 * Date: 1/30/13
 * Time: 12:58 AM
 * To change this template use File | Settings | File Templates.
 */
public class EmailSubscribe extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String email = req.getParameter("email");
        String action = req.getParameter("action");
        PrintWriter writer = resp.getWriter();
        if (email == null) {
            UserControlAccess ucl = UserControlAccess.getInstance();
            email = ucl.getUserService().getCurrentUser().getEmail();
        }
        if (email == null || action == null) {
            return;
        } else {
            if (action.equalsIgnoreCase("subscribe")) {
                if (doSubcribe(email, true)) {
                    writer.print("You subscribe successfully for email " + email);
                }
            } else if (action.equalsIgnoreCase("unsubscribe")) {
                if (doSubcribe(email, false)) {
                    writer.print("You unsubscribe successfully for email " + email);
                }
            }
        }

    }

    private boolean doSubcribe(String email, boolean b) {
        Entity user = User.getUser(email);
        if (user != null) {
            User.setUserSubsrcibe(email, b);
            return true;
        }
        return false;
    }
}
