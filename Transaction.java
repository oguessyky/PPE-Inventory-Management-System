
import java.time.LocalDate;

public class Transaction {
    /* attributes */
    private static int newTransactionID = 1;
    private final String transactionID;
    private final Item item;
    private final int quantity;
    private final String transactionType;
    private final Partner partner;
    private final LocalDate date;

    /* constructor */
    public Transaction(Item item, int quantity, String transactionType, Partner partner, LocalDate date) {
        transactionID = "tset"; /* do auto incrementing */
        this.item = item;
        this.quantity = quantity;
        this.transactionType = transactionType;
        this.partner = partner;
        this.date = date;
    }

    /* getters */
    public String getTransactionID() { return transactionID; }
    public Item getItem() { return item; }
    public int getQuantity() { return quantity; }
    public String getTransactionType() { return transactionType; }
    public Partner getPartner() { return partner; }
    public LocalDate getDate() { return date; }
}
