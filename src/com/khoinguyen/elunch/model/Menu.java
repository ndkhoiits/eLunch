/*
 *  Copyright (C) 2013 Khoi NGUYEN (ndkhoi168@gmail.com)
 *
 *  This file is part of eLunch.
 *
 *  eLunch is free software: you can redistribute it and/or modify
 *  it under the terms of the GNU Lesser General Public License as published by
 *  the Free Software Foundation, either version 3 of the License, or
 *  (at your option) any later version.
 *
 *  eLunch is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  GNU Lesser General Public License for more details.
 *
 *  You should have received a copy of the GNU Lesser General Public License
 *  along with eLunch.  If not, see <http://www.gnu.org/licenses/>.
 */
package com.khoinguyen.elunch.model;

import com.khoinguyen.elunch.dao.PMF;

import javax.jdo.PersistenceManager;
import javax.jdo.Query;
import javax.jdo.annotations.IdGeneratorStrategy;
import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;
import javax.jdo.annotations.PrimaryKey;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * @author <a href="mailto:ndkhoi168@gmail.com">Khoi NGUYEN</a>
 */
@PersistenceCapable
public class Menu {
    @PrimaryKey
    @Persistent(valueStrategy = IdGeneratorStrategy.IDENTITY)
    private String date;

    @Persistent
    private String menu;

    private static final PersistenceManager pmf = PMF.get().getPersistenceManager();

    public Menu(String menu, String date) {
        this.menu = menu;
        this.date = date;
    }

    public void save() {
        PMF.get().getPersistenceManager().makePersistent(this);
    }

    public static Menu getMenu(Date date) {
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        String strDate = sdf.format(date);
        Query query = pmf.newQuery(Menu.class, "date == '" + strDate + "'");
        List<Menu> menus = (List<Menu>) query.execute();
        if (menus != null && menus.size() > 0) {
            return menus.get(0);
        }
        return null;
    }

    public static String getMenuAsHtml(Date date) {
        Menu menuEntity = getMenu(date);
        String result = "";
        if (menuEntity != null) {
            result = menuEntity.getMenu().replaceAll("\r\n", "<br/>");
        } else {
            result = "Sorry, there is no set Today.";
        }
        return result;
    }

    public String getDate() {
        return date;
    }

    public String getMenu() {
        return menu;
    }
}
