package managers;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class PersistenceManager implements IPersistentManager {

    private static int taId;
    // pageId, page
    private static Map<Integer, Page> _buffer;
    // taId, pageIds
    private static Map<Integer, ArrayList<Integer>> _ongoingTransactions;

    public PersistenceManager() {
        _ongoingTransactions = new ConcurrentHashMap<>();
        _buffer = new ConcurrentHashMap<>();
        taId = 0;
    }

    /**
     * starts a new transaction. The persistence manager creates a unique
     * transaction ID and returns it to the client
     *
     * @return id
     */
    public int beginTransaction() {
        try {
            int nextTaId = this.taId++;
            _ongoingTransactions.put(nextTaId, new ArrayList<Integer>());
            //TO-DO write BOT-Log
            //TO-DO add to TA Array
            return nextTaId;
        } catch (Exception e) {
            System.err.println(e.getMessage());
            return 1000;
        }
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

        int lsn = 0;//TO-DO
        Page page = new Page(pageid, lsn, data);

        System.out.println("TA: " + taid + ", Page: " + pageid + " - " + data);
        
        if (!_ongoingTransactions.get(taid).contains(pageid)) {
            _ongoingTransactions.get(taid).add(pageid);
        }
        
        _buffer.put(page.getPageId(), page);
        if (bufferFull()) {
            pushCommittedTAs();
        }
    }

    /**
     * commits the transaction specified by the given transaction ID
     *
     * @param taid
     */
    public void commit(int taid) {
        System.out.println("Committed TA: " + taid);
        _ongoingTransactions.remove(taid);
        if (bufferFull()) {
            pushCommittedTAs();
        }
    }

    private boolean bufferFull() {
        return (_buffer.size() == 5);
    }

    private void pushCommittedTAs() {
        //Iterate through Buffer PageIds
        for (final int pageId : _buffer.keySet()) {
            boolean pageCommitted = true;
            //Check of the PageId is in one of the ongoing TAs
            for (final ArrayList<Integer> pageIdArray : _ongoingTransactions.values()) {
                if (pageIdArray.contains(pageId)) {
                    //if found - it means not committed
                    pageCommitted = false;
                    break;
                }
            }
            if (pageCommitted) {
                writePageOnPersistenceStorage(pageId, _buffer.get(pageId).getLSN(), _buffer.get(pageId).getData());
            }
        }
        //If the buffer is still full because there were 
        //no committed TAs, clear it to avoid steal
        if (_buffer.size() == 5) {
            _buffer.clear();
        }
        System.out.println("Buffer emptied!");
    }

    /**
     *
     * @param pageId
     * @param lsn
     * @param data
     * @return Whether the page was written successfully or not
     */
    private boolean writePageOnPersistenceStorage(int pageId, int lsn, String data) {

        File f = new File(pageId + ".txt");

        try (FileWriter fw = new FileWriter(f)) {
            fw.write(pageId + "," + lsn
                    + "," + data);
            fw.flush();
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }

        return true;
    }
}
