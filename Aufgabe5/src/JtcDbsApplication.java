import com.google.inject.Guice;
import com.google.inject.Injector;

public class JtcDbsApplication {

    public static void main(final String[] args) {
        new JtcDbsApplication();
    }

    private JtcDbsApplication() {
        Injector injector = Guice.createInjector(new JtcDbsModule());

        //Recovery
//        RecoveryManager recoveryManager = injector.getInstance(RecoveryManager.class);
//        if (recoveryManager.isRecoveryNeeded()) {
//            recoveryManager.recover();
//        }

        Client amina = injector.getInstance(Amina.class);
        Client jeremy = injector.getInstance(Jeremy.class);
        Client chris = injector.getInstance(Chris.class);
        Client wizkid = injector.getInstance(Wizkid.class);
        Client boss = injector.getInstance(Boss.class);

        amina.start();
        jeremy.start();
        chris.start();
        wizkid.start();
        boss.start();
    }
}
