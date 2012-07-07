package it.infn.mib.jsaga.adaptor.drmaa.job;

import fr.in2p3.jsaga.adaptor.job.monitor.JobStatus;
import fr.in2p3.jsaga.adaptor.job.monitor.QueryIndividualJob;
import fr.in2p3.jsaga.adaptor.job.monitor.QueryListJob;
import it.infn.mib.jsaga.adaptor.drmaa.DRMAAAdaptorAbstract;

import java.util.Arrays;

import org.apache.log4j.Logger;
import org.ggf.drmaa.DrmaaException;
import org.ggf.drmaa.Session;
import org.ogf.saga.error.NoSuccessException;
import org.ogf.saga.error.TimeoutException;

public class DRMAAJobMonitor extends DRMAAAdaptorAbstract implements QueryIndividualJob, QueryListJob {

	private Logger logger = Logger.getLogger(DRMAAJobMonitor.class);
	
	public JobStatus getStatus(String nativeJobId) throws TimeoutException, NoSuccessException {
		logger.debug("Trying to retrieve job statos for job with id " + nativeJobId + ".");
		
		try {
			String statusAsString = null;
			int status = getSession().getJobStatus(nativeJobId);
			
			switch(status) {
				case Session.UNDETERMINED:
					statusAsString = "Undetermined state";
					break;
				case Session.QUEUED_ACTIVE:
					statusAsString = "Active in queue";
					break;
				case Session.SYSTEM_ON_HOLD:
					statusAsString = "On hold by system request";
					break;
				case Session.USER_ON_HOLD:
					statusAsString = "On hold by user request";
					break;
				case Session.USER_SYSTEM_ON_HOLD:
					statusAsString = "On hold by system and user request";
					break;
				case Session.RUNNING:
					statusAsString = "Running";
					break;
				case Session.SYSTEM_SUSPENDED:
					statusAsString = "Suspended by system request";
					break;
				case Session.USER_SUSPENDED:
					statusAsString = "Suspendend by user reuqest";
					break;
				case Session.USER_SYSTEM_SUSPENDED:
					statusAsString = "Suspendend by system request";
					break;
				case Session.DONE:
					statusAsString = "Done";
					break;
				case Session.FAILED:
					statusAsString = "Failed";
					break;
			}
			
	        return new DRMAAJobStatus(nativeJobId, new Integer(status), statusAsString);
		} catch (DrmaaException e) {
			logger.error("Error while retrieving job status for job " + nativeJobId  + ".", e);
			throw new NoSuccessException(e);
		}
	}

	public JobStatus[] getStatusList(String[] nativeJobIds) throws TimeoutException, NoSuccessException {
		logger.debug("Trying to retrieve job statuses for a lost of jobs: " + Arrays.toString(nativeJobIds) + ".");
		
		JobStatus[] jobStatuses = new JobStatus[nativeJobIds.length];
		for (int i = 0; i < nativeJobIds.length; i++) {
			jobStatuses[i] = this.getStatus(nativeJobIds[i]);
		}
		return jobStatuses;
	}

}
