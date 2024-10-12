public class Hospital extends Partner {
    private static int newHospitalCode;
    public Hospital(String name, String address) {
        super(String.format("H%03d", ++newHospitalCode), name, address);
    }
    public Hospital(String name, String address, Status status) {
        super(String.format("H%03d", ++newHospitalCode), name, address, status);
    }
}
