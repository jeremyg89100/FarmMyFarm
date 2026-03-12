package animals;

public class Chicken extends Animal {
    public Chicken() {
        super("Poule", "Oeuf", 8,"Mais", 20, 18, 0, 20, "/img/poule.png");
    }

    public boolean feed(String foodGiven) {
        return this.food.equals(foodGiven);
    }
}
