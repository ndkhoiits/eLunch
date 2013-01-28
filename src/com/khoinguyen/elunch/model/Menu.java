package com.khoinguyen.elunch.model;

import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.KeyFactory;
import com.google.appengine.api.datastore.Query;
import com.khoinguyen.elunch.util.DateUtil;
import com.khoinguyen.elunch.util.Util;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: khoinguyen
 * Date: 1/26/13
 * Time: 10:15 AM
 * To change this template use File | Settings | File Templates.
 */
public class Menu {
    private static final String MENU_ENTITY = "Menu";

    public static void createOrOrderMenu(Date date, String menu) {
        Entity currentMenu;
        currentMenu = getMenu(DateUtil.getSingaporeDate(date));
        if (currentMenu == null) {
            currentMenu = new Entity(MENU_ENTITY, getDateInFormat(date));
        }
        currentMenu.setProperty("menu", menu);
        Util.persistEntity(currentMenu);
    }

    private static String getDateInFormat(Date date) {
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        String strDate = sdf.format(date);
        return strDate;
    }

    public static String getMenuHtml(Date date) {
        Entity entity = getMenu(date);
        String htmlResult = "";
        if (entity != null) {
            String menuContent = (String) entity.getProperty("menu");
            htmlResult = menuContent.replaceAll("\r\n", "<br/>");
        } else {
            htmlResult = "There is no set Today";
        }
        return htmlResult;
    }
    public static Entity getMenu(Date date) {
        String strDate = getDateInFormat(date);
        Key key = KeyFactory.createKey(MENU_ENTITY, strDate);
        Query q = new Query(MENU_ENTITY, key);
        List<Entity> menus = Util.queryAsList(q);
        if (menus != null && menus.size() > 0) {
            return menus.get(0);
        }
        return null;
    }
}
