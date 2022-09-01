package gitlet;

import java.util.*;
import java.io.Serializable;

/** Represents a gitlet staging area.
 *  This area has two parts: staging for addition and staging for removal.
 *  Staging for addition, named addition, will be a HashMap.
 *  Staging for removal, named removal, will be an ArrayList.
 *
 *  @author TODO
 */

public class StagingArea implements Serializable {

    private HashMap<String, String> addition;
    private ArrayList<String> removal;

    public StagingArea() {
        addition = new HashMap<String, String>();
        removal = new ArrayList<String>();
    }

    public StagingArea(HashMap<String, String> addStage) {
        addition = addStage;
    }

    public void add(String fileName, String SHA1) {
        addition.put(fileName, SHA1);
    }

    public void rm(String fileName) {
        removal.add(fileName);
    }

    public void clearStageAdd() {
        addition = new HashMap<String, String>();
    }

    public void clearStageRemove() {
        removal = new ArrayList<String>();
    }

    public void clear() {
        clearStageAdd();
        clearStageRemove();
    }

    public HashMap<String, String> getADD() {
        return this.addition;
    }

    public ArrayList<String> getRM() {
        return this.removal;
    }

    // Returns whether or not the file is staged for addition/removal.
    public boolean containsADD(String fileName) {
        return addition.containsKey(fileName);
    }

    public boolean containsRM(String fileName) {
        return removal.contains(fileName);
    }

    public void removeFromADD(String fileName) {
        addition.remove(fileName);
    }

    public void removeFromRM(String fileName) { removal.remove(fileName); }

}