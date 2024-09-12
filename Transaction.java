
import java.util.Date;

public class Transaction {
    /* attributes */
    private static int newTransactionID = 1;
    private final String transactionID;
    private final Item item;
    private final int quantity;
    private final String transactionType;
    private final Partner partner;
    private final Date date;

    /* constructors */
    public Transaction(Item item, int quantity, String transactionType, Partner partner, Date date) {
        transactionID = String.format("T%05d",newTransactionID++);
        this.item = item;
        this.quantity = quantity;
        this.transactionType = transactionType;
        this.partner = partner;
        this.date = date;
    }
    public Transaction(Item item, int quantity, String transactionType, Partner partner) {
        transactionID = String.format("T%05d",newTransactionID++);
        this.item = item;
        this.quantity = quantity;
        this.transactionType = transactionType;
        this.partner = partner;
        date = new Date();
    }

    /* getters */
    public String getTransactionID() { return transactionID; }
    public Item getItem() { return item; }
    public int getQuantity() { return quantity; }
    public String getTransactionType() { return transactionType; }
    public Partner getPartner() { return partner; }
    public Date getDate() { return date; }

    /* TESTING CODE */
    public static void main(String[] args) {
        Transaction a = new Transaction(new Item(null, null, null), 1, "a", new Supplier("a", "a", "a"), new Date());
        System.out.println(a.getTransactionID());
        Transaction b = new Transaction(new Item(null, null, null), 1, "a", new Supplier("a", "a", "a"));
        System.out.println(b.getTransactionID());
        System.out.println(b.date);
    }
}
