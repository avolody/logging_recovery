package managers;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class PersistenceManager implements IPersistentManager {

    private static int taId;
    private static Map<Integer, Page> _buffer;
    // taId, pageId
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
        _ongoingTransactions.get(taid).add(pageid);
        
        _buffer.put(page.getPageId(), page);
        if (bufferFull()) {
            pushCommittedPages();
        }
    }

    /**
     * commits the transaction specified by the given transaction ID
     *
     * @param taid
     */
    public void commit(int taid) {
        System.out.println("Commited TA: "+taid);
        if (bufferFull()) {
            pushCommittedPages();
        }
    }

    private boolean bufferFull() {
        return (_buffer.size() == 5);
    }

    private void pushCommittedPages() {
        _buffer.clear();
        System.out.println("Buffer emptied");
    }
    
	void propagatePage(Page page){
		try {
			String content = page.getPageId() + "," + page.getLSN() + ","
					+ page.getData() + "\n";

			File file = new File(createPageFileName(page.getPageId()));

			// if file doesnt exists, then create it
			if (!file.exists()) {
				file.createNewFile();
			}

			FileWriter fw = new FileWriter(file.getAbsoluteFile());
			BufferedWriter bw = new BufferedWriter(fw);
			bw.write(content);
			bw.close();

			System.out.println("Page is propagated (Page file is created)");

		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public String createPageFileName(int x){
		return "";
	}

}
