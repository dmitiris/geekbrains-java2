package Team;

public class Team {
    private String title = "Звёздочка";
    private Competitor[] members;

    public Team () {
        this.members = new Competitor[] {
                new Human("Боб"), new Cat("Барсик"),
                new Dog("Бобик"), new Dog("Шарик",500, 5, 100),

        };
    }

    public Team (Competitor[] members) {
        this.members = members;
    }

    public Team (String title, Competitor[] members) {
        this.title = title;
        this.members = members;
    }

    // метод для вывода информации о членах команды, прошедших дистанцию
    public void showResults() {
        for (Competitor competitor : members){
            if (competitor.isOnDistance()) {
                System.out.println(competitor.getName() + " прошёл дистанцию");
            }
        }
    }

    // метод вывода информации обо всех членах команды
    public void showMembers() {
        System.out.println("Команда: " + title);
        for (Competitor c : members){
            c.info();
        }
    }

    public Competitor[] getMembers() {
        return this.members;
    }
}
