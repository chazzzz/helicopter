package org.kumosutra.service;

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

}
