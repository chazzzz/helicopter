package org.kumosutra.helicopter.model;

import java.util.Date;

public class StockFeed {

	private String name;
	
	private String symbol;
	
	private Price price;
	
	private float percentChange;
	
	private long volume;
	
	private Date date;
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getSymbol() {
		return symbol;
	}

	public void setSymbol(String symbol) {
		this.symbol = symbol;
	}

	public Price getPrice() {
		return price;
	}

	public void setPrice(Price price) {
		this.price = price;
	}

	public float getPercentChange() {
		return percentChange;
	}

	public void setPercentChange(float percentChange) {
		this.percentChange = percentChange;
	}

	public long getVolume() {
		return volume;
	}

	public void setVolume(long volume) {
		this.volume = volume;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}
}
