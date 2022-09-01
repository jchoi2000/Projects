package gitlet;

import java.util.*;
import java.io.File;

// TODO: any imports you need here

/** Represents a gitlet repository.
 *  TODO: It's a good idea to give a description here of what else this Class
 *  does at a high level.
 *
 *  @author TODO
 */
public class Repository {
    /**
     *
     * List all instance variables of the Repository class here with a useful
     * comment above them describing what that variable represents and how that
     * variable is used. We've provided two examples for you.
     */
    /** Instance variables */

    /** The current working directory. */
    public static final File CWD = new File(System.getProperty("user.dir"));
    /** The .gitlet directory. */
    public static final File GITLET_DIR = Utils.join(CWD, ".gitlet");
    public static final File BRANCHES_DIR = Utils.join(GITLET_DIR, "branches");
    public static final File BLOBS_DIR = Utils.join(GITLET_DIR, "blobs");
    public static final File COMMITS_DIR = Utils.join(GITLET_DIR, "commits");
    public static final File STAGE = Utils.join(GITLET_DIR, "stage");
    public static final File currStage = Utils.join(STAGE, "currStage");
    public static final File HEAD = Utils.join(BRANCHES_DIR, "HEAD");
    public static final File MASTER = Utils.join(BRANCHES_DIR, "master");
    // public static final File COMMITS_TRIE = Utils.join(COMMITS_DIR, "COMMITS_TRIE");

    /* TODO: fill in the rest of this class. */
    public static void init() {
        if (GITLET_DIR.exists()) {
            System.out.println("A Gitlet version-control system already exists in the current directory.");
            System.exit(0);

        } else {

            GITLET_DIR.mkdir();
            BRANCHES_DIR.mkdir();
            BLOBS_DIR.mkdir();
            COMMITS_DIR.mkdir();
            STAGE.mkdir();

            //Start a new commit object
            Commit initial = new Commit(null, "initial commit", new HashMap<>());
            Utils.writeObject(Utils.join(COMMITS_DIR, initial.getSelf()), initial);

            //Create commits trie
            // Trie commitsTrie = new Trie();
            // Utils.writeObject(COMMITS_TRIE, commitsTrie);

            //Set Master and HEAD pointers
            Utils.writeContents(MASTER, initial.getSelf());
            Utils.writeContents(HEAD, "master");

            //Create staging area
            StagingArea stage = new StagingArea();
            saveStage(stage);
        }
    }

    public static void log() {
        gitletChecker();
        Commit current = getHEAD();
        while (current.getParent() != null && current.getMessage() != "initial commit") {
            System.out.println(current.info());
            current = Utils.readObject(Utils.join(COMMITS_DIR, current.getParent()), Commit.class);
        }
        System.out.println(current.info());
    }

    public static void add(String fileName) {
        gitletChecker();
        StagingArea stage = Utils.readObject(currStage, StagingArea.class);
        File toADD = Utils.join(CWD, fileName);
        if (toADD.exists()) {
            Commit current = getHEAD();
            byte[] blob = Utils.readContents(toADD);
            String blobHASH = Utils.sha1(blob);
            if (stage.containsRM(fileName)) {
                stage.removeFromRM(fileName);
                saveStage(stage);
                return;
            }
            if (current.isTracked(fileName) && current.getBlobs().get(fileName).equals(blobHASH)) {
                if (stage.containsADD(fileName)) {
                    stage.removeFromADD(fileName);
                    saveStage(stage);
                }
                if (stage.containsRM(fileName)) {
                    stage.removeFromRM(fileName);
                    saveStage(stage);
                }
                return;
            }
            stage.add(fileName, blobHASH);
            saveStage(stage);
        } else {
            System.out.println("File does not exist.");
            System.exit(0);
        }
    }

    public static void commit(String message) {
        gitletChecker();
        StagingArea stage = Utils.readObject(currStage, StagingArea.class);
        if (stage.getADD().isEmpty() && stage.getRM().isEmpty()) {
            System.out.println("No changes added to the commit.");
            return;
        }
        if (message.equals("")) {
            System.out.println("Please enter a commit message.");
            return;
        }
        Commit parent = getHEAD();
        HashMap<String, String> blobs = parent.getBlobs();
        for (String fileName : stage.getADD().keySet()) {
            String sha1 = stage.getADD().get(fileName);
            blobs.put(fileName, sha1);
            fileToBlob(Utils.join(CWD, fileName));

        }
        for (String fileName : stage.getRM()) {
            blobs.remove(fileName);
        }
        String currBranch = getHEADbranch();
        Commit newCommit = new Commit(parent.getSelf(), message, blobs);
        Utils.writeObject(Utils.join(COMMITS_DIR, newCommit.getSelf()), newCommit);
        Utils.writeContents(Utils.join(BRANCHES_DIR, currBranch), newCommit.getSelf());

        // Adds commit ID to trie for lookup
        // Trie commitsTrie = Utils.readObject(COMMITS_TRIE, Trie.class);
        // commitsTrie.add(newCommit.getSelf());
        // Utils.writeObject(COMMITS_TRIE, commitsTrie);

        // Reset staging area
        stage.clear();
        saveStage(stage);
    }

    public static void checkoutBranch(String branchName) {
        gitletChecker();
        List<String> branches = Utils.plainFilenamesIn(BRANCHES_DIR);
        if (getHEADbranch().equals(branchName)) {
            System.out.println("No need to checkout the current branch.");
        } else if (!branches.contains(branchName)) {
            System.out.println("No such branch exists.");
        } else {
            String givenSHA = Utils.readContentsAsString(Utils.join(BRANCHES_DIR, branchName));
            HashMap<String, String> givenTracked = Utils.readObject(Utils.join(COMMITS_DIR, givenSHA), Commit.class).getBlobs();
            HashMap<String, String> checkedOutTracked = getHEAD().getBlobs();
            for (String fileName : givenTracked.keySet()) {
                if (Utils.join(CWD, fileName).exists() && !checkedOutTracked.containsKey(fileName)) {
                    System.out.println("There is an untracked file in the way; delete it, or add and commit first.");
                    System.exit(0);
                }
            }
            for (String fileName : givenTracked.keySet()) {
                String fileSHA = givenTracked.get(fileName);
                byte[] blob = Utils.readContents(Utils.join(BLOBS_DIR, fileSHA));
                Utils.writeContents(Utils.join(CWD, fileName), blob);
            }
            for (String fileName : checkedOutTracked.keySet()) {
                if (Utils.join(CWD, fileName).exists() && !givenTracked.containsKey(fileName) && checkedOutTracked.containsKey(fileName)) {
                    Utils.restrictedDelete(Utils.join(CWD, fileName));
                }
            }
            StagingArea stage = Utils.readObject(currStage, StagingArea.class);
            stage.clear();
            saveStage(stage);
            setHEAD(branchName);
        }
    }

    public static void checkoutFile(String fileName) {
        gitletChecker();
        Commit curr = getHEAD();
        String fileSHA = curr.getBlobs().get(fileName);
        if (!curr.getBlobs().containsKey(fileName)) {
            System.out.println("File does not exist in that commit.");
            System.exit(0);
        }
        byte[] blob = Utils.readContents(Utils.join(BLOBS_DIR, fileSHA));
        Utils.writeContents(Utils.join(CWD, fileName), blob);
    }

    public static void checkoutCommit(String commitID, String fileName) {
        gitletChecker();
        if (commitID.length() < 40) {
            List<String> commits = Utils.plainFilenamesIn(COMMITS_DIR);
            for (String commit : commits) {
                for (int i = 0; i < commitID.length(); i++) {
                    String charID = commitID.substring(i, i+1);
                    String charCommit = commit.substring(i, i+1);
                    if (charID != charCommit) {
                        break;
                    }
                    if (i == commitID.length() - 1) {
                        commitID = commit;
                    }
                }
            }
        }
        System.out.println(commitID);
        if (!Utils.join(COMMITS_DIR, commitID).exists()) {
            System.out.println("No commit with that id exists.");
            return;
        }
        Commit target = Utils.readObject(Utils.join(COMMITS_DIR, commitID), Commit.class);
        if (!target.getBlobs().containsKey(fileName)) {
            System.out.println("File does not exist in that commit.");
            System.exit(0);
        }
        String fileSHA = target.getBlobs().get(fileName);
        byte[] blob = Utils.readContents(Utils.join(BLOBS_DIR, fileSHA));
        Utils.writeContents(Utils.join(CWD, fileName), blob);
    }

    public static void rm(String fileName) {
        gitletChecker();
        Commit curr = getHEAD();
        StagingArea stage = Utils.readObject(currStage, StagingArea.class);
        HashMap<String, String> addition = stage.getADD();
        ArrayList<String> removal = stage.getRM();
        if (!addition.containsKey(fileName) && !curr.isTracked(fileName)) {
            System.out.println("No reason to remove this file.");
            return;
        }
        if (addition.containsKey(fileName)) {
            stage.removeFromADD(fileName);
            saveStage(stage);
        }
        if (curr.isTracked(fileName) && !removal.contains(fileName)) {
            stage.rm(fileName);
            saveStage(stage);
            Utils.restrictedDelete(Utils.join(CWD, fileName));
        }
    }

    public static void globalLog() {
        gitletChecker();
        List<String> commits = Utils.plainFilenamesIn(COMMITS_DIR);
        for (String commit : commits) {
            //if (!commit.equals("COMMITS_TRIE")) {
                Commit curr = Utils.readObject(Utils.join(COMMITS_DIR, commit), Commit.class);
                System.out.println(curr.info());
            //}
        }
    }

    public static void find(String message) {
        gitletChecker();
        List<String> commits = Utils.plainFilenamesIn(COMMITS_DIR);
        Integer count = 0;
        for (String commit : commits) {
            //if (commit.equals("COMMITS_TRIE")) {
            //    continue;
            //}
            Commit curr = Utils.readObject(Utils.join(COMMITS_DIR, commit), Commit.class);
            if (curr.getMessage().equals(message)) {
                System.out.println(curr.getSelf());
                count++;
            }
        }
        if (count == 0) {
            System.out.println("Found no commit with that message.");
        }
    }

    public static void branch(String branchName) {
        gitletChecker();
        List<String> branches = Utils.plainFilenamesIn(BRANCHES_DIR);
        if (!branches.contains(branchName)) {
            Utils.writeContents(Utils.join(BRANCHES_DIR, branchName), getHEADID());
        } else {
            System.out.println("A branch with that name already exists.");
        }
    }

    public static void rmbranch(String branchName) {
        gitletChecker();
        List<String> branches = Utils.plainFilenamesIn(BRANCHES_DIR);
        if (getHEADbranch().equals(branchName)) {
            System.out.println("Cannot remove the current branch.");
            System.exit(0);
        } else if (branches.contains(branchName) && !Utils.join(BRANCHES_DIR, branchName).isDirectory()) {
            Utils.join(BRANCHES_DIR, branchName).delete();
        } else {
            System.out.println("A branch with that name does not exist.");
        }
    }
    public static void status() {
        gitletChecker();
        List<String> branches = Utils.plainFilenamesIn(BRANCHES_DIR);
        System.out.println("=== Branches ===");
        String currBranch = getHEADbranch();
        System.out.println("*" + currBranch);
        for (String branch : branches) {
            if (!branch.equals("HEAD") && !branch.equals(currBranch)) {
                System.out.println(branch);
            }
        }
        StagingArea stage = Utils.readObject(currStage, StagingArea.class);
        System.out.println("\n" + "=== Staged Files ===");
        for (String fileName : stage.getADD().keySet()) {
            System.out.println(fileName);
        }
        System.out.println("\n" + "=== Removed Files ===");
        for (String fileName : stage.getRM()) {
            System.out.println(fileName);
        }
        System.out.println("\n" + "=== Modifications Not Staged For Commit ===");
        System.out.println("\n" + "=== Untracked Files ===" + "\n");
    }

    public static void reset(String commitID) {
        gitletChecker();
        List<String> commits = Utils.plainFilenamesIn(COMMITS_DIR);
        if (commits.contains(commitID)) {
            Commit target = Utils.readObject(Utils.join(COMMITS_DIR, commitID), Commit.class);
            HashMap<String, String> givenTracked = target.getBlobs();
            HashMap<String, String> checkedOutTracked = getHEAD().getBlobs();
            for (String fileName : givenTracked.keySet()) {
                if (Utils.join(CWD, fileName).exists() && !checkedOutTracked.containsKey(fileName)) {
                    System.out.println("There is an untracked file in the way; delete it, or add and commit first.");
                    System.exit(0);
                }
            }
            for (String fileName : givenTracked.keySet()) {
                String fileSHA = givenTracked.get(fileName);
                byte[] blob = Utils.readContents(Utils.join(BLOBS_DIR, fileSHA));
                Utils.writeContents(Utils.join(CWD, fileName), blob);
            }
            for (String fileName : checkedOutTracked.keySet()) {
                if (Utils.join(CWD, fileName).exists() && !givenTracked.containsKey(fileName) && checkedOutTracked.containsKey(fileName)) {
                    Utils.restrictedDelete(Utils.join(CWD, fileName));
                }
            }
            StagingArea stage = Utils.readObject(currStage, StagingArea.class);
            stage.clear();
            saveStage(stage);
            String currentBranch = Utils.readContentsAsString(HEAD);
            Utils.writeContents(Utils.join(BRANCHES_DIR, currentBranch), commitID);
        } else {
            System.out.println("No commit with that id exists.");
        }
    }

    // Returns HEAD commit
    private static Commit getHEAD() {
        String branch = Utils.readContentsAsString(HEAD);
        String SHA1 = Utils.readContentsAsString(Utils.join(BRANCHES_DIR, branch));
        return Utils.readObject(Utils.join(COMMITS_DIR, SHA1), Commit.class);
    }

    // Returns SHA of HEAD commit
    private static String getHEADID() {
        Commit HEAD = getHEAD();
        return HEAD.getSelf();
    }

    // Returns string name of current branch.
    private static String getHEADbranch() {
        return Utils.readContentsAsString(HEAD);
    }

    // Sets HEAD to given branch.
    private static void setHEAD(String branchName) {
        Utils.writeContents(HEAD, branchName);
    }

    // Writes stage instance into currStage file.
    private static void saveStage(StagingArea stage) {
        Utils.writeObject(currStage, stage);
    }

    // Given a file name, creates a blob with the file's contents saved in the BLOBS_DIR. Blob is named as the SHA1 of the contents of the file.
    private static void fileToBlob(File fileName) {
        byte[] blob = Utils.readContents(fileName);
        String hash = Utils.sha1(blob);
        Utils.writeContents(Utils.join(BLOBS_DIR, hash), blob);
    }

    // Returns false if not in an initialized gitlet directory.
    private static void gitletChecker() {
        if (!GITLET_DIR.exists()) {
            System.out.println("Not in an initialized gitlet directory.");
            System.exit(0);
        }
    }
}