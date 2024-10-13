import java.util.ArrayList;
import java.util.Comparator;
import java.util.function.Predicate;
import javax.swing.*;
public class UserTable extends DataTable {
    public UserTable() {
        super("USER MANAGEMENT",
            new String[] {"User ID","Name","Type"},
            new Class<?>[] {String.class,String.class,User.Type.class},
            new String[] {"User ID :","Name :","Type :"},
            new JComponent[] {
                new JTextField(),
                new JTextField(),
                new JComboBox<>(new User.Type[] { null, User.Type.Admin, User.Type.Staff })
            },
            new JButton[] {
                new JButton("Add User"),
                new JButton("Edit User"),
                new JButton("Delete User")
            }
        );

        tableButtons[0].setBackground(new java.awt.Color(0, 51, 102));
        tableButtons[0].setFont(new java.awt.Font("Segoe UI", 1, 12));
        tableButtons[0].setForeground(new java.awt.Color(255, 255, 255));
        tableButtons[0].addActionListener((evt) -> {
            dispose();
            Main.newForm(Main.DataType.User);
        });

        tableButtons[1].setBackground(new java.awt.Color(0, 51, 102));
        tableButtons[1].setFont(new java.awt.Font("Segoe UI", 1, 12));
        tableButtons[1].setForeground(new java.awt.Color(255, 255, 255));
        tableButtons[1].addActionListener((evt) -> {
            User user = userList.get(table.getSelectedRow());
            if (user == Main.getUser()) {
                Main.showError(this, "Cannot edit current user.");
            } else {
                this.dispose();
                Main.editForm(Main.DataType.User,user);
            }
        });

        tableButtons[2].setBackground(new java.awt.Color(153, 0, 51));
        tableButtons[2].setFont(new java.awt.Font("Segoe UI", 1, 12));
        tableButtons[2].setForeground(new java.awt.Color(255, 255, 255));
        tableButtons[2].addActionListener((evt) -> {
            User user = userList.get(table.getSelectedRow());
            if (user == Main.getUser()) {
                Main.showError(this, "Cannot delete current user.");
            } else {
                Records.deleteUser(user);
                updateTableData();
            }
        });
    }

    @Override
    protected void dataEditSetEnabled(boolean enabled) {
        tableButtons[1].setEnabled(enabled);
        tableButtons[2].setEnabled(enabled);
    }

    @Override
    protected void updateTableData() {
        Predicate<User> filter = User.IDContains((String)getInputOf(0))
        .and(User.NameContains((String)getInputOf(1)));
        if (getInputOf(2) != null) {
            filter = filter.and(User.IsType((User.Type)getInputOf(2)));
        }
        String[] newHeader = tableHeader.clone();
        Comparator<User> sorter = switch (selectedColumn) {
            case 0 -> User.IDComparator;
            case 1 -> User.NameComparator;
            case 2 -> User.TypeComparator;
            default -> throw new IndexOutOfBoundsException("Selected Column out of bounds");
        };

        if (orderASC) {
            newHeader[selectedColumn] += " \u25B2";
        } else {
            sorter = sorter.reversed();
            newHeader[selectedColumn] += " \u25BC";
        }
        sorter = sorter.thenComparing(User.IDComparator);
        userList = Records.getUserList(filter,sorter);
        data = new Object[userList.size()][tableHeader.length];
        User user;
        for (int idx = 0; idx < userList.size(); idx++) {
            user = userList.get(idx);
            data[idx] = new Object[] {
                user.getUserID(),
                user.getName(),
                user.getUserType()
            };
        }
        tableModel.setDataVector(data, newHeader);
    }

    ArrayList<User> userList;
}
