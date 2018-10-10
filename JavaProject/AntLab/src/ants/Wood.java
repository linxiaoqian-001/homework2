package ants;

public class Wood {
    protected int length;
    protected int start;
    protected int end;

    public Wood(int length) {
        start = 0;
        this.length = length;
        end = length;
    }

    public void setLength(int length1) {
        length = length1;
    }

    public void isOut(Ant ant) {
        // 判断蚂蚁是否爬出木杆
        if (ant.position <= this.start || ant.position > this.end) {
            ant.isAlive = false;
        }
    }
}