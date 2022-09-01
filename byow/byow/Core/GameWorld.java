package byow.Core;

import byow.TileEngine.TETile;
import byow.TileEngine.Tileset;
import edu.princeton.cs.introcs.StdDraw;

import java.awt.*;
import java.util.ArrayList;
import java.util.Random;

public class GameWorld {
    TETile[][] Map;
    Point player;
    Point player2;
    TETile standingOn = Tileset.LAMP_OFF;
    TETile standingOn2 = Tileset.LAMP_OFF;
    ArrayList<Room> rooms;
    int score1 = 0;
    int score2 = 0;

    public GameWorld(Random rand) {
        this.rooms = new ArrayList<>();
        this.Map = MapGen.createMap(rand, rooms);
        this.player = getPlayer();
        this.player2 = getPlayer2();
    }

    public static class Point {
        private int x;
        private int y;

        public Point(int x, int y) {
            this.x = x;
            this.y = y;
        }

        public Point(Point p) {
            this.x = p.getX();
            this.y = p.getY();
        }

        public int getX() {
            return this.x;
        }

        public int getY() {
            return this.y;
        }
    }

    public static class Room {
        Point center;
        int width;
        int height;
        int area;
        Point[] five_points = new Point[5];

        /*
        Creates a room object with a given center, width, and height.
        With the given center, there are four additional points that keep track of the dimensions of the room.
        From the center, the bottom left point is width and height away from the center point.
        The "four corners" of the room follow the same strategy by being width and height away from the center point.
        This gives us 5 distinct points of a room, that we keep track in the array five_points.
         */

        public Room(Point center, int width, int height) {
            this.width = width;
            this.height = height;

            Point bottom_left = new Point(center.x - width, center.y - height);
            Point bottom_right = new Point(center.x + width, center.y - height);
            Point top_left = new Point(center.x - width, center.y + height);
            Point top_right = new Point(center.x + width, center.y + height);
            this.center = center;

            five_points[0] = bottom_left;
            five_points[1] = bottom_right;
            five_points[2] = top_left;
            five_points[3] = top_right;
            five_points[4] = center;

            this.area = (2*this.width + 1) * (2*this.height + 1);
        }

        public Point getCenter() {
            return this.center;
        }
    }

    // Returns the Point which contains the coordinates of the player avatar
    public Point getPlayer() {
        for (int x = 0; x < Engine.WIDTH; x++) {
            for (int y = 0; y < Engine.HEIGHT; y++) {
                if (this.Map[x][y].description().equals("player1") && this.Map[x][y].textColor == Tileset.lightblue) {
                    return new Point(x,y);
                }
            }
        }
        return null;
    }

    // Returns the Point which contains the coordinates of the player2 avatar
    public Point getPlayer2() {
        for (int x = 0; x < Engine.WIDTH; x++) {
            for (int y = 0; y < Engine.HEIGHT; y++) {
                if (this.Map[x][y].description().equals("player2") && this.Map[x][y].textColor == Color.red) {
                    return new Point(x,y);
                }
            }
        }
        return null;
    }

    // Moves the player avatar according to the string input.
    // If the tile trying to be moved into is not a floor tile, nothing happens.
    public void movePlayer(char input) {
        if (input == 'w' || input == 'W') {
            if (this.Map[player.x][player.y + 1].description().equals("floor")) {
                this.Map[player.x][player.y] = standingOn;
                standingOn = this.Map[player.x][player.y + 1];
                Color background = this.Map[player.x][player.y + 1].backgroundColor;
                Tileset.AVATAR.backgroundColor = background;
                this.Map[player.x][player.y + 1] = Tileset.AVATAR;
                player.y++;
            }
        }
        if (input == 'a' || input == 'A') {
            if (this.Map[player.x - 1][player.y].description().equals("floor")) {
                this.Map[player.x][player.y] = standingOn;
                standingOn = this.Map[player.x - 1][player.y];
                Color background = this.Map[player.x - 1][player.y].backgroundColor;
                Tileset.AVATAR.backgroundColor = background;
                this.Map[player.x - 1][player.y] = Tileset.AVATAR;
                player.x--;
            }
        }
        if (input == 's' || input == 'S') {
            if (this.Map[player.x][player.y - 1].description().equals("floor")) {
                this.Map[player.x][player.y] = standingOn;
                standingOn = this.Map[player.x][player.y - 1];
                Color background = this.Map[player.x][player.y - 1].backgroundColor;
                Tileset.AVATAR.backgroundColor = background;
                this.Map[player.x][player.y - 1] = Tileset.AVATAR;
                player.y--;
            }
        }
        if (input == 'd' || input == 'D') {
            if (this.Map[player.x + 1][player.y].description().equals("floor")) {
                this.Map[player.x][player.y] = standingOn;
                standingOn = this.Map[player.x + 1][player.y];
                Color background = this.Map[player.x + 1][player.y].backgroundColor;
                Tileset.AVATAR.backgroundColor = background;
                this.Map[player.x + 1][player.y] = Tileset.AVATAR;
                player.x++;
            }
        }
    }

    // Moves the player2 avatar according to the string input.
    // If the tile trying to be moved into is not a floor tile, nothing happens.
    public void movePlayer2(char input) {
        if (input == 'i' || input == 'I') {
            if (this.Map[player2.x][player2.y + 1].description().equals("floor")) {
                this.Map[player2.x][player2.y] = standingOn2;
                standingOn2 = this.Map[player2.x][player2.y + 1];
                Color background = this.Map[player2.x][player2.y + 1].backgroundColor;
                Tileset.AVATAR2.backgroundColor = background;
                this.Map[player2.x][player2.y + 1] = Tileset.AVATAR2;
                player2.y++;
            }
        }
        if (input == 'j' || input == 'J') {
            if (this.Map[player2.x - 1][player2.y].description().equals("floor")) {
                this.Map[player2.x][player2.y] = standingOn2;
                standingOn2 = this.Map[player2.x - 1][player2.y];
                Color background = this.Map[player2.x - 1][player2.y].backgroundColor;
                Tileset.AVATAR2.backgroundColor = background;
                this.Map[player2.x - 1][player2.y] = Tileset.AVATAR2;
                player2.x--;
            }
        }
        if (input == 'k' || input == 'K') {
            if (this.Map[player2.x][player2.y - 1].description().equals("floor")) {
                this.Map[player2.x][player2.y] = standingOn2;
                standingOn2 = this.Map[player2.x][player2.y - 1];
                Color background = this.Map[player2.x][player2.y - 1].backgroundColor;
                Tileset.AVATAR2.backgroundColor = background;
                this.Map[player2.x][player2.y - 1] = Tileset.AVATAR2;
                player2.y--;
            }
        }
        if (input == 'l' || input == 'L') {
            if (this.Map[player2.x + 1][player2.y].description().equals("floor")) {
                this.Map[player2.x][player2.y] = standingOn2;
                standingOn2 = this.Map[player2.x + 1][player2.y];
                Color background = this.Map[player2.x + 1][player2.y].backgroundColor;
                Tileset.AVATAR2.backgroundColor = background;
                this.Map[player2.x + 1][player2.y] = Tileset.AVATAR2;
                player2.x++;
            }
        }
    }

    // Allows player1 to interact with lamps.
    // A player can only interact with the lamp if they are standing on top of it.
    public void interact() {
        if (standingOn.equals(Tileset.LAMP) || standingOn.equals(Tileset.LAMP2) || standingOn.equals(Tileset.LAMP_OFF)) {
            for (Room r : rooms) {
                if (r.center.x == player.x && r.center.y == player.y) {
                    if (!Map[player.x][player.y].backgroundColor.equals(Color.black)) {
                        Point c = r.getCenter();
                        int rightOfRoom = c.x + r.width;
                        int leftOfRoom = c.x - r.width;

                        int topOfRoom = c.y + r.height;
                        int bottomOfRoom = c.y - r.height;

                        for (int i = bottomOfRoom + 1; i < topOfRoom; i++) {
                            for (int j = leftOfRoom+1; j < rightOfRoom; j++) {
                                if (Map[j][i].description().equals("floor")) {
                                    Map[j][i] = Tileset.FLOOR;
                                }
                            }
                        }
                        standingOn = Tileset.LAMP_OFF;
                        Tileset.AVATAR.backgroundColor = standingOn.backgroundColor;
                        if (Map[player.x][player.y].backgroundColor.equals(Tileset.lamp2)) {
                            score2 -= r.area;
                        } else {
                            score1 -= r.area;
                        }
                    } else {
                        if (!standingOn.backgroundColor.equals(Tileset.lamp1)) {
                            score1 += r.area;
                        }
                        MapGen.addLighting(r, this.Map);
                        standingOn = Tileset.LAMP;
                        Tileset.AVATAR.backgroundColor = standingOn.backgroundColor;
                    }
                    Map[player.x][player.y] = Tileset.AVATAR;
                    break;
                }
            }
        }
    }

    // Allows player2 to interact with lamps.
    // A player can only interact with the lamp if they are standing on top of it.
    public void interact2() {
        if (standingOn2.equals(Tileset.LAMP) || standingOn2.equals(Tileset.LAMP2) || standingOn2.equals(Tileset.LAMP_OFF)) {
            for (Room r : rooms) {
                if (r.center.x == player2.x && r.center.y == player2.y) {
                    if (!Map[player2.x][player2.y].backgroundColor.equals(Color.black)) {
                        Point c = r.getCenter();
                        int rightOfRoom = c.x + r.width;
                        int leftOfRoom = c.x - r.width;

                        int topOfRoom = c.y + r.height;
                        int bottomOfRoom = c.y - r.height;

                        for (int i = bottomOfRoom + 1; i < topOfRoom; i++) {
                            for (int j = leftOfRoom+1; j < rightOfRoom; j++) {
                                if (Map[j][i].description().equals("floor")) {
                                    Map[j][i] = Tileset.FLOOR;
                                }
                            }
                        }
                        standingOn2 = Tileset.LAMP_OFF;
                        Tileset.AVATAR2.backgroundColor = standingOn2.backgroundColor;
                        if (Map[player2.x][player2.y].backgroundColor.equals(Tileset.lamp1)) {
                            score1 -= r.area;
                        } else {
                            score2 -= r.area;
                        }
                    } else {
                        if (!standingOn2.backgroundColor.equals(Tileset.lamp2)) {
                            score2 += r.area;
                        }
                        MapGen.addLighting2(r, this.Map);
                        standingOn2 = Tileset.LAMP2;
                        Tileset.AVATAR2.backgroundColor = standingOn2.backgroundColor;
                    }
                    Map[player2.x][player2.y] = Tileset.AVATAR2;
                    break;
                }
            }
        }
    }

    /*
        Inspired by this medium article.
        Roughly follows the room creation algorithm specified in the article, with a few minor changes.
        While the map hasn't been filled to a given percentage,
        our implementation chooses a random point on a map, generates a random size room, checks if it fits.

        Building hallways follows our own implementation by connecting centers of rooms.
         */
    /** @SOURCE https://medium.com/@laurheth/building-a-dungeon-cbf10ad6126f  */
    private static class MapGen {

        //Returns a percentage of world that is empty.
        public static float percentage(TETile[][] world) {
            int count_tiles = 0;
            int total_tiles = 0 ;
            int height = Engine.HEIGHT;
            int width = Engine.WIDTH;
            for (int x = 0; x < width - 1; x += 1) {
                for (int y = 0; y < height - 1; y += 1) {
                    if (world[x][y].description() == "nothing") {
                        count_tiles += 1;
                    }
                    total_tiles += 1;
                }
            }
            float ans = (float) count_tiles/total_tiles;
            return ans;
        }

        // Takes in a Random object and an ArrayList rooms and returns the randomly generated map.
        // Rooms is populated with Room objects that represent rooms on the map.
        public static TETile[][] createMap(Random rand, ArrayList<Room> rooms) {
            TETile[][] World = new TETile[Engine.WIDTH][Engine.HEIGHT];
            for (int x = 0; x < Engine.WIDTH; x += 1) {
                for (int y = 0; y < Engine.HEIGHT; y += 1) {
                    World[x][y] = Tileset.NOTHING;
                }
            }

            while (percentage(World) > 0.65) {
                Point center = generateCenter(rand);
                //Generate a random height and width for room
                int RandomHeight = RandomUtils.uniform(rand, 4,  10);
                int RandomWidth = RandomUtils.uniform(rand, 4, 10);
                Room room = new Room(center, RandomWidth, RandomHeight);

                //check if it fits in the world
                if (doesFit(World, room)) {
                    generateRoom(World, center, room);
                    rooms.add(room);
                }
            }

            // Spawns players into map
            World[rooms.get(0).center.x][rooms.get(0).center.y] = Tileset.AVATAR;
            World[rooms.get(1).center.x][rooms.get(1).center.y] = Tileset.AVATAR2;

            // Adds paths
            for (int i = 1; i < rooms.size(); i++) {
                Room r1 = rooms.get(i);
                Room r2 = rooms.get(i - 1);
                createPath(r1, r2, World);
            }

            return World;
        }

        //Allows player1 to turn on lamp. Helper function for interact
        public static void addLighting(Room r, TETile[][] World) {
            Point c = r.five_points[4];
            World[r.center.getX()][r.center.getY()] = Tileset.LAMP;

            int rightOfRoom = c.x + r.width;
            int leftOfRoom = c.x - r.width;

            int topOfRoom = c.y + r.height;
            int bottomOfRoom = c.y - r.height;

            int color = 90;

            for (int l = 1; l < Math.max(r.width, r.height); l++) {
                if (color < 0) {
                    color = 0;
                }
                Point[] outline = outline(c, l);
                TETile gradient = new TETile('·', new Color(128, 192, 128), new Color(color, color, color + 90), "floor");

                for (int i = outline[0].y; i < outline[2].y; i++) {
                    if (!(outline[0].x <= leftOfRoom) && !(outline[1].x >= rightOfRoom)) {
                        if (World[outline[0].x][i].description().equals("floor") && World[outline[1].x][i].description().equals("floor")) {
                            World[outline[0].x][i] = gradient;
                            World[outline[1].x][i] = gradient;
                        }
                    }
                }
                for (int i = outline[0].x; i <= outline[1].x; i++) {
                    if (!(outline[0].y <= bottomOfRoom) && !(outline[2].y >= topOfRoom)) {
                        if (World[i][outline[2].y].description().equals("floor") && World[i][outline[1].y].description().equals("floor")) {
                            World[i][outline[2].y] = gradient;
                            World[i][outline[1].y] = gradient;
                        }
                    }
                }
                color -= 20;
            }
        }

        //Allows player2 to turn on lamp. Helper function for interact
        public static void addLighting2(Room r, TETile[][] World) {
            Point c = r.five_points[4];
            World[r.center.getX()][r.center.getY()] = Tileset.LAMP2;

            int rightOfRoom = c.x + r.width;
            int leftOfRoom = c.x - r.width;

            int topOfRoom = c.y + r.height;
            int bottomOfRoom = c.y - r.height;

            int color = 90;

            for (int l = 1; l < Math.max(r.width, r.height); l++) {
                if (color < 0) {
                    color = 0;
                }
                Point[] outline = outline(c, l);
                TETile gradient = new TETile('·', new Color(128, 192, 128), new Color(color + 70, color, color), "floor");

                for (int i = outline[0].y; i < outline[2].y; i++) {
                    if (!(outline[0].x <= leftOfRoom) && !(outline[1].x >= rightOfRoom)) {
                        if (World[outline[0].x][i].description().equals("floor") && World[outline[1].x][i].description().equals("floor")) {
                            World[outline[0].x][i] = gradient;
                            World[outline[1].x][i] = gradient;
                        }
                    }
                }
                for (int i = outline[0].x; i <= outline[1].x; i++) {
                    if (!(outline[0].y <= bottomOfRoom) && !(outline[2].y >= topOfRoom)) {
                        if (World[i][outline[2].y].description().equals("floor") && World[i][outline[1].y].description().equals("floor")) {
                            World[i][outline[2].y] = gradient;
                            World[i][outline[1].y] = gradient;
                        }
                    }
                }
                color -= 20;
            }
        }

        // Helper for addLighting methods
        // Takes in a center point,
        // and returns an array of the four points which makes a square with center point as the middle.
        public static Point[] outline(Point center, int length) {
            Point[] result = new Point[4];
            result[0] = new Point(center.x - length, center.y - length);
            result[1] = new Point(center.x + length, center.y - length);
            result[2] = new Point(center.x - length, center.y + length);
            result[3] = new Point(center.x + length, center.y + length);
            return result;
        }

        // Creates a path from the center of start to the center of end, regardless of what is in the way.
        // Empty spaces or walls that are encountered will be replaced by floor tiles. Walls for the hallway are added as needed.
        // Starts by extending out L/R from the center of start, and then U/D from the center of end.
        public static void createPath(Room start, Room end, TETile[][] World) {
            Point startPoint = new Point(start.center);
            Point endPoint = new Point(end.center);

            // Creates horizontal path
            if (startPoint.x > endPoint.x) {
                int countX = startPoint.x - endPoint.x;
                Point currX = startPoint;
                while (countX > 0) {
                    currX.x--;
                    if (World[currX.x][currX.y].description().equals("nothing") || World[currX.x][currX.y].description().equals("wall") ||
                            !World[currX.x][currX.y].backgroundColor.equals(Color.black)) {
                        World[currX.x][currX.y] = Tileset.FLOOR;
                        World[currX.x][currX.y + 1] = Tileset.WALL;
                        World[currX.x][currX.y - 1] = Tileset.WALL;
                    }
                    countX--;
                }
                if (World[currX.x-1][currX.y].description().equals("nothing")) {
                    World[currX.x-1][currX.y] = Tileset.WALL;
                }
                if (World[currX.x-1][currX.y + 1].description().equals("nothing")) {
                    World[currX.x-1][currX.y + 1] = Tileset.WALL;
                }
                if (World[currX.x-1][currX.y - 1].description().equals("nothing")) {
                    World[currX.x-1][currX.y - 1] = Tileset.WALL;
                }

            } else if (startPoint.x < endPoint.x) {
                int _countX = endPoint.x - startPoint.x;
                Point _currX = startPoint;
                while (_countX > 0) {
                    _currX.x++;
                    if (World[_currX.x][_currX.y].description().equals("nothing") || World[_currX.x][_currX.y].description().equals("wall") ||
                            !World[_currX.x][_currX.y].backgroundColor.equals(Color.black)) {
                        World[_currX.x][_currX.y] = Tileset.FLOOR;
                        World[_currX.x][_currX.y + 1] = Tileset.WALL;
                        World[_currX.x][_currX.y - 1] = Tileset.WALL;
                    }
                    _countX--;
                }
                if (World[_currX.x+1][_currX.y].description().equals("nothing")) {
                    World[_currX.x+1][_currX.y] = Tileset.WALL;
                }
                if (World[_currX.x+1][_currX.y + 1].description().equals("nothing")) {
                    World[_currX.x+1][_currX.y + 1] = Tileset.WALL;
                }
                if (World[_currX.x+1][_currX.y - 1].description().equals("nothing")) {
                    World[_currX.x+1][_currX.y - 1] = Tileset.WALL;
                }
            }

            // Creates vertical path
            if (startPoint.y > endPoint.y) {
                int countY = startPoint.y - endPoint.y;
                Point currY = endPoint;
                while (countY > 0) {
                    currY.y++;
                    if (World[currY.x][currY.y].description().equals("nothing") || World[currY.x][currY.y].description().equals("wall")) {
                        World[currY.x][currY.y] = Tileset.FLOOR;
                        World[currY.x - 1][currY.y] = Tileset.WALL;
                        World[currY.x + 1][currY.y] = Tileset.WALL;
                    }
                    countY--;
                }
            } else if (startPoint.y < endPoint.y) {
                int _countY = endPoint.y - startPoint.y;
                Point _currY = endPoint;
                while (_countY > 0) {
                    _currY.y--;
                    if (World[_currY.x][_currY.y].description().equals("nothing") || World[_currY.x][_currY.y].description().equals("wall")) {
                        World[_currY.x][_currY.y] = Tileset.FLOOR;
                        World[_currY.x - 1][_currY.y] = Tileset.WALL;
                        World[_currY.x + 1][_currY.y] = Tileset.WALL;
                    }
                    _countY--;
                }
            }
        }

        //This method generates a random center point in the world, 5 away from the edge.
        public static Point generateCenter(Random rand) {
            //Pick random (x, y) coordinates for center of room
            int RandomX = RandomUtils.uniform(rand, 5, Engine.WIDTH-5);
            int RandomY = RandomUtils.uniform(rand, 5, Engine.HEIGHT-5);
            Point center = new Point(RandomX, RandomY);
            return center;
        }

        //This method generates a room on the map with the dimensions of given Room object at Point center.
        public static void generateRoom(TETile[][] World, Point center, Room room) {
            //x coordinates of room
            int rightOfRoom = center.x + room.width;
            int leftOfRoom = center.x - room.width;
            //y coordinates of room
            int topOfRoom = center.y + room.height;
            int bottomOfRoom = center.y - room.height;

            //left&right wall
            for (int i = bottomOfRoom; i < topOfRoom; i++) {
                World[leftOfRoom][i] = Tileset.WALL;
                World[rightOfRoom][i] = Tileset.WALL;
                for (int j = leftOfRoom+1; j < rightOfRoom; j++) {
                    World[j][i] = Tileset.FLOOR;
                }
            }
            //top&bottom wall
            for (int i = leftOfRoom; i <= rightOfRoom; i++) {
                World[i][topOfRoom] = Tileset.WALL;
                World[i][bottomOfRoom] = Tileset.WALL;
            }
            World[center.getX()][center.getY()] = Tileset.LAMP_OFF;
        }

        //This method checks if a room fits in the current world.
        public static boolean doesFit(TETile[][] World, Room room) {
            Point[] five_points = room.five_points;
            //Check boundaries of world
            for (int i = 0; i < five_points.length; i++) {
                if (five_points[i].x + room.width >= Engine.WIDTH) {
                    return false;
                } else if (five_points[i].y + room.height >= Engine.HEIGHT) {
                    return false;
                } else if (five_points[i].x - room.width < 0) {
                    return false;
                } else if (five_points[i].y - room.height < 0) {
                    return false;
                }
            }
            Point start = room.five_points[0];
            for (int y = 0; y < room.height*2 + 1; y++) {
                for (int x = 0; x < room.width*2 + 1; x++) {
                    if (!World[start.x + x][start.y + y].description().equals("nothing")) {
                        return false;
                    }
                }
            }
            return true;
        }

    }
}
