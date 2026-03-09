package animals;

public class Pig extends Animal {
    public Pig() {
        super("Cochon", "Viande", 1,"Tout", 20, 60, 0, 130, "/img/cochon.png");
    }

    public boolean feed(String foodGiven) {
        if (foodGiven != null && !foodGiven.isEmpty()) {
            return true;
        } else return false;
    }
}
