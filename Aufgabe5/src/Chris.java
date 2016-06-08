public class Chris extends Client{

	 public Chris() {
	        super("Chris");
	    }
	 
	 @Override
	  public void doSmt() {
	        Transaction tx = this.createTA();
	        tx.write(randomPage(), "seen");
	        randomSleep();
	        tx.write(randomPage(), "my");
	        randomSleep();
	        tx.write(randomPage(), "two");
	        randomSleep();
	        tx.commit();
	    }
}