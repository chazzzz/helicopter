package org.kumosutra.helicopter.repo;

import com.mongodb.MongoClient;
import org.kumosutra.helicopter.model.Breakout;
import org.kumosutra.helicopter.model.StockInfo;
import org.mongodb.morphia.Datastore;
import org.mongodb.morphia.Morphia;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

/**
 * Created by chazz on 9/22/16.
 */
public class Repo {

	public static Datastore datastore;

	public static Datastore init() {
		Morphia morphia = new Morphia();
		morphia.map(StockInfo.class, Breakout.class);

		final MongoClient mongoClient = new MongoClient();
		datastore = morphia.createDatastore(mongoClient, "stocks");

		return datastore;
	}


	public static List<StockInfo> findRecentFromDate(String symbol, Date date, int days) {
		return dummyData();
	}

	public static void saveBreakout(Breakout breakout) {
		datastore.save(breakout);
	}

	private static List<StockInfo> dummyData() {
		List<StockInfo> list = new ArrayList<StockInfo>();
		list.addAll(Arrays.asList(
             create(0.95f, 0.95f, 0.89f, 0.89f),
             create(0.91f, 0.92f, 0.88f, 0.88f),
             create(0.89f, 0.9f, 0.85f, 0.87f),
             create(0.88f, 0.95f, 0.86f, 0.94f),
             create(0.94f, 0.95f, 0.88f, 0.88f),
             create(0.88f, 0.9f, 0.84f, 0.87f),
             create(0.87f, 0.9f, 0.86f, 0.89f),
             create(0.89f, 0.89f, 0.86f, 0.89f),
             create(0.89f, 0.95f, 0.89f, 0.94f)
		));

		return list;
	}

	private static StockInfo create(float open, float high, float low, float close) {
		StockInfo data = new StockInfo();
		data.setOpen(open);
		data.setHigh(high);
		data.setLow(low);
		data.setClose(close);


		return data;
	}
}
