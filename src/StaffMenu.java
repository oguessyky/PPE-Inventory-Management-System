import javax.swing.*;
public class StaffMenu extends Menu {
    public StaffMenu() {
        super("STAFF MENU",
        new JButton[] {
            new JButton("Supplier management"),
            new JButton("Hospital management"),
            new JButton("Inventory management"),
            new JButton("Update profile"),
            new JButton("Logout")
        });

        menuButtons[0].setBackground(new java.awt.Color(0, 102, 153));
        menuButtons[0].setFont(new java.awt.Font("Swis721 BlkCn BT", 0, 18)); // NOI18N
        menuButtons[0].setForeground(new java.awt.Color(255, 255, 255));
        menuButtons[0].addActionListener((evt) -> { Main.hideMenu(); Main.manage(Main.DataType.Supplier); });

        menuButtons[1].setBackground(new java.awt.Color(0, 153, 153));
        menuButtons[1].setFont(new java.awt.Font("Swis721 Cn BT", 1, 18)); // NOI18N
        menuButtons[1].setForeground(new java.awt.Color(255, 255, 255));
        menuButtons[1].addActionListener((evt) -> { Main.hideMenu(); Main.manage(Main.DataType.Hospital); });

        menuButtons[2].setBackground(new java.awt.Color(0, 204, 204));
        menuButtons[2].setFont(new java.awt.Font("Swis721 Cn BT", 1, 18)); // NOI18N
        menuButtons[2].setForeground(new java.awt.Color(255, 255, 255));
        menuButtons[2].addActionListener((evt) -> { Main.hideMenu(); Main.manage(Main.DataType.Item); });

        menuButtons[3].setBackground(new java.awt.Color(0, 204, 204));
        menuButtons[3].setFont(new java.awt.Font("Swis721 Cn BT", 1, 18)); // NOI18N
        menuButtons[3].setForeground(new java.awt.Color(255, 255, 255));
        menuButtons[3].addActionListener((evt) -> { Main.hideMenu(); Main.updateProfile(); });

        menuButtons[4].setBackground(new java.awt.Color(255, 20, 20));
        menuButtons[4].setFont(new java.awt.Font("Swis721 Cn BT", 1, 18)); // NOI18N
        menuButtons[4].setForeground(new java.awt.Color(255, 255, 255));
        menuButtons[4].addActionListener((evt) -> { Main.logout(); });

    }
}
