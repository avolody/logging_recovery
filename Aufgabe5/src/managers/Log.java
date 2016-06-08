/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package managers;

/**
 *
 * @author LJ
 */
public class Log {
	
	private int lsn;
    private int taId;
    private int pageId;
    private String redo;
    private String logType;
    
    public Log(int lsn, int taId, String logType, int pageId, String redo) {
		this.lsn = lsn;
		this.taId = taId;
		this.pageId = pageId;
		this.redo = redo;
		this.logType = logType;
	}

	public int getTaId() {
        return taId;
    }

    public void setTaId(int taId) {
        this.taId = taId;
    }

    public int getLsn() {
        return lsn;
    }

    public void setLsn(int lsn) {
        this.lsn = lsn;
    }

    public int getPageId() {
        return pageId;
    }

    public void setPageId(int pageId) {
        this.pageId = pageId;
    }

    public String getRedo() {
        return redo;
    }

    public void setRedo(String redo) {
        this.redo = redo;
    }

	public String getLogType() {
		return logType;
	}

	public void setLogType(String logType) {
		this.logType = logType;
	}

	@Override
	public String toString() {
		return lsn + "," + taId +  "," + logType +  "," + pageId +  ","  + redo + "\n"; 
	}
}
