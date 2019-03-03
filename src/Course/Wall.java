package Course;

import Team.Competitor;

public class Wall extends Obstacle {
    private int height;

    Wall(int height) {
        this.height = height;
    }

    @Override
    public void doIt(Competitor competitor) {
        competitor.jump(height);
    }
}
