/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package job;

import dao.CampanhaDAO;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import org.quartz.Job;
import org.quartz.JobBuilder;
import org.quartz.JobDetail;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.SimpleScheduleBuilder;
import org.quartz.Trigger;
import org.quartz.TriggerBuilder;
import org.quartz.impl.StdSchedulerFactory;
import util.ConectaBanco;

/**
 *
 * @author nelson_amaral
 */
public class JobIdade implements Job{
    @Override
    public void execute(JobExecutionContext jec) throws JobExecutionException {
        CampanhaDAO campanhaDAO = new CampanhaDAO();
        campanhaDAO.jobUsuarioIdade();
        System.out.println("JobIdade is run");
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
    
    
    /** DAO USUARIO Adiciona **/
    private final String ATUALIZA_IDADE = "UPDATE usuario SET usuario_idade= ((current_date - usuario_nascimento )/360)WHERE usuario_idade != ((current_date - usuario_nascimento )/360);";
    
    public void jobUsuarioIdade() {
        Connection conexao = null;
        PreparedStatement pstmt = null;
       
        
        try {
            conexao = ConectaBanco.getConexao();
            pstmt = conexao.prepareStatement(ATUALIZA_IDADE);
                
            pstmt.execute();
        } catch (SQLException erro) {
            System.out.println("Erro SQL(CampanhaDAO)" + erro);
            throw new RuntimeException(erro);
        } finally {
            if (conexao != null) {
                try {
                    conexao.close();
                } catch (SQLException erroSQL) {
                    System.out.println("Erro SQL(CampanhaDAO)" + erroSQL);
                    throw new RuntimeException(erroSQL);
                }
            }
        }
    }
}
