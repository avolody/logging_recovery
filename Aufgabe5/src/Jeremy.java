public class Jeremy extends Client{

	 public Jeremy() {
	        super("Jeremy");
	    }
	 
	  @Override
	  public void doSmt() {
	        Transaction tx = this.createTA();
	        tx.write(randomPage(), "because");
	        randomSleep();
	        tx.write(randomPage(), "i");
	        randomSleep();
	        tx.write(randomPage(), "have");
	        randomSleep();
	        tx.commit();
	    }
}