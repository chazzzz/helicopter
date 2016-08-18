package org.kumosutra.helicopter.service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.json.JSONArray;
import org.kumosutra.helicopter.conf.Endpoints;
import org.kumosutra.helicopter.model.StockFeed;

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
			JSONArray body = Unirest.get(Endpoints.BASE_URL + ".json").asJson().getBody().getObject().getJSONArray("stock");
			return new Gson().fromJson(body.toString(), new TypeToken<ArrayList<StockFeed>>(){}.getType());
		} catch (UnirestException e) {
			e.printStackTrace();
		}
		
		return Collections.emptyList();
	}

}
