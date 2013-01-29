package com.khoinguyen.elunch.util;

import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.users.User;
import com.google.appengine.api.users.UserService;
import com.google.appengine.api.users.UserServiceFactory;

/**
 * Created with IntelliJ IDEA.
 * User: khoinguyen
 * Date: 1/26/13
 * Time: 1:07 AM
 * To change this template use File | Settings | File Templates.
 */
public class UserControlAccess {
    private static UserControlAccess _instance;

    public UserService getUserService() {
        return userService;
    }

    private UserService userService;

    private UserControlAccess() {
        userService = UserServiceFactory.getUserService();
    }

    public static UserControlAccess getInstance() {
        if (_instance == null) {
            _instance = new UserControlAccess();
        }
        return _instance;
    }

    public String getUserEmail() {
        User user = userService.getCurrentUser();
        return user.getEmail();
    }

    public String getUserDisplayName() {
        String emailAddress = getUserEmail();
        Entity user = com.khoinguyen.elunch.model.User.getUser(emailAddress);
        String displayName = null;
        if (user != null) {
            displayName = (String) user.getProperty("displayName");
        }
        return displayName;
    }
}
