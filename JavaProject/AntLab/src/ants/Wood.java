package ants;

public class Wood {

    protected int length;
    protected int start;
    protected int end;

    public Wood(int length1) {
        start = 0;
        length = length1;
        end = length1;
    }

    public void isOut(Ant ant) {
        // 判断蚂蚁是否爬出木杆
        if (ant.position <= this.start || ant.position > this.end) {
            ant.isAlive = false;
        }
    }
}