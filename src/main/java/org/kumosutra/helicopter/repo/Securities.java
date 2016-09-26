package org.kumosutra.helicopter.repo;

import org.kumosutra.helicopter.model.SecurityInfo;

import java.util.List;

/**
 * Created by chazz on 9/24/16.
 */
public class Securities {

	public static void save(List<SecurityInfo> securities) {
		Repo.datastore.save(securities);
	}

	public static SecurityInfo find(String symbol) {
		return Repo.datastore.find(SecurityInfo.class)
				       .field("symbol").equal(symbol).get();
	}

	public static List<SecurityInfo> findActive() {
		return Repo.datastore.find(SecurityInfo.class)
				       .field("status").equal("O")
				       .asList();
	}

	public static void clear() {
		Repo.datastore.delete(Repo.datastore.createQuery(SecurityInfo.class));
	}
}
