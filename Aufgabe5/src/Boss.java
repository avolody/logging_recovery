public class Boss extends Client{

	 public Boss() {
	        super("Boss");
	    }
	 
	  @Override
	  public void doSmt() {
	        Transaction tx = this.createTA();
	        tx.write(randomPage(), "one");
	        randomSleep();
	        tx.write(randomPage(), "place");
	        randomSleep();
	        tx.write(randomPage(), "!");
	        randomSleep();
	        tx.commit();
	    }
}