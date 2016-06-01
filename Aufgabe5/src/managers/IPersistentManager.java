package managers;

public interface IPersistentManager {
	public interface PersistenceManager {

	    int beginTransaction();

	    void commit(int tx);

	    void write(int tx, int pageId, String data);

	}
}
