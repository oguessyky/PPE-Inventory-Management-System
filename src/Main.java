import java.awt.Component;
import javax.swing.JOptionPane;

public abstract class Main {

    public enum FormType {
        User,
        Supplier,
        Hospital,
        Item,
        Transaction
    }

    private static User currentUser;
    private static Menu menu;
    private static final loginGUI loginGUI = new loginGUI();
    private static userManagement userManagement;
    private static EditForm form;

    public static void main(String[] args) {
        // try (Scanner userInput = new Scanner(System.in)) {
        //     String input;
        //     do {
        //         System.out.print("Input your gmail: ");
        //         //input = userInput.findInLine("(?=.*Try)(?=.*test).*");
        //         //input = userInput.findInLine("^Try.*test.*");
        //         input = userInput.nextLine().trim();
        //         if (!input.matches("^[\\w.-]+@gmail\\.com$")) {
        //             System.err.println("Invalid email. Please try again.\n");
        //         }
        //     } while (!input.matches("^[\\w.-]+@gmail\\.com$"));
        //     System.out.println("You entered: " + input);
        // }

        // int i = 0;
        // do {
        //     System.out.println("try: " + ++i);
        // } while (i < 10);
        Records.readRecords();
        while (Records.getUserList(User.IsType(User.Type.Admin)).isEmpty()) {
            newForm(FormType.User,true);
        }
        while (Records.getSupplierList().size() < 3) {
            newForm(FormType.Supplier,true);
        }
        while (Records.getHospitalList().size() < 3) {
            newForm(FormType.Hospital,true);
        }
        /* Get all item data, GUI stuff */
        for (String itemCode : new String[] {"HC","FS","MS","GL","GW","SC"}) {
            while (Records.getItem(itemCode) == null) {
                addItem(itemCode);
                while (form.isDisplayable()) {}
            }
        }

        loginGUI.setVisible(true);

        // initializeMenu(Records.getUser("01"));
    }

    public static void initializeMenu(User user) {
        currentUser = user;
        switch (user.getUserType()) {
            case Admin -> {
                menu = new Admin_Menu();
            }
            case Staff -> {

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
    public static void manageUser() {
        userManagement = new userManagement();
        userManagement.setVisible(true);
    }
    public static User getUser() { return currentUser; }

    public static void editUser(User user) {
        form = form instanceof UserEdit && form.isDisplayable() ? form : new UserEdit(user);
        form.setVisible(true);
    }

    public static void editSupplier(Supplier supplier) {
        form = form instanceof UserEdit && form.isDisplayable() ? form : new SupplierEdit(supplier);
        form.setVisible(true);
    }

    public static void newForm(FormType formType) {
        form = switch (formType) {
            case User -> form instanceof UserEdit && form.isDisplayable() ? form : new UserEdit();
            case Supplier -> form instanceof SupplierEdit && form.isDisplayable() ? form : new SupplierEdit();
            case Hospital -> form instanceof HospitalEdit && form.isDisplayable() ? form : new HospitalEdit();
            default -> throw new AssertionError("Incompatible form type");
        };
        form.setVisible(true);
    }
    public static void newForm(FormType formType, boolean firstRun) {
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


    public static void showError(Component parentComponent,String errorMsg) {
        JOptionPane.showMessageDialog(parentComponent, errorMsg,"Error",JOptionPane.ERROR_MESSAGE);
    }
}