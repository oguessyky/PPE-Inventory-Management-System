import javax.swing.*;
public class UserEdit extends Form {
    public UserEdit() {
        super("ADD USER",
        new String[] {
            "User ID :",
            "Name :",
            "Password :",
            "Confirm Password :",
            "Uesr Type :"
        },
        new JComponent[] {
            new JTextField(),
            new JTextField(),
            new JPasswordField(),
            new JPasswordField(),
            new JComboBox<>(new User.Type[] { User.Type.Admin, User.Type.Staff })
        });
    }

    @Override
    protected void exit() {
        this.dispose();
        Main.manageUser();
    }

    @Override
    protected void submit() {
        String userID = ((JTextField)inputFields[0]).getText();
        String name = ((JTextField)inputFields[1]).getText();
        String password = String.valueOf(((JPasswordField)inputFields[2]).getPassword());
        String passwordConfirm = String.valueOf(((JPasswordField)inputFields[3]).getPassword());
        User.Type userType = (User.Type)((JComboBox<User.Type>)inputFields[4]).getSelectedItem();

        if (userID.isBlank()) {
            Main.showError(this, "UserID cannot be blank!");
        } else if (userID.contains(";")) {
            Main.showError(this, "Invalid UserID!"); // can further restrict via regex if needed
        } else if (Records.getUser(userID) != null) {
            Main.showError(this, "UserID already exist!");
        } else if (name.isBlank()) {
            Main.showError(this, "Name cannot be blank!");
        } else if (name.contains(";")) {
            Main.showError(this, "Invalid Name!"); // can further restrict via regex if needed
        } else if (password.isBlank()) {
            Main.showError(this, "Password cannot be blank!");
        } else if (password.contains(";")) {
            Main.showError(this, "Please choose another password."); // LMAO i cannot think of a reason to stop users from using ; in their password
        } else if (!password.equals(passwordConfirm)) {
            Main.showError(this, "Password mismatch!");
        } else {
            User newUser = new User(userID, name, password, userType);
            Records.addUser(newUser);
            Records.updateRecords();
            this.dispose();
            Main.manageUser();
        }
    }

    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(update.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(update.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(update.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(update.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new UserEdit().setVisible(true);
            }
        });
    }

}
