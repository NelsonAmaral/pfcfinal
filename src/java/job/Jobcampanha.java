/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package job;

import dao.CampanhaDAO;
import org.quartz.SchedulerException;
import org.quartz.Job;
import org.quartz.JobBuilder;
import org.quartz.JobDetail;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.Scheduler;
import org.quartz.SimpleScheduleBuilder;
import org.quartz.Trigger;
import org.quartz.TriggerBuilder;
import org.quartz.impl.StdSchedulerFactory;
import util.Email;


/**
 *
 * @author nelson_amaral
 */
public class Jobcampanha implements Job{
    
    @Override
    public void execute(JobExecutionContext jec) throws JobExecutionException {
        Email email = new Email();
        
        email.finalizarcampanha();
    }
    
    public void startJobCampanha() throws SchedulerException{
        JobDetail j = JobBuilder.newJob(Jobcampanha.class).build();
        
        Trigger t = TriggerBuilder.newTrigger().withIdentity("CroneTrigger")
            .withSchedule(SimpleScheduleBuilder.simpleSchedule().withIntervalInHours(24) /*Time do Job*/
                .repeatForever()).build();
        
        Scheduler s=StdSchedulerFactory.getDefaultScheduler();
        
        s.start();
        s.scheduleJob(j,t);
    }
}
