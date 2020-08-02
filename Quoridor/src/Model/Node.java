package Model;

import java.util.Comparator;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.Random;

public class Node {

    int hA;
    int hB;
    int forwardA, forwardB;
    double heuristic;
    int wallA;
    int wallB;
    Board board;
    Point wall1 = null; // too realize if new walls are added in the next state
    Point wall2 = null;

    public Node(int wallA, int wallB, Board board) {
        this.wallA = wallA;
        this.wallB = wallB;
        this.board = board;
    }


    void calculateHeuristic(boolean its_A, Gene gene) { //receives a gene

        double n1 = gene.chromosome[0];
        double n2 = gene.chromosome[1];
        double n3 = gene.chromosome[2];
        double n4 = gene.chromosome[3];

        if (its_A) {

            this.heuristic = n1 * (this.hB - this.hA) + n2 * (this.wallA - this.wallB) + n3 * (board.posA.x - (16 - board.posB.x)) + n4 * (forwardA - forwardB);

        } else {

            this.heuristic = n1 * (this.hA - this.hB) + n2 * (this.wallB - this.wallA) + n3 * ((16 - board.posB.x) - board.posA.x) + n4 * (forwardB - forwardA);
        }
    }


}

class MiniMAx {

    Gene gene;
    boolean its_A;

    Node finalNode = null;

    PathFinder pathFinder = new PathFinder();

    double miniMaxAlphaBeta(Node node, int level, int depth, boolean isMaxTurn, double alpha, double beta, int turn) {


        //need to be fixed !!
        if (node.wallB == 0) {
            if (node.wallA == 0) {
                Pos a = pathFinder.BFS(node.board.posA, 16, node.board.board, true);
                while (a.prevPos.prevPos != null) {
                    a = a.prevPos;
                }
                Board b = new Board(node.board);
                b.movePlayer('A', a.point.x, a.point.y);
                Node n = new Node(node.wallA, node.wallB, b);
                finalNode = n;
            } else {
                Pos a = pathFinder.BFS(node.board.posA, 16, node.board.board, true);
                Pos b = pathFinder.BFS(node.board.posB, 0, node.board.board, false);
                if (a.counter < b.counter) {
                    int j = a.counter - 1;
                    for (int i = 0; i < j; i++) {
                        a = a.prevPos;
                    }
                    Board b1 = new Board(node.board);
                    b1.movePlayer('A', a.point.x, a.point.y);
                    Node n = new Node(node.wallA, node.wallB, b1);
                    finalNode = n;
                }
            }
            return 0;
        } else {
            if (level == depth) {
                pathFinder.calculateForward(node);
                node.calculateHeuristic(its_A, gene);
                return node.heuristic;
            }
            Node n, n1 = null;

            PriorityQueue<Node> childs;

            if (isMaxTurn) {

                double best = -1 * Double.MAX_VALUE;
                double value;
                childs = childGenerator(node, level, depth, turn);

                while (!childs.isEmpty()) {
                    n = childs.poll();
                    value = miniMaxAlphaBeta(n, level + 1, depth, false, alpha, beta, turn * -1);
                    if (value > best) {
                        best = value;
                        n1 = n;
                    }
                    if (best > alpha)
                        alpha = best;
                    if (beta < alpha)
                        break;
                }
                if (level == 0) {
                    finalNode = n1;
                }
                return best;

            } else {

                double best = Double.MAX_VALUE;
                double value;
                childs = childGenerator(node, level, depth, turn);
                while (!childs.isEmpty()) {
                    n = childs.poll();
                    value = miniMaxAlphaBeta(n, level + 1, depth, true, alpha, beta, turn * -1);
                    if (value < best)
                        best = value;
                    if (best < beta)
                        beta = best;
                    if (beta <= alpha)
                        break;
                }
                return best;

            }
        }
    }

    PriorityQueue<Node> childGenerator(Node node, int level, int depth, int turn) {

        PriorityQueue<Node> pq;
        Queue<Node> nodes;
        Node n, n1, n2;

        if (turn == 1) { // A

            pq = new PriorityQueue<>(new MaxComperator());
            Board b1 = new Board(node.board);
            n = new Node(node.wallA, node.wallB, b1);
            nodes = pathFinder.movePlayer(n, 'A');
            while (!nodes.isEmpty()) {
                n1 = nodes.poll();
                Pos a = pathFinder.BFS(n1.board.posA, 16, n1.board.board, true);
                Pos b = pathFinder.BFS(n1.board.posB, 0, n1.board.board, false);
                if (a != null) {
                    n1.hA = a.counter;
                } else
                    System.out.println(" a weird null path for A");
                if (b != null) {
                    n1.hB = b.counter;
                } else
                    System.out.println(" a weird null path for B");
                pathFinder.calculateForward(n1);
                n1.calculateHeuristic(its_A, gene);
                pq.add(n1);
            }

            if (node.wallA != 0) {


                if (level == 1) {

                    for (int j = 1; j < 16; j += 2) // horizontal wall
                    {
                        for (int k = 0; k < 15; k += 2) {
                            n2 = pathFinder.puttingWall(node, j, k, j, k + 2, turn);
                            if (n2 != null) {
                                pq.add(n2);
                            }
                        }
                    }

                    for (int j = 0; j <= 14; j += 2) // vertical wall
                    {
                        for (int k = 1; k < 15; k += 2) {
                            n2 = pathFinder.puttingWall(node, j, k, j + 2, k, turn);
                            if (n2 != null) {
                                pq.add(n2);
                            }
                        }
                    }
                } else {
                    // sade sazii
                    Random r = new Random();
                    boolean[][] visited;


                    int x1 = r.nextInt(2) + 1;
                    x1 *= 2;
                    int x2 = r.nextInt(2) + 1;
                    x2 *= 2;

                    for (int j = 1; j <= node.board.posB.x - 1; j += x1) // horizontal wall
                    {
                        for (int k = 0; k <= 14; k += x2) {
                            n2 = pathFinder.puttingWall(node, j, k, j, k + 2, turn);
                            if (n2 != null) {
                                pq.add(n2);
                            }
                        }
                    }

                    x1 = r.nextInt(2) + 1;
                    x1 *= 2;
                    x2 = r.nextInt(2) + 1;
                    x2 *= 2;
                    for (int j = 0; j <= node.board.posB.x - 2; j += x1) // vertical wall
                    {
                        for (int k = 5; k <= 13; k += x2) {
                            n2 = pathFinder.puttingWall(node, j, k, j + 2, k, turn);
                            if (n2 != null) {
                                pq.add(n2);
                            }
                        }
                    }
                }
            }


        } else { // B

            pq = new PriorityQueue<>(new MinComperator());
            Board b1 = new Board(node.board);
            n = new Node(node.wallA, node.wallB, b1);
            nodes = pathFinder.movePlayer(n, 'B');
            while (!nodes.isEmpty()) {
                n1 = nodes.poll();
                Pos a = pathFinder.BFS(n1.board.posA, 16, n1.board.board, true);
                Pos b = pathFinder.BFS(n1.board.posB, 0, n1.board.board, false);
                if (a != null) {
                    n1.hA = a.counter;
                } else
                    System.out.println(" a weird null path for A");
                if (b != null) {
                    n1.hB = b.counter;
                } else
                    System.out.println(" a weird null path for B");

                pathFinder.calculateForward(n1);
                n1.calculateHeuristic(its_A, gene);
                pq.add(n1);
            }

            if (node.wallB != 0) {

                if (level == 1) {

                    for (int j = 1; j < 16; j += 2) // horizontal wall
                    {
                        for (int k = 0; k < 15; k += 2) {
                            n2 = pathFinder.puttingWall(node, j, k, j, k + 2, turn);
                            if (n2 != null) {
                                pq.add(n2);
                            }
                        }
                    }

                    for (int j = 0; j <= 14; j += 2) // vertical wall
                    {
                        for (int k = 1; k < 15; k += 2) {
                            n2 = pathFinder.puttingWall(node, j, k, j + 2, k, turn);
                            if (n2 != null) {
                                pq.add(n2);
                            }
                        }
                    }
                } else {

                    Random r = new Random();
                    int x1 = r.nextInt(2) + 1;
                    x1 *= 2;
                    int x2 = r.nextInt(2) + 1;
                    x2 *= 2;
                    for (int j = node.board.posA.x + 1; j < 16; j += x1) // horizontal wall
                    {
                        for (int k = 0; k < 15; k += x2) {
                            n2 = pathFinder.puttingWall(node, j, k, j, k + 2, turn);
                            if (n2 != null) {
                                pq.add(n2);
                            }
                        }
                    }

                    x1 = r.nextInt(2) + 1;
                    x1 *= 2;
                    x2 = r.nextInt(2) + 1;
                    x2 *= 2;
                    for (int j = node.board.posA.x; j <= 14; j += x1) // vertical wall
                    {
                        for (int k = 5; k <= 13; k += x2) {
                            n2 = pathFinder.puttingWall(node, j, k, j + 2, k, turn);
                            if (n2 != null) {
                                pq.add(n2);
                            }
                        }
                    }
                }
            }
        }

        return pq;
    }
}

class MaxComperator implements Comparator<Node> {

    public int compare(Node n1, Node n2) {
        if (n1.heuristic > n2.heuristic)
            return 1;
        if (n1.heuristic == n2.heuristic)
            return 0;
        return -1;
    }
}

class MinComperator implements Comparator<Node> {
    public int compare(Node n1, Node n2) {
        if (n1.heuristic > n2.heuristic)
            return -1;
        if (n1.heuristic == n2.heuristic)
            return 0;
        return 1;

    }
}
