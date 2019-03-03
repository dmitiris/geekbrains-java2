package Team;

class Cat extends Animal {
    Cat(String name) {
        super("Кот", name, 200, 20, 0);
    }

    Cat(String name, int...data) {
        super("Пес", name, 500, 5, 20);
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

}
