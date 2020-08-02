package Model;

class Point {
    int x, y;

    Point(int x, int y) {
        this.x = x;
        this.y = y;
    }

    void setCoordinate(int x, int y) {
        this.x = x;
        this.y = y;
    }
}

public class Board {
    char[][] board = new char[17][17];
    Point posA = new Point(0, 8);
    Point posB = new Point(16, 8);



    public Board() {
        for (int i = 0; i < 17; i++) {
            for (int j = 0; j < 17; j++) {
                if (i % 2 == 0 && j % 2 == 0) {
                    board[i][j] = '.';
                }
                if (i % 2 == 1 && j % 2 == 1) {
                    board[i][j] = 'W';
                }
            }
        }
        board[posA.x][posA.y] = 'A';
        board[posB.x][posB.y] = 'B';
    }

    public Board(Board board) {
        for (int i = 0; i < 17; i++) {
            for (int j = 0; j < 17; j++) {
                this.board[i][j] = board.board[i][j];
            }
        }
        Point point = new Point(board.posA.x, board.posA.y);
        this.posA = point;
        point = new Point(board.posB.x, board.posB.y);
        this.posB = point;
    }

    void movePlayer(char player, int x, int y) {
        if (player == 'A') {
            board[posA.x][posA.y] = '.';
            board[x][y] = 'A';
            posA.setCoordinate(x, y);
        } else if (player == 'B') {
            board[posB.x][posB.y] = '.';
            board[x][y] = 'B';
            posB.setCoordinate(x, y);
        }
    }

    void putWall(int x1, int y1, int x2, int y2) {
        board[x1][y1] = 'W';
        board[x2][y2] = 'W';
    }

    void removeWall(int x1, int y1, int x2, int y2) {
        board[x1][y1] = 0;
        board[x2][y2] = 0;
    }
}