package clients;

import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;
import managers.PersistenceManager;
import managers.Transaction;

public class Client implements Runnable {

    private final String clientName;
    private final PersistenceManager persistenceManager;
    private final int[] accessPages;
    private static int pagesCounter = 0;

    Client(String name, PersistenceManager mgmt) {
        clientName = name;
        persistenceManager = mgmt;
        this.accessPages = new int[10];
        int i;
        for (i = 0; i < 10; i++) {
            accessPages[i] = pagesCounter + i;
        }
        pagesCounter += 10;
    }

    /**
     * Creates a new transaction.
     *
     * @return instance
     */
    protected Transaction createTA() {
        return new Transaction(persistenceManager);
    }

    /**
     * @return a random page ID where this client has write access to.
     */
    protected int randomPage() {
        int idx = new Random().nextInt(10);
        return accessPages[idx];
    }

    @Override
    /**
     * Implemented Method of Runnable Create TA, write, sleep,..., commit
     */
    public void run() {
        try {
            Transaction tx = this.createTA();
            
            tx.write(randomPage(), "My name is " + clientName);
            Thread.sleep((long) (Math.random() * 1000));
            tx.write(randomPage(), clientName + " writes from page " + accessPages[0]);
            Thread.sleep((long) (Math.random() * 1000));
            tx.write(randomPage(), clientName + " writes up to page " + accessPages[accessPages.length - 1]);
            Thread.sleep((long) (Math.random() * 1000));

            tx.commit();
        } catch (InterruptedException ex) {
            Logger.getLogger(Client.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
