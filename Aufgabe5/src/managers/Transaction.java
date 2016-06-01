package managers;

import managers.IPersistentManager.PersistenceManager;

public class Transaction {
    private final int id;
    private final PersistenceManager persistenceManager;

    public Transaction(PersistenceManager persistenceManager) {
        this.id = persistenceManager.beginTransaction();
        this.persistenceManager = persistenceManager;
    }

    public int getId() {
        return id;
    }

    public void commit() {
        persistenceManager.commit(id);
    }

    public void write(int pageId, String data) {
        persistenceManager.write(id, pageId, data);
    }
}
