
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Date;
import java.util.ArrayList;
import java.util.Scanner;

public class Records {
    /* Storing records */
    public static ArrayList<User> userList = new ArrayList<>();
    public static ArrayList<Supplier> supplierList;
    public static ArrayList<Hospital> hospitalList;
    public static ArrayList<Item> itemList;
    public static ArrayList<Transaction> transactionList;

    /* Reading records from file */
    public static void readUsers(Scanner recordReader) {
        String[] parameters;
        while (recordReader.hasNextLine()) {
            parameters = recordReader.nextLine().split("[|]");
            UserType userType = parameters[3].equals("Admin") ? UserType.Admin : UserType.Staff;
            userList.add(new User(parameters[0],parameters[1],parameters[2],userType));
        }
        recordReader.close();
    }
    public static void readSuppliers(Scanner recordReader) {
        String[] parameters;
        while (recordReader.hasNextLine()) {
            parameters = recordReader.nextLine().split("[|]");
            supplierList.add(new Supplier(parameters[0],parameters[1],parameters[2]));
        }
        recordReader.close();
    }
    public static void readHospitals(Scanner recordReader) {
        String[] parameters;
        while (recordReader.hasNextLine()) {
            parameters = recordReader.nextLine().split("[|]");
            hospitalList.add(new Hospital(parameters[0],parameters[1],parameters[2]));
        }
        recordReader.close();
    }
    public static void readItems(Scanner recordReader) {
        String[] parameters;
        while (recordReader.hasNextLine()) {
            parameters = recordReader.nextLine().split("[|]");
            Supplier supplier = getSupplier(parameters[2]);
            int quantity = Integer.parseInt(parameters[4]);
            itemList.add(new Item(parameters[0], parameters[1], supplier, quantity));
        }
        recordReader.close();
    }
    public static void readTransactions(Scanner recordReader) {
        String[] parameters;
        while (recordReader.hasNextLine()) {
            parameters = recordReader.nextLine().split("[|]");
            Item item = getItem(parameters[0]);
            int quantity = Integer.parseInt(parameters[1]);
            TransactionType transactionType = parameters[2].equals("Distributed") ? TransactionType.Distributed : TransactionType.Received;
            Partner partner;
            if (transactionType == TransactionType.Distributed)
                partner = getHospital(parameters[3]);
            else
                partner = getSupplier(parameters[3]);
            Date date = new Date(Integer.parseInt(parameters[4]));
            transactionList.add(new Transaction(item, quantity, transactionType, partner, date));
        }
        recordReader.close();
    }

    public static void readRecords() throws IOException {
        File mainFolder = new File(".\\Records");
        File user = new File(".\\Records\\user.txt");
        File supplier = new File(".\\Records\\suppliers.txt");
        File hospital = new File(".\\Records\\hospitals.txt");
        File item = new File(".\\Records\\ppe.txt");
        File transaction = new File(".\\Records\\transactions.txt");
        if (!mainFolder.exists()) mainFolder.mkdirs();
        if (user.exists()) readUsers(new Scanner(user));
        else {
            user.createNewFile();
            /* GUI stuff */
        }
        if (supplier.exists()) readSuppliers(new Scanner(supplier));
        else {
            supplier.createNewFile();
            /* GUI stuff */
        }
        if (hospital.exists()) readHospitals(new Scanner(hospital));
        else {
            hospital.createNewFile();
            /* GUI stuff */
        }
        if (item.exists()) readItems(new Scanner(item));
        else {
            item.createNewFile();
            /* GUI stuff */
        }
        if (transaction.exists()) readTransactions(new Scanner(transaction));
        else transaction.createNewFile();
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

    public static void main(String[] args) throws IOException {
        File b = new File(".\\Records");
        b.mkdirs();
        File c = new File(".\\Records\\test.txt");
        b.createNewFile();
        PrintWriter a = new PrintWriter(c);
        a.print("12321");
        a.close();
        readUsers(new Scanner("afsdawfa|adadaad|ADawdasas|Admin\nasd|afsdawfa|adadaad|ADawdasas"));
    }
}
