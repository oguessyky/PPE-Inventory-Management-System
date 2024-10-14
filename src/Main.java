import java.awt.Component;
import javax.swing.JOptionPane;

public abstract class Main {

    public enum DataType {
        User,
        Supplier,
        Hospital,
        Item,
        Transaction,
        TransactionSummary
    }

    private static User currentUser;
    private static Menu menu;
    private static loginGUI loginGUI = new loginGUI();
    private static EditForm form;
    private static DataTable dataTable;

    public static void main(String[] args) {

        Records.readRecords();
        while (Records.getUserList(User.IsType(User.Type.Admin)).isEmpty()) {
            newForm(DataType.User,true);
        }
        while (Records.getSupplierList().size() < 3) {
            newForm(DataType.Supplier,true);
        }
        while (Records.getHospitalList().size() < 3) {
            newForm(DataType.Hospital,true);
        }
        /* Get all item data, GUI stuff */
        for (String itemCode : new String[] {"HC","FS","MS","GL","GW","SC"}) {
            while (Records.getItem(itemCode) == null) {
                addItem(itemCode);
                while (form.isDisplayable()) {}
            }
        }

        loginGUI.setVisible(true);
    }

    public static void initializeMenu(User user) {
        currentUser = user;
        switch (user.getUserType()) {
            case Admin -> {
                menu = new AdminMenu();
            }
            case Staff -> {
                menu = new StaffMenu();
            }
        }
        showMenu();
    }

    public static void showMenu() {
        menu.setVisible(true);
    }
    public static void hideMenu() {
        menu.setVisible(false);
    }

    public static void manage(DataType dataType) {
        dataTable = switch (dataType) {
            case User -> dataTable instanceof UserTable && dataTable.isDisplayable() ? dataTable : new UserTable();
            case Supplier -> dataTable instanceof SupplierTable && dataTable.isDisplayable() ? dataTable : new SupplierTable();
            case Hospital -> dataTable instanceof HospitalTable && dataTable.isDisplayable() ? dataTable : new HospitalTable();
            case Item -> dataTable instanceof ItemTable && dataTable.isDisplayable() ? dataTable : new ItemTable();
            case Transaction -> dataTable instanceof TransactionTable && dataTable.isDisplayable() ? dataTable : new TransactionTable();
            case TransactionSummary -> dataTable instanceof TransactionSummaryTable && dataTable.isDisplayable() ? dataTable : new TransactionSummaryTable();
        };
        dataTable.setVisible(true);
    }

    public static User getUser() { return currentUser; }

    public static void editForm(DataType formType, Object data) {
        form = switch (formType) {
            case User -> form instanceof UserEdit && form.isDisplayable() ? form : new UserEdit((User)data);
            case Supplier -> form instanceof SupplierEdit && form.isDisplayable() ? form : new SupplierEdit((Supplier)data);
            case Hospital -> form instanceof HospitalEdit && form.isDisplayable() ? form : new HospitalEdit((Hospital)data);
            case Item -> form instanceof ItemEdit && form.isDisplayable() ? form : new ItemEdit((Item)data);
            case Transaction -> form instanceof TransactionEdit && form.isDisplayable() ? form : new TransactionEdit((Transaction.Type)data);
            default -> throw new AssertionError("Incompatible form type");
        };
        form.setVisible(true);
    }

    public static void newForm(DataType formType) {
        form = switch (formType) {
            case User -> form instanceof UserEdit && form.isDisplayable() ? form : new UserEdit();
            case Supplier -> form instanceof SupplierEdit && form.isDisplayable() ? form : new SupplierEdit();
            case Hospital -> form instanceof HospitalEdit && form.isDisplayable() ? form : new HospitalEdit();
            default -> throw new AssertionError("Incompatible form type");
        };
        form.setVisible(true);
    }
    public static void newForm(DataType formType, boolean firstRun) {
        form = switch (formType) {
            case User -> form instanceof UserEdit && form.isDisplayable() ? form : new UserEdit(firstRun);
            case Supplier -> form instanceof SupplierEdit && form.isDisplayable() ? form : new SupplierEdit(firstRun);
            case Hospital -> form instanceof HospitalEdit && form.isDisplayable() ? form : new HospitalEdit(firstRun);
            default -> throw new AssertionError("Incompatible form type");
        };
        form.setVisible(true);
    }

    public static void addItem(String itemCode) {
        form = form instanceof ItemEdit && form.isDisplayable() ? form : new ItemEdit(itemCode,
        switch (itemCode) {
            case "HC" -> "Head Cover";
            case "FS" -> "Face Shield";
            case "MS" -> "Mask";
            case "GL" -> "Gloves";
            case "GW" -> "Gown";
            case "SC" -> "Shoe Covers";
            default -> throw new AssertionError("Invalid item code");
        });
        form.setVisible(true);
    }

    public static void updateProfile() {
        form = form instanceof UserEdit && form.isDisplayable() ? form : new UserEdit(currentUser,true);
        form.setVisible(true);
    }

    public static void logout() {
        menu.dispose();
        currentUser = null;
        loginGUI = new loginGUI();
        loginGUI.setVisible(true);
    }

    public static void showError(Component parentComponent,String errorMsg) {
        JOptionPane.showMessageDialog(parentComponent, errorMsg,"Error",JOptionPane.ERROR_MESSAGE);
    }
}