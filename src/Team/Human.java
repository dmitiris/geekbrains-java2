package Team;

public class Human implements Competitor {
    private String name;

    private int maxRunDistance;
    private int maxJumpHeight;
    private int maxSwimDistance;

    private boolean active;

    @Override
    public boolean isOnDistance() {
        return active;
    }

    Human(String name) {
        this.name = name;
        this.maxRunDistance = 5000;
        this.maxJumpHeight = 30;
        this.maxSwimDistance = 200;
        this.active = true;
    }

    Human(String name, int...data) {
        this.name = name;
        this.active = true;
        if (data.length == 3) {
            this.maxRunDistance = data[0];
            this.maxJumpHeight = data[1];
            this.maxSwimDistance = data[2];
        }
        else if (data.length == 2) {
            this.maxRunDistance = data[0];
            this.maxJumpHeight = data[1];
        }
        else if (data.length == 1) {
            this.maxRunDistance = data[0];
        }
    }


    @Override
    public void run(int dist) {
        if (dist <= maxRunDistance) {
            System.out.println(name + " хорошо справился с кроссом");
        } else {
            System.out.println(name + " не справился с кроссом");
            active = false;
        }
    }

    @Override
    public void jump(int height) {
        if (height <= maxJumpHeight) {
            System.out.println(name + " удачно перепрыгнул через стену");
        } else {
            System.out.println(name + " не смог перепрыгнуть стену");
            active = false;
        }
    }

    @Override
    public void swim(int dist) {
        if (dist <= maxSwimDistance) {
            System.out.println(name + " отлично проплыл");
        } else {
            System.out.println(name + " не смог проплыть");
            active = false;
        }
    }

    @Override
    public String getName() {
        return this.name;
    }

    @Override
    public void info() {
        System.out.println(
                name + ": бег - " + maxRunDistance + ", прыжок - " + maxJumpHeight + ", плавание - " + maxSwimDistance
                );
    }
}