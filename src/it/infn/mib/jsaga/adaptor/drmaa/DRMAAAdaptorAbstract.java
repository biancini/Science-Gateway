package it.infn.mib.jsaga.adaptor.drmaa;

import java.io.File;
import java.lang.reflect.Field;
import java.util.Map;
import java.util.Properties;

import org.apache.log4j.Logger;
import org.ggf.drmaa.AlreadyActiveSessionException;
import org.ggf.drmaa.AuthorizationException;
import org.ggf.drmaa.DefaultContactStringException;
import org.ggf.drmaa.DrmaaException;
import org.ggf.drmaa.NoDefaultContactStringSelectedException;
import org.ggf.drmaa.Session;
import org.ggf.drmaa.SessionFactory;
import org.ogf.saga.error.AuthenticationFailedException;
import org.ogf.saga.error.AuthorizationFailedException;
import org.ogf.saga.error.BadParameterException;
import org.ogf.saga.error.IncorrectStateException;
import org.ogf.saga.error.NoSuccessException;
import org.ogf.saga.error.NotImplementedException;
import org.ogf.saga.error.TimeoutException;

import fr.in2p3.jsaga.adaptor.ClientAdaptor;
import fr.in2p3.jsaga.adaptor.base.defaults.Default;
import fr.in2p3.jsaga.adaptor.base.usage.UAnd;
import fr.in2p3.jsaga.adaptor.base.usage.UOptional;
import fr.in2p3.jsaga.adaptor.base.usage.Usage;
import fr.in2p3.jsaga.adaptor.security.SecurityCredential;
import fr.in2p3.jsaga.adaptor.security.impl.SSHSecurityCredential;
import fr.in2p3.jsaga.adaptor.security.impl.UserPassSecurityCredential;
import fr.in2p3.jsaga.adaptor.security.impl.UserPassStoreSecurityCredential;

public abstract class DRMAAAdaptorAbstract implements ClientAdaptor {
	private Logger logger = Logger.getLogger(DRMAAAdaptorAbstract.class);
	
	protected static final String COMPRESSION_LEVEL = "CompressionLevel";
	protected static final String KNOWN_HOSTS = "KnownHosts";
	protected SecurityCredential credential = null;
	protected Session session = null;
	
	public DRMAAAdaptorAbstract() {
		try {
			Properties properties = new Properties();
			properties.load(getClass().getResourceAsStream("drmaa.properties"));
			System.setProperty("java.library.path", properties.getProperty("drmaa_library_path"));
			 
			Field fieldSysPath = ClassLoader.class.getDeclaredField("sys_paths");
			fieldSysPath.setAccessible(true);
			fieldSysPath.set(null, null);
		}
		catch (Exception e) {
			logger.error("The adaptor was not able to link Gridway libraries. Probable misfunctions during execution.", e);
		}
	}

	@SuppressWarnings("rawtypes")
	public Class[] getSupportedSecurityCredentialClasses() {
		// TODO: review implementation
		return new Class[] { UserPassSecurityCredential.class, UserPassStoreSecurityCredential.class, SSHSecurityCredential.class };
	}

	public void setSecurityCredential(SecurityCredential credential) {
		this.credential = credential;
	}

	public int getDefaultPort() {
		return 6725;
	}

	public String getType() {
		return "DRMAA";
	}

	public Usage getUsage() {
		// TODO: review implementation
		return new UAnd(new Usage[] { new UOptional(KNOWN_HOSTS), new UOptional(COMPRESSION_LEVEL) });
	}

	@SuppressWarnings("rawtypes")
	public Default[] getDefaults(Map map) throws IncorrectStateException {
		// TODO: review implementation
		return new Default[] {
				new Default(KNOWN_HOSTS, new File[] {
						new File(System.getProperty("user.home") + "/.ssh/known_hosts") }) };
	}

	@SuppressWarnings("rawtypes")
	public void connect(String userInfo, String host, int port, String basePath, Map attributes)
			throws NotImplementedException, AuthenticationFailedException, AuthorizationFailedException,
			BadParameterException, TimeoutException, NoSuccessException {
		
		logger.debug("Initializing a new session.");
		
		try {
			SessionFactory factory = SessionFactory.getFactory();
			this.session = factory.getSession();
			session.init(null);
		}
		catch (AlreadyActiveSessionException e) {
			logger.debug("Trying to activated an already active session.", e);
		}
		catch (AuthorizationException e) {
			logger.debug("Authorization exception during connect method.", e);
			throw new AuthorizationFailedException(e);
		}
		catch(DefaultContactStringException e) {
			logger.debug("Parameters exception during connect method.", e);
			throw new BadParameterException(e);
		}
		catch(NoDefaultContactStringSelectedException e) {
			logger.debug("Parameters exception during connect method.", e);
			throw new BadParameterException(e);
		}
		catch (Exception e) {
			logger.debug("Exception during connect method.", e);
			throw new NoSuccessException(e);
		}
	}

	public void disconnect() throws NoSuccessException {
		logger.debug("Disconnecting session.");
		
		try {
			if (this.session != null) this.session.exit();
		}
		catch (DrmaaException e) {
			logger.debug("DrmaaException during disconnect method.", e);
			throw new NoSuccessException(e);
		}
	}
	
	protected Session getSession() {
		if (session == null) logger.error("Trying to retrieve session, which is null.");
		return session;
	}


}
