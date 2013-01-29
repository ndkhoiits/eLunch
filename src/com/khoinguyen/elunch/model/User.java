package com.khoinguyen.elunch.model;

import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.KeyFactory;
import com.google.appengine.api.datastore.Query;
import com.khoinguyen.elunch.util.Util;

import java.util.List;

public class User {
    private static final String USER_ENTITY = "Users";

    public static void createOrUpdateUser(String email, String displayName) {
        Entity user = getUser(email);
        if (user == null) {
            Key k = KeyFactory.createKey(USER_ENTITY, email);
            user = new Entity(USER_ENTITY, email);
        }
        user.setProperty("displayName", displayName);
        user.setProperty("subscribe", true);
        Util.persistEntity(user);
    }

    public static void setUserSubsrcibe(String email, boolean isSubscribe) {
        Entity user = getUser(email);
        if (user != null) {
            user.setProperty("subscribe", isSubscribe);
            Util.persistEntity(user);
        }
    }

    public static void deleteUser(String email) {
        Key k = KeyFactory.createKey(USER_ENTITY, email);
        Util.deleteEntity(k);
    }

    public static Entity getUser(String email) {
        Key key = KeyFactory.createKey(USER_ENTITY, email);
        Query query = new Query(USER_ENTITY, key);
        List<Entity> users = Util.queryAsList(query);
        if (users != null && users.size() > 0) {
            return users.get(0);
        }
        return null;
    }

    public static List<Entity> getUsers() {
        Query query = new Query(USER_ENTITY);
        List<Entity> users = Util.queryAsList(query);
        return users;
    }
}
