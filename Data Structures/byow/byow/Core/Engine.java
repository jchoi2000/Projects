package byow.Core;

import byow.TileEngine.TERenderer;
import byow.TileEngine.TETile;
import edu.princeton.cs.introcs.StdDraw;

import java.awt.*;
import java.io.File;
import java.util.ArrayList;
import java.util.Random;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Engine {
    TERenderer ter = new TERenderer();
    /* Feel free to change the width and height. */
    public static final int WIDTH = 80;
    public static final int HEIGHT = 50;
    private Random rand;
    private boolean mainMenu = true;
    private boolean gameOver = false;

    /**
     * Method used for exploring a fresh world. This method should handle all inputs,
     * including inputs from the main menu.
     */

    /** @SOURCE https://www.baeldung.com/java-check-string-number  */
    public static boolean isNumeric(String strNum) {
        if (strNum == null) {
            return false;
        }
        try {
            double d = Double.parseDouble(strNum);
        } catch (NumberFormatException nfe) {
            return false;
        }
        return true;
    }

    //This runs the game by interacting with the keyboard
    public void interactWithKeyboard() {
        String SEED = "";
        String keyLog = "";

        StdDraw.setCanvasSize(50 * 16, 50 * 16);
        Font font1 = new Font("Monaco", Font.BOLD, 30);
        Font font2 = new Font("Monaco", Font.BOLD, 20);
        StdDraw.setPenColor(Color.white);
        StdDraw.setFont(font1);
        StdDraw.setXscale(0, 50);
        StdDraw.setYscale(0, 50);
        StdDraw.clear(Color.BLACK);

        StdDraw.text(25, 100 / 3, "CS61B: THE GAME");
        StdDraw.setFont(font2);
        StdDraw.text(25, 25, "New Game (N)");
        StdDraw.text(25, 25 - 2, "Load Game (L)");
        StdDraw.text(25, 25 - 4, "Quit (Q)");

        while (mainMenu) {
            if (StdDraw.hasNextKeyTyped()) {
                char c = StdDraw.nextKeyTyped();
                if (c == 'n' || c == 'N') {
                    StdDraw.setFont(font1);
                    StdDraw.clear(Color.black);
                    StdDraw.text(25, 100/3, "Seed");
                    while (mainMenu) {
                        if (StdDraw.hasNextKeyTyped()) {
                            char n = StdDraw.nextKeyTyped();
                            if (n == 's' || n == 'S') {
                                mainMenu = false;
                            } else if (isNumeric(Character.toString(n))) {
                                SEED += n;
                                StdDraw.clear(Color.black);
                                StdDraw.text(25, 100/3, "Seed");
                                StdDraw.text(25, 25, SEED);
                            }
                        }
                    }
                }
                if (c == 'l' || c == 'L') {
                    String save = Utils.readContentsAsString(Utils.join(System.getProperty("user.dir"), "LASTSAVE"));
                    String first = save.substring(0,1);
                    String rest = save.substring(1);
                    while (!first.equals("$")) {
                        SEED += first;
                        first = rest.substring(0,1);
                        rest = rest.substring(1);
                    }
                    keyLog = rest;
                    mainMenu = false;
                }
                if (c == 'q' || c == 'Q') {
                    System.exit(0);
                }
            }
        }
        runGame(SEED, keyLog);
        System.exit(0);
    }

    //Handles the main game play loop
    public void runGame(String SEED, String keyLog) {
        long seed = Long.parseLong(SEED);
        Random rand = new Random(seed);

        ter.initialize(80, 50);
        GameWorld world = new GameWorld(rand);

        // Loads the save state
        if (!keyLog.equals("")) {
            char curr = keyLog.charAt(0);
            String rest = keyLog.substring(1);
            for (int i = 1; i < keyLog.length(); i++) {
                if (curr == 'w' || curr == 'W' || curr == 'a' || curr == 'A' || curr == 's' || curr == 'S' || curr == 'd' || curr == 'D') {
                    world.movePlayer(curr);
                }
                if (curr == 'f' || curr == 'F') {
                    world.interact();
                }
                if (curr == 'i' || curr == 'I' || curr == 'j' || curr == 'J' || curr == 'k' || curr == 'K' || curr == 'l' || curr == 'L') {
                    world.movePlayer2(curr);
                }
                if (curr == 'h' || curr == 'H') {
                    world.interact2();
                }
                curr = rest.charAt(0);
                rest = rest.substring(1);
            }
        }

        // Runs the game
        ter.renderFrame(world.Map);
        while (!gameOver) {
            //This keeps track of what the mouse hovers over and displays in the HUD on the top left.
            int mouseX = (int) StdDraw.mouseX();
            int mouseY = (int) StdDraw.mouseY();

            String mouse_description = world.Map[mouseX][mouseY].description();
            StdDraw.setPenColor(Color.white);
            StdDraw.text(2, Engine.HEIGHT - 1, mouse_description);
            StdDraw.show();
            ter.renderFrame(world.Map);

            //Handles players' key inputs.
            if (StdDraw.hasNextKeyTyped()) {
                char c = StdDraw.nextKeyTyped();
                if (c == ':') {
                    keyLog += c;
                    while (!StdDraw.hasNextKeyTyped()) {
                        StdDraw.pause(100);
                    }
                    char next = StdDraw.nextKeyTyped();
                    if (next == 'q' || next == 'Q') {
                        keyLog += next;
                        File LASTSAVE = Utils.join(new File(System.getProperty("user.dir")), "LASTSAVE");
                        String lastSave = SEED + "$" + keyLog;
                        Utils.writeContents(LASTSAVE, lastSave);
                        gameOver = true;
                    } else {
                        continue;
                    }
                }
                if (c == 'w' || c == 'W' || c == 'a' || c == 'A' || c == 's' || c == 'S' || c == 'd' || c == 'D') {
                    keyLog += c; // Cannot remove common element in the if statements because the way the method handles the ":" input.
                    world.movePlayer(c);
                }
                if (c == 'f' || c == 'F') {
                    keyLog += c;
                    world.interact();
                }
                if (c == 'i' || c == 'I' || c == 'j' || c == 'J' || c == 'k' || c == 'K' || c == 'l' || c == 'L') {
                    keyLog += c;
                    world.movePlayer2(c);
                }
                if (c == 'h' || c == 'H') {
                    keyLog += c;
                    world.interact2();
                }
                ter.renderFrame(world.Map);
            }
        }
    }



    /**
     * Method used for autograding and testing your code. The input string will be a series
     * of characters (for example, "n123sswwdasdassadwas", "n123sss:q", "lwww". The engine should
     * behave exactly as if the user typed these characters into the engine using
     * interactWithKeyboard.
     *
     * Recall that strings ending in ":q" should cause the game to quite save. For example,
     * if we do interactWithInputString("n123sss:q"), we expect the game to run the first
     * 7 commands (n123sss) and then quit and save. If we then do
     * interactWithInputString("l"), we should be back in the exact same state.
     *
     * In other words, both of these calls:
     *   - interactWithInputString("n123sss:q")
     *   - interactWithInputString("lww")
     *
     * should yield the exact same world state as:
     *   - interactWithInputString("n123sssww")
     *
     * @param input the input string to feed to your program
     * @return the 2D TETile[][] representing the state of the world
     */
    public TETile[][] interactWithInputString(String input) {
        // TODO: Fill out this method so that it run the engine using the input
        // passed in as an argument, and return a 2D tile representation of the
        // world that would have been drawn if the same inputs had been given
        // to interactWithKeyboard().
        //
        // See proj3.byow.InputDemo for a demo of how you can make a nice clean interface
        // that works for many different input types.
        String SEED = "";
        String keyLog = "";

        char CURR = input.charAt(0);
        String REST = input.substring(1);
        if (CURR == 'n' || CURR == 'N') {
            CURR = REST.charAt(0);
            REST = REST.substring(1);
            while (true) {
                if (CURR == 's' || CURR == 'S') {
                    break;
                }
                SEED += CURR;
                CURR = REST.charAt(0);
                REST = REST.substring(1);
            }
            keyLog = REST;
        } else if (CURR == 'l' || CURR == 'L') {
            String movements = REST;

            File CWD = new File(System.getProperty("user.dir"));
            File BYOW_DIR = Utils.join(CWD, "byow");
            File LASTSAVE = Utils.join(BYOW_DIR, "LASTSAVE.txt");

            String save = Utils.readContentsAsString(LASTSAVE);
            CURR = save.charAt(0);
            REST = save.substring(1);

            while (true) {
                if (CURR == '$') {
                    break;
                }
                SEED += CURR;
                CURR = REST.charAt(0);
                REST = REST.substring(1);
            }
            keyLog = REST.substring(0, REST.length() - 2) + movements;
        }

        long seed = Long.parseLong(SEED);
        Random rand = new Random(seed);
        GameWorld world = new GameWorld(rand);

        String movement1 = keyLog;

        if (!keyLog.equals("")) {
            String movement = keyLog;
            char curr = keyLog.charAt(0);
            String rest = keyLog.substring(1);
            for (int i = 1; i < keyLog.length(); i++) {
                if (curr == 'w' || curr == 'W' || curr == 'a' || curr == 'A' || curr == 's' || curr == 'S' || curr == 'd' || curr == 'D') {
                    world.movePlayer(curr);
                }
                if (curr == 'f' || curr == 'F') {
                    world.interact();
                }
                if (curr == 'i' || curr == 'I' || curr == 'j' || curr == 'J' || curr == 'k' || curr == 'K' || curr == 'l' || curr == 'L') {
                    world.movePlayer2(curr);
                }
                if (curr == 'h' || curr == 'H') {
                    world.interact2();
                }
                if (curr == ':') {
                    if (rest.charAt(0) == 'q' || rest.charAt(0) == 'Q') {
                        File CWD = new File(System.getProperty("user.dir"));
                        File BYOW_DIR = Utils.join(CWD, "byow");
                        File LASTSAVE = Utils.join(BYOW_DIR, "LASTSAVE.txt");
                        String SAVE = SEED + "$" + movement;
                        Utils.writeContents(LASTSAVE, SAVE);
                        break;
                    }
                }
                curr = rest.charAt(0);
                rest = rest.substring(1);
            }
        }

        TETile[][] finalWorldFrame = world.Map;
        return finalWorldFrame;
    }
}
