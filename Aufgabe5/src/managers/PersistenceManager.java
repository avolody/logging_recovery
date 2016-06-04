package managers;

import java.util.ArrayList;
import java.util.Map;


public class PersistenceManager implements IPersistentManager{
    
    private static int taId = 0;
    private static Map<Integer, Page> _buffer; // The buffer containing all												// currently used pages
    private static Map<Integer, ArrayList<Integer>> _ongoingTransactions; // A
    
    /**
     * starts a new transaction. The persistence manager creates a unique
     * transaction ID and returns it to the client
     *
     * @return id
     */
    public int beginTransaction() {
        return taId++;
        //TO-DO write BOT-Log
        //TO-DO add to TA Array
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
        
        System.out.println("TA: "+taid+", Page: "+pageid+" - "+data);
    }

    /**
     * commits the transaction specified by the given transaction ID
     *
     * @param taid
     */
    public void commit(int taid) {
        
    }

}
