package org.kumosutra.helicopter.model;

import java.util.Date;
import java.util.List;

/**
 * Created by chazz on 9/24/16.
 */
public class Interests {

	private Date date;

	private List<Breakout> breakouts;

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public List<Breakout> getBreakouts() {
		return breakouts;
	}

	public void setBreakouts(List<Breakout> breakouts) {
		this.breakouts = breakouts;
	}
}
