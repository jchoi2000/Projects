package gitlet;

// TODO: any imports you need here
import java.io.Serializable;
import java.util.HashMap;
import java.util.Date;
import java.text.SimpleDateFormat;

/** Represents a gitlet commit object.
 *  TODO: It's a good idea to give a description here of what else this Class
 *  does at a high level.
 *
 *  @author TODO
 */
public class Commit implements Serializable {
    /**
     * TODO: add instance variables here.
     *
     * List all instance variables of the Commit class here with a useful
     * comment above them describing what that variable represents and how that
     * variable is used. We've provided one example for `message`.
     */

    /** The message of this Commit. */
    private String self;
    private String parent;
    private String message;
    private String timeStamp;
    private HashMap<String, String> blobs;

    public Commit(String parentHash, String message, HashMap<String, String> blobs) {
        SimpleDateFormat DTFormat = new SimpleDateFormat("EEE MMM d HH:mm:ss yyyy Z");
        this.parent = parentHash;
        this.message = message;
        this.blobs = blobs;
        if (parentHash == null && message == "initial commit") {
            this.timeStamp = DTFormat.format(new Date(0));
        } else {
            this.timeStamp = DTFormat.format(new Date());
        }
        this.self = Utils.sha1(Utils.serialize(this));
    }

    private Commit(String selfHash, String parentHash, String message, HashMap<String, String> blobs, String timeStamp) {
        this.parent = parentHash;
        this.message = message;
        this.blobs = blobs;
        this.self = selfHash;
        this.timeStamp = timeStamp;
    }

    public String getSelf() {
        return this.self;
    }

    public String getMessage() { return this.message; }

    public String getParent() {
        return this.parent;
    }

    public HashMap<String, String> getBlobs() { return this.blobs; }

    public String info() {
        return "===\n" + "commit " + this.self + "\n" + "Date: " + this.timeStamp + "\n" + this.message + "\n";
    }

    public boolean isTracked(String fileName) {
        return blobs.containsKey(fileName);
    }

    public Commit clone() {
        return new Commit(this.self, this.parent, this.message, this.blobs, this.timeStamp);
    }

    public void setSelf(String shortHash) {
        this.self = shortHash;
    }




    /* TODO: fill in the rest of this class. */
}