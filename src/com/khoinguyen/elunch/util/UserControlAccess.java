package com.khoinguyen.elunch.util;

import com.google.appengine.api.users.User;
import com.google.appengine.api.users.UserService;
import com.google.appengine.api.users.UserServiceFactory;
import com.khoinguyen.elunch.model.Users;

/**
 * @author <a href="mailto:ndkhoi168@gmail.com">Khoi NGUYEN</a>
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
        String displayName = Users.getDisplayNameByEmail(emailAddress);
        return displayName;
    }
}
