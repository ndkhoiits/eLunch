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
import javax.jdo.annotations.*;
import java.util.ArrayList;
import java.util.List;

/**
 * @author <a href="mailto:ndkhoi168@gmail.com">Khoi NGUYEN</a>
 */

@PersistenceCapable
public class Users {
    @PrimaryKey
    @Persistent(valueStrategy = IdGeneratorStrategy.IDENTITY)
    private String email;

    @Persistent
    private String displayName;

    @Persistent
    private Boolean subscribe = true;

    @Persistent
    private Boolean active = true;

    private static PersistenceManager getPM() {
        return PMF.get().getPersistenceManager();
    }

    public void save() {
        PersistenceManager pm = getPM();
        if (email != null) {
            pm.makePersistent(this);
            pm.close();
        }
    }

    public static List<Users> getUsers() {
        PersistenceManager pm = getPM();
        Query query = pm.newQuery(Users.class);
        List<Users> listUser = (List<Users>) query.execute();
        List<Users> result = new ArrayList<Users>();
        if (listUser != null) {
            for (Users u : listUser) {
                result.add(u);
            }
        }
        query.closeAll();
        pm.close();
        return result;
    }

    public static Users getUserByEmail(String email) {
        PersistenceManager pm = getPM();
        Users entity = pm.getObjectById(Users.class, email);
        if (entity != null) {
            pm.close();
            return entity;
        }
        return null;
    }

    public static String getDisplayNameByEmail(String email) {
        String displayName = null;
        Users entity = getUserByEmail(email);
        if (entity != null) {
            displayName = entity.getDisplayName();
        }
        return displayName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public boolean isSubscribe() {
        return subscribe;
    }

    public void setSubscribe(boolean subscribe) {
        this.subscribe = subscribe;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }
}
