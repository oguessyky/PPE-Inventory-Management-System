import java.util.Comparator;
import java.util.function.Predicate;
public abstract class Partner {
    public enum Status {
        Active,
        Inactive
    }
    /* attributes */
    protected static int newPartnerCode;
    protected final String partnerCode;
    protected String name, address;
    protected Status status;

    /* Comparators for sorting */
    public static final Comparator<Partner> CodeComparator = Comparator.comparing(Partner::getPartnerCode);
    public static final Comparator<Partner> NameComparator = Comparator.comparing(Partner::getName);
    public static final Comparator<Partner> AddressComparator = Comparator.comparing(Partner::getAddress);

    /* Predicates for filtering */
    public static final Predicate<Partner> NameContains(String s) { return (partner -> partner.name.contains(s)); }
    public static final Predicate<Partner> AddressContains(String s) { return (partner -> partner.address.contains(s)); }

    /* constructor */
    public Partner(String partnerCodeFormat, String name, String address) {
        this.partnerCode = String.format(partnerCodeFormat,newPartnerCode++);
        this.name = name;
        this.address = address;
        this.status = Status.Active;
    }
    public Partner(String partnerCodeFormat, String name, String address, Status status) {
        this.partnerCode = String.format(partnerCodeFormat,newPartnerCode++);
        this.name = name;
        this.address = address;
        this.status = status;
    }

    /* getters */
    public String getPartnerCode() { return partnerCode; }
    public String getName() { return name; }
    public String getAddress() { return address; }

    /* setters */
    public void setName(String name) { this.name = name; }
    public void setAddress(String address) { this.address = address; }

    @Override
    public String toString() {
        return String.format("%s;%s;%s", partnerCode, name, address);
    }
}
