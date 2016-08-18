package org.kumosutra.helicopter.service;

import java.util.List;

import org.kumosutra.helicopter.model.StockFeed;

public class IchiMokuService {
	
	public float calculateSen(List<StockFeed> feeds) {
		
		float highestHigh = 0;
		float lowestLow = 0;
		
		for (StockFeed stockFeed : feeds) {
			float amount = stockFeed.getPrice().getAmount();
			if(amount > highestHigh) {
				highestHigh = amount;
			}
			
			if(lowestLow == 0 || amount < lowestLow) {
				lowestLow = amount;
			}
		}
		
		return (highestHigh - lowestLow) / 2;
	}

}
