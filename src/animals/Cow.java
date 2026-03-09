package animals;

public class Cow extends Animal {
    public Cow() {
        super("Vache", "Lait", 12, "Blé", 40, 3, 0, 24, "/img/vache.png");
    }

    public boolean feed(String foodGiven) {
        if (foodGiven == null || foodGiven.isEmpty()) {
            return true;
        }
        return false;
    }
}
