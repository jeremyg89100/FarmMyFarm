public class ItemCount {
    public String itemName;
    public int count;

    // Constructor needs to be empty for Jackson
    public ItemCount() {}

    public ItemCount(String itemName, int count) {
        this.itemName = itemName;
        this.count = count;
    }
}
