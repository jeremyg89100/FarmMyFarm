package vegetables;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.image.Image;
import javafx.util.Duration;

public abstract class Vegetables {
    public String name;
    public String seedName;
    public int buyingCost;
    public int growthTime;
    public int currentGrowth = 0;
    public int sellingPrice;
    public String imgPath;

    public Vegetables (String name, String seedName, int buyingCost, int growthTime, int currentGrowth, int sellingPrice, String imgPath) {
        this.name = name;
        this.seedName = seedName;
        this.buyingCost = buyingCost;
        this.growthTime = growthTime;
        this.currentGrowth = currentGrowth;
        this.sellingPrice = sellingPrice;
        this.imgPath = imgPath;
    }

    public String getSeedName() { return seedName; }
    public String getName() { return name; };
    public int getBuyingCost() { return buyingCost; }
    public int getSellingPrice() { return sellingPrice; }
}
