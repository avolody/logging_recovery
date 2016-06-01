package managers;

import java.util.logging.Level;
import java.util.logging.Logger;

public class PersistanceManager implements IPersistentManager{

// assumes the current class is called logger

    /**
     * starts a new transaction. The persistence manager creates a unique
     * transaction ID and returns it to the client
     *
     * @return id
     */
    public int beginTransaction() {
        return 0;
    }

    /**
     * writes the given data with the given page ID on behalf of the given
     * transaction to the buffer. If the given page already exists, its content
     * is replaced completely by the given data
     *
     * @param taid
     * @param pageid
     * @param data
     */
    public void write(int taid, int pageid, String data) {
    }

    /**
     * commits the transaction specified by the given transaction ID
     *
     * @param taid
     */
    public void commit(int taid) {
        
    }

}
