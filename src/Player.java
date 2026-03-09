import animals.Animal;
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

    public void addAnimal(Animal animal) {
        String name = animal.name;

        int currentAmount = inventory.getOrDefault(name, 0);
        inventory.put(name, currentAmount +1);
    }

    public void withdrawAnimal(String name) {
        int currentAmount = inventory.getOrDefault(name, 0);
        if (currentAmount > 0) {
            inventory.put(name, currentAmount - 1);
        }
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

    public int getResourceCount(String resourceName) {return inventory.getOrDefault(resourceName, 0);}

    public int getAnimalCount(String animalName) {
        return inventory.getOrDefault(animalName, 0);
    }

    public int getVegetableCount(String vegetableName) { return inventory.getOrDefault(vegetableName, 0);}

    public int getMoney() {return this.money;}

    public boolean hasSeed(String seedName) {
        return inventory.getOrDefault(seedName, 0) > 0;
    }

    public boolean hasAnimal(String animalName) {return inventory.getOrDefault(animalName, 0) > 0;}

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

    public void addResourcesAfterGrowth(Animal animal) {
        String resource = animal.resource;
        int currentAmount = inventory.getOrDefault(resource, 0);
        inventory.put(resource, currentAmount + 1);
        System.out.println("Inventaire : " + resource + " possède maintenant " + (currentAmount + 1));
    }
}
