package it.infn.mib.jsaga.adaptor.drmaa.job;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import fr.in2p3.jsaga.adaptor.job.BadResource;
import fr.in2p3.jsaga.adaptor.job.control.JobControlAdaptor;
import fr.in2p3.jsaga.adaptor.job.control.description.JobDescriptionTranslator;
import fr.in2p3.jsaga.adaptor.job.control.description.JobDescriptionTranslatorXSLT;
import fr.in2p3.jsaga.adaptor.job.monitor.JobMonitorAdaptor;
import it.infn.mib.jsaga.adaptor.drmaa.DRMAAAdaptorAbstract;

import org.apache.log4j.Logger;
import org.ggf.drmaa.AuthorizationException;
import org.ggf.drmaa.DrmaaException;
import org.ggf.drmaa.JobTemplate;
import org.ggf.drmaa.Session;
import org.globus.rsl.ParseException;
import org.globus.rsl.RSLParser;
import org.globus.rsl.RslNode;

import org.ogf.saga.error.NoSuccessException;
import org.ogf.saga.error.PermissionDeniedException;
import org.ogf.saga.error.TimeoutException;

public class DRMAAJobControl extends DRMAAAdaptorAbstract implements JobControlAdaptor {
	private Logger logger = Logger.getLogger(DRMAAJobControl.class);
	
	public void cancel(String nativeJobId) throws PermissionDeniedException, TimeoutException, NoSuccessException {
		logger.debug("Trying to cancel job with id " + nativeJobId + ".");
		try {			
			getSession().control(nativeJobId, Session.TERMINATE);
		} catch (AuthorizationException e) {
			logger.error("Authorization error while trying to terminate job " + nativeJobId  + ".", e);
			throw new PermissionDeniedException(e);
		} catch (DrmaaException e) {
			logger.error("Error while trying to terminate job " + nativeJobId  + ".", e);
			throw new NoSuccessException(e);
		}
	}

	public JobMonitorAdaptor getDefaultJobMonitor() {
		return new DRMAAJobMonitor();
	}

	public JobDescriptionTranslator getJobDescriptionTranslator() throws NoSuccessException {
		return new JobDescriptionTranslatorXSLT("xsl/job/rsl-1.0.xsl");
	}

	public String submit(String jobDesc, boolean checkMatch, String uniqId)  throws PermissionDeniedException, TimeoutException, NoSuccessException, BadResource {
		logger.debug("Trying to submit job with: jobDesc=" + jobDesc + " checkMatch=" + (checkMatch ? "true" : "false") + " uniqId=" + uniqId + ".");
		if (checkMatch) logger.info("CheckMatch not supported");
		
		try {
			JobTemplate jt = getSession().createJobTemplate();
			
			// Parsing jobDesc variable, input to this method (RSL format).
			RslNode rslTree;
	        try {
	        	rslTree = RSLParser.parse(jobDesc);
	        } catch (ParseException e) {
	        	logger.error("Error while parsing rsl job description: " + jobDesc  + ".", e);
	            throw new NoSuccessException(e);
	        }
			
	        // Setting current working directory: defaults user.dir
	        String currentWorkingDirectory = System.getProperty("user.dir");
	        if (!rslTree.getParam("directory").getValues().isEmpty()) {
	        	currentWorkingDirectory = rslTree.getParam("directory").getValues().get(0).toString();
	        	logger.debug("Got current working directory from jobDesc: " + currentWorkingDirectory); 
	        }
			jt.setWorkingDirectory(currentWorkingDirectory);
			
			// Setting unique job id as specified as input to this method
			jt.setJobName(uniqId);
			
			// Setting the executable name
			String executable = null;
			if (rslTree.getParam("executable").getValues().isEmpty()) {
				logger.error("Executable provided in jobDescnot valid: " + jobDesc);
				throw new BadResource("Executable provided in jobDescnot valid.");
			}
			else {
	        	currentWorkingDirectory = rslTree.getParam("executable").getValues().get(0).toString();
	        	logger.debug("Got executable from jobDesc: " + executable); 
	        }
			jt.setRemoteCommand(executable);
			
			// Setting the job arguments
			List<String> jobArgs = new ArrayList<String>();
			for (Object argument : rslTree.getParam("arguments").getValues()) {
				jobArgs.add(argument.toString());
	        	logger.debug("Got argument from jobDesc: " + argument.toString()); 
	        }
			jt.setArgs(jobArgs);
			
			// Setting the standard output file
			String stdoutFileName = jt.getJobName() + ".stdout";
			if (!rslTree.getParam("stdout").getValues().isEmpty()) {
				stdoutFileName = rslTree.getParam("stdout").getValues().get(0).toString();
	        	logger.debug("Got stdout filename from jobDesc: " + stdoutFileName); 
	        }
			jt.setOutputPath(stdoutFileName);
			
			// Setting the standard error file
			String stderrFileName = jt.getJobName() + ".stdout";
			if (!rslTree.getParam("stderr").getValues().isEmpty()) {
				stderrFileName = rslTree.getParam("stderr").getValues().get(0).toString();
	        	logger.debug("Got stderr filename from jobDesc: " + stderrFileName); 
	        }
			jt.setErrorPath(stderrFileName);
			
			// Setting the standard input file
			if (!rslTree.getParam("stdin").getValues().isEmpty()) {
				String stdinFileName = rslTree.getParam("stdin").getValues().get(0).toString();
	        	logger.debug("Got stdin filename from jobDesc: " + stdinFileName);
	        	if (stdinFileName != null) jt.setInputPath(stdinFileName);
	        }
			
			// Setting job environment
			HashMap<String, String> jobEnv = new HashMap<String, String>();
			for (Object argument : rslTree.getParam("environments").getValues()) {
				// TODO: verify which object is returned in the argument variable
				jobEnv.put(argument.getClass().toString(), argument.toString());
	        	logger.debug("Got environment variable from jobDesc: " + argument.getClass().toString() + "=" + argument.toString()); 
	        }
			jt.setJobEnvironment(jobEnv);
			
			// Running job
			String id = session.runJob(jt);
			logger.debug("Job " + uniqId + " successfully submitted with ID: " + id);
			return id;
		} catch (IllegalArgumentException e) {
			logger.error("Illegal argument error while submitting job.", e);
			throw new BadResource(e);
		} catch (AuthorizationException e) {
			logger.error("Authorization error while submitting job.", e);
			throw new PermissionDeniedException(e);
		}
		catch (DrmaaException e) {
			logger.error("Error while submitting job.", e);
			throw new NoSuccessException(e);
		}
	}
	

}
