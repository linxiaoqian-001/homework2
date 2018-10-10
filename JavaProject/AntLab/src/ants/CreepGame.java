package ants;

public class CreepGame {

    protected Wood wood;
    protected boolean isGame;
    protected int count = 0;// 记录蚂蚁运动的位置
    protected int time = 0;// 每一种情况运行的时间

    protected Ant[] ants;
    protected int[][] pole = new int[29][32];// 最长时间27，杆长30，就有30个位置。
    protected int[][] antsDirection = new int[29][5];

    public CreepGame() {
        this.isGame = true;
        ants = new Ant[5];
        for (int i = 0; i < 5; i++) {
            ants[i] = new Ant();
        }
        wood = new Wood(30);
    }

    public void checkCollision() {
        // 相撞或者相遇
        for (int j = 0; j < ants.length - 1; j++) {
            if ((ants[j].isAlive && ants[j + 1].isAlive && ants[j].direction && !ants[j + 1].direction)
                    && (ants[j + 1].position == ants[j].position || (ants[j + 1].position - ants[j].position) == 1)) {
                ants[j].changeDirection();
                ants[j + 1].changeDirection();
            }
        }
    }

    public void initialize(int i) {
        // 初始化
        for (int j = 0; j < pole.length; j++)
            for (int k = 0; k < pole[j].length; k++)
                pole[j][k] = 0;
        for (int j = 0; j < ants.length; j++) {
            ants[j].setAlive(true);
            ants[j].setPosition(PlayRoom.pos[j]);
            ants[j].setDirection(PlayRoom.dirs[i][j]);
            if (ants[j].direction) antsDirection[0][j] = 1;
            else antsDirection[0][j] = 0;
        }
        time = 0;
    }

    public void situation(int i) {
        initialize(i);
        // 蚂蚁移动
        while (true) {
            // 记录数据， 初始化
            int deadAnt = 0;
            for (int j = 0; j < ants.length; j++) pole[time][ants[j].position] = 1;
            time++;
            // 移动
            for (int j = 0; j < ants.length; j++) {
                ants[j].creep();
                wood.isOut(ants[j]);
            }
            for (int j = 0; j < antsDirection[j].length; j++) {
                if (!ants[j].isAlive) {
                    deadAnt++;
                    antsDirection[time][j] = -2;
                } else if (ants[j].direction) antsDirection[time][j] = 1;
                else antsDirection[time][j] = 0;
            }

            checkCollision();
            // 是否结束
            if (deadAnt == 5) break;
        }
    }
}