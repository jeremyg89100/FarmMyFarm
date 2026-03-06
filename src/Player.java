import vegetables.Vegetables;

import java.util.HashMap;

public class Player {
    public int money = 2000;
    public String name;
    private Vegetables vegetables;
    private HashMap<String, Integer> inventory = new HashMap<>();

    public Player(String name) {
        this.name = name;
    }

    public void addSeed(Vegetables vegetables) {
        String name = vegetables.seedName;

        int currentAmount = inventory.getOrDefault(name, 0);
        inventory.put(name, currentAmount + 1);
        System.out.println("Inventaire : " + name + " possède maintenant " + (currentAmount + 1) + " graines.");
    }

    public void withdrawSeed(String name) {
        int currentAmount = inventory.getOrDefault(name, 0);
        if (currentAmount > 0) {
            inventory.put(name, currentAmount - 1);
        }
    }

    public int getSeedCount(String vegetableSeedName) {
        return inventory.getOrDefault(vegetableSeedName, 0);
    }

    public int getVegetableCount(String vegetableName) { return inventory.getOrDefault(vegetableName, 0);}

    public boolean hasSeed(String seedName) {
        return inventory.getOrDefault(seedName, 0) > 0;
    }

    public void useSeed(String seedName) {
        int seedCount = inventory.getOrDefault(seedName, 0);

        if (seedCount > 0) {
            inventory.put(seedName, seedCount -1);
        }
    }

    public void addVegetablesAfterGrowth(Vegetables vegetables) {
        String name = vegetables.name;
        int currentAmount = inventory.getOrDefault(name, 0);
        inventory.put(name, currentAmount + 1);
        System.out.println("Inventaire : " + name + " possède maintenant " + (currentAmount + 1));
    }

    public void removeVegetable(String name) {
        int currentAmount = inventory.getOrDefault(name, 0);
        if (currentAmount > 0) {
            inventory.put(name, currentAmount - 1);
            System.out.println("Vente effectué, votre solde : " + money);
        } else System.out.println("Vous n'avez pas ce produit à vendre");
    }
}
