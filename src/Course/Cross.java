package Course;

import Team.Competitor;

public class Cross extends Obstacle {
    private int length;

    Cross(int length) {
        this.length = length;
    }

    @Override
    public void doIt(Competitor competitor) {
        competitor.run(length);
    }
}