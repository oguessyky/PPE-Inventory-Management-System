import javax.swing.*;
public class UserEdit extends EditForm {

    private final EditType editType;
    private User user;
    private boolean editSelf = false;

    public UserEdit() {
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
            new JTextField(user.getUserID()),
            new JTextField(user.getName()),
            new JComboBox<>(new User.Type[] { User.Type.Admin, User.Type.Staff }),
            new JButton("Reset Password")
        });
        this.editType = EditType.Update;
        this.user = user;
        setInputOf(2, user.getUserType());
        ((JButton)inputFields[3]).addActionListener((evt) -> {
            user.setPassword("12345678");
            Records.updateRecords();
            JOptionPane.showMessageDialog(this, "Password reset successful.","Info",JOptionPane.INFORMATION_MESSAGE);
        });
    }

    public UserEdit(User user, boolean editSelf) {
        super(editSelf ? "UPDATE PROFILE" : "EDIT USER",
        editSelf ? new String[] {
            "User ID :",
            "Name :",
            "Password :",
            "New Password :",
            "Confirm New Password :"
        } : new String[] {
            "User ID :",
            "Name :",
            "User Type :",
            "Reset Password :"
        },
        editSelf ? new JComponent[] {
            new JTextField(user.getUserID()),
            new JTextField(user.getName()),
            new JPasswordField(),
            new JPasswordField(),
            new JPasswordField()
        } : new JComponent[] {
            new JTextField(user.getUserID()),
            new JTextField(user.getName()),
            new JComboBox<>(new User.Type[] { User.Type.Admin, User.Type.Staff }),
            new JButton("Reset Password")
        });
        this.editType = EditType.Update;
        this.user = user;
        this.editSelf = editSelf;
        if (!editSelf) {
            setInputOf(2, user.getUserType());
            ((JButton)inputFields[3]).addActionListener((evt) -> {
                user.setPassword("12345678");
                Records.updateRecords();
                JOptionPane.showMessageDialog(this, "Password reset successful.","Info",JOptionPane.INFORMATION_MESSAGE);
            });
        }
    }


    @Override
    protected void exit() {
        this.dispose();
        Main.manage(Main.DataType.User);
    }

    @Override
    protected void submit() {
        String userID = (String)getInputOf(0);
        String name = (String)getInputOf(1);
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
        } else if (editSelf) {
            String password = (String)getInputOf(2);
            String newPassword = (String)getInputOf(3);
            String newPasswordConfirm = (String)getInputOf(4);
            if (!password.equals(user.getPassword())) {
                Main.showError(this, "Incorrect password!");
            } else if (newPassword.contains(";")) {
                Main.showError(this, "Please choose another password."); // LMAO i cannot think of a reason to stop users from using ; in their password
            } else if (!newPassword.equals(newPasswordConfirm)) {
                Main.showError(this, "Password mismatch!");
            } else {
                user.setUserID(userID);
                user.setName(name);
                if (!newPassword.isBlank()) {
                    user.setPassword(newPassword);
                }
                Records.updateRecords();
                dispose();
                Main.showMenu();
            }
        } else {
            User.Type userType = (User.Type)getInputOf(2);
            switch (editType) {
                case Update -> {
                    user.setUserID(userID);
                    user.setName(name);
                    user.setUserType(userType);
                    Records.updateRecords();
                    this.dispose();
                    Main.manage(Main.DataType.User);
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
                        Records.addUser(new User(userID, name, password, userType));
                        this.dispose();
                        if (editType != EditType.FirstRun) {
                            Main.manage(Main.DataType.User);
                        }
                    }
                }
            }

        }
    }
}
