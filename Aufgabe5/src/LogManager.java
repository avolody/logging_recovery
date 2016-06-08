import java.io.BufferedReader;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class LogManager implements ILogManager {
	
	private FileWriter fw;
	
	public LogManager(){
		File path = new File("logs/");
		path.mkdirs();
		
		File file = new File("logs/log");
		try {
			fw = new FileWriter(file, true);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	@Override
	public void writeLog(Log log) {
        try {
			fw.write(log.toString());
		} catch (IOException e) {
			e.printStackTrace();
		}      
	}

	@Override
	public Log readLog(BufferedReader reader) {
		 try {
			String str;
			str = reader.readLine();
			while ((str = reader.readLine()) != null) {
				String[] logParts = str.split(",");
				return new Log(Integer.parseInt(logParts[0]),Integer.parseInt(logParts[1]),logParts[2],Integer.parseInt(logParts[3]),logParts[4]);
			}
		} catch (IOException e) {
			System.out.println("File Read Error");
		}
		return null;
	}
}
