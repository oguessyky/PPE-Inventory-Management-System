public class Item {
    /* attributes */
    private String itemCode, name;
    private Supplier supplier;
    
    /* constructors */
    public Item(String itemCode, String name, Supplier supplier) {
        this.itemCode = itemCode;
        this.name = name;
        this.supplier = supplier;
    }

    /* getters */
    public String getItemCode() { return itemCode; }
    public String getName() { return name; }
    public Supplier getSupplier() { return supplier; }

    /* setters */
    public void setItemCode(String itemCode) { this.itemCode = itemCode; }
    public void setName(String name) { this.name = name; }
    public void setSupplier(Supplier supplier) { this.supplier = supplier; }

 }
