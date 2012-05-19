package it.infn.mib.jsaga.adaptor.example;

import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import org.ggf.drmaa.DrmaaException;
import org.ggf.drmaa.JobTemplate;
import org.ggf.drmaa.Session;
import org.ggf.drmaa.SessionFactory;


public class APITest {
	
	public static void printLog(String message) { printLog(message, false); }
	public static void printLog(String message, boolean isError) { ((isError) ? System.err : System.out).println(message); }

	public static void main(String[] args) throws Exception {
		printLog("Class used to test the interaction to DRMAA via Java API.");
		
		SessionFactory factory = SessionFactory.getFactory();
		Session session = factory.getSession();
		
		try {
			session.init("");
			
			JobTemplate gridwayJobTemplate = session.createJobTemplate();
			gridwayJobTemplate.setRemoteCommand("sleeper.sh");
			List<String> jobArgs = new ArrayList<String>();
			jobArgs.add("5");
			gridwayJobTemplate.setArgs(jobArgs);
			
			Properties job_env = new Properties();
			job_env.setProperty("ENV_VARIABLE", "Environament variable value");
			gridwayJobTemplate.setJobEnvironment(job_env);
			
			String id = session.runJob(gridwayJobTemplate);
			printLog("Your job has been submitted with id " + id);
			session.deleteJobTemplate(gridwayJobTemplate);
			
			session.exit();
		} catch (DrmaaException e) {
			printLog("Error: " + e.getMessage(), true);
		}
		
		printLog("End of activities.", true);
	}
}
