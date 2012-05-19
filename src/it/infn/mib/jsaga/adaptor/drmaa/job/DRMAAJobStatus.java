package it.infn.mib.jsaga.adaptor.drmaa.job;

import fr.in2p3.jsaga.adaptor.job.SubState;
import fr.in2p3.jsaga.adaptor.job.monitor.JobStatus;

public class DRMAAJobStatus extends JobStatus {

	public DRMAAJobStatus(String jobId, Object stateCode, String stateString) {
		super(jobId, stateCode, stateString);
		// TODO Method not yet implemented, INFN
	}

	@Override
	public String getModel() {
		// TODO Method not yet implemented, INFN
		return null;
	}

	@Override
	public SubState getSubState() {
		// TODO Method not yet implemented, INFN
		return null;
	}
	
}
