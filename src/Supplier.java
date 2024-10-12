public class Supplier extends Partner {
    public Supplier(String name, String address) {
        super("S%03d", name, address);
    }
    public Supplier(String name, String address, Status status) {
        super("S%03d", name, address, status);
    }
}
