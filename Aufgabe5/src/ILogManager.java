import java.io.BufferedReader;

public interface ILogManager {

	void writeLog(Log log);
	
	Log readLog(BufferedReader reader);
}
