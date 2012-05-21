package it.infn.mib.jsaga.adaptor.drmaa.security;

import java.util.Map;

import org.ogf.saga.error.IncorrectStateException;
import org.ogf.saga.error.NoSuccessException;

import fr.in2p3.jsaga.adaptor.base.defaults.Default;
import fr.in2p3.jsaga.adaptor.base.usage.Usage;
import fr.in2p3.jsaga.adaptor.security.SecurityAdaptor;
import fr.in2p3.jsaga.adaptor.security.SecurityCredential;


public class DRMAASecurityAdaptor implements SecurityAdaptor {

	@SuppressWarnings("rawtypes")
	public Default[] getDefaults(Map map) throws IncorrectStateException {	
		// TODO Method not yet implemented
		return null;
	}

	public String getType() {
		// TODO Method not yet implemented
		return null;
	}

	public Usage getUsage() {
		// TODO Method not yet implemented
		return null;
	}

	@SuppressWarnings("rawtypes")
	public SecurityCredential createSecurityCredential(int usage, Map attributes, String contextId) throws IncorrectStateException, NoSuccessException {
		// TODO Method not yet implemented
		return null;
	}

	@SuppressWarnings("rawtypes")
	public Class getSecurityCredentialClass() {
		// TODO Method not yet implemented
		return null;
	}


}
