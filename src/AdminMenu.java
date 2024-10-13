import javax.swing.*;
public class AdminMenu extends Menu {
    public AdminMenu() {
        super("ADMIN MENU",
        new JButton[] {
            new JButton("User management"),
            new JButton("Supplier management"),
            new JButton("Hospital management"),
            new JButton("Inventory management"),
        });

        menuButtons[0].setBackground(new java.awt.Color(0, 51, 102));
        menuButtons[0].setFont(new java.awt.Font("Swis721 BlkCn BT", 0, 18)); // NOI18N
        menuButtons[0].setForeground(new java.awt.Color(255, 255, 255));
        menuButtons[0].addActionListener((evt) -> { Main.hideMenu(); Main.manage(Main.DataType.User); });
        
        menuButtons[1].setBackground(new java.awt.Color(0, 102, 153));
        menuButtons[1].setFont(new java.awt.Font("Swis721 BlkCn BT", 0, 18)); // NOI18N
        menuButtons[1].setForeground(new java.awt.Color(255, 255, 255));
        menuButtons[1].addActionListener((evt) -> { Main.hideMenu(); Main.manage(Main.DataType.Supplier); });

        menuButtons[2].setBackground(new java.awt.Color(0, 153, 153));
        menuButtons[2].setFont(new java.awt.Font("Swis721 Cn BT", 1, 18)); // NOI18N
        menuButtons[2].setForeground(new java.awt.Color(255, 255, 255));
        menuButtons[2].addActionListener((evt) -> { Main.hideMenu(); Main.manage(Main.DataType.Hospital); });

        menuButtons[3].setBackground(new java.awt.Color(0, 204, 204));
        menuButtons[3].setFont(new java.awt.Font("Swis721 Cn BT", 1, 18)); // NOI18N
        menuButtons[3].setForeground(new java.awt.Color(255, 255, 255));
        menuButtons[3].addActionListener((evt) -> { Main.hideMenu(); Main.manage(Main.DataType.Item); });

    }

    public static void main(String[] args) {
        new AdminMenu().setVisible(true);
    }
}
