package org.kumosutra.helicopter;

import org.joda.time.DateTime;
import org.joda.time.Seconds;
import org.kumosutra.helicopter.model.Breakout;
import org.kumosutra.helicopter.model.Interests;
import org.kumosutra.helicopter.model.SecurityInfo;
import org.kumosutra.helicopter.model.StockInfo;
import org.kumosutra.helicopter.repo.Securities;
import org.kumosutra.helicopter.repo.Stocks;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by chazz on 9/22/16.
 */
public class Helicopter {

	public static Interests findSomethingInteresting() {
		return findSomethingInteresting(new DateTime().plusDays(1).minus(Seconds.ONE).toDate());
	}

	public static Interests findSomethingInteresting(SecurityInfo security, Date date) {
		List<Breakout> breakouts = new ArrayList<>();
		// find tenkan sen breakouts
		List<StockInfo> recents = Stocks.findRecent(security.getSymbol(), date, 10);
		if(!recents.isEmpty() && recents.size() == 10) {
			List<StockInfo> pasts = new ArrayList<>();
			pasts.addAll(recents);

			pasts.remove(0);
			recents.remove(9);

			float currentTs = computeLine(recents);
			float previousTs = computeLine(pasts);
			if(recents.get(0).getClose() > currentTs && pasts.get(0).getClose() < previousTs) {
				Breakout breakout = new Breakout();
				breakout.setType(Breakout.Type.TENKAN_SEN);
				breakout.setDate(date);
				breakout.setSymbol(security.getSymbol());

				breakouts.add(breakout);
			}
		}

		Interests interests = new Interests();
		interests.setBreakouts(breakouts);

		return interests;
	}

	public static Interests findSomethingInteresting(Date date) {
		List<Breakout> breakouts = new ArrayList<>();

		List<SecurityInfo> securities = Securities.findActive();
		for (SecurityInfo security : securities) {
			Interests somethingInteresting = findSomethingInteresting(security, date);
			breakouts.addAll(somethingInteresting.getBreakouts());
		}

		Interests interests = new Interests();
		interests.setBreakouts(breakouts);

		return interests;
	}

	private static float computeLine(List<StockInfo> data) {
		Float high = null;
		Float low = null;

		for (StockInfo stockInfo : data) {
			if(high == null || stockInfo.getHigh() > high) {
				high = stockInfo.getHigh();
			}

			if(low == null || stockInfo.getLow() < low) {
				low = stockInfo.getLow();
			}
		}

		return (high + low) /2;
	}

}
