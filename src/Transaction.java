
import java.util.Date;

public class Transaction {
    /* attributes */
    private static int newTransactionID = 1;
    private final String transactionID;
    private final Item item;
    private final int quantity;
    private final TransactionType transactionType;
    private final Partner partner;
    private final Date date;

    /* constructors */
    /* when reading record */
    public Transaction(Item item, int quantity, TransactionType transactionType, Partner partner, Date date) {
        transactionID = String.format("T%05d",newTransactionID++);
        this.item = item;
        this.quantity = quantity;
        this.transactionType = transactionType;
        this.partner = partner;
        this.date = date;
    }
    /* when recording new transactions */
    public Transaction(Item item, int quantity, TransactionType transactionType, Partner partner) {
        transactionID = String.format("T%05d",newTransactionID++);
        this.item = item;
        this.quantity = quantity;
        this.transactionType = transactionType;
        this.partner = partner;
        date = new Date();
        if (transactionType == TransactionType.Received)
            item.addQuantity(quantity);
        else
            item.removeQuantity(quantity);
    }

    /* getters */
    public String getTransactionID() { return transactionID; }
    public Item getItem() { return item; }
    public int getQuantity() { return quantity; }
    public TransactionType getTransactionType() { return transactionType; }
    public Partner getPartner() { return partner; }
    public Date getDate() { return date; }

    /* TESTING CODE */
    public static void main(String[] args) {
        Transaction a = new Transaction(new Item(null, null, null), 1, TransactionType.Received, new Supplier("a", "a", "a"), new Date());
        System.out.println(a.getTransactionID());
        Transaction b = new Transaction(new Item(null, null, null), 1, TransactionType.Distributed, new Supplier("a", "a", "a"));
        System.out.println(b.getPartner().getPartnerCode());
        System.out.println(b.date);
    }
}
