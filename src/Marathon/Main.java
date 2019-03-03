package Marathon;

import Course.Course;
import Team.Team;

public class Main{
    public static  void main(String[] args) {
        Course c = new Course();
        Team team = new Team();
        team.showMembers();
        c.doIt(team);
        System.out.println("Финал");
        team.showResults();
    }
}