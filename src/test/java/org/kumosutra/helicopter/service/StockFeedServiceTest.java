package org.kumosutra.helicopter.service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;
import org.kumosutra.helicopter.model.StockFeed;
import org.kumosutra.helicopter.service.StockFeedService;

public class StockFeedServiceTest {
	
	@Test
	public void testPull() {
		List<StockFeed> feeds = new StockFeedService().pull();
		
		Assert.assertFalse(feeds.isEmpty());
	}
	
	@Test
	public void testPullSpecificSymbolByDateRange() throws ParseException {
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
		Date from = formatter.parse("2016-08-4");
		Date to = formatter.parse("2016-08-9");
		
		List<StockFeed> feeds = new StockFeedService().pull("dfnn", from, to);
		
		Assert.assertEquals(4, feeds.size());
	}

}
