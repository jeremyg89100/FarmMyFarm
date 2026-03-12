package animals;

public class Sheep extends Animal {
    public Sheep() {
        super("Mouton", "Laine", 10, "Blé",  40, 12, 0, 22, "/img/mouton.png");
    }

    public boolean feed(String foodGiven) {
        return this.food.equals(foodGiven);
    }
}

