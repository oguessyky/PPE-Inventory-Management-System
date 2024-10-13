import javax.swing.*;
public class StaffMenu extends Menu {
    public StaffMenu() {
        super("ADMIN MENU",
        new JButton[] {
            new JButton("Inventory management"),
            new JButton("Update profile"),
            new JButton("Logout")
        });

        menuButtons[0].setBackground(new java.awt.Color(0, 204, 204));
        menuButtons[0].setFont(new java.awt.Font("Swis721 Cn BT", 1, 18)); // NOI18N
        menuButtons[0].setForeground(new java.awt.Color(255, 255, 255));
        menuButtons[0].addActionListener((evt) -> { Main.hideMenu(); Main.manage(Main.DataType.Item); });

        menuButtons[1].setBackground(new java.awt.Color(0, 204, 204));
        menuButtons[1].setFont(new java.awt.Font("Swis721 Cn BT", 1, 18)); // NOI18N
        menuButtons[1].setForeground(new java.awt.Color(255, 255, 255));
        menuButtons[1].addActionListener((evt) -> { Main.hideMenu(); Main.updateProfile(); });

        menuButtons[2].setBackground(new java.awt.Color(255, 20, 20));
        menuButtons[2].setFont(new java.awt.Font("Swis721 Cn BT", 1, 18)); // NOI18N
        menuButtons[2].setForeground(new java.awt.Color(255, 255, 255));
        menuButtons[2].addActionListener((evt) -> { Main.logout(); });

    }
}
