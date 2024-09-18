public class Item {
    /* attributes */
    private String itemCode, name;
    private Supplier supplier;
    private int quantity;
    
    /* constructors */
    public Item(String itemCode, String name, Supplier supplier, int quantity) {
        this.itemCode = itemCode;
        this.name = name;
        this.supplier = supplier;
        this.quantity = quantity;
    }
    public Item(String itemCode, String name, Supplier supplier) {
        this.itemCode = itemCode;
        this.name = name;
        this.supplier = supplier;
        this.quantity = 100;
    }

    /* getters */
    public String getItemCode() { return itemCode; }
    public String getName() { return name; }
    public Supplier getSupplier() { return supplier; }
    public int getQuantity() { return quantity; }

    /* setters */
    public void setItemCode(String itemCode) { this.itemCode = itemCode; }
    public void setName(String name) { this.name = name; }
    public void setSupplier(Supplier supplier) { this.supplier = supplier; }

    /* quantity modifiers */
    public void addQuantity(int quantity) { this.quantity += quantity; }
    public void removeQuantity(int quantity) { this.quantity -= quantity; }
}
