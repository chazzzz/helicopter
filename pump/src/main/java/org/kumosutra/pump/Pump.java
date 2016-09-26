package org.kumosutra.pump;

import org.joda.time.LocalDate;
import org.kumosutra.helicopter.Helicopter;
import org.kumosutra.helicopter.comms.Pse;
import org.kumosutra.helicopter.model.Breakout;
import org.kumosutra.helicopter.model.Interests;
import org.kumosutra.helicopter.model.SecurityInfo;
import org.kumosutra.helicopter.model.StockInfo;
import org.kumosutra.helicopter.repo.Breakouts;
import org.kumosutra.helicopter.repo.Stocks;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import java.util.List;

/**
 * Created by chazz on 9/27/16.
 */
public class Pump implements Job {
	@Override
	public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {
		try {
			LocalDate today = new LocalDate();
			List<SecurityInfo> securities = Pse.securities();
			for (SecurityInfo security : securities) {
				if("S".equals(security.getStatus())) {
					continue;
				}

				StockInfo data = Pse.data(security.getCompanyId(), security.getSymbolId());

				LocalDate lastTraded = new LocalDate(data.getDate());
				if(lastTraded.isEqual(today)) {
					Stocks.save(data);

					Interests somethingInteresting = Helicopter.findSomethingInteresting(security, today.toDate());
					List<Breakout> breakouts = somethingInteresting.getBreakouts();
					if(!breakouts.isEmpty()) {
						Breakouts.save(breakouts);
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
