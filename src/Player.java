import vegetables.Vegetables;

import java.util.HashMap;

public class Player {
    public int money = 2000;
    public String name;
    private HashMap<String, Integer> inventory = new HashMap<>();

    public Player(String name) {
        this.name = name;
    }
    public void addSeed(Vegetables vegetables) {
        String name = vegetables.name;

        int currentAmount = inventory.getOrDefault(name, 0);
        inventory.put(name, currentAmount + 1);
        System.out.println("Inventaire : " + name + " possède maintenant " + (currentAmount + 1) + " graines.");
    }

}
