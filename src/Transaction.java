
import java.util.Comparator;
import java.util.Date;
import java.util.function.Predicate;

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

    /* Comparators for sorting */
    public static final Comparator<Transaction> IDComparator = Comparator.comparing(Transaction::getTransactionID);
    public static final Comparator<Transaction> ItemComparator = Comparator.comparing(Transaction::getItem,Item.CodeComparator);
    public static final Comparator<Transaction> QuantityComparator = Comparator.comparing(Transaction::getQuantity);
    public static final Comparator<Transaction> TypeComparator = Comparator.comparing(Transaction::getTransactionType);
    public static final Comparator<Transaction> PartnerComparator = Comparator.comparing(Transaction::getPartner,Partner.CodeComparator);
    public static final Comparator<Transaction> DateComparator = Comparator.comparing(Transaction::getDate);

    /* Predicates for filtering */
    public static final Predicate<Transaction> WithMinQuantity(int quantity) { return (transaction -> transaction.quantity >= quantity); }
    public static final Predicate<Transaction> WithMaxQuantity(int quantity) { return (transaction -> transaction.quantity <= quantity); }
    public static final Predicate<Transaction> WithQuantityBetween(int minQuantity, int maxQuantity) { return (transaction -> (minQuantity <= transaction.quantity && transaction.quantity <= maxQuantity)); }
    public static final Predicate<Transaction> WithPartner(Partner partner) { return (transaction -> transaction.partner == partner); }
    public static final Predicate<Transaction> WithItem(Item item) { return (transaction -> transaction.item == item); }
    public static final Predicate<Transaction> WithType(Type transactionType) { return (transaction -> transaction.transactionType == transactionType); }
    public static final Predicate<Transaction> WithDateAfter(Date date) { return (transaction -> transaction.date.after(date)); }
    public static final Predicate<Transaction> WithDateBefore(Date date) { return (transaction -> transaction.date.before(date)); }
    public static final Predicate<Transaction> WithDateBetween(Date dateAfter, Date dateBefore) { return (transaction -> transaction.date.after(dateAfter) && transaction.date.before(dateBefore)); }

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
        return String.format("%s;%d;%s;%s;%d", item.getItemCode(), quantity, transactionType, partner.getPartnerCode(), date.getTime());
    }
}
