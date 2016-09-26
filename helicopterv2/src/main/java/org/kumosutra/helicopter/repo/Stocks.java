package org.kumosutra.helicopter.repo;

import org.kumosutra.helicopter.model.StockInfo;

import java.util.Date;
import java.util.List;

/**
 * Created by chazz on 9/24/16.
 */
public class Stocks {

	public static void save(StockInfo stockInfo) {
		Repo.datastore.save(stockInfo);
	}

	public static List<StockInfo> findRecent(String symbol, Date date, int limit) {
		return Repo.datastore.find(StockInfo.class)
					   .field("symbol").equal(symbol)
					   .field("date").lessThan(date)
				       .limit(limit).order("-date")
				       .asList();
	}

	public static void save(List<StockInfo> stockInfos) {
		Repo.datastore.save(stockInfos);
	}

	public static void clear() {
		Repo.datastore.delete(Repo.datastore.createQuery(StockInfo.class));
	}
}
