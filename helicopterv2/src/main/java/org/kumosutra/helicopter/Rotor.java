package org.kumosutra.helicopter;

import org.kumosutra.helicopter.comms.Pse;
import org.kumosutra.helicopter.model.SecurityInfo;
import org.kumosutra.helicopter.model.StockInfo;
import org.kumosutra.helicopter.repo.Securities;
import org.kumosutra.helicopter.repo.Stocks;

import java.util.List;

/**
 * Created by chazz on 10/5/16.
 */
public class Rotor {

	public static void spin(List<StockInfo> stockInfos) throws Exception {
		for (StockInfo cursor : stockInfos) {
			SecurityInfo security = Securities.find(cursor.getSymbol());

			if(Stocks.count(cursor.getSymbol()) > 0) {
				StockInfo stock = Pse.data(security.getCompanyId(), security.getSymbolId());
				long count = Stocks.count(stock.getSymbol(), stock.getDate());
				if(count == 0) {
					Stocks.save(stock);
				}
				continue;
			}

			List<StockInfo> allData = Pse.allData(cursor.getSymbol());
			if(!allData.isEmpty()) {
				Stocks.save(allData);

			}
		}
	}
}

