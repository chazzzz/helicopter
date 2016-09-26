package org.kumosutra.helicopter;

import com.mashape.unirest.http.exceptions.UnirestException;
import org.joda.time.DateTime;
import org.joda.time.Seconds;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.kumosutra.helicopter.comms.Pse;
import org.kumosutra.helicopter.model.Breakout;
import org.kumosutra.helicopter.model.Interests;
import org.kumosutra.helicopter.model.StockInfo;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.zip.DataFormatException;

/**
 * Created by chazz on 9/22/16.
 */
public class HelicopterTest {

	@Before
	public void setup() {
	}

	@Test
	public void testTenkanSenCalculation() {
		Assert.assertEquals(0.895f, computeLine(dummyData()), 0.0001f);
	}

	@Test
	public void testPlcTsBreakout() throws Exception {

		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
		Date date = formatter.parse("2016-09-06");

		DateTime dateTime = new DateTime(date);
		dateTime = dateTime.plusDays(1).minus(Seconds.ONE);

		Interests interests = Helicopter.findSomethingInteresting(dateTime.toDate());

		Assert.assertTrue(!interests.getBreakouts().isEmpty());

		List<Breakout> breakouts = interests.getBreakouts();
		boolean hasPlc = false;
		for (Breakout breakout : breakouts) {
			if(breakout.getSymbol().equals("PLC")) {
				hasPlc = true;
			}
		}

		Assert.assertTrue(hasPlc);
	}

	@Test
	public void quickies() throws DataFormatException, UnirestException, ParseException, IOException {
		Pse.allData("TLJJ");

	}

	private float computeLine(List<StockInfo> data) {
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


	private List<StockInfo> dummyData() {
		List<StockInfo> list = new ArrayList<>();
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

	private StockInfo create(float open, float high, float low, float close) {
		StockInfo data = new StockInfo();
		data.setOpen(open);
		data.setHigh(high);
		data.setLow(low);
		data.setClose(close);


		return data;
	}

}
