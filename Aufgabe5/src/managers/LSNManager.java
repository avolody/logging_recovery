package managers;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.concurrent.atomic.AtomicInteger;

public class LSNManager {
	private final AtomicInteger lsn;
	
	public LSNManager(){
		File logFile = new File("logs/logs.txt");

		if (logFile.exists()) {
			lsn = getLastLSN(logFile);
		} else {
			this.lsn = new AtomicInteger();
		}
	}
	
	public int nextLSN() {
        return lsn.incrementAndGet();
    }
	
	private AtomicInteger getLastLSN(File logFile) {
		String lastLine = new String();
		try {
			BufferedReader reader = new BufferedReader(new FileReader(logFile));
			String newLine;

			while ((newLine = reader.readLine()) != null) {
				lastLine = newLine;
			}
			reader.close();

		} catch (Exception e) {
			System.out.println("Read log file error occured");
			e.printStackTrace();
		}
	
		String[] logParts = lastLine.split(",");
		String lastLogLsn = logParts[0];
		AtomicInteger lastLsn = new AtomicInteger(Integer.parseInt(lastLogLsn));
		return lastLsn;
	}
}
