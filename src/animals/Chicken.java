package animals;

public class Chicken extends Animal {
    public Chicken() {
        super("Poule", "Oeuf", 8,"Corn", 20, 30, 0, 20, "/img/poule.png");
    }

    public boolean feed(String foodGiven) {
        if (this.food.equals(foodGiven)) {
            return true;
        } else return false;
    }
}
