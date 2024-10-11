import javax.swing.*;
public class UserEdit extends Form {

    EditType editType;
    User user;

    public UserEdit() {
        super("ADD USER",
        new String[] {
            "User ID :",
            "Name :",
            "Password :",
            "Confirm Password :",
            "User Type :"
        },
        new JComponent[] {
            new JTextField(),
            new JTextField(),
            new JPasswordField(),
            new JPasswordField(),
            new JComboBox<>(new User.Type[] { User.Type.Admin, User.Type.Staff })
        });
        this.editType = EditType.Add;
    }

    public UserEdit(boolean firstRun) {
        super("ADD USER",
        new String[] {
            "User ID :",
            "Name :",
            "User Type :",
            "Password :",
            "Confirm Password :"
        },
        new JComponent[] {
            new JTextField(),
            new JTextField(),
            new JComboBox<>(new User.Type[] { User.Type.Admin, User.Type.Staff }),
            new JPasswordField(),
            new JPasswordField()
        });
        this.editType = firstRun ? EditType.FirstRun : EditType.Add;
        backButton.setVisible(!firstRun);
        inputFields[2].setEnabled(!firstRun);
    }

    public UserEdit(User user) {
        super("EDIT USER",
        new String[] {
            "User ID :",
            "Name :",
            "User Type :",
            "Reset Password :"
        },
        new JComponent[] {
            new JTextField(),
            new JTextField(),
            new JComboBox<>(new User.Type[] { User.Type.Admin, User.Type.Staff }),
            new JButton()
        });
        this.editType = EditType.Update;
        this.user = user;
        setInputOf(0, user.getUserID());
        setInputOf(1, user.getName());
        setInputOf(2, user.getUserType());
        setInputOf(3, "Reset Password");
        ((JButton)inputFields[3]).addActionListener((evt) -> {
            user.setPassword("12345678");
            Records.updateRecords();
            JOptionPane.showMessageDialog(this, "Password reset successful.","Info",JOptionPane.INFORMATION_MESSAGE);
        });
    }


    @Override
    protected void exit() {
        this.dispose();
        Main.manageUser();
    }

    @Override
    protected void submit() {
        String userID = (String)getInputOf(0);
        String name = (String)getInputOf(1);
        User.Type userType = (User.Type)getInputOf(2);

        if (userID.isBlank()) {
            Main.showError(this, "UserID cannot be blank!");
        } else if (userID.contains(";")) {
            Main.showError(this, "Invalid UserID!"); // can further restrict via regex if needed
        } else if (Records.getUser(userID) != null && Records.getUser(userID) != user) {
            Main.showError(this, "UserID already exist!");
        } else if (name.isBlank()) {
            Main.showError(this, "Name cannot be blank!");
        } else if (name.contains(";")) {
            Main.showError(this, "Invalid Name!"); // can further restrict via regex if needed
        } else {
            switch (editType) {
                case Update -> {
                    user.setUserID(userID);
                    user.setName(name);
                    user.setUserType(userType);
                    Records.updateRecords();
                    this.dispose();
                    Main.manageUser();
                }
                default -> {
                    String password = (String)getInputOf(3);
                    String passwordConfirm = (String)getInputOf(4);
                    if (password.isBlank()) {
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
                        if (editType != EditType.FirstRun) {
                            Main.manageUser();
                        }
                    }
                }
            }
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
                new UserEdit(true).setVisible(true);
            }
        });
    }

}
