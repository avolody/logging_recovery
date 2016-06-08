public class Amina extends Client{

	 public Amina() {
	        super("Amina");
	    }
	 
	  @Override
	  public void doSmt() {
	        Transaction tx = this.createTA();
	        tx.write(randomPage(), "i");
	        randomSleep();
	        tx.write(randomPage(), "am");
	        randomSleep();
	        tx.write(randomPage(), "happy");
	        randomSleep();
	        tx.commit();
	    }
}
