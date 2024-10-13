
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

        private static final ArrayList<SummaryKey> summaryKeys = new ArrayList<>();
        public static class SummaryKey {
            private final Item item;
            private final Partner partner;
            public SummaryKey(Item item, Partner partner) {
                this.item = item;
                this.partner = partner;
            }
            public Item getItem() { return item; }
            public Partner getPartner() { return partner; }
        }

        public static class SummaryContent {
            private int totalQuantity, count;
            public SummaryContent() {
                totalQuantity = 0;
                count = 0;
            }
            public SummaryContent(int totalQuantity, int count) {
                this.totalQuantity = totalQuantity;
                this.count = count;
            }
            public void add(int quantity) {
                totalQuantity += quantity;
                count++;
            }
            public int getTotalQuantity() { return totalQuantity; }
            public double getAverageQuantity() { return totalQuantity/count; }
            public int getCount() { return count; }
        }

        private static SummaryKey getSummaryKey(Item item, Partner partner) {
            for (SummaryKey summaryKey : summaryKeys) {
                if (summaryKey.item == item && summaryKey.partner == partner) return summaryKey;
            }
            SummaryKey summaryKey = new SummaryKey(item, partner);
            summaryKeys.add(summaryKey);
            return summaryKey;
        }

        private HashMap<SummaryKey,SummaryContent> summaryList = new HashMap<>();

        public Summary(ArrayList<Transaction> transactionList) {
            SummaryKey summaryKey;
            for (Transaction transaction : transactionList) {
                summaryKey = getSummaryKey(transaction.item, transaction.partner);
                if (summaryList.containsKey(summaryKey)) {
                    summaryList.get(summaryKey).add(transaction.quantity);
                } else {
                    summaryList.put(summaryKey, new SummaryContent(transaction.quantity,1));
                }
            }
        }

        public static final Comparator<SummaryContent> totalComparator = Comparator.comparing(SummaryContent::getTotalQuantity);
        public static final Comparator<SummaryContent> averageComparator = Comparator.comparing(SummaryContent::getAverageQuantity);
        public static final Comparator<SummaryContent> countComparator = Comparator.comparing(SummaryContent::getCount);

        public Set<Map.Entry<SummaryKey,SummaryContent>> getSummaryList() {
            return summaryList.entrySet();
        }

        public Set<Map.Entry<SummaryKey,SummaryContent>> getSummaryList(Comparator<SummaryContent> comparator) {
            return summaryList.entrySet()
                .stream()
                .sorted(Map.Entry.comparingByValue(comparator))
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue, (e1, e2) -> e2, HashMap::new))
                .entrySet();
        }
    }

    public static void main(String[] args) {
        Map<Object[],Integer> test = new HashMap<>();
        String[] a = new String[] {"test"};
        String[] b = new String[] {"test"};
        
        test.put(a,1);
        test.put(b,2);
        System.out.println(test);
    }
}
