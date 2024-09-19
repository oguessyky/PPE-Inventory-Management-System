
import java.util.Date;

public class Transaction {
    /* attributes */
    public enum Type {
        Received,
        Distributed;
    }
    private static int newTransactionID = 1;
    private final String transactionID;
    private final Item item;
    private final int quantity;
    private final Type transactionType;
    private final Partner partner;
    private final Date date;

    /* constructors */
    /* when reading record */
    public Transaction(Item item, int quantity, Type transactionType, Partner partner, Date date) {
        transactionID = String.format("T%05d",newTransactionID++);
        this.item = item;
        this.quantity = quantity;
        this.transactionType = transactionType;
        this.partner = partner;
        this.date = date;
    }
    /* when recording new transactions */
    public Transaction(Item item, int quantity, Type transactionType, Partner partner) {
        transactionID = String.format("T%05d",newTransactionID++);
        this.item = item;
        this.quantity = quantity;
        this.transactionType = transactionType;
        this.partner = partner;
        date = new Date();
        if (transactionType == Type.Received)
            item.addQuantity(quantity);
        else
            item.removeQuantity(quantity);
    }

    /* getters */
    public String getTransactionID() { return transactionID; }
    public Item getItem() { return item; }
    public int getQuantity() { return quantity; }
    public Type getTransactionType() { return transactionType; }
    public Partner getPartner() { return partner; }
    public Date getDate() { return date; }

    @Override
    public String toString() {
        return String.format("%s|%d|%s|%s|%d", item.getItemCode(), quantity, transactionType, partner.getPartnerCode(), date.getTime());
    }

    /* TESTING CODE */
    public static void main(String[] args) {
        Transaction a = new Transaction(new Item(null, null, null), 1, Type.Received, new Supplier("a", "a", "a"), new Date());
        System.out.println(a.getTransactionID());
        Transaction b = new Transaction(new Item(null, null, null), 1, Type.Distributed, new Supplier("a", "a", "a"));
        System.out.println(b.getPartner().getPartnerCode());
        System.out.println(b);
    }
}
