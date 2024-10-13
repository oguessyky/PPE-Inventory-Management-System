import javax.swing.*;
public class Menu extends JFrame {

    protected JButton[] menuButtons;

    public Menu(String title, JButton[] menuButtons) {

        this.menuButtons = menuButtons;

        jPanel1 = new javax.swing.JPanel();
        Admin_H = new javax.swing.JLabel(title,SwingConstants.CENTER);

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jPanel1.setBackground(new java.awt.Color(0, 51, 102));

        Admin_H.setBackground(new java.awt.Color(255, 255, 255));
        Admin_H.setFont(new java.awt.Font("Swis721 BlkCn BT", 0, 48));
        Admin_H.setForeground(new java.awt.Color(255, 255, 255));

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(Admin_H, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addComponent(Admin_H)
                .addContainerGap(21, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        GroupLayout.ParallelGroup parallelGroup = layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING);
        GroupLayout.SequentialGroup sequentialGroup = layout.createSequentialGroup()
            .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE);
        for (JButton button : menuButtons) {
            parallelGroup = parallelGroup.addComponent(button, javax.swing.GroupLayout.PREFERRED_SIZE, 268, javax.swing.GroupLayout.PREFERRED_SIZE);
            sequentialGroup = sequentialGroup.addGap(18, 18, 18)
            .addComponent(button, javax.swing.GroupLayout.PREFERRED_SIZE, 63, javax.swing.GroupLayout.PREFERRED_SIZE);
        }

        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE)
            .addGroup(javax.swing.GroupLayout.Alignment.CENTER, layout.createSequentialGroup()
                .addGap(141,141,141)
                .addGroup(parallelGroup)
                .addGap(141, 141, 141))
        );

        layout.linkSize(javax.swing.SwingConstants.HORIZONTAL, menuButtons);

        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
            .addGroup(sequentialGroup
                .addContainerGap(58, Short.MAX_VALUE))
        );

        layout.linkSize(javax.swing.SwingConstants.VERTICAL, menuButtons);

        pack();
    }

    protected javax.swing.JLabel Admin_H;
    protected javax.swing.JPanel jPanel1;
}
