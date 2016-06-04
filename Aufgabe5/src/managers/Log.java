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

    private int taId;

    private int lsn;

    private int pageId;

    private String redo;

    public Log(int pageId, int lsn, int taId) {
        this.taId = taId;
        this.lsn = lsn;
        this.pageId = pageId;
        this.redo = "";
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
}
