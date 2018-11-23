package mymazegame6;

import java.awt.event.KeyEvent;
import UserBuildIn.StdDraw;

public class MazeSolver {

    public static int n = 5;
    public static int X = 2;
    public static int Y = 0;

    static boolean solved = false;
    static boolean keyUpPresssed = false;
    static boolean keyDownPresssed = false;
    static boolean keyLeftPresssed = false;
    static boolean keyRightPresssed = false;

    public static void main(String[] args) {

        while (X > 0) {
            solved = false;
            keyUpPresssed = false;
            keyDownPresssed = false;
            keyLeftPresssed = false;
            keyRightPresssed = false;
            int x = 1;
            int y = 1;

            Maze maze = new Maze(n);

            while (true) {
                if (!maze.finished && !maze.game_over) {
                    if (StdDraw.isKeyPressed(KeyEvent.VK_SPACE) && !solved) {

                        maze.solve = true;
                        maze.showSolve(x, y);
                        solved = true;
                    } else if (!StdDraw.isKeyPressed(KeyEvent.VK_SPACE) && solved) {
                        solved = false;
                    }

                    if (StdDraw.isKeyPressed(KeyEvent.VK_UP) && !keyUpPresssed) {
                        if (y < n && maze.check(x, y, "UP")) {
                            maze.solve = false;
                            maze.player_point += maze.visited_point[x][y];
                            maze.visited_point[x][y] = 0;
                            if (!maze.cheakingpoint(x, y, "UP")) {
                                y++;
                            }

                            maze.draw(x, y);

                        }
                        keyUpPresssed = true;
                    } else if (!StdDraw.isKeyPressed(KeyEvent.VK_UP) && keyUpPresssed) {
                        keyUpPresssed = false;
                    }

                    if (StdDraw.isKeyPressed(KeyEvent.VK_DOWN) && !keyDownPresssed) {
                        if (y > 1 && maze.check(x, y, "DOWN")) {
                            maze.solve = false;
                            maze.player_point += maze.visited_point[x][y];
                            maze.visited_point[x][y] = 0;
                            if (!maze.cheakingpoint(x, y, "DOWN")) {
                                y--;
                            }

                            maze.draw(x, y);
                        }
                        keyDownPresssed = true;
                    } else if (!StdDraw.isKeyPressed(KeyEvent.VK_DOWN) && keyDownPresssed) {
                        keyDownPresssed = false;
                    }

                    if (StdDraw.isKeyPressed(KeyEvent.VK_LEFT) && !keyLeftPresssed) {
                        if (x > 1 && maze.check(x, y, "LEFT")) {
                            maze.solve = false;
                            maze.player_point += maze.visited_point[x][y];
                            maze.visited_point[x][y] = 0;
                            if (!maze.cheakingpoint(x, y, "LEFT")) {
                                x--;
                            }

                            maze.draw(x, y);
                        }
                        keyLeftPresssed = true;
                    } else if (!StdDraw.isKeyPressed(KeyEvent.VK_LEFT) && keyLeftPresssed) {
                        keyLeftPresssed = false;
                    }

                    if (StdDraw.isKeyPressed(KeyEvent.VK_RIGHT) && !keyRightPresssed) {
                        if (x < n && maze.check(x, y, "RIGHT")) {
                            maze.solve = false;
                            maze.player_point += maze.visited_point[x][y];
                            maze.visited_point[x][y] = 0;
                            if (!maze.cheakingpoint(x, y, "RIGHT")) {
                                x++;
                            }

                            maze.draw(x, y);
                        }
                        keyRightPresssed = true;
                    } else if (!StdDraw.isKeyPressed(KeyEvent.VK_RIGHT) && keyRightPresssed) {
                        keyRightPresssed = false;
                    }
                } else {
                    if (maze.game_over) {
                        X--;
                        maze.Gameover();

                        while (true) {
                            if (StdDraw.isKeyPressed(KeyEvent.VK_ENTER)) {
                                Y = 1;
                                break;
                            }
                        }

                    } else {
                        maze.Congratulation();
                        n += 5;
                        while (true) {
                            if (StdDraw.isKeyPressed(KeyEvent.VK_ENTER)) {
                                Y = 1;
                                break;
                            }
                        }

                    }
                    if (Y == 1) {
                        Y = 0;
                        break;
                    }

                }
            }
        }
    }

}
