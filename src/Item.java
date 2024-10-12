import java.util.Comparator;
import java.util.function.Predicate;

public class Item {
    /* attributes */
    private String itemCode, name;
    private Supplier supplier;
    private int quantity = 100;

    /* Comparators for sorting */
    public static final Comparator<Item> CodeComparator = Comparator.comparing(Item::getItemCode);
    public static final Comparator<Item> NameComparator = Comparator.comparing(Item::getName);
    public static final Comparator<Item> SupplierComparator = Comparator.comparing(Item::getSupplier,Supplier.CodeComparator);
    public static final Comparator<Item> QuantityComparator = Comparator.comparing(Item::getQuantity);

    /* Predicates for filtering */
    public static final Predicate<Item> NameContains(String s) { return (item -> item.name.contains(s)); }
    public static final Predicate<Item> WithMinQuantity(int quantity) { return (item -> item.quantity >= quantity); }
    public static final Predicate<Item> WithMaxQuantity(int quantity) { return (item -> item.quantity <= quantity); }
    public static final Predicate<Item> WithQuantityBetween(int minQuantity, int maxQuantity) { return (item -> (minQuantity <= item.quantity && item.quantity <= maxQuantity)); }
    public static final Predicate<Item> WithSupplier(Supplier supplier) { return (item -> item.supplier == supplier); }

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

    /* display text */
    @Override
    public String toString() {
        return String.format("%s (%s)", itemCode, name);
    }

    /* record storing text */
    public String toRecordText() {
        return String.format("%s;%s;%s;%d", itemCode, name, supplier.getPartnerCode(), quantity);
    }
}
