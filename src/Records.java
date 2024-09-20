
import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.sql.Date;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Scanner;
import java.util.function.Predicate;
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
                User.Type userType = User.Type.valueOf(parameters[3]);
                userList.add(new User(parameters[0],parameters[1],parameters[2],userType));
            }
        }
    }
    private static void readSuppliers() throws FileNotFoundException {
        String[] parameters;
        try (Scanner recordReader = new Scanner(SUPPLIER_FILE)) {
            while (recordReader.hasNextLine()) {
                parameters = recordReader.nextLine().split("[|]");
                supplierList.add(new Supplier(parameters[0],parameters[1]));
            }
        }
    }
    private static void readHospitals() throws FileNotFoundException {
        String[] parameters;
        try (Scanner recordReader = new Scanner(HOSPITAL_FILE)) {
            while (recordReader.hasNextLine()) {
                parameters = recordReader.nextLine().split("[|]");
                hospitalList.add(new Hospital(parameters[0],parameters[1]));
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
                Transaction.Type transactionType = Transaction.Type.valueOf(parameters[2]);
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
    public static void readRecords() {
        try { readUsers(); } catch (FileNotFoundException e) {}
        try { readSuppliers(); } catch (FileNotFoundException e) {}
        try { readHospitals(); } catch (FileNotFoundException e) {}
        try { readItems(); } catch (FileNotFoundException e) {}
        try { readTransactions(); } catch (FileNotFoundException e) {}
    }

    /* Writing records into file */
    private static void updateUsers() throws FileNotFoundException {
        try (PrintWriter recordWriter = new PrintWriter(USER_FILE)) {
            userList.forEach(user -> recordWriter.println(user));
        }
    }
    private static void updateSuppliers() throws FileNotFoundException {
        try (PrintWriter recordWriter = new PrintWriter(SUPPLIER_FILE)) {
            supplierList.forEach(supplier -> recordWriter.println(supplier));
        }
    }
    private static void updateHospitals() throws FileNotFoundException {
        try (PrintWriter recordWriter = new PrintWriter(HOSPITAL_FILE)) {
            hospitalList.forEach(hospital -> recordWriter.println(hospital));
        }
    }
    private static void updateItems() throws FileNotFoundException {
        try (PrintWriter recordWriter = new PrintWriter(ITEM_FILE)) {
            itemList.forEach(item -> recordWriter.println(item));
        }
    }
    private static void updateTransactions() throws FileNotFoundException {
        try (PrintWriter recordWriter = new PrintWriter(TRANSACTION_FILE)) {
            transactionList.forEach(transaction -> recordWriter.println(transaction));
        }
    }
    public static void updateRecords() {
        if (!MAIN_FOLDER.exists()) MAIN_FOLDER.mkdirs();
        try { updateUsers(); } catch (FileNotFoundException e) {}
        try { updateSuppliers(); } catch (FileNotFoundException e) {}
        try { updateHospitals(); } catch (FileNotFoundException e) {}
        try { updateItems(); } catch (FileNotFoundException e) {}
        try { updateTransactions(); } catch (FileNotFoundException e) {}
    }

    /* Getters, filters and sorters */
    public static ArrayList<User> getUserList() { return userList; }
    public static ArrayList<User> getUserList(Comparator<User> sorter) {
        ArrayList<User> outputList = new ArrayList<>(userList);
        outputList.sort(sorter);
        return outputList;
    }
    public static ArrayList<User> getUserList(Predicate<User> filter) {
        ArrayList<User> outputList = new ArrayList<>(userList);
        outputList.removeIf(Predicate.not(filter));
        return outputList;
    }
    public static ArrayList<User> getUserList(Predicate<User> filter, Comparator<User> sorter) {
        ArrayList<User> outputList = new ArrayList<>(userList);
        outputList.removeIf(Predicate.not(filter));
        outputList.sort(sorter);
        return outputList;
    }

    public static ArrayList<Supplier> getSupplierList() { return supplierList; }
    public static ArrayList<Supplier> getSupplierList(Comparator<Supplier> sorter) {
        ArrayList<Supplier> outputList = new ArrayList<>(supplierList);
        outputList.sort(sorter);
        return outputList;
    }
    public static ArrayList<Supplier> getSupplierList(Predicate<Supplier> filter) {
        ArrayList<Supplier> outputList = new ArrayList<>(supplierList);
        outputList.removeIf(Predicate.not(filter));
        return outputList;
    }
    public static ArrayList<Supplier> getSupplierList(Predicate<Supplier> filter, Comparator<Supplier> sorter) {
        ArrayList<Supplier> outputList = new ArrayList<>(supplierList);
        outputList.removeIf(Predicate.not(filter));
        outputList.sort(sorter);
        return outputList;
    }

    public static ArrayList<Hospital> getHospitalList() { return hospitalList; }
    public static ArrayList<Hospital> getHospitalList(Comparator<Hospital> sorter) {
        ArrayList<Hospital> outputList = new ArrayList<>(hospitalList);
        outputList.sort(sorter);
        return outputList;
    }
    public static ArrayList<Hospital> getHospitalList(Predicate<Hospital> filter) {
        ArrayList<Hospital> outputList = new ArrayList<>(hospitalList);
        outputList.removeIf(Predicate.not(filter));
        return outputList;
    }
    public static ArrayList<Hospital> getHospitalList(Predicate<Hospital> filter, Comparator<Hospital> sorter) {
        ArrayList<Hospital> outputList = new ArrayList<>(hospitalList);
        outputList.removeIf(Predicate.not(filter));
        outputList.sort(sorter);
        return outputList;
    }

    public static ArrayList<Item> getItemList() { return itemList; }
    public static ArrayList<Item> getItemList(Comparator<Item> sorter) {
        ArrayList<Item> outputList = new ArrayList<>(itemList);
        outputList.sort(sorter);
        return outputList;
    }
    public static ArrayList<Item> getItemList(Predicate<Item> filter) {
        ArrayList<Item> outputList = new ArrayList<>(itemList);
        outputList.removeIf(Predicate.not(filter));
        return outputList;
    }
    public static ArrayList<Item> getItemList(Predicate<Item> filter, Comparator<Item> sorter) {
        ArrayList<Item> outputList = new ArrayList<>(itemList);
        outputList.removeIf(Predicate.not(filter));
        outputList.sort(sorter);
        return outputList;
    }

    public static ArrayList<Transaction> getTransactionList() { return transactionList; }
    public static ArrayList<Transaction> getTransactionList(Comparator<Transaction> sorter) {
        ArrayList<Transaction> outputList = new ArrayList<>(transactionList);
        outputList.sort(sorter);
        return outputList;
    }
    public static ArrayList<Transaction> getTransactionList(Predicate<Transaction> filter) {
        ArrayList<Transaction> outputList = new ArrayList<>(transactionList);
        outputList.removeIf(Predicate.not(filter));
        return outputList;
    }
    public static ArrayList<Transaction> getTransactionList(Predicate<Transaction> filter, Comparator<Transaction> sorter) {
        ArrayList<Transaction> outputList = new ArrayList<>(transactionList);
        outputList.removeIf(Predicate.not(filter));
        outputList.sort(sorter);
        return outputList;
    }

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

    /* Adding records */
    public static void addUser(User user) { userList.add(user); }
    public static void addSupplier(Supplier supplier) { supplierList.add(supplier); }
    public static void addHospital(Hospital hospital) { hospitalList.add(hospital); }
    public static void addTransaction(Transaction transaction) { transactionList.add(transaction); }
    public static void addItem(Item item) { itemList.add(item); }

    /* Deleting records */
    public static void deleteUser(User user) { userList.remove(user); }

    public static void main(String[] args) {
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
        userList.forEach(System.out::println);
        updateRecords();
    }
}