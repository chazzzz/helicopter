package org.kumosutra.pump;

import org.quartz.CronScheduleBuilder;
import org.quartz.JobBuilder;
import org.quartz.JobDetail;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.Trigger;
import org.quartz.TriggerBuilder;
import org.quartz.impl.StdSchedulerFactory;

/**
 * Created by chazz on 9/27/16.
 */
public class Main {

	public static void main(String[] args){
		try {
			// Grab the Scheduler instance from the Factory
			Scheduler scheduler = StdSchedulerFactory.getDefaultScheduler();


			// define the job and tie it to our HelloJob class
			JobDetail job = JobBuilder.newJob(Pump.class)
					                .withIdentity("pump", "kumosutra")
					                .build();

			Trigger trigger = TriggerBuilder
					                  .newTrigger()
					                  .withIdentity("pumpTrigger", "kumosutra")
					                  .withSchedule(
			                               CronScheduleBuilder.cronSchedule("0 30 15 1/1 * ? *"))
					                  .build();



			// and start it off
			scheduler.start();
			scheduler.scheduleJob(job, trigger);
		} catch (SchedulerException se) {
			se.printStackTrace();
		}
	}
}
