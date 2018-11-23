package mymazegame6;

import UserBuildIn.*;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.lang.String;
import static mymazegame6.MazeSolver.solved;

public class Maze implements Runnable {

    private int n;                 // dimension of maze
    private boolean[][] north;     // is there a wall to north of cell i, j
    private boolean[][] east;
    private boolean[][] south;
    private boolean[][] west;
    private boolean[][] visited;
    private boolean[][] bnorth;     // is there a breakable wall to north of cell i, j
    private boolean[][] beast;
    private boolean[][] bsouth;
    private boolean[][] bwest;

    public int[][] visited_point = new int[110][110];

    private boolean done = false;
    public boolean finished = false;
    public boolean game_over = false;
    public boolean solve = false;
    public int player_point;
    public int Breaked_wall;
    public int needed_point_to_break_wall;
    //public int[][] a = new int[110][10];
    public int[][] b = new int[110][110];
    //clock
    int m = 1, s1 = 0, s2 = 0, x = 0, S1, S2, M;
    int X = 1, Y = 1;
    Thread t = new Thread(this);

    public Maze(int n) {
        this.n = n;

        

        player_point = 1;
        Breaked_wall = n / 2;
        needed_point_to_break_wall = 2 * n / 5;

        StdDraw.enableDoubleBuffering();
        StdDraw.setXscale(0, n + 2);
        StdDraw.setYscale(0, n + 2);
        init();
        generate(1, 1);
        generatebreakalbewall();
        clockstart();
    }

    private void init() {

        for (int i = 1; i < n + 2; i++) {
            for (int j = 1; j < n + 2; j++) {
                visited_point[i][j] = 1;
            }
        }

        // initialize border cells as already visited
        visited = new boolean[n + 2][n + 2];

        for (int x = 0; x < n + 2; x++) {
            visited[x][0] = true;
            visited[x][n + 1] = true;

        }
        for (int y = 0; y < n + 2; y++) {
            visited[0][y] = true;
            visited[n + 1][y] = true;

        }

        // initialize all walls as present
        north = new boolean[n + 2][n + 2];
        east = new boolean[n + 2][n + 2];
        south = new boolean[n + 2][n + 2];
        west = new boolean[n + 2][n + 2];

        for (int x = 0; x < n + 2; x++) {
            for (int y = 0; y < n + 2; y++) {
                north[x][y] = true;
                east[x][y] = true;
                south[x][y] = true;
                west[x][y] = true;

            }
        }
    }

    // generate the maze
    private void generate(int x, int y) {

        visited[x][y] = true;

        // while there is an unvisited neighbor
        while (!visited[x][y + 1] || !visited[x + 1][y] || !visited[x][y - 1] || !visited[x - 1][y]) {

            Random z = new Random();
            double r = z.nextInt(4);

            if (r == 0 && !visited[x][y + 1]) {
                north[x][y] = false;
                south[x][y + 1] = false;
                generate(x, y + 1);

            } else if (r == 1 && !visited[x + 1][y]) {
                east[x][y] = false;
                west[x + 1][y] = false;
                generate(x + 1, y);

            } else if (r == 2 && !visited[x][y - 1]) {
                south[x][y] = false;
                north[x][y - 1] = false;
                generate(x, y - 1);

            } else if (r == 3 && !visited[x - 1][y]) {
                west[x][y] = false;
                east[x - 1][y] = false;
                generate(x - 1, y);

            }

        }

    }

    private void generatebreakalbewall() {

        bnorth = new boolean[n + 2][n + 2];
        beast = new boolean[n + 2][n + 2];
        bsouth = new boolean[n + 2][n + 2];
        bwest = new boolean[n + 2][n + 2];
        int x, y;
        Random z = new Random();
        for (int i = 0; i < n; i++) {

            x = z.nextInt(n) + 1;
            y = z.nextInt(n) + 1;

            if ((x > 1 && y > 1) && n == 5) {
                b[i][0] = x;
                b[i][1] = y;
            } else if ((x > 2 && y > 2) && n > 5) {

                b[i][0] = x;
                b[i][1] = y;
            } else {

                i--;
            }

            if (i > -1) {
                x = b[i][0];
                y = b[i][1];

                if (!south[x][y]) {
                    bsouth[x][y] = true;
                    bnorth[x][y - 1] = true;

                } else if (!north[x][y]) {
                    bnorth[x][y] = true;
                    bsouth[x][y + 1] = true;

                } else if (!west[x][y]) {
                    bwest[x][y] = true;
                    beast[x - 1][y] = true;

                } else if (!east[x][y]) {
                    beast[x][y] = true;
                    bwest[x + 1][y] = true;

                }
            }

        }

    }

    // solve the maze using depth-first search
    private void solve(int x, int y) {
        if (x == 0 || y == 0 || x == n + 1 || y == n + 1) {
            return;
        }
        if (done || visited[x][y]) {
            return;
        }
        visited[x][y] = true;

        StdDraw.setPenColor(StdDraw.BLUE);
        StdDraw.filledSquare(x + 0.5, y + 0.5, 0.25);
        StdDraw.show();
        StdDraw.pause(30);

        // reached finish point
        if (x == n && y == n) {
            done = true;
        }

        if (!north[x][y]) {
            solve(x, y + 1);
        }
        if (!east[x][y]) {
            solve(x + 1, y);
        }
        if (!south[x][y]) {
            solve(x, y - 1);
        }
        if (!west[x][y]) {
            solve(x - 1, y);
        }

        if (done) {
            return;
        }

        StdDraw.setPenColor(StdDraw.GRAY);
        StdDraw.filledSquare(x + 0.5, y + 0.5, 0.25);
        StdDraw.show();
        StdDraw.pause(30);
    }

    // solve the maze starting from the start state
    public void showSolve(int posX, int posY) {
        for (int x = 1; x <= n; x++) {
            for (int y = 1; y <= n; y++) {
                visited[x][y] = false;
            }
        }
        done = false;
        solve(posX, posY);
    }

    //clock
    public void clock(int x, int y) {

        if (m == 1 && s1 == 0 && s2 == 0) {
            S1 = s1;
            S2 = s2;
            M = m;
            s1 = 1;
            s2 = 9;
            m = 0;
            player_point = 0;
        } else if (s2 == 0) {
            S1 = s1;
            S2 = s2;
            M = m;
            if (s1 > 0) {
                s1--;
                s2 = 9;
            } else {
                m = 1;
            }

        } else {
            S1 = s1;
            S2 = s2;
            M = m;
            s2--;
        }
        if (!solve) {
//            MazeSolver.solved = true;
//            MazeSolver.keyUpPresssed = true;
//            MazeSolver.keyDownPresssed = true;
//            MazeSolver.keyLeftPresssed = true;
//            MazeSolver.keyRightPresssed = true;
            draw(X, Y);
        }
        try {
            t.sleep(1000);
        } catch (InterruptedException ex) {
            Logger.getLogger(Maze.class.getName()).log(Level.SEVERE, null, ex);
        }
        if (!game_over && !finished) {
            clock(X, Y);
        }
    }

    public void clockstart() {
        t.start();
    }

    public void run() {
        clock(X, Y);
    }

    // draw the maze
    public void draw(int circleX, int circleY) {
        X = circleX;
        Y = circleY;
        StdDraw.clear();

        float cons = .5f;

        if (game_over) {

            finished = true;

        } else {
            if ((circleX == circleY) && (circleY == n)) {
                if (Breaked_wall == 0) {

                    finished = true;
                } else {
                    StdDraw.setPenColor(StdDraw.GREEN);
                    StdDraw.text(4 * n / 5, (n + 1.5), "You have to break " + Breaked_wall + " walls to win...");
                }
            } else {
                StdDraw.setPenColor(StdDraw.BLACK);
                StdDraw.text(4 * n / 5, n + 1.5, "Player's point: " + player_point + "   Needed to Break Walls " + Breaked_wall);
            }
        }

        StdDraw.setPenColor(StdDraw.BLACK);
        StdDraw.text(4 * (n + 2) / 5, .3, needed_point_to_break_wall + " point needed to break a wall");

        for (int x = 1; x <= n; x++) {
            for (int y = 1; y <= n; y++) {
                if (visited_point[x][y] == 0) {
                    StdDraw.setPenColor(StdDraw.LIGHT_GRAY);
                    StdDraw.filledSquare(0.5f + x, 0.5f + y, 0.5);
                }
            }
        }
        StdDraw.setPenColor(StdDraw.RED);
        StdDraw.filledCircle(n + cons, n + cons, 0.375);
        StdDraw.filledCircle(0.5f + circleX, 0.5f + circleY, 0.375);

        StdDraw.setPenColor(StdDraw.GREEN);
        StdDraw.setPenRadius(0.005);
        for (int i = 0; i < n; i++) {
            int x = b[i][0];
            int y = b[i][1];

            if (!south[x][y] && bsouth[x][y]) {

                StdDraw.line(x, y, x + 1, y);
            } else if (!north[x][y] && bnorth[x][y]) {

                StdDraw.line(x, y + 1, x + 1, y + 1);
            } else if (!west[x][y] && bwest[x][y]) {

                StdDraw.line(x, y, x, y + 1);
            } else if (!east[x][y] && beast[x][y]) {

                StdDraw.line(x + 1, y, x + 1, y + 1);
            }

        }

        StdDraw.setPenColor(StdDraw.BLACK);
        StdDraw.setPenRadius(0.01);
        for (int x = 1; x <= n; x++) {
            for (int y = 1; y <= n; y++) {
                if (south[x][y]) {
                    StdDraw.line(x, y, x + 1, y);
                }
                if (north[x][y]) {
                    StdDraw.line(x, y + 1, x + 1, y + 1);
                }
                if (west[x][y]) {
                    StdDraw.line(x, y, x, y + 1);
                }
                if (east[x][y]) {
                    StdDraw.line(x + 1, y, x + 1, y + 1);
                }
            }
        }
//        StdDraw.setPenColor(StdDraw.GREEN);
//        StdDraw.setPenRadius(0.005);
//        for (int i = 0; i < n; i++) {
//            int x = a[i][0];
//            int y = a[i][1];
//            if (b[x][y] > 0) {
//                if (!south[x][y]) {
//
//                    b[x][y - 1] = 1;
//                    StdDraw.line(x, y, x + 1, y);
//                } else if (!north[x][y]) {
//
//                    b[x][y + 1] = 1;
//                    StdDraw.line(x, y + 1, x + 1, y + 1);
//                } else if (!west[x][y]) {
//
//                    b[x - 1][y] = 1;
//                    StdDraw.line(x, y, x, y + 1);
//                } else if (!east[x][y]) {
//
//                    b[x + 1][y] = 1;
//                    StdDraw.line(x + 1, y, x + 1, y + 1);
//                }
//            }
//        }

        //clock
        if (M == 1 && S1 == 0 && S2 == 0) {

            StdDraw.setPenColor(StdDraw.GREEN);
            StdDraw.text(n / 5, (n + 1.5), "0:20");
        } else if (S2 == 0 && S1 > 5) {
            StdDraw.setPenColor(StdDraw.GREEN);
            StdDraw.text(n / 5, (n + 1.5), "0:" + S1 + "" + S2);

        } else {
            if (S2 < 6 && S1 == 0) {
                StdDraw.setPenColor(StdDraw.RED);
            } else {
                StdDraw.setPenColor(StdDraw.GREEN);
            }
            StdDraw.text(n / 5, (n + 1.5), "0:" + S1 + "" + S2);

        }
        StdDraw.setPenColor(StdDraw.BLACK);
        StdDraw.show();
        //StdDraw.pause(1000);        

    }

    //cheaking point of player
    public boolean cheakingpoint(int x, int y, String str) {

        boolean k = false;

        if (str == "UP" && !north[x][y]) {
            if (bnorth[x][y]) {
                if (player_point >= needed_point_to_break_wall) {
                    if (Breaked_wall > 0) {
                        Breaked_wall--;
                    }
                    player_point -= needed_point_to_break_wall;
                    bnorth[x][y] = false;
                    bsouth[x][y + 1] = false;

                } else {
                    game_over = true;
                    return true;
                }
            }
        } else if (str == "DOWN" && !south[x][y]) {
            if (bsouth[x][y]) {
                if (player_point >= needed_point_to_break_wall) {
                    player_point -= needed_point_to_break_wall;
                    if (Breaked_wall > 0) {
                        Breaked_wall--;
                    }
                    bsouth[x][y] = false;
                    bnorth[x][y - 1] = false;

                } else {
                    game_over = true;
                    return true;
                }
            }
        } else if (str == "LEFT" && !west[x][y]) {
            if (bwest[x][y]) {
                if (player_point >= needed_point_to_break_wall) {
                    player_point -= needed_point_to_break_wall;
                    if (Breaked_wall > 0) {
                        Breaked_wall--;
                    }
                    bwest[x][y] = false;
                    beast[x - 1][y] = false;

                } else {
                    game_over = true;
                    return true;
                }
            }
        } else if (str == "RIGHT" && !east[x][y]) {
            if (beast[x][y]) {
                if (player_point >= needed_point_to_break_wall) {
                    player_point -= needed_point_to_break_wall;
                    if (Breaked_wall > 0) {
                        Breaked_wall--;
                    }
                    beast[x][y] = false;
                    bwest[x + 1][y] = false;

                } else {
                    game_over = true;
                    return true;
                }
            }
        }

        return false;
    }

    public boolean check(int x, int y, String direction) {
        boolean result = false;

        switch (direction) {
            case "UP":
                result = !north[x][y];
                break;
            case "DOWN":
                result = !south[x][y];
                break;
            case "LEFT":
                result = !west[x][y];
                break;
            case "RIGHT":
                result = !east[x][y];
                break;
        }

        return result;
    }

    public void Gameover() {
        float x = 0.0f;
        if (n % 2 == 1) {
            x = .5f;
        }
        StdDraw.clear();
        StdDraw.setPenColor(StdDraw.LIGHT_GRAY);
        StdDraw.filledSquare((n + 2) / 2 + x, (n + 2) / 2 + x, (n + 2) / 2 + x);
        StdDraw.setPenColor(StdDraw.BOOK_RED);
        StdDraw.filledCircle((n + 2) / 2 + x, (n + 2) / 2 + x, (n + 2) / 2 + x);
//        StdDraw.setPenColor(StdDraw.BOOK_BLUE);
//        StdDraw.filledSquare(n / 5, n + 2 - n / 5, n / 5);
//        StdDraw.filledSquare(n + 2 - n / 5, n / 5, n / 5);

        if (MazeSolver.X > 0) {
            StdDraw.setPenColor(StdDraw.RED);
            StdDraw.text2((n + 2) / 2 + x, 2 * (n + 2) / 3, "GAME OVER . . .");
            StdDraw.setPenColor(StdDraw.GREEN);
            StdDraw.text((n + 2) / 2 + x, (n + 2) / 2 + x, "BUT,you got another chance to comptete the level");
            StdDraw.text3((n + 2) / 2 + x, (n / 5) * 2, "Press 'ENTER' Key to complete the current level");
        } else {
            StdDraw.setPenColor(StdDraw.RED);
            StdDraw.text2((n + 2) / 2 + x, (n + 2) / 2 + x, "GAME OVER . . .");
        }
        StdDraw.show();

    }

    public void Congratulation() {

        float x = 0.0f;
        if (n % 2 == 1) {
            x = .5f;
        }
        StdDraw.clear();
        StdDraw.setPenColor(StdDraw.LIGHT_GRAY);
        StdDraw.filledSquare((n + 2) / 2 + x, (n + 2) / 2 + x, (n + 2) / 2 + x);
        StdDraw.setPenColor(StdDraw.BOOK_RED);
        StdDraw.filledCircle((n + 2) / 2 + x, (n + 2) / 2 + x, (n + 2) / 2 + x);
//        StdDraw.setPenColor(StdDraw.BOOK_BLUE);
//        StdDraw.filledSquare(n / 5, n + 2 - n / 5, n / 5);
//        StdDraw.filledSquare(n + 2 - n / 5, n / 5, n / 5);
        StdDraw.setPenColor(StdDraw.GREEN);
        StdDraw.text2((n + 2) / 2 + x, 2 * (n + 2) / 3, "Congratulations ! ! ! ");
        StdDraw.text3((n + 2) / 2 + x, (n + 2) / 2 + x, "Level " + n / 5 + " complete , let's try next level...");
        StdDraw.text3((n + 2) / 2 + x, (n / 5) * 2, "Press 'ENTER' Key to start next level");

        StdDraw.show();

    }
}
