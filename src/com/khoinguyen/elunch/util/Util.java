package com.khoinguyen.elunch.util;

import java.util.List;

import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.EntityNotFoundException;
import com.google.appengine.api.datastore.FetchOptions;
import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.PreparedQuery;
import com.google.appengine.api.datastore.Query;

public class Util {
	private static DatastoreService datastore = DatastoreServiceFactory
			.getDatastoreService();

	public static Entity findEntity(Key key) {
		try {
			return datastore.get(key);
		} catch (EntityNotFoundException e) {
			return null;
		}
	}

	public static void persistEntity(Entity entity) {
		datastore.put(entity);
	}

	public static void deleteEntity(Key key) {
		datastore.delete(key);
	}

	public static Iterable<Entity> listEntities(String kind, String searchBy,
			String searchFor) {
		Query q = new Query(kind);
		PreparedQuery pq = datastore.prepare(q);
		return pq.asIterable();
	}
	
	public static List<Entity> queryAsList(Query query) {
		PreparedQuery pq = datastore.prepare(query);
		return pq.asList(FetchOptions.Builder.withDefaults());
	}
}
