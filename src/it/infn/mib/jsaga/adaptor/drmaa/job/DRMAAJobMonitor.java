package it.infn.mib.jsaga.adaptor.drmaa.job;

import fr.in2p3.jsaga.adaptor.job.control.manage.ListableJobAdaptor;
import fr.in2p3.jsaga.adaptor.job.monitor.JobStatus;
import fr.in2p3.jsaga.adaptor.job.monitor.QueryIndividualJob;
import it.infn.mib.jsaga.adaptor.drmaa.DRMAAAdaptorAbstract;

import org.ogf.saga.error.NoSuccessException;
import org.ogf.saga.error.PermissionDeniedException;
import org.ogf.saga.error.TimeoutException;

public class DRMAAJobMonitor extends DRMAAAdaptorAbstract implements QueryIndividualJob, ListableJobAdaptor {

	public String[] list() throws PermissionDeniedException, TimeoutException, NoSuccessException {
		// TODO Method not yet implemented, INFN
		return null;
	}

	public JobStatus getStatus(String arg0) throws TimeoutException,
			NoSuccessException {
		// TODO Method not yet implemented, INFN
		return null;
	}

	

}
