package com.khoinguyen.elunch.servlet;

import com.khoinguyen.elunch.model.Users;
import com.khoinguyen.elunch.util.UserControlAccess;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * @author <a href="mailto:ndkhoi168@gmail.com">Khoi NGUYEN</a>
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
        Users entity = Users.getUserByEmail(email);
        if (entity != null) {
            entity.setSubscribe(b);
            entity.save();
            return true;
        }
        return false;
    }
}
