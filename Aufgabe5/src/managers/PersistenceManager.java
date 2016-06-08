package managers;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class PersistenceManager {

    private static int taId;
    // pageId, page
    private static Map<Integer, Page> pageBuffer;
    // taId, pageIds
    private static Map<Integer, ArrayList<Integer>> currentTAs;

    private LogManager logMng;

    private LSNManager lsnMng;

    public PersistenceManager() {
        currentTAs = new ConcurrentHashMap<>();
        pageBuffer = new ConcurrentHashMap<>();
        taId = 0;
        logMng = new LogManager();
        lsnMng = new LSNManager();
    }
    
    public void closeLog()
    {
        logMng.closeLog();
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
            System.out.println("Begin TA "+nextTaId);
            currentTAs.put(nextTaId, new ArrayList<Integer>());
            //TODO: write BOT-Log -> this must be checked
            int lsn = lsnMng.nextLSN();
            Log log = new Log(lsn, nextTaId, "BOT", 0, null);
            logMng.writeLog(log);
            System.out.println("BOT Log written.");
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

        int lsn = lsnMng.nextLSN();//TO-DO
        //int lsn = 0;
        Page page = new Page(pageid, lsn, data);

        System.out.println("TA: " + taid + ", Page: " + pageid + " - " + data);

        Log log = new Log(lsn, taid, "write", pageid, data);
        logMng.writeLog(log);
         System.out.println("WRITE Log written.");
        if (!currentTAs.get(taid).contains(pageid)) {
            currentTAs.get(taid).add(pageid);
        }
        
        pageBuffer.put(page.getPageId(), page);
        System.out.println("Page written to Buffer "+page.getPageId());
        if (bufferFull()) {
            System.out.println("Buffer is full.");
            pushCommittedTAs();
        }
    }

    /**
     * commits the transaction specified by the given transaction ID
     *
     * @param taid
     */
    public void commit(int taid) {
        System.out.println("Committed TA " + taid);
        int lsn = lsnMng.nextLSN();
            Log log = new Log(lsn, taid, "Commit", 0, null);
            logMng.writeLog(log);
             System.out.println("Commit Log written.");
        currentTAs.remove(taid);
        if (bufferFull()) {
            pushCommittedTAs();
        }
    }

    private boolean bufferFull() {
        return (pageBuffer.size() == 5);
    }

    private void pushCommittedTAs() {
        //Iterate through Buffer PageIds
        for (final int pageId : pageBuffer.keySet()) {
            boolean pageCommitted = true;
            //Check of the PageId is in one of the ongoing TAs
            for (final ArrayList<Integer> pageIdArray : currentTAs.values()) {
                if (pageIdArray.contains(pageId)) {
                    //if found - it means not committed
                    pageCommitted = false;
                    break;
                }
            }
            if (pageCommitted) {
                propagatePageOnPersistenceStorage2(pageId, pageBuffer.get(pageId).getLSN(), pageBuffer.get(pageId).getData());
            }
        }
        //If the buffer is still full because there were 
        //no committed TAs, clear it to avoid steal
        if (pageBuffer.size() == 5) {
            pageBuffer.clear();
        }
        System.out.println("Buffer cleared (uncommitted Pages deleted)!");
    }

    void propagatePage(Page page) {
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

    public String createPageFileName(int x) {
        //TODO: CREATE DIRECTORY
        return "pages/" + x + ".txt";
    }

    /**
     *
     * @param pageId
     * @param lsn
     * @param data
     * @return Whether the page was written successfully or not
     */
    private boolean propagatePageOnPersistenceStorage2(int pageId, int lsn, String data) {

        File f = new File(pageId + ".txt");

        try (FileWriter fw = new FileWriter(f)) {
            fw.write(pageId + "," + lsn
                    + "," + data);
            fw.flush();
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
        System.out.println("Page "+ pageId +" propagated");

        return true;
    }
}
