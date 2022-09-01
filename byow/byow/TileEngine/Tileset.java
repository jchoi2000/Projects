package byow.TileEngine;

import java.awt.Color;

/**
 * Contains constant tile objects, to avoid having to remake the same tiles in different parts of
 * the code.
 *
 * You are free to (and encouraged to) create and add your own tiles to this file. This file will
 * be turned in with the rest of your code.
 *
 * Ex:
 *      world[x][y] = Tileset.FLOOR;
 *
 * The style checker may crash when you try to style check this file due to use of unicode
 * characters. This is OK.
 */

public class Tileset {
    public static Color lightblue = new Color(51, 204, 255);
    public static final TETile AVATAR = new TETile('@', lightblue, Color.black, "player1");
    public static final TETile AVATAR2 = new TETile('@', Color.red, Color.black, "player2");
    public static final TETile WALL = new TETile(' ', new Color(216, 128, 128), Color.gray,
            "wall");
    public static final TETile FLOOR = new TETile('·', new Color(128, 192, 128), Color.black,
            "floor");
    public static final TETile NOTHING = new TETile(' ', Color.black, Color.black, "nothing");

    public static Color lamp1 = new Color(51, 51, 255);
    public static Color lamp2 = new Color(150, 51, 51);
    public static final TETile LAMP = new TETile('I', new Color(128, 192, 128), lamp1,
            "floor");
    public static final TETile LAMP2 = new TETile('I', new Color(128, 192, 128), lamp2,
            "floor");
    public static final TETile LAMP_OFF = new TETile('I', new Color(128, 192, 128), Color.black,
            "floor");
}


