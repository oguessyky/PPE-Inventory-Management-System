
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.function.Predicate;
import java.util.stream.Collectors;

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

    public static Comparator<Transaction> ItemComparator(Comparator<Item> itemComparator) { return Comparator.comparing(Transaction::getItem,itemComparator); }
    public static Comparator<Transaction> PartnerComparator(Comparator<Partner> partnerComparator) { return Comparator.comparing(Transaction::getPartner,partnerComparator); }

    /* Predicates for filtering */
    public static final Predicate<Transaction> IDContains(String s) { return (transaction -> transaction.transactionID.contains(s)); }
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

    /* display text */
    @Override
    public String toString() {
        return String.format("%s;%d;%s;%s;%d", item.getItemCode(), quantity, transactionType, partner.getPartnerCode(), date.getTime());
    }

    /* record storing text */
    public String toRecordText() {
        return String.format("%s;%d;%s;%s;%d", item.getItemCode(), quantity, transactionType, partner.getPartnerCode(), date.getTime());
    }

    public static class Summary {

        private final Item item;
        private final Partner partner;
        private final Type type;
        private int totalQuantity, count;

        private Summary(Item item, Partner partner, Type type) {
            this.item = item;
            this.partner = partner;
            this.type = type;
            totalQuantity = 0;
            count = 0;
        }

        public Item getItem() { return item; }
        public Partner getPartner() { return partner; }
        public Type getType() { return type; }
        public int getTotalQuantity() { return totalQuantity; }
        public double getAverageQuantity() { return totalQuantity/count; }
        public int getCount() { return count; }

        public void add(int quantity) {
            totalQuantity += quantity;
            count++;
        }

        public static final Comparator<Summary> ItemComparator = Comparator.comparing(Summary::getItem,Item.CodeComparator);
        public static final Comparator<Summary> PartnerComparator = Comparator.comparing(Summary::getPartner,Partner.CodeComparator);
        public static final Comparator<Summary> TypeComparator = Comparator.comparing(Summary::getType);
        public static final Comparator<Summary> TotalComparator = Comparator.comparing(Summary::getTotalQuantity);
        public static final Comparator<Summary> AverageComparator = Comparator.comparing(Summary::getAverageQuantity);
        public static final Comparator<Summary> CountComparator = Comparator.comparing(Summary::getCount);

        public static final Comparator<Summary> ItemComparator(Comparator<Item> comparator) { return Comparator.comparing(Summary::getItem,comparator); }
        public static final Comparator<Summary> PartnerComparator(Comparator<Partner> comparator) { return Comparator.comparing(Summary::getPartner,comparator); }

        public static ArrayList<Summary> getSummaryList(ArrayList<Transaction> transactionList) {
            ArrayList<Summary> summaryList = new ArrayList<>();
            for (Transaction transaction : transactionList) {
                Summary newSummary = null;
                for (Summary summary : summaryList) {
                    if (summary.item == transaction.item && summary.partner == transaction.partner) {
                        newSummary = summary;
                        break;
                    }
                }
                if (newSummary == null) {
                    newSummary = new Summary(transaction.item, transaction.partner,transaction.transactionType);
                    summaryList.add(newSummary);
                }
                newSummary.add(transaction.quantity);
            }
            return summaryList;
        }

        public static ArrayList<Summary> getSummaryList(ArrayList<Transaction> transactionList, Comparator<Summary> sorter) {
            ArrayList<Summary> summaryList = new ArrayList<>();
            for (Transaction transaction : transactionList) {
                Summary newSummary = null;
                for (Summary summary : summaryList) {
                    if (summary.item == transaction.item && summary.partner == transaction.partner) {
                        newSummary = summary;
                        break;
                    }
                }
                if (newSummary == null) {
                    newSummary = new Summary(transaction.item, transaction.partner,transaction.transactionType);
                    summaryList.add(newSummary);
                }
                newSummary.add(transaction.quantity);
            }
            summaryList.sort(sorter);
            return summaryList;
        }
    }
}
