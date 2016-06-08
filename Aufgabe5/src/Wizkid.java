public class Wizkid extends Client{

	 public Wizkid() {
	        super("Wizkid");
	    }
	 
	 @Override
	  public void doSmt() {
	        Transaction tx = this.createTA();
	        tx.write(randomPage(), "biggest");
	        randomSleep();
	        tx.write(randomPage(), "loves");
	        randomSleep();
	        tx.write(randomPage(), "at");
	        randomSleep();
	        tx.commit();
	    }
}