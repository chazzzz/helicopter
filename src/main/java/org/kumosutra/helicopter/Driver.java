package org.kumosutra.helicopter;

import com.mashape.unirest.http.Unirest;
import org.kumosutra.helicopter.comms.Pse;
import org.kumosutra.helicopter.model.SecurityInfo;
import org.kumosutra.helicopter.model.StockInfo;
import org.kumosutra.helicopter.repo.Repo;
import org.kumosutra.helicopter.repo.Securities;
import org.kumosutra.helicopter.repo.Stocks;

import java.util.List;

/**
 * Created by chazz on 9/22/16.
 */
public class Driver {

	public static void main(String[] args) throws Exception {

		Unirest.setTimeouts(10000, 10000);

		Repo.init();

		Securities.clear();
		Stocks.clear();

		System.out.println("Getting all securities...");
		Securities.save(Pse.securities());

		System.out.println("Starting to fetch 2yr data of every active stocks...");
		List<SecurityInfo> securities = Securities.findActive();
		System.out.println("All active securities: " + securities.size());
		int count = 1;
		for (SecurityInfo security : securities) {

			System.out.println(count++ + " :Getting data for " + security.getSymbol());
			List<StockInfo> stockInfos = Pse.allData(security.getSymbol());
			Stocks.save(stockInfos);
		}
	}


}
