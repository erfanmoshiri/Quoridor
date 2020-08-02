package Model;

public class Pos {

    Point point;
    int counter;
    Pos prevPos;


    public Pos(Point point, int counter, Pos prevPos) {
        this.point = point;
        this.counter = counter;
        this.prevPos = prevPos;
    }


}
