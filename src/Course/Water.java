package Course;

import Team.Competitor;

public class Water extends Obstacle {
    private int length;

    Water(int length) {
        this.length = length;
    }

    @Override
    public void doIt(Competitor competitor) {
        competitor.swim(length);
    }
}