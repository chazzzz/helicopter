package org.kumosutra.helicopter.repo;

import org.kumosutra.helicopter.model.Breakout;

import java.util.List;

/**
 * Created by chazz on 9/27/16.
 */
public class Breakouts {
	public static void save(List<Breakout> breakouts) {
		Repo.datastore.save(breakouts);
	}

	public static void save(Breakout breakout) {
		Repo.datastore.save(breakout);
	}
}
