package animals;

 public abstract class Animal {
     public String name;
     public String resource;
     public int life;
     public String food;
     public int buyingCost;
     public int growthTime;
     public int currentGrowth = 0;
     public int sellingPrice;
     public String imgPath;

    public Animal(String name, String resource, int life, String food, int buyingCost, int growthTime, int currentGrowth, int sellingPrice, String imgPath) {
        this.name = name;
        this.resource = resource;
        this.life = life;
        this.food = food;
        this.buyingCost = buyingCost;
        this.growthTime = growthTime;
        this.currentGrowth = currentGrowth;
        this.sellingPrice = sellingPrice;
        this.imgPath = imgPath;
    }

    public String getResource() { return resource; }
    public String getName() { return name; };
    public int getBuyingCost() { return buyingCost; }
    public int getSellingPrice() { return sellingPrice; }

     public abstract boolean feed(String foodGiven);
}

