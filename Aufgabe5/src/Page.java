

/**
 *
 * @author LJ
 */
public class Page {

    private int id;

    private int lsn;

    private String data;

    public Page() {
    }

    public Page(int pageId, int lsn, String data) {
        this.id = pageId;
        this.lsn = lsn;
        this.data = data;
    }

    public int getPageId() {
        return id;
    }

    public void setPageId(int id) {
        this.id = id;
    }

    public int getLSN() {
        return lsn;
    }

    public void setLSN(int lsn) {
        this.lsn = lsn;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }
}
