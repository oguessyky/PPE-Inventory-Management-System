public class Supplier extends Partner {
    private static int newSupplierCode;
    public Supplier(String name, String address) {
        super(String.format("S%03d", ++newSupplierCode), name, address);
    }
    public Supplier(String name, String address, Status status) {
        super(String.format("S%03d", ++newSupplierCode), name, address, status);
    }
}
