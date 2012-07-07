package it.infn.mib.jsaga.adaptor.tests;                                                                     
                                             
import org.ogf.saga.context.Context;
import org.ogf.saga.context.ContextFactory;
import org.ogf.saga.error.SagaException;
import org.ogf.saga.job.Job;
import org.ogf.saga.job.JobDescription;
import org.ogf.saga.job.JobFactory;
import org.ogf.saga.job.JobService;
import org.ogf.saga.session.Session;
import org.ogf.saga.session.SessionFactory;
import org.ogf.saga.task.State;

import fr.in2p3.jsaga.impl.job.instance.JobImpl;

public class RunTest {
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		System.setProperty("JSAGA_HOME","/opt/jsaga");
		System.setProperty("saga.factory", "fr.in2p3.jsaga.impl.SagaFactoryImpl");
		
		//modifiy this section according to the A&A schema of your middleware
		//below the VOMS sample is showed
		Session session = null;
		Context context = null;
		try {
			session = SessionFactory.createSession(false);
			context = ContextFactory.createContext("VOMS");
			context.setAttribute(Context.USERPROXY,"/tmp/x509up_100"); //put here your proxy path

			session.addContext(context);

			System.out.println("DN="+context.getAttribute(Context.USERID));
		} catch (Exception e) {
			System.out.println("Error in creating session: " + e.toString());
			System.out.println("Cause: " + e.getCause());
		}
		
		JobService service = null;
		try {
			service = JobFactory.createJobService(session, null);
		} catch (Exception e) {
			System.out.println("Error in creating jobService: " + e.toString());
			System.out.println("Cause:" + e.getCause());
		}
		
		Job job = null;
		
		try {
			System.out.println("Creating job description...");
            JobDescription desc = JobFactory.createJobDescription();
			
			desc.setAttribute(JobDescription.EXECUTABLE, "./mybashscript.sh");
			
			desc.setAttribute(JobDescription.OUTPUT, "stdout");
			desc.setAttribute(JobDescription.ERROR, "stderr");
			
			desc.setVectorAttribute(JobDescription.FILETRANSFER, ("~/stdout<stdout,~/stderr<stderr").split(","));
			job = service.createJob(desc);
		
            System.out.println("Job submitting...");	
			job.run();
		} catch (Exception e) {
			System.out.println("Error in creating jobDescription: " + e.toString());
			System.out.println("Cause: " + e.getCause());
		}

		boolean jobIsDone = false;
		while(!jobIsDone) {
			// display final state
			State state = null;
			try {
				state = job.getState();
				System.out.println("state = " + state.name());
			}
			catch (Exception e) {
				System.out.println("Error in get job state: " + e.toString());
				System.out.println("Cause: " + e.getCause());
			}
			
			if (State.CANCELED.compareTo(state) == 0) {
				System.out.println("Job canceled.");
			}
			else {
				if (State.DONE.compareTo(state) == 0) {
					System.out.println("Job done.");
					jobIsDone = true;
					// execute post-staging and cleanup
					try {
						((JobImpl)job).postStagingAndCleanup();
					}
					catch (Exception e) {
						System.out.println("Error in get job post staging and cleanup: " + e.toString());
						System.out.println("Cause: " + e.getCause());
					}
					System.out.println("Job output have been retrieved successfully (if it exists)");
					break;
				}
				else if (State.FAILED.compareTo(state) == 0) {
					try {
						String exitCode = job.getAttribute(Job.EXITCODE);
						System.out.println("Job failed with exit code: " + exitCode);
					}
					catch(SagaException e) {
						System.out.println("Job failed.");
					}
				}
				else {
					System.out.println("Unexpected state: " + state);
				}
			}
			
			try {
				Thread.sleep(10000);
			}
			catch (InterruptedException e) {
				e.printStackTrace();
			}
			
		}
	}
}
