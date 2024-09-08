package edu.robertob.p1compi2.data;

import java.util.ArrayList;

/**
 * @author robertob
 * Esta clase se encarga de manejar la sesión actual del usuario, es decir, los archivos que tiene abiertos, el archivo que está activo, etc.
 */

public class CurrentSession {
    private ArrayList<PFile> files;
    private int activeFile;

    public CurrentSession() {
        this.files = new ArrayList<>();
        this.activeFile = -1;
    }

    public void addFile(PFile file) {
        this.files.add(file);
    }

    public void removeFile(int index) {
        if (this.files.get(index) != null) {
            this.files.remove(index);
            if (this.activeFile == index) {
                this.activeFile = -1;
            }
        }
    }

    public void setActiveFile(int index) {
        if (this.files.get(index) != null) {
            this.activeFile = index;
        }
    }

    public PFile getActiveFile() {
        if (this.activeFile != -1) {
            return this.files.get(this.activeFile);
        }
        return null;
    }

    public ArrayList<PFile> getFiles() {
        return this.files;
    }

    public int getActiveFileIndex() {
        return this.activeFile;
    }
}
