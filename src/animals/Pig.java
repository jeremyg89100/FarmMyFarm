package animals;

public class Pig extends Animal {
    public Pig() {
        super("Cochon", "Viande", 1,"Tout", 20, 60, 0, 130, "/img/cochon.png");
    }

    public boolean feed(String foodGiven) {
        return foodGiven != null && !foodGiven.isEmpty();
    }
}
