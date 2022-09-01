package gitlet;

/** Driver class for Gitlet, a subset of the Git version-control system.
 *  @author TODO
 */
public class Main {

    /** Usage: java gitlet.Main ARGS, where ARGS contains
     *  <COMMAND> <OPERAND1> <OPERAND2> ... 
     */
    public static void main(String[] args) {
        if (args.length == 0) {
            System.out.println("Please enter a command.");
            System.exit(0);
        }
        String firstArg = args[0];
        //Things still need to do: argument checker.
        //If a user inputs a command with the wrong number or format of operands, print the message Incorrect operands. and exit.
        switch(firstArg) {
            case "init":
                if (args.length == 1) {
                    Repository.init();
                    break;
                } else {
                    incorrectOperands();
                }
            case "add":
                if (args.length == 2) {
                    Repository.add(args[1]);
                    break;
                } else {
                    incorrectOperands();
                }
            case "commit":
                if (args.length == 2) {
                    Repository.commit(args[1]);
                    break;
                } else if (args.length == 1) {
                    System.out.println("Please enter a commit message.");
                    System.exit(0);
                } else {
                    incorrectOperands();
                }
            case "checkout":
                if (args.length == 2) {
                    Repository.checkoutBranch(args[1]);
                    break;
                } else if (args.length == 3 && args[1].equals("--")) {
                    Repository.checkoutFile(args[2]);
                    break;
                } else if (args.length == 4 && args[2].equals("--")) {
                    Repository.checkoutCommit(args[1], args[3]);
                    break;
                } else {
                    incorrectOperands();
                }
            case "log":
                if (args.length == 1) {
                    Repository.log();
                    break;
                } else {
                    incorrectOperands();
                }
            case "rm":
                if (args.length == 2) {
                    Repository.rm(args[1]);
                    break;
                } else {
                    incorrectOperands();
                }
            case "global-log":
                if (args.length == 1) {
                    Repository.globalLog();
                    break;
                } else {
                    incorrectOperands();
                }
            case "find":
                if (args.length == 2) {
                    Repository.find(args[1]);
                    break;
                } else {
                    incorrectOperands();
                }
            case "branch":
                if (args.length == 2) {
                    Repository.branch(args[1]);
                    break;
                } else {
                    incorrectOperands();
                }
            case "rm-branch":
                if (args.length == 2) {
                    Repository.rmbranch(args[1]);
                    break;
                } else {
                    incorrectOperands();
                }
            case "status":
                if (args.length == 1) {
                    Repository.status();
                    break;
                } else {
                    incorrectOperands();
                }
            case "reset":
                if (args.length == 2) {
                    Repository.reset(args[1]);
                    break;
                } else {
                    incorrectOperands();
                }
            default:
                System.out.println("No command with that name exists.");
                System.exit(0);
        }
    }

    private static void incorrectOperands() {
        System.out.println("Incorrect operands");
        System.exit(0);
    }
}
