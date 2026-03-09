package animals;

public class Sheep extends Animal {
    public Sheep() {
        super("Mouton", "Laine", 10, "Blé",  40, 24, 0, 22, "/img/mouton.png");
    }

    public boolean feed(String foodGiven) {
        if (foodGiven == null || foodGiven.isEmpty()) {
            return true;
        }
        return false;
    }
}

