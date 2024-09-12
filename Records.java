
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

    public static void main(String[] args) {
        readUsers(new Scanner("afsdawfa|adadaad|ADawdasas|Admin\nasd|afsdawfa|adadaad|ADawdasas"));
    }
}
