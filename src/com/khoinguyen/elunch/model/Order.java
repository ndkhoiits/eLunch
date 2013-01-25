package com.khoinguyen.elunch.model;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.KeyFactory;
import com.google.appengine.api.datastore.Query;
import com.khoinguyen.elunch.util.Util;

public class Order {
	public static final String Order = "Order";
	public static final String User = "User";

	public static void createOrUpdateOrder(String user, String set, String note) {
		Date today = new Date();
		Entity order = getOrder(today, user);
		if (order == null) {
			Key k = KeyFactory.createKey(User, user);
			order = new Entity(Order, k);
			order.setProperty("date", today);
			order.setProperty("user", user);
		}
		order.setProperty("set", set);
		order.setProperty("note", note);

		Util.persistEntity(order);
	}

	private static Entity getOrder(Date date, String user) {
		Key key = KeyFactory.createKey(User, user);
		Query query = new Query(Order, key).addSort("date",
				Query.SortDirection.DESCENDING);
		List<Entity> orders = Util.queryAsList(query);
		for (Entity e : orders) {
			Object o = e.getProperty("date");
			if (o != null && o instanceof Date) {
				Date orderedDate = (Date) o;
				Calendar cal1 = Calendar.getInstance();
				Calendar cal2 = Calendar.getInstance();
				cal1.setTime(date);
				cal2.setTime(orderedDate);
				if (cal1.get(Calendar.YEAR) == cal2.get(Calendar.YEAR)
						&& cal1.get(Calendar.MONTH) == cal2.get(Calendar.MONTH)
						&& cal1.get(Calendar.DATE) == cal2.get(Calendar.DATE)) {
					return e;
				}
			}
		}
		return null;
	}
	
	public static List<Entity> getOrdersByDate(Date date) {
		Query query = new Query();
		List<Entity> orders = Util.queryAsList(query);
		List<Entity> result = new ArrayList<Entity>();
		for (Entity order : orders) {
			Object orderedDate = order.getProperty("date");
			if (orderedDate != null && orderedDate instanceof Date) {
				Date d = (Date) orderedDate;
				Calendar cal1 = Calendar.getInstance();
				Calendar cal2 = Calendar.getInstance();
				cal1.setTime(date);
				cal2.setTime(d);
				if (cal1.get(Calendar.YEAR) == cal2.get(Calendar.YEAR)
						&& cal1.get(Calendar.MONTH) == cal2.get(Calendar.MONTH)
						&& cal1.get(Calendar.DATE) == cal2.get(Calendar.DATE)) {
					result.add(order);
				}
			}
		}
		return result;
	}
}
