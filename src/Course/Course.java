package Course;

import Team.Team;
import Team.Competitor;

public class Course {
    private Obstacle[] obstacles;

    public Course() {
        this.obstacles = new Obstacle[] {
                new Cross(80), new Wall(2),
                new Wall(1), new Cross(120),
                new Water(100)
        };
    }

    public Course (Obstacle...obstacles) {
        this.obstacles = obstacles;
    }

    public void doIt (Team team) {
        for (Obstacle obstacle: obstacles){
            for (Competitor competitor: team.getMembers()) {
                obstacle.doIt(competitor);
            }
        }
    }
}
