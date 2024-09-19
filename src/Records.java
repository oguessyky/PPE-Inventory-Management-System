
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.Date;
import java.util.ArrayList;
import java.util.Scanner;

public class Records {
    /* Storing records */
    private static final ArrayList<User> userList = new ArrayList<>();
    private static final ArrayList<Supplier> supplierList = new ArrayList<>();
    private static final ArrayList<Hospital> hospitalList = new ArrayList<>();
    private static final ArrayList<Item> itemList = new ArrayList<>();
    private static final ArrayList<Transaction> transactionList = new ArrayList<>();

    /* Record files */
    private static final File MAIN_FOLDER = new File("..\\Records");
    private static final File USER_FILE = new File("..\\Records\\user.txt");
    private static final File SUPPLIER_FILE = new File("..\\Records\\suppliers.txt");
    private static final File HOSPITAL_FILE = new File("..\\Records\\hospitals.txt");
    private static final File ITEM_FILE = new File("..\\Records\\ppe.txt");
    private static final File TRANSACTION_FILE = new File("..\\Records\\transactions.txt");

    /* Reading records from file */
    private static void readUsers() throws FileNotFoundException {
        String[] parameters;
        try (Scanner recordReader = new Scanner(USER_FILE)) {
            while (recordReader.hasNextLine()) {
                parameters = recordReader.nextLine().split("[|]");
                User.Type userType = parameters[3].equals("Admin") ? User.Type.Admin : User.Type.Staff;
                userList.add(new User(parameters[0],parameters[1],parameters[2],userType));
            }
        }
    }
    private static void readSuppliers() throws FileNotFoundException {
        String[] parameters;
        try (Scanner recordReader = new Scanner(SUPPLIER_FILE)) {
            while (recordReader.hasNextLine()) {
                parameters = recordReader.nextLine().split("[|]");
                supplierList.add(new Supplier(parameters[0],parameters[1],parameters[2]));
            }
        }
    }
    private static void readHospitals() throws FileNotFoundException {
        String[] parameters;
        try (Scanner recordReader = new Scanner(HOSPITAL_FILE)) {
            while (recordReader.hasNextLine()) {
                parameters = recordReader.nextLine().split("[|]");
                hospitalList.add(new Hospital(parameters[0],parameters[1],parameters[2]));
            }
        }
    }
    private static void readItems() throws FileNotFoundException {
        String[] parameters;
        try (Scanner recordReader = new Scanner(ITEM_FILE)) {
            while (recordReader.hasNextLine()) {
                parameters = recordReader.nextLine().split("[|]");
                Supplier supplier = getSupplier(parameters[2]);
                int quantity = Integer.parseInt(parameters[3]);
                itemList.add(new Item(parameters[0], parameters[1], supplier, quantity));
            }
        }
    }
    private static void readTransactions() throws FileNotFoundException  {
        String[] parameters;
        try (Scanner recordReader = new Scanner(TRANSACTION_FILE)) {
            while (recordReader.hasNextLine()) {
                parameters = recordReader.nextLine().split("[|]");
                Item item = getItem(parameters[0]);
                int quantity = Integer.parseInt(parameters[1]);
                Transaction.Type transactionType = parameters[2].equals("Distributed") ? Transaction.Type.Distributed : Transaction.Type.Received;
                Partner partner;
                if (transactionType == Transaction.Type.Distributed)
                    partner = getHospital(parameters[3]);
                else
                    partner = getSupplier(parameters[3]);
                Date date = new Date(Integer.parseInt(parameters[4]));
                transactionList.add(new Transaction(item, quantity, transactionType, partner, date));
            }
        }
    }
    public static void readRecords() throws FileNotFoundException {
        if (!MAIN_FOLDER.exists()) MAIN_FOLDER.mkdirs();
        if (USER_FILE.exists()) readUsers();
        if (SUPPLIER_FILE.exists()) readSuppliers();
        if (HOSPITAL_FILE.exists()) readHospitals();
        if (ITEM_FILE.exists()) readItems();
        if (TRANSACTION_FILE.exists()) readTransactions();
    }

    /* Filter, EVENTUALLY ADD OTHER PARAMETERS */
    public static User getUser(String userID) {
        for (User user : userList)
            if (user.getUserID().equals(userID))
                return user;
        return null;
    }
    public static Supplier getSupplier(String supplierCode) {
        for (Supplier supplier : supplierList)
            if (supplier.getPartnerCode().equals(supplierCode))
                return supplier;
        return null;
    }
    public static Hospital getHospital(String hospitalCode) {
        for (Hospital hospital : hospitalList)
            if (hospital.getPartnerCode().equals(hospitalCode))
                return hospital;
        return null;
    }
    public static Item getItem(String itemCode) {
        for (Item item : itemList)
            if (item.getItemCode().equals(itemCode))
                return item;
        return null;
    }
    public static Transaction getTransaction(String transactionID) {
        for (Transaction transaction : transactionList)
            if (transaction.getTransactionID().equals(transactionID))
                return transaction;
        return null;
    }

    public static void main(String[] args) throws IOException {
        /* Main code (throw this in some relevant class (GUI) instead) */
        readRecords();
        if (userList.isEmpty()) {
            /* Get first user data, GUI stuff */
        }
        if (supplierList.size() < 3) {
            /* Get min 3 supplier data, GUI stuff */
        }
        if (hospitalList.size() < 3) {
            /* Get min 3 hospital data, GUI stuff */
        }

        /* Get all item data, GUI stuff */
        if (true) {
        }


        /* ignore this

        File b = new File(".\\Records");
        b.mkdirs();
        File c = new File(".\\Records\\test.txt");

        PrintWriter a = new PrintWriter(c);
        a.print("12321");
        a.close();
        readUsers(new Scanner("afsdawfa|adadaad|ADawdasas|Admin\nasd|afsdawfa|adadaad|ADawdasas"));
        User t = getUser("afsdawfa");
        System.out.println(t.getName());
        */
    }
}
