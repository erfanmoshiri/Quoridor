package Model;

import java.util.LinkedList;
import java.util.Queue;

import static java.lang.StrictMath.abs;

public class PathFinder {

    Gene gene;
    boolean its_A;

    boolean[][] visited;
    Queue<Pos> queue;
    boolean turnA;

    Pos BFS(Point src, int des, char[][] board, boolean turn) {

        turnA = turn;

        visited = new boolean[17][17];
        queue = new LinkedList<Pos>();

        visited[src.x][src.y] = true;

        Pos pos = new Pos(src, 0, null);

        queue.add(pos);

        while (!queue.isEmpty()) {
            Pos pt = queue.peek();
            if (pt.point.x == des) { // reach end line
                return pt;
            }
            queue.poll();
            Object[] o = valids(board, pt, visited);
            queue = (Queue<Pos>) o[0];
            visited = (boolean[][]) o[1];
        }

        return null;
    }


    Object[] valids(char[][] board, Pos pos, boolean[][] visited) {

        int currX = pos.point.x;
        int currY = pos.point.y;
        int counter = pos.counter;

        if (currX <= 14) {
            if (board[currX + 1][currY] != 'W') {
                if (board[currX + 2][currY] == 'B' && currX + 2 == 16) {
                    if (!visited[currX + 2][currY]) {
                        queue.add(new Pos(new Point(currX + 2, currY), counter + 1, pos));
                        visited[currX + 2][currY] = true;
                    }
                }
                if (board[currX + 2][currY] != 'B' && board[currX + 2][currY] != 'A') {
                    if (!visited[currX + 2][currY]) {
                        queue.add(new Pos(new Point(currX + 2, currY), counter + 1, pos));
                        visited[currX + 2][currY] = true;
                    }
                } else {
                    if (currX <= 13 && board[currX + 3][currY] == 'W') {
                        if (currY >= 2 && board[currX + 2][currY - 1] != 'W') {
                            if (!visited[currX + 2][currY - 2]) {
                                queue.add(new Pos(new Point(currX + 2, currY - 2), counter + 1, pos));
                                visited[currX + 2][currY - 2] = true;
                            }
                        }
                        if (currY <= 14 && board[currX + 2][currY + 1] != 'W') {
                            if (!visited[currX + 2][currY + 2]) {
                                queue.add(new Pos(new Point(currX + 2, currY + 2), counter + 1, pos));
                                visited[currX + 2][currY + 2] = true;
                            }
                        }
                    } else if (currX <= 12) {
                        if (!visited[currX + 4][currY]) {
                            queue.add(new Pos(new Point(currX + 4, currY), counter + 1, pos));
                            visited[currX + 4][currY] = true;
                        }
                    }
                }
            }
        }
        if (currY >= 2) {
            if (board[currX][currY - 1] != 'W') {
                if (board[currX][currY - 2] != 'B' && board[currX][currY - 2] != 'A') {
                    if (!visited[currX][currY - 2]) {
                        queue.add(new Pos(new Point(currX, currY - 2), counter + 1, pos));
                        visited[currX][currY - 2] = true;
                    }
                } else {
                    if (currY >= 3 && board[currX][currY - 3] == 'W') {
                        if (currX >= 2 && board[currX - 1][currY - 2] != 'W') {
                            if (!visited[currX - 2][currY - 2]) {
                                queue.add(new Pos(new Point(currX - 2, currY - 2), counter + 1, pos));
                                visited[currX - 2][currY - 2] = true;
                            }
                        }
                        if (currX <= 15 && board[currX + 1][currY - 2] != 'W') {
                            if (!visited[currX + 2][currY - 2]) {
                                queue.add(new Pos(new Point(currX + 2, currY - 2), counter + 1, pos));
                                visited[currX + 2][currY - 2] = true;
                            }
                        }
                    } else if (currY >= 4) {
                        if (!visited[currX][currY - 4]) {
                            queue.add(new Pos(new Point(currX, currY - 4), counter + 1, pos));
                            visited[currX][currY - 4] = true;
                        }
                    }
                }
            }
        }

        if (currY <= 14) {
            if (board[currX][currY + 1] != 'W') {
                if (board[currX][currY + 2] != 'B' && board[currX][currY + 2] != 'A') {
                    if (!visited[currX][currY + 2]) {
                        queue.add(new Pos(new Point(currX, currY + 2), counter + 1, pos));
                        visited[currX][currY + 2] = true;
                    }
                } else {
                    if (currY <= 13 && board[currX][currY + 3] == 'W') {
                        if (currX >= 2 && board[currX - 1][currY + 2] != 'W') {
                            if (!visited[currX - 2][currY + 2]) {
                                queue.add(new Pos(new Point(currX - 2, currY + 2), counter + 1, pos));
                                visited[currX - 2][currY + 2] = true;
                            }
                        }
                        if (currX <= 15 && board[currX + 1][currY + 2] != 'W') {
                            if (!visited[currX + 2][currY + 2]) {
                                queue.add(new Pos(new Point(currX + 2, currY + 2), counter + 1, pos));
                                visited[currX + 2][currY + 2] = true;
                            }
                        }
                    } else if (currY <= 12) {
                        if (!visited[currX][currY + 4]) {
                            queue.add(new Pos(new Point(currX, currY + 4), counter + 1, pos));
                            visited[currX][currY + 4] = true;
                        }
                    }
                }
            }
        }

        if (currX >= 2) {
            if (board[currX - 1][currY] != 'W') {
                if (board[currX - 2][currY] == 'A' && currX - 2 == 0) {
                    if (!visited[currX - 2][currY]) {
                        queue.add(new Pos(new Point(currX - 2, currY), counter + 1, pos));
                        visited[currX - 2][currY] = true;
                    }
                }
                if (board[currX - 2][currY] != 'B' && board[currX - 2][currY] != 'A') {
                    if (!visited[currX - 2][currY]) {
                        queue.add(new Pos(new Point(currX - 2, currY), counter + 1, pos));
                        visited[currX - 2][currY] = true;
                    }
                } else {
                    if (currX >= 3 && board[currX - 3][currY] == 'W') {
                        if (currY >= 2 && board[currX - 2][currY - 1] != 'W') {
                            if (!visited[currX - 2][currY - 2]) {
                                queue.add(new Pos(new Point(currX - 2, currY - 2), counter + 1, pos));
                                visited[currX - 2][currY - 2] = true;
                            }
                        }
                        if (currY <= 15 && board[currX - 2][currY + 1] != 'W') {
                            if (!visited[currX - 2][currY + 2]) {
                                queue.add(new Pos(new Point(currX - 2, currY + 2), counter + 1, pos));
                                visited[currX - 2][currY + 2] = true;
                            }
                        }
                    } else if (currX >= 4) {
                        if (!visited[currX - 4][currY]) {
                            queue.add(new Pos(new Point(currX - 4, currY), counter + 1, pos));
                            visited[currX - 4][currY] = true;
                        }
                    }
                }
            }
        }


        Object[] o = new Object[2];
        o[0] = queue;
        o[1] = visited;

        return o;
    }

    Queue<Node> movePlayer(Node node, char c) {


        Queue<Node> nodes = new LinkedList<>();
        int currX, currY;
        if (c == 'A') {
            currX = node.board.posA.x;
            currY = node.board.posA.y;
        } else {
            currX = node.board.posB.x;
            currY = node.board.posB.y;
        }


        if (currX <= 14) {
            if (node.board.board[currX + 1][currY] != 'W') {
                if (node.board.board[currX + 2][currY] != 'B' && node.board.board[currX + 2][currY] != 'A') {
                    nodes.add(makeNodeByMovingPlayer(node, currX + 2, currY, c));
                } else {
                    if (currX <= 13 && node.board.board[currX + 3][currY] == 'W') {
                        if (currY >= 2 && node.board.board[currX + 2][currY - 1] != 'W') {
                            nodes.add(makeNodeByMovingPlayer(node, currX + 2, currY - 2, c));
                        }
                        if (currY <= 14 && node.board.board[currX + 2][currY + 1] != 'W') {
                            nodes.add(makeNodeByMovingPlayer(node, currX + 2, currY + 2, c));
                        }
                    } else if (currX <= 12) {
                        nodes.add(makeNodeByMovingPlayer(node, currX + 4, currY, c));
                    }
                }
            }
        }
        if (currY >= 2) {
            if (node.board.board[currX][currY - 1] != 'W') {
                if (node.board.board[currX][currY - 2] != 'B' && node.board.board[currX][currY - 2] != 'A') {
                    nodes.add(makeNodeByMovingPlayer(node, currX, currY - 2, c));
                } else {
                    if (currY >= 3 && node.board.board[currX][currY - 3] == 'W') {
                        if (currX >= 2 && node.board.board[currX - 1][currY - 2] != 'W') {
                            nodes.add(makeNodeByMovingPlayer(node, currX - 2, currY - 2, c));
                        }
                        if (currX <= 15 && node.board.board[currX + 1][currY - 2] != 'W') {
                            nodes.add(makeNodeByMovingPlayer(node, currX + 2, currY - 2, c));
                        }
                    } else if (currY >= 4) {
                        nodes.add(makeNodeByMovingPlayer(node, currX, currY - 4, c));
                    }
                }
            }
        }

        if (currY <= 14) {
            if (node.board.board[currX][currY + 1] != 'W') {
                if (node.board.board[currX][currY + 2] != 'B' && node.board.board[currX][currY + 2] != 'A') {
                    nodes.add(makeNodeByMovingPlayer(node, currX, currY + 2, c));
                } else {
                    if (currY <= 13 && node.board.board[currX][currY + 3] == 'W') {
                        if (currX >= 2 && node.board.board[currX - 1][currY + 2] != 'W') {
                            nodes.add(makeNodeByMovingPlayer(node, currX - 2, currY + 2, c));
                        }
                        if (currX <= 14 && node.board.board[currX + 1][currY + 2] != 'W') {
                            nodes.add(makeNodeByMovingPlayer(node, currX + 2, currY + 2, c));
                        }
                    } else if (currY <= 12) {
                        nodes.add(makeNodeByMovingPlayer(node, currX, currY + 4, c));
                    }
                }
            }
        }

        if (currX >= 2) {
            if (node.board.board[currX - 1][currY] != 'W') {
                if (node.board.board[currX - 2][currY] != 'B' && node.board.board[currX - 2][currY] != 'A') {
                    nodes.add(makeNodeByMovingPlayer(node, currX - 2, currY, c));
                } else {
                    if (currX >= 3 && node.board.board[currX - 3][currY] == 'W') {
                        if (currY >= 2 && node.board.board[currX - 2][currY - 1] != 'W') {
                            nodes.add(makeNodeByMovingPlayer(node, currX - 2, currY - 2, c));

                        }
                        if (currY <= 14 && node.board.board[currX - 2][currY + 1] != 'W') {
                            nodes.add(makeNodeByMovingPlayer(node, currX - 2, currY + 2, c));
                        }
                    } else if (currX >= 4) {
                        nodes.add(makeNodeByMovingPlayer(node, currX - 4, currY, c));
                    }
                }
            }
        }
        return nodes;
    }


    Node puttingWall(Node node, int x1, int y1, int x2, int y2, int turn) {

        int temp1 = (y1 + y2) / 2;
        int temp2 = (x1 + x2) / 2;
        Node n;

        if ((x1 % 2 == 1 && (node.board.board[x1 + 1][temp1] != 'W' || node.board.board[x1 - 1][temp1] != 'W')) || ((y1 % 2 == 1 && (node.board.board[temp2][y1 - 1] != 'W' || node.board.board[temp2][y1 + 1] != 'W')))) {
            if (node.board.board[x1][y1] == 0 && node.board.board[x2][y2] == 0) {
                if ((y1 == y2 && y1 % 2 == 1 && abs(x1 - x2) == 2) || (x1 == x2 && x1 % 2 == 1 && abs(y1 - y2) == 2)) {
                    Board b = new Board(node.board);
                    n = new Node(node.wallA, node.wallB, b);
                    n.board.putWall(x1, y1, x2, y2);
                    Pos posA = BFS(n.board.posA, 16, n.board.board, true);
                    Pos posB = BFS(n.board.posB, 0, n.board.board, false);
                    if (posA != null && posB != null) {
                        n.hA = posA.counter;
                        n.hB = posB.counter;
                        n.wall1 = new Point(x1, y1);
                        n.wall2 = new Point(x2, y2);
                        if (turn == 1) {
                            n.wallA--;

                        } else {
                            n.wallB--;
                        }
                        calculateForward(n);
                        n.calculateHeuristic(its_A, gene);
                    } else {
                        return null;
                    }
                } else {
                    return null;
                }
            } else {
                return null;
            }
        } else {
            return null;
        }
        return n;
    }

    Node makeNodeByMovingPlayer(Node node, int x, int y, char c) {
        Board b = new Board(node.board);
        Node n = new Node(node.wallA, node.wallB, b);
        n.board.movePlayer(c, x, y);

        return n;
    }

    void calculateForward(Node node) {

        int x = node.board.posA.x;
        int y = node.board.posA.y;
        x++;
        int count = 0;
        while (x <= 16 && node.board.board[x][y] != 'W') {
            x += 2;
            count++;
        }
        node.forwardA = count;

        x = node.board.posB.x;
        y = node.board.posB.y;
        x--;
        count = 0;
        while (x > 0 && node.board.board[x][y] != 'W') {
            x -= 2;
            count++;
        }
        node.forwardB = count;



    }
}
