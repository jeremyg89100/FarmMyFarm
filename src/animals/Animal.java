package animals;

 public abstract class Animal {
     public String name;
     public String resource;
     public int life;
     public int buyingCost;
     public int growthTime;
     public int currentGrowth = 0;
     public int sellingPrice;
     public String imgPath;

    public Animal(String name, String resource, int life, int buyingCost, int growthTime, int currentGrowth, int sellingPrice, String imgPath) {
        this.name = name;
        this.resource = resource;
        this.life = life;
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
}

