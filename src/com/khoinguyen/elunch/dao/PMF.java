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
package com.khoinguyen.elunch.dao;

import javax.jdo.JDOHelper;
import javax.jdo.PersistenceManagerFactory;

/**
 * @author <a href="mailto:ndkhoi168@gmail.com">Khoi NGUYEN</a>
 */
public final class PMF {
    private static final PersistenceManagerFactory pmfInstance = JDOHelper.getPersistenceManagerFactory("transactions-optional");

    private PMF() {

    }

    public static PersistenceManagerFactory get() {
        return pmfInstance;
    }
}
