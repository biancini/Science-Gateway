package it.infn.mib.jsaga.adaptor.drmaa.job;

import org.ggf.drmaa.Session;

import fr.in2p3.jsaga.adaptor.job.SubState;
import fr.in2p3.jsaga.adaptor.job.monitor.JobStatus;

public class DRMAAJobStatus extends JobStatus {

	public DRMAAJobStatus(String jobId, Object stateCode, String stateString) {
		super(jobId, stateCode, stateString);
	}

	@Override
	public String getModel() {
		return "DRMAA";
	}

	@Override
	public SubState getSubState() {
		if (m_nativeCause != null) {
            return SubState.FAILED_ERROR;
        }
        
        switch(((Integer)m_nativeStateCode).intValue()) {
	        case Session.UNDETERMINED:
				return null;
			
	        case Session.QUEUED_ACTIVE:
			case Session.SYSTEM_ON_HOLD:
			case Session.USER_ON_HOLD:
			case Session.USER_SYSTEM_ON_HOLD:
				return SubState.RUNNING_QUEUED;
				
			case Session.RUNNING:
				return SubState.RUNNING_ACTIVE;
				
			case Session.SYSTEM_SUSPENDED:
			case Session.USER_SUSPENDED:
			case Session.USER_SYSTEM_SUSPENDED:
				return SubState.SUSPENDED_ACTIVE;
				
			case Session.DONE:
				return SubState.DONE;
				
			case Session.FAILED:
				return SubState.FAILED_ERROR;
	        
	        default:
	            return null;
        }
	}
	
}
