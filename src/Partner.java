public abstract class Partner {
    /* attributes */
    protected final String partnerCode;
    protected String name, address;

    /* constructor */
    public Partner(String partnerCode, String name, String address) {
        this.partnerCode = partnerCode;
        this.name = name;
        this.address = address;
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
        return String.format("%s|%s|%s", partnerCode, name, address);
    }
}
