package org.kumosutra.helicopter.service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import org.joda.time.DateTime;
import org.joda.time.DateTimeConstants;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.json.JSONArray;
import org.json.JSONException;
import org.kumosutra.helicopter.conf.Endpoints;
import org.kumosutra.helicopter.model.StockFeed;
import org.kumosutra.helicopter.util.Logger;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;

public class StockFeedService {
	
	/**
	 * Fetches the latest stock feed 
	 * @return
	 */
	public List<StockFeed> pull() {
		
		try {
			Logger.log("Fetching " + Endpoints.LATEST_FEEDS);
			JSONArray body = Unirest.get(Endpoints.LATEST_FEEDS).asJson().getBody().getObject().getJSONArray("stock");
			
			return toFeeds(body.toString());
		} catch (UnirestException e) {
			e.printStackTrace();
		}
		
		return Collections.emptyList();
	}

	public List<StockFeed> pull(String symbol, Date from, Date to) {
		List<StockFeed> feeds = new ArrayList<StockFeed>();
				
		DateTimeFormatter formatter = DateTimeFormat.forPattern("yyyy-MM-dd");
		DateTime targetDate = new DateTime(to);
		try {
			for (DateTime date = new DateTime(from); !date.isAfter(targetDate); date = date.plusDays(1)) {
				if(date.getDayOfWeek() == DateTimeConstants.SUNDAY || date.getDayOfWeek() == DateTimeConstants.SATURDAY) {
					continue;
				}
				
				if(date.withTimeAtStartOfDay().equals(new DateTime().withTimeAtStartOfDay()) 
						&& new DateTime().isBefore(new DateTime().withTime(15, 30, 1, 0))) {
					break;
				}
				
				String url = String.format(Endpoints.BY_SYMBOL_AND_DATE, symbol.toLowerCase(), formatter.print(date));
				
				Logger.log("Fetching " + url);
				String body = Unirest.get(url).asJson().getBody().getObject().getJSONArray("stock").toString();
				
				feeds.addAll(toFeeds(body));
			}
			
			return feeds;
		} catch (JSONException e) {
			e.printStackTrace();
		} catch (UnirestException e) {
			e.printStackTrace();
		}
		
		return Collections.emptyList();
	}
	
	private List<StockFeed> toFeeds(String stocksJson) {
		return new Gson().fromJson(stocksJson, new TypeToken<ArrayList<StockFeed>>(){}.getType());
	}

}
