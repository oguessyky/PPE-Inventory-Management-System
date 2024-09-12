public class Inventory {
    /* attributes */
    private final Item item;
    private int quantity;

    /* constructors */
    public Inventory(Item item, int quantity) {
        this.item = item;
        this.quantity = quantity;
    }
    public Inventory(Item item) {
        this.item = item;
        this.quantity = 100;
    }

    /* getters */
    public Item getItem() { return item; }
    public int getQuantity() { return quantity; }

    /* quantity modifiers */
    public void addQuantity(int quantity) { this.quantity += quantity; }
    public void removeQuantity(int quantity) { this.quantity -= quantity; }
}
