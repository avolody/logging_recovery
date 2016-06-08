import com.google.inject.AbstractModule;

public class JtcDbsModule extends AbstractModule {
    @Override
    protected void configure() {
    	 bind(IPersistenceManager.class).to(PersistenceManager.class);
    	 bind(ILSNManager.class).to(LSNManager.class);
         bind(ILogManager.class).to(LogManager.class);
    }
}
