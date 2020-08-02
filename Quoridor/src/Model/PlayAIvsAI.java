package Model;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;

public class PlayAIvsAI {

    MiniMAx miniMax = new MiniMAx();
    Board game = new Board();
    boolean turnA = false, turnB = false;
    int wallB = 10, wallA = 10;

    Gene gene1, gene2;


    void rand() {
        if (Math.random() > 0.5)
            turnA = true;
        else
            turnB = true;
    }

    void updateTurn() {
        turnA = !turnA;
        turnB = !turnB;
    }

    boolean goalState() {
        if (turnA) {
            if (game.posA.x == 16) {
                System.out.println("player A is winner!");

                return true;
            }
        } else if (turnB) {
            if (game.posB.x == 0) {
                System.out.println("player B is winner!");
                return true;
            }
        }
        return false;
    }

    public Object[] AIvsAITurn() {

        Object[] o = new Object[3];
        Node node = new Node(wallA, wallB, new Board(game));

        int i;
        if (turnA)
            i = 1;
        else
            i = -1;

        if (turnA) {
            miniMax.gene = gene1;
            miniMax.its_A = true;

        } else {
            miniMax.gene = gene2;
            miniMax.its_A = false;
        }

        double x = miniMax.miniMaxAlphaBeta(node, 0, 3, true, -1 * Integer.MAX_VALUE, Integer.MAX_VALUE, i);
        System.out.println("heuristic : " + x);
        Node n = miniMax.finalNode;
        if (n.wall1 != null && n.wall2 != null) {
            o[0] = false;
            o[1] = n.wall1;
            o[2] = n.wall2;

            System.out.println(" new wall found : " + n.wall1.x + "," + n.wall1.y + "||" + n.wall2.x + "," + n.wall2.y);
            game.putWall(n.wall1.x, n.wall1.y, n.wall2.x, n.wall2.y);
            if (turnA)
                wallA--;
            else
                wallB--;

        } else {
            o[0] = true;
            Point p;
            if (turnA) {
                p = new Point(game.posA.x, game.posA.y);
                o[1] = p;
                o[2] = n.board.posA;
                System.out.println("pos_A : " + n.board.posA.x + " , " + n.board.posA.y);
                game.movePlayer('A', n.board.posA.x, n.board.posA.y);

            } else {
                p = new Point(game.posB.x, game.posB.y);
                o[1] = p;
                o[2] = n.board.posB;
                System.out.println("pos_B : " + n.board.posB.x + " , " + n.board.posB.y);
                game.movePlayer('B', n.board.posB.x, n.board.posB.y);

            }

        }

        return o;
    }

    void readGenes() {
        try {
            FileInputStream is = new FileInputStream("src/Source/current_generation.txt");
            ObjectInputStream oi = new ObjectInputStream(is);
            Generation generation = (Generation) oi.readObject();

             gene1 = generation.genes.get(0);
             gene2 = generation.genes.get(1);

        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
    void setGene() {
        if (turnA) {

            miniMax.gene = miniMax.pathFinder.gene = gene1;
            miniMax.pathFinder.its_A = miniMax.its_A = true;
        } else {
            miniMax.gene = miniMax.pathFinder.gene = gene2;
            miniMax.pathFinder.its_A = miniMax.its_A = false;
        }
    }

}
