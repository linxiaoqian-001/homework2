package ants;

public class Wood {
    public static int LENGTH;
    public static int START;
    public static int END;

    public Wood(int length) {
        START = 0;
        LENGTH = length;
        END = LENGTH;
    }

    public static void setLENGTH(int LENGTH) {
        Wood.LENGTH = LENGTH;
    }

    public void isOut(Ant ant) {
        // 判断蚂蚁是否爬出木杆
        if (ant.position <= this.START || ant.position > this.END) {
            ant.isAlive = false;
        }
    }
}