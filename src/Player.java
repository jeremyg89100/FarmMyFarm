import animals.Animal;
import vegetables.Vegetables;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Player {
    public int money;
    public String name;
    private Vegetables vegetables;
    private HashMap<String, Integer> inventory = new HashMap<>();

    public Player(String name) {
        this.name = name;
        this.money = 2000000;
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
    public void setMoney(int money) {this.money = money;}

    public void setName(String name) {this.name = name;}

    public int getSeedCount(String vegetableSeedName) {
        return inventory.getOrDefault(vegetableSeedName, 0);
    }

    public String getName() { return this.name;}

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

    //Save
    public List<ItemCount> convertInventoryToData() {
        List<ItemCount> list = new ArrayList<>();

        for (String key : inventory.keySet()) {
            list.add(new ItemCount(key, inventory.get(key)));
        }
        return list;
    }

    public List<ItemCount> getInventoryList() {
        return convertInventoryToData();
    }

    //Restore
    public void restoreInventory(List<ItemCount> inventoryData) {
        this.inventory.clear();

        for (ItemCount item : inventoryData) {
            if (item.count > 0) {
                inventory.put(item.itemName, item.count);
            }
        }
    }
}
