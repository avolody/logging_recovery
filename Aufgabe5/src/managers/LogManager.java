package managers;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class LogManager {

    private FileWriter fw;
    private File file;

    public LogManager() {
        File path = new File("logs/");
        path.mkdirs();

        file = new File("logs/log");
    }

    void writeLog(Log log) {
        try {
            fw = new FileWriter(file, true);
            fw.write(log.toString());
            fw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    void closeLog() {
        try {
            fw.close();
        } catch (IOException ex) {
            Logger.getLogger(LogManager.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    Log readLog(BufferedReader reader) {
        try {
            String str;
            str = reader.readLine();
            while ((str = reader.readLine()) != null) {
                String[] logParts = str.split(",");
                return new Log(Integer.parseInt(logParts[0]), Integer.parseInt(logParts[1]), logParts[2], Integer.parseInt(logParts[3]), logParts[4]);
            }
        } catch (IOException e) {
            System.out.println("File Read Error");
        }
        return null;
    }
}
