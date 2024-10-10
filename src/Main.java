


public abstract class Main {

    private static Menu menu;
    private static final loginGUI loginGUI = new loginGUI();
    private static userManagement userManagement;

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
        if (Records.getUserList().isEmpty()) {
            /* Get first user data, GUI stuff */
        }
        if (Records.getSupplierList().size() < 3) {
            /* Get min 3 supplier data, GUI stuff */
        }
        if (Records.getHospitalList().size() < 3) {
            /* Get min 3 hospital data, GUI stuff */
        }
        /* Get all item data, GUI stuff */
        for (String itemCode : new String[] {"HS","FS","MS","GL","GW","SC"}) {
            if (Records.getItem(itemCode) == null) {
                
            }
        }

        loginGUI.setVisible(true);

        Records.updateRecords();
    }

    public static void initializeMenu(User user) {
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
}
