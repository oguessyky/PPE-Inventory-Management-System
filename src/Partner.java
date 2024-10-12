import java.util.Comparator;
import java.util.function.Predicate;
public abstract class Partner {
    public enum Status {
        Active,
        Inactive
    }
    /* attributes */
    protected final String partnerCode;
    protected String name, address;
    protected Status status;

    /* Comparators for sorting */
    public static final Comparator<Partner> CodeComparator = Comparator.comparing(Partner::getPartnerCode);
    public static final Comparator<Partner> NameComparator = Comparator.comparing(Partner::getName);
    public static final Comparator<Partner> AddressComparator = Comparator.comparing(Partner::getAddress);

    /* Predicates for filtering */
    public static final Predicate<Partner> CodeContains(String s) { return (partner -> partner.partnerCode.contains(s)); }
    public static final Predicate<Partner> NameContains(String s) { return (partner -> partner.name.contains(s)); }
    public static final Predicate<Partner> AddressContains(String s) { return (partner -> partner.address.contains(s)); }
    public static final Predicate<Partner> IsActive() { return (partner -> partner.status == Status.Active); }

    /* constructor */
    public Partner(String partnerCode, String name, String address) {
        this.partnerCode = partnerCode;
        this.name = name;
        this.address = address;
        this.status = Status.Active;
    }
    public Partner(String partnerCode, String name, String address, Status status) {
        this.partnerCode = partnerCode;
        this.name = name;
        this.address = address;
        this.status = status;
    }

    /* getters */
    public String getPartnerCode() { return partnerCode; }
    public String getName() { return name; }
    public String getAddress() { return address; }
    public Status getStatus() { return status; }

    /* setters */
    public void setName(String name) { this.name = name; }
    public void setAddress(String address) { this.address = address; }
    public void setInactive() { this.status = Status.Inactive; }

    /* display text */
    @Override
    public String toString() {
        return String.format("%s (%s)", partnerCode, name);
    }

    /* record storing text */
    public String toRecordText() {
        return String.format("%s;%s;%s", name, address, status);
    }

}
