package com.khoinguyen.elunch.servlet;

import com.google.appengine.api.datastore.Entity;
import com.khoinguyen.elunch.model.Menu;
import com.khoinguyen.elunch.model.User;
import com.khoinguyen.elunch.util.DateUtil;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Properties;

/**
 * Created with IntelliJ IDEA.
 * User: khoinguyen
 * Date: 1/29/13
 * Time: 11:56 PM
 * To change this template use File | Settings | File Templates.
 */
public class SendEmail extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String action = req.getParameter("action");
        if (action == null) {
            return;
        } else if (action.equalsIgnoreCase("send")) {
            Date today = DateUtil.getSingaporeDate();
            String menu = Menu.getMenuHtml(today);
            Properties properties = new Properties();
            Session session = Session.getDefaultInstance(properties);
            try {
                String from = req.getParameter("from");
                if (from == null || from.length() == 0) {
                    from = "elunch.osim@gmail.com";
                }
                List<Entity> users = User.getUsers();
                SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
                String strToday = sdf.format(today);
                String unsubscribeURL = "http://osimlunch.appspot.com/email?action=unsubscribe";
                String subscribeURL = "http://osimlunch.appspot.com/email?action=subscribe";
                String messageText = "";
                messageText += "<b>Good morning, this is menu for " + strToday + "</b><br/><br/>";
                messageText += menu;
                messageText += "<br/><hr><p>You received this message because you are member of StarHub Onsiters Family and enjoy lunch with us!</p>";
                messageText += "<br/><p>If you don't want to receive this email, please unsubscibe by access to " + unsubscribeURL + "</p>";
                messageText += "<br/>To receive the email for menu again, you can request to " + subscribeURL;

                for (Entity e : users) {
                    Object subscribe = e.getProperty("subscribe");
                    if (subscribe != null && (subscribe instanceof Boolean)) {
                        Boolean subscribe1 = (Boolean) subscribe;
                        if (subscribe1) {
                            String email = e.getKey().getName();
                            MimeMessage message = new MimeMessage(session, req.getInputStream());
                            message.setFrom(new InternetAddress(from, "eLunch Admin"));
                            message.addRecipient(Message.RecipientType.TO, new InternetAddress(email));
                            message.setSubject("Lunch menu for today (" + strToday + ")");
                            message.setText(messageText, "utf-8", "html");
                            System.out.println(email);
                            Transport.send(message);
                        }
                    }

                }

                resp.getWriter().print("Email is sent by " + from);
            } catch (MessagingException e) {
                e.printStackTrace();
                resp.getWriter().print("Error " + e.getMessage());
            }
        }


    }
}
