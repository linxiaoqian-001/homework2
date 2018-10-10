package ants;

public class Ant {

    private int velocity;
    protected int position;
    protected boolean direction;  // true向右, false向左
    protected boolean isAlive;

    public Ant() {
        this.velocity = 1;
        this.isAlive = true;
    }

    public void setPosition(int position) {
        this.position = position;
    }

    public int getPosition() {
        return position;
    }

    public void setDirection(boolean direction) {
        this.direction = direction;
    }

    public boolean getDirection() {
        return direction;
    }

    public void setAlive(boolean isAlive) {
        this.isAlive = isAlive;
    }

    public void changeDirection() {
        direction = !direction;
    }

    //在面板上展示蚂蚁的爬行方向
    public String displayDirection() {
        if (this.direction) {
            return "right";
        } else {
            return "left";
        }
    }

    public void creep() {
        if (isAlive) {
            if (direction) {
                position += velocity;
            } else {
                position -= velocity;
            }

        }
    }
}