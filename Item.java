public class Item {
    /* attributes */
    private String itemCode, name;
    
    /* constructor */
    public Item(String itemCode, String name) {
        this.itemCode = itemCode;
        this.name = name;
    }

    /* getters */
    public String getItemCode() { return itemCode; }
    public String getName() { return name; }

    /* setters */
    public void setItemCode(String itemCode) { this.itemCode = itemCode; }
    public void setName(String name) { this.name = name; }
 }
