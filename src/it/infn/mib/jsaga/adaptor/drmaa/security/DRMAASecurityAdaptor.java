package it.infn.mib.jsaga.adaptor.drmaa.security;

import java.util.Map;

import org.ogf.saga.error.IncorrectStateException;
import org.ogf.saga.error.NoSuccessException;
import org.ogf.saga.error.TimeoutException;

import fr.in2p3.jsaga.adaptor.base.defaults.Default;
import fr.in2p3.jsaga.adaptor.base.usage.Usage;
import fr.in2p3.jsaga.adaptor.security.SecurityAdaptor;
import fr.in2p3.jsaga.adaptor.security.SecurityCredential;


public class DRMAASecurityAdaptor implements SecurityAdaptor {

	public Default[] getDefaults(Map arg0) throws IncorrectStateException {	
		// TODO Method not yet implemented, INFN
		return null;
	}

	public String getType() {
		// TODO Method not yet implemented, INFN
		return null;
	}

	public Usage getUsage() {
		// TODO Method not yet implemented, INFN
		return null;
	}

	public SecurityCredential createSecurityCredential(int arg0, Map arg1, String arg2)
			throws IncorrectStateException, TimeoutException, NoSuccessException {
		// TODO Method not yet implemented, INFN
		return null;
	}

	public Class getSecurityCredentialClass() {
		// TODO Method not yet implemented, INFN
		return null;
	}


}
